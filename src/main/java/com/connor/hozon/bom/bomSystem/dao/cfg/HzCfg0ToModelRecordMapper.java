package com.connor.hozon.bom.bomSystem.dao.cfg;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.HzCfg0ToModelRecord;

public interface HzCfg0ToModelRecordMapper {

    int deleteByPrimaryKey(String puid);

    int insert(HzCfg0ToModelRecord record);


    HzCfg0ToModelRecord selectByPrimaryKey(String puid);

    int updateByPrimaryKey(HzCfg0ToModelRecord record);
}