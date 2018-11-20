package com.connor.hozon.bom.process.controller;

import com.connor.hozon.bom.process.service.ProcessManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
