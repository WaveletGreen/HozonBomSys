/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelDetail;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import cn.net.connor.hozon.services.service.configuration.fullConfigSheet.impl.HzCfg0ModelServiceImpl;
import cn.net.connor.hozon.services.service.main.HzMainConfigService;
import cn.net.connor.hozon.services.service.main.ProjectHelperService;
import com.connor.hozon.bom.bomSystem.service.model.HzCfg0ModelRecordService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static cn.net.connor.hozon.common.util.StringHelper.checkString;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 基本车型controller
 * 每一个车型模型都与主配置关联，主配置关联项目，因此每一个车型模型都与项目间接关联，从而获取到项目数据
 * 配置管理controller的所有返回消息字段key都是msg
 * 配置管理controller的所有返回成功标志字段key都是status
 * 如发现不一致需要特殊处理
 * 已完成注释
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
@Controller
@RequestMapping("/model")
public class HzCfg0ModelController {
    /*** 模型的详细信息*/
    @Autowired
    private HzCfg0ModelServiceImpl hzCfg0ModelServiceImpl;
    /*** 数据库中的车型模型，没有详细信息*/
    @Autowired
    private HzCfg0ModelRecordService hzCfg0modelRecordService;
    /*** 配置主数据服务层*/
    @Autowired
    private HzMainConfigService hzMainConfigService;
    /***项目助手*/
    @Autowired
    private ProjectHelperService projectHelperService;

    /**
     * 保存基本车型的详情数据
     * 当{link {@link HzCfg0ModelDetail#pModelVersion}数据与基本车型
     * {@link HzCfg0ModelRecord#objectName}不一致时，将设置以详情数据为基本参考强制设置其为一致
     *
     * @param detail 基本车型详情数据(非主数据)
     * @return 操作成功与否状态和消息
     */
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
        HzCfg0ModelDetail fromDBDetail = hzCfg0ModelServiceImpl.getOneByModelId(detail);
        if (fromDBDetail != null) {
            detail.setpModelPuid(fromDBDetail.getpModelPuid());
            detail.setPuid(fromDBDetail.getPuid());
            isSuccess = hzCfg0ModelServiceImpl.doUpdateOne(detail);
            sb.append("更新");
        } else {
            detail.setPuid(UUID.randomUUID().toString());
            isSuccess = hzCfg0ModelServiceImpl.doInsert(detail);
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

    /**
     * 修改车型模型数据
     * <p>
     * 先根据传入的车型模型主键查询车型模型的详情数据和项目树信息，并将数据绑定到页面中与页面一起返回前端
     * 若找不到项目树结构则返回errorWithEntity页面
     * 若找不到车型详情数据，同样返回errorWithEntity页面
     *
     * @param pModelPuid 车型模型主数据的主键
     * @param model      不用传
     * @return 错误页面/修改页面
     */
    @RequestMapping(value = "/modModel", method = RequestMethod.GET)
    public String modifyModel(@RequestParam String pModelPuid, Model model) {
        HzCfg0ModelDetail fromDBDetail = new HzCfg0ModelDetail();
        fromDBDetail.setpModelPuid(pModelPuid);
        fromDBDetail = hzCfg0ModelServiceImpl.getOneByModelId2(fromDBDetail);
        HzCfg0ModelRecord hzCfg0ModelRecord = hzCfg0modelRecordService.doGetById(pModelPuid);
        HzMainConfig hzMainConfig = hzMainConfigService.doGetByPrimaryKey(hzCfg0ModelRecord.getpCfg0ModelOfMainRecord());

        projectHelperService.doGetProjectTreeByProjectId(hzMainConfig.getProjectId());
        if (projectHelperService.getProject() == null ||
                projectHelperService.getVehicle() == null ||
                projectHelperService.getPlatform() == null ||
                projectHelperService.getBrand() == null
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
                fromDBDetail.setpModelBrand(projectHelperService.getBrand().getpBrandName());
                fromDBDetail.setpModelPlatform(projectHelperService.getPlatform().getpPlatformName());
                fromDBDetail.setpModelMod(projectHelperService.getVehicle().getpVehicleName());
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

}
