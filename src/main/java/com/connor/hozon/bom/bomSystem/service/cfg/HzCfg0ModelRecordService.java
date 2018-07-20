package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0ModelRecord;

import java.util.Collections;
import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 14:55
 */
@Service("hzCfg0modelRecordService")
public class HzCfg0ModelRecordService {
    @Autowired
    HzCfg0ModelRecordDao hzCfg0ModelRecordDao;

    public HzCfg0ModelRecord doGetById(String puid) {
        return hzCfg0ModelRecordDao.selectByPrimaryKey(puid);
    }

    public boolean doUpdateBasic(HzCfg0ModelRecord modelRecord) {
        return hzCfg0ModelRecordDao.updateBasicByPuid(modelRecord) > 0 ? true : false;
    }

    public boolean doUpdateModelName(HzCfg0ModelRecord modelRecord) {
        return hzCfg0ModelRecordDao.updateModelName(modelRecord) > 0 ? true : false;
    }


    public boolean doInsert(List<HzCfg0ModelRecord> modelRecord) {
        return hzCfg0ModelRecordDao.insertByBatch(modelRecord) > 0 ? true : false;
    }
}
