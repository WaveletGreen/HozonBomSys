package com.connor.hozon.bom.resources.mybatis.bom;

import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzPbomLineMaintainRecord;
import sql.pojo.bom.HzPbomMaintainRecord;

import java.util.List;

/**
 * Created by haozt on 2018/5/24
 */
public interface HzPbomMaintainRecordDAO {
    /**
     * 批量插入PBOM维护信息
     * @return
     */
    int insertList(List<HzPbomMaintainRecord> records);

    /**
     * 查询PBOM信息 PBOM维护信息
     */
    List<HzPbomLineMaintainRecord> getPBomLineMaintainRecord();

    /**
     * 更新PBOM维护信息
     * @param record
     * @return
     */
    int update(HzPbomMaintainRecord record);

    /**
     * 删除PBOM维护 通过外键删除
     * @param pPuid
     * @return
     */
    int deleteByForeignId(String pPuid);

    /**
     * 按条件搜索PBOM在线维护信息
     * @return
     */
    List<HzPbomLineMaintainRecord> searchPbomMaintainDetail(HzBomLineRecord record);
}
