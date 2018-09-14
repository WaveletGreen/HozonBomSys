package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0ColorSet;
import sql.pojo.cfg.HzCfg0ModelColor;
import sql.pojo.cfg.HzCfg0OptionFamily;
import sql.pojo.cfg.HzColorModel;
import sql.redis.SerializeUtil;

import java.util.*;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 10:54
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

    public boolean doInsertOne(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.insertOne(color) > 0 ? true : false;
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
            List<HzColorModel> cm = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
            //把历史数据取出来，进行分步存储
            if (null == color.getUpdateDefault() || 0 == color.getUpdateDefault()) {
                if (null != color.getpColorfulMapBlock()) {
                    Object o = SerializeUtil.unserialize(color.getpColorfulMapBlock());
                    //旧数据需要进行插入，然后判断是否有历史数据，如果有历史数据，删除历史数据，插入，更新当前颜色车型的状态值为1
                    if (o instanceof HashMap) {
                        HashMap<String, String> _map = (HashMap) o;
                        List<HzColorModel> colorList = new ArrayList<>();
                        int index = 0;
                        String colorUid = "-";
                        for (Map.Entry<String, String> entry : _map.entrySet()) {
                            if (mapOfColorSet.containsKey(entry.getValue())) {
                                colorUid = mapOfColorSet.get(entry.getValue()).getPuid();
                            } else {
                                colorUid = "-";
                            }
                            HzColorModel hzColorModel = new HzColorModel();
                            hzColorModel.setPuid(UUIDHelper.generateUpperUid());
                            hzColorModel.setModelUid(color.getPuid());
                            hzColorModel.setColorUid(colorUid);
                            hzColorModel.setCfgUid(families.get(index).getPuid());
                            hzColorModel.setCfgMainUid(color.getpCfg0MainRecordOfMC());
                            hzColorModel.setCreateDate(date);
                            hzColorModel.setModifyDate(date);
                            hzColorModel.setCreator(user.getUserName());
                            hzColorModel.setModifier(user.getUserName());
                            colorList.add(hzColorModel);
                            //?，估计有bug
                            _result.put("s" + index, entry.getValue());
                            index++;
                        }
                        if (hzColorModelService.doInsertByBatch(colorList) > 0) {
                            logger.warn("批量新增配置颜色数据成功");
                            List<HzColorModel> cms = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
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
        List<HzCfg0OptionFamily> familiesNew = hzCfg0OptionFamilyService.getFamilies(projectPuid);//hzCfg0OptionFamilyDao.selectNameByMainId2(projectPuid);
        List<String> column = hzCfg0OptionFamilyService.getColumnNewWithFamilies(familiesNew, "<br/>");

        /**
         * 用于筛选颜色集
         */
        List<HzCfg0ColorSet> colorSets = hzCfg0ColorSetService.doGetAll();
        Map<String, HzCfg0ColorSet> mapOfColorSet = new HashMap<>();
        colorSets.forEach(set -> mapOfColorSet.put(set.getpColorCode(), set));

        List<Map<String, String>> res = new ArrayList<>();
        Map<String, HzColorModel> coach = new LinkedHashMap<>();
        colorSet.forEach(color -> {
            Map<String, String> _result = new LinkedHashMap<>();
            _result.put("puid", color.getPuid());
            _result.put("codeOfColorModel", color.getpCodeOfColorfulModel());
            _result.put("descOfColorModel", color.getpDescOfColorfulModel());
            _result.put("modelShell", color.getpModelShellOfColorfulModel());
            _result.put("modeColorIsMultiply", color.getpColorIsMultiply());
//            List<HzColorModel> cm = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
            List<HzColorModel> cm = hzColorModelService.doSelectByModelUidWithColor2(color.getPuid());
            coach.clear();
            cm.forEach(c -> coach.put(c.getCfgUid(), c));

            //把历史数据取出来，进行分步存储
            if (null == color.getUpdateDefault() || 0 == color.getUpdateDefault()) {
                if (null != color.getpColorfulMapBlock()) {
                    Object o = SerializeUtil.unserialize(color.getpColorfulMapBlock());
                    //旧数据需要进行插入，然后判断是否有历史数据，如果有历史数据，删除历史数据，插入，更新当前颜色车型的状态值为1
                    if (o instanceof HashMap) {
                        HashMap<String, String> _map = (HashMap) o;
                        List<HzColorModel> colorList = new ArrayList<>();
                        int index = 0;
                        String colorUid = "-";
                        for (Map.Entry<String, String> entry : _map.entrySet()) {
                            if (mapOfColorSet.containsKey(entry.getValue())) {
                                colorUid = mapOfColorSet.get(entry.getValue()).getPuid();
                            } else {
                                colorUid = "-";
                            }
                            HzColorModel hzColorModel = new HzColorModel();
                            hzColorModel.setPuid(UUIDHelper.generateUpperUid());
                            hzColorModel.setModelUid(color.getPuid());
                            hzColorModel.setColorUid(colorUid);
                            hzColorModel.setCfgMainUid(color.getpCfg0MainRecordOfMC());
                            hzColorModel.setCreateDate(date);
                            hzColorModel.setModifyDate(date);
                            hzColorModel.setCreator(user.getUserName());
                            hzColorModel.setModifier(user.getUserName());
                            colorList.add(hzColorModel);
                            //?，估计有bug
                            _result.put("s" + index, entry.getValue());
                            index++;
                        }
                        if (hzColorModelService.doInsertByBatch(colorList) > 0) {
                            logger.warn("批量新增配置颜色数据成功");
                            List<HzColorModel> cms = hzColorModelService.doSelectByModelUidWithColor(color.getPuid());
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
                    List<HzColorModel> list = new ArrayList<>();
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
                            HzColorModel hcm = new HzColorModel();
                            hcm.setColorUid("-");
                            hcm.setPuid(UUIDHelper.generateUpperUid());
                            hcm.setCreator(user.getUsername());
                            hcm.setCreateDate(date);
                            hcm.setModifier(user.getUsername());
                            hcm.setModifyDate(date);
                            hcm.setModelUid(color.getPuid());
                            hcm.setCfgMainUid(projectPuid);
                            hcm.setCfgUid(familiesNew.get(i).getPuid());
                            list.add(hcm);
                            logger.error(familiesNew.get(i).getpOptionfamilyName());
                            logger.error(familiesNew.get(i).getpOptionfamilyDesc());
                            logger.error(familiesNew.get(i).getPuid());
                        }
                        if (list.size() > 0) {
                            hzColorModelService.doInsertByBatch(list);
                        }
//                        }
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

}
