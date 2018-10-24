/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoOpiPmt;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: PMT经理意见
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzVwoOpiPmtDao extends BasicDao<HzVwoOpiPmt>{
    HzVwoOpiPmt selectByVwoId(Long id);

    int updateUserByVwoId(HzVwoOpiPmt hzVwoOpiPmt);
}