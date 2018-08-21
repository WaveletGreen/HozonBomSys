package com.connor.hozon.bom.activiti.bean;

import javax.servlet.ServletRequest;

/**
 * Created by K on 2018/8/21.
 */
public class DoTaskBean {
    private String taskId;

    public DoTaskBean(String taskId) {
        this.taskId = taskId;
    }

    public void initRequest(ServletRequest request){
        if(taskId!=null&&!"".equals(taskId)){
            request.setAttribute("actDoTask",taskId);
        }
    }
}
