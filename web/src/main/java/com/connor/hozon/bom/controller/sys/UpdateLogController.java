/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.controller.sys;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/11
 * Time: 13:31
 */
@Controller
@RequestMapping("updateLog")
public class UpdateLogController {

    @RequestMapping(value = "getUpdateLog",method = RequestMethod.GET)
    public String getUpdateLog(@RequestParam("version") String version, Model model){
        model.addAttribute("version",version);
        return "updateLog/"+version;
    }
}
