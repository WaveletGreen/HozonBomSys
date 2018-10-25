/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.project;

import java.util.List;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.project.HzProjectLibs;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 项目dao
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzProjectLibsDao extends BasicDao<HzProjectLibs> {

    HzProjectLibs selectByPrimaryKey(@Param("puid") String puid);

    List<HzProjectLibs> selectAllProject();

    HzProjectLibs selectByProjectCode(@Param("pProjectCode") String pProjectCode);

    int deleteByPrimaryKey(@Param("puid") String puid);
}