package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0ModelRecord;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 14:53
 */
@Service("hzCfg0ModelRecordDao")
public class HzCfg0ModelRecordDaoImpl implements HzCfg0ModelRecordDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public HzCfg0ModelRecord selectByPrimaryKey(HzCfg0ModelRecord record) {
        return baseSQLUtil.executeQueryById(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao.selectByPrimaryKey");
    }
}
