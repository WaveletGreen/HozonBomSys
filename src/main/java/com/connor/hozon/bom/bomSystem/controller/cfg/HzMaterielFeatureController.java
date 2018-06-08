package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OptionFaamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/8 17:03
 */
@Controller
@RequestMapping("/materiel")
public class HzMaterielFeatureController {
    private final
    HzCfg0OptionFaamilyService hzCfg0OptionFaamilyService;

    @Autowired
    public HzMaterielFeatureController(HzCfg0OptionFaamilyService hzCfg0OptionFaamilyService) {
        this.hzCfg0OptionFaamilyService = hzCfg0OptionFaamilyService;
    }

    @RequestMapping("/loadColumnByProjectPuid")
    @ResponseBody
    public List<String> getColumn(@RequestParam("projectPuid") String projectPuid) {
        return hzCfg0OptionFaamilyService.doGetColumn(projectPuid);
    }

}
