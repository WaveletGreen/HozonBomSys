/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.project;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.project.HzPlatformRecord;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 平台dao
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzPlatformRecordDao extends BasicDao<HzPlatformRecord> {

    int deleteByPrimaryKey(@Param("puid") String puid);

    HzPlatformRecord selectByPrimaryKey(@Param("puid") String puid);

    List<HzPlatformRecord> selectAll();

    HzPlatformRecord selectByPlatformCode(@Param("pPlatformCode") String pPlatformCode);
}