/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgMain;

import java.math.BigDecimal;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public class HzFullCfgMainDaoImpl extends BasicDaoImpl<HzFullCfgMain> implements HzFullCfgMainDao {
    private static final HzFullCfgMain HZ_FULL_CFG_MAIN = new HzFullCfgMain();

    public HzFullCfgMainDaoImpl() {
        clz = HzFullCfgMainDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public int deleteByPrimaryKey(BigDecimal id) {
        HZ_FULL_CFG_MAIN.setId(id);
        return baseSQLUtil.executeDelete(HZ_FULL_CFG_MAIN, clzName + ".deleteByPrimaryKey");
    }

    @Override
    public BigDecimal insertBackId(HzFullCfgMain record) {
        baseSQLUtil.executeInsert(record, clzName + ".insertBackId");
        return record.getId();
    }

    @Override
    public HzFullCfgMain selectByPrimaryKey(BigDecimal id) {
        HZ_FULL_CFG_MAIN.setId(id);
        return baseSQLUtil.executeQueryById(HZ_FULL_CFG_MAIN, clzName + ".selectByPrimaryKey");
    }

    @Override
    public HzFullCfgMain selectByProjectId(String id) {
        HZ_FULL_CFG_MAIN.setProjectUid(id);
        return baseSQLUtil.executeQueryById(HZ_FULL_CFG_MAIN, clzName + ".selectByProjectId");
    }
}
