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
    int doDeleteByPrimaryKey(Long id);

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
     * @param id
     * @return
     */

    HzFeatureChangeBean doSelectByPrimaryKey(Long id);

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKey(HzFeatureChangeBean record);

    /**
     * 根据配置进行插入
     * @param record
     * @return
     */
    boolean insertByCfg(HzCfg0Record record,String tableName);
}
