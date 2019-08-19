/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.resources.domain.dto.response.HzWorkProcessRespDTO;
import cn.net.connor.hozon.dao.pojo.depository.work.HzWorkProcedure;
import cn.net.connor.hozon.dao.pojo.depository.work.HzWorkProcess;

/**
 * @Author: haozt
 * @Date: 2018/11/19
 * @Description:
 */
public class HzWorkProcedureFactory {

    public static HzWorkProcedure workProcedureToProcedure(HzWorkProcedure workProcedure){
        HzWorkProcedure procedure = new HzWorkProcedure();
        procedure.setPuid(workProcedure.getPuid());
        procedure.setMaterielId(workProcedure.getMaterielId());
        procedure.setControlCode(workProcedure.getControlCode());
        procedure.setpWorkPuid(workProcedure.getpWorkPuid());
        procedure.setState(workProcedure.getState());
        procedure.setPurpose(workProcedure.getPurpose());
        procedure.setpUpdateName(workProcedure.getpUpdateName());
        procedure.setProjectId(workProcedure.getProjectId());
        procedure.setpProcedureCode(workProcedure.getpProcedureCode());
        procedure.setpProcedureDesc(workProcedure.getpProcedureDesc());
        procedure.setpOtherCost(workProcedure.getpOtherCost());
        procedure.setpMachineMaterialLabor(workProcedure.getpMachineMaterialLabor());
        procedure.setpMachineLabor(workProcedure.getpMachineLabor());
        procedure.setpIndirectLabor(workProcedure.getpIndirectLabor());
        procedure.setpDirectLabor(workProcedure.getpDirectLabor());
        procedure.setpCreateName(workProcedure.getpCreateName());
        procedure.setpCount(workProcedure.getpCount());
        procedure.setpBurn(workProcedure.getpBurn());
        procedure.setpFactoryId(workProcedure.getpFactoryId());
        procedure.setpStatus(workProcedure.getpStatus());
        procedure.setpMaterielCode(workProcedure.getpMaterielCode());
        procedure.setpMaterielDesc(workProcedure.getpMaterielDesc());
        procedure.setOrderId(workProcedure.getOrderId());
        procedure.setEffectTime(workProcedure.getEffectTime());
        procedure.setRevision(workProcedure.getRevision());
        procedure.setTableName(workProcedure.getTableName());
        return procedure;
    }


    public static HzWorkProcessRespDTO workProcedureToRespDTO(HzWorkProcedure hzWorkProcedure){
        HzWorkProcessRespDTO respDTO = new HzWorkProcessRespDTO();
        respDTO.setPuid(hzWorkProcedure.getPuid());
        respDTO.setFactoryCode(hzWorkProcedure.getFactoryCode());
        respDTO.setpBurn(hzWorkProcedure.getpBurn());
        respDTO.setpCount(hzWorkProcedure.getpCount());
        respDTO.setpDirectLabor(hzWorkProcedure.getpDirectLabor());
        respDTO.setpIndirectLabor(hzWorkProcedure.getpIndirectLabor());
        respDTO.setpMachineMaterialLabor(hzWorkProcedure.getpMachineMaterialLabor());
        respDTO.setpMachineLabor(hzWorkProcedure.getpMachineLabor());
        respDTO.setpOtherCost(hzWorkProcedure.getpOtherCost());
        respDTO.setpProcedureCode(hzWorkProcedure.getpProcedureCode());
        respDTO.setpProcedureDesc(hzWorkProcedure.getpProcedureDesc());
        respDTO.setpWorkCode(hzWorkProcedure.getWorkCenterCode());
        respDTO.setpWorkDesc(hzWorkProcedure.getWorkCenterDesc());
        respDTO.setMaterielId(hzWorkProcedure.getMaterielId());
        respDTO.setpMaterielCode(hzWorkProcedure.getpMaterielCode());
        respDTO.setpMaterielDesc(hzWorkProcedure.getpMaterielDesc());
        respDTO.setControlCode(hzWorkProcedure.getControlCode());
        respDTO.setPurpose(hzWorkProcedure.getPurpose());
        respDTO.setState(hzWorkProcedure.getState());
        respDTO.setStatus(hzWorkProcedure.getpStatus());
        return  respDTO;
    }



    public static HzWorkProcessRespDTO workProcessToRespDTO(HzWorkProcess hzWorkProcess){
        HzWorkProcessRespDTO respDTO   = new HzWorkProcessRespDTO();
        respDTO.setFactoryCode(hzWorkProcess.getFactoryCode());
        respDTO.setMaterielId(hzWorkProcess.getPuid());
        respDTO.setpBurn(hzWorkProcess.getpBurn());
        respDTO.setpCount(hzWorkProcess.getpCount());
        respDTO.setpDirectLabor(hzWorkProcess.getpDirectLabor());
        respDTO.setpIndirectLabor(hzWorkProcess.getpIndirectLabor());
        respDTO.setpMachineMaterialLabor(hzWorkProcess.getpMachineMaterialLabor());
        respDTO.setpMachineLabor(hzWorkProcess.getpMachineLabor());
        respDTO.setpOtherCost(hzWorkProcess.getpOtherCost());
        respDTO.setpProcedureCode(hzWorkProcess.getpProcedureCode());
        respDTO.setpProcedureDesc(hzWorkProcess.getpProcedureDesc());
        respDTO.setpWorkCode(hzWorkProcess.getWorkCenterCode());
        respDTO.setpWorkDesc(hzWorkProcess.getWorkCenterDesc());
        respDTO.setPuid(hzWorkProcess.getPuid());
        respDTO.setpMaterielCode(hzWorkProcess.getpMaterielCode());
        respDTO.setpMaterielDesc(hzWorkProcess.getpMaterielDesc());
        respDTO.setControlCode(hzWorkProcess.getControlCode());
        respDTO.setPurpose(hzWorkProcess.getPurpose());
        respDTO.setState(hzWorkProcess.getState());
        respDTO.setpWorkPuid(hzWorkProcess.getpWorkPuid());
        respDTO.setIsSent(hzWorkProcess.getIsSent());
        respDTO.setDataType(hzWorkProcess.getDataType());
        return respDTO;
    }
}
