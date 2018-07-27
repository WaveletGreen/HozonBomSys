package com.connor.hozon.bom.bomSystem.controller.bom;

import com.connor.hozon.bom.bomSystem.service.bom.HzBomDataService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0BomLineOfModelService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0ModelService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.cfg.HzCfg0Record;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/loadBom")
public class HzLoadBomDataController {
    private final HzCfg0BomLineOfModelService hzCfg0BomLineOfModelService;
    private final HzBomDataService hzBomDataService;

    @Autowired
    HzCfg0Service hzCfg0Service;

    public HzLoadBomDataController(HzCfg0BomLineOfModelService hzCfg0BomLineOfModelService, HzBomDataService hzBomDataService) {
        this.hzCfg0BomLineOfModelService = hzCfg0BomLineOfModelService;
        this.hzBomDataService = hzBomDataService;
    }

    @RequestMapping(value = "/loadCfg0BomLineOfModel", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject loadCfg0BomLineOfModel(@RequestParam String bdf) {
        return hzCfg0BomLineOfModelService.parse(bdf);
    }

    /**
     * 获取列信息
     *
     * @param projectPuid 项目的puid
     * @return
     */
    @RequestMapping(value = "/loadColumns", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray loadColumns(@RequestParam String projectPuid) {
        return hzBomDataService.doLoadColumns(projectPuid);
    }

    /**
     * 下载所有的bom行信息
     *
     * @param projectPuid 项目的puid
     * @return
     */
    @RequestMapping(value = "/loadByID", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray getLineRecords(@RequestParam String projectPuid) {
        return hzBomDataService.load(projectPuid);
    }

    /**
     * 下载所有的bom行信息
     *
     * @param projectPuid 项目的puid
     * @return
     */
    @RequestMapping(value = "/loadByID2", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray getLineRecords2(@RequestParam String projectPuid) {
        JSONArray array = new JSONArray();
        array.addAll(loadColumns(projectPuid));
        array.addAll(hzBomDataService.load(projectPuid));
        return array;
    }

    @RequestMapping(value = "reflectTo2YPage", method = RequestMethod.GET)
    public String reflectTo2YPage(@RequestParam("projectPuid") String projectPuid, Model model) {
        if (null == projectPuid || "".equals(projectPuid)) {
            model.addAttribute("msg", "请选择1个项目进行操作");
            return "errorWithEntity";
        }
        List<HzCfg0Record> features = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid);
        List<HzBomLineRecord> lines = hzBomDataService.doSelect2YByProjectPuid(projectPuid);
        model.addAttribute("features", features);
        model.addAttribute("lines", lines);
        model.addAttribute("action", "./loadBom/reflect2Y");
        return "bom/reflectTo2Y";
    }

    @RequestMapping(value = "reflect2Y", method = RequestMethod.POST)
    @ResponseBody
    public boolean reflect2Y(@RequestBody Map<String, String> params) {
        if (params != null) {
            return true;
        } else {
            return false;
        }
    }
}
