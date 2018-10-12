/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.project.HzMaterielRecord;

import java.util.List;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/9 13:56
 */
@Service("HzSuperMaterielDao")
public class HzSuperMaterielDaoImpl extends BasicDaoImpl<HzMaterielRecord> implements HzSuperMaterielDao {

    private final static HzMaterielRecord MATERIEL = new HzMaterielRecord();

    public HzSuperMaterielDaoImpl() {
        clz = HzSuperMaterielDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid,
                clzName + ".deleteByPrimaryKey");
    }


    @Override
    public HzMaterielRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(MATERIEL, puid,
                clzName + ".selectByPrimaryKey", true);
    }

    @Override
    public HzMaterielRecord selectByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(MATERIEL, projectPuid,
                clzName + ".selectByProjectPuid", true);
    }

}
