package com.connor.hozon.bom.bomSystem.dao.project;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.project.HzBrandRecord;

import java.util.List;

public interface HzBrandRecordDao extends BasicDao<HzBrandRecord> {

    int deleteByPrimaryKey(@Param("puid") String puid);

    HzBrandRecord selectByPrimaryKey(@Param("puid") String puid);

    HzBrandRecord selectByBrandCode(@Param("pBrandCode") String pBrandCode);

    List<HzBrandRecord> selectAll();
}