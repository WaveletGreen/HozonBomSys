/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import com.connor.hozon.bom.bomSystem.dto.task.HzTaskPostDto;
import com.connor.hozon.bom.bomSystem.dto.task.TaskReceivedDto;
import com.connor.hozon.bom.bomSystem.iservice.task.IHzTaskService;
import com.connor.hozon.bom.bomSystem.service.vwo.HzVwoInfoService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.Tree;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.service.TreeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.vwo.HzVwoInfo;
import sql.pojo.task.HzTasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 人员任务controller，依据当前登录用户，查询用户需要执行的任务集合，并返回前端，由JS进行动态绘制需要执行的任务
 * @Date: Created in 2018/10/19 16:47
 * @Modified By:
 */
@Controller
@RequestMapping("task")
public class HzTaskController {
    /**任务服务层*/
    @Autowired
    IHzTaskService iHzTaskService;
    /***前端网址树*/
    @Autowired
    TreeService treeService;
    /**VWO变更表单服务层*/
    @Autowired
    HzVwoInfoService hzVwoInfoService;

    /***
     * 保存任务，已废除，不再使用该独立方法作为保存任务的作用
     * @param dto 接收的任务对象
     * @return 不应当用该返回值作为保存任务成功的标志，因为方法已废除
     */
    @Deprecated
    @RequestMapping("saveTask")
    public boolean saveTask(@RequestBody TaskReceivedDto dto) {
        iHzTaskService.saveTask(dto);
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
        User user = UserInfo.getUser();
        JSONObject result = new JSONObject();
        List<HzTaskPostDto> dtoList = new ArrayList<>();
        List<HzTasks> tasks = iHzTaskService.doSelectUserExecutingTasks(Long.valueOf(user.getId()));
        Tree tree = new Tree();
        if (tasks != null && !tasks.isEmpty()) {
            Map<Integer, Tree> maoOfForm = new HashMap<>();
            Tree dbTree = null;
            for (int i = 0; i < tasks.size(); i++) {
                HzTasks task = tasks.get(i);
                tree.setId(task.getTaskFormId());
                //缓存树地址
                if (maoOfForm.containsKey(tree.getId())) {
                    dbTree = maoOfForm.get(tree.getId());
                } else {
                    dbTree = treeService.get(tree);
                }
                HzTaskPostDto dto = new HzTaskPostDto();
                if (dbTree != null) {
                    //设置URL
                    dto.setUrl(dbTree.getUrl());
                    //设置表单ID
                    dto.setId(dbTree.getId());
                }
                switch (task.getTaskFormType()) {
                    case 1:
                        HzVwoInfo info = hzVwoInfoService.doSelectByPrimaryKey(task.getTaskTargetId());
                        dto.setText(info.getVwoNum());
                        dto.setTargetId(info.getId());
                        dto.setTargetName(info.getVwoNum() + "表单");
                        dto.setTargetType(task.getTaskTargetType());
                        dto.setFormType(task.getTaskFormType());
                        break;
                    //预留给EWO表单用
                    case 2:
                        break;
                    //预留该MWO表单用
                    case 3:
                        break;
                    default:
                        break;
                }
                dto.setReserve("");
                dto.setReserve2("");
                dto.setReserve3("");
                dto.setReserve4("");
                dto.setReserve5("");
                dto.setReserve6("");
                dto.setReserve7("");
                dto.setReserve8("");
                dto.setReserve9("");
                dto.setReserve10("");
                dtoList.add(dto);
            }
        }
        result.put("data", dtoList);
        return result;
    }
}
