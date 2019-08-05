package com.connor.hozon.bom.bomSystem.dao.fullCfg;


import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgModelChange;

import java.util.List;

public interface HzFullCfgModelChangeDao {
    int insert(HzFullCfgModelChange record);

    int insertSelective(HzFullCfgModelChange record);

    int insertList(List<HzFullCfgModelChange> hzFullCfgModelChanges);

    List<HzFullCfgModelChange> selectByMainId(Integer id);
}