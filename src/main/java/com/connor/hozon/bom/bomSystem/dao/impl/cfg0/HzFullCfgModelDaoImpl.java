package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzFullCfgModel;
import sql.pojo.cfg.HzFullCfgWithCfg;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class HzFullCfgModelDaoImpl implements HzFullCfgModelDao {

    @Autowired
    IBaseSQLUtil baseSQLUtil;
    private static final HzFullCfgModel HZ_FULL_CFG_MODEL = new HzFullCfgModel();

    @Override
    public int deleteByPrimaryKey(BigDecimal id) {
        HZ_FULL_CFG_MODEL.setId(id);
        return baseSQLUtil.executeDelete(HZ_FULL_CFG_MODEL, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao.deleteByPrimaryKey");
    }

    @Override
    public int insert(HzFullCfgModel record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao.insert");
    }

    @Override
    public int insertSelective(HzFullCfgModel record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao.insertSelective");
    }

    @Override
    public HzFullCfgModel selectByPrimaryKey(BigDecimal id) {
        HZ_FULL_CFG_MODEL.setId(id);
        return baseSQLUtil.executeQueryById(HZ_FULL_CFG_MODEL, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao.selectByPrimaryKey");
    }

    @Override
    public int updateByPrimaryKeySelective(HzFullCfgModel record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao.updateByPrimaryKeySelective");
    }

    @Override
    public int updateByPrimaryKey(HzFullCfgModel record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao.updateByPrimaryKey");
    }

    @Override
    public List<String> selectCfg(String puid) {
        return baseSQLUtil.executeQuery(puid,"com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao.selectCfg");
    }

    @Override
    public void insertCfgs(List<HzFullCfgModel> cfgs) {
        baseSQLUtil.executeInsert(cfgs, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao.insertCfgs");
    }

    @Override
    public List<HzFullCfgModel> selectByMainPuid(BigDecimal mainPuid) {
        HZ_FULL_CFG_MODEL.setFlModVersion(mainPuid);
        return baseSQLUtil.executeQuery(HZ_FULL_CFG_MODEL, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao.selectByMainPuid");
    }

    @Override
    public int updateByHzFullCfgModelList(List<HzFullCfgModel> hzFullCfgModels) {
        return baseSQLUtil.executeUpdate(hzFullCfgModels,"com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao.updateByHzFullCfgModelList");
    }

    @Override
    public int updateByBomLinePuid(HzFullCfgWithCfg hzFullCfgWithCfg) {
        return baseSQLUtil.executeUpdate(hzFullCfgWithCfg,"com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao.updateByBomLinePuid");
    }

}
