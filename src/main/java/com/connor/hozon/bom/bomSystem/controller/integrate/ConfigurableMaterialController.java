package com.connor.hozon.bom.bomSystem.controller.integrate;

import com.connor.hozon.bom.bomSystem.service.integrate.SynConfigurableMaterialService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("cfgMateriel")
public class ConfigurableMaterialController {

    @Autowired
    private SynConfigurableMaterialService synConfigurableMaterialService;

    @RequestMapping("/configurableMaterial")
    @ResponseBody
    public JSONObject add(String[] puids, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid){
        return synConfigurableMaterialService.addConfigurableMaterial(puids, cfg0MainPuids, modeBasiceDetails, projectPuid);
    }
}
