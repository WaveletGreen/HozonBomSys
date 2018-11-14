/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.cfg0;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import com.connor.hozon.bom.bomSystem.dto.HzFeatureQueryDto;
import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.cfg0.HzCfg0Record;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 特性基础dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzCfg0RecordDao extends BasicDao<HzCfg0Record> {

    /***
     * 根据主键删除在Hz_Cfg0_Record表中插入1条数据的数据
     * @param puid
     * @return
     */
    int deleteByPrimaryKey(@Param("puid") String puid);

    /***
     * 根据项目的puid搜索所有的特性信息
     * @param projectPuid
     * @param queryBase
     * @return
     */
    List<HzCfg0Record> selectListByProjectPuid(@Param("projectPuid") String projectPuid, HzFeatureQueryDto queryBase);


    List<HzMaterielFeatureBean> selectMaterielFeatureByProjectPuid(@Param("projectPuid") String projectPuid);

    /***
     * 根据puid查找1条特性数据
     * @param puid
     * @return
     */
    HzCfg0Record selectByPrimaryKey(@Param("puid") String puid);


    /**
     * 批量查找特性值
     *
     * @param _map
     * @return
     */
    List<HzCfg0Record> selectCfg0ListByPuids(Map<String, Object> _map);

    /**
     * 根据特性值和特性值描述进行查找
     *
     * @param record
     * @return
     */
    List<HzCfg0Record> selectByCodeAndDesc(HzCfg0Record record);

    /**
     * 批量删除
     *
     * @param records
     * @return
     */
    int deleteCfgByList(List<HzCfg0Record> records);

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
    int tellMeHowManyOfThose(HzFeatureQueryDto projectPuid);

    /**
     * @param queryBase
     * @return
     */
    List<HzCfg0Record> selectByCondition(HzFeatureQueryDto queryBase);

    /**
     * 用HZCSYS+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    HzCfg0Record selectByCodeAndDescWithMainItem(HzCfg0Record record);

    /**
     * 用"车身颜色"+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    HzCfg0Record selectByCodeAndCnDescWithMainItem(HzCfg0Record record);

    /**
     * 根据项目和特性PUID(父)获取一组特性
     *
     * @param familyUid  特性UID
     * @param projectUid 项目UID
     * @return
     */
    List<HzCfg0Record> selectByFamilyUidWithProject(String familyUid, String projectUid);

    int updateList(List<HzCfg0Record> hzCfg0RecordList);

    int updateByVwoid(HzCfg0Record hzCfg0Record);

    List<HzCfg0Record> selectByPuids(List<String> puidList);
}