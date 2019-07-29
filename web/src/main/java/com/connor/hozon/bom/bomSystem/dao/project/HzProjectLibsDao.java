/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.project;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.project.HzProjectLibs;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 项目dao
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzProjectLibsDao extends BasicDao<HzProjectLibs> {

    HzProjectLibs selectByPrimaryKey(@Param("puid") String puid);

    List<HzProjectLibs> selectAllProject();

    HzProjectLibs selectByProjectCode(@Param("pProjectCode") String pProjectCode);

    int deleteByPrimaryKey(@Param("puid") String puid);
}