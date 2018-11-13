package com.connor.hozon.bom.bomSystem.dao.fullCfg;


import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfgChange;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public interface HzFullCfgWithCfgChangeDao {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(HzFullCfgWithCfgChange record);

    int insertSelective(HzFullCfgWithCfgChange record);

    HzFullCfgWithCfgChange selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(HzFullCfgWithCfgChange record);

    int updateByPrimaryKey(HzFullCfgWithCfgChange record);

    int insertList(List<HzFullCfgWithCfgChange> hzFullCfgWithCfgChanges);
}