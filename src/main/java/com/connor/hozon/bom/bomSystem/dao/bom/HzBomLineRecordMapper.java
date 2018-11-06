/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.bom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import sql.pojo.bom.HzBomLineRecord;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 0.0.2版BOM dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzBomLineRecordMapper {

    HzBomLineRecord selectByPrimaryKey(HzBomLineRecord puid);

    List<HzBomLineRecord> selectByProjectPuid(@Param("projectPuid") String projectPuid);

    int updateByPrimaryKey(HzBomLineRecord record);
}