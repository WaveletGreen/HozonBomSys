package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0Record;

import java.util.List;


@Service("hzCfg0RecordDao")
public class HzCfg0RecordDaoImpl implements HzCfg0RecordDao {

    private final IBaseSQLUtil baseSQLUtil;

    @Autowired
    public HzCfg0RecordDaoImpl(IBaseSQLUtil baseSQLUtil) {
        this.baseSQLUtil = baseSQLUtil;
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.deleteByPrimaryKey");
    }

    @Override
    public int insert(HzCfg0Record record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.insert");
    }

    @Override
    public List<HzCfg0Record> selectByProjectPuid(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0Record(), puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.selectByProjectPuid");
    }

    @Override
    public HzCfg0Record selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0Record(), puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.selectByPrimaryKey", true);
    }

    @Override
    public int updateByPrimaryKey(HzCfg0Record record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.updateByPrimaryKey");
    }
}
