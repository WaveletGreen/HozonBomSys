package com.connor.hozon.bom.bomSystem.dao.relevance;


import sql.pojo.cfg.relevance.HzRelevanceBasicChange;

import java.util.List;

public interface HzRelevanceBasicChangeDao {
    int insert(HzRelevanceBasicChange record);

    int insertSelective(HzRelevanceBasicChange record);

    int insertList(List<HzRelevanceBasicChange> hzRelevanceBasicChanges);
}