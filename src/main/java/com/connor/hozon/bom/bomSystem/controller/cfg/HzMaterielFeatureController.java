package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.*;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.IHzCfg0ModelFeatureService;
import com.connor.hozon.bom.bomSystem.service.project.HzSuperMaterielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzCfg0MainRecord;
import sql.pojo.cfg.HzCfg0ModelDetail;
import sql.pojo.cfg.HzCfg0ModelFeature;
import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.project.HzMaterielRecord;

import java.util.*;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/8 17:03
 */
@Controller
@RequestMapping("/materiel")
public class HzMaterielFeatureController {
    /**
     * 族层服务
     */
    private final HzCfg0OptionFamilyService hzCfg0OptionFamilyService;
    /**
     * 车身颜色服务
     */
    private final HzCfg0ModelColorService hzCfg0ModelColorService;
    /**
     * 颜色车型dao层
     */
    private final HzCfg0ModelColorDao hzCfg0ModelColorDao;
    /**
     * 配置值服务层
     */
    private final HzCfg0Service hzCfg0Service;
    /**
     * 超级物料服务层
     */
    private final HzSuperMaterielService hzSuperMaterielService;
    /**
     * 车型模型服务层
     */
    private final HzCfg0ModelRecordService hzCfg0ModelRecordService;
    /**
     * 配置主模型服务层
     */
    private final HzCfg0MainService hzCfg0MainService;
    /**
     * 模型特性数据服务
     */
    private final IHzCfg0ModelFeatureService hzCfg0ModelFeatureService;

    @Autowired
    public HzMaterielFeatureController(HzCfg0OptionFamilyService hzCfg0OptionFamilyService,
                                       HzCfg0ModelColorService hzCfg0ModelColorService,
                                       HzCfg0ModelColorDao hzCfg0ModelColorDao,
                                       HzCfg0Service hzCfg0Service,
                                       HzSuperMaterielService hzSuperMaterielService,
                                       HzCfg0ModelRecordService hzCfg0ModelRecordService,
                                       HzCfg0MainService hzCfg0MainService,
                                       IHzCfg0ModelFeatureService hzCfg0ModelFeatureService) {
        this.hzCfg0OptionFamilyService = hzCfg0OptionFamilyService;
        this.hzCfg0ModelColorService = hzCfg0ModelColorService;
        this.hzCfg0ModelColorDao = hzCfg0ModelColorDao;
        this.hzCfg0Service = hzCfg0Service;
        this.hzSuperMaterielService = hzSuperMaterielService;
        this.hzCfg0ModelRecordService = hzCfg0ModelRecordService;
        this.hzCfg0MainService = hzCfg0MainService;
        this.hzCfg0ModelFeatureService = hzCfg0ModelFeatureService;
    }

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
        if ((_result = hzCfg0OptionFamilyService.doGetColumnDef(projectPuid, "<br/>")) != null) {
            column.addAll(_result);
            //附加列信息
            appendColumn(column);
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
        List<String> column = hzCfg0OptionFamilyService.doGetColumn(projectPuid);
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
                    //获取中文描述
                    _result.put("s" + ++index, hzCfg0ModelFeature.getpFeatureCnDesc());
                    //获取单车配置码
                    _result.put("s" + ++index, hzCfg0ModelFeature.getpFeatureSingleVehicleCode());
                    _result.put("puidOfModelFeature", hzCfg0ModelFeature.getPuid());
                }

                //
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
    public String modPage(@RequestParam String puid, @RequestParam String puidOfModelFeature, @RequestParam String page, Model model) {
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
                hzCfg0ModelFeature = hzCfg0ModelFeatureService.doSelectByPrimaryKey(puidOfModelFeature);
            //没有找到
            if (hzCfg0ModelFeature == null)
                hzCfg0ModelFeature = new HzCfg0ModelFeature();
            model.addAttribute("entity", modelRecord);
            model.addAttribute("modelFeature", hzCfg0ModelFeature);

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
    public boolean updateModelBasic(@RequestBody HzCfg0ModelRecord modelRecord,
                                    @RequestParam String pFeatureCnDesc,
                                    @RequestParam String pFeatureSingleVehicleCode,
                                    @RequestParam String modelFeaturePuid) {
        boolean result = hzCfg0ModelRecordService.doUpdateBasic(modelRecord);
        if (result == false)
            return false;
        HzCfg0ModelFeature hzCfg0ModelFeature = new HzCfg0ModelFeature();
        if (modelFeaturePuid == null || "".equals(modelFeaturePuid)) {
            hzCfg0ModelFeature.setPuid(UUID.randomUUID().toString());
            hzCfg0ModelFeature.setpFeatureCnDesc(pFeatureCnDesc);
            hzCfg0ModelFeature.setpFeatureSingleVehicleCode(pFeatureSingleVehicleCode);
            hzCfg0ModelFeature.setpPertainToModel(modelRecord.getPuid());
            result = hzCfg0ModelFeatureService.doInsert(hzCfg0ModelFeature);
        } else if (null != (hzCfg0ModelFeature = hzCfg0ModelFeatureService.doSelectByPrimaryKey(modelFeaturePuid))) {
            hzCfg0ModelFeature.setpFeatureCnDesc(pFeatureCnDesc);
            hzCfg0ModelFeature.setpFeatureSingleVehicleCode(pFeatureSingleVehicleCode);
            hzCfg0ModelFeature.setpPertainToModel(modelRecord.getPuid());
            result = hzCfg0ModelFeatureService.doUpdateByPrimaryKey(hzCfg0ModelFeature);
        } else return false;
        return result;
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
            model.addAttribute("cfgmain", hzCfg0MainRecord);
            model.addAttribute("action", "./materiel/addVehicleModel");
            return "cfg/materielFeature/addModel";
        } else {
            model.addAttribute("msg", "请选择项目再操作");
            return "errorWithEntity";
        }
    }

    @RequestMapping("/addVehicleModel")
    @ResponseBody
    public boolean addVehicleModel(@RequestBody  Map<String, String> params) {
        if (params != null) {
            if (!params.containsKey("pCfg0ModelOfMainRecord")) {
                return false;
            } else {
                HzCfg0ModelRecord modelRecord = new HzCfg0ModelRecord();
                //设置归属主配置
                modelRecord.setpCfg0ModelOfMainRecord(params.get(""));
                //设置车型名
                modelRecord.setObjectName(params.get("objectName"));
                //设置车型描述
                modelRecord.setObjectDesc(params.get("objectDesc"));
                //生成UID
                modelRecord.setPuid(UUIDHelper.generateUpperUid());
                //设置基本信息代码
                modelRecord.setpCfg0ModelBasicDetail(params.get("pCfg0ModelBasicDetail"));

                HzCfg0ModelFeature modelDetail = new HzCfg0ModelFeature();
                //设置归属车型
                modelDetail.setpPertainToModel(modelRecord.getPuid());
                //生成UID
                modelDetail.setPuid(UUIDHelper.generateUpperUid());
                //中文描述
                modelDetail.setpFeatureCnDesc(params.get("pFeatureCnDesc"));
                //设置单车配置码
                modelDetail.setpFeatureSingleVehicleCode(params.get("pFeatureSingleVehicleCode"));
                //没有设置归属的颜色车型
                hzCfg0ModelRecordService.doInsert(Collections.singletonList(modelRecord));
                hzCfg0ModelFeatureService.doInsert(modelDetail);
            }
            return true;
        } else {
            return false;
        }
    }

}
