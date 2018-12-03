/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0OptionFamilyDao;
import com.connor.hozon.bom.bomSystem.option.SpecialFeatureOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.cfg0.HzCfg0OptionFamily;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzCfg0OptionFamilyService")
public class HzCfg0OptionFamilyService {
    @Autowired
    HzCfg0OptionFamilyDao hzCfg0OptionFamilyDao;
    private static final Map<String, Object> paramMap = new HashMap<>();
    private static final List<String> paramList = new ArrayList<>();

    static {
        paramList.add(SpecialFeatureOptions.CSNAME.getDesc());
        paramList.add(SpecialFeatureOptions.YQCSNAME.getDesc());
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
     * 主键删除
     *
     * @param uid
     * @return
     */
    public boolean doDeleteByPrimaryKey(String uid) {
        HzCfg0OptionFamily family = new HzCfg0OptionFamily();
        family.setPuid(uid);
        return hzCfg0OptionFamilyDao.deleteByPrimaryKey(family) > 0 ? true : false;
    }

    /**
     * 在当前项目找出带颜色/不带颜色的特性，不能排除2特性值分别带颜色和不带颜色，因此会发生重复，需要进行排重
     *
     * @param projectUid 项目UID
     * @param isColor    1，带颜色的特性；0，不带颜色的特性
     * @return
     */
    public List<HzCfg0OptionFamily> selectForColorBluePrint(String projectUid, Integer isColor) {
        Map<String, Object> param = new HashMap<>();
        param.put("projectUid", projectUid);
        param.put("isColor", isColor);
        return hzCfg0OptionFamilyDao.selectForColorBluePrint(param);
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

    /**
     * 将车身颜色特性提前打首位
     *
     * @param families
     * @param def
     * @return
     */
    private List<String> sortFamiliesCode(List<HzCfg0OptionFamily> families, String def) {
        List<String> result = new ArrayList<>();
        families.stream().filter(f -> f != null).collect(Collectors.toList()).forEach(f -> {
            StringBuilder sb = new StringBuilder();
            if ("车身颜色".equals(f.getpOptionfamilyDesc())) {
                String localTemp = null;
                sb.append(f.getpOptionfamilyDesc() == null ? f.getpOptionfamilyName() : f.getpOptionfamilyDesc());
                sb.append(def);
                sb.append(f.getpOptionfamilyName() == null ? "" : f.getpOptionfamilyName());
                //交换一下位置
                if(!result.isEmpty()){
                    localTemp=result.get(0);
                    result.set(0, sb.toString());
                    result.add(localTemp);
                }
                else{
                    result.add(sb.toString());
                }

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
        List<HzCfg0OptionFamily> families = getFamilies(projectUid, 0, 1);
        List<String> result = new ArrayList<>();
        families.forEach(fn -> result.add(fn.getpOptionfamilyDesc() + def + fn.getpOptionfamilyName()));
        return result;
    }

    /**
     * 获取排序好的列
     *
     * @param projectUid
     * @param def
     * @return
     */
    public List<String> getColumnNew2(String projectUid, String def) {
        List<HzCfg0OptionFamily> families = getFamilies(projectUid, 0, 2);
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

    /**
     * 将项目下的所有特性查询出来，车身颜色和油漆车身总成放在前两位，其余都经过特性NAME排序
     *
     * @param projectUid
     * @param start
     * @param end
     * @return
     */
    public List<HzCfg0OptionFamily> getFamilies(String projectUid, int start, int end) {
        paramMap.put("isIn", false);
        paramMap.put("list", paramList);
        paramMap.put("projectUid", projectUid);
        List<HzCfg0OptionFamily> familiesNew1 = hzCfg0OptionFamilyDao.selectNameByMap(paramMap);
        paramMap.put("isIn", true);
        /**
         * 拆分list
         */
        paramMap.put("list", paramList.subList(start, end));
        List<HzCfg0OptionFamily> familiesNew2 = hzCfg0OptionFamilyDao.selectNameByMap(paramMap);
        familiesNew2.addAll(familiesNew1);
        return familiesNew2;
    }

    /**
     * @param projectUid 项目UID
     * @param names      特性描述是否符合列表中出现的字符串数据
     * @param isIn       当names为null时，是否将不起作用，该标识用于标识当前项目中的names是否会出现再查询条件中，
     *                   如果为true且names!=null，则会搜索出匹配names列表中的所有特性
     *                   如果未false且names！=null，则搜索出不匹配names列表中的所有特性
     * @return
     * @Desc 如果想查询项目中的使用的特性，且特性经过特性代码排序后的数据，将names设为null即可
     */
    public List<HzCfg0OptionFamily> doSelectNameByMap(String projectUid, List<String> names, boolean isIn) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("isIn", isIn);
        params.put("list", names);
        params.put("projectUid", projectUid);
        return hzCfg0OptionFamilyDao.selectNameByMap(params);
    }

    /**
     * 选择性更新项目下的特性
     *
     * @param family 特性对象
     * @return
     */
    public boolean doUpdateByPrimaryKeySelective(HzCfg0OptionFamily family) {
        return hzCfg0OptionFamilyDao.updateByPrimaryKeySelective(family) > 0 ? true : false;
    }

}
