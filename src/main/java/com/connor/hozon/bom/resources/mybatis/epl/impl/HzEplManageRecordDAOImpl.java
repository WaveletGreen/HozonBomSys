package com.connor.hozon.bom.resources.mybatis.epl.impl;

import com.connor.hozon.bom.resources.mybatis.epl.HzEplMangeRecordDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;

/**
 * Created by haozt on 2018/06/05
 */
@Service("HzEplMangeRecordDAO")
public class HzEplManageRecordDAOImpl extends BaseSQLUtil implements HzEplMangeRecordDAO {
    @Override
    public List<HzEPLManageRecord> getHzEplManageRecord() {
        return super.findForList("HzEplManageRecordDAOImpl_getHzEplManageRecord",null);
    }
}
