package com.connor.hozon.bom.bomSystem.dao.cfg;

import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.cfg.HzFullCfgModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface HzFullCfgModelDao {

    int deleteByPrimaryKey(BigDecimal id);

    int insert(HzFullCfgModel record);

    int insertSelective(HzFullCfgModel record);

    HzFullCfgModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(HzFullCfgModel record);

    int updateByPrimaryKey(HzFullCfgModel record);

    List<String> selectCfg(String puid);

    void insertCfgs(List<HzFullCfgModel> cfgs);

    List<HzFullCfgModel> selectByMainPuid(BigDecimal mainPuid);

}