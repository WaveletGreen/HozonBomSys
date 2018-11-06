/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgMain;

import java.math.BigDecimal;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 全配置BOM一级清单主数据dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzFullCfgMainDao extends BasicDao<HzFullCfgMain>{

    int deleteByPrimaryKey(BigDecimal id);

    BigDecimal insertBackId(HzFullCfgMain record);


    HzFullCfgMain selectByPrimaryKey(BigDecimal id);

    HzFullCfgMain selectByProjectId(String id);
}