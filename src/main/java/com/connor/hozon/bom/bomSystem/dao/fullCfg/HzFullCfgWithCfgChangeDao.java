package com.connor.hozon.bom.bomSystem.dao.fullCfg;


import sql.pojo.cfg.fullCfg.HzFullCfgWithCfg;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfgChange;

import java.util.List;

public interface HzFullCfgWithCfgChangeDao {
    int deleteByPrimaryKey(Long id);

    int insert(HzFullCfgWithCfgChange record);

    int insertSelective(HzFullCfgWithCfgChange record);

    HzFullCfgWithCfgChange selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HzFullCfgWithCfgChange record);

    int updateByPrimaryKey(HzFullCfgWithCfgChange record);

    int insertList(List<HzFullCfgWithCfgChange> hzFullCfgWithCfgChanges);


    List<HzFullCfgWithCfgChange> selectByMainId(Integer id);
}