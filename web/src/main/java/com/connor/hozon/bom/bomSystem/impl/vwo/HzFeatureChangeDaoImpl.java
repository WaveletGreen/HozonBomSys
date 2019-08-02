///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.vwo;
//
//import cn.net.connor.hozon.dao.dao.change.vwo.HzFeatureChangeDao;
//import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureChangeBean;
//import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import org.springframework.stereotype.Repository;
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
//@Repository
//public class HzFeatureChangeDaoImpl extends BasicDaoImpl<HzFeatureChangeBean> implements HzFeatureChangeDao {
//
//    public HzFeatureChangeDaoImpl() {
//        clz = HzFeatureChangeDao.class;
//        clzName = clz.getCanonicalName();
//    }
//    @Override
//    public int insertList( Map<String, Object> map) {
//        return baseSQLUtil.executeInsert(map, clzName + ".insertList");
//    }
//
//    //------------------------------------------
//    /**
//     * 查找特性下最新的更改
//     *
//     * @param bean
//     * @return
//     */
//    @Override
//    public HzFeatureChangeBean findNewestChange(HzFeatureChangeBean bean) {
//        return baseSQLUtil.executeQueryById(bean, clzName + ".findNewestChange");
//    }
//
//    @Override
//    public List<HzFeatureChangeBean> selectByVwoId(HzFeatureChangeBean bean) {
//        return baseSQLUtil.executeQuery(bean, clzName + ".selectByVwoId");
//    }
//
//
//    /**
//     * 根据VWO ID找到当前变更的特性UID
//     * @param bean
//     * @return
//     */
//    @Override
//    public List<HzFeatureChangeBean> selectCfgUidsByVwoId(HzFeatureChangeBean bean) {
//        return baseSQLUtil.executeQuery(bean, clzName + ".selectCfgUidsByVwoId");
//    }
//
//    @Override
//    public List<HzFeatureChangeBean> findLastTwoChange(HzFeatureChangeBean hzFeatureChangeBean) {
//        return baseSQLUtil.executeQuery(hzFeatureChangeBean, clzName+".findLastTwoChange");
//    }
//
//    @Override
//    public List<HzFeatureChangeBean> selectHasEffect(List<HzFeatureValue> records) {
//        return baseSQLUtil.executeQueryByPass(new HzFeatureChangeBean(),records,clzName+".selectHasEffect");
//    }
//
//    @Override
//    public int updateStatusByOrderId(Map<String, Object> map) {
//        return baseSQLUtil.executeUpdate(map,clzName+".updateStatusByOrderId");
//    }
//
//    @Override
//    public int deleteByPrimaryKeys(List<Long> changeFeatureIds) {
//        return baseSQLUtil.executeDelete(changeFeatureIds,clzName+".deleteByPrimaryKeys");
//    }
//
//    @Override
//    public HzFeatureChangeBean selectLast(HzFeatureChangeBean hzFeatureChangeBean) {
//        return baseSQLUtil.executeQueryById(hzFeatureChangeBean,clzName+".selectLast");
//    }
//
//    @Override
//    public HzFeatureChangeBean selectByChangeIdAndCfgid(HzFeatureChangeBean hzFeatureChangeBean) {
//        return baseSQLUtil.executeQueryById(hzFeatureChangeBean,clzName+".selectByChangeIdAndCfgid");
//    }
//
//    @Override
//    public List<HzFeatureChangeBean> selectByChangeId(Long orderId) {
//        return baseSQLUtil.executeQueryByPass(new HzFeatureChangeBean(),orderId,clzName+".selectByChangeId");
//    }
//
//    @Override
//    public List<HzFeatureChangeBean> selectHasNotEffect(List<Long> changeFeatureIds) {
//        return baseSQLUtil.executeQueryByPass(new HzFeatureChangeBean(),changeFeatureIds,clzName+".selectHasNotEffect");
//    }
//
//
//}
