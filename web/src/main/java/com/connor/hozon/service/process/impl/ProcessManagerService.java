/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.process.impl;

import com.connor.hozon.service.process.IProcess;
import com.connor.hozon.service.process.IProcessFinish;
import com.connor.hozon.service.process.IProcessManagerService;
import com.connor.hozon.service.process.IProcessStart;
import cn.net.connor.hozon.dao.pojo.sys.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.task.HzTasks;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 流程综合管理类，旨在驱动流程的开始，审核和结束环节
 * <p>
 * 开始的前置检查由{@link IProcessStart#checkOrderAuditor(Long)}校验表单是否已存在审核的任务{@link HzTasks}
 * <p>
 * 开始环节：交由{@link IProcessStart}实现
 * <p>
 * 审核环节：分为通过审核与不通过
 * <p>
 * 通过交由{@link ReleaseEntity}实现，并发布数据
 * 不通过交由{@link InterruptEntity}实现，并终止流程
 * <p>
 * 结束环节：交由{@link IProcessFinish}实现，根据审核环节的审核状态，设置表单的状态和任务的完成状态
 * @Date: Created in  2018/11/22 15:33
 * @Modified By:
 */

@Service("processManagerService")
public class ProcessManagerService implements IProcessManagerService {
    @Inject
    InterruptEntity interruptEntity;
    @Inject
    ReleaseEntity releaseEntity;
    @Inject
    IProcessStart processStartEntity;
    @Inject
    IProcessFinish processFinishEntity;

    /**
     * 检开始选择审核人环节，检查表单是否已存在审核任务，如果以存在审核任务，则返回该审核人员对象，反之返回null
     *
     * @param orderId
     * @return
     */
    public User checkOrderAuditor(Long orderId) {
        return processStartEntity.checkOrderAuditor(orderId);
    }

    /**
     * 流程开始环节，更新表单状态，增加审核任务，添加审核人，添加申请人等信息
     *
     * @param orderId 传入的表单ID
     * @param param
     * @return
     */
    @Override
    public Object start(long orderId, Object... param) {
        try {
            return processStartEntity.doStart(orderId, param);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("status", false);
            result.put("msg", "流程发起失败");
            return result;
        }
    }

    /**
     * 审核完成，数据发布操作
     *
     * @param orderId 表单的ID
     * @param param   配置用的参数，可选
     * @return
     */
    @Override
    public Object release(long orderId, Object... param) {
        JSONObject result = new JSONObject();
        boolean status = executeProcess(releaseEntity, new ReleaseContainer(), orderId, param);
        if (status) {
            result.put("msg", "同意变更，审核已完成");
            processFinishEntity.doFinish(orderId, PROCESS_OK, param[0]);
        } else {
            result.put("msg", "流程审核发布失败");
        }
        result.put("status", status);
        return result;
    }

    /**
     * 审核不通过，数据回版操作
     *
     * @param orderId 表单的ID
     * @param param   配置用的参数，可选
     * @return
     */
    @Override
    public Object interrupt(long orderId, Object... param) {
        JSONObject result = new JSONObject();
        boolean status = executeProcess(interruptEntity, new InterruptionContainer(), orderId, param);
        if (status) {
            result.put("msg", "不同意变更，审核已完成");
            processFinishEntity.doFinish(orderId, PROCESS_NOT_OK, param[0]);
        } else {
            result.put("msg", "流程审核终止失败");
        }
        result.put("status", status);
        return result;
    }

    /**
     * 启动容器的执行回调方法
     *
     * @param entity
     * @param container 容器对象
     * @param orderId   表单ID
     * @param param     可选参数，具体传参实际需求进行，这里无法提供具体的入参形式
     * @return
     */
    private boolean executeProcess(Object entity, IProcess container, long orderId, Object... param) {
        container.setCallBackEntity(entity);
        boolean status = container.execute(orderId, param);
        return status;
    }

}
