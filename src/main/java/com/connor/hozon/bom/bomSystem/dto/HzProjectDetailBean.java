/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 项目详情传输对象
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Data
public class HzProjectDetailBean {
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
