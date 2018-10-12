package com.connor.hozon.bom.bomSystem.dao.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzCfg0OfBomLineRecord;

import java.util.List;

@Configuration
public interface HzCfg0OfBomLineRecordDao extends BasicDao<HzCfg0OfBomLineRecord> {
    /**
     * 主键删除
     *
     * @param puid
     * @return
     */
    int deleteByPrimaryKey(String puid);


    /**
     * 根据主键搜索
     *
     * @param puid
     * @return
     */
    HzCfg0OfBomLineRecord selectByPrimaryKey(String puid);

    /**
     * 根据项目ID和Bomline UID查找一条配置+BOMLine关联数据
     *
     * @return
     */
    HzCfg0OfBomLineRecord selectByBLUidAndPrjUid(HzCfg0OfBomLineRecord record);


    /**
     * 批量插入
     *
     * @param records
     * @return
     */
    int insertByBatch(List<HzCfg0OfBomLineRecord> records);
}