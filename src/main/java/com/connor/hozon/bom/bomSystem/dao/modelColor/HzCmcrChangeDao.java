/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.modelColor;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.modelColor.HzCmcrChange;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 基础车型主数据变更dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzCmcrChangeDao extends BasicDao<HzCmcrChange> {
    /**
     * 插入变更后数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    Long insertAfter(HzCmcrChange cmcr) throws Exception;

    /**
     * 插入变更前数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    Long insertBefore(HzCmcrChange cmcr) throws Exception;

    /**
     * 插入变更后数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    Long insertAfterSelective(HzCmcrChange cmcr) throws Exception;

    /**
     * 插入变更前数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    Long insertBeforeSelective(HzCmcrChange cmcr) throws Exception;

    /**
     * 变更后数据主键查找
     *
     * @param id 主键
     * @return 变更对象
     */
    HzCmcrChange selectAfterByPrimaryKey(Long id) throws Exception;

    /**
     * 变更前数据主键查找
     *
     * @param id 主键
     * @return 变更对象
     */
    HzCmcrChange selectBeforeByPrimaryKey(Long id) throws Exception;

    /**
     * 主键删除变更后数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteAfterByPrimaryKey(Long id) throws Exception;

    /**
     * 主键删除变更前数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteBeforeByPrimaryKey(Long id) throws Exception;

    /**
     * 主键更新变更后数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    int updateAfterByPrimaryKey(HzCmcrChange cmcr) throws Exception;

    /**
     * 主键更新变更前数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    int updateBeforeByPrimaryKey(HzCmcrChange cmcr) throws Exception;

    /**
     * 主键选择更新变更前数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    int updateAfterByPrimaryKeySelective(HzCmcrChange cmcr) throws Exception;

    /**
     * 主键选择更新变更后数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    int updateBeforeByPrimaryKeySelective(HzCmcrChange cmcr) throws Exception;

    /**
     * 批量插入变更后主数据
     * @param hzCmcrChangesAfter
     * @return
     * @throws Exception
     */
    int insertAfterList(List<HzCmcrChange> hzCmcrChangesAfter) throws Exception;

    /**
     * 查询变更后最近一次的主数据
     * @param hzCmcrChangesLastAfter
     * @return
     * @throws Exception
     */
    List<HzCmcrChange> selectLastAfter(List<HzCmcrChange> hzCmcrChangesLastAfter) throws Exception;

    /**
     * 批量插入变更前的主数据
     * @param hzCmcrChangesLastAfter
     * @return
     * @throws Exception
     */
    int insertBeforeList(List<HzCmcrChange> hzCmcrChangesLastAfter) throws Exception;

    List<HzCmcrChange> doQueryCmcrDetailChangeBeforAndAfter(HzCmcrChange hzCmcrChange);

    List<HzCmcrChange> doQueryCmcrChangeBefor(Long vwoId);

    List<HzCmcrChange> doQueryCmcrChangeBeforFirst(Long vwoId);

    List<HzCmcrChange> doQueryCmcrChangeAfterFirst(Long vwoId);

    List<HzCmcrChange> doQueryCmcrChangeAfter(Long vwoId);
}