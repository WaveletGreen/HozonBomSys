/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzMaterielRecord;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in  2018/5/30 14:15
 * @Modified By:
 */
@Service("hzSuperMaterielService")
public class HzSuperMaterielService {
    private final HzSuperMaterielDao hzSuperMaterielDao;

    @Autowired
    public HzSuperMaterielService(HzSuperMaterielDao hzSuperMaterielDao) {
        this.hzSuperMaterielDao = hzSuperMaterielDao;
    }

    public boolean doDeleteByPrimaryKey(String puid) {
        return hzSuperMaterielDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    public boolean doInsertOne(HzMaterielRecord record)  {
        return hzSuperMaterielDao.insert(record) > 0 ? true : false;
    }

    public HzMaterielRecord doSelectByPrimaryKey(String puid) {
        return hzSuperMaterielDao.selectByPrimaryKey(puid);
    }

    public HzMaterielRecord doSelectByProjectPuid(String projectPuid) {
        return hzSuperMaterielDao.selectByProjectPuid(projectPuid);
    }

    public boolean doUpdateByPrimaryKey(HzMaterielRecord record)  {
        return hzSuperMaterielDao.updateByPrimaryKey(record) > 0 ? true : false;
    }
}
