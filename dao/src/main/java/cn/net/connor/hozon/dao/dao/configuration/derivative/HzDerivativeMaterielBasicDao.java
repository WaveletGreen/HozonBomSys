/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.configuration.derivative;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzComposeDelDto;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasicChangeBean;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 新版配置物料基础dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
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
     * @param dmbModelUid 车型模型UID
     * @param dmbColorModelUid 配色模型UID
     * @return
     */
    HzDerivativeMaterielBasic selectByModelAndColorUid(@Param("dmbModelUid") String dmbModelUid, @Param("dmbColorModelUid") String dmbColorModelUid);

    List<HzDerivativeMaterielBasic> selectByPuids(List<String> puids);

    int updateByBasicList(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics);

    int updateByBasicListChangId(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics);

    int updateStatus(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics);

    int updateByBasicAll(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasicsUpdate);

    int deleteByIds(List<HzComposeDelDto> delDtos);

    int updateStatusByOrderId(Long orderId, int status);

    int updateByChangeIds(List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeans);

    List<HzDerivativeMaterielBasic> selectByChangeOrderId(Long orderId);

    int deleteByOrderId(Long orderId);

    int updateStatusUpdate(HzDerivativeMaterielBasic hzDerivativeMaterielBasics);
}