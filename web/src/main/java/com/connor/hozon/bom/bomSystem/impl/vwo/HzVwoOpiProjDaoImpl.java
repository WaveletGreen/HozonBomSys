/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import cn.net.connor.hozon.dao.dao.change.vwo.HzVwoOpiProjDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoOpiProj;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service("hzVwoOpiProjDao")
@Repository
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
