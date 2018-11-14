package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.change.HzChangeDataRecord;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:
 */
@Service("hzChangeDataRecordDAO")
public class HzChangeDataRecordDAOImpl extends BaseSQLUtil implements HzChangeDataRecordDAO {
    @Override
    public int insert(HzChangeDataRecord record) {
        return super.insert("HzChangeDataRecordDAOImpl_insert",record);
    }
}
