/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import com.connor.hozon.bom.bomSystem.dto.relevance.HzRelevanceQueryDTO;
import com.connor.hozon.bom.bomSystem.service.relevance.HzRelevanceService2;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/relevance")
public class HzRelevanceController2 {

    @Autowired
    private HzRelevanceService2 hzRelevanceService2;

    @RequestMapping("/addRelevance")
    @ResponseBody
    public JSONObject addRelevance(@RequestParam String projectPuid) {
        return hzRelevanceService2.addRelevance(projectPuid);
    }

    @RequestMapping(value = "/queryRelevance", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject queryRelevance(HzRelevanceQueryDTO dto) {
        return hzRelevanceService2.queryRelevance(dto);
    }
}
