/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dto.HzFeatureQueryDto;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzCfg0OfBomLineService;
import com.connor.hozon.bom.bomSystem.service.bom.HzBomDataService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0BomLineOfModelService;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0ModelService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.fullCfg.HzCfg0OfBomLineRecord;

import java.util.*;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 预研时用到的controller，已不再符合正式业务要求，可以删除
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
@Deprecated
//@Controller
//@RequestMapping("/loadBom")
public class HzLoadBomDataController {
    private final HzCfg0BomLineOfModelService hzCfg0BomLineOfModelService;
    @Autowired
    HzBomDataService hzBomDataService;

    @Autowired
    HzBomLineRecordDaoImpl hzBomLineRecordDao;
    @Autowired
    HzBomMainRecordDao hzBomMainRecordDao;
    @Autowired
    IHzCfg0OfBomLineService iHzCfg0OfBomLineService;
    /**
     * 特性
     */
    @Autowired
    HzCfg0Service hzCfg0Service;
    /**
     * 车型模型
     */
    @Autowired
    HzCfg0ModelService hzCfg0ModelService;

    private boolean debug = false;

    public HzLoadBomDataController(HzCfg0BomLineOfModelService hzCfg0BomLineOfModelService) {
        this.hzCfg0BomLineOfModelService = hzCfg0BomLineOfModelService;
    }

    @RequestMapping(value = "/loadCfg0BomLineOfModel", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject loadCfg0BomLineOfModel(@RequestParam String bdf) {
        return hzCfg0BomLineOfModelService.parse(bdf);
    }

    /**
     * 获取列信息
     *
     * @param projectPuid 项目的puid
     * @return
     */
    @RequestMapping(value = "/loadColumns", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray loadColumns(@RequestParam String projectPuid) {

        return hzBomDataService.doLoadColumns(projectPuid);
    }

    /**
     * 下载所有的bom行信息
     *
     * @param projectPuid 项目的puid
     * @return
     */
    @RequestMapping(value = "/loadByID", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray getLineRecords(@RequestParam String projectPuid) {
        return hzBomDataService.load(projectPuid);
    }

    /**
     * 下载所有的bom行信息
     *
     * @param projectPuid 项目的puid
     * @return
     */
    @RequestMapping(value = "/loadByID2", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray getLineRecords2(@RequestParam String projectPuid) {
        JSONArray array = new JSONArray();
        array.addAll(loadColumns(projectPuid));
        array.addAll(hzBomDataService.load(projectPuid));
//        /**搜索全部特性，并经过P_CFG0_OBJECT_ID 升序排序*/
//        QueryBase queryBase = new QueryBase();
//        queryBase.setSort("P_CFG0_OBJECT_ID");
//        hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, queryBase);
        /**
         * 获取该项目下的所有车型模型
         */
        hzCfg0ModelService.doSelectByProjectPuid(projectPuid);
        return array;
    }

    @RequestMapping(value = "reflectTo2YPage", method = RequestMethod.GET)
    public String reflectTo2YPage(@RequestParam("projectPuid") String projectPuid, Model model) {
        if (null == projectPuid || "".equals(projectPuid)) {
            model.addAttribute("msg", "请选择1个项目进行操作");
            return "errorWithEntity";
        }
        HzFeatureQueryDto queryBase = new HzFeatureQueryDto();
        queryBase.setSort("P_CFG0_OBJECT_ID");
        List<HzCfg0Record> features = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, queryBase);
        List<HzBomLineRecord> lines = hzBomDataService.doSelect2YByProjectPuid(projectPuid);
        model.addAttribute("features", features);
        model.addAttribute("lines", lines);
        model.addAttribute("action", "./loadBom/reflect2Y");
        return "bom/reflectTo2Y";
    }

    @RequestMapping(value = "reflect2Y", method = RequestMethod.POST)
    @ResponseBody
    public boolean reflect2Y(@RequestBody Map<String, String> params) {
        List<HzCfg0OfBomLineRecord> records = new ArrayList<>();
        HZBomMainRecord mainRecord = new HZBomMainRecord();
        Map<String, String> myParam = new HashMap<>();
        Map<String, String> coach = new HashMap<>();
        String projectUid;
        if (params != null) {
            int index = 0;
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                if (index <= 0) {
                    projectUid = iterator.next().getValue();
                    mainRecord = hzBomMainRecordDao.selectByProjectPuid(projectUid);
                    myParam.put("projectId", projectUid);
                    index++;
                    continue;
                }
                HzCfg0OfBomLineRecord record = new HzCfg0OfBomLineRecord();

                Map.Entry<String, String> p1 = iterator.next();
                if (!iterator.hasNext()) {
                    break;
                }
                Map.Entry<String, String> p2 = iterator.next();

                if (p2 == null || p1 == null) {
                    continue;
                }
                myParam.put("puid", p2.getValue());
                if (coach.containsKey(p2.getValue())) {
                    continue;
                }

                HzBomLineRecord bomLineRecord = hzBomLineRecordDao.findBomLineByPuid(myParam);
                if (bomLineRecord == null) {
                    continue;
                }
                //需要强关联
                record.setpCfg0name(p1.getKey());
                //对应的bom行的UID
                record.setpToCfg0IdOfBl(p1.getValue());
                //对应的BOM行item_id
                record.setpBomLineName(bomLineRecord.getLineID());
                record.setpBomlinepuid(bomLineRecord.getPuid());
                HzCfg0Record record1 = hzCfg0Service.doSelectOneByPuid(p1.getValue());
                if (record1 == null) {
                    continue;
                }
                record.setpCfg0familyname(record1.getpCfg0FamilyName());
                record.setpBomDigifaxId(mainRecord.getPuid());
                record.setPuid(UUIDHelper.generateUpperUid());
                records.add(record);
                coach.put(p2.getValue(), p2.getValue());
                index++;
            }
            if (!debug) {
                return iHzCfg0OfBomLineService.doInsertByBatch(records);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
