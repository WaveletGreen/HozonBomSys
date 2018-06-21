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

    public List<HzCfg0OptionFamily> doGetCfg0OptionFamilyListByProjectPuid(String mainId) {
        return hzCfg0OptionFamilyDao.selectNameByMainId(mainId);
    }

    /**
     * 根据主配置puid获取列信息定义，强制包含<br/>
     *
     * @param mainId 主配置puid
     * @return 一组列信息
     */
    public List<String> doGetColumn(String mainId) {
        List<HzCfg0OptionFamily> families = doGetCfg0OptionFamilyListByProjectPuid(mainId);
        if (families == null || families.isEmpty())
            return null;
        List<String> result = new ArrayList<>();
        families.stream().filter(f -> f != null).collect(Collectors.toList()).forEach(f -> result.add(f.getpOptionfamilyDesc() + "<br/>" + f.getpOptionfamilyName()));
        return result;
    }

    /**
     * 根据主配置puid获取列信息定义，强制包含\t
     *
     * @param mainId 主配置puid
     * @return 一组列信息
     */
    public List<String> doGetColumn2(String mainId) {
        List<HzCfg0OptionFamily> families = doGetCfg0OptionFamilyListByProjectPuid(mainId);
        if (families == null || families.isEmpty())
            return null;
        List<String> result = new ArrayList<>();
        families.stream().filter(f -> f != null).collect(Collectors.toList()).forEach(f -> result.add(f.getpOptionfamilyDesc() + "\t" + f.getpOptionfamilyName()));
        return result;
    }

    /**
     * 自定义的列信息，如果组的id(name)未定义(null)，则取留空;如果组描述(desc)未定义，则取组的id(name)的值
     *
     * @param mainId 主配置的puid
     * @param def    自定义的分隔符
     * @return 一组列信息，包含分隔符
     */
    public List<String> doGetColumnDef(String mainId, String def) {
        List<HzCfg0OptionFamily> families = doGetCfg0OptionFamilyListByProjectPuid(mainId);
        if (families == null || families.isEmpty())
            return null;
        List<String> result = new ArrayList<>();

        families.stream().filter(f -> f != null).collect(Collectors.toList()).forEach(f -> {
            StringBuilder sb = new StringBuilder();
            sb.append(f.getpOptionfamilyDesc() == null ? f.getpOptionfamilyName() : f.getpOptionfamilyDesc());
            sb.append(def);
            sb.append(f.getpOptionfamilyName() == null ? "" : f.getpOptionfamilyName());
            result.add(sb.toString());
        });
        return result;
    }

    /**
     * 根据主键获取组对象信息
     *
     * @param family 一个含有puid的组对象
     * @return
     */
    public HzCfg0OptionFamily doGetById(HzCfg0OptionFamily family) {
        return hzCfg0OptionFamilyDao.selectByPrimaryKey(family);
    }
}
