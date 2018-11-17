package com.connor.hozon.bom.bomSystem.dao.fullCfg;


import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgMainChange;

@Configuration
public interface HzFullCfgMainChangeDao {
    int insert(HzFullCfgMainChange record);

    int insertSelective(HzFullCfgMainChange record);
}
