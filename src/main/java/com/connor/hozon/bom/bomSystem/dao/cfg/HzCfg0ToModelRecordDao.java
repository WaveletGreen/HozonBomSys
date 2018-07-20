package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0ToModelRecord;

@Configuration
public interface HzCfg0ToModelRecordDao {

    int deleteByPrimaryKey(String puid);

    int insert(HzCfg0ToModelRecord record);


    HzCfg0ToModelRecord selectByPrimaryKey(String puid);


    int updateByPrimaryKey(HzCfg0ToModelRecord record);
}