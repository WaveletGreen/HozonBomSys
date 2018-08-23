package com.connor.hozon.bom.activiti.test;

import com.connor.hozon.bom.activiti.ActUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by K on 2018/8/9.
 */
public class Test {
    public static void main(String[] args) {
        ActService.initialize(); //初始化流程引擎和服务
        testApi();
    }

    private static void testApi() {
        try {
            Map<String, Object> res = ActUtil.queryVariableByProcessId(ActService.runtimeService, "140039");
            for (String k:res.keySet()) {
                System.out.println(k+"=="+res.get(k));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<HistoricTaskInstance> res = ActUtil.queryHistoricTaskByAssignee(ActService.historyService,"admin");
        for (HistoricTaskInstance h:res) {
            System.out.println(h.getName()+"|"+h.getEndTime());
        }
    }


    private static void showVars(){
        ProcessInstance pi = ActService.runtimeService.createProcessInstanceQuery().processInstanceId("280005").singleResult();
        Map<String, Object> vars = pi.getProcessVariables();
        for (String k:vars.keySet()) {
            System.out.println(k+"=="+vars.get(k));
        }
    }

    private static void showProcessDiagram() {
        ProcessInstance pi = ActService.runtimeService.createProcessInstanceQuery().processInstanceId("280005").singleResult();
        ProcessDefinition pde = ActService.repositoryService.getProcessDefinition(pi.getProcessDefinitionId());
        if(pde!=null&&pde.hasGraphicalNotation()){
            System.out.println("有流程图");
            BpmnModel bpmnModel = ActService.repositoryService.getBpmnModel(pde.getId());
            ProcessDiagramGenerator processDiagramGenerator=new DefaultProcessDiagramGenerator();
            try {
                InputStream is=processDiagramGenerator.generateDiagram(bpmnModel,"png",ActService.runtimeService.getActiveActivityIds(pi.getId())
                        ,new ArrayList<>(),"宋体","宋体","宋体",null,1.0);
                byte[] buffer=new byte[is.available()];
                is.read(buffer);
                File imageFile=new File("D:\\projects\\img-"+pi.getId()+".png");
                FileOutputStream fileOutputStream=new FileOutputStream(imageFile);
                fileOutputStream.write(buffer);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void test2(){
        List<Task> tasks = ActService.taskService.createTaskQuery().taskAssignee("admin,white").list();
        System.out.println(tasks.size());
    }

    private static void runTask() {
       /* Map<String, Object> map = new HashMap<>();
        map.put("decision_bom", true);
        map.put("decision_purchase", true);
        map.put("decision_quality", true);
        map.put("decision_design", true);
        map.put("decision_logistics", true);
        map.put("decision_project_group", true);
        map.put("decision_bom_manager", true);
        runTaskV2("317501", map);*/
       runTask("317501");
    }

    private static void startProcessV2() {
        System.out.println("当前流程实例数量：" + ActService.runtimeService.createProcessInstanceQuery().count());
        Map<String, Object> variables = new HashMap<>();
        variables.put("employeeName", "kk");
        variables.put("numberOfDays", 6);
        variables.put("vacationMotivation", "I want a summer holidy");
        ActService.runtimeService.startProcessInstanceByKey("hozon_vwo");
        System.out.println("当前流程实例数量：" + ActService.runtimeService.createProcessInstanceQuery().count());
    }

    private static void runTaskV2(String processId, Map<String, Object> map) {
        List<Task> taskList = ActService.taskService.createTaskQuery().processInstanceId(processId).list();
        System.out.println("流程当前任务数量：" + taskList.size());
        if (taskList.size() == 0) {
            return;
        }
        //ActService.taskService.complete(taskList.get(0).getId(),map);
        for (Task task : taskList) {
            List<String> assignees=new ArrayList<>();
            assignees.add("admin");
            assignees.add("white");
            ActService.taskService.setVariable(task.getId(),"assign_bom_manager",assignees);
            System.out.println("完成任务："+task.getName());
            try {
                ActService.taskService.complete(task.getId(), map);
            } catch (Exception e) {
                System.out.println("1111:"+e.getMessage());
                //e.printStackTrace();
            }
        }
        System.out.println("end");
    }


    private static void deployProcess() {
        System.out.println("当前流程定义数量：" + ActService.repositoryService.createProcessDefinitionQuery().count());
        ActService.repositoryService.createDeployment().addClasspathResource("processes/hozon_VWO.bpmn20.xml").deploy();
        System.out.println("当前流程定义数量：" + ActService.repositoryService.createProcessDefinitionQuery().count());
    }

    private static void claimTask(Task task, String user) {
        ActService.taskService.claim(task.getId(), user);
    }


    private static void taskList(String user, String group) {
        List<Task> tasks = ActService.taskService.createTaskQuery().taskCandidateUser(user).list();
        System.out.println(tasks.size());
        tasks = ActService.taskService.createTaskQuery().taskCandidateGroup(group).list();
        System.out.println(tasks.size());
    }

    private static void test() {
        System.out.println("当前流程实例数量：" + ActService.runtimeService.createProcessInstanceQuery().count());
        ActService.runtimeService.startProcessInstanceByKey("k0Process");
        System.out.println("当前流程实例数量：" + ActService.runtimeService.createProcessInstanceQuery().count());

    }

    private static void startProcess() {
        Map<String, Object> map = new HashMap<>();
        map.put("apply", "kk");
        ProcessInstance pi = ActService.runtimeService.startProcessInstanceByKey("leave", "流程1", map);
        System.out.println("流程名称：" + pi.getName());
        System.out.println("流程定义名称：" + pi.getProcessDefinitionName());
        System.out.println("流程id：" + pi.getId());
        System.out.println("流程定义id：" + pi.getProcessDefinitionId());
        //System.out.println("Execution id:"+pi.getSuperExecutionId());
    }


    private static void runTask(String processId) {
        List<Task> taskList = ActService.taskService.createTaskQuery().processInstanceId(processId).list();
        System.out.println("找到任务数量：" + taskList.size());
        if (taskList.size() == 0) {
            return;
        }
        Task task = taskList.get(0);
        System.out.println("执行任务：" + task.getName());
        ActService.taskService.complete(task.getId());
    }

    private static void queryTasks() {
        List<Task> tasks = ActService.taskService.createTaskQuery().taskCandidateGroup("management").list();
        for (Task task : tasks) {
            System.out.println("Task：" + task.getName() + "|" + task.getId());
        }
    }

    private static void query() {
        List<Task> tasks = ActService.taskService.createNativeTaskQuery()
                .sql("SELECT * FROM " + ActService.managementService.getTableName(Task.class) + " T WHERE T.NAME_ = #{taskName}")
                .parameter("taskName", "提交申请")
                .list();
        System.out.println(tasks.size());
        for (Task t : tasks) {
            System.out.println(t.getId());
        }
    }


}
