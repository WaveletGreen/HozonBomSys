package com.connor.hozon.bom.resources.controller.bom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * \* User: xulf
 * \* Date: 2018/6/4
 * \* Time: 13:01
 * \
 */
@Controller
@RequestMapping(value = "/ebom")
public class HzEbomController {

@RequestMapping(value = "/epl",method = RequestMethod.GET)
    public void inva(){
    System.out.println("哈哈哈哈哈");
    }
}
