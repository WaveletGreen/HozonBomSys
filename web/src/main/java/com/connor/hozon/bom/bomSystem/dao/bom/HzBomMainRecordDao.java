/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.bom;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import sql.pojo.bom.HZBomMainRecord;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: BOM主配置dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzBomMainRecordDao extends BasicDao<HZBomMainRecord> {
    /**
     * 根据项目查找BOM主配置
     *
     * @param projectUid 项目UID
     * @return 返回当前项目下的主配置
     */
    HZBomMainRecord selectByProjectPuid(@Param("projectUid") String projectUid);
}