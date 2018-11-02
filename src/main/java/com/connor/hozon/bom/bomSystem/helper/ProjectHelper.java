/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.helper;

import com.connor.hozon.bom.bomSystem.iservice.project.IHzVehicleService;
import com.connor.hozon.bom.bomSystem.service.project.HzBrandService;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzBrandRecord;
import sql.pojo.project.HzPlatformRecord;
import sql.pojo.project.HzProjectLibs;
import sql.pojo.project.HzVehicleRecord;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 项目助手
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("projectHelper")
public class ProjectHelper {
    /**
     * 项目服务层
     */
    @Autowired
    HzProjectLibsService projectLibsService;
    /**
     * 车型服务层
     */
    @Autowired
    IHzVehicleService hzVehicleService;
    /**
     * 平台服务层
     */
    @Autowired
    HzPlatformService hzPlatformService;
    /**
     * 品牌服务层
     */
    @Autowired
    HzBrandService brandService;
    /**
     * 项目
     */
    @Getter
    private HzProjectLibs project;
    /**
     * 车型
     */
    @Getter
    private HzVehicleRecord vehicle;
    /**
     * 平台
     */
    @Getter
    private HzPlatformRecord platform;
    /**
     * 品牌
     */
    @Getter
    private HzBrandRecord brand;

    private final Logger logger = LoggerFactory.getLogger(ProjectHelper.class);

    @Autowired
    public ProjectHelper() {
    }

    /**
     * 根据项目的puid，获取到项目树结构，从上到下为：品牌，平台，车型，项目
     *
     * @param projectPuid 项目puid
     */
    public void doGetProjectTreeByProjectId(String projectPuid) {
        try {
            project = projectLibsService.doLoadProjectLibsById(projectPuid);
            vehicle = hzVehicleService.doGetByPuid(project.getpProjectPertainToVehicle());
            platform = hzPlatformService.doGetByPuid(vehicle.getpVehiclePertainToPlatform());
            brand = brandService.doGetByPuid(platform.getpPertainToBrandPuid());
        } catch (Exception e) {
            logger.error("根据项目PUID查询项目结构树出现错误", e);
        }
    }
}
