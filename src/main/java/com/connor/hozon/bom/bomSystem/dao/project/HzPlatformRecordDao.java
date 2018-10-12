package com.connor.hozon.bom.bomSystem.dao.project;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.project.HzPlatformRecord;

import java.util.List;

public interface HzPlatformRecordDao extends BasicDao<HzPlatformRecord> {

    int deleteByPrimaryKey(@Param("puid") String puid);

    HzPlatformRecord selectByPrimaryKey(@Param("puid") String puid);

    List<HzPlatformRecord> selectAll();

    HzPlatformRecord selectByPlatformCode(@Param("pPlatformCode") String pPlatformCode);
}