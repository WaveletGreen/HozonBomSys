package com.connor.hozon.bom.resources.mybatis.bom;

import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzPbomLineRecord;

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
}
