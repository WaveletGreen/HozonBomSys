package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzSingleVehiclesReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesBomRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesRespDTO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.factory.impl.HzFactoryDAOImpl;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzSingleVehiclesBomRecord;
import sql.pojo.factory.HzFactory;
import sql.pojo.interaction.HzSingleVehicles;

import java.util.Date;


/**
 * @Author: haozt
 * @Date: 2018/9/26
 * @Description:
 */
public class HzSingleVehiclesFactory {
    public static HzSingleVehiclesRespDTO singleVehiclesToRespDTO(HzSingleVehicles vehicles){
        HzSingleVehiclesRespDTO respDTO = new HzSingleVehiclesRespDTO();
        respDTO.setId(vehicles.getId());
        respDTO.setBrandCode(vehicles.getBrandCode());
        respDTO.setBrandName(vehicles.getBrandName());
        respDTO.setColorCode(vehicles.getColorCode());
        respDTO.setColorName(vehicles.getColorName());
        respDTO.setPlatformCode(vehicles.getPlatformCode());
        respDTO.setPlatformName(vehicles.getPlatformName());
        respDTO.setSvlBatteryCode(vehicles.getSvlBatteryCode());
        respDTO.setSvlInnerColorCode(vehicles.getSvlInnerColorCode());
        respDTO.setSvlInnerColorName(vehicles.getSvlInnerColorName());
        respDTO.setSvlMaterialBasicInfo(vehicles.getSvlMaterialBasicInfo());
        respDTO.setSvlMaterialCode(vehicles.getSvlMaterialCode());
        respDTO.setSvlMotorCode(vehicles.getSvlMotorCode());
        respDTO.setVehicleCode(vehicles.getVehicleCode());
        respDTO.setVehicleName(vehicles.getVehicleName());
        return  respDTO;
    }


    public static HzSingleVehicles updateReqDTOSingleVehicles(UpdateHzSingleVehiclesReqDTO reqDTO){
        HzSingleVehicles hzSingleVehicles = new HzSingleVehicles();
        hzSingleVehicles.setId(reqDTO.getId());
        hzSingleVehicles.setSvlInnerColorCode(reqDTO.getSvlInnerColorCode());
        hzSingleVehicles.setSvlInnerColorName(reqDTO.getSvlInnerColorName());
        hzSingleVehicles.setSvlMotorCode(reqDTO.getSvlMotorCode());
        hzSingleVehicles.setSvlBatteryCode(reqDTO.getSvlBatteryCode());
        return hzSingleVehicles;
    }



    public static HzSingleVehiclesBomRecord mbomRecordToSingleVehiclesBom(HzMbomLineRecord record,Long singleVehiclesId){
        HzSingleVehiclesBomRecord vehiclesBomRecord = new HzSingleVehiclesBomRecord();

        vehiclesBomRecord.setBomDigifaxId(record.getBomDigifaxId());
        vehiclesBomRecord.setChange(record.getChange());
        vehiclesBomRecord.setChangeNum(record.getChangeNum());
        vehiclesBomRecord.setCreateName("system");
        vehiclesBomRecord.setEBomPuid(record.geteBomPuid());
        vehiclesBomRecord.setIs2Y(record.getIs2Y());
        vehiclesBomRecord.setIsDept(record.getIsDept());
        vehiclesBomRecord.setIsHas(record.getIsHas());
        vehiclesBomRecord.setIsPart(record.getIsPart());
        vehiclesBomRecord.setLaborHour(record.getLaborHour());
        vehiclesBomRecord.setLineId(record.getLineId());
        vehiclesBomRecord.setLineIndex(record.getLineIndex());
        vehiclesBomRecord.setMachineMaterial(record.getMachineMaterial());
        vehiclesBomRecord.setMBomPuid(record.getPuid());
        vehiclesBomRecord.setParentUid(record.getParentUid());
        vehiclesBomRecord.setPBomLinePartClass(record.getpBomLinePartClass());
        vehiclesBomRecord.setPBomLinePartEnName(record.getpBomLinePartEnName());
        vehiclesBomRecord.setPBomLinePartName(record.getpBomLinePartName());
        vehiclesBomRecord.setPBomLinePartResource(record.getpBomLinePartResource());
        vehiclesBomRecord.setPBomOfWhichDept(record.getpBomOfWhichDept());
        vehiclesBomRecord.setPFactoryId(record.getpFactoryId());
        vehiclesBomRecord.setProcessRoute(record.getProcessRoute());
        vehiclesBomRecord.setPStockLocation(record.getpStockLocation());
        vehiclesBomRecord.setResource(record.getpBomLinePartResource());
        vehiclesBomRecord.setRhythm(record.getRhythm());
        vehiclesBomRecord.setSingleVehiclesId(singleVehiclesId);
        vehiclesBomRecord.setSolderJoint(record.getSolderJoint());
        vehiclesBomRecord.setSortNum(record.getSortNum());
        vehiclesBomRecord.setSparePart(record.getSparePart());
        vehiclesBomRecord.setSparePartNum(record.getSparePartNum());
        vehiclesBomRecord.setStandardPart(record.getStandardPart());
        vehiclesBomRecord.setTools(record.getTools());
        vehiclesBomRecord.setUpdateName("system");
        vehiclesBomRecord.setWasterProduct(record.getWasterProduct());
        vehiclesBomRecord.setCreateTime(new Date());
        vehiclesBomRecord.setUpdateTime(new Date());
        return vehiclesBomRecord;
    }



    public static HzSingleVehiclesBomRespDTO hzSingleVehiclesBomRecordToRespDTO(HzSingleVehiclesBomRecord record){
        HzSingleVehiclesBomRespDTO respDTO  = new HzSingleVehiclesBomRespDTO();
        respDTO.setId(record.getId());
        Integer is2Y = record.getIs2Y();
        Integer hasChildren = record.getIsHas();
        String lineIndex = record.getLineIndex();
        String[] strings = HzBomSysFactory.getLevelAndRank(lineIndex, is2Y, hasChildren);
        respDTO.setLevel(strings[0]);//层级
        respDTO.setLineId(record.getLineId());
        respDTO.setPBomOfWhichDept(record.getPBomOfWhichDept());
        respDTO.setPBomLinePartClass(record.getPBomLinePartClass());
        respDTO.setPBomLinePartEnName(record.getPBomLinePartEnName());
        respDTO.setPBomLinePartName(record.getPBomLinePartName());
        respDTO.setPBomLinePartResource(record.getPBomLinePartResource());
        respDTO.setSparePart(record.getSparePart());
        respDTO.setSparePartNum(record.getSparePartNum());
        respDTO.setLaborHour(record.getLaborHour());
        respDTO.setRhythm(record.getRhythm());
        respDTO.setSolderJoint(record.getSolderJoint());
        respDTO.setMachineMaterial(record.getMachineMaterial());
        respDTO.setStandardPart(record.getStandardPart());
        respDTO.setTools(record.getTools());
        respDTO.setWasterProduct(record.getWasterProduct());
        respDTO.setChange(record.getChange());
        respDTO.setChangeNum(record.getChangeNum());
        respDTO.setPStockLocation(record.getPStockLocation());
        if(null == record.getPFactoryId()){
            respDTO.setPFactoryCode("1001");
        }else {
            HzFactoryDAO hzFactoryDAO = new HzFactoryDAOImpl();
            HzFactory factory = hzFactoryDAO.findFactory(record.getPFactoryId(),"");
            if(factory!= null){
                respDTO.setPFactoryCode(factory.getpFactoryCode());
            }else {
                respDTO.setPFactoryCode("1001");
            }
        }
        return respDTO;
    }

}
