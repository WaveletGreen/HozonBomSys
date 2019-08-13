///*
// * Copyright (c) 2018.
// * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
// * ALL RIGHTS RESERVED.
// */
//
//package com.connor.hozon.bom.bomSystem.impl.bom;
//
//import cn.net.connor.hozon.dao.query.configuration.fullConfigSheet.FeatureAnd2YRelationQuery;
//import cn.net.connor.hozon.dao.dao.bom.bom.HzBomDataDao;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import org.springframework.stereotype.Repository;
//import cn.net.connor.hozon.dao.pojo.bom.bom.HzBomLineRecord;
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
////@Service("hzBomDataDao2")
//@Repository
//public class HzBomDataDaoImpl extends BasicDaoImpl<HzBomLineRecord> implements HzBomDataDao {
//    private static final HzBomLineRecord RECORD = new HzBomLineRecord();
//
//    public HzBomDataDaoImpl() {
//        clz = HzBomDataDao.class;
//        clzName = clz.getCanonicalName();
//    }
//
//    @Override
//    public List<HzBomLineRecord> selectByProjectId(HzBomLineRecord bomLineRecord) {
//        return baseSQLUtil.executeQuery(bomLineRecord, "cn.net.connor.hozon.dao.dao.bom.bom.HzBomDataDao.selectByProjectId");
//    }
//
//    /**
//     * Author: Fancyears·Maylos·Mayways
//     * Description: 根据数模层puid获取到所有的BomLine
//     * Date: 2018/5/23 9:59
//     *
//     * @param query
//     * @return
//     */
//    @Override
//    public List<HzBomLineRecord> select2YByProjectPuid(FeatureAnd2YRelationQuery query) {
//        return baseSQLUtil.executeQueryByPass(new HzBomLineRecord(), query, "cn.net.connor.hozon.dao.dao.bom.bom.HzBomDataDao.select2YByProjectPuid");
//    }
//
//    /**
//     * Author: Fancyears·Maylos·Mayways
//     * Description: 根据部门和2Y层获取到2Y层的下级总成
//     * Date: 2018/5/23 9:59
//     *
//     * @param params 包含部门名称和项目UID
//     * @return
//     */
//    @Override
//    public List<HzBomLineRecord> selectVehicleAssembly(Map<String, Object> params) {
//        return baseSQLUtil.executeQueryByPass(RECORD, params, "cn.net.connor.hozon.dao.dao.bom.bom.HzBomDataDao.selectVehicleAssembly");
//    }
//
//    /**
//     * 查找已经存储好的2级配色方案
//     *
//     * @param params 包含modelUid
//     * @return
//     */
//    @Override
//    public List<HzBomLineRecord> selectVehicleAssembly2(Map<String, Object> params) {
//        return baseSQLUtil.executeQueryByPass(RECORD, params, "cn.net.connor.hozon.dao.dao.bom.bom.HzBomDataDao.selectVehicleAssembly2");
//    }
//
//}
