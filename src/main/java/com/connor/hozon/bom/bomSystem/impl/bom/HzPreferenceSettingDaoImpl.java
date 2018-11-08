/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import sql.pojo.HzPreferenceSetting;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Deprecated
@Configuration
public class HzPreferenceSettingDaoImpl extends BasicDaoImpl<HzPreferenceSetting> implements HzPreferenceSettingDao {

    private final static HzPreferenceSetting SETTING = new HzPreferenceSetting();

    public HzPreferenceSettingDaoImpl() {
        clz = HzPreferenceSettingDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 根据主键删除1个首选项
     *
     * @param puid
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, clzName + ".deleteByPrimaryKey");
    }

    /**
     * 根据主键筛选首选项
     *
     * @param puid
     * @return
     */
    @Override
    public HzPreferenceSetting selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(SETTING, puid, clzName + ".selectByPrimaryKey", true);
    }

    /**
     * 根据首选项名个主数据查找首选项
     *
     * @param puid
     * @return
     */
    @Override
    public HzPreferenceSetting selectSettingByNameWithMainRecord(HzPreferenceSetting puid) {
        return baseSQLUtil.executeQueryById(puid, clzName + ".selectSettingByNameWithMainRecord");
    }


    @Override
    public List<HzPreferenceSetting> selectSettingByTemplateName(HzPreferenceSetting setting) {
        return baseSQLUtil.executeQuery(setting, clzName + ".selectSettingByTemplateName");
    }
}
