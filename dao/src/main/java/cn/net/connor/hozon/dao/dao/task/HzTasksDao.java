/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.task;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.task.HzTasks;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 任务dao
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzTasksDao extends BasicDao<HzTasks> {
    /**
     * 人员任务查找
     * @param tasks
     * @return
     */
    List<HzTasks> selectUserTasks(HzTasks tasks);

    /**
     * 查询用户需要执行的任务
     * @param tasks
     * @return
     */
    List<HzTasks> selectInterfaceTasks(HzTasks tasks);

    /**
     * 根据任务类型查询用户下的任务
     * @param task
     * @return
     */
    List<HzTasks> selectUserTargetTaskByType(HzTasks task);

    /**
     * 查询用户下的所有任务
     * @param task
     * @return
     */
    List<HzTasks> selectUserTargetTask(HzTasks task);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertByBatch(List<HzTasks> list);

    /**
     * 更新任务状态
     * @param task
     * @return
     */
    int updateTargetStatus(HzTasks task);

    /**
     * 终止任务
     * @param task
     * @return
     */
    int updateTasksStop(HzTasks task);
}