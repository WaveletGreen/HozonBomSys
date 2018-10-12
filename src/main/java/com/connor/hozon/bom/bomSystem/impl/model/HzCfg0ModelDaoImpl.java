/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.model;

import com.connor.hozon.bom.bomSystem.dao.model.HzCfg0ModelDetailDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.model.HzCfg0ModelDetail;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 16:30
 */
@Service("hzCfg0ModelDetailDao")
public class HzCfg0ModelDaoImpl extends BasicDaoImpl<HzCfg0ModelDetail> implements HzCfg0ModelDetailDao {

    private final static HzCfg0ModelDetail DETAIL = new HzCfg0ModelDetail();

    public HzCfg0ModelDaoImpl() {
        clz = HzCfg0ModelDetailDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public HzCfg0ModelDetail selectByModelId(HzCfg0ModelDetail detail) {
        return baseSQLUtil.executeQueryById(detail,
                clzName + ".selectByModelId");
    }


    /**
     * 连同车型模型的数据都一起查出来
     *
     * @param detail
     * @return
     */
    @Override
    public HzCfg0ModelDetail selectByModelId2(HzCfg0ModelDetail detail) {
        return baseSQLUtil.executeQueryById(detail,
                clzName + ".selectByModelId2");
    }


    @Override
    public List<HzCfg0ModelDetail> selectByModelIds(List<HzCfg0ModelDetail> hzCfg0ModelDetails) {
        return baseSQLUtil.executeQueryByPass(DETAIL, hzCfg0ModelDetails,
                clzName + ".selectByModelIds");
    }


}
