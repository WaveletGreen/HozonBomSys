///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.derivative;
//
//import cn.net.connor.hozon.dao.dao.configuration.derivative.HzMaterielCfgBeanDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import sql.IBaseSQLUtil;
//import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzMaterielCfgBean;
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
//public class HzMaterielCfgBeanDaoImpl implements HzMaterielCfgBeanDao {
//    @Autowired
//    IBaseSQLUtil baseSQLUtil;
//    @Override
//    public List<HzMaterielCfgBean> selectByDiff(HzMaterielCfgBean bean) {
//        return baseSQLUtil.executeQuery(bean,"cn.net.connor.hozon.dao.dao.configuration.derivative.HzMaterielCfgBeanDao.selectByDiff");
//    }
//
//    @Override
//    public int updateIsSent(Map<String, Object> map) {
//        return baseSQLUtil.executeUpdate(map , "cn.net.connor.hozon.dao.dao.configuration.derivative.HzMaterielCfgBeanDao.updateIsSent");
//    }
//
//}
