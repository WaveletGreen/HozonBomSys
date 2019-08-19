/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.mwo.impl;

import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.InitiatingProcessReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOBasicInfoDAO;
import com.connor.hozon.service.change.mwo.HzMWOService;
import com.connor.hozon.bom.resources.util.DateUtil;
import cn.net.connor.hozon.dao.dao.sys.OrgGroupDao;
import cn.net.connor.hozon.dao.pojo.sys.OrgGroup;
import cn.net.connor.hozon.dao.pojo.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzBomLineRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzEWOBasicInfo;

import java.util.*;

@Service("hzMWOService")
public class HzMWOServiceImpl implements HzMWOService {
    @Autowired
    private HzEWOBasicInfoDAO hzEWOBasicInfoDAO;

    @Autowired
    private OrgGroupDao orgGroupDao;

    @Override
    public WriteResultRespDTO initiatingProcessForMwoChange(InitiatingProcessReqDTO reqDTO) {
        try{
            String puids = reqDTO.getPuids();
            User user = UserInfo.getUser();
            String createYM = DateUtil.getTodayTextY();
            Date createTime = new Date();
            String lastFourIndex = hzEWOBasicInfoDAO.getMaxEWONoLastFourIndexInThisMonth(reqDTO.getProjectId(),createYM);
            if(lastFourIndex == null){
                lastFourIndex = "0001";
            }else {
                lastFourIndex = getEwoNoLastFourIndex(lastFourIndex);
            }
            String ewoNo = "EC"+createYM+lastFourIndex;
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
                //HzBomLineRecord bomLineRecord = hzBomLineRecordDao.findBomLineByPuid(bomLineMap);
//                if(bomLineRecord != null){
//                    HzBomLineRecord srcRecord = new HzBomLineRecord();
//                    HzBomLineRecord beforeRecord = new HzBomLineRecord();
//                    HzBomLineRecord afterRecord = new HzBomLineRecord();
//
//                    srcRecord.setEwoNo(ewoNo);
//                    srcRecord.setTableName("HZ_BOM_LINE_RECORD");
//                    srcRecord.setId(bomLineRecord.getId());
//
//                    beforeRecord.setEwoNo(ewoNo);
//                    beforeRecord.setTableName("HZ_EBOM_REOCRD_BEFORE_CHANGE");
//                    beforeRecord.setId(bomLineRecord.getId());
//                    beforeRecords.add(beforeRecord);
//
//                    afterRecord.setEwoNo(ewoNo);
//                    afterRecord.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
//                    afterRecord.setId(bomLineRecord.getId());
//                    afterRecords.add(afterRecord);
//
//                    if(bomLineRecord.getStatus().equals(2)){//草稿状态
//                        srcRecord.setStatus(5);
//                        srcRecords.add(srcRecord);
//                    }else if(bomLineRecord.getStatus().equals(4)){//删除状态 需要根据父获取全部子
//                        HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
//                        hzEbomTreeQuery.setId(bomLineRecord.getId());
//                        hzEbomTreeQuery.setProjectId(reqDTO.getProjectId());
//                        List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);
//                        if(ListUtils.isNotEmpty(records)){
//                            records.forEach(record -> {
//                                HzBomLineRecord srcBomLineRecord = new HzBomLineRecord();
//                                srcBomLineRecord.setId(record.getId());
//                                srcBomLineRecord.setEwoNo(ewoNo);
//                                srcBomLineRecord.setStatus(6);
//                                srcBomLineRecord.setTableName("HZ_BOM_LINE_RECORD");
//                                srcRecords.add(srcBomLineRecord);
//
//                                HzBomLineRecord afterBomLineRecord = new HzBomLineRecord();
//                                afterBomLineRecord.setId(record.getId());
//                                afterBomLineRecord.setEwoNo(ewoNo);
//                                afterBomLineRecord.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
//                                afterRecords.add(afterBomLineRecord);
//
//                            });
//                        }
//                    }else {
//                        WriteResultRespDTO respDTO = new WriteResultRespDTO();
//                        respDTO.setErrMsg("只能对草稿状态或者删除状态的数据发起流程！");
//                        respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
//                        return respDTO;
//                    }
//                }else {
//                    WriteResultRespDTO respDTO = new WriteResultRespDTO();
//                    respDTO.setErrMsg("数据产生了一个错误！");
//                    respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
//                    return respDTO;
//                }
            }

            List<HzBomLineRecord> srcList = new ArrayList<>(srcRecords);//原始数据
            List<HzBomLineRecord> beforeList = new ArrayList<>(beforeRecords);//变更前
            List<HzBomLineRecord> afterList = new ArrayList<>(afterRecords);//变更后

//            hzBomLineRecordDao.updateBatch(srcList);
//            hzBomLineRecordDao.updateBatch(beforeList);
//            hzBomLineRecordDao.updateBatch(afterList);
        }catch (Exception e){
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getSuccessResult();
        //return null;
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
