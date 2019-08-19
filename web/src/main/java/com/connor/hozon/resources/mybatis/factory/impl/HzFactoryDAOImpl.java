package com.connor.hozon.resources.mybatis.factory.impl;

import cn.net.connor.hozon.common.util.UUIDHelper;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.resources.mybatis.factory.HzFactoryDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.main.HzFactory;

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
    public String insert() {
        HzFactory f = findFactory("","1001");
        if(f == null){
            HzFactory factory = new HzFactory();
            String puid = UUIDHelper.generateUid();
            factory.setPuid(puid);
            factory.setpFactoryCode("1001");
            factory.setpCreateName(UserInfo.getUser().getUsername());
            factory.setpUpdateName(UserInfo.getUser().getUsername());
            int i =  super.insert("HzFactoryDAOImpl_insert",factory);
            return i>0?puid:"";
        }
        return f.getPuid();
    }

    @Override
    public String insert(String factoryCode) {
        HzFactory factory = new HzFactory();
        String puid = UUIDHelper.generateUid();
        factory.setPuid(puid);
        factory.setpFactoryCode(factoryCode);
        factory.setpCreateName(UserInfo.getUser().getUsername());
        factory.setpUpdateName(UserInfo.getUser().getUsername());
        int i =  super.insert("HzFactoryDAOImpl_insert",factory);
        return i>0?puid:"";
    }

    @Override
    public HzFactory findFactory(String puid,String factoryCode) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        map.put("factoryCode",factoryCode);
        return (HzFactory) super.findForObject("HzFactoryDAOImpl_selectFactoryById",map);
    }

}
