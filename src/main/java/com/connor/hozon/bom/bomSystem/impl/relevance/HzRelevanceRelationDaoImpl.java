/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.relevance;

import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.relevance.HzRelevanceRelationDao;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.relevance.HzRelevanceRelation;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service
@Configuration
public class HzRelevanceRelationDaoImpl extends BasicDaoImpl<HzRelevanceRelation> implements HzRelevanceRelationDao {
    private final static HzRelevanceRelation RELATION = new HzRelevanceRelation();

    public HzRelevanceRelationDaoImpl() {
        clz = HzRelevanceRelationDao.class;
    }

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    @Override
    public int insertByBatch(List<HzRelevanceRelation> list) {
        return baseSQLUtil.executeInsert(list, clz.getCanonicalName() + ".insertByBatch");
    }

    /**
     * 相关性ID删除
     *
     * @param rrRelevanceId
     * @return
     */
    @Override
    public int deleteByBasicId(Long rrRelevanceId) {
        RELATION.setRrRelevanceId(rrRelevanceId);
        return baseSQLUtil.executeDelete(rrRelevanceId, clz.getCanonicalName() + ".deleteByBasicId");
    }

    /**
     * 单条插入
     * @return
     */
    @Override
    public int insertOne(HzRelevanceRelation hzRelevanceRelation) {
        return baseSQLUtil.executeInsert(hzRelevanceRelation, clz.getCanonicalName() + ".insertOne");
    }
}
