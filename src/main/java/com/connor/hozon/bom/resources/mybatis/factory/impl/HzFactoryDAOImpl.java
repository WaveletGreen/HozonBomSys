package com.connor.hozon.bom.resources.mybatis.factory.impl;

import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.query.HzWorkByPageQuery;
import org.mapstruct.ap.shaded.freemarker.ext.beans.HashAdapter;
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
@Service("HzFactoryDAO")
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
