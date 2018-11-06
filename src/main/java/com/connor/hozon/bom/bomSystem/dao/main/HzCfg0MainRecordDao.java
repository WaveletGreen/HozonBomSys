/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.main;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.main.HzCfg0MainRecord;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 主配置
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzCfg0MainRecordDao extends BasicDao<HzCfg0MainRecord>{

    HzCfg0MainRecord selectByPrimaryKey(@Param("puid") String puid);

    HzCfg0MainRecord selectByProjectPuid(@Param("projectPuid") String projectPuid);

}