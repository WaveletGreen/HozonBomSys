/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoExecuteDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.vwo.HzVwoExecute;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service("hzVwoExecuteDao")
@Configuration
public class HzVwoExecuteDaoImpl extends BasicDaoImpl<HzVwoExecute> implements HzVwoExecuteDao {
    private final static HzVwoExecute EXECUTE = new HzVwoExecute();

    public HzVwoExecuteDaoImpl() {
        clz = HzVwoExecuteDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 根据VWO号ID查询
     *
     * @param vwoId vwo号的ID，主键
     * @return 一组分发与实施对象
     */
    @Override
    public List<HzVwoExecute> selectByVwoId(Long vwoId) {
        EXECUTE.setExeVwoId(vwoId);
        return baseSQLUtil.executeQuery(EXECUTE, clzName + ".selectByVwoId");
    }

    @Override
    public int deleteByBatch(List<HzVwoExecute> executes) {
        return baseSQLUtil.executeDelete(executes, clzName + ".deleteByBatch");
    }
}
