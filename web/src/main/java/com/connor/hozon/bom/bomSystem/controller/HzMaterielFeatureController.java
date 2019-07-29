/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import cn.net.connor.hozon.services.service.depository.project.impl.*;
import com.connor.hozon.bom.bomSystem.controller.integrate.ExtraIntegrate;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzCfg0ToModelRecordDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzCfg0ModelFeatureService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OptionFamilyService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzBomAllCfgService;
import com.connor.hozon.bom.bomSystem.service.integrate.SynMaterielService;
import com.connor.hozon.bom.bomSystem.service.main.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.model.HzCfg0ModelRecordService;
import com.connor.hozon.bom.bomSystem.service.modelColor.HzCfg0ModelColorService;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
//@Controller
//@RequestMapping("/materiel")
@Deprecated
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
    HzSuperMaterielServiceImpl hzSuperMaterielServiceImpl;
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
    private HzProjectLibsServiceImpl hzProjectLibsServiceImpl;
    @Autowired
    private HzVehicleServiceImpl hzVehicleServiceImpl;
    @Autowired
    private HzPlatformServiceImpl hzPlatformServiceImpl;
    @Autowired
    private HzBrandServiceImpl hzBrandServiceImpl;

    @Autowired
    HzBomAllCfgService hzBomAllCfgService;



//    /**
//     * 根据项目的puid，获取到配置物料特性表的列设置
//     *
//     * @param projectPuid 项目puid
//     * @return 列信息
//     */
//    @RequestMapping("/loadColumnByProjectPuid")
//    @ResponseBody
//    @Deprecated
//    public Map<String, Object> getColumn(@RequestParam("projectPuid") String projectPuid) {
//        Map<String, Object> result = new HashMap<>();
//        List<String> column = new ArrayList<>();
//        List<String> _result;
//        if ((_result = hzCfg0OptionFamilyService.getColumnNew(projectPuid, "<br/>")) != null) {
//            column.addAll(_result);
//            //附加列信息
////            appendColumn(column);
//            result.put("status", true);
//            result.put("data", column);
//        } else {
//            result.put("status", false);
//        }
//        return result;
//    }
//
//
//    /**
//     * 根据项目puid，加载所有的配置物料特性数据
//     *
//     * @param projectPuid 项目puid
//     * @return
//     */
//    @RequestMapping("/loadAllByProjectPuid")
//    @ResponseBody
//    public Map<String, Object> loadAllByProjectPuid(@RequestParam String projectPuid) {
//        Map<String, Object> result = new HashMap<>();
//        Map<String, Object> data = parse2(projectPuid);
//        List<Map<String, Object>> list = new ArrayList<>();
//
//        data.forEach((key, value) -> list.add((Map<String, Object>) value));
//        result.put("result", list);
//        result.put("totalCount", list.size());
//        return result;
//    }

//    public Map<String, Object> parse2(@RequestParam String projectPuid) {
////        以后的颜色件
////        List<HzCfg0ModelColor> colorSet = hzCfg0ModelColorDao.selectAll(projectPuid);
////        Map<String, Object> stringObjectMap = hzCfg0ModelColorService.doLoadAll(projectPuid);
//        Map<String, Object> result = new HashMap<>();
//        Map<String, HzMaterielFeatureBean> model = new HashMap();
//
//        List<Map<String, Object>> data = new ArrayList<>();
//        List<String> column = hzCfg0OptionFamilyService.doGetColumnDef(projectPuid, "<br/>");
//        List<HzMaterielFeatureBean> hzMaterielFeatureBeans = hzCfg0Service.doSelectMaterielFeatureByProjectPuid(projectPuid);
//
//        HzMaterielRecord superMateriel = hzSuperMaterielServiceImpl.doSelectByProjectPuid(projectPuid);
//
//        Map<String, HzMaterielFeatureBean> sortedBean = new HashMap<>();
//
//        hzMaterielFeatureBeans.stream().filter(_b -> _b.getpCfg0ModelRecord() != null).forEach(_b -> sortedBean.put(_b.getpCfg0ModelRecord() + "=" + _b.getpCfg0FamilyDesc() + "<br/>" + _b.getpCfg0FamilyName(), _b));
//
//        if (hzMaterielFeatureBeans == null || column == null || column.size() == 0) {
//            result.put("status", false);
//            return result;
//        } else {
//            //在此修改各个模型对应的颜色或者模型数量=模型X颜色等
//            hzMaterielFeatureBeans.stream()
//                    .filter(bean -> bean.getpCfg0ModelRecord() != null)
//                    .forEach(bean -> model.put(bean.getpCfg0ModelRecord(), bean));
//
//            model.forEach((key, value) -> {
//                Map<String, Object> _result = new HashMap<>();
//                int index = 0;
//                for (int j = 0; j < column.size(); j++) {
//                    _result.put("s" + j, sortedBean.get(value.getpCfg0ModelRecord() + "=" + column.get(j)) == null ? "-" : sortedBean.get(value.getpCfg0ModelRecord() + "=" + column.get(j)).getpCfg0ObjectId());
//                    index = j;
//                }
//
//                //单独获取模型特性信息
//                HzCfg0ModelFeature hzCfg0ModelFeature = hzCfg0ModelFeatureService.doSelectByModelPuid(key);
//                if (hzCfg0ModelFeature == null) {
//                    _result.put("s" + ++index, "-");
//                    _result.put("s" + ++index, "-");
//                    _result.put("puidOfModelFeature", "");
//
//                } else {
////                    //获取中文描述
////                    _result.put("s" + ++index, hzCfg0ModelFeature.getpFeatureCnDesc());
////                    //获取单车配置码
////                    _result.put("s" + ++index, hzCfg0ModelFeature.getpFeatureSingleVehicleCode());
//                    _result.put("puidOfModelFeature", hzCfg0ModelFeature.getPuid());
//                }
//
//                //
//                _result.put("puid", value.getpCfg0ModelRecord());
//                //族
//                _result.put("cfg0MainPuid", value.getpOfCfg0MainRecord());
//                _result.put("modeBasicDetail", value.getpCfg0ModelBasicDetail());
//                //目前只有无色件
//                _result.put("modeBasicDetailDesc", value.getObjectName());
//                if (superMateriel != null) {
//                    _result.put("superMateriel", superMateriel.getpMaterielCode());
//                } else {
//                    _result.put("superMateriel", "");
//                }
//                //没加工厂字段，工厂字段归属于MBOM
//                result.put(key, _result);
//            });
//        }
//        return result;
//    }


//    public List<Map<String, Object>> parse(@RequestParam String projectPuid) {
////        以后的颜色件
////        List<HzCfg0ModelColor> colorSet = hzCfg0ModelColorDao.selectAll(projectPuid);
////        Map<String, Object> stringObjectMap = hzCfg0ModelColorService.doLoadAll(projectPuid);
//        Map<String, Object> result = new HashMap<>();
//        Map<String, HzMaterielFeatureBean> model = new HashMap();
//        List<Map<String, Object>> data = new ArrayList<>();
//        List<String> column = hzCfg0OptionFamilyService.doGetColumn(projectPuid);
//        List<HzMaterielFeatureBean> hzMaterielFeatureBeans = hzCfg0Service.doSelectMaterielFeatureByProjectPuid(projectPuid);
//        HzMaterielRecord superMateriel = hzSuperMaterielServiceImpl.doSelectByProjectPuid(projectPuid);
//
//        if (hzMaterielFeatureBeans == null || column == null || column.size() == 0) {
//            result.put("status", false);
//            data.add(result);
//            return data;
//        } else {
//            //在此修改各个模型对应的颜色或者模型数量=模型X颜色等
//            hzMaterielFeatureBeans.forEach(bean -> model.put(bean.getpCfg0ModelRecord(), bean));
//            model.forEach((key, value) -> {
//                Map<String, Object> _result = new HashMap<>();
//                for (int j = 0; j < column.size(); j++) {
//                    _result.put("s" + j, hzMaterielFeatureBeans.get(j).getpCfg0ObjectId());
//                }
//                //作为隐藏表单域，标识信息
//                //这个puid就是车型模型的puid
//                _result.put("puid", value.getpCfg0ModelRecord());
//                _result.put("cfg0MainPuid", value.getpOfCfg0MainRecord());
//                _result.put("modeBasiceDetail", value.getpCfg0ModelBasicDetail());
//                //目前只有无色件
//                _result.put("modeBasiceDetailDesc", value.getObjectName());
//                if (superMateriel != null) {
//                    _result.put("superMateriel", superMateriel.getpMaterielCode());
//                } else {
//                    _result.put("superMateriel", "");
//                }
//                //没加工厂字段，工厂字段归属于MBOM
//                data.add(_result);
//            });
//        }
//        return data;
//    }




//    @RequestMapping("/addVehicleModelPage")
//    public String addVehicleModelPage(@RequestParam String projectPuid, Model model) {
//        if (checkString(projectPuid)) {
//            HzCfg0MainRecord hzCfg0MainRecord = hzCfg0MainService.doGetbyProjectPuid(projectPuid);
//            List<HzCfg0Record> cfg0s = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, new HzFeatureQueryDto());
//            Map<String, List<HzCfg0Record>> _map = new HashMap<>();
//            cfg0s.forEach(cfg -> {
//                String id = cfg.getpCfg0FamilyDesc() + "\t" + cfg.getpCfg0FamilyName();
//                if (_map.containsKey(id)) {
//                    _map.get(id).add(cfg);
//                } else {
//                    List<HzCfg0Record> record = new ArrayList<>();
//                    HzCfg0Record empty = new HzCfg0Record();
//                    empty.setPuid("");
//                    empty.setpCfg0ObjectId("-");
//                    record.add(empty);
//                    record.add(cfg);
//                    _map.put(id, record);
//                }
//            });
//
//            model.addAttribute("cfgmain", hzCfg0MainRecord);
//            model.addAttribute("_map", _map);
//            model.addAttribute("action", "./materiel/addVehicleModel");
//            return "cfg/materielFeature/addModel";
//        } else {
//            model.addAttribute("msg", "请选择项目再操作");
//            return "errorWithEntity";
//        }
//    }

//    @RequestMapping("/initAddingPageParams")
//    public String initAddingPageParams(@RequestParam String projectPuid, Model model) {
//        if (checkString(projectPuid)) {
//            HzProjectLibs project = hzProjectLibsServiceImpl.doLoadProjectLibsById(projectPuid);
//            HzVehicleRecord vehicle = hzVehicleServiceImpl.doGetByPuid(project.getpProjectPertainToVehicle());
//            HzPlatformRecord platform = hzPlatformServiceImpl.doGetByPuid(vehicle.getpVehiclePertainToPlatform());
//            HzBrandRecord brand = hzBrandServiceImpl.doGetByPuid(platform.getpPertainToBrandPuid());
//            HzCfg0ModelDetail hzCfg0ModelDetail = new HzCfg0ModelDetail();
//            hzCfg0ModelDetail.setpModelBrand(brand.getpBrandName());
//            hzCfg0ModelDetail.setpModelPlatform(platform.getpPlatformName());
//            hzCfg0ModelDetail.setpModelVehicle(vehicle.getpVehicleName());
//            model.addAttribute("hzCfg0ModelDetail", hzCfg0ModelDetail);
//
//
//            HzCfg0MainRecord hzCfg0MainRecord = hzCfg0MainService.doGetbyProjectPuid(projectPuid);
//            List<HzCfg0Record> cfg0s = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, new HzFeatureQueryDto());
//            Map<String, List<HzCfg0Record>> _map = new HashMap<>();
//            cfg0s.forEach(cfg -> {
//                String id = cfg.getpCfg0FamilyDesc() + "\t" + cfg.getpCfg0FamilyName();
//                if (_map.containsKey(id)) {
//                    _map.get(id).add(cfg);
//                } else {
//                    List<HzCfg0Record> record = new ArrayList<>();
//                    HzCfg0Record empty = new HzCfg0Record();
//                    empty.setPuid("");
//                    empty.setpCfg0ObjectId("-");
//                    record.add(empty);
//                    record.add(cfg);
//                    _map.put(id, record);
//                }
//            });
//
//            model.addAttribute("cfgmain", hzCfg0MainRecord);
//            model.addAttribute("_map", _map);
//            model.addAttribute("action", "./materiel/addVehicleModel2");
//            return "cfg/materielFeature/addModel2";
//        } else {
//            model.addAttribute("msg", "请选择项目再操作");
//            return "errorWithEntity";
//        }
//    }


}
