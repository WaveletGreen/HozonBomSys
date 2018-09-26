package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzSingleVehiclesReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesRespDTO;
import sql.pojo.interaction.HzSingleVehicles;

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
}
