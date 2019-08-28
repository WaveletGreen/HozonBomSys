/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.configuration.modelColor.impl;

import cn.net.connor.hozon.common.util.SerializeUtils;
import cn.net.connor.hozon.common.util.UUIDHelper;
import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureDao;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeature;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCfg0ModelColor;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCfg0ModelColorDetail;
import cn.net.connor.hozon.dao.pojo.depository.color.HzCfg0ColorSet;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import cn.net.connor.hozon.dao.pojo.sys.User;
import cn.net.connor.hozon.services.service.depository.color.impl.HzColorSetServiceImpl;
import cn.net.connor.hozon.services.service.main.HzMainConfigService;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.dao.configuration.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.dao.configuration.modelColor.HzCmcrChangeDao;
import com.connor.hozon.dao.configuration.modelColor.HzCmcrDetailChangeDao;
import com.connor.hozon.dao.configuration.modelColor.HzColorModelDao;
import cn.net.connor.hozon.services.common.option.SpecialFeatureOptions;
import com.connor.hozon.service.configuration.feature.impl.FeatureServiceImpl;
import com.connor.hozon.service.configuration.feature.impl.FeatureValueServiceImpl;
import com.connor.hozon.service.configuration.modelColor.HzCfg0ModelColorService;
import com.connor.hozon.service.change.vwo.impl.HzVwoManagerService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/5/22 10:54
 * @Modified By:
 */
@Service
@Slf4j
public class HzCfg0ModelColorServiceImpl implements HzCfg0ModelColorService {
    /**
     * 车型模型
     */
    @Autowired
    private HzCfg0ModelColorDao hzCfg0ModelColorDao;
    /**
     * 族
     */
    @Autowired
    private HzFeatureDao hzFeatureDao;

    @Autowired
    private FeatureServiceImpl hzCfg0OptionFamilyService;
    /**
     * 颜色车型
     */
    @Autowired
    private HzColorModelServiceImpl hzColorModelService;
    /**
     * 颜色库
     */
    @Autowired
    private HzColorSetServiceImpl hzColorSetServiceImpl;
    @Autowired
    private HzMainConfigService hzMainConfigService;
    @Autowired
    private FeatureValueServiceImpl hzFeatureServiceImpl;

    @Autowired
    private HzVwoManagerService hzVwoManagerService;

    @Autowired
    private HzColorModelDao hzColorModelDao;

    @Autowired
    private HzCmcrChangeDao hzCmcrChangeDao;
    @Autowired
    private HzCmcrDetailChangeDao hzCmcrDetailChangeDao;

    @Override
    public List<HzCfg0ModelColor> doLoadModelColorByMainId(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.selectByMainId(color);
    }

    @Override
    public boolean doUpdateOne(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.updateByPrimaryKey(color) == 1 ? true : false;
    }

    @Override
    public boolean doInsert(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.insert(color) > 0 ? true : false;
    }

    @Override
    public HzCfg0ModelColor doGetById(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.selectByPrimaryKey(color);
    }

//    public Map<String, Object> doLoadAll(String projectPuid) {
//        Date date = new Date();
//        User user = UserInfo.getUser();
//        Map<String, Object> result = new HashMap<>();
//        List<HzCfg0ModelColor> colorSet = hzCfg0ModelColorDao.selectByQueryObject(projectPuid);
//        List<HzFeature> families = hzFeatureDao.selectByProjectIdWithOrderMainId(projectPuid);
//        List<HzFeature> familiesNew = hzFeatureDao.selectByProjectIdWithOrderPuid(projectPuid);
//        /**
//         * 用于筛选颜色集
//         */
//        List<HzCfg0ColorSet> colorSets = hzColorSetServiceImpl.doGetAll();
//        Map<String, HzCfg0ColorSet> mapOfColorSet = new HashMap<>();
//        colorSets.forEach(set -> mapOfColorSet.put(set.getpColorCode(), set));
//
//        List<Map<String, String>> res = new ArrayList<>();
//        colorSet.forEach(color -> {
//            Map<String, String> _result = new LinkedHashMap<>();
//            _result.put("puid", color.getPuid());
//            _result.put("codeOfColorModel", color.getpCodeOfColorfulModel());
//            _result.put("descOfColorModel", color.getpDescOfColorfulModel());
//            _result.put("modelShell", color.getpModelShellOfColorfulModel());
//            _result.put("modeColorIsMultiply", color.getpColorIsMultiply());
//            List<HzCfg0ModelColorDetail> cm = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
//            //把历史数据取出来，进行分步存储
//            if (null == color.getUpdateDefault() || 0 == color.getUpdateDefault()) {
//                if (null != color.getpColorfulMapBlock()) {
//                    Object o = SerializeUtils.unserialize(color.getpColorfulMapBlock());
//                    //旧数据需要进行插入，然后判断是否有历史数据，如果有历史数据，删除历史数据，插入，更新当前颜色车型的状态值为1
//                    if (o instanceof HashMap) {
//                        HashMap<String, String> _map = (HashMap) o;
//                        List<HzCfg0ModelColorDetail> colorList = new ArrayList<>();
//                        int index = 0;
//                        String colorUid = "-";
//                        for (Map.Entry<String, String> entry : _map.entrySet()) {
//                            if (mapOfColorSet.containsKey(entry.getValue())) {
//                                colorUid = mapOfColorSet.get(entry.getValue()).getPuid();
//                            } else {
//                                colorUid = "-";
//                            }
//                            HzCfg0ModelColorDetail hzCfg0ModelColorDetail = new HzCfg0ModelColorDetail();
//                            hzCfg0ModelColorDetail.setPuid(UUIDHelper.generateUpperUid());
//                            hzCfg0ModelColorDetail.setModelUid(color.getPuid());
//                            hzCfg0ModelColorDetail.setColorUid(colorUid);
//                            hzCfg0ModelColorDetail.setCfgUid(families.get(index).getId());
//                            hzCfg0ModelColorDetail.setCfgMainUid(color.getpCfg0MainRecordOfMC());
//                            hzCfg0ModelColorDetail.setCreateDate(date);
//                            hzCfg0ModelColorDetail.setModifyDate(date);
//                            hzCfg0ModelColorDetail.setCreator(user.getUsername());
//                            hzCfg0ModelColorDetail.setModifier(user.getUsername());
//                            colorList.add(hzCfg0ModelColorDetail);
//                            //?，估计有bug
//                            _result.put("s" + index, entry.getValue());
//                            index++;
//                        }
//                        if (hzColorModelService.insertByBatch(colorList) > 0) {
//                            log.warn("批量新增配置颜色数据成功");
//                            List<HzCfg0ModelColorDetail> cms = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
//                            if (cms == null || cms.size() == 0) {
//                                for (int i = 0; i < familiesNew.size(); i++) {
//                                    //添加默认值
//                                    _result.put("s" + i, "-");
//                                }
//                            } else {
//                                //添加颜色值用于显示
//                                for (int i = 0; i < cms.size(); i++) {
//                                    _result.put("s" + i, cms.get(i).getpColorCode());
//                                }
//                            }
//                            updateOldData(color);
//                        } else {
//                            log.error("批量新增配置颜色数据失败");
//                        }
////                        _map.forEach((key, value) ->
////                                _result.put(key == null ? "" : key, value == null ? "-" : value)
////                        );
//                    }
//                } else {
//                    for (int i = 0; i < families.size(); i++) {
//                        _result.put("s" + i, "-");
//                    }
//                }
//            }
//            //新数据
//            else if (1 == color.getUpdateDefault()) {
//                String vehicleColor = "";
//                String localTemp = "";
//                if (cm == null || cm.size() == 0) {
//                    for (int i = 0; i < familiesNew.size(); i++) {
//                        //添加默认值
//                        _result.put("s" + i, "-");
//                    }
//                } else {
//                    //添加颜色值用于显示
//                    for (int i = 0; i < cm.size(); i++) {
//                        if ("车身颜色".equals(familiesNew.get(i).getFeatureDesc())) {
//                            vehicleColor = cm.get(i).getpColorCode();
//                            localTemp = _result.get("s0");
//                            _result.put("s0", vehicleColor);
//                            _result.put("s" + i, localTemp);
//                        } else {
//                            _result.put("s" + i, cm.get(i).getpColorCode());
//                        }
//                    }
//                }
//            } else {
//                for (int i = 0; i < familiesNew.size(); i++) {
//                    //添加默认值
//                    _result.put("s" + i, "-");
//                }
//            }
//            res.add(_result);
//        });
//
//        result.put("totalCount", colorSet.size());
//        result.put("result", res);
//        return result;
//    }

    @Override
    public Map<String, Object> doLoadAll2(String projectPuid) {
        Date date = new Date();
        User user = UserInfo.getUser();
        Map<String, Object> result = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<HzCfg0ModelColor> colorSet = hzCfg0ModelColorDao.selectAll(projectPuid);
        List<HzFeature> familiesNewFromDb = hzCfg0OptionFamilyService.selectForColorBluePrint(projectPuid, 1);//.getFamilies(projectPuid, 0, 1);//hzCfg0OptionFamilyDao.selectByProjectIdWithOrderPuid(projectPuid);
        List<HzFeature> familiesNew = new ArrayList<>();
        familiesNew.addAll(familiesNewFromDb.stream().filter(c -> c != null).filter(c -> false == SpecialFeatureOptions.YQCSCODE.getDesc().equals(c.getFeatureCode()))
                .collect(Collectors.toList()));
        //.forEach(c -> mapWithColor.put(c.getId(), c));
        HzMainConfig mainRecord = hzMainConfigService.selectByProjectId(projectPuid);
        /**
         * 用于筛选颜色集
         */
        List<HzCfg0ColorSet> colorSets = hzColorSetServiceImpl.doGetAll();
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
            _result.put("cmcrEffectedDate", color.getCmcrEffectedDate() == null ? "" : sdf.format(color.getCmcrEffectedDate()));
//            List<HzCfg0ModelColorDetail> cm = hzColorModelService.doSelectByModelUidWithColor(color.getId());
            List<HzCfg0ModelColorDetail> cm = hzColorModelService.doSelectByModelUidWithColor2(color.getPuid());
            coach.clear();
            cm.forEach(c -> coach.put(c.getCfgUid(), c));

            //把历史数据取出来，进行分步存储
            if (null == color.getUpdateDefault() || 0 == color.getUpdateDefault()) {
                if (null != color.getpColorfulMapBlock()) {
                    Object o = SerializeUtils.unserialize(color.getpColorfulMapBlock());
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
                            hzCfg0ModelColorDetail.setCreator(user.getUsername());
                            hzCfg0ModelColorDetail.setModifier(user.getUsername());
                            colorList.add(hzCfg0ModelColorDetail);
                            //?，估计有bug
                            _result.put("s" + index, entry.getValue());
                            index++;
                        }
                        if (hzColorModelService.doInsertByBatch(colorList) > 0) {
                            log.warn("批量新增配置颜色数据成功");
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
                            log.error("批量新增配置颜色数据失败");
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
//                        if ("车身颜色".equals(familiesNew.get(i).getFeatureDesc())) {
//                            vehicleColor = cm.get(i).getpColorCode();
//                            localTemp = _result.get("s0");
//                            _result.put("s0", vehicleColor);
//                            _result.put("s" + i, localTemp);F56B98AB58C04CA4AC5007C216A967BC
//                        } else {
                        if (coach.containsKey(familiesNew.get(i).getId())) {
                            _result.put("s" + i, coach.get(familiesNew.get(i).getId()).getpColorCode());
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
                            hcm.setCfgMainUid(mainRecord.getId());
                            hcm.setCfgUid(familiesNew.get(i).getId());
                            list.add(hcm);
                            log.error(familiesNew.get(i).getFeatureCode());
                            log.error(familiesNew.get(i).getFeatureDesc());
                            log.error(familiesNew.get(i).getId());
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
            _result.put("cmcrStatus", color.getCmcrStatus());
            _result.put("cmcrVwoNum", color.getCmcrVwoNum());

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
    @Override
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
    @Override
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
    @Override
    public JSONObject getColumnOnlyColor(String projectUid) {
        JSONObject object = new JSONObject();
        List<String> column;
        List<String> error = new ArrayList<>();
        if (projectUid == null || "".equals(projectUid)) {
            object.put("status", false);
        } else {
            List<HzFeature> withColor = hzCfg0OptionFamilyService.selectForColorBluePrint(projectUid, 1);
            List<HzFeature> withoutColor = hzCfg0OptionFamilyService.selectForColorBluePrint(projectUid, 0);
            Map<String, HzFeature> mapWithColor = new LinkedHashMap<>();
            withColor.stream().filter(c -> c != null).filter(c -> false == SpecialFeatureOptions.YQCSCODE.getDesc().equals(c.getFeatureCode()))
                    .collect(Collectors.toList()).forEach(c -> mapWithColor.put(c.getId(), c));
            if (mapWithColor.isEmpty()) {
                object.put("status", 1);
                object.put("msg", "没有找到表头，请到全配置BOM一级清单中将特性值和带颜色的2Y进行关联");
                return object;
            }
            for (int i = 0; i < withoutColor.size(); i++) {
                if (withoutColor.get(i) != null && mapWithColor.containsKey(withoutColor.get(i).getId())) {
                    List<HzFeatureValue> list = hzFeatureServiceImpl.selectByFamilyUidWithProject(withoutColor.get(i).getId(), projectUid);
                    StringBuilder _sb = new StringBuilder();
                    list.forEach(l -> _sb.append(l.getFeatureValueCode() + " "));
                    error.add(_sb.toString());
                }
            }
            if (error.size() > 0) {
                object.put("status", 0);
                object.put("msg", error);
                return object;
            } else {
                column = new ArrayList<>();
                mapWithColor.forEach((key, value) -> column.add(value.getFeatureDesc() + "<br>" + value.getFeatureCode()));
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
     * @param colors       需发起VWO流程的配色方案
     * @param projectPuid  项目ID
     * @param dynamicTitle
     * @return
     */
    @Override
    public JSONObject getVWO(List<HzCfg0ModelColor> colors, String projectPuid, ArrayList<String> dynamicTitle, Long changeFromId) {
        return hzVwoManagerService.getVWO(colors, projectPuid, dynamicTitle, changeFromId);
    }

    @Override
    public boolean doRelease(HzCfg0ModelColor hzCfg0ModelColor) {
        if (hzCfg0ModelColorDao.updateByVwoId(hzCfg0ModelColor) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int doUpdateStatus(List<HzCfg0ModelColor> colors) {
        return hzCfg0ModelColorDao.updateStatus(colors);
    }
}
