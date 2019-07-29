package cn.net.connor.hozon.dao.dao.configuration.relevance;


import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasic;
import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasicChange;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
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