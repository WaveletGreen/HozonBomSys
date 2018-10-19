/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import com.connor.hozon.bom.bomSystem.dto.task.TaskReceivedDto;
import com.connor.hozon.bom.bomSystem.iservice.task.IHzTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/19 16:47
 * @Modified By:
 */
@Controller
@RequestMapping("task")
public class HzTaskController {
    @Autowired
    IHzTaskService iHzTaskService;

    @RequestMapping("saveTask")
    public boolean saveTask(@RequestBody TaskReceivedDto dto) {
        iHzTaskService.saveTask(dto);
        return false;
    }
}
