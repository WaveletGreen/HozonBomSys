package com.connor.hozon.bom.bomSystem.dao.cfg;

import sql.pojo.cfg.HzFullCfgModel;

import java.math.BigDecimal;

public interface HzFullCfgModelDao {

    int deleteByPrimaryKey(BigDecimal id);

    int insert(HzFullCfgModel record);

    int insertSelective(HzFullCfgModel record);

    HzFullCfgModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(HzFullCfgModel record);

    int updateByPrimaryKey(HzFullCfgModel record);
}