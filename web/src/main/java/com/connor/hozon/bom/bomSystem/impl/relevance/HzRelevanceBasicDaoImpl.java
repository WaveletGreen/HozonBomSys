///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.relevance;
//
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import cn.net.connor.hozon.dao.dao.configuration.relevance.HzRelevanceBasicDao;
//import cn.net.connor.hozon.dao.query.configuration.relevance.HzRelevanceQuery;
//import cn.net.connor.hozon.dao.query.configuration.relevance.HzRelevanceQueryResult;
//import org.springframework.stereotype.Repository;
//import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasic;
//import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasicChange;
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
////@Service
//@Repository
//public class HzRelevanceBasicDaoImpl extends BasicDaoImpl<HzRelevanceBasic> implements HzRelevanceBasicDao {
//    private final static HzRelevanceBasic BASIC = new HzRelevanceBasic();
//    private final static HzRelevanceQueryResult BEAN = new HzRelevanceQueryResult();
//
//    public HzRelevanceBasicDaoImpl() {
//        clz = HzRelevanceBasicDao.class;
//    }
//
//    /**
//     * 删除项目下的全部相关性
//     *
//     * @param rbProjectUid
//     * @return
////     */
////    @Override
////    public int deleteByProjectUid(String rbProjectUid) {
////        return baseSQLUtil.executeDeleteByPass(rbProjectUid, clz.getCanonicalName() + ".deleteByProjectUid");
////    }
////
////    /**
////     * 批量插入
////     *
////     * @param list
////     * @return
////     */
////    @Override
////    public int insertByBatch(List<HzRelevanceBasic> list) {
////        return baseSQLUtil.executeInsert(list, clz.getCanonicalName() + ".insertByBatch");
////    }
//
////    @Override
////    public Long insertBasic(HzRelevanceBasic hzRelevanceBasic) {
////        baseSQLUtil.executeInsert(hzRelevanceBasic, clz.getCanonicalName() + ".insertBasic");
////        return hzRelevanceBasic.getId();
////    }
//
//    /**
//     * 分页查询
//     *
//     * @param dto
//     * @return
//     */
////    @Override
////    public List<HzRelevanceQueryResult> selectByPage(HzRelevanceQuery dto) {
////        return baseSQLUtil.executeQueryByPass(BEAN, dto, clz.getCanonicalName() + ".selectByPage");
////    }
//
//    /**
//     * 获取当前项目下的相关性总数
//     *
//     * @param dto
//     * @return
//     */
////    @Override
////    public Integer count(HzRelevanceQuery dto) {
////        List<Integer> result = baseSQLUtil.executeQueryByPass(new Integer(0), dto, clz.getCanonicalName() + ".count");
////        if (result != null && result.size() > 0) {
////            return result.get(0);
////        } else {
////            return 0;
////        }
////    }
//
////    @Override
////    public List<HzRelevanceBasic> selectByProjectId(String projectPuid) {
////        HzRelevanceBasic hzRelevanceBasic = new HzRelevanceBasic();
////        hzRelevanceBasic.setRbProjectUid(projectPuid);
////        return baseSQLUtil.executeQuery(hzRelevanceBasic,clz.getCanonicalName()+".selectByProjectId");
////    }
//
////    @Override
////    public int updateStatus(HzRelevanceBasic hzRelevanceBasicUpdate) {
////        return baseSQLUtil.executeUpdate(hzRelevanceBasicUpdate,clz.getCanonicalName()+".updateStatus");
////    }
////
////    @Override
////    public int updateStatusByOrderChangeId(HzRelevanceBasic hzRelevanceBasic) {
////        return baseSQLUtil.executeUpdate(hzRelevanceBasic,clz.getCanonicalName()+".updateStatusByOrderChangeId");
////    }
//
////    @Override
////    public List<HzRelevanceBasic> selectByChange(List<HzRelevanceBasicChange> hzRelevanceBasicChangesAdd) {
////        return baseSQLUtil.executeQueryByPass(new HzRelevanceBasic(),hzRelevanceBasicChangesAdd,clz.getCanonicalName()+".selectByChange");
////    }
//
////    @Override
////    public int deleteByPrimaryKeyList(List<HzRelevanceBasic> hzRelevanceBasicsDelete) {
////        return baseSQLUtil.executeDelete(hzRelevanceBasicsDelete,clz.getCanonicalName()+".deleteByPrimaryKeyList");
////    }
////
////    @Override
////    public int updateStatusList(List<HzRelevanceBasic> hzRelevanceBasicsUpdate) {
////        return baseSQLUtil.executeUpdate(hzRelevanceBasicsUpdate,clz.getCanonicalName()+".updateStatusList");
////    }
//
////    @Override
////    public int updateStatusByChange(List<HzRelevanceBasicChange> hzRelevanceBasicChanges) {
////        return baseSQLUtil.executeUpdate(hzRelevanceBasicChanges,clz.getCanonicalName()+".updateStatusByChange");
////    }
////
////    @Override
////    public int doUpdateIsSent(Map<String, Object> map) {
////        return baseSQLUtil.executeUpdate(map,clz.getCanonicalName()+".doUpdateIsSent");
////    }
////
////    @Override
////    public List<HzRelevanceBasic> selectByProjectPuidAndStatus(String projectPuid) {
////        return baseSQLUtil.executeQueryByPass(new HzRelevanceBasic(),projectPuid,clz.getCanonicalName()+".selectByProjectPuidAndStatus");
////    }
////
////    @Override
////    public int deleteByOrderId(Long orderId) {
////        return baseSQLUtil.executeDelete(orderId,clz.getCanonicalName()+".deleteByOrderId");
////    }
//
//}
