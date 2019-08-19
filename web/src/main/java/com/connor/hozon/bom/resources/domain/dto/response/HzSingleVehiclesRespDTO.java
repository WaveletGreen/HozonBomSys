/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.dto.response;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;
import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/9/26
 * @Description: 单车清单
 */
@Data
public class HzSingleVehiclesRespDTO extends BaseDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 物料编号
     */
    private String svlMaterialCode;
    /**
     * 基本信息
     */
    private String svlMaterialBasicInfo;
    /**
     * 内饰颜色代码
     */
    private String svlInnerColorCode;
    /**
     * 内饰颜色名称
     */
    private String svlInnerColorName;
    /**
     * 颜色代码
     */
    private String colorCode;
    /**
     * 颜色名称
     */
    private String colorName;
    /**
     * 电池型号
     */
    private String svlBatteryCode;
    /**
     * 电机型号
     */
    private String svlMotorCode;
    /**
     * 品牌代码
     */
    private String brandCode;
    /**
     * 中文品牌
     */
    private String brandName;
    /**
     * 平台代码
     */
    private String platformCode;
    /**
     * 平台名称
     */
    private String platformName;
    /**
     * 车型代码
     */
    private String vehicleCode;
    /**
     * 车型名称
     */
    private String vehicleName;
    /**
     * 检查状态
     */
    private Integer checkStatus;
}
