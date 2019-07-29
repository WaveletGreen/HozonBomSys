/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.vwo;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.vwo.HzVwoInfluenceDept;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 影响部门
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzVwoInfluenceDeptDao  extends BasicDao<HzVwoInfluenceDept>{
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */

    HzVwoInfluenceDept selectByPrimaryKey(Long id);

    /**
     * 根据VWO ID查找影响部门
     *
     * @param vwoId
     * @return
     */
    HzVwoInfluenceDept selectByVwoId(@Param("vwoId") Long vwoId);

}