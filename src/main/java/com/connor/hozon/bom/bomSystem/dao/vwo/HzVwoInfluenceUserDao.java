/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.vwo.HzVwoInfluenceUser;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 影响人员
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzVwoInfluenceUserDao  extends BasicDao<HzVwoInfluenceUser>{
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

    HzVwoInfluenceUser selectByPrimaryKey(@Param("id") Long id);

    /**
     * 根据VWO主键进行查询
     *
     * @param vwoId
     * @return
     */
    HzVwoInfluenceUser selectByVwoId(@Param("vwoId") Long vwoId);

}
