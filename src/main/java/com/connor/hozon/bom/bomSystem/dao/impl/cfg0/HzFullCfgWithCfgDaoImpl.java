package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzFullCfgWithCfg;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/28 15:58
 * @Modified By:
 */
@Configuration
public class HzFullCfgWithCfgDaoImpl implements HzFullCfgWithCfgDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;
    private static final HzFullCfgWithCfg WITH_CFG = new HzFullCfgWithCfg();

    @Override
    public int deleteByPrimaryKey(Long id) {
        WITH_CFG.setId(id);
        return baseSQLUtil.executeDelete(WITH_CFG, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.deleteByPrimaryKey");
    }

    @Override
    public int insert(HzFullCfgWithCfg record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.insert");
    }

    @Override
    public int insertSelective(HzFullCfgWithCfg record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.insertSelective");
    }

    @Override
    public HzFullCfgWithCfg selectByPrimaryKey(Long id) {
        WITH_CFG.setId(id);
        return baseSQLUtil.executeQueryById(WITH_CFG, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.selectByPrimaryKey");
    }

    @Override
    public List<HzFullCfgWithCfg> selectByMainID(Long flCfgVersion) {
        WITH_CFG.setFlCfgVersion(flCfgVersion);
        return baseSQLUtil.executeQuery(WITH_CFG, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.selectByMainID");
    }

    @Override
    public int updateByPrimaryKeySelective(HzFullCfgWithCfg record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.updateByPrimaryKeySelective");
    }

    @Override
    public int updateByPrimaryKey(HzFullCfgWithCfg record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.updateByPrimaryKey");
    }
}
