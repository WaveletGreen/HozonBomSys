package com.connor.hozon.bom.bomSystem.dao.fullCfg;


import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgModelChange;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfg;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfgChange;

import java.math.BigDecimal;
import java.util.List;

@Configuration
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