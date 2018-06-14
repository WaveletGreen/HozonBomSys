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
public class HzCfg0OptionFamilyService {
    @Autowired
    HzCfg0OptionFamilyDao hzCfg0OptionFamilyDao;

    public List<HzCfg0OptionFamily> doCfg0OptionFamilyListByProjectPuid(String mainId) {
        return hzCfg0OptionFamilyDao.selectNameByMainId(mainId);
    }

    public List<String> doGetColumn(String mainId) {
        List<HzCfg0OptionFamily> families = doCfg0OptionFamilyListByProjectPuid(mainId);
        if (families == null || families.isEmpty())
            return null;
        List<String> result = new ArrayList<>();
        families.stream().filter(f -> f != null).collect(Collectors.toList()).forEach(f -> result.add(f.getpOptionfamilyDesc() + "<br/>" + f.getpOptionfamilyName()));
        return result;
    }

    public List<String> doGetColumn2(String mainId) {
        List<HzCfg0OptionFamily> families = doCfg0OptionFamilyListByProjectPuid(mainId);
        if (families == null || families.isEmpty())
            return null;
        List<String> result = new ArrayList<>();
        families.stream().filter(f -> f != null).collect(Collectors.toList()).forEach(f -> result.add(f.getpOptionfamilyDesc() + "\t" + f.getpOptionfamilyName()));
        return result;
    }

    public List<String> doGetColumnDef(String mainId, String def) {
        List<HzCfg0OptionFamily> families = doCfg0OptionFamilyListByProjectPuid(mainId);
        if (families == null || families.isEmpty())
            return null;
        List<String> result = new ArrayList<>();
        families.stream().filter(f -> f != null).collect(Collectors.toList()).forEach(f -> result.add(f.getpOptionfamilyDesc() + def + f.getpOptionfamilyName()));
        return result;
    }

    public HzCfg0OptionFamily doGetById(HzCfg0OptionFamily family) {
        return hzCfg0OptionFamilyDao.selectByPrimaryKey(family);
    }
}
