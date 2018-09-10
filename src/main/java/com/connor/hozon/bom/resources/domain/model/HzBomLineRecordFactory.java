package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import sql.pojo.bom.HzBomLineRecord;

/**
 * @Author: haozt
 * @Date: 2018/9/6
 * @Description: bomLine的工厂类
 */
public class HzBomLineRecordFactory {

    public static HzBomLineRecord addHzEbomDTOHzBomLineDO(AddHzEbomReqDTO reqDTO){
        HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
        hzBomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
        hzBomLineRecord.setIs2Y(0);

        hzBomLineRecord.setIsDept(0);
        hzBomLineRecord.setLineID(reqDTO.getLineId());
        hzBomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
        hzBomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
        if(reqDTO.getP3cpartFlag().equals("Y")){
            hzBomLineRecord.setP3cpartFlag(1);
        }else if(reqDTO.getP3cpartFlag().equals("N")){
            hzBomLineRecord.setP3cpartFlag(0);
        }else {
            hzBomLineRecord.setP3cpartFlag(null);
        }
        hzBomLineRecord.setpBuyEngineer(reqDTO.getpBuyEngineer());
        hzBomLineRecord.setpActualWeight(reqDTO.getpActualWeight());
        hzBomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
        hzBomLineRecord.setpBwgBoxPart(reqDTO.getpBwgBoxPart());
        hzBomLineRecord.setpDataVersion(reqDTO.getpDataVersion());
        hzBomLineRecord.setpDensity(reqDTO.getpDensity());
        hzBomLineRecord.setpCreateName(UserInfo.getUser().getUserName());
        hzBomLineRecord.setpUpdateName(UserInfo.getUser().getUserName());
        hzBomLineRecord.setpDevelopType(reqDTO.getpDevelopType());
        hzBomLineRecord.setpDutyEngineer(reqDTO.getpDutyEngineer());
        hzBomLineRecord.setpFastener(reqDTO.getpFastener());
        hzBomLineRecord.setpFastenerLevel(reqDTO.getpFastenerLevel());
        hzBomLineRecord.setpFastenerStandard(reqDTO.getpFastenerStandard());
        hzBomLineRecord.setpFeatureWeight(reqDTO.getpFeatureWeight());
        hzBomLineRecord.setpFnaDesc(reqDTO.getpFnaDesc());
        hzBomLineRecord.setpFnaInfo(reqDTO.getFna());
        if(reqDTO.getpInOutSideFlag().equals("内饰件")){
            hzBomLineRecord.setpInOutSideFlag(1);
        }else if(reqDTO.getpInOutSideFlag().equals("外饰件")){
            hzBomLineRecord.setpInOutSideFlag(0);
        }else {
            hzBomLineRecord.setpInOutSideFlag(null);
        }

        hzBomLineRecord.setpImportance(reqDTO.getpImportance());
        hzBomLineRecord.setpManuProcess(reqDTO.getpManuProcess());
        hzBomLineRecord.setpMaterial1(reqDTO.getpMaterial1());
        hzBomLineRecord.setpMaterial2(reqDTO.getpMaterial2());
        hzBomLineRecord.setpMaterial3(reqDTO.getpMaterial3());
        hzBomLineRecord.setpMaterialHigh(reqDTO.getpMaterialHigh());
        hzBomLineRecord.setpMaterialStandard(reqDTO.getpMaterialStandard());
        hzBomLineRecord.setpPictureNo(reqDTO.getpPictureNo());
        hzBomLineRecord.setpPictureSheet(reqDTO.getpPictureSheet());
        hzBomLineRecord.setpRegulationCode(reqDTO.getpRegulationCode());
        hzBomLineRecord.setpUpc(reqDTO.getpUpc());
        hzBomLineRecord.setpUnit(reqDTO.getpUnit());
        hzBomLineRecord.setpRemark(reqDTO.getpRemark());
        hzBomLineRecord.setpSymmetry(reqDTO.getpSymmetry());
        hzBomLineRecord.setpTextureColorNum(reqDTO.getpTextureColorNum());
        hzBomLineRecord.setpSurfaceTreat(reqDTO.getpSurfaceTreat());
        hzBomLineRecord.setpTargetWeight(reqDTO.getpTargetWeight());
        hzBomLineRecord.setpSupplyCode(reqDTO.getpSupplyCode());
        hzBomLineRecord.setpSupply(reqDTO.getpSupply());
        hzBomLineRecord.setpTorque(reqDTO.getpTorque());
        hzBomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
        hzBomLineRecord.setNumber(reqDTO.getNumber());
        if(reqDTO.getpRegulationFlag().equals("Y")){
            hzBomLineRecord.setpRegulationFlag(1);
        }else if(reqDTO.getpRegulationFlag().equals("N")){
            hzBomLineRecord.setpRegulationFlag(0);
        }else {
            hzBomLineRecord.setpRegulationFlag(null);
        }

        return hzBomLineRecord;
    }
}
