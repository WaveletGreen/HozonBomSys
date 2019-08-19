/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.vwo;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureChangeBean;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface HzFeatureChangeService {
    /**
     * 主键删除
     *
     * @param bean
     * @return
     */
    boolean doDeleteByPrimaryKey(HzFeatureChangeBean bean);

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    Long doInsert(HzFeatureChangeBean record);

    /**
     * 主键查找
     *
     * @param bean
     * @return
     */

    HzFeatureChangeBean doSelectByPrimaryKey(HzFeatureChangeBean bean);

    /**
     * 主键查找
     *
     * @param bean
     * @return
     */

    HzFeatureChangeBean doSelectAfterByPk(HzFeatureChangeBean bean);

    /**
     * 主键查找
     *
     * @param bean
     * @return
     */

    HzFeatureChangeBean doSelectBeforeByPk(HzFeatureChangeBean bean);

    /**
     * 查找特性下最新的更改
     *
     * @param bean
     * @return
     */

    HzFeatureChangeBean doFindNewestChange(HzFeatureChangeBean bean);

    /**
     * 查找特性下最新的更改
     *
     * @param bean
     * @return
     */

    HzFeatureChangeBean doFindNewestChangeFromAfter(HzFeatureChangeBean bean);

    /**
     * 查找特性下最新的更改
     *
     * @param bean
     * @return
     */

    HzFeatureChangeBean doFindNewestChangeFromBefore(HzFeatureChangeBean bean);

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    boolean doUpdateByPrimaryKey(HzFeatureChangeBean record);

    /**
     * 更新变更后
     *
     * @param record
     * @return
     */
    boolean doUpdateAfterByPk(HzFeatureChangeBean record);

    /**
     * 更新变更前
     *
     * @param record
     * @return
     */
    boolean doUpdateBeforeByPk(HzFeatureChangeBean record);

    /**
     * 根据配置进行插入
     *
     * @param record
     * @return
     */
    Long insertByCfgAfter(HzFeatureValue record);

    /**
     * 根据配置进行插入
     *
     * @param record
     * @return
     */
    Long insertByCfgBefore(HzFeatureValue record);

    /**
     * 配置对应的变更，做字段按对应
     *
     * @param record
     * @param bean
     * @return
     */
    HzFeatureChangeBean reflect(HzFeatureValue record, HzFeatureChangeBean bean);

    List<HzFeatureChangeBean> doSelectAfterByVwoId(Long vwo);

    List<HzFeatureChangeBean> doSelectBeforeByVwoId(Long vwo);

    int doInsertListBefore(List<HzFeatureChangeBean> hzFeatureChangeBeanListBefore);

    int doInsertListAfter(List<HzFeatureChangeBean> hzFeatureChangeBeanListAfter);

    List<HzFeatureChangeBean> doQueryLastTwoChange(String cfgPuid, Long vwoId);

    List<HzFeatureChangeBean> doSelectCfgUidsByVwoId(Long vwoId);

    List<HzFeatureChangeBean> doSelectHasEffect(List<HzFeatureValue> records);

    boolean updateStatusByOrderId(Long orderId, int status);

    int doDeleteByPrimaryKeys(List<Long> changeFeatureIds);

    List<HzFeatureChangeBean> doselectByChangeId(Long orderId);

    List<HzFeatureChangeBean> doSelectHasNotEffect(List<Long> changeFeatureIds);
}
