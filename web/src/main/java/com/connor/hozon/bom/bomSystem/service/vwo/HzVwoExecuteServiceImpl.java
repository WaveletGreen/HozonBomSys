/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.vwo;

import cn.net.connor.hozon.dao.dao.change.vwo.HzVwoExecuteDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.HzVwoExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoExecute;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/10/15 16:05
 * @Modified By:
 */
@Service("hzVwoExecuteService")
public class HzVwoExecuteServiceImpl implements HzVwoExecuteService {

    @Autowired
    private HzVwoExecuteDao hzVwoExecuteDao;

    /**
     * 根据VWO ID查询
     *
     * @param VwoId VWO主键
     * @return 一组分发与实施对象
     */
    @Override
    public List<HzVwoExecute> selectByVwoId(Long VwoId) {
        return hzVwoExecuteDao.selectByVwoId(VwoId);
    }

    /**
     * 增加一个发布与实施对象
     *
     * @param execute
     * @return
     */
    @Override
    public boolean insert(HzVwoExecute execute) {
        return hzVwoExecuteDao.insertSelective(execute) > 0 ? true : false;
    }

    /**
     * 批量删除发布与实施数据
     *
     * @param executes
     * @return
     */
    @Override
    public boolean deleteByBatch(List<HzVwoExecute> executes) {
        return hzVwoExecuteDao.deleteByBatch(executes) > 0 ? true : false;
    }
}
