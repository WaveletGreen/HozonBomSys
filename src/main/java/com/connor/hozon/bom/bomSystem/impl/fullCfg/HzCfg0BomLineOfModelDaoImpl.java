/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzCfg0BomLineOfModelDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.fullCfg.HzCfg0BomLineOfModel;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 18:11
 */
@Service("hzCfg0BomLineOfModelDao")
public class HzCfg0BomLineOfModelDaoImpl extends BasicDaoImpl<HzCfg0BomLineOfModel> implements HzCfg0BomLineOfModelDao {

    private final static HzCfg0BomLineOfModel BOM_LINE_OF_MODEL = new HzCfg0BomLineOfModel();

    public HzCfg0BomLineOfModelDaoImpl() {
        clz = HzCfg0BomLineOfModelDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public List<HzCfg0BomLineOfModel> selectByModelMainId(String modelId) {
        return baseSQLUtil.executeQueryByPass(BOM_LINE_OF_MODEL, modelId, clzName + ".selectByModelMainId");
    }
}
