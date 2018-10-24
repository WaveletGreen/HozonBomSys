/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.task;

import com.connor.hozon.bom.bomSystem.dao.task.HzTasksDao;
import com.connor.hozon.bom.bomSystem.dto.task.TaskReceivedDto;
import com.connor.hozon.bom.bomSystem.iservice.task.IHzTaskService;
import com.connor.hozon.bom.bomSystem.option.TaskOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.task.HzTasks;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/10/19 16:49
 * @Modified By:
 */
@Service("hzTaskService")
public class HzTasksService implements IHzTaskService {
    @Autowired
    HzTasksDao hzTasksDao;

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
}
