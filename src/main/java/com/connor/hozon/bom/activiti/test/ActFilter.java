package com.connor.hozon.bom.activiti.test;

import com.connor.hozon.bom.activiti.Act;
import com.connor.hozon.bom.activiti.bean.DoTaskBean;
import com.connor.hozon.bom.activiti.bean.ProcessBean;
import com.connor.hozon.bom.activiti.bean.ReviewTaskBean;

import javax.servlet.*;
import java.io.IOException;

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
        //添加审核人
        ProcessBean processBean = new ProcessBean("hozon_vwo");
        processBean.addSingleUser("do_start", "black");
        processBean.addMultiUser("review_bom_manager", "admin", "white");
        processBean.addMultiUser("review_project_group", "admin");
        processBean.initRequest(servletRequest);
        // do 任务
        DoTaskBean doTaskBean = new DoTaskBean("22531");
        doTaskBean.initRequest(servletRequest);
        // review 任务
        ReviewTaskBean reviewTaskBean = new ReviewTaskBean("12516", Act.PASS);
        reviewTaskBean.initRequest(servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
