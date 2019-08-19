/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller.integrate;

import integration.service.integrate.impl.SynProcessRouteService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Controller
@RequestMapping("/work")
public class SynProcessRouteController extends ExtraIntegrate {

    @Autowired
    SynProcessRouteService synProcessRouteService;
    @RequestMapping("/process/submit")
    public String submit( String[] materielIds, String projectId, Model model){
        materielIds = new String[]{"e390a8ef-b621-4288-9e98-d6621f45f7ac"};
        JSONObject result =  synProcessRouteService.addRouting(materielIds, projectId);
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }
    @RequestMapping("/process/updata")
    public String updata( String[] materielIds, String projectId, Model model){
        JSONObject result =  synProcessRouteService.updateRouting(materielIds, projectId);
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }
    @RequestMapping("/process/delete1")
    public String delete( String[] materielIds, String projectId, Model model){
        JSONObject result =  synProcessRouteService.deleteRouting(materielIds, projectId);
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }

}
