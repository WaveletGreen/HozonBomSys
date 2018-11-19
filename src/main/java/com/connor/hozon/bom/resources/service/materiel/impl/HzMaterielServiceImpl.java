package com.connor.hozon.bom.resources.service.materiel.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddDataToChangeOrderReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzMaterielReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzMaterielRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzMaterielFactory;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.bom.resources.domain.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.executors.ExecutorServices;
import com.connor.hozon.bom.resources.mybatis.change.HzApplicantChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.materiel.HzMaterielService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.sys.entity.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.change.HzApplicantChangeRecord;
import sql.pojo.change.HzAuditorChangeRecord;
import sql.pojo.change.HzChangeDataRecord;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.HzMaterielRecord;

import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/7/3
 * @Description:
 */
@Service("hzMaterielService")
public class HzMaterielServiceImpl implements HzMaterielService {
    @Autowired
    private HzMaterielDAO hzMaterielDAO;

    @Autowired
    private HzFactoryDAO hzFactoryDAO;

    @Autowired
    private HzChangeDataRecordDAO hzChangeDataRecordDAO;

    @Autowired
    private HzApplicantChangeDAO hzApplicantChangeDAO;

    @Autowired
    private HzAuditorChangeDAO hzAuditorChangeDAO;
    @Override
    public WriteResultRespDTO editHzMateriel(EditHzMaterielReqDTO editHzMaterielReqDTO) {
        WriteResultRespDTO respDTO = new WriteResultRespDTO();
        if(StringUtil.isEmpty(editHzMaterielReqDTO.getProjectId())){
            respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
            respDTO.setErrMsg("请选择项目！");
            return respDTO;
        }
        try{
            HzMaterielRecord record = new HzMaterielRecord();
            User user = UserInfo.getUser();
            if(!editHzMaterielReqDTO.getFactoryCode().equals("") && null != editHzMaterielReqDTO.getFactoryCode()){
                HzFactory hzFactory = hzFactoryDAO.findFactory("", editHzMaterielReqDTO.getFactoryCode());
                if(hzFactory == null){
                    String puid = UUID.randomUUID().toString();
                    hzFactory =  new HzFactory();
                    hzFactory.setPuid(puid);
                    hzFactory.setpFactoryCode(editHzMaterielReqDTO.getFactoryCode());
                    hzFactory.setpUpdateName(user.getUserName());
                    hzFactory.setpCreateName(user.getUserName());
                    int i = hzFactoryDAO.insert(hzFactory);
                    if(i<0){
                        return WriteResultRespDTO.getFailResult();
                    }
                    record.setpFactoryPuid(puid);
                }else{
                    record.setpFactoryPuid(hzFactory.getPuid());
                }
            }
            record.setpCreateName(user.getUserName());
            record.setpVinPerNo(editHzMaterielReqDTO.getpVinPerNo());
            record.setpSpareMaterial(editHzMaterielReqDTO.getpSpareMaterial());
            String loosePartFlag = editHzMaterielReqDTO.getpLoosePartFlag();
            if("N".equals(loosePartFlag.toUpperCase())){
                record.setpLoosePartFlag(0);
            }else if("Y".equals(loosePartFlag.toUpperCase())){
                record.setpLoosePartFlag(1);
            }else{
                record.setpLoosePartFlag(null);
            }
            record.setpMrpController(editHzMaterielReqDTO.getpMrpController());
            record.setpUpdateName(user.getUserName());
            record.setPuid(editHzMaterielReqDTO.getPuid());
            record.setpBasicUnitMeasure(editHzMaterielReqDTO.getpBasicUnitMeasure());
            if("Y".equals(editHzMaterielReqDTO.getpInventedPart().toUpperCase())){
                record.setpInventedPart(1);
            }else if("N".equals(editHzMaterielReqDTO.getpInventedPart().toUpperCase())){
                record.setpInventedPart(0);
            }else {
                record.setpInventedPart(null);
            }
            record.setResource(editHzMaterielReqDTO.getResource());
            if("Y".equals(editHzMaterielReqDTO.getpColorPart().toUpperCase())){
                record.setpColorPart(1);
            }else if("N".equals(editHzMaterielReqDTO.getpColorPart().toUpperCase())){
                record.setpColorPart(0);
            }else {
                record.setpColorPart(null);
            }
            record.setpHeight(editHzMaterielReqDTO.getpHeight());

            if("内饰件".equals(editHzMaterielReqDTO.getpInOutSideFlag())){
                record.setpInOutSideFlag(1);
            }else if("外饰件".equals(editHzMaterielReqDTO.getpInOutSideFlag())){
                record.setpInOutSideFlag(0);
            }else {
                record.setpInOutSideFlag(null);
            }

            if("Y".equals(editHzMaterielReqDTO.getP3cPartFlag().toUpperCase())){
                record.setP3cPartFlag(1);
            }else if("N".equals(editHzMaterielReqDTO.getP3cPartFlag().toUpperCase())){
                record.setP3cPartFlag(0);
            }else {
                record.setP3cPartFlag(null);
            }
            record.setpPartImportantDegree(editHzMaterielReqDTO.getpPartImportantDegree());
            int i = hzMaterielDAO.update(record);
            if(i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return WriteResultRespDTO.getFailResult();
        }

        return WriteResultRespDTO.getFailResult();
    }

    @Override
    public WriteResultRespDTO deleteHzMateriel(String puid) {
        try{
            WriteResultRespDTO respDTO = new WriteResultRespDTO();
            if(StringUtil.isEmpty(puid)){
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                respDTO.setErrMsg("请选择一条需要删除的数据！");
                return respDTO;
            }
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return WriteResultRespDTO.getFailPrivilege();
            }
            int i = hzMaterielDAO.delete(puid);
            if(i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getFailResult();
    }

    @Override
    public Page<HzMaterielRespDTO> findHzMaterielForPage(HzMaterielByPageQuery query) {
        //先找出对应的物料类型，添加到物料类型表，将其数据自动带出
        /**
         * 物料类型  严格按照注释来读写数据
         * （11 整车超级物料主数据
         * 21 整车衍生物料主数据
         * 31 虚拟件物料主数据
         * 41半成品物料主数据
         * 51 生产性外购物料主数据
         * 61自制备件物料主数据）
         */
        try{
            Page<HzMaterielRecord> page = hzMaterielDAO.findHzMaterielForPage(query);
            int num = (page.getPageNumber()-1)*page.getPageSize();
            if(page == null || page.getResult() == null){
                return new Page<>(page.getPageNumber(),page.getPageSize(),0);
            }

            List<HzMaterielRecord> recordList = page.getResult();
            List<HzMaterielRespDTO> respDTOS = new ArrayList<>();
            for(HzMaterielRecord record:recordList){
                HzMaterielRespDTO respDTO = HzMaterielFactory.hzMaterielRecordToRespDTO(record);
                respDTO.setNo(++num);
                respDTOS.add(respDTO);
            }
            return new Page<>(page.getPageNumber(),page.getPageSize(),page.getTotalCount(),respDTOS);
        }catch (Exception e){
            return new Page<>(0,0,0);
        }
    }


    @Override
    public HzMaterielRespDTO getHzMateriel(HzMaterielQuery query) {
        try{
            HzMaterielRecord record = hzMaterielDAO.getHzMaterielRecord(query);
            if(record!= null){
               return HzMaterielFactory.hzMaterielRecordToRespDTO(record);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return  null;
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public WriteResultRespDTO dataToChangeOrder(AddDataToChangeOrderReqDTO reqDTO) {
        if(StringUtil.isEmpty(reqDTO.getPuids()) || StringUtil.isEmpty(reqDTO.getProjectId())
                || null == reqDTO.getOrderId()){
            return WriteResultRespDTO.IllgalArgument();
        }

        try {
            //获取申请人信息
            User user = UserInfo.getUser();
            Long applicantId = Long.valueOf(user.getId());

            //表单id
            Long orderId = reqDTO.getOrderId();

            //获取审核人信息
            Long auditorId = reqDTO.getAuditorId();
            //数据库表名
            String tableName = ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName();
            //获取数据信息
            List<String> puids = Lists.newArrayList(reqDTO.getPuids().split(","));

            //统计操作数据
            Map<String,Object> map = new HashMap<>();

            //查询PBOM表数据 保存历史记录
            HzChangeDataDetailQuery query  = new HzChangeDataDetailQuery();
            query.setProjectId(reqDTO.getProjectId());
            query.setPuids(puids);
            query.setTableName(ChangeTableNameEnum.HZ_MATERIEL.getTableName());
            List<HzMaterielRecord> records = hzMaterielDAO.getMaterialRecordsByPuids(query);
            List<HzMaterielRecord> afterRecords = new ArrayList<>();
            if(ListUtil.isNotEmpty(records)){
                records.forEach(record -> {
                    HzMaterielRecord manageRecord = HzMaterielFactory.hzMaterielRecordToMaterielRecord(record);
                    manageRecord.setOrderId(orderId);
                    afterRecords.add(manageRecord);
                });
                map.put("materielAfter",afterRecords);
            }

            //修改发起流程后状态值
            List<HzMaterielRecord> bomLineRecords = new ArrayList<>();
            for(HzMaterielRecord record:records){
                HzMaterielRecord lineRecord = HzMaterielFactory.hzMaterielRecordToMaterielRecord(record);
                if(Integer.valueOf(2).equals(record.getpValidFlag())){//草稿状态---->审核状态
                    lineRecord.setpValidFlag(5);
                }else if(Integer.valueOf(4).equals(record.getpValidFlag())){// 删除状态----->审核状态
                    lineRecord.setpValidFlag(6);
                }
//                lineRecord.setTableName(ChangeTableNameEnum.HZ_PBOM.getTableName());
                bomLineRecords.add(lineRecord);
            }


            map.put("materielBefore",bomLineRecords);
            //保存以上获取信息
            //变更数据
            List<HzChangeDataRecord> dataRecords = new ArrayList<>();
            puids.forEach(s -> {
                HzChangeDataRecord record = new HzChangeDataRecord();
                record.setApplicantId(applicantId);
                record.setOrderId(reqDTO.getOrderId());
                record.setPuid(s);
                record.setTableName(tableName);
                dataRecords.add(record);
            });

            map.put("changeData",dataRecords);
            //申请人
            HzApplicantChangeRecord applicantChangeRecord = new HzApplicantChangeRecord();
            applicantChangeRecord.setApplicantId(applicantId);
            applicantChangeRecord.setOrderId(reqDTO.getOrderId());
            applicantChangeRecord.setTableName(tableName);

            map.put("applicant",applicantChangeRecord);
            //审核人
            HzAuditorChangeRecord auditorChangeRecord = new HzAuditorChangeRecord();
            auditorChangeRecord.setAuditorId(auditorId);
            auditorChangeRecord.setOrderId(reqDTO.getOrderId());
            auditorChangeRecord.setTableName(tableName);

            map.put("auditor",auditorChangeRecord);


            //启动线程进行插入操作
            List<ExecutorServices> services = new ArrayList<>();
            for(Map.Entry<String,Object> entry:map.entrySet()){
                ExecutorServices executorServices = new ExecutorServices(map.size()) {
                    @Override
                    public void action() {
                        switch (entry.getKey()){
                            case "materielAfter":
                                hzMaterielDAO.insertList((List<HzMaterielRecord>) entry.getValue(),tableName);
                                break;
                            case "materielBefore":
                                hzMaterielDAO.updateList((List<HzMaterielRecord>) entry.getValue());
                                break;
                            case "changeData":
                                hzChangeDataRecordDAO.insertList((List<HzChangeDataRecord>) entry.getValue());
                                break;
                            case "applicant":
                                hzApplicantChangeDAO.insert((HzApplicantChangeRecord) entry.getValue());
                                break;
                            case "auditor" :
                                hzAuditorChangeDAO.insert((HzAuditorChangeRecord) entry.getValue());
                                break;
                            default:break;
                        }
                    }
                };
                services.add(executorServices);
            }

            if(ListUtil.isNotEmpty(services)){
                for(ExecutorServices s:services){
                    s.execute();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getSuccessResult();
    }
}
