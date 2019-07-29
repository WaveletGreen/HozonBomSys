///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.project;
//
//import cn.net.connor.hozon.dao.dao.depository.project.HzPlatformRecordDao;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import org.springframework.stereotype.Repository;
//import cn.net.connor.hozon.dao.pojo.depository.project.HzPlatformRecord;
//
//import java.util.List;
//
///**
// * @Author: Fancyears·Maylos·Malvis
// * @Description: fuck
// * @Date: Created in 2018/9/6 13:19
// * @Modified By:
// */
////@Service("hzPlatformRecordDao")
//@Repository
//public class HzPlatformRecordDaoImpl extends BasicDaoImpl<HzPlatformRecord> implements HzPlatformRecordDao {
//
//    private final static HzPlatformRecord PLATFORM = new HzPlatformRecord();
//
//    public HzPlatformRecordDaoImpl() {
//        clz = HzPlatformRecordDao.class;
//        clzName = clz.getCanonicalName();
//    }
//
//    @Override
//    public int deleteByPrimaryKey(String puid) {
//        return baseSQLUtil.executeDeleteByPass(puid,
//                clzName + ".deleteByPrimaryKey");
//    }
//
//
//    @Override
//    public HzPlatformRecord selectByPrimaryKey(String puid) {
//        return baseSQLUtil.executeQueryByPass(PLATFORM, puid,
//                clzName + ".selectByPrimaryKey", true);
//    }
//
//    @Override
//    public List<HzPlatformRecord> selectAll() {
//        return baseSQLUtil.executeQuery(PLATFORM,
//                clzName + ".selectAll");
//    }
//
//    @Override
//    public HzPlatformRecord selectByPlatformCode(String platformCode) {
//        return baseSQLUtil.executeQueryByPass(PLATFORM, platformCode,
//                clzName + ".selectByPlatformCode", true);
//    }
//}
