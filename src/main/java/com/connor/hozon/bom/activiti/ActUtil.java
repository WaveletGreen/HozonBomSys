package com.connor.hozon.bom.activiti;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by K on 2018/8/20.
 */
public class ActUtil {

    private static Logger logger = Logger.getLogger(ActUtil.class);

    public static ProcessInstance createProcess(RuntimeService runtimeService, String key, Map<String, List<String>> reviewers, Map<String, String> assignees) throws Exception {
        Map<String, Object> variables = new HashMap<>();
        if(reviewers!=null){
            variables.putAll(reviewers);
        }
        if(assignees!=null){
            variables.putAll(assignees);
        }
        ProcessInstance pi;
        pi = runtimeService.startProcessInstanceByKey(key, variables);
        return pi;
    }

    public static void runDoTask(TaskService taskService, String taskId) throws Exception {
        //检查任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            String err = "没有找到任务，id = " + taskId;
            throw new Exception(err);
        }
        String taskName = task.getTaskDefinitionKey();
        if (!taskName.startsWith("do_")) {
            String err = "不是DO任务，请检查任务类型：" + task.toString();
            throw new Exception(err);
        }
        taskService.complete(taskId);
    }

    public static void runReviewTask(TaskService taskService, String taskId, int decision) throws Exception {
        //检查任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            String err = "没有找到任务，id = " + taskId;
            throw new Exception(err);
        }
        String taskName = task.getTaskDefinitionKey();
        if (!taskName.startsWith("review_")) {
            String err = "不是审核任务，请检查任务类型：" + task.toString();
            throw new Exception(err);
        }
        //完成任务
        Object passCountObj = taskService.getVariable(taskId, taskName + "_passCount");
        Object totalCountObj = taskService.getVariable(taskId, taskName + "_totalCount");
        if (passCountObj == null || totalCountObj == null) {
            String err = "审核任务未添加监听";
            throw new Exception(err);
        }
        int passCount = (int) passCountObj;
        int totalCount = (int) totalCountObj;
        int actDecision = Act.CANCEL;
        logger.info(task+" 审核人："+task.getAssignee()+"|审核结果："+decision);
        if (totalCount >= 0) {
            if (decision == Act.PASS) {
                passCount++;
                totalCount++;
            } else if (decision == Act.NOT_PASS) {
                totalCount++;
            } else if (decision == Act.CANCEL) { //取消流程
                totalCount = -9999;
            } else {
                String err = "无法识别的审核结果：decision = " + decision;
                throw new Exception(err);
            }
            taskService.setVariable(taskId, taskName + "_passCount", passCount);
            taskService.setVariable(taskId, taskName + "_totalCount", totalCount);
            if (totalCount < 0) {
                logger.info("取消流程");
                actDecision = Act.CANCEL;
            } else {
                logger.info("通过人数："+passCount+"|已审核人数"+totalCount);
                if (passCount == totalCount) {
                    actDecision = Act.PASS;
                }else{
                    actDecision= Act.NOT_PASS;
                }
            }
        }else{
            logger.info("流程已被其他人设置为取消");
        }
        String varName = taskName + "_decision";
        Map<String, Object> variables = new HashMap<>();
        variables.put(varName, actDecision);
        taskService.complete(taskId, variables);
    }

    public static Task findTaskById(TaskService taskService,String taskId) throws Exception{
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            String err = "没有找到任务，id = " + taskId;
            throw new Exception(err);
        }
        return task;
    }

    public static void deleteProcessInstance(TaskService taskService,RuntimeService runtimeService,String taskId, String reason) throws Exception{
        Task task=findTaskById(taskService,taskId);
        logger.info("强制删除流程："+task);
        String processInstanceId=task.getProcessInstanceId();
        runtimeService.deleteProcessInstance(processInstanceId,reason);
        //TODO
        logger.info("<流程终止> " +processInstanceId);
    }

    public static List<Task> queryTaskByUserId(TaskService taskService, String user){
        return taskService.createTaskQuery().taskAssignee(user).list();
    }

    public static List<Task> queryTasksByProcessId(TaskService taskService, String processInstanceId){
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        return tasks;
    }

    public static Map<Task,String> queryUsersByProcessId(TaskService taskService, String processInstanceId){
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        Map<Task,String> userMap=new LinkedHashMap<>();
        for (Task task: tasks) {
            userMap.put(task,task.getAssignee());
        }
        return userMap;
    }

}
