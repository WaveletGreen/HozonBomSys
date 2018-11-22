package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.resources.mybatis.change.HzChangeListDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.change.HzChangeListRecord;

import java.util.List;

@Service("HzChangeListDAO")
public class HzChangeListDAOImpl extends BaseSQLUtil implements HzChangeListDAO {
    @Override
    public List<HzChangeListRecord> findChangeList(String formID) {
        return super.findForList("HzChangeListDAOImpl_findChangeList", formID);
    }
}
