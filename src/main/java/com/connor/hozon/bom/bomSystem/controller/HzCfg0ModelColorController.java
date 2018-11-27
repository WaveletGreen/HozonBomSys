/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrDetailChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorModelDao;
import com.connor.hozon.bom.bomSystem.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.option.SpecialFeatureOptions;
import com.connor.hozon.bom.bomSystem.option.SpecialSettingOptions;
import com.connor.hozon.bom.bomSystem.service.bom.HzBomDataService;
import com.connor.hozon.bom.bomSystem.service.bom.HzBomLineRecordService;
import com.connor.hozon.bom.bomSystem.service.cfg.*;
import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzColorModelService;
import com.connor.hozon.bom.bomSystem.service.color.HzCfg0ColorSetService;
import com.connor.hozon.bom.bomSystem.service.main.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.modelColor.HzCfg0ModelColorService;
import com.connor.hozon.bom.bomSystem.service.modelColor.HzColorLvl2ModelService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.cfg.cfg0.HzCfg0OptionFamily;
import sql.pojo.cfg.color.HzCfg0ColorSet;
import sql.pojo.cfg.main.HzCfg0MainRecord;
import sql.pojo.cfg.modelColor.*;
import sql.pojo.change.HzChangeOrderRecord;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.*;
import java.util.stream.Collectors;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 配色方案controller
 * 配置管理controller的所有返回消息字段key都是msg
 * 配置管理controller的所有返回成功标志字段key都是status
 * 如发现不一致需要特殊处理
 * 已完成注释
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
@Controller
@RequestMapping("/modelColor")
public class HzCfg0ModelColorController {
    /***颜色车型主数据服务，主数据除了油漆车身总成和车身颜色关联之外，不关联任务其他的特性颜色，需要从其详情service中查询*/
    @Autowired
    HzCfg0ModelColorService hzCfg0ModelColorService;
    /***特性服务层，该特性是项目级别的而不是公司级别的，因此每一个项目只要添加过特性值，都将会存在至少1条特性存在*/
    @Autowired
    HzCfg0OptionFamilyService hzCfg0OptionFamilyService;
    /***颜色库服务*/
    @Autowired
    HzCfg0ColorSetService hzCfg0ColorSetService;
    /***主配置*/
    @Autowired
    HzCfg0MainService hzCfg0MainService;
    /***颜色车型详情服务层，精确到每一个配色方案的所有特性下的颜色*/
    @Autowired
    IHzColorModelService hzColorModelService;
    /***二级配色方案服务层，不再使用二级配色方案*/
    @Autowired
    HzColorLvl2ModelService hzColorLvl2ModelService;
    /***EBOM服务*/
    @Autowired
    HzEbomRecordDAO hzEbomRecordDAO;
    /***EBOM服务，上面那个太啰嗦了*/
    @Autowired
    HzBomLineRecordDaoImpl bomLineRecordDao;
    @Autowired
    HzChangeOrderDAO hzChangeOrderDAO;
    @Autowired
    HzCfg0ModelColorDao hzCfg0ModelColorDao;
    @Autowired
    HzCmcrChangeDao hzCmcrChangeDao;
    @Autowired
    HzCmcrDetailChangeDao hzCmcrDetailChangeDao;
    @Autowired
    HzColorModelDao hzColorModelDao;
    /*** 日志*/
    private final static Logger logger = LoggerFactory.getLogger(HzCfg0ModelColorController.class);

    /**
     * 获取配色方案页面表格所有数据，配色方案数据量少，不采用分页更加简单
     *
     * @param projectPuid 项目id
     * @return
     */
    @RequestMapping(value = "/loadAll", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadAll(@RequestParam String projectPuid) {
        return hzCfg0ModelColorService.doLoadAll2(projectPuid);
    }

    /**
     * 跳转到配色方案添加页面
     *
     * @param projectPuid 项目id
     * @param model
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage(@RequestParam String projectPuid, Model model) {
        List<HzCfg0OptionFamily> _columnList = hzCfg0OptionFamilyService.selectForColorBluePrint(projectPuid, 1);//getFamilies(projectPuid, 0, 1);
        List<HzCfg0OptionFamily> columnList = new ArrayList<>();
        /**过滤油漆车身总成*/
        columnList.addAll(_columnList.stream().filter(c -> c != null).filter(c -> false == SpecialFeatureOptions.YQCSCODE.getDesc().equals(c.getpOptionfamilyName()))
                .collect(Collectors.toList()));
        List<HzCfg0ColorSet> colorList = hzCfg0ColorSetService.doGetAll();//颜色库所有数据
        List<HzCfg0ColorSet> _colorList = new ArrayList<>(colorList);//将colorList复制到_colorList
        HzCfg0ModelColor mc = new HzCfg0ModelColor();
        HzCfg0MainRecord mainRecord = hzCfg0MainService.doGetbyProjectPuid(projectPuid);
        mc.setpCfg0MainRecordOfMC(mainRecord.getPuid());
        //配色方案中已有数据
        List<HzCfg0ModelColor> colorList2 = hzCfg0ModelColorService.doLoadModelColorByMainId(mc);

        Iterator<HzCfg0ColorSet> iterator = _colorList.iterator();
        while (iterator.hasNext()) {
            HzCfg0ColorSet hmc = iterator.next();
            for (int i = 0; i < colorList2.size(); i++) {
                if (colorList2.get(i).getpModelShellOfColorfulModel().equals(hmc.getpColorCode())) {
                    iterator.remove();//去除（过滤）配色库已有数据
                    break;
                }
            }
        }

        System.out.println("colorList.size===" + colorList.size());//8
        System.out.println("colorList2.size===" + colorList2.size());//7
        //添加一个无色
        HzCfg0ColorSet set = new HzCfg0ColorSet();
        set.setpColorName("-");
        set.setpColorCode("-");
        set.setpColorComment("-");
        set.setpColorOfSet("-");
        set.setPuid("-");
        colorList.add(0, set);
        _colorList.add(0, set);
        model.addAttribute("colorList", colorList);
        model.addAttribute("colorList2", _colorList);
        model.addAttribute("columnList", columnList);
        model.addAttribute("pCfg0MainRecordOfMC", projectPuid);

        return "cfg/modelColorCfg/addModelColorCfg";
    }

    /**
     * 跳转到配色方案修改页面
     *
     * @param puid  配色方案id
     * @param model
     * @return
     */
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
        List<HzCfg0OptionFamily> _columnList = hzCfg0OptionFamilyService.selectForColorBluePrint(main.getpCfg0OfWhichProjectPuid(), 1);//getFamilies(main.getpCfg0OfWhichProjectPuid(), 0, 1);
        List<HzCfg0OptionFamily> columnList = new ArrayList<>();
        /**过滤油漆车身总成*/
        columnList.addAll(_columnList.stream().filter(c -> c != null).filter(c -> false == SpecialFeatureOptions.YQCSCODE.getDesc().equals(c.getpOptionfamilyName()))
                .collect(Collectors.toList()));
        List<HzCfg0ColorSet> colorList = hzCfg0ColorSetService.doGetAll();
        ArrayList<String> orgValue = new ArrayList<>();
        List<HzCfg0ModelColorDetail> cm = hzColorModelService.doSelectByModelUidWithColor(puid);

        Map<String, HzCfg0OptionFamily> columnPuid = new LinkedHashMap<>();
        Map<String, HzCfg0ModelColorDetail> cmx = new LinkedHashMap<>();
        List<HzCfg0ModelColorDetail> toInsert = new ArrayList<>();

        for (HzCfg0OptionFamily family : columnList) {
            columnPuid.put(family.getPuid(), family);
        }
        for (HzCfg0ModelColorDetail hcm : cm) {
            cmx.put(hcm.getCfgUid(), hcm);
        }
        //筛选出不足项，将不足项进行插入，但不会筛选出多余项,如果不按顺序进行排序会怎么样？
        for (Map.Entry<String, HzCfg0OptionFamily> entry : columnPuid.entrySet()) {
            if (cmx.containsKey(entry.getKey())) {
                orgValue.add(cmx.get(entry.getKey()).getpColorCode());
            } else {
                //添加进结果集中
                orgValue.add("-");
                HzCfg0ModelColorDetail model1 = new HzCfg0ModelColorDetail();
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

    /**
     * 修改配色方案，由于配色方案的title是动态的，不适合用bean来接收，因此入参也是不确定的，只能用Map进行数据接收
     *
     * @param form
     * @return
     */
    @RequestMapping(value = "/updateColorModel", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateColorModel(@RequestBody LinkedHashMap<String, String> form) {
        User user = UserInfo.getUser();
        Date date = new Date();
        List<HzCfg0ModelColorDetail> colorModels = new ArrayList<>();
        /*修改之后需要进入流程的*/
        List<HzCfg0ModelColorDetail> toProcess = new ArrayList<>();
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
            List<HzCfg0ModelColorDetail> history = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
            Map<String, String> mHistory = new HashMap<>();
            history.forEach(h -> mHistory.put(h.getCfgUid(), h.getColorUid()));
            //没有更新过的值不需要进行更新
            for (Map.Entry<String, String> entry : color.getMapOfCfg0().entrySet()) {
                if (mHistory.containsKey(entry.getKey())) {
                    if (mHistory.get(entry.getKey()).equals(entry.getValue())) {
                        continue;
                    }
                }
                HzCfg0ModelColorDetail model = new HzCfg0ModelColorDetail();
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
            color.setCmcrStatus("0");
            return hzCfg0ModelColorService.doUpdateOne(color);
        } else return false;
    }

    /**
     * 获取配色方案所有表头
     *
     * @param projectPuid 项目id
     * @return
     */
    @RequestMapping(value = "/getColumn", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getColumn(@RequestParam String projectPuid) {
        return hzCfg0ModelColorService.getColumnOnlyColor(projectPuid);
    }

    /**
     * 删除配色方案
     *
     * @param colors 需删除的配色方案模型
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteByBatch(@RequestBody List<HzCfg0ModelColor> colors) {
        JSONObject result = new JSONObject();
        for (HzCfg0ModelColor hzCfg0ModelColor : colors) {
            if ("10".equals(hzCfg0ModelColor.getCmcrStatus())) {
                result.put("status", false);
                result.put("msg", hzCfg0ModelColor.getpCodeOfColorfulModel() + "已在VWO流程中，不能删除");
            }
        }
        if (result.get("status") == null) {
            result.put("status", hzCfg0ModelColorService.doDelete(colors) > 0 ? true : false);
        }
        return result;
    }

    /**
     * 伪删除配色方案，将其状态改为删除状态
     * @param colors
     * @return
     */
    @RequestMapping("/deleteFake")
    @ResponseBody
    public JSONObject deleteFake(@RequestBody List<HzCfg0ModelColor> colors){
        JSONObject result = new JSONObject();
        for (HzCfg0ModelColor hzCfg0ModelColor : colors) {
            if ("10".equals(hzCfg0ModelColor.getCmcrStatus())) {
                result.put("status", false);
                result.put("msg", hzCfg0ModelColor.getpCodeOfColorfulModel() + "已在VWO流程中，不能删除");
                return result;
            }
        }
        List<HzCmcrChange> hzCmcrChanges = hzCmcrChangeDao.doQueryCmcrChangeByModelColorId(colors);
        if(hzCmcrChanges!=null&&hzCmcrChanges.size()>0){
            List<HzCfg0ModelColor> hzCfg0ModelColorsUpdate = new ArrayList<>();
            List<HzCfg0ModelColor> hzCfg0ModelColorsDelete = new ArrayList<>();

            for(HzCfg0ModelColor hzCfg0ModelColor : colors){
                boolean flag = false;
                for(HzCmcrChange hzCmcrChange : hzCmcrChanges){
                    if(hzCfg0ModelColor.getPuid().equals(hzCmcrChange.getCmcrSrcPuid())){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    hzCfg0ModelColorsUpdate.add(hzCfg0ModelColor);
                }else {
                    hzCfg0ModelColorsDelete.add(hzCfg0ModelColor);
                }
            }

            if(hzCfg0ModelColorsUpdate!=null&&hzCfg0ModelColorsUpdate.size()>0){
                try {
                    for(HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColorsUpdate){
                        hzCfg0ModelColor.setCmcrStatus("2");
                    }
                    hzCfg0ModelColorService.doUpdateStatus(hzCfg0ModelColorsUpdate);
                }catch (Exception e){
                    result.put("status", false);
                    result.put("msg","删除失败");
                    return result;
                }
            }
            if(hzCfg0ModelColorsDelete!=null&&hzCfg0ModelColorsDelete.size()>0){
                try {
                    hzCfg0ModelColorService.doDelete(hzCfg0ModelColorsDelete);
                }catch (Exception e){
                    result.put("status", false);
                    result.put("msg","删除失败");
                    return result;
                }
            }
        }else {
            hzCfg0ModelColorService.doDelete(colors);
        }
        return result;
    }

    /**
     * 添加新增的配色方案
     *
     * @param form
     * @return
     */
    @RequestMapping(value = "/saveColorModel", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveColorModel(@RequestBody LinkedHashMap<String, String> form) {
        JSONObject result = new JSONObject();
        result.put("status", false);
        result.put("msg", "请填写表单");
        User user = UserInfo.getUser();
        HzCfg0MainRecord mainRecord = null;
        Date date = new Date();
        List<HzCfg0OptionFamily> families;
        Map<String, HzCfg0OptionFamily> mapOfFamilies = new HashMap<>();
        if (form != null) {
            HzCfg0ModelColor modelColor = new HzCfg0ModelColor();
            for (String key :
                    form.keySet()) {
                String value = form.get(key);
                if ("pCodeOfColorfulModel".equals(key)) {
                    modelColor.setpCodeOfColorfulModel(value);
                } else if ("pDescOfColorfulModel".equals(key)) {
                    modelColor.setpDescOfColorfulModel(value);
                } else if ("pCfg0MainRecordOfMC".equals(key)) {
                    mainRecord = hzCfg0MainService.doGetbyProjectPuid(value);
                    modelColor.setpCfg0MainRecordOfMC(mainRecord.getPuid());
                    families = hzCfg0OptionFamilyService.doSelectByDesc(mainRecord.getPuid(), "车身颜色");
                    for (HzCfg0OptionFamily family : families) {
                        mapOfFamilies.put(family.getPuid(), family);
                    }
                } else if ("modelShell".equals(key)) {
                    HzCfg0ColorSet set = new HzCfg0ColorSet();
                    set.setPuid(value);
                    set = hzCfg0ColorSetService.getById(set);
                    modelColor.setpModelShellOfColorfulModel(set.getpColorCode());
                    modelColor.setpColorUid(set.getPuid());
                } else {
                    modelColor.getMapOfCfg0().put(key, value);
                }
            }
            /**
             * 追加一个油漆车身总成特性值
             */
            if (mainRecord != null) {
                HzCfg0OptionFamily family = new HzCfg0OptionFamily();
                family.setpOfCfg0Main(mainRecord.getPuid());
                family.setpOptionfamilyName(SpecialFeatureOptions.YQCSCODE.getDesc());
                family.setpOptionfamilyDesc(SpecialFeatureOptions.YQCSNAME.getDesc());
                HzCfg0OptionFamily fromDb = hzCfg0OptionFamilyService.doGetByCodeAndDescWithMain(family);
                if (fromDb == null) {
                    result.put("status", false);
                    result.put("msg", "项目中特性表中没有找到特性为" + SpecialFeatureOptions.YQCSCODE.getDesc() + "、特性描述为" + SpecialFeatureOptions.YQCSNAME.getDesc() + "的特性");
                    return result;
                } else {
                    modelColor.getMapOfCfg0().put(fromDb.getPuid(), modelColor.getpColorUid());
                }
            }
            /**
             * 追加无色
             */
            if (SpecialSettingOptions.COLOR_MODEL_APPEND_COLORLESS) {
                List<HzCfg0OptionFamily> withoutColor = hzCfg0OptionFamilyService.selectForColorBluePrint(mainRecord.getpCfg0OfWhichProjectPuid(), 0);
                for (int i = 0; i < withoutColor.size(); i++) {
                    if (null != withoutColor.get(i))
                        modelColor.getMapOfCfg0().put(withoutColor.get(i).getPuid(), "-");
                }
            }
            modelColor.setPuid(UUIDHelper.generateUpperUid());
            List<HzCfg0ModelColorDetail> colorList = new ArrayList<>();
            for (Map.Entry<String, String> entry : modelColor.getMapOfCfg0().entrySet()) {

                HzCfg0ModelColorDetail hzCfg0ModelColorDetail = new HzCfg0ModelColorDetail();
                hzCfg0ModelColorDetail.setPuid(UUIDHelper.generateUpperUid());
                hzCfg0ModelColorDetail.setModelUid(modelColor.getPuid());
                hzCfg0ModelColorDetail.setColorUid(entry.getValue());
                hzCfg0ModelColorDetail.setCfgUid(entry.getKey());
                hzCfg0ModelColorDetail.setCfgMainUid(modelColor.getpCfg0MainRecordOfMC());
                hzCfg0ModelColorDetail.setCreateDate(date);
                hzCfg0ModelColorDetail.setModifyDate(date);
                hzCfg0ModelColorDetail.setCreator(user.getUserName());
                hzCfg0ModelColorDetail.setModifier(user.getUserName());

                if (mapOfFamilies.containsKey(entry.getKey())) {
                    hzCfg0ModelColorDetail.setColorUid(modelColor.getpColorUid());
                }

                colorList.add(hzCfg0ModelColorDetail);
            }
            modelColor.setCmcrStatus("0");
            hzCfg0ModelColorService.doInsert(modelColor);
            hzColorModelService.doInsertByBatch(colorList);
            result.put("status", true);
            result.put("msg", "新增配色方案成功");
        }
        return result;
    }

    /**
     * 校验二级配色方案关联的零件
     * 通过校验，只需要输入零件号，校验成功则会返回零件的完整信息，理论上采用Map<String,Object>效果会好一点，因为JSONObject不能存储null
     * 倘若通过零件号找到多个对象，只取首位对象的数据传到前端，并由前端自动匹配到相应的输入框中
     *
     * @param lineId     零件号
     * @param projectUid 项目UID
     * @return 找到的零件号完整信息
     */
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

    /**
     * 对配色方案发起流程，发起流程时，同时需要传入当前的配色方案的title给后台做变更前后的title对比，不能简单的就对某个配色方案直接
     * 发起VWO变更，变更的复杂性源自于全配置BOM一级清单的不确定性
     * <p>
     * 所需的入参
     * <p>
     * {@code key=rows} 的数据存放在Map中，该数据Map的所有key都需要与{@link HzCfg0ModelColor}的字段至少对应一个，特别时puid必须存在
     * <p>
     * {@code key=titles}的数据即为变更前后的表头做差异性对比的
     *
     * @param params 配色方案数据
     * @return 成功与否标志和消息
     */
    @RequestMapping("/getVWO")
    @ResponseBody
    public JSONObject getVWO(@RequestBody Map<String, Object> params) {
        List<HzCfg0ModelColor> rows = new ArrayList<>();
        List<HashMap<String, String>> x = (List<HashMap<String, String>>) params.get("rows");
        for (int i = 0; i < x.size(); i++) {
            rows.add((HzCfg0ModelColor) JSONObject.toBean(JSONObject.fromObject(x.get(i)), HzCfg0ModelColor.class));
        }
        ArrayList<String> dynamicTitle = (ArrayList<String>) params.get("titles");
        String projectPuid = (String) params.get("projectPuid");
        Long changeFromId = Long.valueOf((String)params.get("changeFromId"));
        return hzCfg0ModelColorService.getVWO(rows, projectPuid, dynamicTitle, changeFromId);
    }

    /**
     * 跳转到变更表单页面
     * @param projectUid
     * @param puids
     * @param titles
     * @param model
     * @return
     */
    @RequestMapping("/setChangeFromPage")
    public String setChangeFromPage(String projectUid, String puids, String titles, Model model){
        List<HzCfg0ModelColor> puidList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        String[] puidArr = puids.split(",");
        String[] titleArr = titles.split(",");
        for(String puid : puidArr){
            HzCfg0ModelColor hzCfg0ModelColor = new HzCfg0ModelColor();
            hzCfg0ModelColor.setPuid(puid);
            puidList.add(hzCfg0ModelColor);
        }
        for(String title : titleArr){
            titleList.add(title);
        }
        List<HzCfg0ModelColor> rows = hzCfg0ModelColorDao.selectByPuids(puidList);
        List<HzChangeOrderRecord> hzChangeOrderRecordList = hzChangeOrderDAO.findHzChangeOrderRecordByProjectId(projectUid);
//        model.addAttribute("beans",beans);
        model.addAttribute("changeFroms",hzChangeOrderRecordList);
        model.addAttribute("titles",titleList);
        model.addAttribute("rows",rows);
        return "cfg/modelColorCfg/modelColorSetChangeFrom";
    }

    @RequestMapping("/goBackData")
    @ResponseBody
    public JSONObject goBackData(@RequestBody List<HzCfg0ModelColor> hzCfg0ModelColors){
        JSONObject result = new JSONObject();
        result.put("status",true);
        result.put("msg","撤销成功");
        Iterator<HzCfg0ModelColor> hzCfg0ModelColorIterator = hzCfg0ModelColors.iterator();

        /********找出所有删除状态的配色方案并改为草稿状态***********/
        List<HzCfg0ModelColor> hzCfg0ModelColorsDelete = new ArrayList<>();
        while (hzCfg0ModelColorIterator.hasNext()){
            HzCfg0ModelColor hzCfg0ModelColor = hzCfg0ModelColorIterator.next();
            if(Integer.valueOf(hzCfg0ModelColor.getCmcrStatus())==2){
                hzCfg0ModelColor.setCmcrStatus("1");
                hzCfg0ModelColorsDelete.add(hzCfg0ModelColor);
                hzCfg0ModelColorIterator.remove();
            }
        }
        int deleteNum = hzCfg0ModelColorDao.updateStatus(hzCfg0ModelColorsDelete);
        /*********查找出所有修改状态的配色方案并将其回退到修改前的数据*******************/
        if(hzCfg0ModelColors==null||hzCfg0ModelColors.size()==0){
            return result;
        }
        //修改主数据的结果集
        List<HzCfg0ModelColor> hzCfg0ModelColorsUpdate = new ArrayList<>();
        //修改从数据的结果集
        List<HzCfg0ModelColorDetail> hzCfg0ModelColorDetailsUpdate = new ArrayList<>();
        //查询变更表查出最近一次的生效数据
        //主数据
        List<HzCmcrChange> hzCmcrChange = hzCmcrChangeDao.doQueryCmcrChangeByModelColorId(hzCfg0ModelColors);
        List<HzCmcrDetailChange> hzCmcrDetailChanges = new ArrayList<>();
        if(hzCmcrChange!=null&&hzCmcrChange.size()!=0){
            //从数据
            hzCmcrDetailChanges = hzCmcrDetailChangeDao.doQueryCmcrDetailByMainChange(hzCmcrChange);
        }
        //为主数据的结果集加值
        for(HzCmcrChange hzCmcrChange1 : hzCmcrChange){
            HzCfg0ModelColor hzCfg0ModelColor = new HzCfg0ModelColor();
            hzCfg0ModelColor.setPuid(hzCmcrChange1.getCmcrSrcPuid());
            hzCfg0ModelColor.setpDescOfColorfulModel(hzCmcrChange1.getCmcrSrcDescOfColorMod());
            hzCfg0ModelColorsUpdate.add(hzCfg0ModelColor);
        }
        for(HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChanges){
            HzCfg0ModelColorDetail hzCfg0ModelColorDetail = new HzCfg0ModelColorDetail();
            hzCfg0ModelColorDetail.setPuid(hzCmcrDetailChange.getCmcrDetailSrcPuid());
            hzCfg0ModelColorDetail.setModelUid(hzCmcrDetailChange.getCmcrDetailSrcModelPuid());
            hzCfg0ModelColorDetail.setCfgUid(hzCmcrDetailChange.getCmcrDetailSrcCfgUid());
            hzCfg0ModelColorDetail.setColorUid(hzCmcrDetailChange.getCmcrDetailSrcColorUid());
            hzCfg0ModelColorDetailsUpdate.add(hzCfg0ModelColorDetail);
        }

        if(hzCfg0ModelColorsUpdate!=null&&hzCfg0ModelColorsUpdate.size()!=0){
            int updateMainNum = hzCfg0ModelColorDao.updateListAll(hzCfg0ModelColorsUpdate);
            if(updateMainNum<=0){
                result.put("status",false);
                result.put("msg","撤销修改主数据失败");
                return result;
            }
        }
        if(hzCfg0ModelColorDetailsUpdate!=null&&hzCfg0ModelColorDetailsUpdate.size()!=0){
            int updateDetailNum = hzColorModelDao.updateListAll(hzCfg0ModelColorDetailsUpdate);
            if(updateDetailNum<=0){
                result.put("status",false);
                result.put("msg","撤销修改从数据失败");
                return result;
            }
        }
        //从配色方案数据集中删除修改的数据，只留下新增的数据
        while (hzCfg0ModelColorIterator.hasNext()) {
            HzCfg0ModelColor hzCfg0ModelColor = hzCfg0ModelColorIterator.next();
            for(HzCmcrChange hzCmcrChange1 : hzCmcrChange){
                if(hzCfg0ModelColor.getPuid().equals(hzCmcrChange1.getCmcrSrcPuid())){
                    hzCfg0ModelColorIterator.remove();
                    break;
                }
            }
        }
        /**********查找出所有新增的数据，并将其删除*********************/
        if(hzCfg0ModelColors==null||hzCfg0ModelColors.size()==0){
            return result;
        }
        int addNum = hzCfg0ModelColorDao.deleteByBatch(hzCfg0ModelColors);
        if(addNum<=0){
            result.put("status",false);
            result.put("msg","撤销新增数据失败");
            return result;
        }
        return result;
    }
    /**********************************************废除方法****************************************/

    /**
     * 删除单挑二级配色方案，
     * 作为二级配色方案前端服务，二级配色方案没有从配色方案中进行关联，虽然没有才用二级配色方案，功能还需要保留
     *
     * @param modelUid 颜色车型UID
     * @param puid     二级配色方案主键
     * @return
     */
    @RequestMapping("deleteFromServer")
    @ResponseBody
    @Deprecated
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

    /**
     * 获取二级配色方案页面，这个页面是用alert进行模式框弹出而不是dialog
     *
     * @param modelUid   配色模型UID
     * @param projectUid 项目UID
     * @return 二级配色方案页面
     */
    @RequestMapping(value = "/setLvl2ColorPage", method = RequestMethod.GET)
    @Deprecated
    public String setLvl2ColorPage(@RequestParam("modelUid") String modelUid, @RequestParam("projectUid") String projectUid, Model model) {
//        List<HzBomLineRecordDO> lineRecords = hzBomDataService.doSelectVehicleAssembly("车身", projectUid, null);
//        List<HzBomLineRecordDO> lineRecords2 = hzBomDataService.doSelectVehicleAssembly("车身", projectUid, modelUid);
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

    /**
     * 保存二级配色方案，二级配色方案已废除，二级配色方案的操作作为参考操作内容存在而非当作正式业务存在，即所有的二级配色方案相关方法都不应当发布
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveColorLvl2", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
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

    /**********************************************废除方法****************************************/

}
