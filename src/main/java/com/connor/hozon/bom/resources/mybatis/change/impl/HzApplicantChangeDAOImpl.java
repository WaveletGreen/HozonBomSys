package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.resources.mybatis.change.HzApplicantChangeDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.change.HzApplicantChangeRecord;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:
 */
@Service("hzApplicantChangeDAO")
public class HzApplicantChangeDAOImpl extends BaseSQLUtil implements HzApplicantChangeDAO {
    @Override
    public int insert(HzApplicantChangeRecord record) {
        return super.insert("HzApplicantChangeDAOImpl_insert",record);
    }
}
