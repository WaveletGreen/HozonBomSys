/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoExecuteDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVwoExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.vwo.HzVwoExecute;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/15 16:05
 * @Modified By:
 */
@Service("hzVwoExecuteService")
public class HzVwoExecuteService implements IHzVwoExecuteService {

    @Autowired
    HzVwoExecuteDao hzVwoExecuteDao;

    /**
     * 根据VWO ID查询
     *
     * @param VwoId VWO主键
     * @return 一组分发与实施对象
     */
    @Override
    public List<HzVwoExecute> doSelectByVwoId(Long VwoId) {
        return hzVwoExecuteDao.selectByVwoId(VwoId);
    }

    /**
     * 增加一个发布与实施对象
     *
     * @param execute
     * @return
     */
    @Override
    public boolean doInsert(HzVwoExecute execute) {
        return hzVwoExecuteDao.insertSelective(execute) > 0 ? true : false;
    }


}
