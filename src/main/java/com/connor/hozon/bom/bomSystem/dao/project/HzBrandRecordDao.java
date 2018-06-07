package com.connor.hozon.bom.bomSystem.dao.project;

import org.apache.ibatis.annotations.Param;
import sql.pojo.project.HzBrandRecord;

import java.util.List;

public interface HzBrandRecordDao {

    int deleteByPrimaryKey(@Param("puid") String puid);

    int insert(HzBrandRecord record);

    HzBrandRecord selectByPrimaryKey(@Param("puid")String puid);

    int updateByPrimaryKey(HzBrandRecord record);

    List<HzBrandRecord> selectAll();
}