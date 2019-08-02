/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.derivative;

import com.connor.hozon.bom.bomSystem.dto.cfg.compose.HzComposeDelDto;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzDerivativeMaterielDetailDao;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasic;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielDetail;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Repository
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

    @Override
    public List<HzDerivativeMaterielDetail> selectByBasics(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics) {
        return baseSQLUtil.executeQueryByPass(new HzDerivativeMaterielDetail(), hzDerivativeMaterielBasics, clz.getCanonicalName() + ".selectByBasics");
    }

    @Override
    public int updateByDetailAll(List<HzDerivativeMaterielDetail> hzDerivativeMaterielDetailsUpdate) {
        return baseSQLUtil.executeUpdate(hzDerivativeMaterielDetailsUpdate,clz.getCanonicalName()+".updateByDetailAll");
    }

    @Override
    public int deleteByBasicIds(List<HzComposeDelDto> delDtos) {
        return baseSQLUtil.executeDelete(delDtos,clz.getCanonicalName()+".deleteByBasicIds");
    }

}
