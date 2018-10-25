
/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.derivative;

import com.connor.hozon.bom.bomSystem.dao.derivative.HzCfg0ModelFeatureDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.derivative.HzCfg0ModelFeature;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public class HzCfg0ModelFeatureDaoImpl extends BasicDaoImpl<HzCfg0ModelFeature> implements HzCfg0ModelFeatureDao {

    private static final HzCfg0ModelFeature MODEL_FEATURE = new HzCfg0ModelFeature();

    public HzCfg0ModelFeatureDaoImpl() {
        clz = HzCfg0ModelFeatureDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 根据主键删除模型特性数据
     *
     * @param puid
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, clzName + ".deleteByPrimaryKey");
    }

    /**
     * 根据主键查找1个模型特性数据
     *
     * @param puid 主键
     * @return
     */
    @Override
    public HzCfg0ModelFeature selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(MODEL_FEATURE, puid, clzName + ".selectByPrimaryKey", true);
    }

    /**
     * 连同工厂编号一起查出来
     *
     * @param puid
     * @return
     */
    @Override
    public HzCfg0ModelFeature selectByPrimaryKeyWithFactoryCode(String puid) {
        return baseSQLUtil.executeQueryByPass(MODEL_FEATURE, puid, clzName + ".selectByPrimaryKeyWithFactoryCode", true);
    }

    /**
     * 根据车型模型puid查找1个模型特性数据
     *
     * @param pPertainToModel 车型模型主键，puid
     * @return
     */
    @Override
    public HzCfg0ModelFeature selectByModelPuid(String pPertainToModel) {
        return baseSQLUtil.executeQueryByPass(MODEL_FEATURE, pPertainToModel, clzName + ".selectByModelPuid", true);
    }

    /**
     * 根据颜色车身puid查找1个模型特性数据
     *
     * @param pPertainToColorModel 颜色车身的主键,puid
     * @return
     */
    @Override
    public HzCfg0ModelFeature selectByModelColorPuid(String pPertainToColorModel) {
        return baseSQLUtil.executeQueryByPass(MODEL_FEATURE, pPertainToColorModel, clzName + ".selectByModelColorPuid", true);
    }

    /**
     * 根据颜色车身和车型模型的puid查找1个模型特性数据
     *
     * @param feature 传入所属车型模型个配色模型
     * @return
     */
    @Override
    public HzCfg0ModelFeature selectByModelAndColorPuids(HzCfg0ModelFeature feature) {
        return baseSQLUtil.executeQueryById(feature, clzName + ".selectByModelAndColorPuids");
    }

    /**
     * 仅仅更新基本信息。不更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByModelPuidWithBasic(HzCfg0ModelFeature record) {
        return baseSQLUtil.executeUpdate(record, clzName + ".updateByModelPuidWithBasic");
    }

    /**
     * 查找项目下的所有衍生物料
     *
     * @param projectUid 项目UID
     * @return
     */
    @Override
    public List<HzCfg0ModelFeature> selectAllByProjectUid(String projectUid) {
        return baseSQLUtil.executeQueryByPass(MODEL_FEATURE, projectUid, clzName + ".selectAllByProjectUid");
    }


}
