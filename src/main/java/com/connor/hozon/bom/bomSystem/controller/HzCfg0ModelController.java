/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import com.connor.hozon.bom.bomSystem.helper.ProjectHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0ModelRecordService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0ModelService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.main.HzCfg0MainRecord;
import sql.pojo.cfg.model.HzCfg0ModelDetail;
import sql.pojo.cfg.model.HzCfg0ModelRecord;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

@Controller
@RequestMapping("/model")
public class HzCfg0ModelController {
    /**
     * 模型的详细信息
     */
    @Autowired
    HzCfg0ModelService hzCfg0ModelService;
    /**
     * 数据库中的车型模型，没有详细信息
     */
    @Autowired
    HzCfg0ModelRecordService hzCfg0modelRecordService;
    /**
     * 配置主数据服务层
     */
    @Autowired
    HzCfg0MainService cfg0MainService;

    @Autowired
    ProjectHelper projectHelper;

    @RequestMapping(value = "/saveModelData", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveModelData(@RequestBody HzCfg0ModelDetail detail) {
        JSONObject result = new JSONObject();
        StringBuilder sb = new StringBuilder();
        boolean isSuccess;
        if (detail == null || (detail.getpModelPuid() == null || "".equals(detail.getpModelPuid()))) {
            result.put("state", false);
            result.put("msg", "没有找到模型信息，请联系管理员");
            return result;
        }
        HzCfg0ModelDetail fromDBDetail = hzCfg0ModelService.getOneByModelId(detail);
        if (fromDBDetail != null) {
            detail.setpModelPuid(fromDBDetail.getpModelPuid());
            detail.setPuid(fromDBDetail.getPuid());
            isSuccess = hzCfg0ModelService.doUpdateOne(detail);
            sb.append("更新");
        } else {
            detail.setPuid(UUID.randomUUID().toString());
            isSuccess = hzCfg0ModelService.doInsert(detail);
            sb.append("数据");
        }
        if (isSuccess) {
            HzCfg0ModelRecord modelRecord = hzCfg0modelRecordService.doGetById(detail.getpModelPuid());
            //更新车型模型名，如果有修改
            modelRecord.setObjectDesc(detail.getObjectDesc());
            modelRecord.setpCfg0ModelBasicDetail(detail.getpModelCfgMng());
            if (!checkString(detail.getObjectName()) || !detail.getpModelVersion().equals(detail.getObjectName())) {
                modelRecord.setObjectName(detail.getpModelVersion());
            } else {
                modelRecord.setObjectName(detail.getObjectName());
            }
            isSuccess = hzCfg0modelRecordService.doUpdateModelName(modelRecord);

        }
        result.put("msg", sb + detail.getpModelVersion() + "成功");
        result.put("state", isSuccess);
        return result;
    }

    @RequestMapping(value = "/modModel", method = RequestMethod.GET)
    public String modifyModel(@RequestParam String pModelPuid, Model model) {
        HzCfg0ModelDetail fromDBDetail = new HzCfg0ModelDetail();
        fromDBDetail.setpModelPuid(pModelPuid);
        fromDBDetail = hzCfg0ModelService.getOneByModelId2(fromDBDetail);
        HzCfg0ModelRecord hzCfg0ModelRecord = hzCfg0modelRecordService.doGetById(pModelPuid);
        HzCfg0MainRecord hzCfg0MainRecord = cfg0MainService.doGetByPrimaryKey(hzCfg0ModelRecord.getpCfg0ModelOfMainRecord());

        projectHelper.doGetProjectTreeByProjectId(hzCfg0MainRecord.getpCfg0OfWhichProjectPuid());
        if (projectHelper.getProject() == null ||
                projectHelper.getVehicle() == null ||
                projectHelper.getPlatform() == null ||
                projectHelper.getBrand() == null
                ) {
            model.addAttribute("msg", "无法查询项目结构，请确保选中项目或保证车型模型在项目中");
            return "errorWithEntity";
        }
        if (fromDBDetail == null) {
            HzCfg0ModelRecord record = hzCfg0modelRecordService.doGetById(pModelPuid);
            if (record == null) {
                return "error";
            } else {
                fromDBDetail = new HzCfg0ModelDetail();
                fromDBDetail.setpModelPuid(record.getPuid());
                fromDBDetail.setpModelBrand(projectHelper.getBrand().getpBrandName());
                fromDBDetail.setpModelPlatform(projectHelper.getPlatform().getpPlatformName());
                fromDBDetail.setpModelMod(projectHelper.getVehicle().getpVehicleName());
                fromDBDetail.setpModelVersion(record.getObjectName());
                fromDBDetail.setpModelDesc(record.getObjectDesc());
                fromDBDetail.setpModelPuid(hzCfg0ModelRecord.getPuid());
                model.addAttribute("entity", fromDBDetail);
            }
        } else {
            model.addAttribute("entity", fromDBDetail);
        }
        return "bom/modifyPage";
    }


    private static void saveModelDetailToDB(@NotNull HzCfg0ModelController controller) {
        HzCfg0ModelDetail detail = new HzCfg0ModelDetail();
        detail.setpModelPuid("046adedc-09b2-43ca-a49c-a99d47c9fa3e");
        detail.setpModelName("setpModelName");
        detail.setpModelDesc("setpModelDesc");
        detail.setpModelSaleArea("setpModelSaleArea");
        detail.setpModelBrand("setpModelBrand");
        detail.setpModelVehicle("setpModelVehicle");
        detail.setpModelPlatform("setpModelPlatform");
        detail.setpModelMod("setpModelMod");
        detail.setpModelAnnual("setpModelAnnual");
        detail.setpModelVersion("setpModelVersion");
        detail.setpModelTransform("setpModelTransform");
        detail.setpModelDriverPosition("setpModelDriverPosition");
        detail.setpModelMembers("setpModelMembers");
        detail.setpModelShape("setpModelShape");
        detail.setpModelAnnouncement("setpModelAnnouncement");
        detail.setpModelPowers("setpModelPowers");
        detail.setpModelCfgVersion("setpModelCfgVersion");
        detail.setpModelCfgDesc("setpModelCfgDesc");
        detail.setpModelTrailNum("setpModelTrailNum");
        detail.setpModelGoodsNum("setpModelGoodsNum");
        controller.saveModelData(detail);
        System.out.println();
    }

}
