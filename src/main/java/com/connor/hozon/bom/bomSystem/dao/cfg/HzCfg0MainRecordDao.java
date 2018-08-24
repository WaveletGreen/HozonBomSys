package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0MainRecord;

@Configuration
public interface HzCfg0MainRecordDao {

    HzCfg0MainRecord selectByPrimaryKey(@Param("puid") String puid);

    HzCfg0MainRecord selectByProjectPuid(@Param("projectPuid") String projectPuid);

    int insert(HzCfg0MainRecord hzCfg0MainRecord);
}