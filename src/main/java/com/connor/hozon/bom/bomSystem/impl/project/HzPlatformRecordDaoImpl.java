/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzPlatformRecordDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.project.HzPlatformRecord;

import java.util.List;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/6/1 11:17
 *
 * Description:
 *
 * ***********************************************************************************************************************/
@Service("hzPlatformRecordDao")
public class HzPlatformRecordDaoImpl extends BasicDaoImpl<HzPlatformRecord> implements HzPlatformRecordDao {

    private final static HzPlatformRecord PLATFORM = new HzPlatformRecord();

    public HzPlatformRecordDaoImpl() {
        clz = HzPlatformRecordDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid,
                clzName + ".deleteByPrimaryKey");
    }


    @Override
    public HzPlatformRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(PLATFORM, puid,
                clzName + ".selectByPrimaryKey", true);
    }

    @Override
    public List<HzPlatformRecord> selectAll() {
        return baseSQLUtil.executeQuery(PLATFORM,
                clzName + ".selectAll");
    }

    @Override
    public HzPlatformRecord selectByPlatformCode(String platformCode) {
        return baseSQLUtil.executeQueryByPass(PLATFORM, platformCode,
                clzName + ".selectByPlatformCode", true);
    }
}
