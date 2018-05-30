package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.bom.HzPbomRecord;

import java.util.List;

/**
 * Created by haozt on 2018/5/25
 */
@Service("HzPbomRecordDAO")
public class HzPbomRecordDAOImpl extends BaseSQLUtil implements HzPbomRecordDAO {


    @Override
    public List<HzPbomLineRecord> getPbomRecord() {

        return super.findForList("HzPbomRecordDAOImpl_getPbomRecord",null);
    }

    @Override
    public List<HzPbomLineRecord> searchPbomLineDetail(HzBomLineRecord record) {

       return super.findForList("HzPbomRecordDAOImpl_searchPbomLineDetail",record);

    }

    @Override
    public int insert(HzPbomRecord record) {
        return super.insert("HzPbomRecordDAOImpl_insert",record);
    }

    @Override
    public int update(HzPbomRecord record) {
        return super.update("HzPbomRecordDAOImpl_update",record);
    }

    @Override
    public int deleteByForeignId(String ePuid) {
        return super.delete("HzPbomRecordDAOImpl_deleteByForeignId",ePuid);
    }
}
