/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.configuration.derivative.impl;

import cn.net.connor.hozon.dao.dao.configuration.derivative.HzCfg0ModelFeatureDao;
import com.connor.hozon.bom.service.configuration.derivative.HzConfigModelFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzCfg0ModelFeature;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Mayways
 * @Date :  2018/10/12 13:12
 * @Description: 配置配置物料特性表单个衍生物料信息修改服务层
 * @Modified By:
 */
@Service
public class HzConfigModelFeatureServiceImpl implements HzConfigModelFeatureService {
    /**
     * 对应的模型特性dao层
     */
    @Autowired
    private HzCfg0ModelFeatureDao hzCfg0ModelFeatureDao;


    @Override
    public boolean doDeleteByPrimaryKey(String puid) {
        return hzCfg0ModelFeatureDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    @Override
    public boolean doInsert(HzCfg0ModelFeature record) {
        return hzCfg0ModelFeatureDao.insert(record) > 0 ? true : false;
    }

    @Override
    public HzCfg0ModelFeature doSelectByPrimaryKey(String puid) {
        return hzCfg0ModelFeatureDao.selectByPrimaryKey(puid);
    }

    /**
     * 连同工厂编号一起查出来
     *
     * @param puid
     * @return
     */
    @Override
    public HzCfg0ModelFeature doSelectByPrimaryKeyWithFactoryCode(String puid) {
        return hzCfg0ModelFeatureDao.selectByPrimaryKeyWithFactoryCode(puid);
    }


    @Override
    public HzCfg0ModelFeature doSelectByModelPuid(String pPertainToModel) {
        return hzCfg0ModelFeatureDao.selectByModelPuid(pPertainToModel);
    }

    @Override
    public HzCfg0ModelFeature doSelectByModelColorPuid(String pPertainToColorModel) {
        return hzCfg0ModelFeatureDao.selectByModelColorPuid(pPertainToColorModel);
    }

    @Override
    public HzCfg0ModelFeature doSelectByModelAndColorPuids(String pPertainToModel, String pPertainToColorModel) {
        HzCfg0ModelFeature feature = new HzCfg0ModelFeature();
        feature.setpPertainToModel(pPertainToModel);
        feature.setpPertainToColorModel(pPertainToColorModel);
        return hzCfg0ModelFeatureDao.selectByModelAndColorPuids(feature);
    }

    @Override
    public boolean doUpdateByPrimaryKey(HzCfg0ModelFeature record) {
        return hzCfg0ModelFeatureDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    @Override
    public boolean doUpdateByModelPuidWithBasic(String puid, String pFeatureCnDesc, String pFeatureSingleVehicleCode) {
        HzCfg0ModelFeature hzCfg0ModelFeature = new HzCfg0ModelFeature();
        hzCfg0ModelFeature.setpPertainToModel(puid);
        hzCfg0ModelFeature.setpFeatureCnDesc(pFeatureCnDesc);
        hzCfg0ModelFeature.setpFeatureSingleVehicleCode(pFeatureSingleVehicleCode);
        return hzCfg0ModelFeatureDao.updateByModelPuidWithBasic(hzCfg0ModelFeature) > 0 ? true : false;
    }

    /**
     * 获取项目下所有的衍生物料基本信息
     *
     * @param projectUid
     * @return
     */
    @Override
    public List<HzCfg0ModelFeature> doSelectAllByProjectUid(String projectUid) {
        return hzCfg0ModelFeatureDao.selectAllByProjectUid(projectUid);
    }


}
