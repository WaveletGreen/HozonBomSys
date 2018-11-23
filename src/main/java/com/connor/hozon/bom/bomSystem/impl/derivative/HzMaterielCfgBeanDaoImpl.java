/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.derivative;

import com.connor.hozon.bom.bomSystem.dao.derivative.HzMaterielCfgBeanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.derivative.HzMaterielCfgBean;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Repository
public class HzMaterielCfgBeanDaoImpl implements HzMaterielCfgBeanDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;
    @Override
    public List<HzMaterielCfgBean> selectByDiff(HzMaterielCfgBean bean) {
        return baseSQLUtil.executeQuery(bean,"com.connor.hozon.bom.bomSystem.dao.derivative.HzMaterielCfgBeanDao.selectByDiff");
    }

    @Override
    public int updateIsSent(Map<String, Object> map) {
        return baseSQLUtil.executeUpdate(map , "com.connor.hozon.bom.bomSystem.dao.derivative.HzMaterielCfgBeanDao.updateIsSent");
    }

}
