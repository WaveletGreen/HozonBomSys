/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.bom.HZBomMainRecord;

/**
 * Author: Fancyears·Maylos·Mayways
 * Date: 2018/5/30 16:12
 * <p>
 * Description:
 */
@Service("hzBomMainRecordDao")
public class HzBomMainRecordDaoImpl extends BasicDaoImpl<HZBomMainRecord> implements HzBomMainRecordDao {
    private final static HZBomMainRecord RECORD = new HZBomMainRecord();

    public HzBomMainRecordDaoImpl() {
        clz = HzBomMainRecordDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public HZBomMainRecord selectByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(RECORD, projectPuid, clzName + ".selectByProjectPuid", true);
    }

}
