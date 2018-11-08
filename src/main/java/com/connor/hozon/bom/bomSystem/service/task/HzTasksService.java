/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.task;

import com.connor.hozon.bom.bomSystem.dao.task.HzTasksDao;
import com.connor.hozon.bom.bomSystem.dto.task.HzTaskPostDto;
import com.connor.hozon.bom.bomSystem.dto.task.TaskReceivedDto;
import com.connor.hozon.bom.bomSystem.iservice.task.IHzTaskService;
import com.connor.hozon.bom.bomSystem.option.TaskOptions;
import com.connor.hozon.bom.bomSystem.service.vwo.HzVwoInfoService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.Tree;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.service.TreeService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.vwo.HzVwoInfo;
import sql.pojo.task.HzTasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/10/19 16:49
 * @Modified By:
 */
@Service("hzTaskService")
public class HzTasksService implements IHzTaskService {
    /***任务dao层*/
    @Autowired
    HzTasksDao hzTasksDao;
    /***前端网址树服务层*/
    @Autowired
    TreeService treeService;
    /**
     * VWO变更表单服务层
     */
    @Autowired
    HzVwoInfoService hzVwoInfoService;


    private final static Logger LOGGER = LoggerFactory.getLogger(HzTasksService.class);

    /**
     * 发起流程之后，保存task
     *
     * @return
     */
    @Override
    public boolean saveTask(TaskReceivedDto dto) {
        System.out.println("保存任务");
        return false;
    }

    /**
     * 用户跟踪的任务
     *
     * @param userId
     * @return
     */
    @Override
    public List<HzTasks> doSelectUserTrackingTasks(Long userId) {
        HzTasks task = new HzTasks();
        task.setTaskStatus(TaskOptions.TASK_STATUS_TRACKING);
        task.setTaskUserId(userId);
        return hzTasksDao.selectUserTasks(task);
    }

    /**
     * 用户需要执行的任务
     *
     * @param userId
     * @return
     */
    @Override
    public List<HzTasks> doSelectUserExecutingTasks(Long userId) {
        HzTasks task = new HzTasks();
        task.setTaskStatus(TaskOptions.TASK_STATUS_EXECUTING);
        task.setTaskUserId(userId);
        return hzTasksDao.selectUserTasks(task);
    }

    /**
     * 用户已完成的任务
     *
     * @param userId
     * @return
     */
    @Override
    public List<HzTasks> doSelectUserFinishTasks(Long userId) {
        HzTasks task = new HzTasks();
        task.setTaskStatus(TaskOptions.TASK_STATUS_FINISHED);
        task.setTaskUserId(userId);
        return hzTasksDao.selectUserTasks(task);
    }

    @Override
    public List<HzTasks> doSelectUserTargetTaskByType(Integer taskFormType, Integer taskTargetType, Long taskTargetId, Long taskUserId, Integer status) {
        HzTasks task = new HzTasks();
        task.setTaskFormType(taskFormType);
        task.setTaskTargetType(taskTargetType);
        task.setTaskTargetId(taskTargetId);
        task.setTaskUserId(taskUserId);
        task.setTaskStatus(status);
        return hzTasksDao.selectUserTargetTaskByType(task);
    }

    /**
     * 根据VWO主键和用户主键，获取到当前VWO变更表单该用户的任务
     *
     * @param vwoId  VWO ID
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<HzTasks> doSelectUserVWOTaskByIds(Long vwoId, Long userId, Integer targetType) {
        return doSelectUserTargetTaskByType(TaskOptions.FORM_TYPE_VWO, targetType, vwoId, userId, null);
    }

    /**
     * 批量插入任务
     *
     * @param list
     * @return
     */
    @Override
    public boolean doInsertByBatch(List<HzTasks> list) {
        return hzTasksDao.insertByBatch(list) > 0 ? true : false;
    }


    @Override
    public boolean doInsert(HzTasks task) {
        return hzTasksDao.insert(task) > 0 ? true : false;
    }

    @Override
    public boolean doUpdateByPrimaryKeySelective(HzTasks task) {
        return hzTasksDao.updateByPrimaryKeySelective(task) > 0 ? true : false;
    }

    @Override
    public boolean doStopTask(int formType, Integer targetType, Long targetId) {
        HzTasks task = new HzTasks();
        task.setTaskFormType(formType);
        task.setTaskTargetType(targetType);
        task.setTaskTargetId(targetId);
        //当前任务先完成
        task.setTaskStatus(TaskOptions.TASK_STATUS_FINISHED);
        task.setAntherStatus(TaskOptions.TASK_STATUS_EXECUTING);

        if (hzTasksDao.updateTargetStatus(task) <= 0) {
            LOGGER.error("更新当前任务为完成状态失败:formType=" + formType + "\ttargetType=" + targetType + "\ttargetId=" + targetId);
            return false;
        }
        task.setTaskStatus(TaskOptions.TASK_STATUS_BE_FINISHED);
        task.setAntherStatus(TaskOptions.TASK_STATUS_TRACKING);

        return hzTasksDao.updateTargetStatus(task) > 0 ? true : false;
    }

    @Override
    public JSONObject loadUserTask() {
        User user = UserInfo.getUser();
        JSONObject result = new JSONObject();
        List<HzTaskPostDto> dtoList = new ArrayList<>();
        List<HzTasks> tasks = doSelectUserExecutingTasks(Long.valueOf(user.getId()));
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
