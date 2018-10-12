package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzMaterielRespDTO;
import com.connor.hozon.bom.resources.enumtype.BomResourceEnum;
import com.connor.hozon.bom.resources.util.StringUtil;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.cfg.derivative.HzCfg0ModelFeature;
import sql.pojo.project.HzMaterielRecord;

import java.util.UUID;

/**
 * @Author: haozt
 * @Date: 2018/9/11
 * @Description:
 */
public class HzMaterielFactory {
    public static HzMaterielRecord addHzEbomReqDTOMaterielRecord(AddHzEbomReqDTO reqDTO){
        HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();
        hzMaterielRecord.setpMaterielCode(reqDTO.getLineId());
        hzMaterielRecord.setpMaterielType("A002");
        hzMaterielRecord.setpMaterielDesc(reqDTO.getpBomLinePartName());
        hzMaterielRecord.setpMaterielDescEn(reqDTO.getpBomLinePartEnName());
        hzMaterielRecord.setpBasicUnitMeasure(reqDTO.getpUnit());
        hzMaterielRecord.setpColorPart(1);
        hzMaterielRecord.setpHeight(reqDTO.getpActualWeight());
        if(reqDTO.getP3cpartFlag().equals("Y")){
            hzMaterielRecord.setP3cPartFlag(1);
        }else if(reqDTO.getP3cpartFlag().equals("N")){
            hzMaterielRecord.setP3cPartFlag(0);
        }else {
            hzMaterielRecord.setP3cPartFlag(null);
        }
        if(reqDTO.getpInOutSideFlag().equals("内饰件")){
            hzMaterielRecord.setpInOutSideFlag(1);
        }else if(reqDTO.getpInOutSideFlag().equals("外饰件")){
            hzMaterielRecord.setpInOutSideFlag(0);
        }else {
            hzMaterielRecord.setpInOutSideFlag(null);
        }
        hzMaterielRecord.setPuid(UUID.randomUUID().toString());
        hzMaterielRecord.setpPartImportantDegree(reqDTO.getpImportance());
        hzMaterielRecord.setpPertainToProjectPuid(reqDTO.getProjectId());
        if("Y".equals(reqDTO.getColorPart())){
            hzMaterielRecord.setpColorPart(1);
        }else if("N".equals(reqDTO.getColorPart())){
            hzMaterielRecord.setpColorPart(0);
        }else {
            hzMaterielRecord.setpColorPart(null);
        }

        return hzMaterielRecord;
    }


    public static HzMaterielRecord updateHzEbomReqDTOMaterielRecord(UpdateHzEbomReqDTO reqDTO){
        HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();
        hzMaterielRecord.setpMaterielCode(reqDTO.getLineId());
        hzMaterielRecord.setpMaterielType("A002");
        hzMaterielRecord.setpMaterielDesc(reqDTO.getpBomLinePartName());
        hzMaterielRecord.setpMaterielDescEn(reqDTO.getpBomLinePartEnName());
        hzMaterielRecord.setpBasicUnitMeasure(reqDTO.getpUnit());
        hzMaterielRecord.setpColorPart(1);
        hzMaterielRecord.setpHeight(reqDTO.getpActualWeight());
        if(reqDTO.getP3cpartFlag().equals("Y")){
            hzMaterielRecord.setP3cPartFlag(1);
        }else if(reqDTO.getP3cpartFlag().equals("N")){
            hzMaterielRecord.setP3cPartFlag(0);
        }else {
            hzMaterielRecord.setP3cPartFlag(null);
        }
        if(reqDTO.getpInOutSideFlag().equals("内饰件")){
            hzMaterielRecord.setpInOutSideFlag(1);
        }else if(reqDTO.getpInOutSideFlag().equals("外饰件")){
            hzMaterielRecord.setpInOutSideFlag(0);
        }else {
            hzMaterielRecord.setpInOutSideFlag(null);
        }
        hzMaterielRecord.setpPartImportantDegree(reqDTO.getpImportance());
        if("Y".equals(reqDTO.getColorPart())){
            hzMaterielRecord.setpColorPart(1);
        }else if("N".equals(reqDTO.getColorPart())){
            hzMaterielRecord.setpColorPart(0);
        }else {
            hzMaterielRecord.setpColorPart(null);
        }
        hzMaterielRecord.setpPertainToProjectPuid(reqDTO.getProjectId());
        return hzMaterielRecord;
    }

    public static HzMaterielRecord mbomRecordToMaterielRecord(String projectId, HzMbomLineRecord record){
        HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();
        hzMaterielRecord.setpMaterielCode(record.getLineId());
        hzMaterielRecord.setpMaterielDesc(record.getpBomLinePartName());
        hzMaterielRecord.setpMaterielDescEn(record.getpBomLinePartEnName());
        hzMaterielRecord.setpMaterielType("A002");
        hzMaterielRecord.setpPertainToProjectPuid(projectId);
        hzMaterielRecord.setpMaterielDataType(BomResourceEnum.enumTypeToMaterielTypeNum(record.getpBomLinePartResource(),record.getIs2Y()));
        hzMaterielRecord.setPuid(UUID.randomUUID().toString());
        hzMaterielRecord.setMaterielResourceId(record.geteBomPuid());
        hzMaterielRecord.setpColorPart(record.getIsColorPart());
        hzMaterielRecord.setpFactoryPuid(record.getpFactoryId());
        return hzMaterielRecord;
    }



    public static HzMaterielRespDTO hzMaterielRecordToRespDTO(HzMaterielRecord record){
        HzMaterielRespDTO respDTO = new HzMaterielRespDTO();
        respDTO.setpBasicUnitMeasure(record.getpBasicUnitMeasure());
        respDTO.setpMaterielCode(record.getpMaterielCode());
        respDTO.setpMaterielDesc(record.getpMaterielDesc());
        respDTO.setpMaterielDescEn(record.getpMaterielDescEn());
        respDTO.setpMaterielType(record.getpMaterielType());
        respDTO.setpMrpController(record.getpMrpController());


        if(StringUtil.isEmpty(record.getFactoryCode())){
            respDTO.setFactoryCode("1001");
        }else {
            respDTO.setFactoryCode(record.getFactoryCode());
        }
        respDTO.setPuid(record.getPuid());
        Integer p3CPartFlag = record.getP3cPartFlag();
        Integer colorPart = record.getpColorPart();
        Integer inOutSideFlag = record.getpInOutSideFlag();
        Integer inventedFlag = record.getpInventedPart();
        Integer loosePartFlag = record.getpLoosePartFlag();
        if (Integer.valueOf(0).equals(p3CPartFlag)) {
            respDTO.setP3cPartFlag("N");
        } else if (Integer.valueOf(1).equals(p3CPartFlag)) {
            respDTO.setP3cPartFlag("Y");
        } else {
            respDTO.setP3cPartFlag("");
        }

        if (Integer.valueOf(0).equals(inventedFlag)) {
            respDTO.setpInventedPart("N");
        } else if (Integer.valueOf(1).equals(inventedFlag)) {
            respDTO.setpInventedPart("Y");
        } else {
            respDTO.setpInventedPart("");
        }

        if (Integer.valueOf(0).equals(colorPart)) {
            respDTO.setpColorPart("N");
        } else if (Integer.valueOf(1).equals(colorPart)) {
            respDTO.setpColorPart("Y");
        } else {
            respDTO.setpColorPart("");
        }

        if (Integer.valueOf(1).equals(inOutSideFlag)) {
            respDTO.setpInOutSideFlag("内饰件");
        } else if (Integer.valueOf(0).equals(inOutSideFlag)) {
            respDTO.setpInOutSideFlag("外饰件");
        } else {
            respDTO.setpInOutSideFlag("");
        }

        if (Integer.valueOf(1).equals(loosePartFlag)) {
            respDTO.setpLoosePartFlag("Y");
        } else if (Integer.valueOf(0).equals(loosePartFlag)) {
            respDTO.setpLoosePartFlag("N");
        } else {
            respDTO.setpLoosePartFlag("");
        }
        respDTO.setpHeight(record.getpHeight());
        respDTO.setpPartImportantDegree(record.getpPartImportantDegree());
        respDTO.setpSpareMaterial(record.getpSpareMaterial());
        respDTO.setpVinPerNo(record.getpVinPerNo());
        respDTO.setResource(record.getResource());
        return respDTO;
    }


    public static HzMaterielRecord featureToMaterielRecord(HzCfg0ModelFeature feature,String projectId){
        HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();
        hzMaterielRecord.setpFactoryPuid(feature.getFactoryCode());
        hzMaterielRecord.setpColorPart(feature.getColor());
        hzMaterielRecord.setPuid(UUID.randomUUID().toString());
        hzMaterielRecord.setpPertainToProjectPuid(projectId);
        hzMaterielRecord.setpMaterielDataType(21);
        hzMaterielRecord.setpMaterielCode(feature.getpFeatureSingleVehicleCode());
        hzMaterielRecord.setpMaterielDesc(feature.getMaterielDesc());
        hzMaterielRecord.setpMaterielDescEn(feature.getMaterielEnDesc());
        hzMaterielRecord.setpCreateName(UserInfo.getUser().getUserName());
        hzMaterielRecord.setpUpdateName(UserInfo.getUser().getUserName());
        hzMaterielRecord.setpMrpController(feature.getMrpController());
        hzMaterielRecord.setpVinPerNo(feature.getVinCode());
        hzMaterielRecord.setResource(feature.getPurchaseType());
        hzMaterielRecord.setpInOutSideFlag(feature.getLabelFlag());
        hzMaterielRecord.setpBasicUnitMeasure(feature.getBasicCountUnit());
        hzMaterielRecord.setMaterielResourceId(feature.getPuid());
        hzMaterielRecord.setpPartImportantDegree(String.valueOf(feature.getImportance()));
        hzMaterielRecord.setP3cPartFlag(feature.getRulesFlag());
        hzMaterielRecord.setpInventedPart(feature.getVirtualFlag());
        hzMaterielRecord.setpMaterielType("A001");
        return hzMaterielRecord;
    }

}
