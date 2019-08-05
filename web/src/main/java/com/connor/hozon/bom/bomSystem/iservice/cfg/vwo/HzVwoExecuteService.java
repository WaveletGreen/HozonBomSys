/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.cfg.vwo;

import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoExecute;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
public interface HzVwoExecuteService {
    /**
     * 根据VWO ID查询
     *
     * @param VwoId VWO主键
     * @return 一组分发与实施对象
     */
    List<HzVwoExecute> selectByVwoId(Long VwoId);

    /**
     * 增加一个发布与实施对象
     *
     * @param execute
     * @return
     */
    boolean insert(HzVwoExecute execute);

    /**
     * 批量删除发布与实施数据
     *
     * @param executes
     * @return
     */
    boolean deleteByBatch(List<HzVwoExecute> executes);
}
