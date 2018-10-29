/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.project;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.project.HzMaterielRecord;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 超级物料dao，用物料dao层代替
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzSuperMaterielDao extends BasicDao<HzMaterielRecord> {

    int deleteByPrimaryKey(String puid);

    HzMaterielRecord selectByPrimaryKey(@Param("puid") String puid);

    HzMaterielRecord selectByProjectPuid(@Param("projectPuid") String projectPuid);


}