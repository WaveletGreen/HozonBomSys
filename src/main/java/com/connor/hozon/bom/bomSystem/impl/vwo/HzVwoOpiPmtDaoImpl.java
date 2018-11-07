/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoOpiPmtDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.vwo.HzVwoOpiPmt;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service("hzVwoOpiPmtDao")
@Configuration
public class HzVwoOpiPmtDaoImpl extends BasicDaoImpl<HzVwoOpiPmt> implements HzVwoOpiPmtDao {
    private final static HzVwoOpiPmt PMT = new HzVwoOpiPmt();

    public HzVwoOpiPmtDaoImpl() {
        clz = HzVwoOpiPmtDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public HzVwoOpiPmt selectByVwoId(Long id) {
        PMT.setOpiVwoId(id);
        return baseSQLUtil.executeQueryById(PMT, clzName + ".selectByVwoId");
    }

    @Override
    public int updateUserByVwoId(HzVwoOpiPmt hzVwoOpiPmt) {
        return baseSQLUtil.executeUpdate(hzVwoOpiPmt, clzName + ".updateUserByVwoId");
    }
}
