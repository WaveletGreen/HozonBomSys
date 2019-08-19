/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.work.impl;

import com.connor.hozon.service.bom.work.HzWorkService;
import com.connor.hozon.bom.resources.domain.dto.request.AddWorkCenterReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateWorkCenterReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzWorkCenterRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzWorkByPageQuery;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkCenterDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.net.connor.hozon.dao.pojo.main.HzFactory;
import cn.net.connor.hozon.dao.pojo.depository.work.HzWorkCenter;

import java.util.*;


/**
 * \* User: xulf
 * \* Date: 2018/7/2
 * \* Time: 13:28
 * \
 */
@Service("hzWorkService")
public class HzWorkServiceImpl implements HzWorkService {
    @Autowired
    private HzWorkCenterDAO hzWorkCenterDAO;
    @Autowired
    private HzFactoryDAO hzFactoryDAO;

    /**
     * 分页获取数据
     * @param query
     * @return
     */
    @Override
    public Page<HzWorkCenterRespDTO> findHzWorkPage(HzWorkByPageQuery query) {
        try {
            Page<HzWorkCenter> centerPage = hzWorkCenterDAO.findWorkCenterForPage(query);
            int num = (centerPage.getPageNumber()-1)*centerPage.getPageSize();
            if(centerPage == null || centerPage.getResult() == null){
                return  new Page<>(centerPage.getPageNumber(),centerPage.getPageSize(),0);
            }
//            Map<String,Object> map = new HashMap<>();
//            map.put("projectId",query.getProjectId());
            List<HzWorkCenter> list = centerPage.getResult();
            List<HzWorkCenterRespDTO> respDTOList = new ArrayList<>();
            for (HzWorkCenter center:list){
//                HzFactory hzFactory = hzFactoryDAO.findFactory(center.getpFactoryPuid(),"");
                HzWorkCenterRespDTO respDTO = new HzWorkCenterRespDTO();

                respDTO.setNo(++num);
                respDTO.setPuid(center.getPuid());
                respDTO.setFactoryCode("1001");
//                respDTO.setFactoryId(hzFactory.getId());
                if(StringUtil.isEmpty(center.getpFactoryCode())){
                    respDTO.setFactoryCode("1001");
                }else {
                    respDTO.setFactoryCode(center.getpFactoryCode());
                }
                respDTO.setFactoryId(center.getpFactoryId());
                respDTO.setpWorkCode(center.getpWorkCode());
                respDTO.setpWorkDesc(center.getpWorkDesc());
                respDTO.setpWorkType(center.getpWorkType());
                respDTO.setpPurpose(center.getpPurpose());
                respDTO.setpStandardCode(center.getpStandardCode());
                respDTO.setpControlCode(center.getpControlCode());
                respDTO.setpDirectLabor(center.getpDirectLabor());
                respDTO.setpIndirectLabor(center.getpIndirectLabor());
                respDTO.setpMachineLabor(center.getpMachineLabor());
                respDTO.setpBurn(center.getpBurn());
                respDTO.setpMachineMaterial(center.getpMachineMaterial());
                respDTO.setpOtherCost(center.getpOtherCost());
                respDTO.setpProcessExression(center.getpProcessExression());
                respDTO.setpAbilityType(center.getpAbilityType());
                respDTO.setpStartTime(center.getpStartTime());
                respDTO.setpEndTime(center.getpEndTime());
                respDTO.setpRestTime(center.getpRestTime());
                respDTO.setpAbilityCount(center.getpAbilityCount());
                respDTO.setpDispatchExpression(center.getpDispatchExpression());
                respDTO.setpCostCenter(center.getpCostCenter());
                respDTO.setpTaskType1(center.getpTaskType1());
                respDTO.setpTaskType2(center.getpTaskType2());
                respDTO.setpTaskType3(center.getpTaskType3());
                respDTO.setpTaskType4(center.getpTaskType4());
                respDTO.setpTaskType5(center.getpTaskType5());
                respDTO.setpTaskType6(center.getpTaskType6());
                respDTO.setpExperssion1(center.getpExperssion1());
                respDTO.setpExperssion2(center.getpExperssion2());
                respDTO.setpExperssion3(center.getpExperssion3());
                respDTO.setpExperssion4(center.getpExperssion4());
                respDTO.setpExperssion5(center.getpExperssion5());
                respDTO.setpExperssion6(center.getpExperssion6());
                respDTOList.add(respDTO);
            }
            return new Page<>(centerPage.getPageNumber(),centerPage.getPageSize(),centerPage.getTotalCount(),respDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 插入一条数据
     * @param reqDTO
     * @return
     */
    @Override
    public WriteResultRespDTO insertHzWorkRecord(AddWorkCenterReqDTO reqDTO) {
        try {
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return WriteResultRespDTO.getFailPrivilege();
            }
            HzWorkCenter hzWorkCenter = new HzWorkCenter();
            HzFactory hzFactory = hzFactoryDAO.findFactory("",reqDTO.getFactoryCode());

            if(hzFactory == null){
                hzFactory = new HzFactory();
                String puid = UUID.randomUUID().toString();
                hzFactory.setPuid(puid);
                hzFactory.setpFactoryCode(reqDTO.getFactoryCode());
                int i =hzFactoryDAO.insert(hzFactory);
                if(i>0){
                    hzWorkCenter.setpFactoryPuid(puid);
                }else{
                    return WriteResultRespDTO.getFailResult();
                }
            }else{
               hzWorkCenter.setpFactoryPuid(hzFactory.getPuid());
            }
            hzWorkCenter.setPuid(UUID.randomUUID().toString());
            hzWorkCenter.setpFactoryPuid(hzFactory.getPuid());
            hzWorkCenter.setpWorkCode(reqDTO.getpWorkCode());
            hzWorkCenter.setpWorkDesc(reqDTO.getpWorkDesc());
            hzWorkCenter.setpWorkType(reqDTO.getpWorkType());
            hzWorkCenter.setpPurpose(reqDTO.getpPurpose());
            hzWorkCenter.setpStandardCode(reqDTO.getpStandardCode());
            hzWorkCenter.setpControlCode(reqDTO.getpControlCode());
            hzWorkCenter.setpDirectLabor(reqDTO.getpDirectLabor());
            hzWorkCenter.setpIndirectLabor(reqDTO.getpIndirectLabor());
            hzWorkCenter.setpMachineLabor(reqDTO.getpMachineLabor());
            hzWorkCenter.setpBurn(reqDTO.getpBurn());
            hzWorkCenter.setpMachineMaterial(reqDTO.getpMachineMaterial());
            hzWorkCenter.setpOtherCost(reqDTO.getpOtherCost());
            hzWorkCenter.setpProcessExression(reqDTO.getpProcessExression());
            hzWorkCenter.setpAbilityType(reqDTO.getpAbilityType());
            hzWorkCenter.setpStartTime(reqDTO.getpStartTime());
            hzWorkCenter.setpEndTime(reqDTO.getpEndTime());
            hzWorkCenter.setpRestTime(reqDTO.getpRestTime());
            hzWorkCenter.setpAbilityCount(reqDTO.getpAbilityCount());
            hzWorkCenter.setpDispatchExpression(reqDTO.getpDispatchExpression());
            hzWorkCenter.setpCostCenter(reqDTO.getpCostCenter());
            hzWorkCenter.setpTaskType1(reqDTO.getpTaskType1());
            hzWorkCenter.setpTaskType2(reqDTO.getpTaskType2());
            hzWorkCenter.setpTaskType3(reqDTO.getpTaskType3());
            hzWorkCenter.setpTaskType4(reqDTO.getpTaskType4());
            hzWorkCenter.setpTaskType5(reqDTO.getpTaskType5());
            hzWorkCenter.setpTaskType6(reqDTO.getpTaskType6());
            hzWorkCenter.setpExperssion1(reqDTO.getpExperssion1());
            hzWorkCenter.setpExperssion2(reqDTO.getpExperssion2());
            hzWorkCenter.setpExperssion3(reqDTO.getpExperssion3());
            hzWorkCenter.setpExperssion4(reqDTO.getpExperssion4());
            hzWorkCenter.setpExperssion5(reqDTO.getpExperssion5());
            hzWorkCenter.setpExperssion6(reqDTO.getpExperssion6());
//            hzWorkCenter.setProjectId(reqDTO.getProjectId());
            int i = hzWorkCenterDAO.insert(hzWorkCenter);
            if (i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getFailResult();
    }

    /**
     * 根据id查询一条数据
     * @param puid
     * @return
     */
    @Override
    public HzWorkCenterRespDTO findHzWorkByPuid(String puid) {
        try {
            HzWorkCenter center = hzWorkCenterDAO.findWorkCenterById(puid);
            if (center!=null){
                HzFactory hzFactory = hzFactoryDAO.findFactory(center.getpFactoryPuid(),"");
                HzWorkCenterRespDTO respDTO = new HzWorkCenterRespDTO();
                respDTO.setPuid(center.getPuid());
                respDTO.setFactoryCode(hzFactory.getpFactoryCode());
                respDTO.setFactoryId(hzFactory.getPuid());
                respDTO.setpWorkCode(center.getpWorkCode());
                respDTO.setpWorkDesc(center.getpWorkDesc());
                respDTO.setpWorkType(center.getpWorkType());
                respDTO.setpPurpose(center.getpPurpose());
                respDTO.setpStandardCode(center.getpStandardCode());
                respDTO.setpControlCode(center.getpControlCode());
                respDTO.setpDirectLabor(center.getpDirectLabor());
                respDTO.setpIndirectLabor(center.getpIndirectLabor());
                respDTO.setpMachineLabor(center.getpMachineLabor());
                respDTO.setpBurn(center.getpBurn());
                respDTO.setpMachineMaterial(center.getpMachineMaterial());
                respDTO.setpOtherCost(center.getpOtherCost());
                respDTO.setpProcessExression(center.getpProcessExression());
                respDTO.setpAbilityType(center.getpAbilityType());
                respDTO.setpStartTime(center.getpStartTime());
                respDTO.setpEndTime(center.getpEndTime());
                respDTO.setpRestTime(center.getpRestTime());
                respDTO.setpAbilityCount(center.getpAbilityCount());
                respDTO.setpDispatchExpression(center.getpDispatchExpression());
                respDTO.setpCostCenter(center.getpCostCenter());
                respDTO.setpTaskType1(center.getpTaskType1());
                respDTO.setpTaskType2(center.getpTaskType2());
                respDTO.setpTaskType3(center.getpTaskType3());
                respDTO.setpTaskType4(center.getpTaskType4());
                respDTO.setpTaskType5(center.getpTaskType5());
                respDTO.setpTaskType6(center.getpTaskType6());
                respDTO.setpExperssion1(center.getpExperssion1());
                respDTO.setpExperssion2(center.getpExperssion2());
                respDTO.setpExperssion3(center.getpExperssion3());
                respDTO.setpExperssion4(center.getpExperssion4());
                respDTO.setpExperssion5(center.getpExperssion5());
                respDTO.setpExperssion6(center.getpExperssion6());
                return respDTO;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 编辑一条数据
     * @param reqDTO
     * @return
     */
    @Override
    public WriteResultRespDTO updateHzWorkRecord(UpdateWorkCenterReqDTO reqDTO) {
        try {
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return WriteResultRespDTO.getFailPrivilege();
            }
            HzWorkCenter center = new HzWorkCenter();
            HzFactory hzFactory = hzFactoryDAO.findFactory("",reqDTO.getFactoryCode());
            if (hzFactory==null){
                hzFactory = new HzFactory();
                String puid = UUID.randomUUID().toString();
                hzFactory.setPuid(puid);
                hzFactory.setpFactoryCode(reqDTO.getFactoryCode());
                int i =hzFactoryDAO.insert(hzFactory);
                if (i>0){
                    center.setpFactoryPuid(puid);
                }
                else {
                    return WriteResultRespDTO.getFailResult();
                }
            }
            else {
                center.setpFactoryPuid(hzFactory.getPuid());
            }
            center.setPuid(reqDTO.getPuid());
            center.setpFactoryPuid(hzFactory.getPuid());
            center.setpWorkCode(reqDTO.getpWorkCode());
            center.setpWorkDesc(reqDTO.getpWorkDesc());
            center.setpWorkType(reqDTO.getpWorkType());
            center.setpPurpose(reqDTO.getpPurpose());
            center.setpStandardCode(reqDTO.getpStandardCode());
            center.setpControlCode(reqDTO.getpControlCode());
            center.setpDirectLabor(reqDTO.getpDirectLabor());
            center.setpIndirectLabor(reqDTO.getpIndirectLabor());
            center.setpMachineLabor(reqDTO.getpMachineLabor());
            center.setpBurn(reqDTO.getpBurn());
            center.setpMachineMaterial(reqDTO.getpMachineMaterial());
            center.setpOtherCost(reqDTO.getpOtherCost());
            center.setpProcessExression(reqDTO.getpProcessExression());
            center.setpAbilityType(reqDTO.getpAbilityType());
            center.setpStartTime(reqDTO.getpStartTime());
            center.setpEndTime(reqDTO.getpEndTime());
            center.setpRestTime(reqDTO.getpRestTime());
            center.setpAbilityCount(reqDTO.getpAbilityCount());
            center.setpDispatchExpression(reqDTO.getpDispatchExpression());
            center.setpCostCenter(reqDTO.getpCostCenter());
            center.setpTaskType1(reqDTO.getpTaskType1());
            center.setpTaskType2(reqDTO.getpTaskType2());
            center.setpTaskType3(reqDTO.getpTaskType3());
            center.setpTaskType4(reqDTO.getpTaskType4());
            center.setpTaskType5(reqDTO.getpTaskType5());
            center.setpTaskType6(reqDTO.getpTaskType6());
            center.setpExperssion1(reqDTO.getpExperssion1());
            center.setpExperssion2(reqDTO.getpExperssion2());
            center.setpExperssion3(reqDTO.getpExperssion3());
            center.setpExperssion4(reqDTO.getpExperssion4());
            center.setpExperssion5(reqDTO.getpExperssion5());
            center.setpExperssion6(reqDTO.getpExperssion6());
            int i = hzWorkCenterDAO.update(center);
            if (i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getFailResult();
    }

    /**
     * 删除一条数据
     * @param puid
     * @return
     */
    @Override
    public WriteResultRespDTO deleteHzWorkRecord(String puid) {
        try {
            if (!PrivilegeUtil.writePrivilege()) {
               return WriteResultRespDTO.getFailResult();
            }
            int i = hzWorkCenterDAO.delete(puid);
            if (i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getFailResult();
    }

}
