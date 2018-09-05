package com.connor.hozon.bom.bomSystem.dao.bom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import sql.pojo.bom.HzBomLineRecord;

public interface HzBomLineRecordMapper {

    HzBomLineRecord selectByPrimaryKey(HzBomLineRecord puid);

    List<HzBomLineRecord> selectByProjectPuid(@Param("projectPuid") String projectPuid);

    int updateByPrimaryKey(HzBomLineRecord record);
}