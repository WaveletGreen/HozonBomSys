/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import cn.net.connor.hozon.dao.dao.change.vwo.HzVwoOpiBomDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoOpiBom;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service("hzVwoOpiBomDao")
@Repository
public class HzVwoOpiBomDaoImpl extends BasicDaoImpl<HzVwoOpiBom> implements HzVwoOpiBomDao {
    private final static HzVwoOpiBom BOM = new HzVwoOpiBom();

    public HzVwoOpiBomDaoImpl() {
        clz = HzVwoOpiBomDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public HzVwoOpiBom selectByVwoId(Long id) {
        BOM.setOpiVwoId(id);
        return baseSQLUtil.executeQueryById(BOM, clzName + ".selectByVwoId");
    }

    @Override
    public int updateUserByVwoId(HzVwoOpiBom hzVwoOpiBom) {
        return baseSQLUtil.executeUpdate(hzVwoOpiBom, clzName + ".updateUserByVwoId");
    }
}
