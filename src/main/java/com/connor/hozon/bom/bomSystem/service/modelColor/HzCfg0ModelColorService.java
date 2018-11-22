/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.modelColor;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0OptionFamilyDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrDetailChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorModelDao;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.option.SpecialFeatureOptions;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OptionFamilyService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.color.HzCfg0ColorSetService;
import com.connor.hozon.bom.bomSystem.service.main.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.vwo.HzVwoManagerService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.cfg0.HzCfg0OptionFamily;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.color.HzCfg0ColorSet;
import sql.pojo.cfg.main.HzCfg0MainRecord;
import sql.pojo.cfg.modelColor.HzCfg0ModelColor;
import sql.pojo.cfg.modelColor.HzCfg0ModelColorDetail;
import sql.redis.SerializeUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/5/22 10:54
 * @Modified By:
 */
@Service("hzCfg0ModelColorService")
public class HzCfg0ModelColorService {
    /**
     * 车型模型
     */
    @Autowired
    HzCfg0ModelColorDao hzCfg0ModelColorDao;
    /**
     * 族
     */
    @Autowired
    HzCfg0OptionFamilyDao hzCfg0OptionFamilyDao;

    @Autowired
    HzCfg0OptionFamilyService hzCfg0OptionFamilyService;
    /**
     * 颜色车型
     */
    @Autowired
    HzColorModelService hzColorModelService;
    /**
     * 颜色库
     */
    @Autowired
    HzCfg0ColorSetService hzCfg0ColorSetService;
    @Autowired
    HzCfg0MainService hzCfg0MainService;
    @Autowired
    HzCfg0Service hzCfg0Service;

    @Autowired
    HzVwoManagerService hzVwoManagerService;

    @Autowired
    HzColorModelDao hzColorModelDao;

    @Autowired
    HzCmcrChangeDao hzCmcrChangeDao;
    @Autowired
    HzCmcrDetailChangeDao hzCmcrDetailChangeDao;
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(HzCfg0ModelColorService.class);


    public List<HzCfg0ModelColor> doLoadModelColorByMainId(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.selectByMainId(color);
    }

    public boolean doUpdateOne(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.updateByPrimaryKey(color) == 1 ? true : false;
    }

    public boolean doInsert(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.insert(color) > 0 ? true : false;
    }

    public HzCfg0ModelColor doGetById(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.selectByPrimaryKey(color);
    }

    public Map<String, Object> doLoadAll(String projectPuid) {
        Date date = new Date();
        User user = UserInfo.getUser();
        Map<String, Object> result = new HashMap<>();
        List<HzCfg0ModelColor> colorSet = hzCfg0ModelColorDao.selectAll(projectPuid);
        List<HzCfg0OptionFamily> families = hzCfg0OptionFamilyDao.selectNameByMainId(projectPuid);
        List<HzCfg0OptionFamily> familiesNew = hzCfg0OptionFamilyDao.selectNameByMainId2(projectPuid);
        /**
         * 用于筛选颜色集
         */
        List<HzCfg0ColorSet> colorSets = hzCfg0ColorSetService.doGetAll();
        Map<String, HzCfg0ColorSet> mapOfColorSet = new HashMap<>();
        colorSets.forEach(set -> mapOfColorSet.put(set.getpColorCode(), set));

        List<Map<String, String>> res = new ArrayList<>();
        colorSet.forEach(color -> {
            Map<String, String> _result = new LinkedHashMap<>();
            _result.put("puid", color.getPuid());
            _result.put("codeOfColorModel", color.getpCodeOfColorfulModel());
            _result.put("descOfColorModel", color.getpDescOfColorfulModel());
            _result.put("modelShell", color.getpModelShellOfColorfulModel());
            _result.put("modeColorIsMultiply", color.getpColorIsMultiply());
            List<HzCfg0ModelColorDetail> cm = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
            //把历史数据取出来，进行分步存储
            if (null == color.getUpdateDefault() || 0 == color.getUpdateDefault()) {
                if (null != color.getpColorfulMapBlock()) {
                    Object o = SerializeUtil.unserialize(color.getpColorfulMapBlock());
                    //旧数据需要进行插入，然后判断是否有历史数据，如果有历史数据，删除历史数据，插入，更新当前颜色车型的状态值为1
                    if (o instanceof HashMap) {
                        HashMap<String, String> _map = (HashMap) o;
                        List<HzCfg0ModelColorDetail> colorList = new ArrayList<>();
                        int index = 0;
                        String colorUid = "-";
                        for (Map.Entry<String, String> entry : _map.entrySet()) {
                            if (mapOfColorSet.containsKey(entry.getValue())) {
                                colorUid = mapOfColorSet.get(entry.getValue()).getPuid();
                            } else {
                                colorUid = "-";
                            }
                            HzCfg0ModelColorDetail hzCfg0ModelColorDetail = new HzCfg0ModelColorDetail();
                            hzCfg0ModelColorDetail.setPuid(UUIDHelper.generateUpperUid());
                            hzCfg0ModelColorDetail.setModelUid(color.getPuid());
                            hzCfg0ModelColorDetail.setColorUid(colorUid);
                            hzCfg0ModelColorDetail.setCfgUid(families.get(index).getPuid());
                            hzCfg0ModelColorDetail.setCfgMainUid(color.getpCfg0MainRecordOfMC());
                            hzCfg0ModelColorDetail.setCreateDate(date);
                            hzCfg0ModelColorDetail.setModifyDate(date);
                            hzCfg0ModelColorDetail.setCreator(user.getUserName());
                            hzCfg0ModelColorDetail.setModifier(user.getUserName());
                            colorList.add(hzCfg0ModelColorDetail);
                            //?，估计有bug
                            _result.put("s" + index, entry.getValue());
                            index++;
                        }
                        if (hzColorModelService.doInsertByBatch(colorList) > 0) {
                            logger.warn("批量新增配置颜色数据成功");
                            List<HzCfg0ModelColorDetail> cms = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
                            if (cms == null || cms.size() == 0) {
                                for (int i = 0; i < familiesNew.size(); i++) {
                                    //添加默认值
                                    _result.put("s" + i, "-");
                                }
                            } else {
                                //添加颜色值用于显示
                                for (int i = 0; i < cms.size(); i++) {
                                    _result.put("s" + i, cms.get(i).getpColorCode());
                                }
                            }
                            updateOldData(color);
                        } else {
                            logger.error("批量新增配置颜色数据失败");
                        }
//                        _map.forEach((key, value) ->
//                                _result.put(key == null ? "" : key, value == null ? "-" : value)
//                        );
                    }
                } else {
                    for (int i = 0; i < families.size(); i++) {
                        _result.put("s" + i, "-");
                    }
                }
            }
            //新数据
            else if (1 == color.getUpdateDefault()) {
                String vehicleColor = "";
                String localTemp = "";
                if (cm == null || cm.size() == 0) {
                    for (int i = 0; i < familiesNew.size(); i++) {
                        //添加默认值
                        _result.put("s" + i, "-");
                    }
                } else {
                    //添加颜色值用于显示
                    for (int i = 0; i < cm.size(); i++) {
                        if ("车身颜色".equals(familiesNew.get(i).getpOptionfamilyDesc())) {
                            vehicleColor = cm.get(i).getpColorCode();
                            localTemp = _result.get("s0");
                            _result.put("s0", vehicleColor);
                            _result.put("s" + i, localTemp);
                        } else {
                            _result.put("s" + i, cm.get(i).getpColorCode());
                        }
                    }
                }
            } else {
                for (int i = 0; i < familiesNew.size(); i++) {
                    //添加默认值
                    _result.put("s" + i, "-");
                }
            }
            res.add(_result);
        });

        result.put("totalCount", colorSet.size());
        result.put("result", res);
        return result;
    }


    public Map<String, Object> doLoadAll2(String projectPuid) {
        Date date = new Date();
        User user = UserInfo.getUser();
        Map<String, Object> result = new HashMap<>();
        List<HzCfg0ModelColor> colorSet = hzCfg0ModelColorDao.selectAll(projectPuid);
        List<HzCfg0OptionFamily> familiesNewFromDb = hzCfg0OptionFamilyService.selectForColorBluePrint(projectPuid, 1);//.getFamilies(projectPuid, 0, 1);//hzCfg0OptionFamilyDao.selectNameByMainId2(projectPuid);
        List<HzCfg0OptionFamily> familiesNew = new ArrayList<>();
        familiesNew.addAll(familiesNewFromDb.stream().filter(c->c!=null).filter(c -> false == SpecialFeatureOptions.YQCSCODE.getDesc().equals(c.getpOptionfamilyName()))
                .collect(Collectors.toList()));
        //.forEach(c -> mapWithColor.put(c.getPuid(), c));
        HzCfg0MainRecord mainRecord = hzCfg0MainService.doGetbyProjectPuid(projectPuid);
        /**
         * 用于筛选颜色集
         */
        List<HzCfg0ColorSet> colorSets = hzCfg0ColorSetService.doGetAll();
        Map<String, HzCfg0ColorSet> mapOfColorSet = new HashMap<>();
        colorSets.forEach(set -> mapOfColorSet.put(set.getpColorCode(), set));

        List<Map<String, String>> res = new ArrayList<>();
        Map<String, HzCfg0ModelColorDetail> coach = new LinkedHashMap<>();
        colorSet.forEach(color -> {
            Map<String, String> _result = new LinkedHashMap<>();
            _result.put("puid", color.getPuid());
            _result.put("codeOfColorModel", color.getpCodeOfColorfulModel());
            _result.put("descOfColorModel", color.getpDescOfColorfulModel());
            _result.put("modelShell", color.getpModelShellOfColorfulModel());
            _result.put("modeColorIsMultiply", color.getpColorIsMultiply());
            _result.put("vwoNum", String.valueOf(color.getCmcrVwoId()));
//            List<HzCfg0ModelColorDetail> cm = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
            List<HzCfg0ModelColorDetail> cm = hzColorModelService.doSelectByModelUidWithColor2(color.getPuid());
            coach.clear();
            cm.forEach(c -> coach.put(c.getCfgUid(), c));

            //把历史数据取出来，进行分步存储
            if (null == color.getUpdateDefault() || 0 == color.getUpdateDefault()) {
                if (null != color.getpColorfulMapBlock()) {
                    Object o = SerializeUtil.unserialize(color.getpColorfulMapBlock());
                    //旧数据需要进行插入，然后判断是否有历史数据，如果有历史数据，删除历史数据，插入，更新当前颜色车型的状态值为1
                    if (o instanceof HashMap) {
                        HashMap<String, String> _map = (HashMap) o;
                        List<HzCfg0ModelColorDetail> colorList = new ArrayList<>();
                        int index = 0;
                        String colorUid = "-";
                        for (Map.Entry<String, String> entry : _map.entrySet()) {
                            if (mapOfColorSet.containsKey(entry.getValue())) {
                                colorUid = mapOfColorSet.get(entry.getValue()).getPuid();
                            } else {
                                colorUid = "-";
                            }
                            HzCfg0ModelColorDetail hzCfg0ModelColorDetail = new HzCfg0ModelColorDetail();
                            hzCfg0ModelColorDetail.setPuid(UUIDHelper.generateUpperUid());
                            hzCfg0ModelColorDetail.setModelUid(color.getPuid());
                            hzCfg0ModelColorDetail.setColorUid(colorUid);
                            hzCfg0ModelColorDetail.setCfgMainUid(color.getpCfg0MainRecordOfMC());
                            hzCfg0ModelColorDetail.setCreateDate(date);
                            hzCfg0ModelColorDetail.setModifyDate(date);
                            hzCfg0ModelColorDetail.setCreator(user.getUserName());
                            hzCfg0ModelColorDetail.setModifier(user.getUserName());
                            colorList.add(hzCfg0ModelColorDetail);
                            //?，估计有bug
                            _result.put("s" + index, entry.getValue());
                            index++;
                        }
                        if (hzColorModelService.doInsertByBatch(colorList) > 0) {
                            logger.warn("批量新增配置颜色数据成功");
                            List<HzCfg0ModelColorDetail> cms = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
                            if (cms == null || cms.size() == 0) {
                                for (int i = 0; i < familiesNew.size(); i++) {
                                    //添加默认值
                                    _result.put("s" + i, "-");
                                }
                            } else {
                                //添加颜色值用于显示
                                for (int i = 0; i < cms.size(); i++) {
                                    _result.put("s" + i, cms.get(i).getpColorCode());
                                }
                            }
                            updateOldData(color);
                        } else {
                            logger.error("批量新增配置颜色数据失败");
                        }
//                        _map.forEach((key, value) ->
//                                _result.put(key == null ? "" : key, value == null ? "-" : value)
//                        );
                    }
                } else {
                }
            }
            //新数据
            else if (1 == color.getUpdateDefault()) {
                String vehicleColor = "";
                String localTemp = "";
                if (cm == null || cm.size() == 0) {
                    for (int i = 0; i < familiesNew.size(); i++) {
                        //添加默认值
                        _result.put("s" + i, "-");
                    }
                } else {
                    //添加颜色值用于显示
                    List<HzCfg0ModelColorDetail> list = new ArrayList<>();
                    for (int i = 0; i < familiesNew.size(); i++) {
//                        if ("车身颜色".equals(familiesNew.get(i).getpOptionfamilyDesc())) {
//                            vehicleColor = cm.get(i).getpColorCode();
//                            localTemp = _result.get("s0");
//                            _result.put("s0", vehicleColor);
//                            _result.put("s" + i, localTemp);F56B98AB58C04CA4AC5007C216A967BC
//                        } else {
                        if (coach.containsKey(familiesNew.get(i).getPuid())) {
                            _result.put("s" + i, coach.get(familiesNew.get(i).getPuid()).getpColorCode());
                        } else {
                            _result.put("s" + i, "-");
                            HzCfg0ModelColorDetail hcm = new HzCfg0ModelColorDetail();
                            hcm.setColorUid("-");
                            hcm.setPuid(UUIDHelper.generateUpperUid());
                            hcm.setCreator(user.getUsername());
                            hcm.setCreateDate(date);
                            hcm.setModifier(user.getUsername());
                            hcm.setModifyDate(date);
                            hcm.setModelUid(color.getPuid());
                            hcm.setCfgMainUid(mainRecord.getPuid());
                            hcm.setCfgUid(familiesNew.get(i).getPuid());
                            list.add(hcm);
                            logger.error(familiesNew.get(i).getpOptionfamilyName());
                            logger.error(familiesNew.get(i).getpOptionfamilyDesc());
                            logger.error(familiesNew.get(i).getPuid());
                        }
//                        }
                    }
                    if (list.size() > 0) {
                        hzColorModelService.doInsertByBatch(list);
                    }
                }
            } else {
                for (int i = 0; i < familiesNew.size(); i++) {
                    //添加默认值
                    _result.put("s" + i, "-");
                }

            }
            //添加状态
            _result.put("cmcrStatus",color.getCmcrStatus());
            _result.put("cmcrVwoNum",color.getCmcrVwoNum());

            res.add(_result);
        });

        result.put("totalCount", colorSet.size());
        result.put("result", res);
        return result;
    }

    /**
     * 根据主键批量删除数据
     *
     * @param colors
     * @return
     */
    public int doDelete(List<HzCfg0ModelColor> colors) {
        return hzCfg0ModelColorDao.deleteByBatch(colors);
    }

    /**
     * 更新旧数据操作
     *
     * @param color
     * @return
     */
    private boolean updateOldData(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.updateOldData(color) > 0 ? true : false;
    }

    /**
     * 带全部的特性，旧版方法,2018-9-20 11:15
     *
     * @param projectUid
     * @return
     */
    public JSONObject getColumn(String projectUid) {
        JSONObject object = new JSONObject();
        List<String> column;
        if (projectUid == null || "".equals(projectUid)) {
            object.put("status", false);
        } else {
            column = hzCfg0OptionFamilyService.getColumnNew(projectUid, "<br/>");

            if (column == null || column.size() <= 0) {
                object.put("status", false);
            } else {
                object.put("status", true);
                object.put("data", column);
            }
        }
        return object;
    }

    /**
     * 单独查找有颜色的特性，没颜色的特性不允许出现，出现同一个特性出现歧义，则不允许生成表头
     *
     * @param projectUid
     * @return
     */
    public JSONObject getColumnOnlyColor(String projectUid) {
        JSONObject object = new JSONObject();
        List<String> column;
        List<String> error = new ArrayList<>();
        if (projectUid == null || "".equals(projectUid)) {
            object.put("status", false);
        } else {
            List<HzCfg0OptionFamily> withColor = hzCfg0OptionFamilyService.selectForColorBluePrint(projectUid, 1);
            List<HzCfg0OptionFamily> withoutColor = hzCfg0OptionFamilyService.selectForColorBluePrint(projectUid, 0);
            Map<String, HzCfg0OptionFamily> mapWithColor = new LinkedHashMap<>();
            withColor.stream().filter(c -> c != null).filter(c -> false == SpecialFeatureOptions.YQCSCODE.getDesc().equals(c.getpOptionfamilyName()))
                    .collect(Collectors.toList()).forEach(c -> mapWithColor.put(c.getPuid(), c));
            if (mapWithColor.isEmpty()) {
                object.put("status", 1);
                object.put("msg", "没有找到表头，请到全配置BOM一级清单中将特性值和带颜色的2Y进行关联");
                return object;
            }
            for (int i = 0; i < withoutColor.size(); i++) {
                if (withoutColor.get(i) != null && mapWithColor.containsKey(withoutColor.get(i).getPuid())) {
                    List<HzCfg0Record> list = hzCfg0Service.doSelectByFamilyUidWithProject(withoutColor.get(i).getPuid(), projectUid);
                    StringBuilder _sb = new StringBuilder();
                    list.forEach(l -> _sb.append(l.getpCfg0ObjectId() + " "));
                    error.add(_sb.toString());
                }
            }
            if (error.size() > 0) {
                object.put("status", 0);
                object.put("msg", error);
                return object;
            } else {
                column = new ArrayList<>();
                mapWithColor.forEach((key, value) -> column.add(value.getpOptionfamilyDesc() + "<br>" + value.getpOptionfamilyName()));
            }
            if (column == null || column.size() <= 0) {
                object.put("status", 1);
                object.put("msg", "没有找到特性");
            } else {
                object.put("status", 99);
                object.put("data", column);
            }
        }
        return object;
    }

    /**
     * 发起VOW流程
     *
     * @param colors    需发起VWO流程的配色方案
     * @param projectPuid   项目ID
     * @param dynamicTitle
     * @return
     */
    public JSONObject getVWO(List<HzCfg0ModelColor> colors, String projectPuid, ArrayList<String> dynamicTitle, Long changeFromId) {
        return hzVwoManagerService.getVWO(colors,projectPuid,dynamicTitle, changeFromId);
    }


    public boolean doRelease(HzCfg0ModelColor hzCfg0ModelColor) {
        if(hzCfg0ModelColorDao.updateByVwoId(hzCfg0ModelColor)==1){
            return true;
        }else {
            return false;
        }
    }
}
