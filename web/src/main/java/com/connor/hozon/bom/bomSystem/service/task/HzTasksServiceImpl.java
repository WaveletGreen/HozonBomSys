/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.task;

import cn.net.connor.hozon.dao.dao.task.HzTasksDao;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoInfo;
import cn.net.connor.hozon.dao.pojo.sys.Tree;
import cn.net.connor.hozon.dao.pojo.sys.User;
import cn.net.connor.hozon.dao.pojo.task.HzTasks;
import cn.net.connor.hozon.services.response.task.HzTaskResponseDTO;
import cn.net.connor.hozon.services.request.task.TaskRequestDTO;
import cn.net.connor.hozon.services.service.task.HzTaskService;
import com.connor.hozon.bom.bomSystem.option.TaskOptions;
import com.connor.hozon.bom.bomSystem.service.vwo.HzVwoInfoServiceImpl;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.mybatis.wokeList.HzWorkListDAO;
import com.connor.hozon.bom.resources.service.change.HzChangeOrderService;
import com.connor.hozon.bom.sys.service.TreeService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/10/19 16:49
 * @Modified By:
 */
@Service
@Slf4j
public class HzTasksServiceImpl implements HzTaskService {
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
    HzVwoInfoServiceImpl hzVwoInfoServiceImpl;

    @Autowired
    private HzWorkListDAO hzWorkListDAO;

    @Autowired
    HzChangeOrderService hzChangeOrderService;
    /**
     * 发起流程之后，保存task
     *
     * @return
     */
    @Override
    public boolean saveTask(TaskRequestDTO dto) {
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
        //task.setTaskStatus(TaskOptions.TASK_STATUS_EXECUTING);
        task.setTaskUserId(userId);
        //return hzTasksDao.selectUserTasks(task);
        return hzTasksDao.selectInterfaceTasks(task);
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

    @Override
    public List<HzTasks> doSelectUserTargetTask(Integer taskFormType, Integer taskTargetType, Long taskTargetId, Long taskUserId, Integer status) {
        HzTasks task = new HzTasks();
        task.setTaskFormType(taskFormType);
        task.setTaskTargetType(taskTargetType);
        task.setTaskTargetId(taskTargetId);
        task.setTaskUserId(taskUserId);
        task.setTaskStatus(status);
        return hzTasksDao.selectUserTargetTask(task);
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
            log.error("更新当前任务为完成状态失败:formType=" + formType + "\ttargetType=" + targetType + "\ttargetId=" + targetId);
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
        List<HzTaskResponseDTO> dtoList = new ArrayList<>();
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
                HzTaskResponseDTO dto = new HzTaskResponseDTO();
                if (dbTree != null) {
                    //设置URL
                    dto.setUrl(dbTree.getUrl());
                    //设置表单ID
                    dto.setId(dbTree.getId());
                }
                switch (task.getTaskFormType()) {
                    case TaskOptions.FORM_TYPE_VWO:
                        HzVwoInfo info = hzVwoInfoServiceImpl.doSelectByPrimaryKey(task.getTaskTargetId());
                        dto.setText(info.getVwoNum());
                        dto.setTargetId(info.getId());
                        dto.setTargetName(info.getVwoNum() + "表单");
                        dto.setTargetType(task.getTaskTargetType());
                        dto.setFormType(task.getTaskFormType());
                        break;
                    //预留给EWO表单用
                    case TaskOptions.FORM_TYPE_EWO:
                        break;
                    //预留该MWO表单用
                    case TaskOptions.FORM_TYPE_MWO:
                        break;
                    case TaskOptions.FORM_TYPE_CHANGE:
                        HzChangeOrderRespDTO hzcor= hzChangeOrderService.getHzChangeOrderRecordById(task.getTaskTargetId());
                        dto.setText(hzcor.getChangeNo());
                        dto.setTargetId(hzcor.getId());
                        dto.setTargetName(hzcor.getChangeNo() + "表单");
                        dto.setTargetType(task.getTaskTargetType());
                        dto.setFormType(task.getTaskFormType());
                        break;
                    default:
                        break;
                }
                //HzVwoInfo info = hzVwoInfoServiceImpl.selectByPrimaryKey(task.getTaskTargetId());
                //int count = hzWorkListDAO.count(user.getLogin(),info.getProjectUid());

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
