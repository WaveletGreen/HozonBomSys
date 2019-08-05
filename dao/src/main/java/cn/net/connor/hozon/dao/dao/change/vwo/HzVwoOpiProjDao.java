/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.change.vwo;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoOpiProj;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 项目经理意见
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzVwoOpiProjDao extends BasicDao<HzVwoOpiProj>{
    HzVwoOpiProj selectByVwoId(@Param("opiVwoId") Long opiVwoId);

    int updateUserByVwoId(HzVwoOpiProj hzVwoOpiProj);
}