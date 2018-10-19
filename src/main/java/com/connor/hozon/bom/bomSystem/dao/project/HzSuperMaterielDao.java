/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.project;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.project.HzMaterielRecord;

import java.util.List;

@Configuration
public interface HzSuperMaterielDao extends BasicDao<HzMaterielRecord> {

    int deleteByPrimaryKey(String puid);

    HzMaterielRecord selectByPrimaryKey(@Param("puid") String puid);

    HzMaterielRecord selectByProjectPuid(@Param("projectPuid") String projectPuid);


}