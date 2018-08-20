package com.connor.hozon.bom.activiti.serviceTask;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * Created by K on 2018/8/16.
 */
public class PublishTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        //String var= (String) delegateExecution.getVariable("input");
        //TODO
        System.out.println("<发布>");
    }
}
