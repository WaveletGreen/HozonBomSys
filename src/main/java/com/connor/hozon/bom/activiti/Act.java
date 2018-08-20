package com.connor.hozon.bom.activiti;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by K on 2018/8/17.
 */
@RestController
@RequestMapping("/act")
public interface Act{

    /** 通过 */
    int PASS=1;
    /** 不通过 */
    int NOT_PASS =0;
    /** 结束流程 */
    int CANCEL=-1;

    /**
     * 创建流程实例
     * @param key 流程id
     * @param reviewers 审核人
     * @param assignees 任务负责人
     * @return 页面跳转
     */
    @RequestMapping(value = "/createProcess", method = RequestMethod.GET)
    ModelAndView createProcess(String key, Map<String, List<String>> reviewers, Map<String, String> assignees);

    /**
     * 运行DO任务
     * @param taskId 任务id
     * @return
     */
    @RequestMapping(value = "/runDoTask",method = RequestMethod.GET)
    ModelAndView runDoTask(String taskId);

    /**
     * 运行审核任务
     * @param taskId 任务id
     * @param decision 审核结果
     * @return
     */
    @RequestMapping(value = "/runReviewTask",method = RequestMethod.GET)
    ModelAndView runReviewTask(String taskId, int decision);

    /**
     * 示例方法：通过用户id查询任务
     * @param taskService taskService 通过autowire获取
     * @param user 用户id
     * @return
     */
    List<Task> queryTaskByUserId(TaskService taskService, String user);

    /**
     * 示例方法：通过流程实例id查询任务和用户
     * @param taskService taskService 通过autowire获取
     * @param processInstanceId 流程实例id
     * @return
     */
    List<Task> queryTasksByProcessId(TaskService taskService, String processInstanceId);

    /**
     * 示例方法：通过流程实例id查询任务
     * @param taskService taskService 通过autowire获取
     * @param processInstanceId 流程实例id
     * @return
     */
    Map<Task,String> queryUsersByProcessId(TaskService taskService, String processInstanceId);

  /*  @RequestMapping(value = "/getProcessDiagramByProcess",method = RequestMethod.GET)
    ModelAndView getProcessDiagram(String folder, String processInstanceId);

    @RequestMapping(value = "/getProcessDiagramByTask",method = RequestMethod.GET)
    ModelAndView getProcessDiagram(String folder, Task task);*/

    @RequestMapping(value = "/queryTasksByUser",method = RequestMethod.GET)
    ModelAndView queryTasks(String user);

}
