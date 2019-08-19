/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.change;

import com.connor.hozon.bom.resources.domain.query.HzChangeDataQuery;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeDataRecord;

import java.util.List;

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

    /**
     * 批量插入记录
     * @param records
     * @return
     */
    int insertList(List<HzChangeDataRecord> records);

    /**
     * 获取变更表单 表名
     * @param query
     * @return
     */
    List<HzChangeDataRecord> getChangeDataTableName(HzChangeDataQuery query);


    List<String> getChangeDataPuids(HzChangeDataQuery query);

    int deleteByOrderIdAndTableName(HzChangeDataRecord hzChangeDataRecord);
}
