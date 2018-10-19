/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoOpiProjDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.vwo.HzVwoOpiProj;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/17 17:55
 * @Modified By:
 */
@Service("hzVwoOpiProjDao")
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
}
