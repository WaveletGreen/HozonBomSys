package com.connor.hozon.bom.bomSystem.dao.cfg.relevance;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.relevance.HzRelevanceBasic;

import java.util.List;

public interface HzRelevanceBasicDao extends BasicDao<HzRelevanceBasic> {
    /**
     * 删除项目下的全部相关性
     *
     * @param rbProjectUid
     * @return
     */
    int deleteByProjectUid(@Param("rbProjectUid") String rbProjectUid);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertByBatch(List<HzRelevanceBasic> list);
}