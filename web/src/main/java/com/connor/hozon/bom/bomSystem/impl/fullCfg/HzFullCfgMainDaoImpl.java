/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgMain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Repository
public class HzFullCfgMainDaoImpl extends BasicDaoImpl<HzFullCfgMain> implements HzFullCfgMainDao {
    private static final HzFullCfgMain HZ_FULL_CFG_MAIN = new HzFullCfgMain();

    public HzFullCfgMainDaoImpl() {
        clz = HzFullCfgMainDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        HZ_FULL_CFG_MAIN.setId(id);
        return baseSQLUtil.executeDelete(HZ_FULL_CFG_MAIN, clzName + ".deleteByPrimaryKey");
    }

    @Override
    public Long insertBackId(HzFullCfgMain record) {
        baseSQLUtil.executeInsert(record, clzName + ".insertBackId");
        return record.getId();
    }

    @Override
    public HzFullCfgMain selectByPrimaryKey(Long id) {
        HZ_FULL_CFG_MAIN.setId(id);
        return baseSQLUtil.executeQueryById(HZ_FULL_CFG_MAIN, clzName + ".selectByPrimaryKey");
    }

    @Override
    public HzFullCfgMain selectByProjectId(String id) {
        HZ_FULL_CFG_MAIN.setProjectUid(id);
        return baseSQLUtil.executeQueryById(HZ_FULL_CFG_MAIN, clzName + ".selectByProjectId");
    }

    @Override
    public int deleteByProjectUid(String projectUid) {
        return baseSQLUtil.executeDelete(projectUid,clzName+".deleteByProjectUid");
    }

    @Override
    public Long insertSeqAll(HzFullCfgMain hzFullCfgMain) {
         baseSQLUtil.executeInsert(hzFullCfgMain,clzName+".insertSeqAll");
        return hzFullCfgMain.getId();
    }

    @Override
    public int updateStatusByOrderId(Long orderId, Integer stutas) {
        Map<String,Object> map = new HashMap<>();
        map.put("stutas",stutas);
        map.put("orderId",orderId);
        map.put("effectiveDate",new Date());
        return baseSQLUtil.executeUpdate(map,clzName+".updateStatusByOrderId");
    }

    @Override
    public int updateStatusById(HzFullCfgMain hzFullCfgMain) {
        return baseSQLUtil.executeUpdate(hzFullCfgMain,clzName+".updateStatusById");
    }

    @Override
    public int updateChangeByOrderId(Long orderId) {
        return baseSQLUtil.executeUpdate(orderId,clzName+".updateChangeByOrderId");
    }
}
