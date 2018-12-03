/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import sql.pojo.cfg.fullCfg.HzFullCfgMain;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 全配置BOM一级清单主数据dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzFullCfgMainDao extends BasicDao<HzFullCfgMain>{

    int deleteByPrimaryKey(Long id);

    Long insertBackId(HzFullCfgMain record);


    HzFullCfgMain selectByPrimaryKey(Long id);

    HzFullCfgMain selectByProjectId(String id);

    int deleteByProjectUid(String projectUid);

    Long insertSeqAll(HzFullCfgMain hzFullCfgMain);

    int updateStatusByOrderId(Long orderId, Integer stutas);

    int updateStatusById(HzFullCfgMain hzFullCfgMain);

    int updateChangeByOrderId(Long orderId);
}