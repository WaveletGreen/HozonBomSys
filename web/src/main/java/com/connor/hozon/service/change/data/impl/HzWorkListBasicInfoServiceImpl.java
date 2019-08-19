/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.data.impl;

import com.connor.hozon.bom.resources.domain.dto.response.HzWorkListBasicInfoRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzWorkListBasicInfoQuery;
import com.connor.hozon.bom.resources.mybatis.wokeList.HzWorkListDAO;
import com.connor.hozon.service.change.data.HzWorkListBasicInfoService;
import com.connor.hozon.bom.resources.util.DateUtil;
import cn.net.connor.hozon.common.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.workList.HzWorkListRecord;

import java.util.ArrayList;
import java.util.List;

@Service
public class HzWorkListBasicInfoServiceImpl implements HzWorkListBasicInfoService {
    @Autowired
    private HzWorkListDAO hzWorkListDAO;

    @Override
    public HzWorkListBasicInfoRespDTO findHzWorkListBasicInfo(HzWorkListBasicInfoQuery query) {
        HzWorkListBasicInfoRespDTO respDTO = new HzWorkListBasicInfoRespDTO();
        try {
            List<HzWorkListRecord> hzWorkListBasicInfoList = hzWorkListDAO.findHzWorkListBasicInfoList(query);
            if(ListUtils.isNotEmpty(hzWorkListBasicInfoList)){
                HzWorkListRecord hzWorkListRecord = hzWorkListBasicInfoList.get(0);
                respDTO.setId(hzWorkListRecord.getId());
                respDTO.setChangeNum(hzWorkListRecord.getChangeNum());
                respDTO.setLauncher(hzWorkListRecord.getLauncher());
                respDTO.setLauncherId(hzWorkListRecord.getLauncherId());
                respDTO.setLauncherDep(hzWorkListRecord.getLauncherDep());
                respDTO.setLauncherContact(hzWorkListRecord.getLauncherContact());
                respDTO.setAuditer(hzWorkListRecord.getAuditer());
                respDTO.setAuditerId(hzWorkListRecord.getAuditerId());
                respDTO.setLaunchTime(DateUtil.formatTimestampDate(hzWorkListRecord.getLaunchTime()));
                respDTO.setUpdateTime(DateUtil.formatTimestampDate(hzWorkListRecord.getUpdateTime()));
                respDTO.setStatus(hzWorkListRecord.getStatus());
                respDTO.setChangeType(hzWorkListRecord.getChangeType());
                respDTO.setIsRelevanceChange(hzWorkListRecord.getIsRelevanceChange());
                respDTO.setMarketType(hzWorkListRecord.getMarketType());
                respDTO.setProjectStage(hzWorkListRecord.getProjectStage());
                respDTO.setNote(hzWorkListRecord.getNote());
                respDTO.setProjectId(hzWorkListRecord.getProjectId());
            }
            return respDTO;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<HzWorkListBasicInfoRespDTO> findHzWorkList(HzWorkListBasicInfoQuery query) {
        List<HzWorkListBasicInfoRespDTO> ewoBasicInfoList = new ArrayList<>();
        try{
            List<HzWorkListRecord> infos = null;
            if(query.getFlag()==1){
                infos = hzWorkListDAO.findHzWorkListBasicInfoList1(query);
            }else if(query.getFlag()==2){
                infos = hzWorkListDAO.findHzWorkListBasicInfoList2(query);
            }else if(query.getFlag()==3){
                infos = hzWorkListDAO.findHzWorkListBasicInfoList3(query);
            }

            if(ListUtils.isNotEmpty(infos)){
                infos.forEach(hzWorkListBasicInfo -> {
                    HzWorkListBasicInfoRespDTO respDTO = new HzWorkListBasicInfoRespDTO();
                    respDTO.setProjectId(hzWorkListBasicInfo.getProjectId());
                    respDTO.setChangeNum(hzWorkListBasicInfo.getChangeNum());
                    respDTO.setId(hzWorkListBasicInfo.getId());
                    respDTO.setLauncher(hzWorkListBasicInfo.getLauncher());
                    respDTO.setLauncherDep(hzWorkListBasicInfo.getLauncherDep());//部门
                    respDTO.setChangeType(hzWorkListBasicInfo.getChangeType());//变更类型
                    respDTO.setLaunchTime(DateUtil.formatTimestampDate(hzWorkListBasicInfo.getLaunchTime()));
                    //respDTO.setLaunchTime(hzWorkListBasicInfo.getLaunchTime());
                    ewoBasicInfoList.add(respDTO);
                });

                return ewoBasicInfoList;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
