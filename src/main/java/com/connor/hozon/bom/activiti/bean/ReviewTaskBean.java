package com.connor.hozon.bom.activiti.bean;

import javax.servlet.ServletRequest;

/**
 * Created by K on 2018/8/21.
 */
public class ReviewTaskBean {
    private String taskId;
    private int decision;

    public ReviewTaskBean(String taskId, int decision) {
        this.taskId = taskId;
        this.decision=decision;
    }

    public void initRequest(ServletRequest request){
        if(taskId!=null&&!"".equals(taskId)){
            request.setAttribute("actReviewTask",taskId);
            request.setAttribute("actDecision",decision);
        }
    }
}
