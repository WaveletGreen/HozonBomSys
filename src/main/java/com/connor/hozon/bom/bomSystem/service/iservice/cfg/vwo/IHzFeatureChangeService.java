package com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo;

import sql.pojo.cfg.HzCfg0Record;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;

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
    int doInsert(HzFeatureChangeBean record);

    /**
     * 主键查找
     *
     * @param bean
     * @return
     */

    HzFeatureChangeBean doSelectByPrimaryKey(HzFeatureChangeBean bean);

    /**
     * 查找特性下最新的更改
     *
     * @param bean
     * @return
     */

    HzFeatureChangeBean doFindNewestChange(HzFeatureChangeBean bean);

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
    int insertByCfg(HzCfg0Record record, String tableName, String seqName);
}
