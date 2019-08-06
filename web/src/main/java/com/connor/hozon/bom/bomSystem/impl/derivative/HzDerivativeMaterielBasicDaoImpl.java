///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.derivative;
//
//import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzComposeDelDto;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import cn.net.connor.hozon.dao.dao.configuration.derivative.HzDerivativeMaterielBasicDao;
//import org.springframework.stereotype.Repository;
//import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDMBasicChangeBean;
//import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasic;
//
//import java.util.Date;
//import java.util.HashMap;
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
//public class HzDerivativeMaterielBasicDaoImpl extends BasicDaoImpl<HzDerivativeMaterielBasic> implements HzDerivativeMaterielBasicDao {
//
//    private final static HzDerivativeMaterielBasic BASIC = new HzDerivativeMaterielBasic();
//
//    public HzDerivativeMaterielBasicDaoImpl() {
//        clz = HzDerivativeMaterielBasicDao.class;
//    }
//
//    /**
//     * 根据项目查找项目下的所有配置物料特性数据
//     *
//     * @param params
//     * @return
//     */
//    public List<HzDerivativeMaterielBasic> selectByProjectUid(Map<String, Object> params) {
//        return baseSQLUtil.executeQueryByPass(BASIC, params, clz.getCanonicalName() + ".selectByProjectUid");
//    }
//
//    /**
//     * 车型模型+配色模型组成唯一的配置物料特性数据
//     *
//     * @param modelUid   车型模型UID
//     * @param colorModel 配色模型UID
//     * @return
//     */
//    @Override
//    public HzDerivativeMaterielBasic selectByModelAndColorUid(String modelUid, String colorModel) {
//        BASIC.setDmbModelUid(modelUid);
//        BASIC.setDmbColorModelUid(colorModel);
//        return baseSQLUtil.executeQueryById(BASIC, clz.getCanonicalName() + ".selectByModelAndColorUid");
//    }
//
//    @Override
//    public List<HzDerivativeMaterielBasic> selectByPuids(List<String> puids) {
//        return baseSQLUtil.executeQueryByPass(new HzDerivativeMaterielBasic(), puids, clz.getCanonicalName() +".selectByPuids");
//    }
//
//    @Override
//    public int updateByBasicList(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics) {
//        return baseSQLUtil.executeUpdate(hzDerivativeMaterielBasics, clz.getCanonicalName()+".updateByBasicList");
//    }
//
//    @Override
//    public int updateByBasicListChangId(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics) {
//        return baseSQLUtil.executeUpdate(hzDerivativeMaterielBasics, clz.getCanonicalName()+".updateByBasicListChangId");
//    }
//
//    @Override
//    public int updateStatus(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics) {
//        return baseSQLUtil.executeUpdate(hzDerivativeMaterielBasics,clz.getCanonicalName()+".updateStatus");
//    }
//
//    @Override
//    public int updateByBasicAll(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasicsUpdate) {
//        return baseSQLUtil.executeUpdate(hzDerivativeMaterielBasicsUpdate,clz.getCanonicalName()+".updateByBasicAll");
//    }
//
//    @Override
//    public int deleteByIds(List<HzComposeDelDto> delDtos) {
//        return baseSQLUtil.executeDelete(delDtos,clz.getCanonicalName()+".deleteByIds");
//    }
//
//    @Override
//    public int updateStatusByOrderId(Long orderId, int status) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("orderId",orderId);
//        map.put("status",status);
//        map.put("effectedDate",new Date());
//        return baseSQLUtil.executeUpdate(map,clz.getCanonicalName()+".updateStatusByOrderId");
//    }
//
//    @Override
//    public int updateByChangeIds(List<HzDMBasicChangeBean> hzDMBasicChangeBeans) {
//        return baseSQLUtil.executeUpdate(hzDMBasicChangeBeans,clz.getCanonicalName()+".updateByChangeIds");
//    }
//
//    @Override
//    public List<HzDerivativeMaterielBasic> selectByChangeOrderId(Long orderId) {
//        return baseSQLUtil.executeQueryByPass(new HzDerivativeMaterielBasic(),orderId, clz.getCanonicalName()+".selectByChangeOrderId");
//    }
//
//    @Override
//    public int deleteByOrderId(Long orderId) {
//        return baseSQLUtil.executeDelete(orderId,clz.getCanonicalName()+".deleteByOrderId");
//    }
//
//    @Override
//    public int updateStatusUpdate(HzDerivativeMaterielBasic hzDerivativeMaterielBasics) {
//        return baseSQLUtil.executeUpdate(hzDerivativeMaterielBasics,clz.getCanonicalName()+".updateStatusUpdate");
//    }
//}
