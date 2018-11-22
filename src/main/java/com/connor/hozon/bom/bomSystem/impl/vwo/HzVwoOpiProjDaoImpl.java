/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoOpiProjDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.vwo.HzVwoOpiProj;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service("hzVwoOpiProjDao")
@Configuration
public class HzVwoOpiProjDaoImpl extends BasicDaoImpl<HzVwoOpiProj> implements HzVwoOpiProjDao {
    private final static HzVwoOpiProj PROJ = new HzVwoOpiProj();

    public HzVwoOpiProjDaoImpl() {
        clz = HzVwoOpiProjDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public HzVwoOpiProj selectByVwoId(Long id) {
        PROJ.setOpiVwoId(id);
        return baseSQLUtil.executeQueryById(PROJ, clzName + ".selectByVwoId");
    }

    @Override
    public int updateUserByVwoId(HzVwoOpiProj hzVwoOpiProj) {
        return baseSQLUtil.executeUpdate(hzVwoOpiProj, clzName + ".updateUserByVwoId");
    }
}
