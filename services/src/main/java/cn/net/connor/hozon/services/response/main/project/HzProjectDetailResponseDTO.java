/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.response.main.project;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 项目详情传输对象
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Data
public class HzProjectDetailResponseDTO implements Serializable {
    private static final long serialVersionUID = 3696046024666686697L;
    /**
     * 项目代码
     */
    private String projCode;
    private String projName;
    /**
     * 品牌代码
     */
    private String projBrandCode;
    private String projBrandName;
    /**
     * 平台代码
     */
    private String projPlatformCode;
    private String projPlatformName;
    /**
     * 车型代码
     */
    private String projVehicleCode;
    private String projVehicleName;
    /**
     * 车身形式代码
     */
    private String projVehicleShapeCode;
    private String projVehicleTranName;
    /**
     * 年型
     */
    private String projVehicleAnnualCode;
    private String projVehicleAnnual;
}
