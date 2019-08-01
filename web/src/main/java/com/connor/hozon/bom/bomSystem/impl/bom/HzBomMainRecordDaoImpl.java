///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.bom;
//
//import cn.net.connor.hozon.dao.dao.main.HzBomMainRecordDao;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import org.springframework.context.annotation.Configuration;
//import cn.net.connor.hozon.dao.pojo.main.HzMainBom;
//
///**
// * @Author: Fancyears·Maylos·Malvis
// * @Description: fuck
// * @Date: Created in 2018/9/6 13:19
// * @Modified By:
// */
////@Service("hzBomMainRecordDao")
//@Configuration
//public class HzBomMainRecordDaoImpl extends BasicDaoImpl<HzMainBom> implements HzBomMainRecordDao {
//    private final static HzMainBom RECORD = new HzMainBom();
//
//    public HzBomMainRecordDaoImpl() {
//        clz = HzBomMainRecordDao.class;
//        clzName = clz.getCanonicalName();
//    }
//
//    @Override
//    public HzMainBom selectByProjectId(String projectPuid) {
//        return baseSQLUtil.executeQueryByPass(RECORD, projectPuid, clzName + ".selectByProjectId", true);
//    }
//
//}
