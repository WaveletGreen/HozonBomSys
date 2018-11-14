package com.connor.hozon.bom.resources.controller.myListJob.myApplication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/13
 * Time: 16:21
 */
@Controller
@RequestMapping(value = "myApplication")
public class HzMyApplicationContrller {

    @RequestMapping(value = "ToMyApplicationForm",method = RequestMethod.GET)
    public String getToMyApplicationFormToPage(){

        return "myListJob/myApplication/myApplicationForm";
    }
}
