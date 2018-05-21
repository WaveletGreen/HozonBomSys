package com.connor.hozon.bom.banchOfFancyears.controller.bom;

import com.connor.hozon.bom.banchOfFancyears.dao.cfg.HzCfg0BomLineOfModelDao;
import com.connor.hozon.bom.banchOfFancyears.service.bom.HzBomDataService;
import com.connor.hozon.bom.banchOfFancyears.service.cfg.HzCfg0BomLineOfModelService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.HzCfg0BomLineOfModel;
import share.bean.PreferenceSetting;
import share.bean.RedisBomBean;
import sql.BaseSQLUtil;
import sql.IBaseSQLUtil;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.HzPreferenceSetting;
import sql.redis.SerializeUtil;

import javax.validation.constraints.NotNull;
import java.util.*;

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
