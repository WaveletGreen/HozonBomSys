package com.connor.hozon.bom.bomSystem.controller.bom;

import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.HzBomAllCfgService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzFullCfgMain;

import java.util.Map;
import java.util.Set;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

@Controller
@RequestMapping("/bomAllCfg")
public class HzBomAllCfgController {

    @Autowired
    private HzBomAllCfgService hzBomAllCfgService;

    @RequestMapping("/saveBom")
    public JSONObject saveBom(@RequestParam Map<String, String> data){
        return new JSONObject();
    }

//    @RequestMapping("/savePoint")
//    public JSONObject savePoint(@RequestBody Map<String, Map<String,String>> data){
//        return new JSONObject();
//    }

    @RequestMapping("/loadCfg0BomLineOfModel")
    @ResponseBody
    public JSONObject loadCfg0BomLineOfModel(@RequestParam String bdf){
        return hzBomAllCfgService.parse(bdf);
    }

    @RequestMapping("/saveOneRow")
    @ResponseBody
    public JSONObject saveOneRow(String bomLinePuid, String cfgPuid){
        return hzBomAllCfgService.saveOneRow(bomLinePuid,cfgPuid);
    }

    @RequestMapping("/savePoint")
    @ResponseBody
    public JSONObject savePoint(@RequestBody Map<String, Map<String,String>> data){
        return hzBomAllCfgService.savePoint(data);
    }

    @RequestMapping("/deleteModel")
    @ResponseBody
    public JSONObject deleteModel(@RequestParam String modelId){
        return hzBomAllCfgService.deleteModel(modelId);
    }

    /**
     * @return
     * @Autor Fancyears
     */
    @RequestMapping("setStagePage")
    public String setStagePage(@RequestParam String projectUid, Model model) {
        if (!checkString(projectUid)) {
            model.addAttribute("msg", "请选择一个项目进行操作");
            return "errorWithEntity";
        }
        HzFullCfgMain fullCfgMain = hzBomAllCfgService.getFullCfgMain(projectUid);
        if (fullCfgMain == null) {
            model.addAttribute("msg", "该项目下没有全配置BOM一级清单表，请先添加2Y层和特性值再进行操作");
            return "errorWithEntity";
        }
        String releaseDate = fullCfgMain.getEffectiveDate() == null ? "" : DateStringHelper.dateToString(fullCfgMain.getEffectiveDate());
        model.addAttribute("entity", fullCfgMain);
        model.addAttribute("releaseDate", releaseDate);
        model.addAttribute("action", "./bomAllCfg/setStage");
        return "bom/setStagePage";
    }

    @RequestMapping("setStage")
    @ResponseBody
    public JSONObject setStage(@RequestBody Map<String, String> params) {
        return hzBomAllCfgService.setStage(params);
    }

    @RequestMapping("query2YCfg")
    @ResponseBody
    public JSONObject query2YCfg(@RequestParam String projectPuid) {
        return hzBomAllCfgService.query2YCfg(projectPuid);
    }
}
