package com.connor.hozon.bom.bomSystem.dao.bom;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.bom.HZBomMainRecord;

@Configuration
public interface HzBomMainRecordDao {
    HZBomMainRecord selectByProjectPuid(@Param("projectPuid") String projectPuid);

    int insert(HZBomMainRecord hzBomMainRecord);
}