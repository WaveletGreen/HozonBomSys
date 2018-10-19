/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzProjectLibsDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzProjectLibs;

import java.util.List;

/**
 * Author: Fancyears·Maylos·Mayways
 * Date: 2018/5/30
 * Time: 14:10
 * Description:
 */
@Service("hzProjectLibsDao")
public class HzProjectLibsDaoImpl extends BasicDaoImpl<HzProjectLibs> implements HzProjectLibsDao {
    private final static HzProjectLibs PROJECT = new HzProjectLibs();

    public HzProjectLibsDaoImpl() {
        clz = HzProjectLibsDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据项目的puid找到该项目信息
     * Date: 2018/5/30 14:13
     *
     * @param puid 项目的puid
     * @return 找到则返回项目对象，否则返回null
     */
    @Override
    public HzProjectLibs selectByPrimaryKey(String puid) {
        HzProjectLibs libs = new HzProjectLibs();
        libs.setPuid(puid);
        return baseSQLUtil.executeQueryById(libs,
                clzName + ".selectByPrimaryKey");
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description:
     * Date: 2018/5/30 14:14
     *
     * @return 找到所有的项目信息
     */
    @Override
    public List<HzProjectLibs> selectAllProject() {
        return baseSQLUtil.executeQuery(PROJECT,
                clzName + ".selectAllProject");
    }

    @Override
    public HzProjectLibs selectByProjectCode(String pProjectCode) {
        return baseSQLUtil.executeQueryByPass(PROJECT, pProjectCode,
                clzName + ".selectByProjectCode", true);
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid,
                clzName + ".deleteByPrimaryKey");
    }
}
