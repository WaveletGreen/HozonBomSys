package com.connor.hozon.bom.activiti;

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

    private static Logger logger= Logger.getLogger(ActImpl.class);

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
    public ModelAndView createProcess(@RequestAttribute(value = "actKey") String key, @RequestAttribute("actReviewers") Map<String, List<String>> reviewers, @RequestAttribute("actAssignees") Map<String, String> assignees) {
        logger.info("流程实例数量：" + runtimeService.createProcessInstanceQuery().count());
        logger.info("开始进行创建");
        Map<String, Object> variables = new HashMap<>();
        variables.putAll(reviewers);
        variables.putAll(assignees);
        ProcessInstance pi;
        try {
            pi = runtimeService.startProcessInstanceByKey(key,variables);
        } catch (Exception e) {
            logger.error(e);
            String err="流程创建失败："+e.getMessage();
            return error(err);
        }
        logger.info("创建完成");
        logger.info("流程实例数量：" + runtimeService.createProcessInstanceQuery().count());
        ModelAndView mv=new ModelAndView("success");
        mv.addObject("processId","新建流程实例："+pi.getId());
        return mv;
    }

    @Override
    public ModelAndView runDoTask(@RequestAttribute(value = "actDoTask") String taskId) {
        //检查任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task==null){
            String err="没有找到任务，id = "+taskId;
            return error(err);
        }
        logger.info("开始执行任务："+task.toString());
        String taskName = task.getTaskDefinitionKey();
        if(!taskName.startsWith("do_")){
            String err="不是DO任务，请检查任务类型："+task.toString();
            return error(err);
        }
        //完成任务
        try {
            taskService.complete(taskId);
        } catch (Exception e) {
            logger.error(e);
            String err="任务执行失败："+e.getMessage();
            return error(err);
        }
        logger.info("执行完成");
        ModelAndView mv=new ModelAndView("success");
        return mv;
    }

    @Override
    public ModelAndView runReviewTask(@RequestAttribute(value = "actReviewTask") String taskId, @RequestAttribute("actDecision") int decision) {
        //检查任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task==null){
            String err="没有找到任务，id = "+taskId;
            return error(err);
        }
        logger.info("开始执行任务："+task.toString());
        String taskName = task.getTaskDefinitionKey();
        if(!taskName.startsWith("review_")){
            String err="不是审核任务，请检查任务类型："+task.toString();
            return error(err);
        }
        //完成任务
        Object passCountObj = taskService.getVariable(taskId, taskName + "_passCount");
        Object totalCountObj = taskService.getVariable(taskId,taskName+"_totalCount");
        if(passCountObj==null||totalCountObj==null){
            String err="审核任务未添加监听";
            return error(err);
        }
        int passCount=(int)passCountObj;
        int totalCount=(int)totalCountObj;
        if(decision==Act.PASS){
            passCount++;
            totalCount++;
        }else if(decision==Act.NOT_PASS){
            totalCount++;
        }else if(decision==Act.CANCEL){
            totalCount=-9999;
        }else{
            String err="无法识别的审核结果：decision = "+decision;
            return error(err);
        }
        taskService.setVariable(taskId,taskName + "_passCount",passCount);
        taskService.setVariable(taskId,taskName + "_totalCount",totalCount);
        String varName=taskName+"_decision";
        Map<String,Object> variables=new HashMap<>();
        //获取结果
        int actDecision=Act.NOT_PASS;
        if(totalCount<0){
            actDecision=Act.CANCEL;
        }else{
            if(passCount==totalCount){
                actDecision=Act.PASS;
            }
        }
        variables.put(varName,actDecision);
        try {
            taskService.complete(taskId,variables);
        } catch (Exception e) {
            logger.error(e);
            return error("任务执行失败："+e.getMessage());
        }
        logger.info("执行完成");
        ModelAndView mv=new ModelAndView("success");
        return mv;
    }


    @Override
    public ModelAndView getProcessDiagram(String folder, String processInstanceId) {
        return null;
    }

    @Override
    public ModelAndView getProcessDiagram(String folder, Task task) {
        return null;
    }

    @Override
    public ModelAndView queryTasks(String user) {
        List<Task> tasks = queryTaskByUserId(taskService,user);
        ModelAndView mv=new ModelAndView("success");
        mv.addObject("actTasks",tasks);
        return mv;
    }

    @Override
    public List<Task> queryTaskByUserId(TaskService taskService, String user){
        return taskService.createTaskQuery().taskAssignee(user).list();
    }

    @Override
    public List<Task> queryTasksByProcessId(TaskService taskService, String processInstanceId){
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        return tasks;
    }

    public Map<Task,String> queryUsersByProcessId(TaskService taskService, String processInstanceId){
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        Map<Task,String> userMap=new LinkedHashMap<>();
        for (Task task: tasks) {
            userMap.put(task,task.getAssignee());
        }
        return userMap;
    }


    @Override
    public void notify(DelegateTask delegateTask) {

        System.out.println("创建任务：" + delegateTask.getName());
        System.out.println("负责人：" + delegateTask.getAssignee());
        System.out.println("<通知负责人>");
        String taskName=delegateTask.getTaskDefinitionKey();
        //创建任务时添加变量记录审核信息
        delegateTask.setVariable(taskName+"_passCount", 0);
        delegateTask.setVariable(taskName+"_totalCount", 0);
    }

    private void checkProcessVariables(String key,Map<String,Object> variables) throws Exception{

    }

    private void checkTaskVariables(Task task,Map<String,Object> variables) throws Exception{

    }

    /**
     * 异常处理
     * @param err 错误信息
     * @return 错误页面
     */
    private ModelAndView error(String err){
        logger.error(err);
        ModelAndView mv=new ModelAndView("error");
        mv.addObject("msg",err);
        return mv;
    }


    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        if(PROCESS_COMPLETED.equals(activitiEvent.getType())){
            logger.info("<流程完成> "+activitiEvent.getProcessInstanceId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
