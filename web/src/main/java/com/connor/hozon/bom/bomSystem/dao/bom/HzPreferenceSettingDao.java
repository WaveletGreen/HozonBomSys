/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.bom;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.HzPreferenceSetting;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 0.0.1版TC同步BOM系统首选项
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Deprecated
@Configuration
public interface HzPreferenceSettingDao extends BasicDao<HzPreferenceSetting> {
    /**
     * 根据主键删除1个首选项
     *
     * @param puid
     * @return
     */
    int deleteByPrimaryKey(String puid);

    /**
     * 根据主键筛选首选项
     *
     * @param puid
     * @return
     */
    HzPreferenceSetting selectByPrimaryKey(String puid);

    /**
     * 根据首选项名个主数据查找首选项
     *
     * @param puid
     * @return
     */
    HzPreferenceSetting selectSettingByNameWithMainRecord(HzPreferenceSetting puid);


    List<HzPreferenceSetting> selectSettingByTemplateName(HzPreferenceSetting setting);
}