package com.connor.hozon.bom.resources.service.change.impl;

import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.InitiatingProcessReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEWOChangeFormRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOBasicInfoDAO;
import com.connor.hozon.bom.resources.query.HzEWOChangeRecordQuery;
import com.connor.hozon.bom.resources.query.HzEbomTreeQuery;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.service.change.HzEWOService;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.sys.dao.OrgGroupDao;
import com.connor.hozon.bom.sys.entity.OrgGroup;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.change.HzEWOBasicInfo;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/8/13
 * @Description:
 */

@Service("HzEWOService")
public class HzEWOServiceImpl implements HzEWOService {

    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    private HzEWOBasicInfoDAO hzEWOBasicInfoDAO;

    @Autowired
    private OrgGroupDao orgGroupDao;
    @Override
    public OperateResultMessageRespDTO initiatingProcessForEwoChange(InitiatingProcessReqDTO reqDTO) {
        try{
            boolean b = PrivilegeUtil.writePrivilege();//判断权限
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            String puids = reqDTO.getPuids();
            User user = UserInfo.getUser();
            String createYM = DateUtil.getTodayTextYM();
            Date createTime = new Date();
            String lastFourIndex = hzEWOBasicInfoDAO.getMaxEWONoLastFourIndexInThisMonth(reqDTO.getProjectId(),createYM);
            if(lastFourIndex == null){
                lastFourIndex = "0001";
            }else {
                lastFourIndex =getEwoNoLastFourIndex(lastFourIndex);
            }
            String ewoNo = createYM+lastFourIndex;
            HzEWOBasicInfo hzEWOBasicInfo = new HzEWOBasicInfo();
            hzEWOBasicInfo.setFormCreateTime(createTime);
            OrgGroup orgGroup  = orgGroupDao.queryOrgGroupById(user.getGroupId());
            if(orgGroup != null && orgGroup.getName() != null){
                hzEWOBasicInfo.setDept(orgGroup.getName());
            }else {
                hzEWOBasicInfo.setDept("未知");
            }
            hzEWOBasicInfo.setEwoNo(ewoNo);
            hzEWOBasicInfo.setOriginatorId(Long.valueOf(user.getId()));
            hzEWOBasicInfo.setOriginator(user.getUsername());
            hzEWOBasicInfo.setProjectId(reqDTO.getProjectId());
            hzEWOBasicInfoDAO.insert(hzEWOBasicInfo);
            String [] ids = puids.split(",");
            //循环遍历 进行查找 如果为删除状态 需要找出所有子 将他们的状态值进行修改
            Set<HzBomLineRecord> srcRecords = new HashSet<>();//原始数据
            Set<HzBomLineRecord> beforeRecords = new HashSet<>();//变更前记录数据
            Set<HzBomLineRecord> afterRecords = new HashSet<>();//变更后记录数据

            for(String id :ids){
                Map<String,Object> bomLineMap = new HashMap<>();
                bomLineMap.put("puid",id);
                bomLineMap.put("projectId",reqDTO.getProjectId());
                HzBomLineRecord bomLineRecord = hzBomLineRecordDao.findBomLineByPuid(bomLineMap);
                if(bomLineRecord != null){
                    HzBomLineRecord srcRecord = new HzBomLineRecord();
                    HzBomLineRecord beforeRecord = new HzBomLineRecord();
                    HzBomLineRecord afterRecord = new HzBomLineRecord();


                    srcRecord.setEwoNo(ewoNo);
                    srcRecord.setTableName("HZ_BOM_LINE_RECORD");
                    srcRecord.setPuid(bomLineRecord.getPuid());

                    beforeRecord.setEwoNo(ewoNo);
                    beforeRecord.setTableName("HZ_EBOM_REOCRD_BEFORE_CHANGE");
                    beforeRecord.setPuid(bomLineRecord.getPuid());
                    beforeRecords.add(beforeRecord);

                    afterRecord.setEwoNo(ewoNo);
                    afterRecord.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                    afterRecord.setPuid(bomLineRecord.getPuid());
                    afterRecords.add(afterRecord);

                    if(bomLineRecord.getStatus().equals(2)){//草稿状态
                        srcRecord.setStatus(5);
                        srcRecords.add(srcRecord);
                    }else if(bomLineRecord.getStatus().equals(4)){//删除状态 需要根据父获取全部子
                        HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
                        hzEbomTreeQuery.setPuid(bomLineRecord.getPuid());
                        hzEbomTreeQuery.setProjectId(reqDTO.getProjectId());
                        List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);
                        if(ListUtil.isNotEmpty(records)){
                            records.forEach(record -> {
                                HzBomLineRecord srcBomLineRecord = new HzBomLineRecord();
                                srcBomLineRecord.setPuid(record.getPuid());
                                srcBomLineRecord.setEwoNo(ewoNo);
                                srcBomLineRecord.setStatus(6);
                                srcBomLineRecord.setTableName("HZ_BOM_LINE_RECORD");
                                srcRecords.add(srcBomLineRecord);

                                HzBomLineRecord afterBomLineRecord = new HzBomLineRecord();
                                afterBomLineRecord.setPuid(record.getPuid());
                                afterBomLineRecord.setEwoNo(ewoNo);
                                afterBomLineRecord.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                                afterRecords.add(afterBomLineRecord);

                            });
                        }
                    }else {
                        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
                        respDTO.setErrMsg("只能对草稿状态或者删除状态的数据发起流程！");
                        respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                        return respDTO;
                    }
                }else {
                    OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
                    respDTO.setErrMsg("数据产生了一个错误！");
                    respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    return respDTO;
                }
            }

            List<HzBomLineRecord> srcList = new ArrayList<>(srcRecords);//原始数据
            List<HzBomLineRecord> beforeList = new ArrayList<>(beforeRecords);//变更前
            List<HzBomLineRecord> afterList = new ArrayList<>(afterRecords);//变更后

            hzBomLineRecordDao.updateBatch(srcList);
            hzBomLineRecordDao.updateBatch(beforeList);
            hzBomLineRecordDao.updateBatch(afterList);
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getSuccessResult();
    }

    @Override
    public List<HzEWOChangeFormRespDTO> getEWOChangeFormRecord(HzEWOChangeRecordQuery query) {
        try {
            List<HzEWOChangeFormRespDTO> changeFormRespDTOList = new ArrayList<>();
            Map<String,Object> map = new HashMap<>();
            map.put("ewoNo",query.getEwoNo());
            map.put("projectId",query.getProjectId());
            List<HzBomLineRecord> changeRecords = hzBomLineRecordDao.findBomListForChange(map);
            if(ListUtil.isNotEmpty(changeRecords)){
                for(HzBomLineRecord hzBomLineRecord :changeRecords){
                    HzEWOChangeFormRespDTO respDTO = new HzEWOChangeFormRespDTO();
                    respDTO.setPuid(hzBomLineRecord.getPuid());
                    respDTO.setLineId(hzBomLineRecord.getLineID());
                    Map<String,Object> beforeMap = new HashMap<>();
                    beforeMap.put("projectId",query.getProjectId());
                    beforeMap.put("puid",hzBomLineRecord.getPuid());
                    beforeMap.put("tableName","HZ_EBOM_REOCRD_BEFORE_CHANGE");
                    beforeMap.put("ewoNo",query.getEwoNo());

                    Map<String,Object> afterMap = new HashMap<>();
                    afterMap.put("projectId",query.getProjectId());
                    afterMap.put("puid",hzBomLineRecord.getPuid());
                    afterMap.put("tableName","HZ_EBOM_REOCRD_AFTER_CHANGE");
                    afterMap.put("ewoNo",query.getEwoNo());
                    HzBomLineRecord beforeRecord = hzBomLineRecordDao.findBomLineByPuid(beforeMap);
                    HzBomLineRecord afterRecord = hzBomLineRecordDao.findBomLineByPuid(afterMap);
                    if(beforeRecord !=null){
                        respDTO.setBefore(beforeRecord);
                    }
                    if(afterRecord != null){
                        respDTO.setAfter(afterRecord);
                    }
                    changeFormRespDTOList.add(respDTO);
                }
            }
            return changeFormRespDTOList;
        }catch (Exception e){
            return new ArrayList<>();
        }

    }

    /**
     * 生成EWO编号的后四位
     * @param str
     * @return
     */
    private String getEwoNoLastFourIndex(String str){
        String lastFourIndex = "";
        int i = Integer.valueOf(str)+1;
        int length = (i+"").length();
        if(length<4){
            StringBuffer stringBuffer = new StringBuffer();
            for(int j = 0;j<4-length;j++){
                stringBuffer.append("0");
            }
            stringBuffer.append(String.valueOf(i));
            lastFourIndex = stringBuffer.toString();
        }else {
            lastFourIndex = String.valueOf(i);
        }
        return lastFourIndex;
    }



}
