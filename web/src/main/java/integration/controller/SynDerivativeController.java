/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.controller;

import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzCfg0ModelFeature;
import com.connor.hozon.bom.service.configuration.derivative.HzConfigModelFeatureService;
import integration.option.ActionFlagOption;
import integration.service.integrate.impl.SynMaterielServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 10:59
 * @Modified By:
 */
@Controller
@RequestMapping("synDerivative")
public class SynDerivativeController extends ExtraIntegrateController {
    /*** 同步物料接口服务*/
    @Autowired
    private SynMaterielServiceImpl synMaterielServiceImpl;
    /*** 衍生物料详情数据服务*/
    @Autowired
    private HzConfigModelFeatureService hzConfigModelFeatureService;

    /**
     * 已废除，不再使用
     *
     * @param puidOfModelFeatures
     * @param model
     * @return
     */
    @Deprecated
    @RequestMapping("/addToSAP")
    public String addToSAP(String[] puidOfModelFeatures, Model model) {
        List<HzCfg0ModelFeature> features = new ArrayList<HzCfg0ModelFeature>();
        for (String puidOfModelFeature : puidOfModelFeatures) {
            HzCfg0ModelFeature feature = hzConfigModelFeatureService.doSelectByPrimaryKey(puidOfModelFeature);
            features.add(feature);
        }
        net.sf.json.JSONObject object = synMaterielServiceImpl.tranMateriel2(features, ActionFlagOption.ADD, "HZ_CFG0_MODEL_FEATURE", "MATERIAL_CODE");
        addToModel(object, model);
        return "stage/templateOfIntegrate";
    }

    /**
     * 已废除，不再使用
     *
     * @param puidOfModelFeatures
     * @param model
     * @return
     */
    @Deprecated
    @RequestMapping("/deleteToSAP")
    public String deleteToSAP(String[] puidOfModelFeatures, Model model) {
        List<HzCfg0ModelFeature> HCMfeatures = new ArrayList<HzCfg0ModelFeature>();
        for (String puidOfModelFeature : puidOfModelFeatures) {
            HzCfg0ModelFeature feature = hzConfigModelFeatureService.doSelectByPrimaryKey(puidOfModelFeature);
            HCMfeatures.add(feature);
        }
        net.sf.json.JSONObject object = synMaterielServiceImpl.tranMateriel2(HCMfeatures, ActionFlagOption.DELETE, "HZ_CFG0_MODEL_FEATURE", "MATERIAL_CODE");
        addToModel(object, model);
        return "stage/templateOfIntegrate";
    }
}
