package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.resources.mybatis.change.HzChangeListDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.change.HzChangeListRecord;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/11/22
 * @Description:
 */
@Service("hzChangeListDAO")
public class HzChangeListDAOImpl extends BaseSQLUtil implements HzChangeListDAO {
    @Override
    public List<HzChangeListRecord> findItemListByFormId(String fromId) {
        return super.findForList("HzChangeListDAOImpl_findItemListByFormId",fromId);
    }
}
