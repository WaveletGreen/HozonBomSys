package com.connor.hozon.bom.resources.mybatis.change;

import sql.pojo.change.HzAuditorChangeRecord;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:审核人审核记录
 */
public interface HzAuditorChangeDAO {
    /**
     * 新增一条记录
     * @param record
     * @return
     */
    int insert(HzAuditorChangeRecord record);
}
