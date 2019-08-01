///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.main;
//
//import cn.net.connor.hozon.dao.dao.main.HzCfg0MainRecordDao;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import sql.IBaseSQLUtil;
//import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
//
///**
// * @Author: Fancyears·Maylos·Malvis
// * @Description: fuck
// * @Date: Created in 2018/9/6 13:19
// * @Modified By:
// */
////@Service("hzCfg0MainRecordDao")
//@Repository
//public class HzCfg0MainRecordDaoImpl extends BasicDaoImpl<HzMainConfig> implements HzCfg0MainRecordDao {
//    @Autowired
//    IBaseSQLUtil baseSQLUtil;
//
//    public HzCfg0MainRecordDaoImpl() {
//        clz = HzCfg0MainRecordDao.class;
//        clzName = clz.getCanonicalName();
//    }
//
//    @Override
//    public HzMainConfig selectByPrimaryKey(String puid) {
//        return baseSQLUtil.executeQueryByPass(new HzMainConfig(), puid, clzName + ".selectByPrimaryKey", true);
//    }
//
//    @Override
//    public HzMainConfig selectByProjectId(String projectPuid) {
//        return baseSQLUtil.executeQueryByPass(new HzMainConfig(), projectPuid, clzName + ".selectByProjectId", true);
//    }
//
//}
