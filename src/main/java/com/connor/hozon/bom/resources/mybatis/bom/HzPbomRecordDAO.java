package com.connor.hozon.bom.resources.mybatis.bom;

import sql.pojo.bom.HzPbomLineRecord;

import java.util.List;

/**
 * Created by haozt on 2018/5/25
 */
public interface HzPbomRecordDAO {
    List<HzPbomLineRecord> getPbomRecord();
}
