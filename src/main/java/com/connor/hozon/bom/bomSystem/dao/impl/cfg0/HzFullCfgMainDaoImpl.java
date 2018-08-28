package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgMainDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzFullCfgMain;

import java.math.BigDecimal;
@Configuration
public class HzFullCfgMainDaoImpl implements HzFullCfgMainDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;
    private static final HzFullCfgMain HZ_FULL_CFG_MAIN = new HzFullCfgMain();
    @Override
    public int deleteByPrimaryKey(BigDecimal id) {
        HZ_FULL_CFG_MAIN.setId(id);
        return baseSQLUtil.executeDelete(HZ_FULL_CFG_MAIN,"com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgMainDao.deleteByPrimaryKey");
    }

    @Override
    public int insert(HzFullCfgMain record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgMainDao.insert");
    }

    @Override
    public int insertSelective(HzFullCfgMain record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgMainDao.insertSelective");
    }

    @Override
    public HzFullCfgMain selectByPrimaryKey(BigDecimal id) {
        HZ_FULL_CFG_MAIN.setId(id);
        return baseSQLUtil.executeQueryById(HZ_FULL_CFG_MAIN, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgMainDao.selectByPrimaryKey");
    }

    @Override
    public int updateByPrimaryKeySelective(HzFullCfgMain record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgMainDao.updateByPrimaryKeySelective");
    }

    @Override
    public int updateByPrimaryKey(HzFullCfgMain record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgMainDao.updateByPrimaryKey");
    }

    @Override
    public HzFullCfgMain selectByProjectId(String id) {
        HZ_FULL_CFG_MAIN.setProjectUid(id);
        return  baseSQLUtil.executeQueryById(HZ_FULL_CFG_MAIN, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgMainDao.selectByProjectId");
    }
}
