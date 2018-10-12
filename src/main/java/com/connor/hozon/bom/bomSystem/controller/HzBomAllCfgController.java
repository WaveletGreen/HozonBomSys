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
import sql.pojo.interaction.HzConfigBomColorBean;

import java.util.List;
import java.util.Map;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

@Controller
@RequestMapping("/bomAllCfg")
public class HzBomAllCfgController {

    @Autowired
    private HzBomAllCfgService hzBomAllCfgService;

    @Autowired
    IHzConfigBomColorService iHzConfigBomColorService;

    @RequestMapping("/saveBom")
    public JSONObject saveBom(@RequestParam Map<String, String> data) {
        return new JSONObject();
    }

//    @RequestMapping("/savePoint")
//    public JSONObject savePoint(@RequestBody Map<String, Map<String,String>> data){
//        return new JSONObject();
//    }

    @RequestMapping("/loadCfg0BomLineOfModel")
    @ResponseBody
    public JSONObject loadCfg0BomLineOfModel(@RequestParam String bdf) {
        return hzBomAllCfgService.parse(bdf);
    }

    @RequestMapping("/saveOneRow")
    @ResponseBody
    public JSONObject saveOneRow(String bomLinePuid, String cfgPuid, Integer colorPart, String msgVal) {
        List<HzConfigBomColorBean> beans = iHzConfigBomColorService.doSelectBy2YUidWithProject(bomLinePuid, "1c128c60-84a2-4076-9b1c-f7093e56e4df");
        return hzBomAllCfgService.saveOneRow(bomLinePuid, cfgPuid, colorPart, msgVal);
    }

    @RequestMapping("/savePoint")
    @ResponseBody
    public JSONObject savePoint(@RequestBody Map<String, Map<String, String>> data) {
        return hzBomAllCfgService.savePoint(data);
    }

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
     * 保存版本数据
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
     * 升小版本
     *
     * @param projectUid
     * @return
     */
    @RequestMapping(value = "promote", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject promote(@RequestParam String projectUid) {
        return hzBomAllCfgService.promote(projectUid);
    }


@RequestMapping(value = "saveBomLinePiont", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveBomLinePiont(@RequestBody Map<String, Map<String, String>> dataMap) {
        return hzBomAllCfgService.saveBomLinePiont(dataMap);
    }
}
