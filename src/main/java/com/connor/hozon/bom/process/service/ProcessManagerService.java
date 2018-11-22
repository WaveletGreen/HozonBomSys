package com.connor.hozon.bom.process.service;

import com.connor.hozon.bom.bomSystem.iservice.process.IProcess;
import com.connor.hozon.bom.bomSystem.service.process.InterruptionContainer;
import com.connor.hozon.bom.bomSystem.service.process.ReleaseContainer;
import com.connor.hozon.bom.process.iservice.IProcessFinish;
import com.connor.hozon.bom.process.iservice.IProcessManagerService;
import com.connor.hozon.bom.process.iservice.IProcessStart;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;


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

    @Override
    public Object release(long orderId, Object... param) {
        JSONObject result = new JSONObject();
        boolean status = executeProcess(releaseEntity, new ReleaseContainer(), orderId, param);
        if (status) {
            result.put("msg", "发布成功");
            processFinishEntity.doFinish(orderId, PROCESS_OK, param[0]);
        } else {
            result.put("msg", "发布失败");
        }
        result.put("status", status);
        return result;
    }

    @Override
    public Object interrupt(long orderId, Object... param) {
        JSONObject result = new JSONObject();
        boolean status = executeProcess(interruptEntity, new InterruptionContainer(), orderId, param);
        if (status) {
            result.put("msg", "流程终止成功");
            processFinishEntity.doFinish(orderId, PROCESS_NOT_OK, param[0]);
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