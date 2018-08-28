package com.connor.hozon.bom.bomSystem.dao.cfg;

import sql.pojo.cfg.HzFullCfgModel;

import java.math.BigDecimal;
import java.util.List;

public interface HzFullCfgModelDao {

    int deleteByPrimaryKey(BigDecimal id);

    int insert(HzFullCfgModel record);

    int insertSelective(HzFullCfgModel record);

    HzFullCfgModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(HzFullCfgModel record);

    int updateByPrimaryKey(HzFullCfgModel record);

    List<String> selectCfg(String puid);
}