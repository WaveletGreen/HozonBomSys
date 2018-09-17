package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0OptionFamily;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static final Map<String, Object> paramMap = new HashMap<>();
    private static final List<String> paramList = new ArrayList<>();

    static {
        paramList.add("车身颜色");
        paramList.add("油漆车身总成");
    }

    /**
     * 旧版方法，根据主配置的PUID排序
     *
     * @param mainId
     * @return
     */
    public List<HzCfg0OptionFamily> doGetCfg0OptionFamilyListByProjectPuid(String mainId) {
        return hzCfg0OptionFamilyDao.selectNameByMainId(mainId);
    }

    /**
     * 新版方法,根据族的PUID排序
     *
     * @param mainId
     * @return
     */
    public List<HzCfg0OptionFamily> doGetCfg0OptionFamilyListByProjectPuid2(String mainId) {
        return hzCfg0OptionFamilyDao.selectNameByMainId2(mainId);
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
        return sortFamiliesCode(families, def);
    }

    /**
     * 自定义的列信息，如果组的id(name)未定义(null)，则取留空;如果组描述(desc)未定义，则取组的id(name)的值
     *
     * @param mainId 主配置的puid
     * @param def    自定义的分隔符
     * @return 一组列信息，包含分隔符
     */
    public List<String> doGetColumnDef2(String mainId, String def) {
        List<HzCfg0OptionFamily> families = doGetCfg0OptionFamilyListByProjectPuid2(mainId);
        if (families == null || families.isEmpty())
            return null;
        return sortFamiliesCode(families, def);
    }

    private List<String> sortFamiliesCode(List<HzCfg0OptionFamily> families, String def) {
        List<String> result = new ArrayList<>();
        families.stream().filter(f -> f != null).collect(Collectors.toList()).forEach(f -> {
            StringBuilder sb = new StringBuilder();
            if ("车身颜色".equals(f.getpOptionfamilyDesc())) {
                String localTemp = result.get(0);
                sb.append(f.getpOptionfamilyDesc() == null ? f.getpOptionfamilyName() : f.getpOptionfamilyDesc());
                sb.append(def);
                sb.append(f.getpOptionfamilyName() == null ? "" : f.getpOptionfamilyName());
                //交换一下位置
                result.set(0, sb.toString());
                result.add(localTemp);
            } else {
                sb.append(f.getpOptionfamilyDesc() == null ? f.getpOptionfamilyName() : f.getpOptionfamilyDesc());
                sb.append(def);
                sb.append(f.getpOptionfamilyName() == null ? "" : f.getpOptionfamilyName());
                result.add(sb.toString());
            }
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

    public HzCfg0OptionFamily doGetByCodeAndDescWithMain(HzCfg0OptionFamily family) {
        return hzCfg0OptionFamilyDao.selectByCodeAndDescWithMain(family);
    }

    public boolean doInsert(HzCfg0OptionFamily family) {
        return hzCfg0OptionFamilyDao.insert(family) > 0 ? true : false;
    }

    public List<HzCfg0OptionFamily> doSelectByDesc(String mainUid, String desc) {
        HzCfg0OptionFamily family = new HzCfg0OptionFamily();
        family.setpOfCfg0Main(mainUid);
        family.setpOptionfamilyDesc(desc);
        return hzCfg0OptionFamilyDao.selectByCodeAndDescWithMain2(family);
    }

    /**
     * 获取排序好的列
     *
     * @param projectUid
     * @param def
     * @return
     */
    public List<String> getColumnNew(String projectUid, String def) {
        List<HzCfg0OptionFamily> families = getFamilies(projectUid);
        List<String> result = new ArrayList<>();
        families.forEach(fn -> result.add(fn.getpOptionfamilyDesc() + def + fn.getpOptionfamilyName()));
        return result;
    }

    /**
     * 重新构造列信息
     *
     * @param families
     * @param def
     * @return
     */
    public List<String> getColumnNewWithFamilies(List<HzCfg0OptionFamily> families, String def) {
        List<String> result = new ArrayList<>();
        families.forEach(fn -> result.add(fn.getpOptionfamilyDesc() + def + fn.getpOptionfamilyName()));
        return result;
    }

    public List<HzCfg0OptionFamily> getFamilies(String projectUid) {
        paramMap.put("isIn", false);
        paramMap.put("list", paramList);
        paramMap.put("projectUid", projectUid);
        List<HzCfg0OptionFamily> familiesNew1 = hzCfg0OptionFamilyDao.selectNameByMap(paramMap);
        paramMap.put("isIn", true);
        paramMap.put("list", paramList.subList(0, 1));
        List<HzCfg0OptionFamily> familiesNew2 = hzCfg0OptionFamilyDao.selectNameByMap(paramMap);
        familiesNew2.addAll(familiesNew1);
        return familiesNew2;
    }
}
