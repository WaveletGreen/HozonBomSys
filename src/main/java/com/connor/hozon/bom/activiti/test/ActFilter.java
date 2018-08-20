package com.connor.hozon.bom.activiti.test;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by K on 2018/8/17.
 */
//@Order(1)
//@WebFilter(filterName = "actFilter", urlPatterns = "/act/*")
public class ActFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入过滤器");
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        //添加审核人
        Map<String,List<String>> reviewers=new HashMap<>();
        List<String> assignees1=new ArrayList<>();
        assignees1.add("admin");
        assignees1.add("white");
        reviewers.put("review_bom_manager_assign",assignees1);
        List<String> assignees2=new ArrayList<>();
        assignees2.add("admin");
        reviewers.put("review_project_group_assign",assignees2);
        //添加负责人
        Map<String,String> assignees=new HashMap<>();
        assignees.put("do_start_assign","black");
        request.setAttribute("actKey","hozon_vwo");
        request.setAttribute("actReviewers",reviewers);
        request.setAttribute("actAssignees",assignees);
        // do 任务
        request.setAttribute("actDoTask","405012");
        // review 任务
        request.setAttribute("actReviewTask","412506");
        request.setAttribute("actDecision",1);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
