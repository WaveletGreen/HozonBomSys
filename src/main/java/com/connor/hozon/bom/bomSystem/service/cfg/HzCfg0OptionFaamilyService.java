package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0OptionFamily;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/23
 * Time: 9:44
 */
@Service("hzCfg0OptionFamilyService")
public class HzCfg0OptionFaamilyService {
    @Autowired
    HzCfg0OptionFamilyDao hzCfg0OptionFamilyDao;

    public List<String> doGetColumn(String mainId) {
        return hzCfg0OptionFamilyDao.selectNameByMainId(mainId);
    }

    public HzCfg0OptionFamily doGetById(HzCfg0OptionFamily family) {
        return hzCfg0OptionFamilyDao.selectByPrimaryKey(family);
    }
}
