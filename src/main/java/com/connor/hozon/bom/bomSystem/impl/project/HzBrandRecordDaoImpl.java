/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.project.HzBrandRecord;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzBrandRecordDao")
public class HzBrandRecordDaoImpl extends BasicDaoImpl<HzBrandRecord> implements HzBrandRecordDao {
    private final static HzBrandRecord BRAND = new HzBrandRecord();

    public HzBrandRecordDaoImpl() {
        clz = HzBrandRecordDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid,
                clzName + ".deleteByPrimaryKey");
    }

    @Override
    public HzBrandRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(BRAND, puid,
                clzName + ".selectByPrimaryKey", true);
    }

    @Override
    public HzBrandRecord selectByBrandCode(String pBrandCode) {
        return baseSQLUtil.executeQueryByPass(BRAND, pBrandCode,
                clzName + ".selectByBrandCode", true);
    }

    @Override
    public List<HzBrandRecord> selectAll() {
        return baseSQLUtil.executeQuery(BRAND,
                clzName + ".selectAll");
    }

}
