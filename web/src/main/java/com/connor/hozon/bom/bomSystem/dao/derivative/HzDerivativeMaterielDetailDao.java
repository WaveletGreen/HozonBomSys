/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.derivative;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import com.connor.hozon.bom.bomSystem.dto.cfg.compose.HzComposeDelDto;
import sql.pojo.cfg.derivative.HzDerivativeMaterielBasic;
import sql.pojo.cfg.derivative.HzDerivativeMaterielDetail;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 新版配置物料单车对应的配置项
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzDerivativeMaterielDetailDao extends BasicDao<HzDerivativeMaterielDetail> {

    /**
     * 基本信息外键查询，附带特性值信息
     *
     * @param detail
     * @return
     */
    List<HzDerivativeMaterielDetail> selectByBasicWithCfg(HzDerivativeMaterielDetail detail);

    List<HzDerivativeMaterielDetail> selectByBasics(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics);

    int updateByDetailAll(List<HzDerivativeMaterielDetail> hzDerivativeMaterielDetailsUpdate);

    int deleteByBasicIds(List<HzComposeDelDto> delDtos);
}