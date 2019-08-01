///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.cfg0;
//
//import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureDao;
//import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielDetail;
//import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeature;
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
////@Service("hzCfg0OptionFamilyDao")
//@Repository
//public class HzFeatureDaoImpl extends BasicDaoImpl<HzFeature> implements HzFeatureDao {
//    private final static HzFeature FAMILY = new HzFeature();
//
//    public HzFeatureDaoImpl() {
//        clz = HzFeatureDao.class;
//        clzName = clz.getCanonicalName();
//    }
//
//    @Override
//    public List<HzFeature> selectByProjectIdWithOrderMainId(String mainId) {
//        return baseSQLUtil.executeQueryByPass(FAMILY, mainId, clzName + ".selectByProjectIdWithOrderMainId");
//    }
//
//    /**
//     * Author: Fancyears·Maylos·Mayways
//     * Description: 根据产品配置器的puid获取到所有的配置系统层
//     * Date: 2018/5/23 9:49
//     *
//     * @param mainId 产品配置器的puid
//     * @return 返回一组系统名称
//     */
//    @Override
//    public List<HzFeature> selectByProjectIdWithOrderPuid(String mainId) {
//        return baseSQLUtil.executeQueryByPass(FAMILY, mainId, clzName + ".selectByProjectIdWithOrderPuid");
//    }
//
//    /**
//     * Author: Fancyears·Maylos·Mayways
//     * Description: 根据产品配置器的puid获取到所有的配置系统层
//     * Date: 2018/5/23 9:49
//     *
//     * @param param
//     * @return 返回一组系统名称
//     */
//    @Override
//    public List<HzFeature> selectNameByMap(Map<String, Object> param) {
//        return baseSQLUtil.executeQueryByPass(FAMILY, param, clzName + ".selectNameByMap");
//    }
//
//    /**
//     * Author: Fancyears·Maylos·Mayways
//     * Description: 根据产品配置器的puid获取到所有的配置系统层
//     * Date: 2018/5/23 9:49
//     *
//     * @param param
//     * @return 返回一组系统名称
//     */
//    @Override
//    public List<HzFeature> selectForColorBluePrint(Map<String, Object> param) {
//        return baseSQLUtil.executeQueryByPass(FAMILY, param, clzName + ".selectForColorBluePrint");
//    }
//
//
//    @Override
//    public HzFeature selectByCodeAndDescWithMain(HzFeature family) {
//        return baseSQLUtil.executeQueryById(family, clzName + ".selectByCodeAndDescWithMain");
//    }
//
//    /**
//     * @param family 包含主配置UID，描述和配置代码
//     * @return
//     */
//    @Override
//    public List<HzFeature> selectByCodeAndDescWithMain2(HzFeature family) {
//        return baseSQLUtil.executeQuery(family, clzName + ".selectByCodeAndDescWithMain");
//    }
//
//    @Override
//    public List<HzFeature> selectByDM(List<HzDerivativeMaterielDetail> hzDMDetailChangeBeans) {
//        return baseSQLUtil.executeQueryByPass(new HzFeature(),hzDMDetailChangeBeans,clzName + ".selectByDM");
//    }
//
//    @Override
//    public int deleteByFamilyName(Map<String,Object> params) {
//        return baseSQLUtil.executeDelete(params,clzName+".deleteByFamilyName");
//    }
//
//}
