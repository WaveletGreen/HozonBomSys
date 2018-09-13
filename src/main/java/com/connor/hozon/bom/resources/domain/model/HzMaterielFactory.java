package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
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
        return hzMaterielRecord;
    }
}
