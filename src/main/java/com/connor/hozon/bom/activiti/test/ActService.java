package com.connor.hozon.bom.activiti.test;

import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.history.HistoryLevel;

/**
 * Created by K on 2018/8/9.
 * 测试用，初始化流程引擎环境
 */

public class ActService {
    public static ProcessEngine processEngine;
    public static RuntimeService runtimeService;
    public static RepositoryService repositoryService;
    public static TaskService taskService;
    public static ManagementService managementService;
    public static IdentityService identityService;
    public static HistoryService historyService;
    public static FormService formService;
    public static DynamicBpmnService dynamicBpmnService;

    private static ProcessEngine createEngine() {
        ProcessEngine engine= ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
                .setJdbcDriver("oracle.jdbc.driver.OracleDriver")
                .setJdbcUrl("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST = 192.168.9.234)(PORT = 1521))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=tc)))")
                .setJdbcUsername("activiti")
                .setJdbcPassword("infodba")
                .setAsyncExecutorActivate(false)
                .setHistoryLevel(HistoryLevel.AUDIT)
                .setActivityFontName("宋体")
                .setLabelFontName("宋体")
                .buildProcessEngine();
        return engine;
    }

    public static void initialize(){
        processEngine= createEngine();
        runtimeService = processEngine.getRuntimeService();
        repositoryService = processEngine.getRepositoryService();
        taskService = processEngine.getTaskService();
        managementService = processEngine.getManagementService();
        identityService = processEngine.getIdentityService();
        historyService = processEngine.getHistoryService();
        formService = processEngine.getFormService();
        dynamicBpmnService = processEngine.getDynamicBpmnService();
    }

    private static void addUser(String userId,String userName,String groupId,String groupName){

        User user=identityService.createUserQuery().userId(userId).singleResult();
        if(user==null){
            user=identityService.newUser(userId);
            user.setLastName(userName);
            identityService.saveUser(user);
        }
        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if(group==null){
            group=identityService.newGroup(groupId);
            group.setName(groupName);
            identityService.saveGroup(group);
        }
        identityService.createMembership(userId,groupId);
    }
}
