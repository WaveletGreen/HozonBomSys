/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.configuration.model.impl;

import cn.net.connor.hozon.dao.dao.configuration.model.HzCfg0ModelRecordDao;
import com.connor.hozon.service.configuration.model.HzCfg0ModelRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/5/22 14:00
 * @Modified By:
 */
@Service
public class HzCfg0ModelRecordServiceImpl implements HzCfg0ModelRecordService {
    @Autowired
    HzCfg0ModelRecordDao hzCfg0ModelRecordDao;

    @Override
    public HzCfg0ModelRecord doGetById(String puid) {
        return hzCfg0ModelRecordDao.selectByPrimaryKey(puid);
    }

    @Override
    public boolean updateBasic(HzCfg0ModelRecord modelRecord) {
        return hzCfg0ModelRecordDao.updateBasicByPuid(modelRecord) > 0 ? true : false;
    }

    @Override
    public boolean updateModelName(HzCfg0ModelRecord modelRecord) {
        return hzCfg0ModelRecordDao.updateModelName(modelRecord) > 0 ? true : false;
    }

    @Override
    public boolean insert(List<HzCfg0ModelRecord> modelRecord) {
        return hzCfg0ModelRecordDao.insertByBatch(modelRecord) > 0 ? true : false;
    }
    /**
     * 根据项目的ID，查找所有的车型
     *
     * @param projectUid 项目ID
     * @return 一组车型模型
     */
    @Override
    public List<HzCfg0ModelRecord> selectByProjectUid(String projectUid) {
        return hzCfg0ModelRecordDao.selectByProjectPuid(projectUid);
    }
    /**
     * 主键删除一个基本模型
     *
     * @param puid
     * @return
     */
    @Override
    public boolean deleteByUid(String puid) {
        return hzCfg0ModelRecordDao.deleteModelById(puid) > 0 ? true : false;
    }
}
