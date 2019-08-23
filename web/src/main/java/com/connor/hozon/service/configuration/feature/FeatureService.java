/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.configuration.feature;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeature;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 13:43
 * @Modified By:
 */
public interface FeatureService {
    /**
     * 旧版方法，根据主配置的PUID排序
     *
     * @param mainId
     * @return
     */
    public List<HzFeature> selectCfg0OptionFamilyListByProjectPuid(String mainId);

    /**
     * 新版方法,根据族的PUID排序
     *
     * @param mainId
     * @return
     */
    public List<HzFeature> selectCfg0OptionFamilyListByProjectPuid2(String mainId);

    /**
     * 主键删除
     *
     * @param uid
     * @return
     */
    public boolean deleteByPrimaryKey(String uid);

    /**
     * 在当前项目找出带颜色/不带颜色的特性，不能排除2特性值分别带颜色和不带颜色，因此会发生重复，需要进行排重
     *
     * @param projectUid 项目UID
     * @param isColor    1，带颜色的特性；0，不带颜色的特性
     * @return
     */
    public List<HzFeature> selectForColorBluePrint(String projectUid, Integer isColor);

    /**
     * 根据主配置puid获取列信息定义，强制包含<br/>
     *
     * @param mainId 主配置puid
     * @return 一组列信息
     */
    public List<String> getColumn(String mainId);

    /**
     * 根据主配置puid获取列信息定义，强制包含\t
     *
     * @param mainId 主配置puid
     * @return 一组列信息
     */
    public List<String> getColumn2(String mainId);

    /**
     * 自定义的列信息，如果组的id(name)未定义(null)，则取留空;如果组描述(desc)未定义，则取组的id(name)的值
     *
     * @param mainId 主配置的puid
     * @param def    自定义的分隔符
     * @return 一组列信息，包含分隔符
     */
    public List<String> getColumnDef(String mainId, String def);

    /**
     * 自定义的列信息，如果组的id(name)未定义(null)，则取留空;如果组描述(desc)未定义，则取组的id(name)的值
     *
     * @param mainId 主配置的puid
     * @param def    自定义的分隔符
     * @return 一组列信息，包含分隔符
     */
    public List<String> getColumnDef2(String mainId, String def);

    /**
     * 根据主键获取组对象信息
     *
     * @param family 一个含有puid的组对象
     * @return
     */
    public HzFeature selectById(HzFeature family);

    public HzFeature selectByCodeAndDescWithMain(HzFeature family);

    public boolean insert(HzFeature family);

    public List<HzFeature> selectByDesc(String mainUid, String desc);

    /**
     * 获取排序好的列
     *
     * @param projectUid
     * @param def
     * @return
     */
    public List<String> getColumnNew(String projectUid, String def);

    /**
     * 获取排序好的列
     *
     * @param projectUid
     * @param def
     * @return
     */
    public List<String> getColumnNew2(String projectUid, String def);

    /**
     * 重新构造列信息
     *
     * @param families
     * @param def
     * @return
     */
    public List<String> getColumnNewWithFamilies(List<HzFeature> families, String def);

    /**
     * 将项目下的所有特性查询出来，车身颜色和油漆车身总成放在前两位，其余都经过特性NAME排序
     *
     * @param projectUid
     * @param start
     * @param end
     * @return
     */
    public List<HzFeature> getFamilies(String projectUid, int start, int end);

    /**
     * @param projectUid 项目UID
     * @param names      特性描述是否符合列表中出现的字符串数据
     * @param isIn       当names为null时，是否将不起作用，该标识用于标识当前项目中的names是否会出现再查询条件中，
     *                   如果为true且names!=null，则会搜索出匹配names列表中的所有特性
     *                   如果未false且names！=null，则搜索出不匹配names列表中的所有特性
     * @return
     * @Desc 如果想查询项目中的使用的特性，且特性经过特性代码排序后的数据，将names设为null即可
     */
    public List<HzFeature> selectNameByMap(String projectUid, List<String> names, boolean isIn);

    /**
     * 选择性更新项目下的特性
     *
     * @param family 特性对象
     * @return
     */
    public boolean updateByPrimaryKeySelective(HzFeature family);
}
