package com.connor.hozon.bom.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/11
 * Time: 13:31
 */
@Controller
@RequestMapping("updateLog")
public class UpdateLogController {

//    @RequestMapping(value = "V1.0.1",method = RequestMethod.GET)
//    public String getUpdateLogPageVersion1() {
//        return "updateLog/V1.0.1";
//    }
//    @RequestMapping(value = "V1.0.3",method = RequestMethod.GET)
//    public String getUpdateLogPageVersion2(){
//        return "updateLog/V1.0.3";
//    }
//    @RequestMapping(value = "V1.1.0",method = RequestMethod.GET)
//    public String getUpdateLogPageVersion3(){
//        return "updateLog/V1.1.0";
//    }
    @RequestMapping(value = "getUpdateLog",method = RequestMethod.GET)
    public String getUpdateLog(@RequestParam("version") String version){
        return "updateLog/"+version;
    }
}
