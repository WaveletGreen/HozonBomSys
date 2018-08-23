package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.IHzCfg0ModelFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0ModelFeature;

@Service("hzCfg0ModelFeatureService")
public class HzCfg0ModelFeatureService implements IHzCfg0ModelFeatureService {
    /**
     * 对应的模型特性dao层
     */
    private final HzCfg0ModelFeatureDao hzCfg0ModelFeatureDao;

    @Autowired
    public HzCfg0ModelFeatureService(HzCfg0ModelFeatureDao hzCfg0ModelFeatureDao) {
        this.hzCfg0ModelFeatureDao = hzCfg0ModelFeatureDao;
    }

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
        return hzCfg0ModelFeatureDao.selectByModelAndColorPuids(pPertainToModel, pPertainToColorModel);
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
}
