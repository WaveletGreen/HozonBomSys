package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.change.HzChangeOrderRecord;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description:
 */
@Service("hzChangeOrderDAO")
public class HzChangeOrderDAOImpl extends BaseSQLUtil implements HzChangeOrderDAO {
    @Override
    public int insert(HzChangeOrderRecord record) {
        return super.insert("HzChangeOrderDAOImpl_insert",record);
    }

    @Override
    public int update(HzChangeOrderRecord record) {
        return super.update("HzChangeOrderDAOImpl_update",record);
    }

    @Override
    public int deleteById(Long id) {

        return super.delete("HzChangeOrderDAOImpl_deleteById",id);
    }
}
