package com.connor.hozon.bom.resources.controller.myListJob.processed;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/13
 * Time: 16:19
 */
@Controller
@RequestMapping(value = "processed")
public class HzProcessedContrller {

    @RequestMapping(value = "ToProcessedForm",method = RequestMethod.GET)
    public String getToProcessedFormToPage(){

        return "myListJob/processed/processedForm";
    }
}
