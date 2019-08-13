package com.connor.hozon.bom.resources.domain.model;


import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.constant.BOMTransConstants;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzBomLineRecord;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzImportEbomRecord;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;

import java.util.Map;
import java.util.UUID;
/**
 * @Author: haozt
 * @Date: 2018/9/6
 * @Description: EBOM的工厂类
 */
public class HzEbomRecordFactory {
    public static HzEPLManageRecord addEbomTOEbomRecord(AddHzEbomReqDTO reqDTO,Long eplId,Integer is2Y,
                                                        Integer hasChildren,String bomDigifaxId ){
        HzEPLManageRecord record = new HzEPLManageRecord();
        record.setPuid(UUID.randomUUID().toString());
        record.setLineID(reqDTO.getLineId());
        record.setpCreateName(UserInfo.getUser().getUsername());
        record.setpUpdateName(UserInfo.getUser().getUsername());
        record.setpFnaDesc(reqDTO.getpFnaDesc());
        record.setpFnaInfo(reqDTO.getFna());
        record.setpUpc(reqDTO.getpUpc());
        record.setNumber(reqDTO.getNumber());
        record.setEplId(eplId);
        record.setIsHas(hasChildren);
        record.setIs2Y(is2Y);
        record.setBomDigifaxId(bomDigifaxId);
        record.setColorPart(BOMTransConstants.constantStringToInteger(reqDTO.getColorPart()));
        record.setSparePartNum(reqDTO.getSparePartNum());
        record.setSparePart(reqDTO.getSparePart());
        record.setStatus(2);
        return record;
    }

    public static HzEPLManageRecord updateEBOMReqTORecord(UpdateHzEbomReqDTO reqDTO){
        HzEPLManageRecord record = new HzEPLManageRecord();
        record.setColorPart(BOMTransConstants.constantStringToInteger(reqDTO.getColorPart()));
        record.setNumber(reqDTO.getNumber());
        record.setpFnaDesc(reqDTO.getpFnaDesc());
        record.setpFnaInfo(reqDTO.getFna());
        record.setFna(reqDTO.getFna());
        record.setpUpc(reqDTO.getpUpc());
        record.setUpdateName(UserInfo.getUser().getUsername());
        record.setPuid(reqDTO.getPuid());
        record.setProjectId(reqDTO.getProjectId());
        Map<String,Object> map = reqDTO.getUpdateDosage();
        //单车用量数据拼接 智时版-23,360pro-,
        StringBuilder stringBuilder = new StringBuilder();
        for(String key : map.keySet()){
            String value = (String)map.get(key);
            if(key.contains("title")){
                int charAt = key.indexOf("title");
                key = key.substring(0,charAt);
            }
            if(StringUtils.isNotBlank(value) ){
                stringBuilder.append(key+"#"+value+",");
            }else {
                stringBuilder.append(key+"#"+"-"+",");
            }

        }
        record.setVehNum(stringBuilder.toString());
        record.setSparePart(reqDTO.getSparePart());
        record.setSparePartNum(reqDTO.getSparePartNum());
        return record;
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
        jsonObject.put("p3cpartFlag",BOMTransConstants.integerToYNString(record.getP3cpartFlag()));
        jsonObject.put("pInOutSideFlag",BOMTransConstants.integerToInOutSideString(record.getpInOutSideFlag()));
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
        jsonObject.put("pRegulationFlag",BOMTransConstants.integerToYNString(record.getpRegulationFlag()));
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
        jsonObject.put("sparePart",record.getSparePart());
        jsonObject.put("sparePartNum",record.getSparePartNum());
        jsonObject.put("status",record.getStatus());
        jsonObject.put("colorPart", BOMTransConstants.integerToYNString(record.getColorPart()));
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
        respDTO.setSparePart(record.getSparePart());
        respDTO.setSparePartNum(record.getSparePartNum());
        return respDTO;
    }


    public static HzEPLManageRecord importEbomRecordToBomLineRecord(HzImportEbomRecord record){
        HzEPLManageRecord hzBomLineRecord = new HzEPLManageRecord();
        hzBomLineRecord.setLineID(record.getLineId());
        hzBomLineRecord.setpUpdateName(UserInfo.getUser().getUsername());
        hzBomLineRecord.setpFnaDesc(record.getpFnaDesc());
        hzBomLineRecord.setpFnaInfo(record.getpFnaInfo());
        hzBomLineRecord.setpUpc(record.getpUpc());
        hzBomLineRecord.setNumber(record.getNumber());
        hzBomLineRecord.setStatus(2);
        hzBomLineRecord.setColorPart(record.getColorPart());
        hzBomLineRecord.setEplId(record.getEplId());
        hzBomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzBomLineRecord.setSparePart(record.getSparePart());
        hzBomLineRecord.setSparePartNum(record.getSparePartNum());
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
        hzBomLineRecord.setEplId(record.getEplId());
        hzBomLineRecord.setPuid(record.getPuid());
        return hzBomLineRecord;
    }

    public static HzEPLManageRecord ebomRecordToEBOMRecord(HzEPLManageRecord record){
        HzEPLManageRecord hzBomLineRecord = new HzEPLManageRecord();
        hzBomLineRecord.setLineID(record.getLineID());
        hzBomLineRecord.setpFnaDesc(record.getpFnaDesc());
        hzBomLineRecord.setpFnaInfo(record.getFna());
        hzBomLineRecord.setpUpc(record.getpUpc());
        hzBomLineRecord.setNumber(record.getNumber());
        hzBomLineRecord.setColorPart(record.getColorPart());
        hzBomLineRecord.setEplId(record.getEplId());
        hzBomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzBomLineRecord.setSparePart(record.getSparePart());
        hzBomLineRecord.setSparePartNum(record.getSparePartNum());
        hzBomLineRecord.setStatus(record.getStatus());
        return hzBomLineRecord;
    }
}
