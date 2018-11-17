package com.connor.hozon.bom.resources.mybatis.change;

import sql.pojo.change.HzApplicantChangeRecord;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:申请人申请记录
 */
public interface HzApplicantChangeDAO {
    /**
     * 新增一条记录
     * @param record
     * @return
     */
    int insert(HzApplicantChangeRecord record);
}
