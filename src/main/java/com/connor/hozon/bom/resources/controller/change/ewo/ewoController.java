package com.connor.hozon.bom.resources.controller.change.ewo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * \* User: xulf
 * \* Date: 2018/7/30
 * \* Time: 15:50
 * \
 */
@Controller
@RequestMapping(value = "/ewo")
public class ewoController {
    @RequestMapping(value = "ewoFromList" ,method = RequestMethod.GET)
    public String getEwoFromList(){
        return "changeManage/ewo/ewoBasicInformation";
    }
}
