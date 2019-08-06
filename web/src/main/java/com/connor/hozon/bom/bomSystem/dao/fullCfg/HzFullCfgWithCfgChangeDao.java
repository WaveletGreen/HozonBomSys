package com.connor.hozon.bom.bomSystem.dao.fullCfg;


import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgWithCfgChange;

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