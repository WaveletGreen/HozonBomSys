package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0ModelFeature;

import java.util.List;

@Configuration
public interface HzCfg0ModelFeatureDao {
    /**
     * 根据主键删除模型特性数据
     *
     * @param puid
     * @return
     */
    int deleteByPrimaryKey(@Param("puid") String puid);

    /**
     * 插入1个模型特性数据
     *
     * @param record 模型特性数据，包含主键，包含车型模型的puid，不一定包含颜色车身的puid(1阶段不需要颜色信息)
     * @return
     */
    int insert(HzCfg0ModelFeature record);

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
     * 根据主键更新1个模型特性
     *
     * @param record 模型特性，包含主键
     * @return
     */
    int updateByPrimaryKey(HzCfg0ModelFeature record);

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
}