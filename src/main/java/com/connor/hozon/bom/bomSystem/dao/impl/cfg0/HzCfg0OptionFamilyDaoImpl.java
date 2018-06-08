package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0OptionFamily;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/23
 * Time: 9:23
 */
@Service("hzCfg0OptionFamilyDao")
public class HzCfg0OptionFamilyDaoImpl implements HzCfg0OptionFamilyDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public HzCfg0OptionFamily selectByPrimaryKey(HzCfg0OptionFamily family) {
        return baseSQLUtil.executeQueryById(family, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao.selectByPrimaryKey");
    }

    @Override
    public List<HzCfg0OptionFamily> selectNameByMainId(String mainId) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0OptionFamily(),mainId, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao.selectNameByMainId");
    }
}
