package com.connor.hozon.bom.bomSystem.controller.bom;

import com.connor.hozon.bom.bomSystem.service.cfg.HzBomAllCfgService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/bomAllCfg")
public class HzBomAllCfgController {

    @Autowired
    private HzBomAllCfgService hzBomAllCfgService;

    @RequestMapping("/saveBom")
    public JSONObject saveBom(@RequestParam Map<String, String> data){
        return new JSONObject();
    }

    @RequestMapping("/loadCfg0BomLineOfModel")
    @ResponseBody
    public JSONObject loadCfg0BomLineOfModel(@RequestParam String bdf){
        return hzBomAllCfgService.parse(bdf);
    }
}
