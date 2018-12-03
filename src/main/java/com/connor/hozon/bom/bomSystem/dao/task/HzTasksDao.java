/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.task;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import sql.pojo.task.HzTasks;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 任务dao
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzTasksDao extends BasicDao<HzTasks> {
    /**
     * 人员任务查找
     * @param tasks
     * @return
     */
    List<HzTasks> selectUserTasks(HzTasks tasks);

    List<HzTasks> selectInterfaceTasks(HzTasks tasks);

    List<HzTasks> selectUserTargetTaskByType(HzTasks task);

    List<HzTasks> selectUserTargetTask(HzTasks task);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertByBatch(List<HzTasks> list);

    int updateTargetStatus(HzTasks task);

    /**
     * 终止任务
     * @param task
     * @return
     */
    int tasksStop(HzTasks task);
}