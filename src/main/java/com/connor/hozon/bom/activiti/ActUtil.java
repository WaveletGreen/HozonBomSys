package com.connor.hozon.bom.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
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

    /**
     * 创建流程实例
     * @param runtimeService 服务
     * @param key 流程id
     * @param multiUserAssign 多实例任务指派信息
     * @param singleUserAssign 单实例任务指派信息
     * @return 新建的流程实例
     * @throws Exception
     */
    public static ProcessInstance createProcess(RuntimeService runtimeService, String key, Map<String, List<String>> multiUserAssign, Map<String, String> singleUserAssign) throws Exception {
        Map<String, Object> variables = new HashMap<>();
        if(multiUserAssign!=null){
            variables.putAll(multiUserAssign);
        }
        if(singleUserAssign!=null){
            variables.putAll(singleUserAssign);
        }
        ProcessInstance pi;
        pi = runtimeService.startProcessInstanceByKey(key, variables);
        return pi;
    }

    /**
     * 执行Do任务
     * @param taskService 服务
     * @param taskId 任务id
     * @throws Exception
     */
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

    /**
     * 执行审核任务
     * @param taskService 服务
     * @param taskId 任务id
     * @param decision 审核结果
     * @throws Exception
     */
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
            logger.info("流程已被其他人设置为取消"); //当前取消方法：有一人取消则该节点审核结果为取消；合众取消流程审核节点为单人审核，所以不会有问题
        }
        String varName = taskName + "_decision";
        logger.info(varName+" == "+actDecision);
        Map<String, Object> variables = new HashMap<>();
        variables.put(varName, actDecision);
        taskService.complete(taskId, variables);
    }

    /**
     * 通过id查询任务
     * @param taskService 服务
     * @param taskId 任务id
     * @return 查询到的任务
     * @throws Exception 没查询到任务则会抛出异常
     */
    public static Task findTaskById(TaskService taskService,String taskId) throws Exception{
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            String err = "没有找到任务，id = " + taskId;
            throw new Exception(err);
        }
        return task;
    }

    /**
     * 删除流程实例
     * @param taskService 服务
     * @param runtimeService 服务
     * @param taskId 任务id
     * @param reason 删除原因
     * @throws Exception
     */
    public static void deleteProcessInstance(TaskService taskService,RuntimeService runtimeService,String taskId, String reason) throws Exception{
        Task task=findTaskById(taskService,taskId);
        logger.info("删除流程："+task);
        String processInstanceId=task.getProcessInstanceId();
        runtimeService.deleteProcessInstance(processInstanceId,reason);
        //TODO
        logger.info("<流程终止> " +processInstanceId);
    }

    /**
     * 通过用户查询指派给该用户的任务信息
     * @param taskService 服务
     * @param userId 用户id
     * @return
     */
    public static List<Task> queryTaskByUserId(TaskService taskService, String userId){
        return taskService.createTaskQuery().taskAssignee(userId).list();
    }

    /**
     * 通过流程实例id查询任务信息
     * @param taskService 服务
     * @param processInstanceId 流程实例id
     * @return 查询到的任务
     */
    public static List<Task> queryTasksByProcessId(TaskService taskService, String processInstanceId){
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        return tasks;
    }

    /**
     * 通过流程实例查询流程当前任务和指派用户信息
     * @param taskService 服务
     * @param processInstanceId 流程实例id
     * @return Map key：任务对象; value: 任务指派人员
     */
    public static Map<Task,String> queryUsersByProcessId(TaskService taskService, String processInstanceId){
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        Map<Task,String> userMap=new LinkedHashMap<>();
        for (Task task: tasks) {
            userMap.put(task,task.getAssignee());
        }
        return userMap;
    }

    /**
     * 通过流程实例id查询已完成任务
     * @param historyService 服务
     * @param processInstanceId 流程实例id
     * @return
     */
    public static List<HistoricTaskInstance> queryHistoricTaskByProcessId(HistoryService historyService, String processInstanceId){
        return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByHistoricTaskInstanceEndTime().desc().finished().list();
    }

    /**
     * 通过用户id查询该用户已完成的任务
     * @param historyService 服务
     * @param assignee 指派人员
     * @return
     */
    public static List<HistoricTaskInstance> queryHistoricTaskByAssignee(HistoryService historyService, String assignee){
        return historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).finished().orderByHistoricTaskInstanceEndTime().desc().list();
    }

    public static Map<String, Object> queryVariableByProcessId(RuntimeService runtimeService, String processInstanceId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(pi==null){
            String err = "没有找到流程实例，id = " + processInstanceId;
            throw new Exception(err);
        }
        return runtimeService.getVariables(processInstanceId);
    }

}
