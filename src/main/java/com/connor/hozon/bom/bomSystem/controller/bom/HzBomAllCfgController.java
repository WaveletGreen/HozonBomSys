package com.connor.hozon.bom.bomSystem.controller.bom;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/bomAllCfg")
public class HzBomAllCfgController {

    @RequestMapping("/saveBom")
    public JSONObject saveBom(@RequestParam Map<String, String> data){
        return new JSONObject();
    }
}
