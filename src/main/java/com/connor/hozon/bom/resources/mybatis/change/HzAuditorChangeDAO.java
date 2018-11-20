package com.connor.hozon.bom.resources.mybatis.change;

import sql.pojo.change.HzAuditorChangeRecord;

import java.util.List;

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

    //待办事项
    List<HzAuditorChangeRecord> findAuditorList(HzAuditorChangeRecord record);
    //已处理事项
    List<HzAuditorChangeRecord> findAuditorList2(HzAuditorChangeRecord record);
}
