/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.configuration.feature;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureChangeBean;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzMaterielFeatureBean;
import cn.net.connor.hozon.dao.query.configuration.feature.HzFeatureQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 特性基础dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzFeatureValueDao extends BasicDao<HzFeatureValue> {



    /***
     * 根据主键删除在Hz_Cfg0_Record表中插入1条数据的数据
     * @param puid
     * @return
     */
    int deleteByPrimaryKey(@Param("puid") String puid);

    /***
     * 根据项目的puid搜分页的特性数据
     * @return
     */
    List<HzFeatureValue> selectListByProjectPuid(Map<String,Object> params);


    List<HzMaterielFeatureBean> selectMaterielFeatureByProjectPuid(@Param("projectPuid") String projectPuid);

    /**
     * 批量查找特性值
     *
     * @param _map
     * @return
     */
    List<HzFeatureValue> selectCfg0ListByPuids(Map<String, Object> _map);

    /**
     * 根据特性值和特性值描述进行查找
     *
     * @param record
     * @return
     */
    List<HzFeatureValue> selectByCodeAndDesc(HzFeatureValue record);

    /**
     * 批量删除
     *
     * @param records
     * @return
     */
    int deleteCfgByList(List<HzFeatureValue> records);

    /**
     * 设置是否已经发送过ERP
     *
     * @param _map
     * @return
     */
    int setIsSent(Map<String, Object> _map);

    /**
     * 设置进入流程状态
     *
     * @param _map
     * @return
     */
    int setToProcess(Map<String, Object> _map);

    /**
     * 查询总数
     *
     * @param projectPuid
     * @return
     */
    int count(HzFeatureQuery projectPuid);

    /**
     * @param queryBase
     * @return
     */
    List<HzFeatureValue> selectByCondition(HzFeatureQuery queryBase);

    /**
     * 用HZCSYS+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    HzFeatureValue selectByCodeAndDescWithMainItem(HzFeatureValue record);

    /**
     * 用"车身颜色"+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    HzFeatureValue selectByCodeAndCnDescWithMainItem(HzFeatureValue record);

    /**
     * 根据项目和特性PUID(父)获取一组特性
     * @return
     */
    List<HzFeatureValue> selectByFamilyUidWithProject(HzFeatureValue feature);

    int updateList(List<HzFeatureValue> hzFeatureValueList);

    int updateByVwoid(HzFeatureValue hzFeatureValue);

    List<HzFeatureValue> selectByPuids(List<String> puidList);

    int updateStatus(List<HzFeatureValue> hzFeatureValueListDelete);

    int updateListAll(List<HzFeatureValue> hzFeatureValueListUpdata);

    int updateStatusByOrderId(Map<String,Object> params);

    int updateByChangeId(List<Long> changeFeatureIds);

    int updateStatusByChangeDate(List<HzFeatureChangeBean> hzFeatureChangeBeans);

    int updateDescByDictionaryLib(HzFeatureValue hzFeatureValue);

    List<HzFeatureValue> selectByDictionaryLibId(String puid);

    List<HzFeatureValue> selectByChangeOrderId(Long vwoId);

    int deleteByOrderId(Long orderId);

    List<HzFeatureValue> selectByFamilyName(HzFeatureValue hzFeatureValue);

    /**
     * 需要传入特性组描述 pCfg0FamilyDesc和项目ID，项目ID传到 pCfg0MainItemPuid属性中进行级联查询
     * @param cfg
     * @return
     */
    List<HzFeatureValue> selectByDescAndProjectId(HzFeatureValue cfg);

    HzFeatureValue selectByPrimaryKey(@Param("puid") String puid);
}