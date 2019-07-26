/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.derivative;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.derivative.HzCfg0ModelFeature;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 配置物料特性表-物料属性dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzCfg0ModelFeatureDao extends BasicDao<HzCfg0ModelFeature> {
    /**
     * 根据主键删除模型特性数据
     *
     * @param puid
     * @return
     */
    int deleteByPrimaryKey(@Param("puid") String puid);

    /**
     * 根据主键查找1个模型特性数据
     *
     * @param puid 主键
     * @return
     */
    HzCfg0ModelFeature selectByPrimaryKey(@Param("puid") String puid);

    /**
     * 连同工厂编号一起查出来
     *
     * @param puid
     * @return
     */
    HzCfg0ModelFeature selectByPrimaryKeyWithFactoryCode(@Param("puid") String puid);

    /**
     * 根据车型模型puid查找1个模型特性数据
     *
     * @param pPertainToModel 车型模型主键，puid
     * @return
     */
    HzCfg0ModelFeature selectByModelPuid(@Param("puid") String pPertainToModel);

    /**
     * 根据颜色车身puid查找1个模型特性数据
     *
     * @param pPertainToColorModel 颜色车身的主键,puid
     * @return
     */
    HzCfg0ModelFeature selectByModelColorPuid(@Param("pPertainToColorModel") String pPertainToColorModel);

    /**
     * 根据颜色车身和车型模型的puid查找1个模型特性数据
     *
     * @param feature 传入所属车型模型个配色模型
     * @return
     */
    HzCfg0ModelFeature selectByModelAndColorPuids(HzCfg0ModelFeature feature);

    /**
     * 仅仅更新基本信息。不更新
     *
     * @param record
     * @return
     */
    int updateByModelPuidWithBasic(HzCfg0ModelFeature record);

    /**
     * 查找项目下的所有衍生物料
     *
     * @param projectUid 项目UID
     * @return
     */
    List<HzCfg0ModelFeature> selectAllByProjectUid(@Param("projectUid") String projectUid);

    int updateIsSent(List<HzCfg0ModelFeature> hzCfg0ModelFeatureList);
}