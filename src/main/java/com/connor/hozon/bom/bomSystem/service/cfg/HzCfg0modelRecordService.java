package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0ModelRecord;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 14:55
 */
@Service("hzCfg0modelRecordService")
public class HzCfg0modelRecordService {
    @Autowired
    HzCfg0ModelRecordDao hzCfg0ModelRecordDao;

    public HzCfg0ModelRecord doGetById(HzCfg0ModelRecord record) {
        return hzCfg0ModelRecordDao.selectByPrimaryKey(record);
    }

}
