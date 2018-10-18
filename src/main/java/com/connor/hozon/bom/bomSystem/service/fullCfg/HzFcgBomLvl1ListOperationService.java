/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFcfgBomLvl1ListOperationDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzFcfgBomLvl1ListOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.fullCfg.HzFcfgBomLvl1ListOperation;

@Service("hzFcgBomLvl1ListOperationService")
public class HzFcgBomLvl1ListOperationService implements IHzFcfgBomLvl1ListOperationService {

    @Autowired
    HzFcfgBomLvl1ListOperationDao hzFcfgBomLvl1ListOperationDao;

    @Override
    public boolean doDeleteByPrimaryKey(String puid) {
        return hzFcfgBomLvl1ListOperationDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    @Override
    public boolean doLogicDeleteByPrimaryKey(HzFcfgBomLvl1ListOperation record) {
        return hzFcfgBomLvl1ListOperationDao.logicDeleteByPrimaryKey(record) > 0 ? true : false;
    }

    @Override
    public boolean doInsert(HzFcfgBomLvl1ListOperation record) {
        return hzFcfgBomLvl1ListOperationDao.insert(record) > 0 ? true : false;
    }

    @Override
    public HzFcfgBomLvl1ListOperation doSelectByPrimaryKey(String puid) {
        return hzFcfgBomLvl1ListOperationDao.selectByPrimaryKey(puid);
    }

    @Override
    public boolean doUpdateByPrimaryKey(HzFcfgBomLvl1ListOperation record) {
        return hzFcfgBomLvl1ListOperationDao.updateByPrimaryKey(record) > 0 ? true : false;
    }
}
