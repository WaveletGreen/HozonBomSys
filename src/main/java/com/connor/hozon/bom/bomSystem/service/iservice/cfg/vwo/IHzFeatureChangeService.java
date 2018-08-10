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
     * @param id
     * @return
     */
    boolean doDeleteByPrimaryKey(Long id);

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    boolean doInsert(HzFeatureChangeBean record);

    /**
     * 主键查找
     *
     * @param id
     * @return
     */

    HzFeatureChangeBean doSelectByPrimaryKey(Long id);
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
     * 根据配置进行插入
     * @param record
     * @return
     */
    boolean insertByCfg(HzCfg0Record record,String tableName);
}
