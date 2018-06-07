package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.HzCfg0Record;

import java.util.List;


public interface HzCfg0RecordDao {

    int deleteByPrimaryKey(@Param("puid")String puid);

    int insert(HzCfg0Record record);

    List<HzCfg0Record> selectByProjectPuid(@Param("projectPuid") String projectPuid);

    HzCfg0Record selectByPrimaryKey(@Param("puid")String puid);

    int updateByPrimaryKey(HzCfg0Record record);
}