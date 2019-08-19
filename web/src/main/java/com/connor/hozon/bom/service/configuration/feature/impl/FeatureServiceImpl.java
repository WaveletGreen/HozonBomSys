/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.configuration.feature.impl;

import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureDao;
import cn.net.connor.hozon.services.common.option.SpecialFeatureOptions;
import com.connor.hozon.bom.service.configuration.feature.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeature;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
public class FeatureServiceImpl implements FeatureService {
    @Autowired
    HzFeatureDao hzFeatureDao;
    private static final Map<String, Object> paramMap = new ConcurrentHashMap<>();
    private static final List<String> paramList = new ArrayList<>();

    static {
        paramList.add(SpecialFeatureOptions.CSNAME.getDesc());
        paramList.add(SpecialFeatureOptions.YQCSNAME.getDesc());
    }

    /**
     * 将车身颜色特性提前打首位
     *
     * @param families
     * @param def
     * @return
     */
    private List<String> sortFamiliesCode(List<HzFeature> families, String def) {
        List<String> result = new ArrayList<>();
        families.stream().filter(f -> f != null).collect(Collectors.toList()).forEach(f -> {
            StringBuilder sb = new StringBuilder();
            if ("车身颜色".equals(f.getFeatureDesc())) {
                String localTemp = null;
                sb.append(f.getFeatureDesc() == null ? f.getFeatureCode() : f.getFeatureDesc());
                sb.append(def);
                sb.append(f.getFeatureCode() == null ? "" : f.getFeatureCode());
                //交换一下位置
                if (!result.isEmpty()) {
                    localTemp = result.get(0);
                    result.set(0, sb.toString());
                    result.add(localTemp);
                } else {
                    result.add(sb.toString());
                }

            } else {
                sb.append(f.getFeatureDesc() == null ? f.getFeatureCode() : f.getFeatureDesc());
                sb.append(def);
                sb.append(f.getFeatureCode() == null ? "" : f.getFeatureCode());
                result.add(sb.toString());
            }
        });
        return result;
    }


    /**
     * 旧版方法，根据主配置的PUID排序
     *
     * @param mainId
     * @return
     */
    @Override
    public List<HzFeature> selectCfg0OptionFamilyListByProjectPuid(String mainId) {
        return hzFeatureDao.selectByProjectIdWithOrderMainId(mainId);
    }

    /**
     * 新版方法,根据族的PUID排序
     *
     * @param mainId
     * @return
     */
    @Override
    public List<HzFeature> selectCfg0OptionFamilyListByProjectPuid2(String mainId) {
        return hzFeatureDao.selectByProjectIdWithOrderPuid(mainId);
    }

    /**
     * 主键删除
     *
     * @param uid
     * @return
     */
    @Override
    public boolean deleteByPrimaryKey(String uid) {
        HzFeature family = new HzFeature();
        family.setId(uid);
        return hzFeatureDao.deleteByPrimaryKey(family) > 0 ? true : false;
    }

    /**
     * 在当前项目找出带颜色/不带颜色的特性，不能排除2特性值分别带颜色和不带颜色，因此会发生重复，需要进行排重
     *
     * @param projectUid 项目UID
     * @param isColor    1，带颜色的特性；0，不带颜色的特性
     * @return
     */
    @Override
    public List<HzFeature> selectForColorBluePrint(String projectUid, Integer isColor) {
        Map<String, Object> param = new HashMap<>();
        param.put("projectUid", projectUid);
        param.put("isColor", isColor);
        return hzFeatureDao.selectForColorBluePrint(param);
    }

    /**
     * 根据主配置puid获取列信息定义，强制包含<br/>
     *
     * @param mainId 主配置puid
     * @return 一组列信息
     */
    @Override
    public List<String> getColumn(String mainId) {
        List<HzFeature> families = selectCfg0OptionFamilyListByProjectPuid(mainId);
        if (families == null || families.isEmpty())
            return null;
        List<String> result = new ArrayList<>();
        families.stream().filter(f -> f != null).collect(Collectors.toList()).forEach(f -> result.add(f.getFeatureDesc() + "<br/>" + f.getFeatureCode()));
        return result;
    }

    /**
     * 根据主配置puid获取列信息定义，强制包含\t
     *
     * @param mainId 主配置puid
     * @return 一组列信息
     */
    @Override
    public List<String> getColumn2(String mainId) {
        List<HzFeature> families = selectCfg0OptionFamilyListByProjectPuid(mainId);
        if (families == null || families.isEmpty())
            return null;
        List<String> result = new ArrayList<>();
        families.stream().filter(f -> f != null).collect(Collectors.toList()).forEach(f -> result.add(f.getFeatureDesc() + "\t" + f.getFeatureCode()));
        return result;
    }

    /**
     * 自定义的列信息，如果组的id(name)未定义(null)，则取留空;如果组描述(desc)未定义，则取组的id(name)的值
     *
     * @param mainId 主配置的puid
     * @param def    自定义的分隔符
     * @return 一组列信息，包含分隔符
     */
    @Override
    public List<String> getColumnDef(String mainId, String def) {
        List<HzFeature> families = selectCfg0OptionFamilyListByProjectPuid(mainId);
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
    @Override
    public List<String> getColumnDef2(String mainId, String def) {
        List<HzFeature> families = selectCfg0OptionFamilyListByProjectPuid2(mainId);
        if (families == null || families.isEmpty())
            return null;
        return sortFamiliesCode(families, def);
    }


    /**
     * 根据主键获取组对象信息
     *
     * @param family 一个含有puid的组对象
     * @return
     */
    @Override
    public HzFeature selectById(HzFeature family) {
        return hzFeatureDao.selectByPrimaryKey(family);
    }

    @Override
    public HzFeature selectByCodeAndDescWithMain(HzFeature family) {
        return hzFeatureDao.selectByCodeAndDescWithMain(family);
    }

    @Override
    public boolean insert(HzFeature family) {
        return hzFeatureDao.insert(family) > 0 ? true : false;
    }

    @Override
    public List<HzFeature> selectByDesc(String mainUid, String desc) {
        HzFeature family = new HzFeature();
        family.setMainConfigUid(mainUid);
        family.setFeatureDesc(desc);
        return hzFeatureDao.selectByCodeAndDescWithMain2(family);
    }

    /**
     * 获取排序好的列
     *
     * @param projectUid
     * @param def
     * @return
     */
    @Override
    public List<String> getColumnNew(String projectUid, String def) {
        List<HzFeature> families = getFamilies(projectUid, 0, 1);
        List<String> result = new ArrayList<>();
        families.forEach(fn -> result.add(fn.getFeatureDesc() + def + fn.getFeatureCode()));
        return result;
    }

    /**
     * 获取排序好的列
     *
     * @param projectUid
     * @param def
     * @return
     */
    @Override
    public List<String> getColumnNew2(String projectUid, String def) {
        List<HzFeature> families = getFamilies(projectUid, 0, 2);
        List<String> result = new ArrayList<>();
        families.forEach(fn -> result.add(fn.getFeatureDesc() + def + fn.getFeatureCode()));
        return result;
    }

    /**
     * 重新构造列信息
     *
     * @param families
     * @param def
     * @return
     */
    @Override
    public List<String> getColumnNewWithFamilies(List<HzFeature> families, String def) {
        List<String> result = new ArrayList<>();
        families.forEach(fn -> result.add(fn.getFeatureDesc() + def + fn.getFeatureCode()));
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
    @Override
    public List<HzFeature> getFamilies(String projectUid, int start, int end) {
        paramMap.put("isIn", false);
        paramMap.put("list", paramList);
        paramMap.put("projectUid", projectUid);
        List<HzFeature> familiesNew1 = hzFeatureDao.selectNameByMap(paramMap);
        paramMap.put("isIn", true);
        /**
         * 拆分list
         */
        paramMap.put("list", paramList.subList(start, end));
        List<HzFeature> familiesNew2 = hzFeatureDao.selectNameByMap(paramMap);
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
    @Override
    public List<HzFeature> selectNameByMap(String projectUid, List<String> names, boolean isIn) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("isIn", isIn);
        params.put("list", names);
        params.put("projectUid", projectUid);
        return hzFeatureDao.selectNameByMap(params);
    }

    /**
     * 选择性更新项目下的特性
     *
     * @param family 特性对象
     * @return
     */
    @Override
    public boolean updateByPrimaryKeySelective(HzFeature family) {
        return hzFeatureDao.updateByPrimaryKeySelective(family) > 0 ? true : false;
    }

}
