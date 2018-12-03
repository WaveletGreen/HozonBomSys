package com.connor.hozon.bom.resources.mybatis.factory.impl;

import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.factory.HzFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
@Service("hzFactoryDAO")
public class HzFactoryDAOImpl extends BaseSQLUtil implements HzFactoryDAO {
    @Override
    public int insert(HzFactory hzFactory) {
        return super.insert("HzFactoryDAOImpl_insert",hzFactory);
    }

    @Override
    public HzFactory findFactory(String puid,String factoryCode) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        map.put("factoryCode",factoryCode);
        return (HzFactory) super.findForObject("HzFactoryDAOImpl_selectFactoryById",map);
    }

}
