/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 特性变更dao
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzFeatureChangeDao extends BasicDao<HzFeatureChangeBean> {

    /**
     * 查找特性下最新的更改
     *
     * @param bean  特性变更bean
     * @return
     */

    HzFeatureChangeBean findNewestChange(HzFeatureChangeBean bean);

    /**
     * 根据VWO ID号查询特性的变更数据
     *
     * @param bean 特性变更bean
     * @return
     */
    List<HzFeatureChangeBean> selectByVwoId(HzFeatureChangeBean bean);

    int insertList(List<HzFeatureChangeBean> hzFeatureChangeBeans);

    /**
     * 根据VWO ID找到当前变更的特性UID
     * @param bean
     * @return
     */
    List<HzFeatureChangeBean> selectCfgUidsByVwoId(HzFeatureChangeBean bean);

    List<HzFeatureChangeBean> doQueryLastTwoChange(HzFeatureChangeBean hzFeatureChangeBean);
}