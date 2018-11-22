package com.connor.hozon.bom.process.service;

import com.connor.hozon.bom.bomSystem.iservice.task.IHzTaskService;
import com.connor.hozon.bom.bomSystem.option.TaskOptions;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.process.iservice.IProcessStart;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import sql.pojo.change.HzAuditorChangeRecord;
import sql.pojo.change.HzChangeOrderRecord;
import sql.pojo.task.HzTasks;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
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
    IHzTaskService iHzTaskService;
    /***是否需要保存执行者的名字**/
    private final static boolean isSaveExecutorName = false;
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessStartEntity.class);

    /**
     * 第一步：保存审核人的数据，保存审核人的ID、审核的表单ID
     * 第二步：关联当前表单orderId，保存流程发起人的ID和发起时间
     * 第三步：
     * @param orderId 变更表单ID
     * @param param   配置参数
     * @return
     * @throws Exception
     */
    @Override
    public Object doStart(long orderId, Object... param) throws Exception {
        Map<String, Object> result = new HashMap<>();
        User user = UserInfo.getUser();
        if (saveAuditor(orderId, param, user) && updateOrder(orderId, param, user) && taskInfo(orderId, param, user)) {
            result.put("status", true);
            result.put("msg", "流程发起成功");
        } else {
            throw new Exception("发起流程失败");
        }
        return result;
    }

    /**
     * 任务通知
     *
     * @param orderId 表单的ID
     * @param param   可选参数
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
        task.setTaskLauncher(user.getUserName());
        task.setTaskLauncherId(Long.valueOf(user.getId()));
        /**可选，单独设置*/
        if (isSaveExecutorName) {
            User taskExecutor = userService.findByUserId((Long) param[0], "1");
            if (taskExecutor != null) {
                /***/
                task.setTaskExecuteUserName(user.getUserName());
            }
        }
        boolean res;
        if (res = iHzTaskService.doInsert(task)) {
            LOGGER.info("保存任务成功");
        } else {
            LOGGER.error("保存任务失败");
        }
        return res;

    }

    /**
     * @param orderId
     * @param param
     * @return
     */
    private boolean updateOrder(long orderId, Object... param) {
        HzChangeOrderRecord hzChangeOrderRecord = new HzChangeOrderRecord();
        hzChangeOrderRecord.setId(orderId);
        hzChangeOrderRecord.setOriginTime(new Date());
        hzChangeOrderRecord.setOriginatorId(Long.valueOf(((User) param[1]).getId()));
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
     * @param param   可选参数，目前可选参数1是审核人ID
     * @return
     */
    public boolean saveAuditor(long orderId, Object... param) {
        Object[] auditorId = (Object[]) param[0];
        HzAuditorChangeRecord hzAuditorChangeRecord = new HzAuditorChangeRecord();
        hzAuditorChangeRecord.setAuditorId((long) auditorId[0]);
        hzAuditorChangeRecord.setOrderId(orderId);
        hzAuditorChangeRecord.setTableName(null);
        hzAuditorChangeRecord.setAuditResult(null);
        hzAuditorChangeRecord.setAuditSugg(null);
        hzAuditorChangeRecord.setAuditTime(null);
        boolean res;
        if ((res = hzAuditorChangeDAO.insert(hzAuditorChangeRecord) > 0 ? true : false)) {
            LOGGER.info("保存审核人数据成功");
        } else {
            LOGGER.error("保存审核人数据失败");
        }
        return res;
    }
}
