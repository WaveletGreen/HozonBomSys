/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.controller;

import integration.service.integrate.impl.SynConfigurableMaterialServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Controller
@RequestMapping("cfgMateriel")
public class SynConfigurableMaterialController extends ExtraIntegrateController {

    @Autowired
    private SynConfigurableMaterialServiceImpl synConfigurableMaterialService;

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
