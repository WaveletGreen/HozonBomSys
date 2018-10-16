/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller.vwo;

import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVWOManagerService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.cfg0.HzCfg0Record;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/9 11:13
 * @Modified By:
 */
@Controller
@RequestMapping("/vwoProcess")
public class HzVWOProcessController {

    @Autowired
    IHzVWOManagerService iHzVWOManagerService;

    /**
     * 特性进入VWO流程Controller
     *
     * @param projectUid 项目UID
     * @param beans      特性值对象
     * @return
     */
    @RequestMapping("/featureGetIntoVWO")
    @ResponseBody
    public JSONObject featureGetIntoVWO(@RequestParam String projectUid, @RequestBody List<HzCfg0Record> beans) {
        return iHzVWOManagerService.featureGetIntoVWO(projectUid, beans);
    }

}
