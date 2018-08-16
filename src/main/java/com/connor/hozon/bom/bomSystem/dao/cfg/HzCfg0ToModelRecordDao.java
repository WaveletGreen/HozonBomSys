package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0ToModelRecord;

import java.util.List;

@Configuration
public interface HzCfg0ToModelRecordDao {

    int deleteByPrimaryKey(String puid);

    int insert(HzCfg0ToModelRecord record);


    HzCfg0ToModelRecord selectByPrimaryKey(String puid);


    int updateByPrimaryKey(HzCfg0ToModelRecord record);

    /**
     * 批量更新发送到SAP的状态
     *
     * @param list
     * @return
     */
    int setIsSent(@Param("list") List<HzCfg0ToModelRecord> list);
}