package com.connor.hozon.bom.bomSystem.dao.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzMBomToERPDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.pojo.bom.HzMBomToERPBean;
import sql.IBaseSQLUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class HzMBomToERPDaoImpl implements HzMBomToERPDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public List<HzMBomToERPBean> selectByBatch(String projectUid, List<String> list) {
        Map<String, Object> _param = new HashMap<>();
        _param.put("projectUid", projectUid);
        _param.put("list", list);
        return baseSQLUtil.executeQueryByPass(new HzMBomToERPBean(), _param, "com.connor.hozon.bom.bomSystem.dao.bom.HzMBomToERPDao.selectByBatch");
    }
}
