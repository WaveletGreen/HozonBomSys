/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import cn.net.connor.hozon.dao.dao.change.vwo.HzVwoInformChangesDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoInformChanges;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Repository
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
    public Long count(Long vwoId) {
        return baseSQLUtil.executeQueryById(vwoId,
                clzName + ".count");
    }

}
