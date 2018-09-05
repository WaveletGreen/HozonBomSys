package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0BomLineOfModelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.cfg.HzCfg0BomLineOfModel;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 18:11
 */
@Service("hzCfg0BomLineOfModelDao")
public class HzCfg0BomLineOfModelDaoImpl implements HzCfg0BomLineOfModelDao {

    @Autowired
    BaseSQLUtil baseSQLUtil;

    @Override
    public List<HzCfg0BomLineOfModel> selectByModelMainId(String modelId) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0BomLineOfModel(), modelId, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0BomLineOfModelDao.selectByModelMainId");
    }
}
