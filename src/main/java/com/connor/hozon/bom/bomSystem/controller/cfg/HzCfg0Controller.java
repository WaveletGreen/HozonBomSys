package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzCfg0Record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/6 16:55
 */
@Controller
@RequestMapping("/cfg0")
public class HzCfg0Controller {
    private final HzCfg0Service hzCfg0Service;

    @Autowired
    public HzCfg0Controller(HzCfg0Service hzCfg0Service) {
        this.hzCfg0Service = hzCfg0Service;
    }

    @RequestMapping("/loadFeature")
    @ResponseBody
    public  Map<String,Object> loadCfg0(@RequestParam("projectPuid") String projectPuid) {
        Map<String,Object> result=new HashMap<>();
        List<HzCfg0Record> records=hzCfg0Service.doLoadCfgByProjectPuid(projectPuid);
        result.put("totalCount", records.size());
        result.put("result", records);
        return result;
    }

    @RequestMapping("/loadRelevance")
    @ResponseBody
    public  Map<String,Object> loadRelevance(@RequestParam("projectPuid") String projectPuid) {
        Map<String,Object> result=new HashMap<>();
        List<HzCfg0Record> records=hzCfg0Service.doLoadCfgByProjectPuid(projectPuid);
        result.put("totalCount", records.size());
        result.put("result", records);
        return result;
    }
}
