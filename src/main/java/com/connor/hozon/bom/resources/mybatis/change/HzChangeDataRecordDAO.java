package com.connor.hozon.bom.resources.mybatis.change;

import sql.pojo.change.HzChangeDataRecord;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description: 变更表单数据记录
 */
public interface HzChangeDataRecordDAO {
    /**
     * 新增一条记录
     * @param record
     * @return
     */
    int insert(HzChangeDataRecord record);
}
