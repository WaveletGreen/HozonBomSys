package com.connor.hozon.bom.bomSystem.dao.fullCfg;


import sql.pojo.cfg.fullCfg.HzFullCfgMainChange;

public interface HzFullCfgMainChangeDao {
    int insert(HzFullCfgMainChange record);

    int insertSelective(HzFullCfgMainChange record);

    HzFullCfgMainChange selectByChangeId(Integer orderChangeId);

    HzFullCfgMainChange selectLast(Long srcMainId);
}
