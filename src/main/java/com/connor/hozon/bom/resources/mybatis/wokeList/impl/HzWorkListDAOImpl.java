package com.connor.hozon.bom.resources.mybatis.wokeList.impl;

import com.connor.hozon.bom.resources.mybatis.wokeList.HzWorkListDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.workList.HzWorkListRecord;

import java.util.List;
import java.util.Map;

@Service("HzWorkListDAO")
public class HzWorkListDAOImpl extends BaseSQLUtil implements HzWorkListDAO {

    @Override
    public int insert(HzWorkListRecord record) {
        return super.insert("HzWorkListDAOImpl_insert",record);
    }

    @Override
    public int update(HzWorkListRecord record) {
        return super.update("HzWorkListDAOImpl_update",record);
    }

    @Override
    public List<HzWorkListRecord> findWorkList(Map<String, Object> map) {
        return super.findForList("HzWorkListDAOImpl_findWorkList",map);
    }
}
