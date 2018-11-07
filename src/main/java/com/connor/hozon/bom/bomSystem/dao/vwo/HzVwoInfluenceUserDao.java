/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoInfluenceUser;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 影响人员
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
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
