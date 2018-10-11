/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.derivative;

import com.connor.hozon.bom.bomSystem.dao.derivative.HzCfg0ModelGroupDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.derivative.HzCfg0ModelGroup;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/15 10:18
 * @Modified By:
 */
@Configuration
public class HzCfg0ModelGroupDaoImpl extends BasicDaoImpl<HzCfg0ModelGroup> implements HzCfg0ModelGroupDao {
    private static final HzCfg0ModelGroup GROUP = new HzCfg0ModelGroup();

    public HzCfg0ModelGroupDaoImpl() {
        clz = HzCfg0ModelGroupDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String id) {
        return baseSQLUtil.executeDeleteByPass(id, clzName+".deleteByPrimaryKey");
    }

    /**
     * 主键筛选
     *
     * @param id
     * @return
     */
    @Override
    public HzCfg0ModelGroup selectByPrimaryKey(String id) {
        return baseSQLUtil.executeQueryByPass(GROUP, id, clzName+".selectByPrimaryKey", true);
    }

    /**
     * 根据主配置寻找模型族
     *
     * @param mainUid
     * @return
     */
    @Override
    public HzCfg0ModelGroup selectByMainUid(String mainUid) {
        return baseSQLUtil.executeQueryByPass(GROUP, mainUid, clzName+".selectByMainUid", true);
    }


    /**
     * 根据主配置寻找族名
     *
     * @param mainUid
     * @return
     */
    public String selectGroupNameByMainUid(String mainUid) {
        String groupName = new String();
        return baseSQLUtil.executeQueryByPass(groupName, mainUid, clzName+".selectGroupNameByMainUid", true);
    }
}
