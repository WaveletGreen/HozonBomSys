/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.project;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.project.HzVehicleRecord;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 车型dao
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzVehicleRecordDao  extends BasicDao<HzVehicleRecord>{
    /**
     * 根据主键删除
     *
     * @param puid puid
     * @return
     */
    int deleteByPrimaryKey(@Param("puid") String puid);


    /**
     * 根据主键获取1条车型数据
     *
     * @param puid puid
     * @return
     */
    HzVehicleRecord selectByPrimaryKey(@Param("puid") String puid);


    /**
     * 查找所有的车型信息
     *
     * @return
     */
    List<HzVehicleRecord> selectAll();

    /**
     * 根据车型代码寻找车型信息
     * @param pVehicleCode 车型代码
     * @return
     */
    HzVehicleRecord selectByCode(@Param("pVehicleCode") String pVehicleCode);
}