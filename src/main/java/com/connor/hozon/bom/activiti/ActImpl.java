package com.connor.hozon.bom.activiti;

import com.connor.hozon.bom.activiti.Act;
import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.activiti.engine.delegate.event.ActivitiEventType.PROCESS_COMPLETED;

/**
 * Created by K on 2018/8/17.
 */
@Service("act")
public class ActImpl implements Act, TaskListener, ActivitiEventListener {

    private static Logger logger = Logger.getLogger(ActImpl.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private FormService formService;

    @Override
    public ModelAndView createProcess(@RequestAttribute(value = "actKey") String key, @RequestAttribute("actReviewers") Map<String, List<String>> multiUserAssign, @RequestAttribute("actAssignees") Map<String, String> singleUserAssign) {
        logger.info("流程实例数量：" + runtimeService.createProcessInstanceQuery().count());
        logger.info("开始进行创建");
        ProcessInstance pi;
        try {
            pi = ActUtil.createProcess(runtimeService, key, multiUserAssign, singleUserAssign);
        } catch (Exception e) {
            logger.error(e);
            String err = "流程创建失败：" + e.getMessage();
            return error(err);
        }
        logger.info("创建完成");
        logger.info("流程实例数量：" + runtimeService.createProcessInstanceQuery().count());
        ModelAndView mv = new ModelAndView("success");
        mv.addObject("processId", "新建流程实例：" + pi.getId());
        return mv;
    }

    @Override
    public ModelAndView runDoTask(@RequestAttribute(value = "actDoTask") String taskId) {
        logger.info("开始执行任务：" + taskId);
        try {
            ActUtil.runDoTask(taskService, taskId);
        } catch (Exception e) {
            logger.error(e);
            String err = "任务执行失败：" + e.getMessage();
            return error(err);
        }
        logger.info("执行完成");
        ModelAndView mv = new ModelAndView("success");
        return mv;
    }

    @Override
    public ModelAndView runReviewTask(@RequestAttribute(value = "actReviewTask") String taskId, @RequestAttribute("actDecision") int decision) {
        logger.info("开始执行任务：" + taskId);
        try {
            ActUtil.runReviewTask(taskService, taskId, decision);
        } catch (Exception e) {
            logger.error(e);
            return error("任务执行失败：" + e.getMessage());
        }
        logger.info("执行完成");
        ModelAndView mv = new ModelAndView("success");
        return mv;
    }


    @Override
    public ModelAndView queryTasks(String user) {
        List<Task> tasks = queryTaskByUserId(taskService, user);
        ModelAndView mv = new ModelAndView("success");
        mv.addObject("actTasks", tasks);
        return mv;
    }

    @Override
    public List<Task> queryTaskByUserId(TaskService taskService, String user) {
        return ActUtil.queryTaskByUserId(taskService, user);
    }

    @Override
    public List<Task> queryTasksByProcessId(TaskService taskService, String processInstanceId) {
        return ActUtil.queryTasksByProcessId(taskService, processInstanceId);
    }

    public Map<Task, String> queryUsersByProcessId(TaskService taskService, String processInstanceId) {
        return ActUtil.queryUsersByProcessId(taskService, processInstanceId);
    }


    /**
     * 创建审核任务监听
     *
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("创建任务：" + delegateTask.getName());
        System.out.println("负责人：" + delegateTask.getAssignee());
        System.out.println("<通知负责人>");
        String taskName = delegateTask.getTaskDefinitionKey();
        //创建任务时添加变量记录审核信息
        delegateTask.setVariable(taskName + "_passCount", 0);
        delegateTask.setVariable(taskName + "_totalCount", 0);
    }

    /**
     * 异常处理
     *
     * @param err 错误信息
     * @return 错误页面
     */
    private ModelAndView error(String err) {
        logger.error(err);
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("msg", err);
        return mv;
    }

    /**
     * 流程完成监听
     *
     * @param activitiEvent
     */
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        if (PROCESS_COMPLETED.equals(activitiEvent.getType())) {
            //TODO 
            logger.info("<流程完成> " + activitiEvent.getProcessInstanceId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
