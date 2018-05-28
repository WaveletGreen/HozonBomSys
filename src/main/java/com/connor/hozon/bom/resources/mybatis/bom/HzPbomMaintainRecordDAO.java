package com.connor.hozon.bom.resources.mybatis.bom;

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

    int update(HzPbomLineMaintainRecord record);
}
