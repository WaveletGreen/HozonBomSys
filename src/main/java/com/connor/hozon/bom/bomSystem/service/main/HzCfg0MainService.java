/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.main;

import com.connor.hozon.bom.bomSystem.dao.main.HzCfg0MainRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.main.HzCfg0MainRecord;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/5/31 14:00
 * @Modified By:
 */
@Service("hzCfg0MainService")
public class HzCfg0MainService {
    @Autowired
    HzCfg0MainRecordDao hzCfg0MainRecordDao;

    public HzCfg0MainRecord doGetbyProjectPuid(String projectPuid) {
        return hzCfg0MainRecordDao.selectByProjectPuid(projectPuid);
    }

    public HzCfg0MainRecord doGetByPrimaryKey(String puid) {
        return hzCfg0MainRecordDao.selectByPrimaryKey(puid);
    }

    /**
     * 选择更新
     *
     * @param mainRecord
     * @return
     */
    public boolean doUpdateByPrimaryKeySelective(HzCfg0MainRecord mainRecord) {
        return hzCfg0MainRecordDao.updateByPrimaryKeySelective(mainRecord) > 0 ? true : false;
    }

}
