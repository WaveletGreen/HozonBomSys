/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.configuration.derivative;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDMBasicChangeBean;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDMDetailChangeBean;

import java.util.List;

/**
 * @Author: zhuDB
 * @Description: 新版配置物料详情变更dao层
 * @Date: Created in 2018/11/9 10:49
 * @Modified By:
 */
public interface HzDMDetailChangeDao extends BasicDao<HzDMDetailChangeBean> {

    int insertList(List<HzDMDetailChangeBean> hzDMDetailChangeBeans);

    List<HzDMDetailChangeBean> selectByBasic(List<HzDMBasicChangeBean> hzDMBasicChangeBeansBefor);
}