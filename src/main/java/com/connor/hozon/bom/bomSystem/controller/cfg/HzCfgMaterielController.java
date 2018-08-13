package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.service.integrate.SynMaterielCfgService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzMaterielCfgBean;

import java.util.*;

@Controller
@RequestMapping("/cfgMateriel")
public class HzCfgMaterielController {
    @Autowired
    SynMaterielCfgService synMaterielCfgService;

    @RequestMapping("/synMateriel")
    @ResponseBody
    public String modPage(@RequestParam String puid) throws Exception {
        HzMaterielCfgBean bean = new HzMaterielCfgBean();
        bean.setPuid(puid);
        JSONObject result = synMaterielCfgService.addFeature(Collections.singletonList(bean));

        return result.toString();
    }
}
