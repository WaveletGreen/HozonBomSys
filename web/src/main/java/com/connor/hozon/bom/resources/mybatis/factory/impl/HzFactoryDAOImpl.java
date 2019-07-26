package com.connor.hozon.bom.resources.mybatis.factory.impl;

import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.factory.HzFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
            factory.setpCreateName(UserInfo.getUser().getUserName());
            factory.setpUpdateName(UserInfo.getUser().getUserName());
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
        factory.setpCreateName(UserInfo.getUser().getUserName());
        factory.setpUpdateName(UserInfo.getUser().getUserName());
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