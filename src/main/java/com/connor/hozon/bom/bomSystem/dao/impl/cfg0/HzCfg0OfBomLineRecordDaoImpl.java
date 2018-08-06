package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OfBomLineRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0OfBomLineRecord;

import java.util.List;

@Configuration
public class HzCfg0OfBomLineRecordDaoImpl implements HzCfg0OfBomLineRecordDao {

    private final static HzCfg0OfBomLineRecord RECORD = new HzCfg0OfBomLineRecord();

    @Autowired
    IBaseSQLUtil baseSQLUtil;

    /**
     * 主键删除
     *
     * @param puid
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OfBomLineRecordDao.deleteByPrimaryKey");
    }

    /**
     * 插入单条数据
     *
     * @param record
     * @return
     */
    @Override
    public int insert(HzCfg0OfBomLineRecord record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OfBomLineRecordDao.insert");
    }

    /**
     * 根据主键搜索
     *
     * @param puid
     * @return
     */
    @Override
    public HzCfg0OfBomLineRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(RECORD, puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OfBomLineRecordDao.selectByPrimaryKey", true);
    }

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(HzCfg0OfBomLineRecord record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OfBomLineRecordDao.updateByPrimaryKey");
    }

    /**
     * 批量插入
     *
     * @param records
     * @return
     */
    @Override
    public int insertByBatch(List<HzCfg0OfBomLineRecord> records) {
        return baseSQLUtil.executeInsert(records, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OfBomLineRecordDao.insertByBatch");
    }
}
