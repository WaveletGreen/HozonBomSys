/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.relevance;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import sql.pojo.cfg.relevance.HzRelevanceRelation;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 相关性详情dao（从数据）
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzRelevanceRelationDao extends BasicDao<HzRelevanceRelation> {
    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int insertByBatch(List<HzRelevanceRelation> list);

    /**
     * 相关性ID删除
     *
     * @param rrRelevanceId
     * @return
     */
    int deleteByBasicId(Long rrRelevanceId);

    /**
     * 单条插入
     * @return
     */
    int insertOne(HzRelevanceRelation hzRelevanceRelation);
}