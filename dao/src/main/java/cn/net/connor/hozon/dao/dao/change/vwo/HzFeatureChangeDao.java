/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.change.vwo;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureChangeBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 特性变更dao
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
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

    int insertList(Map<String, Object> map );

    /**
     * 根据VWO ID找到当前变更的特性UID
     * @param bean
     * @return
     */
    List<HzFeatureChangeBean> selectCfgUidsByVwoId(HzFeatureChangeBean bean);

    List<HzFeatureChangeBean> findLastTwoChange(HzFeatureChangeBean hzFeatureChangeBean);

    List<HzFeatureChangeBean> selectHasEffect(List<HzFeatureValue> records);

    int updateStatusByOrderId(Map<String,Object> map);

    int deleteByPrimaryKeys(List<Long> changeFeatureIds);

    HzFeatureChangeBean selectLast(HzFeatureChangeBean hzFeatureChangeBean);

    HzFeatureChangeBean selectByChangeIdAndCfgid(HzFeatureChangeBean hzFeatureChangeBean);

    List<HzFeatureChangeBean> selectByChangeId(@Param("orderId") Long orderId);

    List<HzFeatureChangeBean> selectHasNotEffect(List<Long> changeFeatureIds);
}