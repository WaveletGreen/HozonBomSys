/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.derivative;

import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzDerivativeMaterielBasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.derivative.HzDerivativeMaterielBasic;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/30 17:00
 * @Modified By:
 */
@Configuration
public class HzDerivativeMaterielBasicDaoImpl extends BasicDaoImpl<HzDerivativeMaterielBasic> implements HzDerivativeMaterielBasicDao {

    private final static HzDerivativeMaterielBasic BASIC = new HzDerivativeMaterielBasic();

    public HzDerivativeMaterielBasicDaoImpl() {
        clz = HzDerivativeMaterielBasicDao.class;
    }

    /**
     * 根据项目查找项目下的所有配置物料特性数据
     *
     * @param params
     * @return
     */
    public List<HzDerivativeMaterielBasic> selectByProjectUid(Map<String, Object> params) {
        return baseSQLUtil.executeQueryByPass(BASIC, params, clz.getCanonicalName() + ".selectByProjectUid");
    }

    /**
     * 车型模型+配色模型组成唯一的配置物料特性数据
     *
     * @param modelUid   车型模型UID
     * @param colorModel 配色模型UID
     * @return
     */
    @Override
    public HzDerivativeMaterielBasic selectByModelAndColorUid(String modelUid, String colorModel) {
        BASIC.setDmbModelUid(modelUid);
        BASIC.setDmbColorModelUid(colorModel);
        return baseSQLUtil.executeQueryById(BASIC, clz.getCanonicalName() + ".selectByModelAndColorUid");
    }
}
