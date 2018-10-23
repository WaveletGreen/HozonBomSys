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
 * @Description:
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

}
