package com.connor.hozon.bom.bomSystem.dao.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import sql.pojo.cfg.fullCfg.HzFullCfgMain;

import java.math.BigDecimal;

public interface HzFullCfgMainDao extends BasicDao<HzFullCfgMain>{

    int deleteByPrimaryKey(BigDecimal id);

    BigDecimal insertBackId(HzFullCfgMain record);


    HzFullCfgMain selectByPrimaryKey(BigDecimal id);

    HzFullCfgMain selectByProjectId(String id);
}