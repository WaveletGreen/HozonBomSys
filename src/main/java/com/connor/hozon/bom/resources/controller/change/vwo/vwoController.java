package com.connor.hozon.bom.resources.controller.change.vwo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * \* User: xulf
 * \* Date: 2018/7/24
 * \* Time: 14:07
 * \
 */
@Controller
@RequestMapping(value = "/vwo")
public class vwoController {



    @RequestMapping(value = "vwoFromList" ,method = RequestMethod.GET)
    public String getVwoFromList(){
        return "changeManage/vwo/vwoBasicInformation";
    }

//    @RequestMapping(value = "vwoChangeDescription" ,method = RequestMethod.GET)
//    public String getvwoChangeDescription(){
//        return "changeManage/vwo/vwoChangeDescription";
//    }
}