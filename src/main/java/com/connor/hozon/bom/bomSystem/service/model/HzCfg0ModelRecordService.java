/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.model;

import com.connor.hozon.bom.bomSystem.dao.model.HzCfg0ModelRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.model.HzCfg0ModelRecord;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/5/22 14:00
 * @Modified By:
 */
@Service("hzCfg0modelRecordService")
public class HzCfg0ModelRecordService {
    @Autowired
    HzCfg0ModelRecordDao hzCfg0ModelRecordDao;

    public HzCfg0ModelRecord doGetById(String puid) {
        return hzCfg0ModelRecordDao.selectByPrimaryKey(puid);
    }

    public boolean doUpdateBasic(HzCfg0ModelRecord modelRecord) {
        return hzCfg0ModelRecordDao.updateBasicByPuid(modelRecord) > 0 ? true : false;
    }

    public boolean doUpdateModelName(HzCfg0ModelRecord modelRecord) {
        return hzCfg0ModelRecordDao.updateModelName(modelRecord) > 0 ? true : false;
    }


    public boolean doInsert(List<HzCfg0ModelRecord> modelRecord) {
        return hzCfg0ModelRecordDao.insertByBatch(modelRecord) > 0 ? true : false;
    }

    /**
     * 根据项目的ID，查找所有的车型
     *
     * @param projectUid 项目ID
     * @return 一组车型模型
     */
    public List<HzCfg0ModelRecord> doSelectByProjectUid(String projectUid) {
        return hzCfg0ModelRecordDao.selectByProjectPuid(projectUid);
    }

    /**
     * 主键删除一个基本模型
     *
     * @param puid
     * @return
     */
    public boolean doDeleteByUid(String puid) {
        return hzCfg0ModelRecordDao.deleteModelById(puid) > 0 ? true : false;
    }
}
