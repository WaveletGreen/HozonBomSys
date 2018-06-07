package com.connor.hozon.bom.bomSystem.dao.project;

import org.apache.ibatis.annotations.Param;
import sql.pojo.project.HzPlatformRecord;

import java.util.List;

public interface HzPlatformRecordDao {

    int deleteByPrimaryKey(@Param("puid") String puid);

    int insert(HzPlatformRecord record);


    HzPlatformRecord selectByPrimaryKey(@Param("puid")String puid);

    int updateByPrimaryKey(HzPlatformRecord record);

    List<HzPlatformRecord> selectAll();
}