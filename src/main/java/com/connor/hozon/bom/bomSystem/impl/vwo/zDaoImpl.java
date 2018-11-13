package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.zMapper;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import sql.pojo.cfg.vwo.z;

import java.util.List;

public class zDaoImpl extends BasicDaoImpl<z> implements zMapper {
    public zDaoImpl() {
        clz =zMapper.class;
        clzName=clz.getCanonicalName()+".";
    }

    @Override
    public List<z> query(String x) {
        return baseSQLUtil.executeQuery(new z(),"query");
    }
}
