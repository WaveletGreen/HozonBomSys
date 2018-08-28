package com.connor.hozon.bom.bomSystem.controller.bom;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class HzBomAllCfgController {

//      /**搜索全部特性，并经过P_CFG0_OBJECT_ID 升序排序*/
//        QueryBase queryBase = new QueryBase();
//        queryBase.setSort("P_CFG0_OBJECT_ID");
//        HzCfg0Service hzCfg0Service ;
//        hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, queryBase);

//        获取该项目下的所有车型模型
//        hzCfg0ModelService.doSelectByProjectPuid(projectPuid);
    @RequestMapping("/bomAllCfgSave")
    public JSONObject Save(@RequestParam Map<String, String> data){
        return new JSONObject();
    }
}
