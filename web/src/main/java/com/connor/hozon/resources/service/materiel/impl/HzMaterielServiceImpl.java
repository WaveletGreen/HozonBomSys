package com.connor.hozon.resources.service.materiel.impl;

import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.resources.domain.constant.BOMTransConstants;
import com.connor.hozon.resources.domain.dto.request.AddDataToChangeOrderReqDTO;
import com.connor.hozon.resources.domain.dto.request.BomBackReqDTO;
import com.connor.hozon.resources.domain.dto.request.EditHzMaterielReqDTO;
import com.connor.hozon.resources.domain.dto.response.HzMaterielRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.model.HzMaterielFactory;
import com.connor.hozon.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.resources.domain.query.HzMaterielByPageQuery;
import com.connor.hozon.resources.domain.query.HzMaterielQuery;
import com.connor.hozon.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.resources.executors.ExecutorServices;
import com.connor.hozon.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.materiel.HzMaterielService;
import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.resources.util.StringUtil;
import cn.net.connor.hozon.dao.pojo.sys.User;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeDataRecord;
import cn.net.connor.hozon.dao.pojo.main.HzFactory;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;

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


    private TransactionTemplate configTransactionTemplate;
    @Autowired
    public void setConfigTransactionTemplate(TransactionTemplate configTransactionTemplate) {
        this.configTransactionTemplate = configTransactionTemplate;
    }


    private int errorCount =0;


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
            if(StringUtils.isNotBlank(editHzMaterielReqDTO.getFactoryCode())){
                HzFactory hzFactory = hzFactoryDAO.findFactory("", editHzMaterielReqDTO.getFactoryCode());
                if(hzFactory == null){
                    record.setpFactoryPuid(hzFactoryDAO.insert(editHzMaterielReqDTO.getFactoryCode()));
                }else{
                    record.setpFactoryPuid(hzFactory.getPuid());
                }
            }
            record.setpCreateName(user.getUsername());
            record.setpVinPerNo(editHzMaterielReqDTO.getpVinPerNo());
            record.setpSpareMaterial(editHzMaterielReqDTO.getpSpareMaterial());
            String loosePartFlag = editHzMaterielReqDTO.getpLoosePartFlag();
            record.setpLoosePartFlag(BOMTransConstants.constantStringToInteger(loosePartFlag));
            record.setpMrpController(editHzMaterielReqDTO.getpMrpController());
            record.setpUpdateName(user.getUsername());
            record.setPuid(editHzMaterielReqDTO.getPuid());
            record.setpBasicUnitMeasure(editHzMaterielReqDTO.getpBasicUnitMeasure());
            record.setpInventedPart(BOMTransConstants.constantStringToInteger(editHzMaterielReqDTO.getpInventedPart()));
            record.setResource(editHzMaterielReqDTO.getResource());
            record.setpColorPart(BOMTransConstants.constantStringToInteger(editHzMaterielReqDTO.getpColorPart()));
            record.setpHeight(editHzMaterielReqDTO.getpHeight());

            record.setpInOutSideFlag(BOMTransConstants.constantStringToInteger(editHzMaterielReqDTO.getpInOutSideFlag()));

            record.setP3cPartFlag(BOMTransConstants.constantStringToInteger(editHzMaterielReqDTO.getP3cPartFlag()));

            record.setpPartImportantDegree(editHzMaterielReqDTO.getpPartImportantDegree());
            record.setpMaterielDesc(editHzMaterielReqDTO.getpMaterielDesc());
            int i = hzMaterielDAO.update(record);
            if(i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getFailResult();
    }

    @Override
    public WriteResultRespDTO deleteHzMateriel(String puids) {
        try{
            if(StringUtils.isBlank(puids)){
                return WriteResultRespDTO.IllgalArgument();
            }
            List<String> list = Lists.newArrayList(puids.split(","));

            List<String> updateList = new ArrayList<>();

            List<HzMaterielRecord> deleteList = new ArrayList<>();

            list.forEach(l->{
                HzMaterielQuery hzMaterielQuery  = new HzMaterielQuery();
                hzMaterielQuery.setPuid(l);
                HzMaterielRecord hzMaterielRecord = hzMaterielDAO.getHzMaterielRecord(hzMaterielQuery);
                if(hzMaterielRecord != null){
                    if(StringUtils.isNotBlank(hzMaterielRecord.getRevision())){
                        updateList.add(l);
                    }else {
                        deleteList.add(hzMaterielRecord);
                    }
                }
            });

            configTransactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus status) {
                    if(ListUtils.isNotEmpty(updateList)){
                        hzMaterielDAO.deleteList(updateList);
                    }
                    if(ListUtils.isNotEmpty(deleteList)){
                        hzMaterielDAO.deleteMaterielList(deleteList,ChangeTableNameEnum.HZ_MATERIEL.getTableName());
                    }
                    return null;
                }
            });
        }catch (Exception e){
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getSuccessResult();
    }

    @Override
    public Page<HzMaterielRespDTO> findHzMaterielForPage(HzMaterielByPageQuery query) {
        //先找出对应的物料类型，添加到物料类型表，将其数据自动带出
        /**
         * 物料类型
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
    @Transactional(rollbackFor = Exception.class)
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
//            Long auditorId = reqDTO.getAuditorId();
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
            if(ListUtils.isNotEmpty(records)){
                //核查参数信息
                this.errorCount = 0;
                String errMsg = checkMaterielParamResult(records);
                if(this.errorCount>0){
                    WriteResultRespDTO respDTO = new WriteResultRespDTO();
                    respDTO.setErrMsg(errMsg);
                    respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    return respDTO;
                }
                //到 after表中查询看是否存在记录
                //存在记录则过滤 不存在记录则插入
                HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
                dataDetailQuery.setProjectId(reqDTO.getProjectId());
                dataDetailQuery.setOrderId(orderId);
                dataDetailQuery.setTableName(ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName());
                List<HzMaterielRecord> recordList = hzMaterielDAO.getMaterielRecordsByOrderId(dataDetailQuery);
                if(ListUtils.isEmpty(recordList)){
                    records.forEach(record -> {
                        HzMaterielRecord manageRecord = HzMaterielFactory.hzMaterielRecordToMaterielRecord(record);
                        manageRecord.setOrderId(orderId);
                        afterRecords.add(manageRecord);
                    });
                }else {
                    for(int i=0;i<records.size();i++){
                        records.get(i).setOrderId(orderId);
                        for(HzMaterielRecord record:recordList){
                            if(records.get(i).equals(record)){
                                records.remove(records.get(i));
                                i--;
                                break;
                            }
                        }
                    }
                    afterRecords.addAll(records);
                }

                map.put("materielAfter",afterRecords);


                //修改发起流程后状态值
                List<HzMaterielRecord> bomLineRecords = new ArrayList<>();
                for(HzMaterielRecord record:records){
                    HzMaterielRecord lineRecord = HzMaterielFactory.hzMaterielRecordToMaterielRecord(record);
                    //审核状态
                    lineRecord.setpValidFlag(5);

//                lineRecord.setTableName(ChangeTableNameEnum.HZ_PBOM.getTableName());
                    bomLineRecords.add(lineRecord);
                }


                map.put("materielBefore",bomLineRecords);
                //保存以上获取信息
                //变更数据
                HzChangeDataRecord record = new HzChangeDataRecord();
                record.setApplicantId(applicantId);
                record.setOrderId(reqDTO.getOrderId());
                record.setTableName(tableName);
                map.put("changeData",record);
                //申请人
//                HzApplicantChangeRecord applicantChangeRecord = new HzApplicantChangeRecord();
//                applicantChangeRecord.setApplicantId(applicantId);
//                applicantChangeRecord.setOrderId(reqDTO.getOrderId());
//                applicantChangeRecord.setTableName(tableName);
//
//                map.put("applicant",applicantChangeRecord);
                //审核人
//                HzAuditorChangeRecord auditorChangeRecord = new HzAuditorChangeRecord();
//                auditorChangeRecord.setAuditorId(auditorId);
//                auditorChangeRecord.setOrderId(reqDTO.getOrderId());
//                auditorChangeRecord.setTableName(tableName);
//
//                map.put("auditor",auditorChangeRecord);


                //启动线程进行插入操作
                for(Map.Entry<String,Object> entry:map.entrySet()){
                     new ExecutorServices(1) {
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
                                    hzChangeDataRecordDAO.insert((HzChangeDataRecord) entry.getValue());
                                    break;
//                                case "applicant":
//                                    hzApplicantChangeDAO.insert((HzApplicantChangeRecord) entry.getValue());
//                                    break;
//                                case "auditor" :
//                                    hzAuditorChangeDAO.insert((HzAuditorChangeRecord) entry.getValue());
//                                    break;
                                default:break;
                            }
                        }
                    }.execute();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getSuccessResult();
    }

    @Override
    public WriteResultRespDTO backBomUtilLastValidState(BomBackReqDTO reqDTO) {
        try{
            List<String> puids = Lists.newArrayList(reqDTO.getPuids().split(","));
            HzChangeDataDetailQuery query = new HzChangeDataDetailQuery();
            query.setProjectId(reqDTO.getProjectId());
            query.setPuids(puids);
            query.setTableName(ChangeTableNameEnum.HZ_MATERIEL.getTableName());
            List<HzMaterielRecord> deleteRecords = new ArrayList<>();
            List<HzMaterielRecord> updateRecords = new ArrayList<>();
            List<HzMaterielRecord> updateList = new ArrayList<>();

            List<HzMaterielRecord> list = hzMaterielDAO.getMaterialRecordsByPuids(query);
            //撤销 1找不存在版本记录的--删除    2找存在记录-直接更新数据为上个版本生效数据
            if(ListUtils.isNotEmpty(list)){
                list.forEach(record -> {
                    if(StringUtils.isBlank(record.getRevision())){
                        deleteRecords.add(record);
                    }else {
                        updateRecords.add(record);
                    }
                });
            }
            if(ListUtils.isNotEmpty(updateRecords)){
                HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
                dataDetailQuery.setRevision(true);
                dataDetailQuery.setProjectId(reqDTO.getProjectId());
                dataDetailQuery.setTableName(ChangeTableNameEnum.HZ_MATERIEL_BEFORE.getTableName());
                dataDetailQuery.setStatus(1);
                updateRecords.forEach(record -> {
                    dataDetailQuery.setRevisionNo(record.getRevision());
                    dataDetailQuery.setPuid(record.getPuid());
                    HzMaterielRecord manageRecord = hzMaterielDAO.getMaterialRecordByPuidAndRevision(dataDetailQuery);
                    if(manageRecord!=null){
                        updateList.add(HzMaterielFactory.hzMaterielRecordToMaterielRecord(manageRecord));
                    }
                });
            }
            if(ListUtils.isNotEmpty(updateList)){
                hzMaterielDAO.updateMaterielList(updateList);
            }
            if(ListUtils.isNotEmpty(deleteRecords)){
                hzMaterielDAO.deleteMaterielList(deleteRecords,ChangeTableNameEnum.HZ_MATERIEL.getTableName());
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }


    /**
     * 物料发起流程需要做参数合法性检验
     * 需要和SAP进行参数传递，SAP规定的必填参数必须填写完整后才允许发起流程
     */
    private String checkMaterielParamResult(List<HzMaterielRecord> materielRecords){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<strong style='color: red'>参数信息填写不完整，不允许发起流程!<br>" +
                "必填参数有:工厂,物料编码,物料中文描述,基本计量单位," +
                "物料类型,采购类型,MRP控制者</strong><br>");
        for(HzMaterielRecord record:materielRecords){
            String materielCode = record.getpMaterielCode();
            String factoryCode = record.getFactoryCode();
            String materielDesc = record.getpMaterielDesc();
            String basicUnit = record.getpBasicUnitMeasure();
            String materielType = record.getpMaterielType();
            String mrpController = record.getpMrpController();
            String buyType = record.getResource();

            if(
                    StringUtils.isBlank(factoryCode)  ||
                    StringUtils.isBlank(materielDesc) ||
                    StringUtils.isBlank(basicUnit)    ||
                    StringUtils.isBlank(materielType) ||
                    StringUtils.isBlank(mrpController)||
                    StringUtils.isBlank(buyType)
                    ){

                stringBuffer.append("<strong style='color:deeppink'>"+materielCode+"</strong>的必填参数填写不完整!<br>");
                this.errorCount++;
            }
        }

        return stringBuffer.toString();
    }


}
