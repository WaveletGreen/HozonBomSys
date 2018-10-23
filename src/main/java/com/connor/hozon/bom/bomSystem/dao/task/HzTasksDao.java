/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.task;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.task.HzTasks;

import java.util.List;

@Configuration
public interface HzTasksDao extends BasicDao<HzTasks> {
    /**
     * 人员任务查找
     * @param tasks
     * @return
     */
    List<HzTasks> selectUserTasks(HzTasks tasks);
}