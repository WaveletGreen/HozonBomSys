package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0ColorSetService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0ModelColorService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OptionFaamilyService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzCfg0ColorSet;
import sql.pojo.cfg.HzCfg0MainRecord;
import sql.pojo.cfg.HzCfg0ModelColor;
import sql.redis.SerializeUtil;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 10:52
 */
@Controller
@RequestMapping("/modelColor")
public class HzCfg0ModelColorController {
    private final HzCfg0ModelColorService hzCfg0ModelColorService;
    private final HzCfg0OptionFaamilyService hzCfg0OptionFaamilyService;
    private final HzCfg0ColorSetService hzCfg0ColorSetService;
    private final HzCfg0MainService hzCfg0MainService;
    private Logger logger;

    @Autowired
    public HzCfg0ModelColorController(HzCfg0ModelColorService hzCfg0ModelColorService, HzCfg0OptionFaamilyService hzCfg0OptionFaamilyService, HzCfg0ColorSetService hzCfg0ColorSetService, HzCfg0MainService hzCfg0MainService) {
        this.hzCfg0MainService = hzCfg0MainService;
        logger = LoggerFactory.getLogger(this.getClass());
        this.hzCfg0ModelColorService = hzCfg0ModelColorService;
        this.hzCfg0OptionFaamilyService = hzCfg0OptionFaamilyService;
        this.hzCfg0ColorSetService = hzCfg0ColorSetService;
    }

    @RequestMapping(value = "/loadAll", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadAll() {
        return hzCfg0ModelColorService.doLoadAll();
    }

    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage(String pCfg0MainRecordOfMC, Model model) {
        List<String> columnList = hzCfg0OptionFaamilyService.doGetColumn(pCfg0MainRecordOfMC);
        List<HzCfg0ColorSet> colorList = hzCfg0ColorSetService.doGetAll();
        //添加一个无色
        HzCfg0ColorSet set = new HzCfg0ColorSet();
        set.setpColorName("-");
        set.setpColorCode("-");
        set.setpColorComment("-");
        set.setpColorOfSet("-");
        set.setPuid("-");
        colorList.add(0, set);
        model.addAttribute("colorList", colorList);
        model.addAttribute("columnList", columnList);
        model.addAttribute("pCfg0MainRecordOfMC", pCfg0MainRecordOfMC);
        return "cfg/modelColorCfg/addModelColorCfg";
    }

    @RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
    public String modifyPage(String puid, Model model) {
        HzCfg0ModelColor color = new HzCfg0ModelColor();
        color.setPuid(puid);
        color = hzCfg0ModelColorService.doGetById(color);
        if (color == null) {
            return "cfg/modelColorCfg/addModelColorCfg";
        }

        List<String> columnList = hzCfg0OptionFaamilyService.doGetColumn(color.getpCfg0MainRecordOfMC());
        List<HzCfg0ColorSet> colorList = hzCfg0ColorSetService.doGetAll();

        ArrayList<String> orgValue = new ArrayList<>();

        Object obj = SerializeUtil.unserialize(color.getpColorfulMapBlock());
        if (obj instanceof LinkedHashMap) {
            LinkedHashMap<String, String> _map = (LinkedHashMap) obj;
            System.out.println();
            _map.forEach((key, value) ->
                    orgValue.add(value)
            );
        } else {
            columnList.forEach(x -> orgValue.add("-"));
        }
        //添加一个无色
        HzCfg0ColorSet set = new HzCfg0ColorSet();
        set.setpColorName("-");
        set.setpColorCode("-");
        set.setpColorComment("-");
        set.setpColorOfSet("-");
        set.setPuid("-");
        colorList.add(0, set);
        model.addAttribute("colorList", colorList);
        model.addAttribute("columnList", columnList);
        model.addAttribute("entity", color);
        model.addAttribute("orgValue", orgValue);
        return "cfg/modelColorCfg/updateModelColorCfg";
    }

    @RequestMapping(value = "/updateColorModel", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateColorModel(@RequestBody LinkedHashMap<String, String> form) {
        if (form != null && form.size() > 0) {
            HzCfg0ModelColor color = new HzCfg0ModelColor();
            form.forEach((key, value) -> {
                switch (key) {
                    case "puid":
                        color.setPuid(form.get("puid"));
                        break;
                    case "pDescOfColorfulModel":
                        color.setpDescOfColorfulModel(value);
                        break;
                    case "pCodeOfColorfulModel":
                        color.setpCodeOfColorfulModel(value);
                        break;
                    default:
                        color.getMapOfCfg0().put(key, value);
                        break;
                }
            });
            byte[] _b = SerializeUtil.serialize(color.getMapOfCfg0());
            color.setpColorfulMapBlock(_b);
            return hzCfg0ModelColorService.doUpdateOne(color);
        } else return false;
    }

    @RequestMapping(value = "/getColumn", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getColumn(HzCfg0ModelColor color) {
        JSONObject object = new JSONObject();
        List<String> column;
        if (color.getpCfg0MainRecordOfMC() == null) {
            object.put("status", false);
        } else {
            object.put("status", true);
            column = hzCfg0OptionFaamilyService.doGetColumn(color.getpCfg0MainRecordOfMC());
            object.put("data", column);
        }
        return object;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteByBatch(@RequestBody List<HzCfg0ModelColor> colors) {
        return hzCfg0ModelColorService.doDelete(colors) > 0 ? true : false;
    }

    @RequestMapping(value = "/saveColorModel", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveColorModel(@RequestBody LinkedHashMap<String, String> form) {
        if (form != null) {
            HzCfg0ModelColor modelColor = new HzCfg0ModelColor();
            form.forEach((key, value) -> {
                if ("pCodeOfColorfulModel".equals(key)) {
                    modelColor.setpCodeOfColorfulModel(value);
                } else if ("pDescOfColorfulModel".equals(key)) {
                    modelColor.setpDescOfColorfulModel(value);
                } else if ("pCfg0MainRecordOfMC".equals(key)) {
                    HzCfg0MainRecord mainRecord = hzCfg0MainService.doGetbyProjectPuid(value);
                    modelColor.setpCfg0MainRecordOfMC(mainRecord.getPuid());
                } else if ("modelShell".equals(key)) {
                    modelColor.setpModelShellOfColorfulModel(value);
                } else {
                    modelColor.getMapOfCfg0().put(key, value);
                }
            });
            modelColor.setPuid(UUID.randomUUID().toString());
            modelColor.setpColorfulMapBlock(SerializeUtil.serialize(modelColor.getMapOfCfg0()));
            return hzCfg0ModelColorService.doInsertOne(modelColor);
        } else return false;
    }

}
