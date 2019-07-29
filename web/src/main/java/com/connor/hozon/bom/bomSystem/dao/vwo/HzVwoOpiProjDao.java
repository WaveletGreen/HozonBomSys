/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.vwo;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import sql.pojo.cfg.vwo.HzVwoOpiProj;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 项目经理意见
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzVwoOpiProjDao extends BasicDao<HzVwoOpiProj>{
    HzVwoOpiProj selectByVwoId(Long id);

    int updateUserByVwoId(HzVwoOpiProj hzVwoOpiProj);
}