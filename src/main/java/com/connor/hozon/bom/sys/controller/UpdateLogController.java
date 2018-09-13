package com.connor.hozon.bom.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/11
 * Time: 13:31
 */
@Controller
@RequestMapping("updateLog")
public class UpdateLogController {

    @RequestMapping(value = "V1.1.1",method = RequestMethod.GET)
    public String getUpdateLogPageVersion1() {
        return "updateLog/V1.1.1";
    }
    @RequestMapping(value = "V1.1.2",method = RequestMethod.GET)
    public String getUpdateLogPageVersion2(){
        return "updateLog/V1.1.2";
    }
    @RequestMapping(value = "V1.1.3",method = RequestMethod.GET)
    public String getUpdateLogPageVersion3(){
        return "updateLog/V1.1.3";
    }
}
