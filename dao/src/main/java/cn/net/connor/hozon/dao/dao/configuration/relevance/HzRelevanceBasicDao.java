/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.configuration.relevance;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasic;
import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasicChange;
import cn.net.connor.hozon.dao.query.relevance.HzRelevanceQuery;
import cn.net.connor.hozon.dao.query.relevance.HzRelevanceQueryResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 相关性主数据dao
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
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
     *
     * @param list
     * @return
     */
    int insertByBatch(List<HzRelevanceBasic> list);

    /**
     * 插入1条基本数据
     *
     * @param hzRelevanceBasic
     * @return
     */
    Long insertBasic(HzRelevanceBasic hzRelevanceBasic);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    List<HzRelevanceQueryResult> selectByPage(HzRelevanceQuery dto);

    /**
     * 获取当前项目下的相关性总数
     *
     * @param dto
     * @return
     */
    Integer tellMeHowManyOfIt(HzRelevanceQuery dto);

    List<HzRelevanceBasic> selectByProjectPuid(@Param("rbProjectUid") String rbProjectUid);

    int updateStatus(HzRelevanceBasic hzRelevanceBasicUpdate);

    int updateStatusByOrderChangeId(HzRelevanceBasic hzRelevanceBasic);

    List<HzRelevanceBasic> selectByChange(List<HzRelevanceBasicChange> hzRelevanceBasicChangesAdd);

    int deleteByPrimaryKeyList(List<HzRelevanceBasic> hzRelevanceBasicsDelete);

    int updateStatusList(List<HzRelevanceBasic> hzRelevanceBasicsUpdate);

    int updateStatusByChange(List<HzRelevanceBasicChange> hzRelevanceBasicChanges);

    int doUpdateIsSent(Map<String,Object> map);

    List<HzRelevanceBasic> selectByProjectPuidAndStatus(String projectPuid);

    int deleteByOrderId(Long orderId);
}