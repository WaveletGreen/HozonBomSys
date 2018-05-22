package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0ModelColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 10:52
 */
@RequestMapping("/modelColor")
public class HzCfg0ModelColorController {

    @Autowired
    HzCfg0ModelColorService hzCfg0ModelColorService;

    @RequestMapping("/loadAll")
    @ResponseBody
    public Map<String, Object> loadAll() {
        return hzCfg0ModelColorService.doLoadAll();
    }



}
