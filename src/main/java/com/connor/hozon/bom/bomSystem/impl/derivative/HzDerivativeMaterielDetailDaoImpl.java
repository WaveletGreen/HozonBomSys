/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.derivative;

import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzDerivativeMaterielDetailDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.derivative.HzDerivativeMaterielDetail;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/30 17:00
 * @Modified By:
 */
@Configuration
public class HzDerivativeMaterielDetailDaoImpl
        extends BasicDaoImpl<HzDerivativeMaterielDetail>
        implements HzDerivativeMaterielDetailDao {

    public HzDerivativeMaterielDetailDaoImpl() {
        clz = HzDerivativeMaterielDetailDao.class;
    }

    /**
     * 基本信息外键查询，附带特性值信息
     *
     * @param detail
     * @return
     */
    @Override
    public List<HzDerivativeMaterielDetail> selectByBasicWithCfg(HzDerivativeMaterielDetail detail) {
        return baseSQLUtil.executeQuery(detail, clz.getCanonicalName() + ".selectByBasicWithCfg");
    }

}
