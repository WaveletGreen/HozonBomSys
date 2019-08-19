/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.interaction;

import cn.net.connor.hozon.dao.basic.MyBatisBaseDao;
import cn.net.connor.hozon.dao.pojo.interaction.FeatureBomLineRelationHistory;
import cn.net.connor.hozon.dao.pojo.interaction.SingleVehicleBomRelation;
import org.apache.ibatis.annotations.Param;
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
    int deleteByProjectId(@Param("projectId") String projectId);

    /**
     * 批量插入数据
     *
     * @param list
     * @return
     */
    int insertList(List<FeatureBomLineRelationHistory> list);

    /**
     * 查询历史的2Y层与特性值对应的数据
     *
     * @param projectId
     * @param dmbId
     * @return
     */
    List<SingleVehicleBomRelation> selectHistory(@Param("projectId") String projectId, @Param("dmbId") Long dmbId);
}