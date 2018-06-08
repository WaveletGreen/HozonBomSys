package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0OptionFamily;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/23
 * Time: 9:44
 */
@Service("hzCfg0OptionFamilyService")
public class HzCfg0OptionFaamilyService {
    @Autowired
    HzCfg0OptionFamilyDao hzCfg0OptionFamilyDao;

    public List<HzCfg0OptionFamily> doCfg0OptionFamilyListByProjectPuid(String mainId) {
        List<HzCfg0OptionFamily> families = hzCfg0OptionFamilyDao.selectNameByMainId(mainId);
        return families;
    }

    public List<String> doGetColumn(String mainId) {
        List<HzCfg0OptionFamily> families = doCfg0OptionFamilyListByProjectPuid(mainId);
        if(families==null||families.isEmpty())
            return null;
        List<String> result = new ArrayList<>();
        families.stream().filter(f->f!=null).collect(Collectors.toList()).forEach(f -> result.add(f.getpOptionfamilyName()));
        return result;
    }

    public HzCfg0OptionFamily doGetById(HzCfg0OptionFamily family) {
        return hzCfg0OptionFamilyDao.selectByPrimaryKey(family);
    }
}
