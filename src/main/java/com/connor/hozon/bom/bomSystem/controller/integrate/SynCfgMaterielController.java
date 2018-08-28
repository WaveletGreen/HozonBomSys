package com.connor.hozon.bom.bomSystem.controller.integrate;

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
public class SynCfgMaterielController extends ExtraIntegrate {
    @Autowired
    SynMaterielCfgService synMaterielCfgService;

    @RequestMapping("/synMaterielAdd")
    public String add(@RequestParam String puid,Model model) {
        HzMaterielCfgBean bean = new HzMaterielCfgBean();
        bean.setPuid(puid);
        JSONObject result = synMaterielCfgService.addMaterielCfg(Collections.singletonList(bean));
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }
    @RequestMapping("/synMaterielDelete")
    public String delete(@RequestParam String puid,Model model) {
        HzMaterielCfgBean bean = new HzMaterielCfgBean();
        bean.setPuid(puid);
        JSONObject result = synMaterielCfgService.deleteMaterielCfg(Collections.singletonList(bean));
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }
    @RequestMapping("/synMaterielUpdata")
    public String updata(@RequestParam String puid,Model model) {
        HzMaterielCfgBean bean = new HzMaterielCfgBean();
        bean.setPuid(puid);
        JSONObject result = synMaterielCfgService.updataMaterielCfg(Collections.singletonList(bean));
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }
}
