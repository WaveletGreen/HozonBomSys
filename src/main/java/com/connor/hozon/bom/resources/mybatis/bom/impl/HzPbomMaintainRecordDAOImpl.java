package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.mybatis.bom.HzPbomMaintainRecordDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzPbomLineMaintainRecord;
import sql.pojo.bom.HzPbomMaintainRecord;

import java.util.List;

/**
 * Created by haozt on 2018/5/24
 */
@Service("HzPbomMaintainRecordDAO")
public class HzPbomMaintainRecordDAOImpl extends BaseSQLUtil implements HzPbomMaintainRecordDAO {

    @Override
    public int insertList(List<HzPbomMaintainRecord> list) {
        return super.insert("HzPbomMaintainRecordDAOImpl_insertList",list);
    }

    @Override
    public List<HzPbomLineMaintainRecord> getPBomLineMaintainRecord() {
        return super.findForList("HzPbomMaintainRecordDAOImpl_getPBomLineMaintainRecord",null);
    }

    @Override
    public int update(HzPbomLineMaintainRecord record) {
        return 0;
    }
}
