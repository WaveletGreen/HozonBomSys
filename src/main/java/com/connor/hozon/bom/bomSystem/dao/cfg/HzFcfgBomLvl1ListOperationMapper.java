package com.connor.hozon.bom.bomSystem.dao.cfg;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.HzFcfgBomLvl1ListOperation;
import sql.pojo.cfg.HzFcfgBomLvl1ListOperationExample;

public interface HzFcfgBomLvl1ListOperationMapper {
    long countByExample(HzFcfgBomLvl1ListOperationExample example);

    int deleteByExample(HzFcfgBomLvl1ListOperationExample example);

    int deleteByPrimaryKey(String puid);

    int insert(HzFcfgBomLvl1ListOperation record);

    int insertSelective(HzFcfgBomLvl1ListOperation record);

    List<HzFcfgBomLvl1ListOperation> selectByExample(HzFcfgBomLvl1ListOperationExample example);

    HzFcfgBomLvl1ListOperation selectByPrimaryKey(String puid);

    int updateByExampleSelective(@Param("record") HzFcfgBomLvl1ListOperation record, @Param("example") HzFcfgBomLvl1ListOperationExample example);

    int updateByExample(@Param("record") HzFcfgBomLvl1ListOperation record, @Param("example") HzFcfgBomLvl1ListOperationExample example);

    int updateByPrimaryKeySelective(HzFcfgBomLvl1ListOperation record);

    int updateByPrimaryKey(HzFcfgBomLvl1ListOperation record);
}