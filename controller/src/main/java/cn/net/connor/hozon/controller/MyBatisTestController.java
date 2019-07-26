package cn.net.connor.hozon.controller;

import cn.net.connor.hozon.services.TestService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/7/25 13:12
 * @Modified By:
 */
@Controller
@RequestMapping("test")
public class MyBatisTestController {

    @Autowired
    TestService testService;

    @RequestMapping("testDao")
    @ResponseBody
    public JSONObject testDao(@RequestParam int type, @RequestParam long id, @RequestParam String name) {
        return testService.test(type, id, name);
    }

    @RequestMapping("getErrorView")
    public String returnError() {
        return "error";
    }
}
