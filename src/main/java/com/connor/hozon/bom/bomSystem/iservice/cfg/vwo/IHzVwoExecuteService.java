/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.cfg.vwo;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoExecute;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: x
 * @Date: Created in 2018/10/15 16:04
 * @Modified By:
 */
@Configuration
public interface IHzVwoExecuteService {
    /**
     * 根据VWO ID查询
     *
     * @param VwoId VWO主键
     * @return 一组分发与实施对象
     */
    List<HzVwoExecute> doSelectByVwoId(Long VwoId);

    /**
     * 增加一个发布与实施对象
     *
     * @param execute
     * @return
     */
    boolean doInsert(HzVwoExecute execute);

    /**
     * 批量删除发布与实施数据
     *
     * @param executes
     * @return
     */
    boolean doDeleteByBatch(List<HzVwoExecute> executes);
}
