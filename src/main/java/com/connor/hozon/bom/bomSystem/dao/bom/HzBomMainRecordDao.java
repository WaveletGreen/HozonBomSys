package com.connor.hozon.bom.bomSystem.dao.bom;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.bom.HZBomMainRecord;

@Configuration
public interface HzBomMainRecordDao extends BasicDao<HZBomMainRecord> {
    /**
     * 根据项目查找BOM主配置
     *
     * @param projectUid 项目UID
     * @return 返回当前项目下的主配置
     */
    HZBomMainRecord selectByProjectPuid(@Param("projectUid") String projectUid);
}