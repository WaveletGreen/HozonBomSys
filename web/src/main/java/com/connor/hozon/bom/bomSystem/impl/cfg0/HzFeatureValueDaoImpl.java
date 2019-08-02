///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.cfg0;
//
//import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureValueDao;
//import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureChangeBean;
//import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
//import cn.net.connor.hozon.dao.pojo.configuration.feature.HzMaterielFeatureBean;
//import cn.net.connor.hozon.dao.query.feature.HzFeatureQuery;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: Fancyears·Maylos·Malvis
// * @Description: fuck
// * @Date: Created in 2018/9/6 13:19
// * @Modified By:
// */
////@Service("hzCfg0RecordDao")
//@Service
//public class HzFeatureValueDaoImpl extends BasicDaoImpl<HzFeatureValue> implements HzFeatureValueDao {
//
//    private final static HzFeatureValue RECORD = new HzFeatureValue();
//
//    @Autowired
//    public HzFeatureValueDaoImpl() {
//        clz = HzFeatureValueDao.class;
//        clzName = clz.getCanonicalName();
//    }
//
//    //----------------------------
//    @Override
//    public int count(HzFeatureQuery dto) {
//        List<Integer> result = baseSQLUtil.executeQueryByPass(new Integer(0), dto, clzName + ".count");
//        if (result == null) {
//            return 0;
//        } else {
//            return result.get(0);
//        }
//    }
//
//    @Override
//    public int updateStatusByOrderId(Map<String,Object> params) {
//        return baseSQLUtil.executeUpdate(params, clzName + ".updateStatusByOrderId");
//    }
//
//
//    @Override
//    public int deleteByPrimaryKey(String puid) {
//        return baseSQLUtil.executeDeleteBySome(clzName + ".deleteByPrimaryKey", puid, "HZ_CFG0_RECORD");
//    }
//    /**
//     * 根据项目和特性PUID(父)获取一组特性
//     * @return
//     */
//    @Override
//    public List<HzFeatureValue> selectByFamilyUidWithProject(HzFeatureValue feature) {
//        return baseSQLUtil.executeQuery(feature, clzName + ".selectByFamilyUidWithProject");
//    }
//
//    /**
//     * 用HZCSYS+特性值（从颜色中来）查找一条车身颜色特性值
//     *
//     * @param record
//     * @return
//     */
//    @Override
//    public HzFeatureValue selectByCodeAndDescWithMainItem(HzFeatureValue record) {
//        return baseSQLUtil.executeQueryById(record, clzName + ".selectByCodeAndDescWithMainItem");
//    }
//    /**
//     * 用"车身颜色"+特性值（从颜色中来）查找一条车身颜色特性值
//     *
//     * @param record
//     * @return
//     */
//    @Override
//    public HzFeatureValue selectByCodeAndCnDescWithMainItem(HzFeatureValue record) {
//        return baseSQLUtil.executeQueryById(record, clzName + ".selectByCodeAndCnDescWithMainItem");
//    }
//    @Override
//    public List<HzFeatureValue> selectCfg0ListByPuids(Map<String, Object> _map) {
//        return baseSQLUtil.executeQueryByPass(RECORD, _map, clzName + ".selectCfg0ListByPuids");
//    }
//
//    @Override
//    public List<HzFeatureValue> selectByCodeAndDesc(HzFeatureValue record) {
//        return baseSQLUtil.executeQuery(record, clzName + ".selectByCodeAndDesc");
//    }
//
//    @Override
//    public int deleteCfgByList(List<HzFeatureValue> records) {
//        return baseSQLUtil.executeDelete(records, clzName + ".deleteCfgByList");
//    }
//
//    @Override
//    public int setIsSent(Map<String, Object> _map) {
//        return baseSQLUtil.executeUpdate(_map, clzName + ".setIsSent");
//    }
//
//    /**
//     * 设置进入流程状态
//     *
//     * @param _map
//     * @return
//     */
//    @Override
//    public int setToProcess(Map<String, Object> _map) {
//        return baseSQLUtil.executeUpdate(_map, clzName + ".setToProcess");
//    }
//
//    @Override
//    public List<HzFeatureValue> selectByCondition(HzFeatureQuery queryBase) {
//        return baseSQLUtil.executeQueryByPass(RECORD, queryBase, clzName + ".selectByCondition");
//    }
//    @Override
//    public List<HzFeatureValue> selectListByProjectPuid(Map<String, Object> params) {
//        return baseSQLUtil.executeQueryByPass(RECORD, params, clzName + ".selectListByProjectPuid");
//    }
//
//    @Override
//    public List<HzMaterielFeatureBean> selectMaterielFeatureByProjectPuid(String projectPuid) {
//        return baseSQLUtil.executeQueryByPass(new HzMaterielFeatureBean(), projectPuid, clzName + ".selectMaterielFeatureByProjectPuid");
//    }
//
//    @Override
//    public int updateList(List<HzFeatureValue> hzFeatureValueList) {
//        return baseSQLUtil.executeUpdate(hzFeatureValueList, clzName + ".updateList");
//    }
//
//
//    @Override
//    public int updateByVwoid(HzFeatureValue hzFeatureValue) {
//        return baseSQLUtil.executeUpdate(hzFeatureValue, clzName + ".updateByVwoid");
//    }
//
//    @Override
//    public List<HzFeatureValue> selectByPuids(List<String> puidList) {
//        return baseSQLUtil.executeQueryByPass(new HzFeatureValue(), puidList, clzName + ".selectByPuids");
//    }
//
//    @Override
//    public int updateStatus(List<HzFeatureValue> hzFeatureValueListDelete) {
//        return baseSQLUtil.executeUpdate(hzFeatureValueListDelete, clzName + ".updateStatus");
//    }
//
//    @Override
//    public int updateListAll(List<HzFeatureValue> hzFeatureValueListUpdata) {
//        return baseSQLUtil.executeUpdate(hzFeatureValueListUpdata, clzName + ".updateListAll");
//    }
//
//    @Override
//    public int updateByChangeId(List<Long> changeFeatureIds) {
//        return baseSQLUtil.executeUpdate(changeFeatureIds, clzName + ".updateByChangeId");
//    }
//
//    @Override
//    public int updateStatusByChangeDate(List<HzFeatureChangeBean> hzFeatureChangeBeans) {
//        return baseSQLUtil.executeUpdate(hzFeatureChangeBeans, clzName + ".updateStatusByChangeDate");
//    }
//
//    @Override
//    public int updateDescByDictionaryLib(HzFeatureValue hzFeatureValue) {
//        return baseSQLUtil.executeUpdate(hzFeatureValue, clzName + ".updateDescByDictionaryLib");
//    }
//
//    @Override
//    public List<HzFeatureValue> selectByDictionaryLibId(String puid) {
//        return baseSQLUtil.executeQueryByPass(new HzFeatureValue(), puid, clzName + ".selectByDictionaryLibId");
//    }
//
//    @Override
//    public List<HzFeatureValue> selectByChangeOrderId(Long vwoId) {
//        return baseSQLUtil.executeQueryByPass(new HzFeatureValue(), vwoId, clzName + ".selectByChangeOrderId");
//    }
//
//    @Override
//    public int deleteByOrderId(Long orderId) {
//        return baseSQLUtil.executeDelete(orderId, clzName + ".deleteByOrderId");
//    }
//
//    @Override
//    public List<HzFeatureValue> selectByFamilyName(HzFeatureValue hzFeatureValue) {
//        return baseSQLUtil.executeQueryByPass(new HzFeatureValue(), hzFeatureValue, clzName + ".selectByFamilyName");
//    }
//
//    @Override
//    public List<HzFeatureValue> doSelectByDescAndProjectId(HzFeatureValue hzFeatureValue) {
//        return baseSQLUtil.executeQueryByPass(new HzFeatureValue(), hzFeatureValue, clzName + ".doSelectByDescAndProjectId");
//    }
//
//}
