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
    private Map<String,String> singleUserAssign =new HashMap<>();
    private Map<String, List<String>> multiUserAssign =new HashMap<>();

    public ProcessBean(String processKey) {
        this.processKey = processKey;
    }

    /**
     * 添加唯一用户，根据流程节点是否配置了多实例
     * @param taskName
     * @param assignee
     */
    public void addSingleUser(String taskName, String assignee){
        singleUserAssign.put(taskName+"_assign",assignee);
    }

    /**
     * 添加多用户，根据流程节点是否配置了多实例
     * @param taskName
     * @param reviewer
     */
    public void addMultiUser(String taskName, String... reviewer){
        List<String> userIds;
        if(multiUserAssign.containsKey(taskName+"_assign")){
            userIds= multiUserAssign.get(taskName+"_assign");
        }else{
            userIds=new ArrayList<>();
            multiUserAssign.put(taskName+"_assign",userIds);
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
            request.setAttribute("actReviewers", multiUserAssign);
            request.setAttribute("actAssignees", singleUserAssign);
        }
    }
}
