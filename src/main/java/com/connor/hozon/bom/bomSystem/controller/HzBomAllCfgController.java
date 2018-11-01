/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzBomAllCfgService;
import com.connor.hozon.bom.interaction.iservice.IHzConfigBomColorService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.fullCfg.HzFullCfgMain;
import sql.pojo.cfg.main.HzCfg0MainRecord;
import sql.pojo.cfg.model.HzCfg0ModelDetail;
import sql.pojo.interaction.HzConfigBomColorBean;

import java.util.List;
import java.util.Map;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;


/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 全配置BOM一级清单
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
@Controller
@RequestMapping("/bomAllCfg")
public class HzBomAllCfgController {

    @Autowired
    private HzBomAllCfgService hzBomAllCfgService;

    @Autowired
    IHzConfigBomColorService iHzConfigBomColorService;

//    /**
//     * 失效
//     * @param data
//     * @return
//     */
//    @RequestMapping("/saveBom")
//    public JSONObject saveBom(@RequestParam Map<String, String> data) {
//        return new JSONObject();
//    }

    @RequestMapping("/addVehicleModelPage2")
    public String addVehicleModelPage2(@RequestParam String projectPuid, Model model) {
        if (checkString(projectPuid)) {
            HzCfg0ModelDetail detail=new HzCfg0ModelDetail();
            HzCfg0MainRecord mainRecord = new HzCfg0MainRecord();
            hzBomAllCfgService.initAddingPageParams(projectPuid, detail,mainRecord);
            model.addAttribute("hzCfg0ModelDetail", detail);
            model.addAttribute("cfgmain", mainRecord);
            model.addAttribute("action", "./materiel/addVehicleModel2");
            return "bom/addModel2";
        } else {
            model.addAttribute("msg", "请选择项目再操作");
            return "errorWithEntity";
        }
    }

    /**
     * 全配置BOM一级清单页面初始化
     * @param bdf   项目id
     * @return
     */
    @RequestMapping("/loadCfg0BomLineOfModel")
    @ResponseBody
    public JSONObject loadCfg0BomLineOfModel(@RequestParam String bdf) {
        return hzBomAllCfgService.parse(bdf);
    }

    /**
     * 保存2Y层对应的各数据
     * @param bomLinePuid   2Y层id
     * @param cfgPuid       特性id
     * @param colorPart     是否颜色件
     * @param msgVal        备注
     * @return
     */
    @RequestMapping("/saveOneRow")
    @ResponseBody
    public JSONObject saveOneRow(String bomLinePuid, String cfgPuid, Integer colorPart, String msgVal) {
//        List<HzConfigBomColorBean> beans = iHzConfigBomColorService.doSelectBy2YUidWithProject(bomLinePuid, "1c128c60-84a2-4076-9b1c-f7093e56e4df");
        return hzBomAllCfgService.saveOneRow(bomLinePuid, cfgPuid, colorPart, msgVal);
    }

    /**
     * 保存所有打点图
     * @param data      所有打点图信息<车辆模型id<特性id，打点图信息>>
     * @return
     */
    @RequestMapping("/savePoint")
    @ResponseBody
    public JSONObject savePoint(@RequestBody Map<String, Map<String, String>> data) {
        return hzBomAllCfgService.savePoint(data);
    }

    /**
     * 删除车辆模型
     * @param modelId   车辆模型id
     * @return
     */
    @RequestMapping("/deleteModel")
    @ResponseBody
    public JSONObject deleteModel(@RequestParam String modelId) {
        return hzBomAllCfgService.deleteModel(modelId);
    }

    /**
     * 获取阶段弹窗页面
     *
     * @return
     * @Autor Fancyears
     */
    @RequestMapping("setStageOrVersionPage")
    public String setStagePage(@RequestParam String projectUid, String setName, Model model) {
        if (!checkString(projectUid)) {
            model.addAttribute("msg", "请选择一个项目进行操作");
            return "errorWithEntity";
        }
        HzFullCfgMain fullCfgMain = hzBomAllCfgService.getFullCfgMain(projectUid);
        if (fullCfgMain == null) {
            model.addAttribute("msg", "该项目下没有全配置BOM一级清单表，请先添加2Y层和特性值再进行操作");
            return "errorWithEntity";
        }
        String releaseDate = fullCfgMain.getEffectiveDate() == null ? "" : DateStringHelper.dateToString(fullCfgMain.getEffectiveDate());
//        model.addAttribute("entity", fullCfgMain);
        model.addAttribute("releaseDate", releaseDate);
        if (setName != null && "version".equals(setName)) {
            Integer stage = fullCfgMain.getStage();
            String stageStr = HzFullCfgMain.parseStage(stage);
            model.addAttribute("entity", fullCfgMain);
            model.addAttribute("stageStr", stageStr);
            model.addAttribute("action", "./bomAllCfg/setVersion");
            return "bom/setVersionPage";
        } else if (setName != null && "stage".equals(setName)) {
            model.addAttribute("entity", fullCfgMain);
            model.addAttribute("action", "./bomAllCfg/setStage");
            return "bom/setStagePage";
        }
        return "";
    }

    /**
     * 保存版本数据，对当前全配置BOM一级清单进行升大版本操作
     *
     * @param params
     * @return
     */
    @RequestMapping("setVersion")
    @ResponseBody
    public JSONObject setVersion(@RequestBody Map<String, String> params) {
        return hzBomAllCfgService.setVersion(params);
    }

    /**
     * 保存阶段数据
     *
     * @param params
     * @return
     */
    @RequestMapping("setStage")
    @ResponseBody
    public JSONObject setStage(@RequestBody Map<String, String> params) {
        return hzBomAllCfgService.setStage(params);
    }

    @RequestMapping("query2YCfg")
    @ResponseBody
    public JSONObject query2YCfg(@RequestParam String projectPuid, @RequestParam String bomLineId) {
        return hzBomAllCfgService.query2YCfg(projectPuid, bomLineId);
    }

    /**
     * 升小版本，为全配置BOM一级清单进行当前版本+0.1小版本状态
     * 仅仅是升级小版本，不是升级为大版本，升级大版本{@link HzBomAllCfgController#}
     *
     * @param projectUid    项目id
     * @return
     */
    @RequestMapping(value = "promote", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject promote(@RequestParam String projectUid) {
        return hzBomAllCfgService.promote(projectUid);
    }


/**
     * 保存2Y层对应的打点图
     * @param dataMap
     * @return
     */@RequestMapping(value = "saveBomLinePiont", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveBomLinePiont(@RequestBody Map<String, Map<String, String>> dataMap) {
        return hzBomAllCfgService.saveBomLinePiont(dataMap);
    }
}
