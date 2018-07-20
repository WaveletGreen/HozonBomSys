package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ToModelRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0ToModelRecord;

@Configuration
public class HzCfg0ToModelRecordDaoImpl implements HzCfg0ToModelRecordDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteBySome("com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ToModelRecordDao.deleteByPrimaryKey", puid);
    }

    @Override
    public int insert(HzCfg0ToModelRecord record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ToModelRecordDao.insert");
    }

    @Override
    public HzCfg0ToModelRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0ToModelRecord(), puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ToModelRecordDao.selectByPrimaryKey", true);
    }

    @Override
    public int updateByPrimaryKey(HzCfg0ToModelRecord record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ToModelRecordDao.updateByPrimaryKey");
    }
}
