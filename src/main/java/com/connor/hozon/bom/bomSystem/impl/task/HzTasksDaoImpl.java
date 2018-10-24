/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.task;

import com.connor.hozon.bom.bomSystem.dao.task.HzTasksDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Service;
import sql.pojo.task.HzTasks;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/23 14:33
 * @Modified By:
 */
@Service("hzTasksDao")
public class HzTasksDaoImpl extends BasicDaoImpl<HzTasks> implements HzTasksDao {
    private final static HzTasks TASKS = new HzTasks();

    public HzTasksDaoImpl() {
        clz = HzTasksDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public List<HzTasks> selectUserTasks(HzTasks tasks) {
        return baseSQLUtil.executeQuery(tasks, clzName + ".selectUserTasks");
    }

    @Override
    public List<HzTasks> selectUserTargetTaskByType(HzTasks task) {
        return baseSQLUtil.executeQuery(task, clzName + ".selectUserTargetTaskByType");
    }

    @Override
    public int insertByBatch(List<HzTasks> list) {
        return baseSQLUtil.executeInsert(list, clzName + ".insertByBatch");
    }

    @Override
    public int updateTargetStatus(HzTasks task) {
        return baseSQLUtil.executeUpdate(task, clzName + ".updateTargetStatus");
    }
}
