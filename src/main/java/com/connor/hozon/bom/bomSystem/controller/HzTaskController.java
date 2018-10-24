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
 * @Description:
 * @Date: Created in 2018/10/19 16:47
 * @Modified By:
 */
@Controller
@RequestMapping("task")
public class HzTaskController {
    @Autowired
    IHzTaskService iHzTaskService;
    @Autowired
    TreeService treeService;
    @Autowired
    HzVwoInfoService hzVwoInfoService;

    @RequestMapping("saveTask")
    public boolean saveTask(@RequestBody TaskReceivedDto dto) {
        iHzTaskService.saveTask(dto);
        return false;
    }

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
                        dto.setTargetName(info.getVwoNum() + "VWO表单");
                        dto.setTargetType(task.getTaskTargetType());
                        dto.setFormType(task.getTaskFormType());
                        break;
                    case 2:
                        break;
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
//        for (int i = 0; i < 10; i++) {
//            HzTaskPostDto dto = new HzTaskPostDto();
//            dto.setUrl("vwoFormList");
//            dto.setId("81");
//            dto.setText("VC2018000"+i);
//            dto.setTargetId("434");
//            dto.setTargetName("VC2018000" + i+"VWO表单");
//            dto.setTargetType(1);
//            dto.setFormType(1);
//            dto.setReserve("");
//            dto.setReserve2("");
//            dto.setReserve3("");
//            dto.setReserve4("");
//            dto.setReserve5("");
//            dto.setReserve6("");
//            dto.setReserve7("");
//            dto.setReserve8("");
//            dto.setReserve9("");
//            dto.setReserve10("");
//            dtoList.add(dto);
//        }
        result.put("data", dtoList);
        return result;
    }
}
