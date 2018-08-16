package com.connor.hozon.bom.bomSystem.service.iservice.cfg;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0OfBomLineRecord;

import java.util.List;

@Configuration
public interface IHzCfg0OfBomLineService {

    /**
     * 主键删除
     *
     * @param puid
     * @return
     */
    boolean doDeleteByPrimaryKey(String puid);

    /**
     * 插入单条数据
     *
     * @param record
     * @return
     */
    boolean doInsert(HzCfg0OfBomLineRecord record);

    /**
     * 根据主键搜索
     *
     * @param puid
     * @return
     */
    HzCfg0OfBomLineRecord doSelectByPrimaryKey(String puid);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    boolean doUpdateByPrimaryKey(HzCfg0OfBomLineRecord record);

    /**
     * 批量插入
     *
     * @param records
     * @return
     */
    boolean doInsertByBatch(List<HzCfg0OfBomLineRecord> records);

    /**
     * 根据项目ID和Bomline UID查找一条配置+BOMLine关联数据
     *
     * @param projectUid 项目UID
     * @param bomLineUid BOM行UID
     * @return
     */
    HzCfg0OfBomLineRecord doSelectByBLUidAndPrjUid(String projectUid, String bomLineUid);
}
