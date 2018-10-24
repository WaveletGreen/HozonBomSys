/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoExecute;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzVwoExecuteDao extends BasicDao<HzVwoExecute> {
    /**
     * 根据VWO号ID查询
     *
     * @param vwoId vwo号的ID，主键
     * @return 一组分发与实施对象
     */
    List<HzVwoExecute> selectByVwoId(Long vwoId);

    /**
     * 批量删除发布与实施数据
     *
     * @param executes 发布与实施数据
     * @return 影响行数
     */
    int deleteByBatch(List<HzVwoExecute> executes);
}