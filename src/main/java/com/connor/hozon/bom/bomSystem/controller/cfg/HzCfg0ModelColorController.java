package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.bom.HzBomDataService;
import com.connor.hozon.bom.bomSystem.service.bom.HzBomLineRecordService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0ColorSetService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0ModelColorService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OptionFamilyService;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.IHzColorModelService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.*;

import java.util.*;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

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
    private final HzCfg0OptionFamilyService hzCfg0OptionFamilyService;
    private final HzCfg0ColorSetService hzCfg0ColorSetService;
    private final HzCfg0MainService hzCfg0MainService;
    @Autowired
    IHzColorModelService hzColorModelService;
    @Autowired
    HzBomLineRecordService hzBomLineRecordService;
    @Autowired
    HzBomDataService hzBomDataService;
    private Logger logger;

    @Autowired
    public HzCfg0ModelColorController(HzCfg0ModelColorService hzCfg0ModelColorService, HzCfg0OptionFamilyService hzCfg0OptionFamilyService, HzCfg0ColorSetService hzCfg0ColorSetService, HzCfg0MainService hzCfg0MainService) {
        this.hzCfg0MainService = hzCfg0MainService;
        logger = LoggerFactory.getLogger(this.getClass());
        this.hzCfg0ModelColorService = hzCfg0ModelColorService;
        this.hzCfg0OptionFamilyService = hzCfg0OptionFamilyService;
        this.hzCfg0ColorSetService = hzCfg0ColorSetService;
    }

    @RequestMapping(value = "/loadAll", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadAll(@RequestParam String projectPuid) {
        return hzCfg0ModelColorService.doLoadAll(projectPuid);
    }

    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage(@RequestParam String projectPuid, Model model) {
        List<HzCfg0OptionFamily> columnList = hzCfg0OptionFamilyService.doGetCfg0OptionFamilyListByProjectPuid(projectPuid);
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
        model.addAttribute("pCfg0MainRecordOfMC", projectPuid);
        return "cfg/modelColorCfg/addModelColorCfg";
    }

    @RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
    public String modifyPage(String puid, Model model) {
        HzCfg0ModelColor currentModel = new HzCfg0ModelColor();
        currentModel.setPuid(puid);
        currentModel = hzCfg0ModelColorService.doGetById(currentModel);
        if (currentModel == null) {
            return "cfg/modelColorCfg/addModelColorCfg";
        }
        HzCfg0MainRecord main = hzCfg0MainService.doGetByPrimaryKey(currentModel.getpCfg0MainRecordOfMC());
//        List<String> columnList = hzCfg0OptionFamilyService.doGetColumnDef(main.getpCfg0OfWhichProjectPuid(), "\t");
        List<HzCfg0OptionFamily> columnList = hzCfg0OptionFamilyService.doGetCfg0OptionFamilyListByProjectPuid(main.getpCfg0OfWhichProjectPuid());
        List<HzCfg0ColorSet> colorList = hzCfg0ColorSetService.doGetAll();
        ArrayList<String> orgValue = new ArrayList<>();
        List<HzColorModel> cm = hzColorModelService.doSelectByModelUidWithColor(puid);

        Map<String, HzCfg0OptionFamily> columnPuid = new LinkedHashMap<>();
        Map<String, HzColorModel> cmx = new LinkedHashMap<>();
        List<HzColorModel> toInsert = new ArrayList<>();

        for (HzCfg0OptionFamily family : columnList) {
            columnPuid.put(family.getPuid(), family);
        }
        for (HzColorModel hcm : cm) {
            cmx.put(hcm.getCfgUid(), hcm);
        }
        //筛选出不足项，将不足项进行插入，但不会筛选出多余项,如果不按顺序进行排序会怎么样？
        for (Map.Entry<String, HzCfg0OptionFamily> entry : columnPuid.entrySet()) {
            if (cmx.containsKey(entry.getKey())) {
                orgValue.add(cmx.get(entry.getKey()).getpColorCode());
            } else {
                //添加进结果集中
                orgValue.add("-");
                HzColorModel model1 = new HzColorModel();
                model1.setCfgUid(entry.getKey());
                model1.setCfgMainUid(main.getPuid());
                model1.setColorUid("-");
                model1.setPuid(UUIDHelper.generateUpperUid());
                model1.setModelUid(currentModel.getPuid());
                //插入多余项
                toInsert.add(model1);
            }
        }

        if (toInsert.size() > 0)
            hzColorModelService.doInsertByBatch(toInsert);

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
        model.addAttribute("entity", currentModel);
        model.addAttribute("orgValue", orgValue);
        return "cfg/modelColorCfg/updateModelColorCfg";
    }

    @RequestMapping(value = "/updateColorModel", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateColorModel(@RequestBody LinkedHashMap<String, String> form) {
        User user = UserInfo.getUser();
        Date date = new Date();
        List<HzColorModel> colorModels = new ArrayList<>();
        /*修改之后需要进入流程的*/
        List<HzColorModel> toProcess = new ArrayList<>();
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
            List<HzColorModel> history = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
            Map<String, String> mHistory = new HashMap<>();
            history.forEach(h -> mHistory.put(h.getCfgUid(), h.getColorUid()));
            //没有更新过的值不需要进行更新
            for (Map.Entry<String, String> entry : color.getMapOfCfg0().entrySet()) {
                if (mHistory.containsKey(entry.getKey())) {
                    if (mHistory.get(entry.getKey()).equals(entry.getValue())) {
                        continue;
                    }
                }
                HzColorModel model = new HzColorModel();
                model.setModelUid(color.getPuid());
                model.setColorUid(entry.getValue());
                model.setCfgUid(entry.getKey());
                model.setModifier(user.getUserName());
                model.setModifyDate(date);
                if (!hzColorModelService.doUpdateColorModelWithCfg(model)) {
                    logger.error("更新" + color.getpCodeOfColorfulModel() + "配置项的颜色值" + model.getColorUid() + "失败");
                } else {
                    logger.info("更新" + color.getpCodeOfColorfulModel() + "配置项的颜色值" + model.getColorUid() + "成功");
                    toProcess.add(model);
                }
            }
            return hzCfg0ModelColorService.doUpdateOne(color);
        } else return false;
    }

    @RequestMapping(value = "/getColumn", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getColumn(@RequestParam String projectPuid) {
        JSONObject object = new JSONObject();
        List<String> column;
        if (projectPuid == null || "".equals(projectPuid)) {
            object.put("status", false);
        } else {
            column = hzCfg0OptionFamilyService.doGetColumnDef2(projectPuid, "<br/>");
            if (column == null || column.size() <= 0) {
                object.put("status", false);
            } else {
                object.put("status", true);
                object.put("data", column);
            }
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
        User user = UserInfo.getUser();
        Date date = new Date();
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
            modelColor.setPuid(UUIDHelper.generateUpperUid());
            List<HzColorModel> colorList = new ArrayList<>();
            for (Map.Entry<String, String> entry : modelColor.getMapOfCfg0().entrySet()) {
                HzColorModel hzColorModel = new HzColorModel();
                hzColorModel.setPuid(UUIDHelper.generateUpperUid());
                hzColorModel.setModelUid(modelColor.getPuid());
                hzColorModel.setColorUid(entry.getValue());
                hzColorModel.setCfgUid(entry.getKey());
                hzColorModel.setCfgMainUid(modelColor.getpCfg0MainRecordOfMC());
                hzColorModel.setCreateDate(date);
                hzColorModel.setModifyDate(date);
                hzColorModel.setCreator(user.getUserName());
                hzColorModel.setModifier(user.getUserName());
                colorList.add(hzColorModel);
            }
            hzCfg0ModelColorService.doInsertOne(modelColor);
            hzColorModelService.doInsertByBatch(colorList);
            return true;
        } else return false;
    }

    @RequestMapping(value = "/setLvl2ColorPage", method = RequestMethod.GET)
    @ResponseBody
    public String setLvl2ColorPage(@RequestParam("modelUid") String modelPuid, @RequestParam("projectPuid") String projectPuid) {
        //List<HzBomLineRecord> lineRecords = hzBomDataService.doSelectVehicleAssembly("车身", projectPuid);
        if (checkString(modelPuid)) {
            return "二级配色方案";
        } else {
            return "发生错误，请选择一个项目和颜色车型进行操作";
        }
    }

}
