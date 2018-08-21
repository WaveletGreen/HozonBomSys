package com.connor.hozon.bom.activiti.bean;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by K on 2018/8/21.
 */
public class ProcessBean {

    private String processKey;
    private Map<String,String> assignees=new HashMap<>();
    private Map<String, List<String>> reviewers=new HashMap<>();

    public ProcessBean(String processKey) {
        this.processKey = processKey;
    }

    public void addAssignee(String taskName,String assignee){
        assignees.put(taskName+"_assign",assignee);
    }

    public void addReviewer(String taskName,String... reviewer){
        List<String> userIds;
        if(reviewers.containsKey(taskName+"_assign")){
            userIds=reviewers.get(taskName+"_assign");
        }else{
            userIds=new ArrayList<>();
            reviewers.put(taskName+"_assign",userIds);
        }
        for (String userId:reviewer) {
            if(!userIds.contains(userId)){
                userIds.add(userId);
            }
        }
    }

    public void initRequest(ServletRequest request){
        if(processKey!=null&&!"".equals(processKey)){
            request.setAttribute("actKey",processKey);
            request.setAttribute("actReviewers",reviewers);
            request.setAttribute("actAssignees",assignees);
        }
    }
}
