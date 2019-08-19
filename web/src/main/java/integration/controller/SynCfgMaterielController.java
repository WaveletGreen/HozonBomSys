/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.controller;

import integration.service.integrate.impl.SynMaterielCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Controller
@RequestMapping("/cfgMateriel")
public class SynCfgMaterielController extends ExtraIntegrateController {
    @Autowired
    SynMaterielCfgService synMaterielCfgService;

//    @RequestMapping("/synMateriel")
//    public String modPage(@RequestParam String puid,Model model) {
//        HzMaterielCfgBean bean = new HzMaterielCfgBean();
//        bean.setId(puid);
//        JSONObject result = synMaterielCfgService.addMaterielCfg(Collections.singletonList(bean));
//        addToModel(result, model);
//        return "stage/templateOfIntegrate";
//    }
//    @RequestMapping("/synMaterielDelete")
//    public String delete(@RequestParam String puid,Model model) {
//        HzMaterielCfgBean bean = new HzMaterielCfgBean();
//        bean.setId(puid);
//        JSONObject result = synMaterielCfgService.deleteMaterielCfg(Collections.singletonList(bean));
//        addToModel(result, model);
//        return "stage/templateOfIntegrate";
//    }
}
