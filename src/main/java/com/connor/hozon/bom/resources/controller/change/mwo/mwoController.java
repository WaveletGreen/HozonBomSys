package com.connor.hozon.bom.resources.controller.change.mwo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * \* User: xulf
 * \* Date: 2018/7/31
 * \* Time: 16:33
 * \
 */
@Controller
@RequestMapping(value = "/mwo")
public class mwoController {
    @RequestMapping(value = "mwoFromList" ,method = RequestMethod.GET)
    public String getMwoFromList(){
        return "changeManage/mwo/mwoBasicInformation";
    }
}
