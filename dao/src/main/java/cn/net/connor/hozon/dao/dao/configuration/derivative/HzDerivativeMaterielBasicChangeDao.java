/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.configuration.derivative;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzComposeDelDto;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasicChangeBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuDB
 * @Description: 新版配置物料基础变更dao层
 * @Date: Created in 2018/11/9 10:49
 * @Modified By:
 */
@Repository
public interface HzDerivativeMaterielBasicChangeDao extends BasicDao<HzDerivativeMaterielBasicChangeBean> {

    int insertList(List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeans);

    List<HzDerivativeMaterielBasicChangeBean> selectByFormid(@Param("changeFromId") Long changeFromId);

    HzDerivativeMaterielBasicChangeBean selectBefor(HzDerivativeMaterielBasicChangeBean hzDerivativeMaterielBasicChangeBeanAfter);


    List<HzDerivativeMaterielBasicChangeBean> selectAfter(@Param("formId") Long formId);

    List<HzDerivativeMaterielBasicChangeBean> selectLastById(List<HzComposeDelDto> delDtos);

    int updateStatusByOrderId(Map<String,Object> params);

    int deleteByChangeIds(List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeans);

    List<HzDerivativeMaterielBasicChangeBean> selectNotEffect(List<Long> changeMaterielFeatureIds);
}