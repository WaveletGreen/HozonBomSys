package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzFcfgBomLvl1ListOperationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzFcfgBomLvl1ListOperation;

@Configuration
public class HzFcfgBomLvl1ListOperationDaoImpl implements HzFcfgBomLvl1ListOperationDao {
    private final IBaseSQLUtil baseSQLUtil;

    @Autowired
    public HzFcfgBomLvl1ListOperationDaoImpl(IBaseSQLUtil baseSQLUtil) {
        this.baseSQLUtil = baseSQLUtil;
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFcfgBomLvl1ListOperationDao.deleteByPrimaryKey");
    }

    @Override
    public int logicDeleteByPrimaryKey(HzFcfgBomLvl1ListOperation record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFcfgBomLvl1ListOperationDao.logicDeleteByPrimaryKey");
    }

    @Override
    public int insert(HzFcfgBomLvl1ListOperation record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFcfgBomLvl1ListOperationDao.insert");
    }

    @Override
    public HzFcfgBomLvl1ListOperation selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzFcfgBomLvl1ListOperation(), puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFcfgBomLvl1ListOperationDao.selectByPrimaryKey", true);
    }

    @Override
    public int updateByPrimaryKey(HzFcfgBomLvl1ListOperation record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFcfgBomLvl1ListOperationDao.updateByPrimaryKey");
    }
}
