///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.task;
//
//import cn.net.connor.hozon.dao.dao.task.HzTasksDao;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import org.springframework.stereotype.Repository;
//import cn.net.connor.hozon.dao.pojo.task.HzTasks;
//
//import java.util.List;
//
///**
// * @Author: Fancyears·Maylos·Malvis
// * @Description: fuck
// * @Date: Created in 2018/9/6 13:19
// * @Modified By:
// */
////@Service("hzTasksDao")
//@Repository
//public class HzTasksDaoImpl extends BasicDaoImpl<HzTasks> implements HzTasksDao {
//    private final static HzTasks TASKS = new HzTasks();
//
//    public HzTasksDaoImpl() {
//        clz = HzTasksDao.class;
//        clzName = clz.getCanonicalName();
//    }
//
//    @Override
//    public List<HzTasks> selectUserTasks(HzTasks tasks) {
//        return baseSQLUtil.executeQuery(tasks, clzName + ".selectUserTasks");
//    }
//
//    @Override
//    public List<HzTasks> selectInterfaceTasks(HzTasks tasks) {
//        return baseSQLUtil.executeQuery(tasks, clzName + ".selectInterfaceTasks");
//    }
//
//    @Override
//    public List<HzTasks> selectUserTargetTaskByType(HzTasks task) {
//        return baseSQLUtil.executeQuery(task, clzName + ".selectUserTargetTaskByType");
//    }
//
//    @Override
//    public List<HzTasks> selectUserTargetTask(HzTasks task) {
//        return baseSQLUtil.executeQuery(task, clzName + ".selectUserTargetTask");
//    }
//
//    @Override
//    public int insertByBatch(List<HzTasks> list) {
//        return baseSQLUtil.executeInsert(list, clzName + ".insertByBatch");
//    }
//
//    @Override
//    public int updateTargetStatus(HzTasks task) {
//        return baseSQLUtil.executeUpdate(task, clzName + ".updateTargetStatus");
//    }
//
//    /**
//     * 终止任务
//     *
//     * @param task
//     * @return
//     */
//    @Override
//    public int updateTasksStop(HzTasks task) {
//        return baseSQLUtil.executeUpdate(task, clzName + ".updateTasksStop");
//    }
//
//}
