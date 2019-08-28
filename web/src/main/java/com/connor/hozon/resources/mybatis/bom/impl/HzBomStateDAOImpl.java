package com.connor.hozon.resources.mybatis.bom.impl;

import com.connor.hozon.resources.mybatis.bom.HzBomStateDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzBomState;

/**
 * @Author: haozt
 * @Date: 2018/6/14
 * @Description: 已不再使用
 */
@Service("hzBomStateDAO")
@Deprecated
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
