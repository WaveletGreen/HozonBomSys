/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.bom.HZBomMainRecord;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service("hzBomMainRecordDao")
@Configuration
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
