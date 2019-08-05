/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.change.vwo;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import org.apache.ibatis.annotations.Param;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoInfluenceDept;
import org.springframework.stereotype.Repository;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 影响部门
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzVwoInfluenceDeptDao  extends BasicDao<HzVwoInfluenceDept>{
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

    HzVwoInfluenceDept selectByPrimaryKey(@Param("id") Long id);

    /**
     * 根据VWO ID查找影响部门
     *
     * @param vwoId
     * @return
     */
    HzVwoInfluenceDept selectByVwoId(@Param("vwoId") Long vwoId);

}