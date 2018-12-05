package com.connor.hozon.bom.resources.mybatis.factory;

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

    /**
     * 初始化默认工厂 代号 1001
     * @return 新增记录puid
     */
    String insert();

    /**
     * 新增工厂
     * @param factoryCode 工厂代码
     * @return
     */
    String insert(String factoryCode);

    HzFactory findFactory(String puid,String factoryCode);
}
