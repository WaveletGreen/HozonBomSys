package cn.net.connor.hozon.controller;

import cn.net.connor.hozon.services.request.TestRequestDTO;
import cn.net.connor.hozon.services.service.TestService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

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

    /**
     * 测试DTO对象转成数据对象
     * @param testRequestDTO
     * @return
     */
    @RequestMapping("testDaoMapStruct")
    @ResponseBody
    public JSONObject testDaoMapStruct(@Valid TestRequestDTO testRequestDTO) {
        return testService.reflect(testRequestDTO);
    }

    @RequestMapping("getErrorView")
    public String returnError() {
        return "error";
    }


}
