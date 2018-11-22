/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller.integrate;

import com.connor.hozon.bom.bomSystem.service.integrate.SynConfigurableMaterialService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
@RequestMapping("cfgMateriel")
public class SynConfigurableMaterialController extends ExtraIntegrate {

    @Autowired
    private SynConfigurableMaterialService synConfigurableMaterialService;

    @RequestMapping("/addConfigurableMaterial")
    public String add(String[] puids, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid, Model model) {
        JSONObject result = new JSONObject();
        result = synConfigurableMaterialService.addConfigurableMaterial(puids, cfg0MainPuids, modeBasiceDetails, projectPuid);
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }

    @RequestMapping("/deleteConfigurableMaterial")
    public String delete(String[] puids, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid, Model model) {
        JSONObject result = new JSONObject();
        result = synConfigurableMaterialService.deleteConfigurableMaterial(puids, cfg0MainPuids, modeBasiceDetails, projectPuid);
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }


}
