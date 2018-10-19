package com.connor.hozon.bom.bomSystem.iservice.cfg.vwo;

import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/9 19:24
 * @Modified By:
 */
public interface IHzFeatureChangeService {
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
    Long insertByCfgAfter(HzCfg0Record record);

    /**
     * 根据配置进行插入
     *
     * @param record
     * @return
     */
    Long insertByCfgBefore(HzCfg0Record record);

    /**
     * 配置对应的变更，做字段按对应
     *
     * @param record
     * @param bean
     * @return
     */
    HzFeatureChangeBean reflect(HzCfg0Record record, HzFeatureChangeBean bean);

    List<HzFeatureChangeBean> doSelectAfterByVwoId(Long vwo);

    List<HzFeatureChangeBean> doSelectBeforeByVwoId(Long vwo);
}