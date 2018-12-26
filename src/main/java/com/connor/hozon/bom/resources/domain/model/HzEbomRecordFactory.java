package com.connor.hozon.bom.resources.domain.model;


import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.constant.BOMTransConstants;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.util.DateUtil;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzImportEbomRecord;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.UUID;
/**
 * @Author: haozt
 * @Date: 2018/9/6
 * @Description: EBOM的工厂类
 */
public class HzEbomRecordFactory {
    public static HzBomLineRecord addEbomDTOBomLineRecord(AddHzEbomReqDTO reqDTO){
        HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
        hzBomLineRecord.setLinePuid(UUID.randomUUID().toString());
        hzBomLineRecord.setLineID(reqDTO.getLineId());
        hzBomLineRecord.setpCreateName(UserInfo.getUser().getUserName());
        hzBomLineRecord.setpUpdateName(UserInfo.getUser().getUserName());
        hzBomLineRecord.setpFnaDesc(reqDTO.getpFnaDesc());
        hzBomLineRecord.setpFnaInfo(reqDTO.getFna());
        hzBomLineRecord.setpUpc(reqDTO.getpUpc());
        hzBomLineRecord.setNumber(reqDTO.getNumber());
        hzBomLineRecord.setColorPart(BOMTransConstants.constantStringToInteger(reqDTO.getColorPart()));
        return hzBomLineRecord;
    }



    public static HzEPLManageRecord addEbomTOEbomRecord(AddHzEbomReqDTO reqDTO,Long eplId,Integer is2Y,
                                                        Integer hasChildren,String bomDigifaxId ){
        HzEPLManageRecord record = new HzEPLManageRecord();
        record.setPuid(UUID.randomUUID().toString());
        record.setLineID(reqDTO.getLineId());
        record.setpCreateName(UserInfo.getUser().getUserName());
        record.setpUpdateName(UserInfo.getUser().getUserName());
        record.setpFnaDesc(reqDTO.getpFnaDesc());
        record.setpFnaInfo(reqDTO.getFna());
        record.setpUpc(reqDTO.getpUpc());
        record.setNumber(reqDTO.getNumber());
        record.setEplId(eplId);
        record.setIsHas(hasChildren);
        record.setIs2Y(is2Y);
        record.setBomDigifaxId(bomDigifaxId);
        record.setColorPart(BOMTransConstants.constantStringToInteger(reqDTO.getColorPart()));
        return record;
    }

    public static HzBomLineRecord updateHzEbomDTOLineRecord(UpdateHzEbomReqDTO reqDTO){
        HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
        hzBomLineRecord.setLineID(reqDTO.getLineId());
        hzBomLineRecord.setpUpdateName(UserInfo.getUser().getUserName());
        hzBomLineRecord.setpFnaDesc(reqDTO.getpFnaDesc());
        hzBomLineRecord.setpFnaInfo(reqDTO.getFna());
        hzBomLineRecord.setpUpc(reqDTO.getpUpc());
        hzBomLineRecord.setNumber(reqDTO.getNumber());
        hzBomLineRecord.setColorPart(BOMTransConstants.constantStringToInteger(reqDTO.getColorPart()));
        return hzBomLineRecord;
    }

    public static JSONObject bomLineRecordTORespDTO(HzEPLManageRecord record){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("puid", record.getPuid());
        Integer is2Y = record.getIs2Y();
        Integer hasChildren = record.getIsHas();
        String lineIndex = record.getLineIndex();
        String[] strings = HzBomSysFactory.getLevelAndRank(lineIndex, is2Y, hasChildren);
        jsonObject.put("level", strings[0]);
        jsonObject.put("rank", strings[1]);
        jsonObject.put("pBomOfWhichDept", record.getpBomOfWhichDept());
        jsonObject.put("lineId", record.getLineID());
        jsonObject.put("fna",record.getFna());
        jsonObject.put("lineNo",strings[2]);
        jsonObject.put("pBomLinePartName", record.getpBomLinePartName());
        jsonObject.put("pBomLinePartClass", record.getpBomLinePartClass());
        jsonObject.put("pBomLinePartEnName",record.getpBomLinePartEnName());
        jsonObject.put("pBomLinePartResource", record.getpBomLinePartResource());
        jsonObject.put("pFastener", record.getpFastener());
        if(Integer.valueOf(1).equals(record.getpLouaFlag())){
            jsonObject.put("pLouaFlag","LOU");
        }else if(Integer.valueOf(0).equals(record.getpLouaFlag())){
            jsonObject.put("pLouaFlag","LOA");
        }
        if(Integer.valueOf(1).equals(record.getP3cpartFlag())){
            jsonObject.put("p3cpartFlag", "Y");
        }else if(Integer.valueOf(0).equals(record.getP3cpartFlag())){
            jsonObject.put("p3cpartFlag", "N");
        }else {
            jsonObject.put("p3cpartFlag", "");
        }
        if(Integer.valueOf(1).equals(record.getpInOutSideFlag())){
            jsonObject.put("pInOutSideFlag", "内饰件");
        }else if(Integer.valueOf(0).equals(record.getpInOutSideFlag())){
            jsonObject.put("pInOutSideFlag", "外饰件");
        }else {
            jsonObject.put("pInOutSideFlag", "");
        }
        jsonObject.put("pUpc",record.getpUpc());
        jsonObject.put("pFnaDesc", record.getpFnaDesc());
        jsonObject.put("pUnit", record.getpUnit());
        jsonObject.put("pPictureNo",record.getpPictureNo());
        jsonObject.put("pPictureSheet", record.getpPictureSheet());
        jsonObject.put("pMaterialHigh", record.getpMaterialHigh());
        jsonObject.put("pMaterial1",record.getpMaterial1());
        jsonObject.put("pMaterial2", record.getpMaterial2());
        jsonObject.put("pMaterial3", record.getpMaterial3());
        jsonObject.put("pDensity",record.getpDensity());
        jsonObject.put("pMaterialStandard", record.getpMaterialStandard());
        jsonObject.put("pSurfaceTreat", record.getpSurfaceTreat());
        jsonObject.put("pTextureColorNum",record.getpTextureColorNum());
        jsonObject.put("pManuProcess", record.getpManuProcess());
        jsonObject.put("pSymmetry", record.getpSymmetry());
        jsonObject.put("pImportance",record.getpImportance());
        if(Integer.valueOf(1).equals(record.getpRegulationFlag())){
            jsonObject.put("pRegulationFlag", "Y");
        }else if(Integer.valueOf(0).equals(record.getpRegulationFlag())){
            jsonObject.put("pRegulationFlag", "N");
        }else {
            jsonObject.put("pRegulationFlag", "");
        }
        jsonObject.put("pBwgBoxPart", record.getpBwgBoxPart());
        jsonObject.put("pDevelopType",record.getpDevelopType());
        jsonObject.put("pDataVersion", record.getpDataVersion());
        jsonObject.put("pTargetWeight", record.getpTargetWeight());
        jsonObject.put("pFeatureWeight",record.getpFeatureWeight());
        jsonObject.put("pActualWeight", record.getpActualWeight());
        jsonObject.put("pFastenerStandard", record.getpFastenerStandard());
        jsonObject.put("pFastenerLevel",record.getpFastenerLevel());

        jsonObject.put("pTorque", record.getpTorque());
        jsonObject.put("pDutyEngineer",record.getpDutyEngineer());
        jsonObject.put("pSupply", record.getpSupply());
        jsonObject.put("pSupplyCode", record.getpSupplyCode());
        jsonObject.put("pRemark",record.getpRemark());
        jsonObject.put("pRegulationCode", record.getpRegulationCode());
        jsonObject.put("number",record.getNumber());
        jsonObject.put("pBuyEngineer",record.getpBuyEngineer());
        jsonObject.put("status",record.getStatus());
        if(Integer.valueOf(1).equals(record.getColorPart())){
            jsonObject.put("colorPart", "Y");
        }else if(Integer.valueOf(0).equals(record.getColorPart())){
            jsonObject.put("colorPart", "N");
        }else {
            jsonObject.put("colorPart", "");
        }
        jsonObject.put("effectTime",DateUtil.formatDefaultDate(record.getEffectTime()));
        return jsonObject;
    }



    public static HzEbomRespDTO eplRecordToEbomRespDTO(HzEPLManageRecord record){
        HzEbomRespDTO respDTO   = new HzEbomRespDTO();
        Integer is2Y = record.getIs2Y();
        Integer hasChildren = record.getIsHas();
        String lineIndex = record.getLineIndex();
        String[] strings = HzBomSysFactory.getLevelAndRank(lineIndex, is2Y, hasChildren);
        respDTO.setLevel(strings[0]);
        respDTO.setLineNo(strings[2]);
        respDTO.setPuid(record.getPuid());
        respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept());
        respDTO.setLineId(record.getLineID());
        respDTO.setpBomLinePartName(record.getpBomLinePartName());
        respDTO.setpBomLinePartClass(record.getpBomLinePartClass());
        respDTO.setpBomLinePartEnName(record.getpBomLinePartEnName());
        respDTO.setpBomLinePartResource( record.getpBomLinePartResource());
        respDTO.setpFastener( record.getpFastener());
        respDTO.setP3cpartFlag(BOMTransConstants.integerToYNString(record.getP3cpartFlag()));
        respDTO.setpInOutSideFlag(BOMTransConstants.integerToInOutSideString(record.getpInOutSideFlag()));
        respDTO.setpUpc(record.getpUpc());
        respDTO.setpFnaDesc( record.getpFnaDesc());
        respDTO.setpUnit( record.getpUnit());
        respDTO.setpPictureNo(record.getpPictureNo());
        respDTO.setpPictureSheet( record.getpPictureSheet());
        respDTO.setpMaterialHigh( record.getpMaterialHigh());
        respDTO.setpMaterial1(record.getpMaterial1());
        respDTO.setpMaterial2( record.getpMaterial2());
        respDTO.setpMaterial3( record.getpMaterial3());
        respDTO.setpDensity(record.getpDensity());
        respDTO.setpMaterialStandard( record.getpMaterialStandard());
        respDTO.setpSurfaceTreat( record.getpSurfaceTreat());
        respDTO.setpTextureColorNum(record.getpTextureColorNum());
        respDTO.setpManuProcess( record.getpManuProcess());
        respDTO.setpSymmetry( record.getpSymmetry());
        respDTO.setpImportance(record.getpImportance());

        respDTO.setpRegulationFlag(BOMTransConstants.integerToYNString(record.getpRegulationFlag()));

        respDTO.setpBwgBoxPart( record.getpBwgBoxPart());
        respDTO.setpDevelopType(record.getpDevelopType());
        respDTO.setpDataVersion( record.getpDataVersion());
        respDTO.setpTargetWeight( record.getpTargetWeight());
        respDTO.setpFeatureWeight(record.getpFeatureWeight());
        respDTO.setpActualWeight( record.getpActualWeight());
        respDTO.setpFastenerStandard( record.getpFastenerStandard());
        respDTO.setpFastenerLevel(record.getpFastenerLevel());

        respDTO.setpTorque( record.getpTorque());
        respDTO.setpDutyEngineer(record.getpDutyEngineer());
        respDTO.setpSupply( record.getpSupply());
        respDTO.setpSupplyCode( record.getpSupplyCode());
        respDTO.setpRemark(record.getpRemark());
        respDTO.setpRegulationCode( record.getpRegulationCode());
        respDTO.setNumber(record.getNumber());
        respDTO.setpBuyEngineer(record.getpBuyEngineer());
        respDTO.setColorPart(BOMTransConstants.integerToYNString(record.getColorPart()));
        return respDTO;
    }


    public static HzBomLineRecord importEbomRecordToBomLineRecord(HzImportEbomRecord record){
        HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
//        hzBomLineRecord.setpBomOfWhichDept(record.getpBomOfWhichDept());
//        hzBomLineRecord.setLinePuid(UUID.randomUUID().toString());
        hzBomLineRecord.setLineID(record.getLineId());
//        hzBomLineRecord.setpBomLinePartClass(record.getpBomLinePartClass());
//        hzBomLineRecord.setpBomLinePartName(record.getpBomLinePartName());
//        hzBomLineRecord.setP3cpartFlag(record.getP3cpartFlag());
//        hzBomLineRecord.setpBuyEngineer(record.getpBuyEngineer());
//        hzBomLineRecord.setpActualWeight(record.getpActualWeight());
//        hzBomLineRecord.setpBomLinePartEnName(record.getpBomLinePartEnName());
//        hzBomLineRecord.setpBwgBoxPart(record.getpBwgBoxPart());
//        hzBomLineRecord.setpDataVersion(record.getpDataVersion());
//        hzBomLineRecord.setpDensity(record.getpDensity());
        hzBomLineRecord.setpUpdateName(UserInfo.getUser().getUserName());
//        hzBomLineRecord.setpDevelopType(record.getpDevelopType());
//        hzBomLineRecord.setpDutyEngineer(record.getpDutyEngineer());
//        hzBomLineRecord.setpFastener(record.getpFastener());
//        hzBomLineRecord.setpFastenerLevel(record.getpFastenerLevel());
//        hzBomLineRecord.setpFastenerStandard(record.getpFastenerStandard());
//        hzBomLineRecord.setpFeatureWeight(record.getpFeatureWeight());
        hzBomLineRecord.setpFnaDesc(record.getpFnaDesc());
        hzBomLineRecord.setpFnaInfo(record.getpFnaInfo());
//        hzBomLineRecord.setpInOutSideFlag(record.getpInOutSideFlag());
//        hzBomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
//        hzBomLineRecord.setpImportance(record.getpImportance());
//        hzBomLineRecord.setpManuProcess(record.getpManuProcess());
//        hzBomLineRecord.setpMaterial1(record.getpMaterial1());
//        hzBomLineRecord.setpMaterial2(record.getpMaterial2());
//        hzBomLineRecord.setpMaterial3(record.getpMaterial3());
//        hzBomLineRecord.setpMaterialHigh(record.getpMaterialHigh());
//        hzBomLineRecord.setpMaterialStandard(record.getpMaterialStandard());
//        hzBomLineRecord.setpPictureNo(record.getpPictureNo());
//        hzBomLineRecord.setpPictureSheet(record.getpPictureSheet());
//        hzBomLineRecord.setpRegulationCode(record.getpRegulationCode());
        hzBomLineRecord.setpUpc(record.getpUpc());
//        hzBomLineRecord.setpUnit(record.getpUnit());
//        hzBomLineRecord.setpRemark(record.getpRemark());
//        hzBomLineRecord.setpSymmetry(record.getpSymmetry());
//        hzBomLineRecord.setpTextureColorNum(record.getpTextureColorNum());
//        hzBomLineRecord.setpSurfaceTreat(record.getpSurfaceTreat());
//        hzBomLineRecord.setpTargetWeight(record.getpTargetWeight());
//        hzBomLineRecord.setpSupplyCode(record.getpSupplyCode());
//        hzBomLineRecord.setpSupply(record.getpSupply());
//        hzBomLineRecord.setpTorque(record.getpTorque());
//        hzBomLineRecord.setpBomLinePartResource(record.getpBomLinePartResource());
        hzBomLineRecord.setNumber(record.getNumber());
        hzBomLineRecord.setStatus(2);
//        hzBomLineRecord.setpRegulationFlag(record.getpRegulationFlag());
        hzBomLineRecord.setColorPart(record.getColorPart());
        return hzBomLineRecord;
    }

    public static HzBomLineRecord eplRecordToBomLineRecord(HzEPLManageRecord record){
        HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
//        hzBomLineRecord.setpBomOfWhichDept(record.getpBomOfWhichDept());
        hzBomLineRecord.setLineID(record.getLineID());
//        hzBomLineRecord.setpBomLinePartClass(record.getpBomLinePartClass());
//        hzBomLineRecord.setpBomLinePartName(record.getpBomLinePartName());
//        hzBomLineRecord.setP3cpartFlag(record.getP3cpartFlag());
//        hzBomLineRecord.setpBuyEngineer(record.getpBuyEngineer());
//        hzBomLineRecord.setpActualWeight(record.getpActualWeight());
//        hzBomLineRecord.setpBomLinePartEnName(record.getpBomLinePartEnName());
//        hzBomLineRecord.setpBwgBoxPart(record.getpBwgBoxPart());
//        hzBomLineRecord.setpDataVersion(record.getpDataVersion());
//        hzBomLineRecord.setpDensity(record.getpDensity());
//        hzBomLineRecord.setpDevelopType(record.getpDevelopType());
//        hzBomLineRecord.setpDutyEngineer(record.getpDutyEngineer());
//        hzBomLineRecord.setpFastener(record.getpFastener());
//        hzBomLineRecord.setpFastenerLevel(record.getpFastenerLevel());
//        hzBomLineRecord.setpFastenerStandard(record.getpFastenerStandard());
//        hzBomLineRecord.setpFeatureWeight(record.getpFeatureWeight());
        hzBomLineRecord.setpFnaDesc(record.getpFnaDesc());
        hzBomLineRecord.setpFnaInfo(record.getFna());
//        hzBomLineRecord.setpInOutSideFlag(record.getpInOutSideFlag());
//        hzBomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
//        hzBomLineRecord.setpImportance(record.getpImportance());
//        hzBomLineRecord.setpManuProcess(record.getpManuProcess());
//        hzBomLineRecord.setpMaterial1(record.getpMaterial1());
//        hzBomLineRecord.setpMaterial2(record.getpMaterial2());
//        hzBomLineRecord.setpMaterial3(record.getpMaterial3());
//        hzBomLineRecord.setpMaterialHigh(record.getpMaterialHigh());
//        hzBomLineRecord.setpMaterialStandard(record.getpMaterialStandard());
//        hzBomLineRecord.setpPictureNo(record.getpPictureNo());
//        hzBomLineRecord.setpPictureSheet(record.getpPictureSheet());
//        hzBomLineRecord.setpRegulationCode(record.getpRegulationCode());
        hzBomLineRecord.setpUpc(record.getpUpc());
//        hzBomLineRecord.setpUnit(record.getpUnit());
//        hzBomLineRecord.setpRemark(record.getpRemark());
//        hzBomLineRecord.setpSymmetry(record.getpSymmetry());
//        hzBomLineRecord.setpTextureColorNum(record.getpTextureColorNum());
//        hzBomLineRecord.setpSurfaceTreat(record.getpSurfaceTreat());
//        hzBomLineRecord.setpTargetWeight(record.getpTargetWeight());
//        hzBomLineRecord.setpSupplyCode(record.getpSupplyCode());
//        hzBomLineRecord.setpSupply(record.getpSupply());
//        hzBomLineRecord.setpTorque(record.getpTorque());
//        hzBomLineRecord.setpBomLinePartResource(record.getpBomLinePartResource());
        hzBomLineRecord.setNumber(record.getNumber());
//        hzBomLineRecord.setpRegulationFlag(record.getpRegulationFlag());
        hzBomLineRecord.setColorPart(record.getColorPart());
        return hzBomLineRecord;
    }
}
