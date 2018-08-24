package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.bom.HzBomDataService;
import com.connor.hozon.bom.bomSystem.service.bom.HzBomLineRecordService;
import com.connor.hozon.bom.bomSystem.service.cfg.*;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.IHzColorModelService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.cfg.*;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.*;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
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
    @Autowired
    HzColorLvl2ModelService hzColorLvl2ModelService;
    @Autowired
    HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    HzBomLineRecordDaoImpl bomLineRecordDao;

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
        Date now = new Date();
        User user = UserInfo.getUser();
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
                model1.setCreateDate(now);
                model1.setCreator(user.getUserName());
                model1.setModifyDate(now);
                model1.setModifier(user.getUserName());
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
    public String setLvl2ColorPage(@RequestParam("modelUid") String modelUid, @RequestParam("projectUid") String projectUid, Model model) {
//        List<HzBomLineRecord> lineRecords = hzBomDataService.doSelectVehicleAssembly("车身", projectUid, null);
//        List<HzBomLineRecord> lineRecords2 = hzBomDataService.doSelectVehicleAssembly("车身", projectUid, modelUid);
        List<HzColorLvl2Model> lvl2Models = hzColorLvl2ModelService.doSelectByModelUid(modelUid);
        Map<String, Object> params = new HashMap<>();
        List<HzBomLineRecord> lineRecords = new ArrayList<>();

        String puid;
        params.put("projectId", projectUid);
        for (int i = 0; i < lvl2Models.size(); i++) {
            puid = lvl2Models.get(i).getpLvlFunction();
            params.put("puid", puid);
            HzBomLineRecord lineRecord = bomLineRecordDao.findBomLine(params);
            if (null == lineRecord) {
                continue;
            } else {
                lineRecord.setColorUid(lvl2Models.get(i).getpColorUid());
                lineRecords.add(lineRecord);
            }
        }
        //这里我估计需要过滤一些紧固件胶带等东西
        List<HzCfg0ColorSet> colorList = hzCfg0ColorSetService.doGetAll();
        model.addAttribute("assembly", lineRecords);
        model.addAttribute("colorList", colorList);
        model.addAttribute("modelUid", modelUid);
        model.addAttribute("projectUid", projectUid);
        model.addAttribute("action", "./modelColor/saveColorLvl2");
        model.addAttribute("total", lineRecords.size());
        if (checkString(modelUid)) {
            return "cfg/modelColorCfg/colorLvl2";
        } else {
            model.addAttribute("msg", "操作错误，请选择一个项目进行操作!");
            return "errorWithEntity";
        }
    }

    @RequestMapping(value = "/saveColorLvl2", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveColorLvl2(@RequestBody Map<String, String> params) {
        User user = UserInfo.getUser();
        Date date = new Date();
        JSONObject result = new JSONObject();
        result.put("status", true);
        List<String> errorResult = new ArrayList<>();
        Map<String, Object> queryParam = new HashMap<>();
        Map<String, String> coach = new HashMap<>();
        if (params != null && params.size() > 0) {
            List<HzColorLvl2Model> toInsert = new ArrayList<>();
            List<HzColorLvl2Model> toUpdate = new ArrayList<>();
            HzColorLvl2Model model;
            HzColorLvl2Model modelFromDb;
            String modelUid = params.get("modelUid");
            String projectUid = params.get("projectUid");
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                Map.Entry<String, String> entry2 = null;

                if ("modelUid".equals(entry.getKey())) {
                    modelUid = entry.getValue();
                } else if ("projectUid".equals(entry.getKey())) {
                    projectUid = entry.getValue();
                } else {
                    if (entry.getKey() != null && entry.getKey().startsWith("_V_")) {
                        continue;
                    }
                    String bomlineId = entry.getValue().trim();
                    String colorUid = "";
                    if (iterator.hasNext()) {
                        entry2 = iterator.next();
                        colorUid = entry2.getValue();
                    }
                    if (!checkString(bomlineId)) {
                        continue;
                    }
                    if (!checkString(colorUid)) {
                        continue;
                    }
                    if (coach.containsKey(bomlineId)) {
                        continue;
                    }
                    queryParam.put("projectId", projectUid);
                    queryParam.put("lineID", bomlineId);
                    List<HzEPLManageRecord> itemList = hzEbomRecordDAO.findEbom(queryParam);
                    if (null == itemList || itemList.size() <= 0) {
                        errorResult.add("零件号" + bomlineId + "不存在");
                        result.put("status", false);
                        coach.put(bomlineId, bomlineId);
                        continue;
                    }
                    HzEPLManageRecord record = itemList.get(0);
                    model = new HzColorLvl2Model();
                    model.setpModelUid(modelUid);

                    model.setpLvlFunction(record.getPuid());
                    model.setpColorUid(colorUid);

                    if ((modelFromDb = hzColorLvl2ModelService.doSelectByModelAndFunctionLvl(model)) != null) {
                        model.setPuid(modelFromDb.getPuid());
                        model.setModifier(user.getUserName());
                        model.setModifyDate(date);
                        try {
                            if (hzColorLvl2ModelService.doUpdateByPrimaryKey(model) <= 0) {
                                logger.error("模型PUID为:" + model.getpModelUid() + "增加1条二级配色方案" + model.getpLvlFunction() + "失败");
                            }
                        } catch (Exception e) {
                            logger.error("模型PUID为:" + model.getpModelUid() + "增加1条二级配色方案(功能总成)" + model.getpLvlFunction() + "发生异常", e);
                        }
                    } else {
                        model.setCreateDate(date);
                        model.setCreator(user.getUserName());
                        model.setModifyDate(date);
                        model.setModifier(user.getUserName());
                        model.setPuid(UUIDHelper.generateUpperUid());
                        try {
                            if (hzColorLvl2ModelService.doInsert(model) <= 0) {
                                logger.error("模型PUID为:" + model.getpModelUid() + "增加1条二级配色方案" + model.getpLvlFunction() + "失败");
                            }
                        } catch (Exception e) {
                            logger.error("模型PUID为:" + model.getpModelUid() + "增加1条二级配色方案(功能总成)" + model.getpLvlFunction() + "发生异常", e);
                        }
                    }
                    coach.put(bomlineId, bomlineId);
                }
            }
            result.put("result", errorResult);
            return result;
        } else {
            result.put("status", false);
            result.put("result", "没有选择二级配色方案，请手动选择");
            return null;
        }
    }

    @RequestMapping("checkItemId")
    @ResponseBody
    public JSONObject checkItemId(@RequestParam String lineId, @RequestParam String projectUid) {
        JSONObject result = new JSONObject();
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("projectId", projectUid);
        queryParam.put("lineID", lineId.trim());
        List<HzEPLManageRecord> itemList = hzEbomRecordDAO.findEbom(queryParam);
        result.put("status", true);
        if (itemList == null || itemList.size() <= 0) {
            result.put("status", false);
            result.put("msg", "没有找到零件");
        } else {
            itemList.get(0).getPuid();
            //如果不设置时间不为null则会报错
            if (itemList.get(0).getpUpdateTime() == null) {
                itemList.get(0).setpUpdateTime(new Date());
            }
            if (itemList.get(0).getpCreateTime() == null) {
                itemList.get(0).setpCreateTime(new Date());
            }
            result.put("item", itemList.get(0));
        }
        return result;
    }

    @RequestMapping("deleteFromServer")
    @ResponseBody
    public JSONObject deleteFromServer(@RequestParam String modelUid, @RequestParam String puid) {
        HzColorLvl2Model model = new HzColorLvl2Model();
        JSONObject result = new JSONObject();
        model.setpLvlFunction(puid);
        model.setpModelUid(modelUid);
        HzColorLvl2Model fromDb = hzColorLvl2ModelService.doSelectByModelAndFunctionLvl(model);
        if (null == fromDb) {
            //随便删
            result.put("status", 1);
        } else {
            hzColorLvl2ModelService.doDeleteByPrimaryKey(fromDb.getPuid());
            result.put("status", 2);
        }
        return result;
    }


}
