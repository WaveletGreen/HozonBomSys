package com.connor.hozon.bom.bomSystem.helper;

import com.connor.hozon.bom.bomSystem.iservice.project.IHzVehicleService;
import com.connor.hozon.bom.bomSystem.service.project.HzBrandService;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzBrandRecord;
import sql.pojo.project.HzPlatformRecord;
import sql.pojo.project.HzProjectLibs;
import sql.pojo.project.HzVehicleRecord;

@Service("projectHelper")
public class ProjectHelper {
    /**
     * 项目服务层
     */
    final
    HzProjectLibsService projectLibsService;
    /**
     * 车型服务层
     */
    final
    IHzVehicleService hzVehicleService;
    /**
     * 平台服务层
     */
    final
    HzPlatformService hzPlatformService;
    /**
     * 品牌服务层
     */
    final
    HzBrandService brandService;

    private final Logger logger = LoggerFactory.getLogger(ProjectHelper.class);
    /**
     * 项目
     */
    private HzProjectLibs project;
    /**
     * 车型
     */
    private HzVehicleRecord vehicle;
    /**
     * 平台
     */
    private HzPlatformRecord platform;
    /**
     * 品牌
     */
    private HzBrandRecord brand;

    @Autowired
    public ProjectHelper(HzProjectLibsService projectLibsService, IHzVehicleService hzVehicleService, HzPlatformService hzPlatformService, HzBrandService brandService) {
        this.projectLibsService = projectLibsService;
        this.hzVehicleService = hzVehicleService;
        this.hzPlatformService = hzPlatformService;
        this.brandService = brandService;
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

    public HzProjectLibs getProject() {
        return project;
    }

    public HzVehicleRecord getVehicle() {
        return vehicle;
    }

    public HzPlatformRecord getPlatform() {
        return platform;
    }

    public HzBrandRecord getBrand() {
        return brand;
    }
}
