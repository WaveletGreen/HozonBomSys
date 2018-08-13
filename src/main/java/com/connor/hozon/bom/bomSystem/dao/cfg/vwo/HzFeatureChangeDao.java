package com.connor.hozon.bom.bomSystem.dao.cfg.vwo;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;

@Configuration
public interface HzFeatureChangeDao {
    /**
     * 主键删除
     *
     * @param bean
     * @return
     */
    int deleteByPrimaryKey(HzFeatureChangeBean bean);

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    int insert(HzFeatureChangeBean record);

    /**
     * 主键查找
     *
     * @param bean
     * @return
     */

    HzFeatureChangeBean selectByPrimaryKey(HzFeatureChangeBean bean);

    /**
     * 查找特性下最新的更改
     *
     * @param bean
     * @return
     */

    HzFeatureChangeBean findNewestChange(HzFeatureChangeBean bean);

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzFeatureChangeBean record);

}