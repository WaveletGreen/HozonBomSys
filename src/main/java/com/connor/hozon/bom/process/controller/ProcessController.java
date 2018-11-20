package com.connor.hozon.bom.process.controller;

import com.connor.hozon.bom.process.service.ProcessManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程控制
 */
@Controller
@RequestMapping("process")
public class ProcessController {
    @Autowired
    ProcessManagerService processManagerService;

    @RequestMapping("getAuditorPage")
    public String getAuditorPage() {
        return "change/changeForm/changeSelectAuditor";
    }

    @RequestMapping(value = "doCheck", method = RequestMethod.POST)
    @ResponseBody
    public Object doCheck(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        if (params == null || params.isEmpty() || !params.containsKey("agreement")) {
            result.put("status", false);
            result.put("msg", "入参无效");
            return result;
        }
        switch (params.get("agreement")) {
            case "1":
                return processManagerService.release(Long.parseLong(params.get("id")));
            case "0":
                return processManagerService.interrupt(Long.parseLong(params.get("id")));
            default:
                result.put("status", false);
                result.put("msg", "审核失败");
                return result;
        }
    }

}
