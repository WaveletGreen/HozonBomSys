package com.connor.hozon.bom.bomSystem.dao.cfg.relevance;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import sql.pojo.cfg.relevance.HzRelevanceRelation;

import java.util.List;

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
}