package com.connor.hozon.bom.bomSystem.dao.cfg;

import sql.pojo.cfg.HzFullCfgMain;

import java.math.BigDecimal;

public interface HzFullCfgMainDao {

    int deleteByPrimaryKey(BigDecimal id);

    BigDecimal insert(HzFullCfgMain record);

    int insertSelective(HzFullCfgMain record);

    HzFullCfgMain selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(HzFullCfgMain record);

    int updateByPrimaryKey(HzFullCfgMain record);

    HzFullCfgMain selectByProjectId(String id);
}