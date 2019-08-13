/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.configuration.derivative;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasicChangeBean;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielDetailChangeBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhuDB
 * @Description: 新版配置物料详情变更dao层
 * @Date: Created in 2018/11/9 10:49
 * @Modified By:
 */
@Repository
public interface HzDerivativeMaterielDetailChangeDao extends BasicDao<HzDerivativeMaterielDetailChangeBean> {

    int insertList(List<HzDerivativeMaterielDetailChangeBean> hzDerivativeMaterielDetailChangeBeans);

    List<HzDerivativeMaterielDetailChangeBean> selectByBasic(List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeansBefor);
}