package com.connor.hozon.bom.bomSystem.dao.cfg;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0MainRecord;

@Configuration
public interface HzCfg0MainRecordDao extends BasicDao<HzCfg0MainRecord>{

    HzCfg0MainRecord selectByPrimaryKey(@Param("puid") String puid);

    HzCfg0MainRecord selectByProjectPuid(@Param("projectPuid") String projectPuid);

}