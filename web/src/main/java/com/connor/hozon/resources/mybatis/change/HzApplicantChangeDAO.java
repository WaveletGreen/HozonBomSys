package com.connor.hozon.resources.mybatis.change;

import cn.net.connor.hozon.dao.pojo.change.change.HzApplicantChangeRecord;

import java.util.List;

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

    int update(HzApplicantChangeRecord record);
    //我的申请
    List<HzApplicantChangeRecord> findApplicantionList(HzApplicantChangeRecord record);
}
