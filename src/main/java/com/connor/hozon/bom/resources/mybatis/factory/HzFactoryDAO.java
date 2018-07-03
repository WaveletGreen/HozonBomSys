package com.connor.hozon.bom.resources.mybatis.factory;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzWorkByPageQuery;
import sql.pojo.factory.HzFactory;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
public interface HzFactoryDAO {
    /**
     * 新增一条记录
     * @param hzFactory
     * @return
     */
    int insert(HzFactory hzFactory);

    HzFactory findFactory(String puid,String factoryCode);
}
