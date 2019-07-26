/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.modelColor;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.modelColor.HzCfg0ModelColor;
import sql.pojo.cfg.modelColor.HzCmcrChange;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service("hzCfg0ModelColorDao")
@Repository
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

    @Override
    public int updateStatus(List<HzCfg0ModelColor> hzCfg0ModelColorsUpdate) {
        return baseSQLUtil.executeUpdate(hzCfg0ModelColorsUpdate,clzName+".updateStatus");
    }

    @Override
    public int updateListAll(List<HzCfg0ModelColor> hzCfg0ModelColorsUpdate) {
        return baseSQLUtil.executeUpdate(hzCfg0ModelColorsUpdate,clzName+".updateListAll");
    }

    @Override
    public int updateStatusByOrderId(Long orderId, int status) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("status",status);
        map.put("effectedDate",new Date());
        return baseSQLUtil.executeUpdate(map,clzName+".updateStatusByOrderId");
    }

    @Override
    public int updateByChangeIds(List<HzCmcrChange> hzCmcrChanges) {
        return baseSQLUtil.executeUpdate(hzCmcrChanges,clzName+".updateByChangeIds");
    }

    @Override
    public int deleteByOrderId(Long orderId) {
        return baseSQLUtil.executeDelete(orderId,clzName+".deleteByOrderId");
    }
}
