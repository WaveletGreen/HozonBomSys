/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.integration;

import cn.net.connor.hozon.dao.MyBatisBaseDao;
import cn.net.connor.hozon.dao.pojo.interaction.FeatureBomLineRelationHistory;
import org.apache.ibatis.annotations.Param;
import org.hibernate.annotations.SQLDelete;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 特性与bomline对应的dao层
 */
@Repository
public interface FeatureBomLineRelationHistoryDao extends MyBatisBaseDao<FeatureBomLineRelationHistory, Long, FeatureBomLineRelationHistory> {
    /**
     * 删除项目下的所有关联数据
     *
     * @return
     */
    @SQLDelete(sql = "delete from CFG_BOMLINE_RELATION_HISTORY where PROJECT_ID = #{projectId}")
    int deleteByProjectId(@Param("projectId") String projectId);

    /**
     * 批量插入数据
     * @param list
     * @return
     */
    int insertList(List<FeatureBomLineRelationHistory> list);
}