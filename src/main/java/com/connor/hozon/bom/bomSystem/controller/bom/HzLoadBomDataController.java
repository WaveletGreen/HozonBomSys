package com.connor.hozon.bom.bomSystem.controller.bom;

import com.connor.hozon.bom.bomSystem.service.bom.HzBomDataService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0BomLineOfModelService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loadBom")
public class HzLoadBomDataController {
    private final HzCfg0BomLineOfModelService hzCfg0BomLineOfModelService;
    private final HzBomDataService hzBomDataService;

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
}
