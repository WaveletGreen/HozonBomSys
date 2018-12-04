package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.mybatis.bom.HzBomStateDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzBomState;

/**
 * @Author: haozt
 * @Date: 2018/6/14
 * @Description:
 */
@Service("hzBomStateDAO")
public class HzBomStateDAOImpl extends BaseSQLUtil implements HzBomStateDAO {

    @Override
    public int insert(HzBomState hzBomState) {
        return super.insert("HzBomStateDAOImpl_insert",hzBomState);
    }
    @Override
    public int update(HzBomState hzBomState) {
        return super.update("HzBomStateDAOImpl_update",hzBomState);
    }

    @Override
    public HzBomState findStateById(String puid) {
        return (HzBomState) super.findForObject("HzBomStateDAOImpl_findStateById",puid);
    }
}
