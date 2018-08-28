package com.connor.hozon.bom.bomSystem.controller.integrate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

@Controller
public class SynBomAllCfgController {

    @RequestMapping("/bomAllCfgSave")
    public void save(@RequestParam Map<String, String> data){
        Set<String> keys = data.keySet();
        for(String key : keys){
            System.out.println(data.get(key));
        }
    }
}
