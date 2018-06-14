package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.bean.HzMaterielFeatureBean;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.service.cfg.*;
import com.connor.hozon.bom.bomSystem.service.project.HzSuperMaterielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzCfg0MainRecord;
import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.project.HzSuperMateriel;

import java.util.*;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/8 17:03
 */
@Controller
@RequestMapping("/materiel")
public class HzMaterielFeatureController {
    private final HzCfg0OptionFamilyService hzCfg0OptionFamilyService;
    private final HzCfg0ModelColorService hzCfg0ModelColorService;
    private final HzCfg0ModelColorDao hzCfg0ModelColorDao;
    private final HzCfg0Service hzCfg0Service;
    private final HzSuperMaterielService hzSuperMaterielService;
    private final HzCfg0ModelRecordService hzCfg0ModelRecordService;
    private final HzCfg0MainService hzCfg0MainService;

    @Autowired
    public HzMaterielFeatureController(HzCfg0OptionFamilyService hzCfg0OptionFamilyService,
                                       HzCfg0ModelColorService hzCfg0ModelColorService,
                                       HzCfg0ModelColorDao hzCfg0ModelColorDao,
                                       HzCfg0Service hzCfg0Service,
                                       HzSuperMaterielService hzSuperMaterielService,
                                       HzCfg0ModelRecordService hzCfg0ModelRecordService,
                                       HzCfg0MainService hzCfg0MainService) {
        this.hzCfg0OptionFamilyService = hzCfg0OptionFamilyService;
        this.hzCfg0ModelColorService = hzCfg0ModelColorService;
        this.hzCfg0ModelColorDao = hzCfg0ModelColorDao;
        this.hzCfg0Service = hzCfg0Service;
        this.hzSuperMaterielService = hzSuperMaterielService;
        this.hzCfg0ModelRecordService = hzCfg0ModelRecordService;
        this.hzCfg0MainService = hzCfg0MainService;
    }

    @RequestMapping("/loadColumnByProjectPuid")
    @ResponseBody
    public Map<String, Object> getColumn(@RequestParam("projectPuid") String projectPuid) {
        Map<String, Object> result = new HashMap<>();
        List<String> column = new ArrayList<>();
        List<String> _result;
//        column.add("基本信息");
//        column.add("工厂");
//        column.add("基本信息");
//        column.add("超级物料");
        if ((_result = hzCfg0OptionFamilyService.doGetColumn(projectPuid)) != null) {
            column.addAll(_result);
            result.put("status", true);
            result.put("data", column);
        } else {
            result.put("status", false);
        }
        return result;
    }

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
        List<String> column = hzCfg0OptionFamilyService.doGetColumn(projectPuid);
        List<HzMaterielFeatureBean> hzMaterielFeatureBeans = hzCfg0Service.doSelectMaterielFeatureByProjectPuid(projectPuid);
        HzSuperMateriel superMateriel = hzSuperMaterielService.doSelectByProjectPuid(projectPuid);

        if (hzMaterielFeatureBeans == null || column == null || column.size() == 0) {
            result.put("status", false);
            return result;
        } else {
            //在此修改各个模型对应的颜色或者模型数量=模型X颜色等
            hzMaterielFeatureBeans.forEach(bean -> model.put(bean.getpCfg0ModelRecord(), bean));
            model.forEach((key, value) -> {
                Map<String, Object> _result = new HashMap<>();
                for (int j = 0; j < column.size(); j++) {
                    _result.put("s" + j, hzMaterielFeatureBeans.get(j).getpCfg0ObjectId());
                }
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
        HzSuperMateriel superMateriel = hzSuperMaterielService.doSelectByProjectPuid(projectPuid);

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

    @RequestMapping("/modifyPage")
    public String modPage(@RequestParam String puid, @RequestParam String page, Model model) {
        //啥也不做
        if (page == null || puid == null)
            ;
            //修改基本信息
        else if ("model".equals(page)) {
            HzCfg0ModelRecord modelRecord = hzCfg0ModelRecordService.doGetById(puid);
            model.addAttribute("entity", modelRecord);
            model.addAttribute("action", "./materiel/updateModelBasic");
            return "cfg/materielFeature/updateModelBasic";
        }
        //修改超级物料
        else if ("superMateriel".equals(page)) {
            HzCfg0MainRecord mainRecord = hzCfg0MainService.doGetByPrimaryKey(puid);
            if (mainRecord != null) {
                HzSuperMateriel superMateriel = hzSuperMaterielService.doSelectByProjectPuid(mainRecord.getpCfg0OfWhichProjectPuid());
                if (superMateriel == null) {
                    superMateriel = new HzSuperMateriel();
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
    public boolean updateModelBasic(@RequestBody HzCfg0ModelRecord modelRecord) {
        return hzCfg0ModelRecordService.doUpdateBasic(modelRecord);
    }

    @RequestMapping("/updateSuperMateriel")
    @ResponseBody
    public boolean updateSuperMateriel(@RequestBody HzSuperMateriel superMateriel) {
        if (superMateriel == null)
            return false;
        if (superMateriel.getpPertainToProjectPuid() == null || "".equals(superMateriel.getpPertainToProjectPuid()))
            return false;
        HzSuperMateriel sm = hzSuperMaterielService.doSelectByProjectPuid(superMateriel.getpPertainToProjectPuid());
        if (sm == null && (superMateriel.getPuid() == null || "".equals(superMateriel.getPuid()))) {
            superMateriel.setPuid(UUID.randomUUID().toString());
            return hzSuperMaterielService.doInsertOne(superMateriel);
        } else if (sm != null) {
            superMateriel.setPuid(sm.getPuid());
            return hzSuperMaterielService.doUpdateByPrimaryKey(superMateriel);
        }
        return false;
    }
}
