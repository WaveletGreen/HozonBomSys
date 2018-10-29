/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.model;

import com.connor.hozon.bom.bomSystem.dao.model.HzCfg0ModelRecordDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.model.HzCfg0ModelRecord;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzCfg0ModelRecordDao")
public class HzCfg0ModelRecordDaoImpl extends BasicDaoImpl<HzCfg0ModelRecord> implements HzCfg0ModelRecordDao {

    private final static HzCfg0ModelRecord RECORD = new HzCfg0ModelRecord();

    public HzCfg0ModelRecordDaoImpl() {
        clz = HzCfg0ModelRecordDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public HzCfg0ModelRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(RECORD, puid,
                clzName + ".selectByPrimaryKey",
                true);
    }

    @Override
    public int updateBasicByPuid(HzCfg0ModelRecord modelRecord) {
        return baseSQLUtil.executeUpdate(modelRecord,
                clzName + ".updateBasicByPuid");
    }

    @Override
    public int updateModelName(HzCfg0ModelRecord modelRecord) {
        return baseSQLUtil.executeUpdate(modelRecord,
                clzName + ".updateModelName");
    }

    @Override
    public int insertByBatch(List<HzCfg0ModelRecord> modelRecord) {
        return baseSQLUtil.executeInsert(modelRecord,
                clzName + ".insertByBatch");
    }

    /**
     * 该项目下的所有车型
     *
     * @param projectPuid
     * @return
     */
    @Override
    public List<HzCfg0ModelRecord> selectByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(RECORD, projectPuid,
                clzName + ".selectByProjectPuid");
    }

    @Override
    public int deleteModelById(String modelId) {
        return baseSQLUtil.executeDelete(modelId,
                clzName + ".deleteModelById");
    }
}
