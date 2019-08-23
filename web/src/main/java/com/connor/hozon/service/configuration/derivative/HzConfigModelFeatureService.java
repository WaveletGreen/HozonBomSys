/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.configuration.derivative;

import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzCfg0ModelFeature;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface HzConfigModelFeatureService {
    /**
     * 根据主键删除模型特性数据
     *
     * @param puid
     * @return
     */
    boolean doDeleteByPrimaryKey(String puid);

    /**
     * 插入1个模型特性数据
     *
     * @param record 模型特性数据，包含主键，包含车型模型的puid，不一定包含颜色车身的puid(1阶段不需要颜色信息)
     * @return
     */
    boolean doInsert(HzCfg0ModelFeature record);

    /**
     * 根据主键查找1个模型特性数据
     *
     * @param puid 主键
     * @return
     */
    HzCfg0ModelFeature doSelectByPrimaryKey(String puid);

    /**
     * 连同工厂编号一起查出来
     *
     * @param puid
     * @return
     */
    HzCfg0ModelFeature doSelectByPrimaryKeyWithFactoryCode(String puid);

    /**
     * 根据车型模型puid查找1个模型特性数据
     *
     * @param pPertainToModel 车型模型主键，puid
     * @return
     */
    HzCfg0ModelFeature doSelectByModelPuid(String pPertainToModel);

    /**
     * 根据颜色车身puid查找1个模型特性数据
     *
     * @param pPertainToColorModel 颜色车身的主键,puid
     * @return
     */
    HzCfg0ModelFeature doSelectByModelColorPuid(String pPertainToColorModel);

    /**
     * 根据颜色车身和车型模型的puid查找1个模型特性数据
     *
     * @param pPertainToModel      车型模型puid
     * @param pPertainToColorModel 颜色车身的puid
     * @return
     */
    HzCfg0ModelFeature doSelectByModelAndColorPuids(String pPertainToModel, String pPertainToColorModel);

    /**
     * 根据主键更新1个模型特性
     *
     * @param record 模型特性，包含主键
     * @return
     */
    boolean doUpdateByPrimaryKey(HzCfg0ModelFeature record);

    boolean doUpdateByModelPuidWithBasic(String puid, String pFeatureCnDesc, String pFeatureSingleVehicleCode);

    /**
     * 获取项目下所有的衍生物料基本信息
     *
     * @param projectUid
     * @return
     */
    List<HzCfg0ModelFeature> doSelectAllByProjectUid(String projectUid);
}
