///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.color;
//
//import cn.net.connor.hozon.dao.dao.depository.color.HzCfg0ColorSetDao;
//import cn.net.connor.hozon.dao.pojo.depository.color.HzColorSetQuery;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import cn.net.connor.hozon.common.entity.QueryBase;
//import org.springframework.stereotype.Repository;
//import cn.net.connor.hozon.dao.pojo.depository.color.HzCfg0ColorSet;
//
//import java.util.List;
//
///**
// * @Author: Fancyears·Maylos·Malvis
// * @Description: fuck
// * @Date: Created in 2018/9/6 13:19
// * @Modified By:
// */
////@Service("hzCfg0ColorSetDaox")
////@Configuration
//@Repository
//public class HzCfg0ColorSetDaoImpl extends BasicDaoImpl<HzCfg0ColorSet> implements HzCfg0ColorSetDao {
//
//    private final static HzCfg0ColorSet SET = new HzCfg0ColorSet();
//
//    public HzCfg0ColorSetDaoImpl() {
//        clz = HzCfg0ColorSetDao.class;
//        clzName = clz.getCanonicalName();
//    }
//
////    @Override
////    public List<HzCfg0ColorSet> selectAll(QueryBase queryBase) {
////        HzCfg0ColorSet set = new HzCfg0ColorSet();
////        queryBase.setSort(set.reflectToDBField(queryBase.getSort()));
////        List<HzCfg0ColorSet> colorSet = baseSQLUtil.executeQueryByPass(set, queryBase, clzName + ".selectAll");
////        return colorSet;
////    }
//
////    /**
////     * @return
////     * @Author: Fancyears·Maylos·Mayways
////     * @Description: 检索所有的颜色集
////     * @Date: 2018/5/21 17:09
////     */
////    @Override
////    public List<HzCfg0ColorSet> selectAll() {
////        List<HzCfg0ColorSet> colorSet = baseSQLUtil.executeQueryByPass(SET, new HzColorSetQuery(), clzName + ".selectAll");
////        return colorSet;
////    }
//
////    /**
////     * 根据颜色代码寻找颜色对象
////     *
////     * @param entity
////     * @return 找到的颜色对象
////     * @Author: Fancyears·Maylos·Mayways
////     * @Description: 根据颜色的id，找到该颜色对象
////     * @Date: 2018/5/21 17:09
////     */
////    @Override
////    public HzCfg0ColorSet selectByColorCode(HzCfg0ColorSet entity) {
////        return baseSQLUtil.executeQueryById(entity, clzName + ".selectByColorCode");
////    }
//
////    /**
////     * @param entity 更新的颜色对象的状态
////     * @return 影响行数
////     * @Author: Fancyears·Maylos·Mayways
////     * @Description: 执行更细颜色信息
////     * @Date: 2018/5/21 17:10
////     */
////    @Override
////    public int updateStatusByPrimaryKey(HzCfg0ColorSet entity) {
////        return baseSQLUtil.executeUpdate(entity, clzName + ".updateStatusByPrimaryKey");
////    }
//
////    @Override
////    public int deleteByBatch(List<HzCfg0ColorSet> entity) {
////        return baseSQLUtil.executeDelete(entity, clzName + ".deleteByBatch");
////    }
//
////    /**
////     * @param entity 传入的颜色集合
////     * @return
////     * @Author: Fancyears·Maylos·Mayways
////     * @Description: 批量逻辑删除颜色信息，设置颜色删除状态为0
////     * @Date: 2018/5/21 17:10
////     */
////    @Override
////    public int logicDeleteByBatch(List<HzCfg0ColorSet> entity) {
////        return baseSQLUtil.executeDelete(entity, clzName + ".logicDeleteByBatch");
////    }
//
////    /**
////     * 查询全部的颜色数量
////     *
////     * @return
////     */
////    @Override
////    public int tellMeHowManyOfIt(HzColorSetQuery hzColorSetQuery) {
////        List<Integer> count = baseSQLUtil.executeQueryByPass(new Integer(0), hzColorSetQuery, clzName + ".tellMeHowManyOfIt");
////        return count.get(0);
////    }
//}
