package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0ModelRecord;

@Configuration
public interface HzCfg0ModelRecordDao {
    HzCfg0ModelRecord selectByPrimaryKey(HzCfg0ModelRecord record);
}