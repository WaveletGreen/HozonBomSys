/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzCfg0BomLineOfModelDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzCfg0BomLineOfModel;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service("hzCfg0BomLineOfModelDao")
@Repository
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
