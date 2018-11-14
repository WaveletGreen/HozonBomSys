package com.connor.hozon.bom.resources.controller.myListJob.untreated;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/13
 * Time: 16:18
 */
@Controller
@RequestMapping(value = "untreated")
public class HzUntreatedContrller {

    @RequestMapping(value = "ToUntreatedForm",method = RequestMethod.GET)
    public String getToUntreatedFormToPage(){

        return "myListJob/untreated/untreatedForm";
    }
}
