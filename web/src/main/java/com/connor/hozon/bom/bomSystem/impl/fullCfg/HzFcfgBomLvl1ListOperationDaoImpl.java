/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFcfgBomLvl1ListOperationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.fullCfg.HzFcfgBomLvl1ListOperation;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Deprecated
//@Configuration
@Repository
public class HzFcfgBomLvl1ListOperationDaoImpl implements HzFcfgBomLvl1ListOperationDao {
    private final IBaseSQLUtil baseSQLUtil;

    @Autowired
    public HzFcfgBomLvl1ListOperationDaoImpl(IBaseSQLUtil baseSQLUtil) {
        this.baseSQLUtil = baseSQLUtil;
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFcfgBomLvl1ListOperationDao.deleteByPrimaryKey");
    }

    @Override
    public int logicDeleteByPrimaryKey(HzFcfgBomLvl1ListOperation record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFcfgBomLvl1ListOperationDao.logicDeleteByPrimaryKey");
    }

    @Override
    public int insert(HzFcfgBomLvl1ListOperation record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFcfgBomLvl1ListOperationDao.insert");
    }

    @Override
    public HzFcfgBomLvl1ListOperation selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzFcfgBomLvl1ListOperation(), puid, "com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFcfgBomLvl1ListOperationDao.selectByPrimaryKey", true);
    }

    @Override
    public int updateByPrimaryKey(HzFcfgBomLvl1ListOperation record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFcfgBomLvl1ListOperationDao.updateByPrimaryKey");
    }
}
