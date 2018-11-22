/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.process.service;

import com.connor.hozon.bom.bomSystem.option.TaskOptions;
import com.connor.hozon.bom.bomSystem.service.task.HzTasksService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.process.iservice.IProcessFinish;
import com.connor.hozon.bom.process.iservice.IProcessManagerService;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import sql.pojo.change.HzAuditorChangeRecord;
import sql.pojo.change.HzChangeOrderRecord;
import sql.pojo.task.HzTasks;

import java.util.Date;
import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 流程结束节点，修改任务状态和表单状态
 * @Date: Created in  2018/11/22 15:10
 * @Modified By:
 */
@Service
@EnableTransactionManagement(proxyTargetClass = true)
@Transactional(rollbackFor = {IllegalArgumentException.class, RuntimeException.class, Exception.class})
public class ProcessFinishEntity implements IProcessFinish {
    /**
     * 任务驱动服务
     */
    @Autowired
    HzTasksService hzTasksService;
    /***审核人记录*/
    @Autowired
    HzAuditorChangeDAO hzAuditorChangeDAO;
    /***变更表单记录*/
    @Autowired
    HzChangeOrderDAO hzChangeOrderDAO;
    /***审核人对象，减少查询User*/
    private HzAuditorChangeRecord hzAuditorChangeRecord;
    /***是否能重复审核，不允许审核则审核不通过，审核不通过不影响修改配置和BOM的数据*/
    private static boolean signAgain = true;
    private final static Logger LOGGER = LoggerFactory.getLogger(ProcessFinishEntity.class);

    /**
     * 首先更新审核人意见，从而获取到审核人的ID
     * 再根据变更表单类型{@link TaskOptions}、审核人的ID和表单ID，对审核人的任务进行更新
     * 最后更新被审核表单的状态为已完成/已取消
     *
     * @param orderId 表单的ID
     * @param params  配置参数
     *                0：1/0，用户表示是发布还是终止，又表示审核人是否同意，用的是同一个状态码
     *                1：审核人审核意见
     * @return
     */
    @Override
    public boolean doFinish(Long orderId, Object... params) {
        return updateAuditor(orderId, params) && updateTask(orderId, params) && updateOrderForm(orderId, params);
    }

    /**
     * 更新表单状态
     *
     * @param orderId
     * @param params
     * @return
     */
    private boolean updateOrderForm(Long orderId, Object[] params) {
        HzChangeOrderRecord hzChangeOrderRecord = new HzChangeOrderRecord();
        hzChangeOrderRecord.setId(orderId);
        //根据审核人同意与否进行设置状态
        hzChangeOrderRecord.setState(IProcessManagerService.PROCESS_OK == (Integer) params[0] ? 1 : 4);
        return hzChangeOrderDAO.update(hzChangeOrderRecord) > 0 ? true : false;
    }

    /**
     * 更新审核人意见状态
     *
     * @param orderId
     * @param params
     * @return
     */
    private boolean updateAuditor(Long orderId, Object[] params) {
        User user = UserInfo.getUser();
        hzAuditorChangeRecord = hzAuditorChangeDAO.findByOrderId(orderId, Long.valueOf(user.getId()));
        if (null != hzAuditorChangeRecord) {
            if (null != hzAuditorChangeRecord.getAuditResult()) {
                LOGGER.error("已完成审核，不需要再进行审核");
                if (!signAgain) {
                    return false;
                }
            }
            //审核人意见
            hzAuditorChangeRecord.setAuditSugg((String) params[1]);
            hzAuditorChangeRecord.setAuditTime(new Date());
            //审核人是否同意，状态码与发布or终止一致
            hzAuditorChangeRecord.setAuditResult((int) params[0]);
            return hzAuditorChangeDAO.updateByPrimaryKeySelective(hzAuditorChangeRecord) > 0 ? true : false;
        }
        return false;
    }

    /**
     * 更新任务状态
     *
     * @param orderId
     * @param params
     * @return
     */
    private boolean updateTask(Long orderId, Object[] params) {
        List<HzTasks> tasks = hzTasksService.doSelectUserTargetTaskByType(TaskOptions.FORM_TYPE_CHANGE, TaskOptions.TASK_TARGET_TYPE_CHANE, orderId, hzAuditorChangeRecord.getAuditorId(), TaskOptions.TASK_STATUS_EXECUTING);
        if (tasks != null && tasks.size() > 0) {
            HzTasks task = new HzTasks();
            task.setId(tasks.get(0).getId());
            if (1 == (int) params[0]) {
                task.setTaskStatus(TaskOptions.TASK_STATUS_FINISHED);
            } else {
                task.setTaskStatus(TaskOptions.TASK_STATUS_STOP);
            }
            return hzTasksService.doUpdateByPrimaryKeySelective(task);
        }
        return false;
    }
}
