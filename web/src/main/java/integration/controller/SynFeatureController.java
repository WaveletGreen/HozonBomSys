/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.controller;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import integration.service.integrate.SynFeatureService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 10:53
 * @Modified By:
 */
@Controller
@RequestMapping("synFeature")
public class SynFeatureController extends ExtraIntegrateController {
    /*** 同步特性*/
    @Autowired
    private SynFeatureService synFeatureService;

    /**
     * 已废除
     *
     * @param records
     * @param model
     * @return
     * @throws Exception
     */
    @Deprecated
    @RequestMapping(value = "/sendToERP", method = RequestMethod.POST)
    public String sendToERP(@RequestBody List<HzFeatureValue> records, Model model) throws Exception {
        JSONObject result = synFeatureService.addFeature(records);
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }

//    /**
//     * 已废除
//     *
//     * @throws Exception
//     */
//    @Deprecated
//    @RequestMapping(value = "/sendRelToERP", method = RequestMethod.POST)
//    public String sendRelToERP(@RequestBody List<HzRelevanceResponseDTO> beans, Model model) throws Exception {
//        //清空上次传输的内容
//        JSONObject result;
//        List<String> puids = new ArrayList<>();
//        List<HzFeatureValue> records;
//        //只要求获取puid
//        beans.forEach(bean -> puids.add(bean.getId()));
//        //从根本根本上查找数据
//        records = hzFeatureService.doLoadListByPuids(puids);
//        //整理数据
//        List<HzRelevanceResponseDTO> myBeans = new ArrayList<>();
//        synRelevanceService.sortData(records, myBeans);
//        result = synRelevanceService.addRelevance(myBeans);
//        addToModel(result, model);
//        return "stage/templateOfIntegrate";
//    }

//    /**
//     * 已废除
//     *
//     * @throws Exception
//     */
//    @Deprecated
//    @RequestMapping(value = "/sendRelToERPDelete", method = RequestMethod.POST)
//    public String sendRelToERPDelete(@RequestBody List<HzRelevanceResponseDTO> beans, Model model) throws Exception {
//        //清空上次传输的内容
//        JSONObject result;
//        List<String> puids = new ArrayList<>();
//        List<HzFeatureValue> records;
//        //只要求获取puid
//        beans.forEach(bean -> puids.add(bean.getId()));
//        //从根本根本上查找数据
//        records = hzFeatureService.doLoadListByPuids(puids);
//        //整理数据
//        List<HzRelevanceResponseDTO> myBeans = new ArrayList<>();
//        synRelevanceService.sortData(records, myBeans);
//        result = synRelevanceService.deleteRelevance(myBeans);
//        addToModel(result, model);
//        return "stage/templateOfIntegrate";
//    }
}
