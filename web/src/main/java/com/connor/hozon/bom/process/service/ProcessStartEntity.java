/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.process.service;

import cn.net.connor.hozon.services.service.task.HzTaskService;
import com.connor.hozon.bom.bomSystem.option.TaskOptions;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.bom.process.iservice.IProcessStart;
import com.connor.hozon.bom.resources.mybatis.change.HzApplicantChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import cn.net.connor.hozon.dao.pojo.sys.User;
import cn.net.connor.hozon.services.service.sys.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import cn.net.connor.hozon.dao.pojo.change.change.HzApplicantChangeRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzAuditorChangeRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;
import cn.net.connor.hozon.dao.pojo.task.HzTasks;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 开启流程
 * 第一步：保存审核人数据
 * 第二步：更新表单流程发起时间和发起人
 * 第三步：开启一个新的任务
 * 第四步：新增该表单发起流程时的申请人数据
 * @Date: Created in  2018/11/22 11:55
 * @Modified By:
 */
@Component
@EnableTransactionManagement(proxyTargetClass = true)
@Transactional(rollbackFor = {IllegalArgumentException.class, RuntimeException.class, Exception.class})
@PropertySource({"classpath:myresource.properties"})
public class ProcessStartEntity implements IProcessStart {
    @Value("${UNTREATED_ID}")
    Long untreatedId;
    /**
     * 表单
     */
    @Autowired
    HzChangeOrderDAO hzChangeOrderDAO;
    /**
     * 审核人
     */
    @Autowired
    HzAuditorChangeDAO hzAuditorChangeDAO;
    /***用户服务*/
    @Autowired
    UserService userService;
    @Autowired
    HzTaskService hzTaskService;
    @Autowired
    HzApplicantChangeDAO hzApplicantChangeDAO;
    /***是否需要保存执行者的名字**/
    private final static boolean isSaveExecutorName = true;
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessStartEntity.class);


    private Long auditRecordId;//审核记录id

    public Long getAuditRecordId() {
        return auditRecordId;
    }

    public void setAuditRecordId(Long auditRecordId) {
        this.auditRecordId = auditRecordId;
    }

    @Override
    public User checkOrderAuditor(Long orderId) {
        List<HzTasks> tasks = hzTaskService.doSelectUserTargetTask(TaskOptions.FORM_TYPE_CHANGE, TaskOptions.TASK_TARGET_TYPE_CHANE, orderId, null, 1);

        if (null == tasks || tasks.size() < 1) {
            return null;
        } else {
            HzTasks task = tasks.get(0);
            Long id = task.getTaskUserId();
            return userService.findByUserId(id, "1");
        }
    }

    /**
     * 第一步：保存审核人的数据，保存审核人的ID、审核的表单ID
     * 第二步：关联当前表单orderId，保存流程发起人的ID和发起时间
     * 第三步：
     *
     * @param orderId 变更表单ID
     * @param param   配置参数，位置说明如下:
     *                0：前端回传的审核人ID，0号参数是一个数组对象，因此需要转换成数组后再取出第0位作为审核人ID
     * @return
     * @throws Exception
     */
    @Override
    public Object doStart(long orderId, Object... param) throws Exception {
        Map<String, Object> result = new HashMap<>();
        User user = UserInfo.getUser();
        if (saveAuditor(orderId, param) && updateOrder(orderId, user) && taskInfo(orderId, param, user) && saveApplicant(orderId, user)) {
            result.put("status", true);
            result.put("msg", "流程发起成功");
        } else {
            throw new Exception("发起流程失败");
        }
        return result;
    }

    /**
     * 保存申请人数据
     *
     * @param orderId 变更表单ID
     * @param user    申请人
     * @return
     */
    private boolean saveApplicant(long orderId, User user) {
        HzApplicantChangeRecord record = new HzApplicantChangeRecord();
        record.setOrderId(orderId);
        record.setTableName("");
        record.setApplicantId(Long.valueOf(user.getId()));
        record.setAuditRecordId(this.auditRecordId);
        record.setApplicantTime(new Date());
        boolean res;

        if (res = hzApplicantChangeDAO.insert(record) > 0 ? true : false) {
            LOGGER.info("保存申请人数据成功");
        } else {
            LOGGER.error("保存申请人数据失败");
        }
        return res;
    }

    /**
     * 任务通知
     *
     * @param orderId 表单的ID
     * @param param   可选参数
     *                0号位：审核表单的ID，Object数据
     *                1号位：审核人（当前用户）
     * @return
     */
    private boolean taskInfo(long orderId, Object... param) {
        Date now = new Date();
        User user = (User) param[1];
        Object[] _param = (Object[]) param[0];
        HzTasks task = new HzTasks();
        /***设置为执行状态*/
        task.setTaskStatus(TaskOptions.TASK_STATUS_EXECUTING);
        /***设置通知人*/
        task.setTaskUserId((long) _param[0]);
        /***设置表单类型*/
        task.setTaskFormType(TaskOptions.FORM_TYPE_CHANGE);
        /****保存目标表单的ID*/
        task.setTaskTargetId(orderId);
        /*** 目标表单类型*/
        task.setTaskTargetType(TaskOptions.TASK_TARGET_TYPE_CHANE);
        /***URL相关*/
        task.setTaskFormId(untreatedId/*Long.parseLong(_param[1].toString())*/);
        task.setTaskCreateDate(now);
        task.setTaskUpdateDate(now);
        task.setTaskLauncher(user.getUsername());
        task.setTaskLauncherId(Long.valueOf(user.getId()));
        /**可选，单独设置*/
        if (isSaveExecutorName) {
            User taskExecutor = userService.findByUserId((Long) _param[0], "1");
            if (taskExecutor != null) {
                /***/
                task.setTaskExecuteUserName(user.getUsername());
            }
        }
        boolean res;
        if (res = hzTaskService.doInsert(task)) {
            LOGGER.info("保存任务成功");
        } else {
            LOGGER.error("保存任务失败");
        }
        return res;

    }

    /**
     * @param orderId
     * @param param   可选参数，0号位是审核人(当前用户)
     * @return
     */
    private boolean updateOrder(long orderId, Object... param) {
        HzChangeOrderRecord hzChangeOrderRecord = new HzChangeOrderRecord();
        hzChangeOrderRecord.setId(orderId);
        //设置为流程中状态
        hzChangeOrderRecord.setState(3);
        hzChangeOrderRecord.setOriginTime(new Date());
        hzChangeOrderRecord.setOriginatorId(Long.valueOf(((User) param[0]).getId()));
        boolean res;
        if ((res = hzChangeOrderDAO.update(hzChangeOrderRecord) > 0 ? true : false)) {
            LOGGER.info("保存流程发起者数据成功");
        } else {
            LOGGER.error("保存流程发起者数据失败");
        }
        return res;
    }

    /**
     * 保存审核人数据
     *
     * @param orderId 表单ID
     * @param param   选配参数，目前可选参数1是审核人ID，看不懂？{@link ProcessStartEntity#saveApplicant}，入参是可以固定下来的
     * @return
     */
    public boolean saveAuditor(long orderId, Object... param) {
        HzAuditorChangeRecord hzAuditorChangeRecord = new HzAuditorChangeRecord();
        hzAuditorChangeRecord.setAuditorId((long) param[0]);
        hzAuditorChangeRecord.setOrderId(orderId);
        hzAuditorChangeRecord.setTableName(null);
        hzAuditorChangeRecord.setAuditResult(null);
        hzAuditorChangeRecord.setAuditSugg(null);
        hzAuditorChangeRecord.setAuditTime(null);
        int i  = hzAuditorChangeDAO.insert(hzAuditorChangeRecord);
        if(i>0){
            this.auditRecordId = hzAuditorChangeRecord.getId();
        }
        boolean res =i>0?true:false;
        if (res) {
            LOGGER.info("保存审核人数据成功");
        } else {
            LOGGER.error("保存审核人数据失败");
        }
        return res;
    }
}
