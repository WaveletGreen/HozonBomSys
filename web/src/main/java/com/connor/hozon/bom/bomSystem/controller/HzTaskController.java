/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import cn.net.connor.hozon.services.request.task.TaskRequestDTO;
import cn.net.connor.hozon.services.service.task.HzTaskService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 人员任务controller，依据当前登录用户，查询用户需要执行的任务集合，并返回前端，由JS进行动态绘制需要执行的任务
 * 配置管理controller的所有返回消息字段key都是msg
 * 配置管理controller的所有返回成功标志字段key都是status
 * 如发现不一致需要特殊处理
 * 已完成注释
 * @Date: Created in 2018/10/19 16:47
 * @Modified By:
 */
@Controller
@RequestMapping("task")
public class HzTaskController {
    /**
     * 任务服务层
     */
    @Autowired
    HzTaskService hzTaskService;

    /***
     * 保存任务，已废除，不再使用该独立方法作为保存任务的作用
     * @param dto 接收的任务对象
     * @return 不应当用该返回值作为保存任务成功的标志，因为方法已废除
     */
    @Deprecated
    @RequestMapping("saveTask")
    public boolean saveTask(@RequestBody TaskRequestDTO dto) {
        hzTaskService.saveTask(dto);
        return false;
    }

    /**
     * 加载个人任务，依据当前登录用户，从任务表中查询出该用户关联的需要执行的任务(任务状态为1)
     *
     * @return 一组当前用户需要执行的的任务集合，以JSON数组回传前端，最终由JS动态绘制到右上角任务栏中
     */
    @RequestMapping(value = "/loadTasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject loadTasks() {
        return hzTaskService.loadUserTask();
    }
}
