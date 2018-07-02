package com.connor.hozon.bom.resources.mybatis.factory.impl;

import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import sql.BaseSQLUtil;
import sql.pojo.factory.HzFactory;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
public class HzFactoryDAOImpl  extends BaseSQLUtil implements HzFactoryDAO {
    @Override
    public int insert(HzFactory hzFactory) {
        return super.insert("HzFactoryDAOImpl_insert",hzFactory);
    }
}
