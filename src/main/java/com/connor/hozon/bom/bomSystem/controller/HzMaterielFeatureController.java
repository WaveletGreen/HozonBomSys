/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import com.connor.hozon.bom.bomSystem.controller.integrate.ExtraIntegrate;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzCfg0ToModelRecordDao;
import com.connor.hozon.bom.bomSystem.dto.HzFeatureQueryDTO;
import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.*;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzBomAllCfgService;
import com.connor.hozon.bom.bomSystem.service.integrate.SynMaterielService;
import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzCfg0ModelFeatureService;
import com.connor.hozon.bom.bomSystem.service.main.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.model.HzCfg0ModelRecordService;
import com.connor.hozon.bom.bomSystem.service.modelColor.HzCfg0ModelColorService;
import com.connor.hozon.bom.bomSystem.service.project.*;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.sys.entity.User;
import integration.option.ActionFlagOption;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.derivative.HzCfg0ToModelRecord;
import sql.pojo.cfg.main.HzCfg0MainRecord;
import sql.pojo.cfg.model.HzCfg0ModelDetail;
import sql.pojo.cfg.derivative.HzCfg0ModelFeature;
import sql.pojo.cfg.model.HzCfg0ModelRecord;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.*;

import java.util.*;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/8 17:03
 */
@Controller
@RequestMapping("/materiel")
public class HzMaterielFeatureController extends ExtraIntegrate {
    /**
     * 族层服务
     */
    @Autowired
    HzCfg0OptionFamilyService hzCfg0OptionFamilyService;
    /**
     * 车身颜色服务
     */
    @Autowired
    HzCfg0ModelColorService hzCfg0ModelColorService;
    /**
     * 颜色车型dao层
     */
    @Autowired
    HzCfg0ModelColorDao hzCfg0ModelColorDao;
    /**
     * 配置值服务层
     */
    @Autowired
    HzCfg0Service hzCfg0Service;
    /**
     * 超级物料服务层
     */
    @Autowired
    HzSuperMaterielService hzSuperMaterielService;
    /**
     * 车型模型服务层
     */
    @Autowired
    HzCfg0ModelRecordService hzCfg0ModelRecordService;
    /**
     * 配置主模型服务层
     */
    @Autowired
    HzCfg0MainService hzCfg0MainService;
    /**
     * 模型特性数据服务
     */
    @Autowired
    IHzCfg0ModelFeatureService hzCfg0ModelFeatureService;
    /**
     * 工厂
     */
    @Autowired
    HzFactoryDAO hzFactoryDAO;
    /**
     * 配置-模型关系
     */
    @Autowired
    HzCfg0ToModelRecordDao hzCfg0ToModelRecordDao;
    /**
     * 同步物料接口服务
     */
    @Autowired
    private SynMaterielService synMaterielService;

    //车型基本数据
    @Autowired
    private HzProjectLibsService hzProjectLibsService;
    @Autowired
    private HzVehicleService hzVehicleService;
    @Autowired
    private HzPlatformService hzPlatformService;
    @Autowired
    private HzBrandService hzBrandService;

    @Autowired
    HzBomAllCfgService hzBomAllCfgService;

    private static Logger logger = LoggerFactory.getLogger(HzMaterielFeatureController.class);

    /**
     * 根据项目的puid，获取到配置物料特性表的列设置
     *
     * @param projectPuid 项目puid
     * @return 列信息
     */
    @RequestMapping("/loadColumnByProjectPuid")
    @ResponseBody
    public Map<String, Object> getColumn(@RequestParam("projectPuid") String projectPuid) {
        Map<String, Object> result = new HashMap<>();
        List<String> column = new ArrayList<>();
        List<String> _result;
        if ((_result = hzCfg0OptionFamilyService.getColumnNew(projectPuid, "<br/>")) != null) {
            column.addAll(_result);
            //附加列信息
//            appendColumn(column);
            result.put("status", true);
            result.put("data", column);
        } else {
            result.put("status", false);
        }
        return result;
    }

    /**
     * 根据项目的puid，获取到配置物料特性表的列设置
     *
     * @param projectPuid 项目puid
     * @return 列信息
     */
    @RequestMapping("/loadColumnByProjectPuid2")
    @ResponseBody
    public Map<String, Object> getColumn2(@RequestParam("projectPuid") String projectPuid) {
        Map<String, Object> result = new HashMap<>();
        List<String> column = new ArrayList<>();
        List<String> _result;
        if ((_result = hzCfg0OptionFamilyService.getColumnNew2(projectPuid, "<br/>")) != null) {
            column.addAll(_result);
            //附加列信息
//            appendColumn(column);
            result.put("status", true);
            result.put("data", column);
        } else {
            result.put("status", false);
        }
        return result;
    }

    /**
     * 单独添加列信息
     *
     * @param column
     */
    private void appendColumn(List<String> column) {
        //添加中文描述
        column.add("中文描述");
        //添加单车配置吗
        column.add("单车配置码");
    }

    /**
     * 根据项目puid，加载所有的配置物料特性数据
     *
     * @param projectPuid 项目puid
     * @return
     */
    @RequestMapping("/loadAllByProjectPuid")
    @ResponseBody
    public Map<String, Object> loadAllByProjectPuid(@RequestParam String projectPuid) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = parse2(projectPuid);
        List<Map<String, Object>> list = new ArrayList<>();

        data.forEach((key, value) -> list.add((Map<String, Object>) value));
        result.put("result", list);
        result.put("totalCount", list.size());
        return result;
    }

    public Map<String, Object> parse2(@RequestParam String projectPuid) {
//        以后的颜色件
//        List<HzCfg0ModelColor> colorSet = hzCfg0ModelColorDao.selectAll(projectPuid);
//        Map<String, Object> stringObjectMap = hzCfg0ModelColorService.doLoadAll(projectPuid);
        Map<String, Object> result = new HashMap<>();
        Map<String, HzMaterielFeatureBean> model = new HashMap();

        List<Map<String, Object>> data = new ArrayList<>();
        List<String> column = hzCfg0OptionFamilyService.doGetColumnDef(projectPuid, "<br/>");
        List<HzMaterielFeatureBean> hzMaterielFeatureBeans = hzCfg0Service.doSelectMaterielFeatureByProjectPuid(projectPuid);

        HzMaterielRecord superMateriel = hzSuperMaterielService.doSelectByProjectPuid(projectPuid);

        Map<String, HzMaterielFeatureBean> sortedBean = new HashMap<>();

        hzMaterielFeatureBeans.stream().filter(_b -> _b.getpCfg0ModelRecord() != null).forEach(_b -> sortedBean.put(_b.getpCfg0ModelRecord() + "=" + _b.getpCfg0FamilyDesc() + "<br/>" + _b.getpCfg0FamilyName(), _b));

        if (hzMaterielFeatureBeans == null || column == null || column.size() == 0) {
            result.put("status", false);
            return result;
        } else {
            //在此修改各个模型对应的颜色或者模型数量=模型X颜色等
            hzMaterielFeatureBeans.stream()
                    .filter(bean -> bean.getpCfg0ModelRecord() != null)
                    .forEach(bean -> model.put(bean.getpCfg0ModelRecord(), bean));

            model.forEach((key, value) -> {
                Map<String, Object> _result = new HashMap<>();
                int index = 0;
                for (int j = 0; j < column.size(); j++) {
                    _result.put("s" + j, sortedBean.get(value.getpCfg0ModelRecord() + "=" + column.get(j)) == null ? "-" : sortedBean.get(value.getpCfg0ModelRecord() + "=" + column.get(j)).getpCfg0ObjectId());
                    index = j;
                }

                //单独获取模型特性信息
                HzCfg0ModelFeature hzCfg0ModelFeature = hzCfg0ModelFeatureService.doSelectByModelPuid(key);
                if (hzCfg0ModelFeature == null) {
                    _result.put("s" + ++index, "-");
                    _result.put("s" + ++index, "-");
                    _result.put("puidOfModelFeature", "");

                } else {
//                    //获取中文描述
//                    _result.put("s" + ++index, hzCfg0ModelFeature.getpFeatureCnDesc());
//                    //获取单车配置码
//                    _result.put("s" + ++index, hzCfg0ModelFeature.getpFeatureSingleVehicleCode());
                    _result.put("puidOfModelFeature", hzCfg0ModelFeature.getPuid());
                }

                //
                _result.put("puid", value.getpCfg0ModelRecord());
                //族
                _result.put("cfg0MainPuid", value.getpOfCfg0MainRecord());
                _result.put("modeBasicDetail", value.getpCfg0ModelBasicDetail());
                //目前只有无色件
                _result.put("modeBasicDetailDesc", value.getObjectName());
                if (superMateriel != null) {
                    _result.put("superMateriel", superMateriel.getpMaterielCode());
                } else {
                    _result.put("superMateriel", "");
                }
                //没加工厂字段，工厂字段归属于MBOM
                result.put(key, _result);
            });
        }
        return result;
    }


    public List<Map<String, Object>> parse(@RequestParam String projectPuid) {
//        以后的颜色件
//        List<HzCfg0ModelColor> colorSet = hzCfg0ModelColorDao.selectAll(projectPuid);
//        Map<String, Object> stringObjectMap = hzCfg0ModelColorService.doLoadAll(projectPuid);
        Map<String, Object> result = new HashMap<>();
        Map<String, HzMaterielFeatureBean> model = new HashMap();
        List<Map<String, Object>> data = new ArrayList<>();
        List<String> column = hzCfg0OptionFamilyService.doGetColumn(projectPuid);
        List<HzMaterielFeatureBean> hzMaterielFeatureBeans = hzCfg0Service.doSelectMaterielFeatureByProjectPuid(projectPuid);
        HzMaterielRecord superMateriel = hzSuperMaterielService.doSelectByProjectPuid(projectPuid);

        if (hzMaterielFeatureBeans == null || column == null || column.size() == 0) {
            result.put("status", false);
            data.add(result);
            return data;
        } else {
            //在此修改各个模型对应的颜色或者模型数量=模型X颜色等
            hzMaterielFeatureBeans.forEach(bean -> model.put(bean.getpCfg0ModelRecord(), bean));
            model.forEach((key, value) -> {
                Map<String, Object> _result = new HashMap<>();
                for (int j = 0; j < column.size(); j++) {
                    _result.put("s" + j, hzMaterielFeatureBeans.get(j).getpCfg0ObjectId());
                }
                //作为隐藏表单域，标识信息
                //这个puid就是车型模型的puid
                _result.put("puid", value.getpCfg0ModelRecord());
                _result.put("cfg0MainPuid", value.getpOfCfg0MainRecord());
                _result.put("modeBasiceDetail", value.getpCfg0ModelBasicDetail());
                //目前只有无色件
                _result.put("modeBasiceDetailDesc", value.getObjectName());
                if (superMateriel != null) {
                    _result.put("superMateriel", superMateriel.getpMaterielCode());
                } else {
                    _result.put("superMateriel", "");
                }
                //没加工厂字段，工厂字段归属于MBOM
                data.add(_result);
            });
        }
        return data;
    }

    /**
     * 根据车型模型进行更新数据
     *
     * @param puid               车型模型puid
     * @param puidOfModelFeature 车型特性的puid
     * @param page               申请页面
     * @param model
     * @return
     */
    @RequestMapping("/modifyPage")
    public String modPage(@RequestParam String projectUid, @RequestParam String puid, @RequestParam String puidOfModelFeature, @RequestParam String page, Model model) {
        //啥也不做
        if (page == null || puid == null)
            ;
            //修改基本信息
        else if ("model".equals(page)) {
            HzCfg0ModelRecord modelRecord = hzCfg0ModelRecordService.doGetById(puid);
            HzCfg0ModelFeature hzCfg0ModelFeature;
            //判断是否为空
            if (puidOfModelFeature == null || "".equals(puidOfModelFeature))
                hzCfg0ModelFeature = new HzCfg0ModelFeature();
            else
                hzCfg0ModelFeature = hzCfg0ModelFeatureService.doSelectByPrimaryKeyWithFactoryCode(puidOfModelFeature);
            //没有找到
            if (hzCfg0ModelFeature == null)
                hzCfg0ModelFeature = new HzCfg0ModelFeature();

            HzMaterielRecord sm = hzSuperMaterielService.doSelectByProjectPuid(projectUid);


            model.addAttribute("entity", modelRecord);
            model.addAttribute("modelFeature", hzCfg0ModelFeature);
            model.addAttribute("projectUid", projectUid);
            model.addAttribute("superMateriel", sm == null ? "" : sm.getpMaterielCode());

            model.addAttribute("action", "./materiel/updateModelBasic");
            return "cfg/materielFeature/updateModelBasic";
        }
        //修改超级物料
        else if ("superMateriel".equals(page)) {
            HzCfg0MainRecord mainRecord = hzCfg0MainService.doGetByPrimaryKey(puid);
            if (mainRecord != null) {
                HzMaterielRecord superMateriel = hzSuperMaterielService.doSelectByProjectPuid(mainRecord.getpCfg0OfWhichProjectPuid());
                if (superMateriel == null) {
                    superMateriel = new HzMaterielRecord();
                }
                //设置项目puid
                if (superMateriel.getpPertainToProjectPuid() == null || "".equals(superMateriel.getpPertainToProjectPuid())) {
                    superMateriel.setpPertainToProjectPuid(mainRecord.getpCfg0OfWhichProjectPuid());
                }
                model.addAttribute("entity", superMateriel);
                model.addAttribute("action", "./materiel/updateSuperMateriel");
                return "cfg/materielFeature/updateSuperMateriel";
            }
        }
        model.addAttribute("msg", "找不到选中行的详细数据，请联系管理员");
        return "errorWithEntity";
    }

    @RequestMapping("/updateModelBasic")
    @ResponseBody
    public JSONObject updateModelBasic(
            @RequestParam String pCfg0ModelBasicDetail,
            @RequestParam String puid,
            @RequestParam String projectUid,
            @RequestParam String superMateriel,
            @RequestBody HzCfg0ModelFeature modelFeature
    ) {
        JSONObject _result = new JSONObject();
        HzCfg0ModelRecord modelRecord = new HzCfg0ModelRecord();
        modelRecord.setPuid(puid);
        modelRecord.setpCfg0ModelBasicDetail(pCfg0ModelBasicDetail);
        Date now = new Date();
        User user = UserInfo.getUser();
        boolean result = hzCfg0ModelRecordService.doUpdateBasic(modelRecord);
        if (result == false) {
            _result.put("status", false);
            _result.put("msg", "更新模型信息失败");
            return _result;
        }

        if (modelFeature.getPuid() == null || "".equals(modelFeature.getPuid())) {
            modelFeature.setPuid(UUID.randomUUID().toString());
            if (hzCfg0ModelFeatureService.doInsert(modelFeature)) {
                _result.put("status", true);
                _result.put("msg", "新增衍生物料基本数据成功");
            } else {
                _result.put("status", false);
                _result.put("msg", "新增衍生物料基本数据失败");
            }
        } else if (null != (hzCfg0ModelFeatureService.doSelectByPrimaryKey(modelFeature.getModelFeaturePuid()))) {
            modelFeature.setPuid(modelFeature.getModelFeaturePuid());
            modelFeature.setMaterielType("A001");
            HzFactory factory = hzFactoryDAO.findFactory("", checkString(modelFeature.getFactoryCode())?modelFeature.getFactoryCode():"1001");
            HzFactory factory1001 = hzFactoryDAO.findFactory(null, "1001");
            if (factory == null) {
                logger.warn("自动创建工厂" + modelFeature.getFactoryCode());
                factory = new HzFactory();
                factory.setpFactoryCode(modelFeature.getFactoryCode());
                factory.setPuid(UUIDHelper.generateUpperUid());
                factory.setpCreateTime(now);
                factory.setpCreateName(user.getUsername());
                factory.setpUpdateTime(now);
                factory.setpUpdateName(user.getUsername());
                factory.setpFactoryDesc("由系统进行自动创建");
                if (hzFactoryDAO.insert(factory) < 0) {
                    logger.error("自动创建工厂" + modelFeature.getFactoryCode() + "失败");
                    _result.put("status", false);
                    _result.put("msg", "没有找到工厂" + modelFeature.getFactoryCode());
                    return _result;
                }
            }
            modelFeature.setFactoryCode(factory == null ? factory1001.getPuid() : factory.getPuid());
            if (hzCfg0ModelFeatureService.doUpdateByPrimaryKey(modelFeature)) {
                _result.put("status", true);
                _result.put("msg", "更新衍生物料基本数据成功");
            } else {
                _result.put("status", false);
                _result.put("msg", "更新衍生物料基本数据失败");
            }

            /**
             * 更新超级物料
             */
            HzMaterielRecord sm = hzSuperMaterielService.doSelectByProjectPuid(projectUid);
            if (checkString(superMateriel)) {
                if (null == sm) {
                    logger.warn("没有超级物料号，进行申请");
                    sm = new HzMaterielRecord();
                    sm.setpMaterielCode(superMateriel);
                    sm.setPuid(UUIDHelper.generateUpperUid());
                    sm.setpFactoryPuid(factory == null ? factory1001.getPuid() : factory.getPuid());
                    sm.setpPertainToProjectPuid(projectUid);
                    if (!hzSuperMaterielService.doInsertOne(sm)) {
                        logger.error("存储超级物料号失败");
                    }
                }
                if (!superMateriel.equals(sm.getpMaterielCode())) {
                    logger.warn("已有超级物料号，进行更新超级物料号");
                    sm.setpMaterielCode(superMateriel);
                    if (!hzSuperMaterielService.doUpdateByPrimaryKey(sm)) {
                        logger.error("更新超级物料号失败");
                    }
                }
            }
            return _result;
        } else {
            _result.put("status", false);
            _result.put("msg", "没有找到衍生物料" + modelFeature.getMaterialCode());

        }
        return _result;
    }

    @RequestMapping("/updateSuperMateriel")
    @ResponseBody
    public boolean updateSuperMateriel(@RequestBody HzMaterielRecord superMateriel) {
        if (superMateriel == null)
            return false;
        if (superMateriel.getpPertainToProjectPuid() == null || "".equals(superMateriel.getpPertainToProjectPuid()))
            return false;
        HzMaterielRecord sm = hzSuperMaterielService.doSelectByProjectPuid(superMateriel.getpPertainToProjectPuid());
        if (sm == null && (superMateriel.getPuid() == null || "".equals(superMateriel.getPuid()))) {
            superMateriel.setPuid(UUID.randomUUID().toString());
            return hzSuperMaterielService.doInsertOne(superMateriel);
        } else if (sm != null) {
            superMateriel.setPuid(sm.getPuid());
            return hzSuperMaterielService.doUpdateByPrimaryKey(superMateriel);
        }
        return false;
    }

    @RequestMapping("/addVehicleModelPage")
    public String addVehicleModelPage(@RequestParam String projectPuid, Model model) {
        if (checkString(projectPuid)) {
            HzCfg0MainRecord hzCfg0MainRecord = hzCfg0MainService.doGetbyProjectPuid(projectPuid);
            List<HzCfg0Record> cfg0s = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, new HzFeatureQueryDTO());
            Map<String, List<HzCfg0Record>> _map = new HashMap<>();
            cfg0s.forEach(cfg -> {
                String id = cfg.getpCfg0FamilyDesc() + "\t" + cfg.getpCfg0FamilyName();
                if (_map.containsKey(id)) {
                    _map.get(id).add(cfg);
                } else {
                    List<HzCfg0Record> record = new ArrayList<>();
                    HzCfg0Record empty = new HzCfg0Record();
                    empty.setPuid("");
                    empty.setpCfg0ObjectId("-");
                    record.add(empty);
                    record.add(cfg);
                    _map.put(id, record);
                }
            });

            model.addAttribute("cfgmain", hzCfg0MainRecord);
            model.addAttribute("_map", _map);
            model.addAttribute("action", "./materiel/addVehicleModel");
            return "cfg/materielFeature/addModel";
        } else {
            model.addAttribute("msg", "请选择项目再操作");
            return "errorWithEntity";
        }
    }

    @RequestMapping("/addVehicleModelPage2")
    public String addVehicleModelPage2(@RequestParam String projectPuid, Model model) {
        if (checkString(projectPuid)) {
            HzProjectLibs project = hzProjectLibsService.doLoadProjectLibsById(projectPuid);
            HzVehicleRecord vehicle = hzVehicleService.doGetByPuid(project.getpProjectPertainToVehicle());
            HzPlatformRecord platform = hzPlatformService.doGetByPuid(vehicle.getpVehiclePertainToPlatform());
            HzBrandRecord brand = hzBrandService.doGetByPuid(platform.getpPertainToBrandPuid());
            HzCfg0ModelDetail hzCfg0ModelDetail = new HzCfg0ModelDetail();
            hzCfg0ModelDetail.setpModelBrand(brand.getpBrandName());
            hzCfg0ModelDetail.setpModelPlatform(platform.getpPlatformName());
            hzCfg0ModelDetail.setpModelVehicle(vehicle.getpVehicleName());
            model.addAttribute("hzCfg0ModelDetail", hzCfg0ModelDetail);


            HzCfg0MainRecord hzCfg0MainRecord = hzCfg0MainService.doGetbyProjectPuid(projectPuid);
            List<HzCfg0Record> cfg0s = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, new HzFeatureQueryDTO());
            Map<String, List<HzCfg0Record>> _map = new HashMap<>();
            cfg0s.forEach(cfg -> {
                String id = cfg.getpCfg0FamilyDesc() + "\t" + cfg.getpCfg0FamilyName();
                if (_map.containsKey(id)) {
                    _map.get(id).add(cfg);
                } else {
                    List<HzCfg0Record> record = new ArrayList<>();
                    HzCfg0Record empty = new HzCfg0Record();
                    empty.setPuid("");
                    empty.setpCfg0ObjectId("-");
                    record.add(empty);
                    record.add(cfg);
                    _map.put(id, record);
                }
            });

            model.addAttribute("cfgmain", hzCfg0MainRecord);
            model.addAttribute("_map", _map);
            model.addAttribute("action", "./materiel/addVehicleModel2");
            return "cfg/materielFeature/addModel2";
        } else {
            model.addAttribute("msg", "请选择项目再操作");
            return "errorWithEntity";
        }
    }

    @RequestMapping("/addVehicleModel")
    @ResponseBody
    public boolean addVehicleModel(@RequestBody Map<String, String> params) {
        if (params != null) {
            if (!params.containsKey("pCfg0ModelOfMainRecord")) {
                return false;
            } else {
                HzCfg0ModelRecord modelRecord = new HzCfg0ModelRecord();
                HzCfg0ModelFeature modelDetail = new HzCfg0ModelFeature();
                List<HzCfg0ToModelRecord> toInsert = new ArrayList<>();
                //生成UID
                modelRecord.setPuid(UUIDHelper.generateUpperUid());
                //生成UID
                modelDetail.setPuid(UUIDHelper.generateUpperUid());
                //设置归属车型
                modelDetail.setpPertainToModel(modelRecord.getPuid());
                for (String key : params.keySet()) {
                    String value = params.get(key);
                    if ("pCfg0ModelOfMainRecord".equals(key)) {
                        //设置归属主配置
                        modelRecord.setpCfg0ModelOfMainRecord(params.get("pCfg0ModelOfMainRecord"));
                    } else if ("objectName".equals(key)) {
                        //设置车型名
                        modelRecord.setObjectName(params.get("objectName"));
                    } else if ("objectDesc".equals(key)) {
                        //设置车型描述
                        modelRecord.setObjectDesc(params.get("objectDesc"));
                    } else if ("pCfg0ModelBasicDetail".equals(key)) {
                        //设置基本信息代码
                        modelRecord.setpCfg0ModelBasicDetail(params.get("pCfg0ModelBasicDetail"));
                    } else if ("pFeatureCnDesc".equals(key)) {
                        //中文描述
                        modelDetail.setpFeatureCnDesc(params.get("pFeatureCnDesc"));
                    } else if ("pFeatureSingleVehicleCode".equals(key)) {
                        //设置单车配置码
                        modelDetail.setpFeatureSingleVehicleCode(params.get("pFeatureSingleVehicleCode"));
                    } else {
                        HzCfg0Record addedRecord = hzCfg0Service.doSelectOneByPuid(value);
                        HzCfg0ToModelRecord hzCfg0ToModelRecord = new HzCfg0ToModelRecord();
                        if (addedRecord == null) {
//                            try {
//                                throw new Exception("无法找到特性值，请检查数据");
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                return false;
//                            }
                            continue;
                        } else {
                            hzCfg0ToModelRecord.setpCfg0IdRecord(addedRecord.getPuid());
                            hzCfg0ToModelRecord.setpCfg0ModelRecord(modelRecord.getPuid());
                            hzCfg0ToModelRecord.setpCfg0OptionValue(addedRecord.getpCfg0ObjectId());
                            hzCfg0ToModelRecord.setpOfCfg0MainRecord(modelRecord.getpCfg0ModelOfMainRecord());
                            hzCfg0ToModelRecord.setPuid(UUIDHelper.generateUpperUid());
                            hzCfg0ToModelRecord.setpParseLogicValue(1);
                            //插入数据
                            toInsert.add(hzCfg0ToModelRecord);
//                            hzCfg0ToModelRecordDao.insert(hzCfg0ToModelRecord);
                        }
                    }
                }

                //没有设置归属的颜色车型
                hzCfg0ModelRecordService.doInsert(Collections.singletonList(modelRecord));
                hzCfg0ModelFeatureService.doInsert(modelDetail);
                for (int i = 0; i < toInsert.size(); i++) {
                    hzCfg0ToModelRecordDao.insert(toInsert.get(i));
                }
                //发送到SAP?
            }
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping("/addVehicleModel2")
    @ResponseBody
    public JSONObject addVehicleModel2(@RequestBody Map<String, String> params) {
        return hzBomAllCfgService.addVehicleModel(params);
    }


    @RequestMapping("/addToSAP")
    public String addToSAP(String[] puidOfModelFeatures, Model model) {
        List<HzCfg0ModelFeature> features = new ArrayList<HzCfg0ModelFeature>();
        for (String puidOfModelFeature : puidOfModelFeatures) {
            HzCfg0ModelFeature feature = hzCfg0ModelFeatureService.doSelectByPrimaryKey(puidOfModelFeature);
            features.add(feature);
        }
        JSONObject object = synMaterielService.tranMateriel2(features, ActionFlagOption.ADD, "HZ_CFG0_MODEL_FEATURE", "MATERIAL_CODE");
        addToModel(object, model);
        return "stage/templateOfIntegrate";
    }

    @RequestMapping("/deleteToSAP")
    public String deleteToSAP(String[] puidOfModelFeatures, Model model) {
        List<HzCfg0ModelFeature> HCMfeatures = new ArrayList<HzCfg0ModelFeature>();
        for (String puidOfModelFeature : puidOfModelFeatures) {
            HzCfg0ModelFeature feature = hzCfg0ModelFeatureService.doSelectByPrimaryKey(puidOfModelFeature);
            HCMfeatures.add(feature);
        }
        JSONObject object = synMaterielService.tranMateriel2(HCMfeatures, ActionFlagOption.DELETE, "HZ_CFG0_MODEL_FEATURE", "MATERIAL_CODE");
        addToModel(object, model);
        return "stage/templateOfIntegrate";
    }
}