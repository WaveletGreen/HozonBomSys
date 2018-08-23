package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0ModelFeature;

import java.util.List;

@Configuration
public class HzCfg0ModelFeatureDaoImpl implements HzCfg0ModelFeatureDao {

    private final IBaseSQLUtil baseSQLUtil;
    private static final HzCfg0ModelFeature MODEL_FEATURE = new HzCfg0ModelFeature();

    @Autowired
    public HzCfg0ModelFeatureDaoImpl(IBaseSQLUtil baseSQLUtil) {
        this.baseSQLUtil = baseSQLUtil;
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao.deleteByPrimaryKey");
    }

    @Override
    public int insert(HzCfg0ModelFeature record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao.insert");
    }

    @Override
    public HzCfg0ModelFeature selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(MODEL_FEATURE, puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao.selectByPrimaryKey", true);
    }

    /**
     * 连同工厂编号一起查出来
     *
     * @param puid
     * @return
     */
    @Override
    public HzCfg0ModelFeature selectByPrimaryKeyWithFactoryCode(String puid) {
        return baseSQLUtil.executeQueryByPass(MODEL_FEATURE, puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao.selectByPrimaryKeyWithFactoryCode", true);
    }


    @Override
    public HzCfg0ModelFeature selectByModelPuid(String pPertainToModel) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0ModelFeature(), pPertainToModel, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao.selectByModelPuid", true);
    }

    @Override
    public HzCfg0ModelFeature selectByModelColorPuid(String pPertainToColorModel) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0ModelFeature(), pPertainToColorModel, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao.selectByModelColorPuid", true);
    }

    @Override
    public HzCfg0ModelFeature selectByModelAndColorPuids(String pPertainToModel, String pPertainToColorModel) {
//        List<HzCfg0ModelFeature> list = baseSQLUtil.executeQueryByPass(new HzCfg0ModelFeature(), new String[]{pPertainToModel, pPertainToColorModel}, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao.selectByModelAndColorPuids");
//        if (list == null || list.size() == 0)
//            return null;
//        else return list.get(0);
        return baseSQLUtil.executeQueryByPass(new HzCfg0ModelFeature(), "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao.selectByModelAndColorPuids", pPertainToModel, pPertainToColorModel);
    }

    @Override
    public int updateByPrimaryKey(HzCfg0ModelFeature record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao.updateByPrimaryKey");
    }

    @Override
    public int updateByModelPuidWithBasic(HzCfg0ModelFeature record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelFeatureDao.updateByModelPuidWithBasic");
    }
}
