/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.bomSystem.service.cfg;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzMaterielFeatureBean;
import cn.net.connor.hozon.dao.query.feature.HzFeatureQuery;
import com.connor.hozon.bom.bomSystem.dto.HzRelevanceBean;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/1 10:49
 * @Modified By:
 */
public interface HzFeatureService {

    /**
     * 主键查询
     *
     * @param puid
     * @return
     */
    public HzFeatureValue doSelectByPrimaryKey(String puid);

    /**
     * 分页查询
     *
     * @param projectPuid
     * @param queryBase
     * @return
     */
    public List<HzFeatureValue> selectFeatureListByProjectId(String projectPuid, HzFeatureQuery queryBase) ;


    public List<HzMaterielFeatureBean> doSelectMaterielFeatureByProjectPuid(String projectPuid) ;
    /**
     * 插入单条特性值
     *
     * @param record
     * @return
     */
    public boolean doInsertOne(HzFeatureValue record);


    /**
     * 主键查询
     *
     * @param puid
     * @return
     */
    public HzFeatureValue doSelectOneByPuid(String puid);


    /**
     * 更新单条特性值数据
     *
     * @param record
     * @return
     */
    public boolean doUpdate(HzFeatureValue record);

    /**
     * 批量更新
     *
     * @param record
     * @return
     */
    public boolean doUpdateByBatch(Map<String, Object> record);


    public boolean doDeleteByPuid(HzFeatureValue record);


    public boolean doDeleteCfgByList(List<HzFeatureValue> records);


    /***
     * 根据table，进行查询并拼接成相关性值
     * @param projectPuid 项目puid
     * @param _list 存储结果的list
     * @param index 序号，使用封装类进行引用从而可以修改引用数据
     * @param _table 取数据的表：HZ_CFG0_RECORD是原数据，HZ_CFG0_ADD_CFG_RECORD是添加的数据
     * @return 返回当前最后一个筛选结果的的正序顺序
     */
    public int doLoadRelevance(String projectPuid, List<HzRelevanceBean> _list, int index, String _table) ;

    public List<HzFeatureValue> doLoadListByPuids(List<String> list) ;

    public void updateByTableName(List<HzFeatureValue> list, String table);

    public boolean preCheck(HzFeatureValue record) ;

    /**
     * 项目上的特性总数是多少，针对项目而非整个合众公司
     *
     * @param dto
     * @return
     */
    public int count(HzFeatureQuery dto) ;

    /**
     * 设置已经在流程中
     *
     * @param cfgs 特性值集合
     * @return
     */
    public int doSetToProcess(List<HzFeatureValue> cfgs) ;

    public List<HzFeatureValue> doLoadByCondition(HzFeatureQuery queryBase) ;

    /**
     * 用HZCSYS+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    public HzFeatureValue doSelectByCodeAndDescWithMainItem(HzFeatureValue record) ;

    /**
     * 用"车身颜色"+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    public HzFeatureValue doSelectByCodeAndCnDescWithMainItem(HzFeatureValue record) ;

    public List<HzFeatureValue> doSelectByFamilyUidWithProject(String familyUid, String projectUid) ;

    /**
     * 进行配置字典联动的后续操作
     *
     * @param projectUid 项目UID
     */
    public void synDictionaryAfterOption(String projectUid) ;

    public int doupdateList(List<HzFeatureValue> hzFeatureValueList);

    public List<HzFeatureValue> doSelectByDescAndProjectId(HzFeatureValue cfg);

    public Map<String,Object> loadFeature(String projectPuid, HzFeatureQuery queryBase) ;

    int updateStatusByOrderId(Long orderId, int i);

    HzFeatureValue selectByPrimaryKey(String cfgCfg0Uid);
}
