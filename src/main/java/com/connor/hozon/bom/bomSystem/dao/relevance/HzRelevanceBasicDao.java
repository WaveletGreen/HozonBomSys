/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.relevance;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import com.connor.hozon.bom.bomSystem.dto.relevance.HzRelevanceQueryDTO;
import com.connor.hozon.bom.bomSystem.dto.relevance.HzRelevanceQueryResultBean;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.relevance.HzRelevanceBasic;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 相关性主数据dao
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
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
    List<HzRelevanceQueryResultBean> selectByPage(HzRelevanceQueryDTO dto);

    /**
     * 获取当前项目下的相关性总数
     *
     * @param dto
     * @return
     */
    Integer tellMeHowManyOfIt(HzRelevanceQueryDTO dto);
}