package com.connor.hozon.bom.bomSystem.dao.fullCfg;


import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgModelChange;

import java.util.List;

@Configuration
public interface HzFullCfgModelChangeDao {
    int insert(HzFullCfgModelChange record);

    int insertSelective(HzFullCfgModelChange record);

    int insertList(List<HzFullCfgModelChange> hzFullCfgModelChanges);
}