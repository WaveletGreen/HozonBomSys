package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0ModelRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 14:53
 */
@Service("hzCfg0ModelRecordDao")
public class HzCfg0ModelRecordDaoImpl implements HzCfg0ModelRecordDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;
    private final static HzCfg0ModelRecord RECORD = new HzCfg0ModelRecord();

    @Override
    public HzCfg0ModelRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(RECORD, puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao.selectByPrimaryKey", true);
    }

    @Override
    public int updateBasicByPuid(HzCfg0ModelRecord modelRecord) {
        return baseSQLUtil.executeUpdate(modelRecord, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao.updateBasicByPuid");
    }

    @Override
    public int updateModelName(HzCfg0ModelRecord modelRecord) {
        return baseSQLUtil.executeUpdate(modelRecord, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao.updateModelName");
    }

    @Override
    public int insertByBatch(List<HzCfg0ModelRecord> modelRecord) {
        return baseSQLUtil.executeInsert(modelRecord, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao.insertByBatch");
    }

    /**
     * 该项目下的所有车型
     *
     * @param projectPuid
     * @return
     */
    @Override
    public List<HzCfg0ModelRecord> selectByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(RECORD, projectPuid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao.selectByProjectPuid");
    }

    @Override
    public int deleteModelById(String modelId) {
        return baseSQLUtil.executeDelete(modelId,"com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao.deleteModelById");
    }
}
