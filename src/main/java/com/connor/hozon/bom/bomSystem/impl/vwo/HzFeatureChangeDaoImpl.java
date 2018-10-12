/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzFeatureChangeDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/9 19:15
 * @Modified By:
 */
@Configuration
public class HzFeatureChangeDaoImpl extends BasicDaoImpl<HzFeatureChangeBean> implements HzFeatureChangeDao {

    public HzFeatureChangeDaoImpl() {
        clz = HzFeatureChangeDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 查找特性下最新的更改
     *
     * @param bean
     * @return
     */
    @Override
    public HzFeatureChangeBean findNewestChange(HzFeatureChangeBean bean) {
        return baseSQLUtil.executeQueryById(bean, clzName + ".findNewestChange");
    }

    @Override
    public List<HzFeatureChangeBean> selectByVwoId(HzFeatureChangeBean bean) {
        return baseSQLUtil.executeQuery(bean, clzName + ".selectByVwoId");
    }


}
