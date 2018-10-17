/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.modelColor;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.modelColor.HzCfg0ModelColor;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 10:53
 */
@Service("hzCfg0ModelColorDao")
public class HzCfg0ModelColorDaoImpl extends BasicDaoImpl<HzCfg0ModelColor> implements HzCfg0ModelColorDao {

    private final static HzCfg0ModelColor COLOR = new HzCfg0ModelColor();

    public HzCfg0ModelColorDaoImpl() {
        clz = HzCfg0ModelColorDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public List<HzCfg0ModelColor> selectByMainId(HzCfg0ModelColor color) {
        return baseSQLUtil.executeQuery(color,
                clzName + ".selectByMainId");
    }


    @Override
    public List<HzCfg0ModelColor> selectAll(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(COLOR, projectPuid,
                clzName + ".selectAll");
    }

    /**
     * 更新旧数据，将旧数据的大对象设置为null
     *
     * @param color 旧数据
     * @return
     */
    @Override
    public int updateOldData(HzCfg0ModelColor color) {
        return baseSQLUtil.executeUpdate(color,
                clzName + ".updateOldData");
    }

    @Override
    public int deleteByBatch(List<HzCfg0ModelColor> colors) {
        return baseSQLUtil.executeDelete(colors,
                clzName + ".deleteByBatch");
    }

    @Override
    public List<HzCfg0ModelColor> selectByPuids(List<HzCfg0ModelColor> colors) {
        return baseSQLUtil.executeQueryByPass(COLOR, colors,
                clzName + ".selectByPuids");
    }

    @Override
    public int updateListData(List<HzCfg0ModelColor> hzCfg0ModelColors) {
        return baseSQLUtil.executeUpdate(hzCfg0ModelColors,
                clzName + ".updateListData");
    }

    @Override
    public int updateByVwoId(HzCfg0ModelColor hzCfg0ModelColor) {
        return baseSQLUtil.executeUpdate(hzCfg0ModelColor,clzName + ".updateByVwoId");
    }
}
