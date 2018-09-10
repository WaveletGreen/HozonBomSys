package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.HzFullCfgWithCfg;

import java.math.BigDecimal;
import java.util.List;

public interface HzFullCfgWithCfgDao {

    int deleteByPrimaryKey(Long id);

    int insert(List<HzFullCfgWithCfg> hzFullCfgWithCfgs);

    int insertSelective(HzFullCfgWithCfg record);

    HzFullCfgWithCfg selectByPrimaryKey(Long id);

    List<HzFullCfgWithCfg> selectByMainID(BigDecimal flCfgVersion);

    int updateByPrimaryKeySelective(HzFullCfgWithCfg record);

    int updateByPrimaryKey(HzFullCfgWithCfg record);

    int insertBomLine(List<HzFullCfgWithCfg> hzFullCfgWithCfgs);

    int updateByBomLinePuid(HzFullCfgWithCfg hzFullCfgWithCfg);
}