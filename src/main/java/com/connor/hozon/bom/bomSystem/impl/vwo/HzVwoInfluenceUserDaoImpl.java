/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoInfluenceUserDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.vwo.HzVwoInfluenceUser;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Repository
public class HzVwoInfluenceUserDaoImpl extends BasicDaoImpl<HzVwoInfluenceUser> implements HzVwoInfluenceUserDao {

    private static final HzVwoInfluenceUser USER = new HzVwoInfluenceUser();

    public HzVwoInfluenceUserDaoImpl() {
        clz = HzVwoInfluenceUserDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        USER.setId(id);
        return baseSQLUtil.executeDelete(USER,
                clzName + ".deleteByPrimaryKey");
    }


    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInfluenceUser selectByPrimaryKey(Long id) {
        USER.setId(id);
        return baseSQLUtil.executeQueryById(USER,
                clzName + ".selectByPrimaryKey");
    }

    /**
     * 根据VWO主键进行查询
     *
     * @param vwoId
     * @return
     */
    @Override
    public HzVwoInfluenceUser selectByVwoId(Long vwoId) {
        USER.setVwoId(vwoId);
        return baseSQLUtil.executeQueryById(USER,
                clzName + ".selectByVwoId");
    }
}
