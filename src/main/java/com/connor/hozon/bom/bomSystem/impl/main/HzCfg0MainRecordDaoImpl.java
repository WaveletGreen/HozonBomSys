/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.main;

import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.main.HzCfg0MainRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.main.HzCfg0MainRecord;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/5/31 13:41
 *
 * Description:
 *
 * ***********************************************************************************************************************/
@Service("hzCfg0MainRecordDao")
public class HzCfg0MainRecordDaoImpl extends BasicDaoImpl<HzCfg0MainRecord> implements HzCfg0MainRecordDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    public HzCfg0MainRecordDaoImpl() {
        clz = HzCfg0MainRecordDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public HzCfg0MainRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0MainRecord(), puid, clzName + ".selectByPrimaryKey", true);
    }

    @Override
    public HzCfg0MainRecord selectByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0MainRecord(), projectPuid, clzName + ".selectByProjectPuid", true);
    }

}
