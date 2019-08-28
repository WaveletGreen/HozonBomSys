package cn.net.connor.hozon.dao.dao.configuration.relevance;


import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasicChange;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HzRelevanceBasicChangeDao {
    int insert(HzRelevanceBasicChange record);

    int insertSelective(HzRelevanceBasicChange record);

    int insertList(List<HzRelevanceBasicChange> hzRelevanceBasicChanges);

    List<HzRelevanceBasicChange> selectByOrderChangeId(@Param("changeOrderId") Long changeOrderId);

    HzRelevanceBasicChange selectMaxVersionByProject(@Param("rbProjectUid") String rbProjectUid);

    List<HzRelevanceBasicChange> selectByVersionAndProjectId(HzRelevanceBasicChange hzRelevanceBasicChangeQueryBefor);

    int deleteByChangeOrderid(Long orderChangeId);

    List<HzRelevanceBasicChange> selectLastexecutedByProjectId(String projectPuid);

    int updateStatusByIOrderId(HzRelevanceBasicChange hzRelevanceBasicChange);

    HzRelevanceBasicChange selectByLatestBySrc(@Param("srcId") Long srcId);

    HzRelevanceBasicChange selectLastVersion(HzRelevanceBasicChange hzRelevanceBasicChangeQueryBefor);

    HzRelevanceBasicChange selectMaxVersion(HzRelevanceBasicChange hzRelevanceBasicChange);
}