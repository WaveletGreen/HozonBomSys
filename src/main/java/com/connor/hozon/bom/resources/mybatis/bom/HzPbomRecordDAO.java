package com.connor.hozon.bom.resources.mybatis.bom;

import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.bom.HzPbomRecord;

import java.util.List;

/**
 * Created by haozt on 2018/5/25
 */
public interface HzPbomRecordDAO {
    /**
     * 获取PBOM管理维护信息
     * @return
     */
    List<HzPbomLineRecord> getPbomRecord();

    /**
     * 按条件搜索Pbom管理维护信息
     * @return
     */
    List<HzPbomLineRecord> searchPbomLineDetail(HzBomLineRecord record);

    /**
     * 插入 PBOM管理信息
     * @param record
     * @return
     */
    int insert(HzPbomRecord record);

    /**
     * 编辑 PBOM管理信息
     * @param record
     * @return
     */
    int update(HzPbomRecord record);

    /**
     * 删除PBOM管理 通过外键删除
     * @param ePuid
     * @return
     */
    int deleteByForeignId(String ePuid);
}
