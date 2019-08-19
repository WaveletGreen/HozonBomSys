package com.connor.hozon.resources.domain.model;

import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.resources.domain.constant.BOMTransConstants;
import com.connor.hozon.resources.domain.dto.response.HzMaterielRespDTO;
import com.connor.hozon.resources.enumtype.BomResourceEnum;
import com.connor.hozon.resources.util.StringUtil;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzMbomLineRecord;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzCfg0ModelFeature;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;

import java.util.UUID;

/**
 * @Author: haozt
 * @Date: 2018/9/11
 * @Description:
 */
public class HzMaterielFactory {
    /**
     * 将EBOM+MBOM的部分字段信息 带入到物料数据来
     * @param
     * @param record
     * @return
     */
    public static HzMaterielRecord mbomRecordToMaterielRecord(String projectId, HzMbomLineRecord record,HzEPLManageRecord eplManageRecord){
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
        hzMaterielRecord.setResource(record.getBuyType());//采购类型
        hzMaterielRecord.setpFactoryPuid(record.getpFactoryId());
        hzMaterielRecord.setpValidFlag(2);
        if(eplManageRecord != null){
            hzMaterielRecord.setpBasicUnitMeasure(eplManageRecord.getpUnit());
            hzMaterielRecord.setpHeight(eplManageRecord.getpActualWeight());
            hzMaterielRecord.setpInOutSideFlag(eplManageRecord.getpInOutSideFlag());
            hzMaterielRecord.setP3cPartFlag(eplManageRecord.getP3cpartFlag());
            hzMaterielRecord.setpPartImportantDegree(eplManageRecord.getpImportance());
        }
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
        respDTO.setStatus(record.getpValidFlag());
        if(StringUtil.isEmpty(record.getFactoryCode())){
            respDTO.setFactoryCode("1001");
        }else {
            respDTO.setFactoryCode(record.getFactoryCode());
        }
        respDTO.setPuid(record.getPuid());
        Integer p3CPartFlag = record.getP3cPartFlag();
        Integer colorPart = record.getpColorPart();
        Integer inOutSideFlag = record.getpInOutSideFlag();
        Integer dataType = record.getpMaterielDataType();
        // 2Y 层的 就是虚拟件  否则就不是
//        Integer inventedFlag = record.getpInventedPart();
        Integer loosePartFlag = record.getpLoosePartFlag();
        respDTO.setP3cPartFlag(BOMTransConstants.integerToYNString(p3CPartFlag));

        if(dataType != null){
            switch (dataType){
                case 31:respDTO.setpInventedPart(BOMTransConstants.Y);
                break;
                default:respDTO.setpInventedPart(BOMTransConstants.N);
                break;
            }
        }
        respDTO.setpColorPart(BOMTransConstants.integerToYNString(colorPart));
        respDTO.setpInOutSideFlag(BOMTransConstants.integerToInOutSideString(inOutSideFlag));
        respDTO.setpLoosePartFlag(BOMTransConstants.integerToYNString(loosePartFlag));
        respDTO.setpHeight(record.getpHeight());
        respDTO.setpPartImportantDegree(record.getpPartImportantDegree());
        respDTO.setpSpareMaterial(record.getpSpareMaterial());
        respDTO.setpVinPerNo(record.getpVinPerNo());
        respDTO.setResource(record.getResource());
        return respDTO;
    }


    public static HzMaterielRecord featureToMaterielRecord(HzCfg0ModelFeature feature,String projectId){
        HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();
        hzMaterielRecord.setpValidFlag(2);
        hzMaterielRecord.setpFactoryPuid(feature.getFactoryCode());
        hzMaterielRecord.setpColorPart(feature.getColor());
        hzMaterielRecord.setPuid(UUID.randomUUID().toString());
        hzMaterielRecord.setpPertainToProjectPuid(projectId);
        hzMaterielRecord.setpMaterielDataType(21);
        hzMaterielRecord.setpMaterielCode(feature.getpFeatureSingleVehicleCode());
        hzMaterielRecord.setpMaterielDesc(feature.getMaterielDesc());
        hzMaterielRecord.setpMaterielDescEn(feature.getMaterielEnDesc());
        if(null == UserInfo.getUser()){
            hzMaterielRecord.setpCreateName("system");
            hzMaterielRecord.setpUpdateName("system");
        }else {
            hzMaterielRecord.setpCreateName(UserInfo.getUser().getUsername());
            hzMaterielRecord.setpUpdateName(UserInfo.getUser().getUsername());
        }
        hzMaterielRecord.setpMrpController("Z02");//整车的MRP控制者都是Z02
        hzMaterielRecord.setpVinPerNo(feature.getVinCode());
        hzMaterielRecord.setResource(feature.getPurchaseType());
        hzMaterielRecord.setpInOutSideFlag(feature.getLabelFlag());
        hzMaterielRecord.setpBasicUnitMeasure(feature.getBasicCountUnit());
        hzMaterielRecord.setMaterielResourceId(feature.getPuid());
        hzMaterielRecord.setpPartImportantDegree(feature.getImportance()==null?null:String.valueOf(feature.getImportance()));
        hzMaterielRecord.setP3cPartFlag(feature.getRulesFlag());
        hzMaterielRecord.setpInventedPart(feature.getVirtualFlag());
        hzMaterielRecord.setpMaterielType("A001");
        return hzMaterielRecord;
    }




    public static HzMaterielRecord hzMaterielRecordToMaterielRecord(HzMaterielRecord record){
        HzMaterielRecord respDTO = new HzMaterielRecord();
        respDTO.setpBasicUnitMeasure(record.getpBasicUnitMeasure());
        respDTO.setpMaterielCode(record.getpMaterielCode());
        respDTO.setpMaterielDesc(record.getpMaterielDesc());
        respDTO.setpMaterielDescEn(record.getpMaterielDescEn());
        respDTO.setpMaterielType(record.getpMaterielType());
        respDTO.setpMrpController(record.getpMrpController());
        respDTO.setpFactoryPuid(record.getpFactoryPuid());
        respDTO.setPuid(record.getPuid());
        respDTO.setP3cPartFlag(record.getP3cPartFlag());
        respDTO.setpColorPart(record.getpColorPart());
        respDTO.setpInOutSideFlag(record.getpInOutSideFlag());
        respDTO.setpInventedPart(record.getpInventedPart());
        respDTO.setpLoosePartFlag(record.getpLoosePartFlag());
        respDTO.setpHeight(record.getpHeight());
        respDTO.setpPartImportantDegree(record.getpPartImportantDegree());
        respDTO.setpSpareMaterial(record.getpSpareMaterial());
        respDTO.setpVinPerNo(record.getpVinPerNo());
        respDTO.setResource(record.getResource());
        respDTO.setOrderId(record.getOrderId());
        respDTO.setEffectTime(record.getEffectTime());
        respDTO.setRevision(record.getRevision());
        respDTO.setMaterielResourceId(record.getMaterielResourceId());
        respDTO.setpValidFlag(record.getpValidFlag());
        respDTO.setpMaterielDataType(record.getpMaterielDataType());
        respDTO.setpCreateTime(record.getpCreateTime());
        respDTO.setpCreateName(record.getpCreateName());
        respDTO.setpPertainToProjectPuid(record.getpPertainToProjectPuid());
        respDTO.setType(record.getType());
        respDTO.setSendSapFlag(record.getSendSapFlag());
        return respDTO;
    }



}
