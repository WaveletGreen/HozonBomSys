/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.derivative;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import com.connor.hozon.bom.bomSystem.dto.cfg.compose.HzComposeDelDto;
import sql.pojo.cfg.derivative.HzDMBasicChangeBean;
import sql.pojo.cfg.derivative.HzDerivativeMaterielBasic;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 新版配置物料基础dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzDerivativeMaterielBasicDao extends BasicDao<HzDerivativeMaterielBasic> {
    /**
     * 根据项目查找项目下的所有配置物料特性数据
     *
     * @param params basic=HzDerivativeMaterielBasic,condition=筛选条件，暂无
     * @return
     */
    List<HzDerivativeMaterielBasic> selectByProjectUid(Map<String, Object> params);

    /**
     * 车型模型+配色模型组成唯一的配置物料特性数据
     * @param modelUid 车型模型UID
     * @param colorModel 配色模型UID
     * @return
     */
    HzDerivativeMaterielBasic selectByModelAndColorUid(String modelUid, String colorModel);

    List<HzDerivativeMaterielBasic> selectByPuids(List<String> puids);

    int updateByBasicList(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics);

    int updateByBasicListChangId(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics);

    int updateStatus(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics);

    int updateByBasicAll(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasicsUpdate);

    int deleteByIds(List<HzComposeDelDto> delDtos);

    int updateStatusByOrderId(Long orderId, int status);

    int updateByChangeIds(List<HzDMBasicChangeBean> hzDMBasicChangeBeans);

    List<HzDerivativeMaterielBasic> selectByChangeOrderId(Long orderId);

    int deleteByOrderId(Long orderId);

    int updateStatusUpdate(HzDerivativeMaterielBasic hzDerivativeMaterielBasics);
}