/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoInformChangesDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.vwo.HzVwoInformChanges;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public class HzVwoInformChangesDaoImpl extends BasicDaoImpl<HzVwoInformChanges> implements HzVwoInformChangesDao {
    private static final HzVwoInformChanges CHANGES = new HzVwoInformChanges();

    public HzVwoInformChangesDaoImpl() {
        clz = HzVwoInformChangesDao.class;
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
        CHANGES.setId(id);
        return baseSQLUtil.executeDelete(CHANGES,
                clzName + ".deleteByPrimaryKey");
    }

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInformChanges selectByPrimaryKey(Long id) {
        CHANGES.setId(id);
        return baseSQLUtil.executeQueryById(CHANGES,
                clzName + ".selectByPrimaryKey");
    }

    /**
     * 根据VWO主键查询所有关联的人员
     *
     * @param vwoId
     * @return
     */
    @Override
    public List<HzVwoInformChanges> selectByVwoId(Long vwoId) {
        CHANGES.setVwoId(vwoId);
        return baseSQLUtil.executeQuery(CHANGES,
                clzName + ".selectByVwoId");
    }

    /**
     * 获取VWO号下的总关联数
     *
     * @param vwoId
     * @return
     */
    @Override
    public Long tellMeHowManyOfIt(Long vwoId) {
        return baseSQLUtil.executeQueryById(vwoId,
                clzName + ".tellMeHowManyOfIt");
    }

}
