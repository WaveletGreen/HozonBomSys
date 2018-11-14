package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.change.HzAuditorChangeRecord;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:
 */
@Service("hzAuditorChangeDAO")
public class HzAuditorChangeDAOImpl extends BaseSQLUtil implements HzAuditorChangeDAO {
    @Override
    public int insert(HzAuditorChangeRecord record) {
        return super.insert("HzAuditorChangeDAOImpl_insert",record);
    }
}
