///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.project;
//
//import cn.net.connor.hozon.dao.dao.depository.project.HzSuperMaterielDao;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import org.springframework.stereotype.Repository;
//import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;
//
///**
// * @Author: Fancyears·Maylos·Malvis
// * @Description: fuck
// * @Date: Created in 2018/9/6 13:19
// * @Modified By:
// */
////@Service("HzSuperMaterielDao")
//@Repository
//public class HzSuperMaterielDaoImpl extends BasicDaoImpl<HzMaterielRecord> implements HzSuperMaterielDao {
//
//    private final static HzMaterielRecord MATERIEL = new HzMaterielRecord();
//
//    public HzSuperMaterielDaoImpl() {
//        clz = HzSuperMaterielDao.class;
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
//    public HzMaterielRecord selectByPrimaryKey(String puid) {
//        return baseSQLUtil.executeQueryByPass(MATERIEL, puid,
//                clzName + ".selectByPrimaryKey", true);
//    }
//
//    @Override
//    public HzMaterielRecord selectByProjectPuid(String projectPuid) {
//        return baseSQLUtil.executeQueryByPass(MATERIEL, projectPuid,
//                clzName + ".selectByProjectPuid", true);
//    }
//
//}
