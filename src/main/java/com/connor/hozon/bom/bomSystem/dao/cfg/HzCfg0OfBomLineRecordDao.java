package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0OfBomLineRecord;

import java.util.List;

@Configuration
public interface HzCfg0OfBomLineRecordDao {
    /**
     * 主键删除
     * @param puid
     * @return
     */
    int deleteByPrimaryKey(String puid);

    /**
     * 插入单条数据
     * @param record
     * @return
     */
    int insert(HzCfg0OfBomLineRecord record);

    /**
     * 根据主键搜索
     * @param puid
     * @return
     */
    HzCfg0OfBomLineRecord selectByPrimaryKey(String puid);

    /**
     * 根据主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzCfg0OfBomLineRecord record);

    /**
     * 批量插入
     * @param records
     * @return
     */
    int insertByBatch(List<HzCfg0OfBomLineRecord> records);
}