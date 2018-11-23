/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzVehicleRecordDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import sql.IBaseSQLUtil;
import sql.pojo.project.HzVehicleRecord;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Repository
public class HzVehicleRecordDaoImpl extends BasicDaoImpl<HzVehicleRecord> implements HzVehicleRecordDao {
    private final static HzVehicleRecord VEHICLE = new HzVehicleRecord();

    public HzVehicleRecordDaoImpl() {
        clz = HzVehicleRecordDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid,
                clzName + ".deleteByPrimaryKey");
    }


    @Override
    public HzVehicleRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(VEHICLE, puid,
                clzName + ".selectByPrimaryKey", true);
    }


    @Override
    public List<HzVehicleRecord> selectAll() {
        return baseSQLUtil.executeQuery(VEHICLE,
                clzName + ".selectAll");
    }

    @Override
    public HzVehicleRecord selectByCode(String pVehicleCode) {
        return baseSQLUtil.executeQueryByPass(VEHICLE, pVehicleCode,
                clzName + ".selectByCode", true);
    }
}
