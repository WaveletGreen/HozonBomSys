package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.HzFullCfgWithCfg;

import java.util.List;

public interface HzFullCfgWithCfgDao {

    int deleteByPrimaryKey(Long id);

    int insert(HzFullCfgWithCfg record);

    int insertSelective(HzFullCfgWithCfg record);

    HzFullCfgWithCfg selectByPrimaryKey(Long id);

    List<HzFullCfgWithCfg> selectByMainID(Long flCfgVersion);

    int updateByPrimaryKeySelective(HzFullCfgWithCfg record);

    int updateByPrimaryKey(HzFullCfgWithCfg record);
}