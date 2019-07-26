package com.connor.hozon.bom.bomSystem.dao.relevance;


import sql.pojo.cfg.relevance.HzRelevanceBasic;
import sql.pojo.cfg.relevance.HzRelevanceBasicChange;

import java.util.List;

public interface HzRelevanceBasicChangeDao {
    int insert(HzRelevanceBasicChange record);

    int insertSelective(HzRelevanceBasicChange record);

    int insertList(List<HzRelevanceBasicChange> hzRelevanceBasicChanges);

    List<HzRelevanceBasicChange> selectByOrderChangeId(Long orderChangeId);

    HzRelevanceBasicChange selectMaxVersionByProject(String projectPuid);

    List<HzRelevanceBasicChange> selectByVersionAndProjectId(HzRelevanceBasicChange hzRelevanceBasicChangeQueryBefor);

    int deleteByChangeOrderid(Long orderChangeId);

    List<HzRelevanceBasicChange> selectLastexecutedByProjectId(String projectPuid);

    int updateStatusByIOrderId(HzRelevanceBasicChange hzRelevanceBasicChange);

    HzRelevanceBasicChange selectByLasteBySrc(HzRelevanceBasic hzRelevanceBasic);

    HzRelevanceBasicChange selectByVersion(HzRelevanceBasicChange hzRelevanceBasicChangeQueryBefor);

    HzRelevanceBasicChange selectMaxVersion(HzRelevanceBasicChange hzRelevanceBasicChange);
}