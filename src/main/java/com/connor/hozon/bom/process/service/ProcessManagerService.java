package com.connor.hozon.bom.process.service;

import com.connor.hozon.bom.bomSystem.iservice.process.IProcess;
import com.connor.hozon.bom.bomSystem.service.process.InterruptionContainer;
import com.connor.hozon.bom.bomSystem.service.process.ReleaseContainer;
import com.connor.hozon.bom.process.iservice.IProcessManagerService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class ProcessManagerService implements IProcessManagerService {
    @Inject
    InterruptEntity interruptEntity;
    @Inject
    ReleaseEntity releaseEntity;
    @Inject
    ProcessStartEntity processStartEntity;

    @Override
    public Object start(long orderId, Object... param) {
        return processStartEntity.doStart(orderId, param);
    }

    @Override
    public Object release(long orderId, Object... param) {
        JSONObject result = new JSONObject();
        boolean status = executeProcess(releaseEntity, new ReleaseContainer(), orderId, param);
        if (status) {
            result.put("msg", "发布成功");
            updateStatus(orderId, 999, true);
        } else {
            result.put("msg", "发布失败");
        }
        result.put("status", status);
        return result;
    }

    /**
     * 更新任务状态和表单状态
     *
     * @param orderId
     * @param i
     * @param b
     */
    private void updateStatus(long orderId, int i, boolean b) {
        updateTask(orderId, i);
        updateOrder(orderId, i);
    }

    private void updateTask(long orderId, int i) {
    }

    private void updateOrder(long orderId, int i) {
    }


    @Override
    public Object interrupt(long orderId, Object... param) {
        JSONObject result = new JSONObject();
        boolean status = executeProcess(interruptEntity, new InterruptionContainer(), orderId, param);
        if (status) {
            result.put("msg", "流程终止成功");
            updateStatus(orderId, 900, true);
        } else {
            result.put("msg", "流程终止失败");
        }
        result.put("status", status);
        return result;
    }

    private boolean executeProcess(Object entity, IProcess container, long orderId, Object... param) {
        container.setCallBackEntity(entity);
        boolean status = container.execute(orderId, param);
        return status;
    }
}
