/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzCfg0BomLineOfModelDao;
import com.connor.hozon.bom.bomSystem.dao.model.HzCfg0ModelDetailDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzFcfgBomLvl1ListOperationService;
import com.connor.hozon.bom.bomSystem.iservice.project.IHzVehicleService;
import com.connor.hozon.bom.bomSystem.service.main.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.project.HzBrandService;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.fullCfg.HzCfg0BomLineOfModel;
import sql.pojo.cfg.fullCfg.HzFcfgBomLvl1ListOperation;
import sql.pojo.cfg.main.HzCfg0MainRecord;
import sql.pojo.cfg.model.HzCfg0ModelDetail;
import sql.pojo.cfg.model.HzCfg0ModelRecord;
import sql.pojo.project.HzBrandRecord;
import sql.pojo.project.HzPlatformRecord;
import sql.pojo.project.HzProjectLibs;
import sql.pojo.project.HzVehicleRecord;

import java.util.*;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 18:14
 */
@Service("hzCfg0BomLineOfModelService")
public class HzCfg0BomLineOfModelService {
    /**
     * BOMLine对应车型模型dao层
     */
    private final HzCfg0BomLineOfModelDao hzCfg0BomLineOfModelDao;
    /**
     * 配置主数据服务层
     */
    private final HzCfg0MainService hzCfg0MainService;
    /**
     * 全配置bom一级清单操作类型服务层，拓展层
     */
    private final IHzFcfgBomLvl1ListOperationService hzFcfgBomLvl1ListOperationService;
    /***
     * 项目服务层
     */
    private final HzProjectLibsService hzProjectLibsService;
    /***
     * 车型服务层
     */
    private final IHzVehicleService hzVehicleService;
    /***
     * 平台服务层
     */
    private final HzPlatformService hzPlatformService;
    /**
     * 品牌服务层
     */
    private final HzBrandService hzBrandService;
    private final Logger logger = LoggerFactory.getLogger(HzCfg0BomLineOfModelService.class);
    private final HzCfg0ModelDetailDao hzCfg0ModelDetailDao;


    @Autowired
    HzCfg0ModelService hzCfg0ModelService;

    @Autowired
    public HzCfg0BomLineOfModelService(
            HzCfg0BomLineOfModelDao hzCfg0BomLineOfModelDao,
            HzCfg0MainService hzCfg0MainService,
            IHzFcfgBomLvl1ListOperationService hzFcfgBomLvl1ListOperationService,
            HzProjectLibsService hzProjectLibsService,
            IHzVehicleService hzVehicleService,
            HzPlatformService platformService,
            HzBrandService hzBrandService, HzCfg0ModelDetailDao hzCfg0ModelDetailDao) {
        this.hzCfg0BomLineOfModelDao = hzCfg0BomLineOfModelDao;
        this.hzCfg0MainService = hzCfg0MainService;
        this.hzFcfgBomLvl1ListOperationService = hzFcfgBomLvl1ListOperationService;
        this.hzProjectLibsService = hzProjectLibsService;
        this.hzVehicleService = hzVehicleService;
        this.hzPlatformService = platformService;
        this.hzBrandService = hzBrandService;
        this.hzCfg0ModelDetailDao = hzCfg0ModelDetailDao;
    }

    /**
     * @param bdf 数模层ID
     * @return java.util.List<sql.pojo.cfg0.cfg0.HzCfg0BomLineOfModel>
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据数模层的puid获取到下级的所有bom行的配置信息，包含了车型模型的信息在内
     * Date: 2018/5/21 18:28
     */
    public List<HzCfg0BomLineOfModel> doLoadByModelMainId(String bdf) {
        return hzCfg0BomLineOfModelDao.selectByModelMainId(bdf);
    }

    /**
     * @param bdf 数模层puid
     * @return net.sf.json.JSONObject
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据数模层获取到bom的配置信息和车型模型信息
     * Date: 2018/5/21 18:27
     */
    public JSONObject parse(String bdf) {
        try {
            HzCfg0MainRecord mainRecord = hzCfg0MainService.doGetbyProjectPuid(bdf);
            List<HzCfg0BomLineOfModel> hzCfg0BomlineOfModels = doLoadByModelMainId(mainRecord.getPuid());

            HzProjectLibs project = hzProjectLibsService.doLoadProjectLibsById(bdf);
            HzVehicleRecord vehicle = hzVehicleService.doGetByPuid(project.getpProjectPertainToVehicle());
            HzPlatformRecord platform = hzPlatformService.doGetByPuid(vehicle.getpVehiclePertainToPlatform());
            HzBrandRecord brand = hzBrandService.doGetByPuid(platform.getpPertainToBrandPuid());
            List<HzCfg0ModelRecord> listOfModel = hzCfg0ModelService.doSelectByProjectPuid(bdf);
            if (hzCfg0BomlineOfModels == null || hzCfg0BomlineOfModels.size() <= 0)
                return null;
            JSONObject respond = new JSONObject();
            JSONArray _data = new JSONArray();
            JSONArray _model = new JSONArray();
            Set<String> optionName = new HashSet<>();
            Map<String, HzCfg0BomLineOfModel> mapToModel = new HashMap<>();
            Map<String, HzCfg0BomLineOfModel> modelWithBomLineMap = new HashMap();
            Map<String, List<HzCfg0BomLineOfModel>> mapModelHasCfg0 = new HashMap<>();

            hzCfg0BomlineOfModels.forEach((model) -> {
                mapToModel.put(model.getObjectName(), model);
                optionName.add(model.getpCfg0OptionValue());
                if (model.getpBomLineId() != null) {
                    modelWithBomLineMap.put(model.getpBomLineId(), model);
                }
            });
            //hzCfg0BomlineOfModels.stream().filter(model->model.getpBomLineId()!=null).forEach(model->modelWithBomLineMap.put(model.getpBomLineId(), model));
            //hzCfg0BomlineOfModels.stream().filter(model -> model.getpBomLineId() != null).map(model -> modelWithBomLineMap.put(model.getpBomLineId(), model));

            mapToModel.keySet().forEach((name) -> {
                List<HzCfg0BomLineOfModel> models = new ArrayList<>();
                hzCfg0BomlineOfModels.forEach((model) -> {
                    if (model.getObjectName().equals(name)) {
                        models.add(model);
                    }
                });
                mapModelHasCfg0.put(name, models);
            });
            modelWithBomLineMap.forEach((keyOfMap, withBomLine) -> {
                JSONObject data = new JSONObject();
                String owningUser = "";
                HzFcfgBomLvl1ListOperation operation = hzFcfgBomLvl1ListOperationService.doSelectByPrimaryKey(withBomLine.getpBomLineId());
                if (null != operation) {
                    data.put(HzCfg0BomLineOfModel.selfDesc[0], operation.getpOprationTypeName());
                } else {
                    data.put(HzCfg0BomLineOfModel.selfDesc[0], "新增");
                }
//                Object obj = SerializeUtil.unserialize(withBomLine.getLineBlock());
//                if (obj instanceof LinkedHashMap) {
//                    owningUser = (String) ((LinkedHashMap) obj).get("bl_rev_owning_user");
//                }
                owningUser = withBomLine.getCreator();
                data.put(HzCfg0BomLineOfModel.selfDesc[1], withBomLine.getpBomOfWhichDept());
                data.put(HzCfg0BomLineOfModel.selfDesc[2], withBomLine.getpBomLineId());
//                data.put(HzCfg0BomLineOfModel.selfDesc[2], withBomLine.getpBomLineId());
                data.put(HzCfg0BomLineOfModel.selfDesc[3], withBomLine.getpBomLineName());
                data.put(HzCfg0BomLineOfModel.selfDesc[4], withBomLine.getpH9featureenname() == null ? "" : withBomLine.getpH9featureenname());
                data.put(HzCfg0BomLineOfModel.selfDesc[5], owningUser == null ? "" : owningUser);
                data.put(HzCfg0BomLineOfModel.selfDesc[6], withBomLine.getpCfg0ObjectId());
                data.put(HzCfg0BomLineOfModel.selfDesc[7], withBomLine.getpCfg0Desc() != null ? withBomLine.getpCfg0Desc() : "");
                data.put(HzCfg0BomLineOfModel.selfDesc[8], withBomLine.getpCfg0Desc() != null ? withBomLine.getpCfg0Desc() : "");
                mapModelHasCfg0.forEach((key, value) -> {
                    for (HzCfg0BomLineOfModel model : value) {
                        if (keyOfMap.equals(model.getpBomLineId())) {
                            if (model.getpParseLogicValue() == 1) {
                                data.put(key, "●");
                            } else {
                                data.put(key, "○");
                            }
                            break;
                        } else {
                            data.put(key, "-");
                        }
                    }
                });
                _data.add(data);
            });

            mapToModel.forEach((key, value) -> {
                //装载模型信息
                HzCfg0ModelDetail detail = new HzCfg0ModelDetail();
                detail.setpModelPuid(value.getModelPuid());
                JSONObject object = new JSONObject();
                object.put("brand", brand.getpBrandName());
                object.put("platform", platform.getpPlatformName());
                object.put("vehicle", vehicle.getpVehicleName());
                object.put("key", key);
                object.put("hide", value.getModelPuid());
                detail = hzCfg0ModelDetailDao.selectByModelId(detail);

                if (detail == null) {
                    object.put("pModelShape", "");
                    object.put("pModelAnnouncement", "");
                    object.put("pModelCfgDesc", "");
                    object.put("pModelCfgMng", "");
                } else {
                    object.put("pModelShape", detail.getpModelShape() == null ? "" : detail.getpModelShape());
                    object.put("pModelAnnouncement", detail.getpModelAnnouncement() == null ? "" : detail.getpModelAnnouncement());
                    object.put("pModelCfgDesc", detail.getpModelCfgDesc() == null ? "" : detail.getpModelCfgDesc());
                    object.put("pModelCfgMng", detail.getpModelCfgMng() == null ? "" : detail.getpModelCfgMng());
                }
                _model.add(object);
            });
            respond.put("data", _data);
            respond.put("model", _model);
            respond.put("modelSize",mapToModel.size());
            respond.put("peculiarity",modelWithBomLineMap.size());
            return respond;
        } catch (Exception e) {
            logger.error("发生错误", e);
            return null;
        }
    }
}
