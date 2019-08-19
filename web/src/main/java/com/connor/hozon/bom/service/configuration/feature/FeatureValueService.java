/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.configuration.feature;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzMaterielFeatureBean;
import cn.net.connor.hozon.dao.query.configuration.feature.HzFeatureQuery;
import cn.net.connor.hozon.services.response.configuration.relevance.HzRelevanceResponseDTO;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/1 10:49
 * @Modified By:
 */
public interface FeatureValueService {

    /**
     * 主键查询
     *
     * @param puid
     * @return
     */
    HzFeatureValue selectByPrimaryKeyFromCurrentTable(String puid);

    /**
     * 分页查询
     *
     * @param projectPuid
     * @param queryBase
     * @return
     */
    List<HzFeatureValue> selectFeatureListByProjectId(String projectPuid, HzFeatureQuery queryBase) ;


    List<HzMaterielFeatureBean> selectMaterielFeatureByProjectPuid(String projectPuid) ;
    /**
     * 插入单条特性值
     *
     * @param record
     * @return
     */
    boolean insertOne(HzFeatureValue record);


    /**
     * 主键查询
     *
     * @param puid
     * @return
     */
    HzFeatureValue selectOneByPuid(String puid);


    /**
     * 更新单条特性值数据
     *
     * @param record
     * @return
     */
    boolean update(HzFeatureValue record);

    /**
     * 批量更新
     *
     * @param record
     * @return
     */
    boolean updateByBatch(Map<String, Object> record);


    boolean deleteByPuid(HzFeatureValue record);


    boolean deleteCfgByList(List<HzFeatureValue> records);


    /***
     * 根据table，进行查询并拼接成相关性值
     * @param projectPuid 项目puid
     * @param _list 存储结果的list
     * @param index 序号，使用封装类进行引用从而可以修改引用数据
     * @param _table 取数据的表：HZ_CFG0_RECORD是原数据，HZ_CFG0_ADD_CFG_RECORD是添加的数据
     * @return 返回当前最后一个筛选结果的的正序顺序
     */
    int doLoadRelevance(String projectPuid, List<HzRelevanceResponseDTO> _list, int index, String _table) ;

    List<HzFeatureValue> doLoadListByPuids(List<String> list) ;

    void updateByTableName(List<HzFeatureValue> list, String table);

    boolean preCheck(HzFeatureValue record) ;

    /**
     * 项目上的特性总数是多少，针对项目而非整个合众公司
     *
     * @param dto
     * @return
     */
    int count(HzFeatureQuery dto) ;

    /**
     * 设置已经在流程中
     *
     * @param cfgs 特性值集合
     * @return
     */
    int doSetToProcess(List<HzFeatureValue> cfgs) ;

    List<HzFeatureValue> doLoadByCondition(HzFeatureQuery queryBase) ;

    /**
     * 用HZCSYS+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    HzFeatureValue selectByCodeAndDescWithMainItem(HzFeatureValue record) ;

    /**
     * 用"车身颜色"+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    HzFeatureValue selectByCodeAndCnDescWithMainItem(HzFeatureValue record) ;

    List<HzFeatureValue> selectByFamilyUidWithProject(String familyUid, String projectUid) ;

    /**
     * 进行配置字典联动的后续操作
     *
     * @param projectUid 项目UID
     */
    void synDictionaryAfterOption(String projectUid) ;

    int updateList(List<HzFeatureValue> hzFeatureValueList);

    List<HzFeatureValue> selectByDescAndProjectId(HzFeatureValue cfg);

    Map<String,Object> loadFeature(String projectPuid, HzFeatureQuery queryBase) ;

    int updateStatusByOrderId(Long orderId, int i);

    HzFeatureValue selectByPrimaryKey(String cfgCfg0Uid);
}
