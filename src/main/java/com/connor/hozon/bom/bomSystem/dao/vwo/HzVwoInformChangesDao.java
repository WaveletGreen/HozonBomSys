/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoInformChanges;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzVwoInformChangesDao extends BasicDao<HzVwoInformChanges> {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") Long id);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    HzVwoInformChanges selectByPrimaryKey(@Param("id") Long id);

    /**
     * 根据VWO主键查询所有关联的人员
     *
     * @param vwoId
     * @return
     */
    List<HzVwoInformChanges> selectByVwoId(@Param("vwoId") Long vwoId);

    /**
     * 获取VWO号下的总关联数
     *
     * @param vwoId
     * @return
     */
    Long tellMeHowManyOfIt(@Param("vwoId") Long vwoId);
}