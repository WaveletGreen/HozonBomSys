package com.connor.hozon.bom.bomSystem.controller.bom;

import com.connor.hozon.bom.bomSystem.service.bom.HzBomDataService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0BomLineOfModelService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loadBom")
public class HzLoadBomDataController {
    @Autowired
    HzCfg0BomLineOfModelService hzCfg0BomLineOfModelService;
    @Autowired
    HzBomDataService hzBomDataService;

    @RequestMapping(value = "/loadCfg0BomLineOfModel", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject loadCfg0BomLineOfModel(@RequestParam String bdf) {
        return hzCfg0BomLineOfModelService.parse(bdf);
    }

    @RequestMapping(value = "/loadByID", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray getLineRecords(@RequestParam String bdf) {
        return hzBomDataService.load(bdf);
    }
}
