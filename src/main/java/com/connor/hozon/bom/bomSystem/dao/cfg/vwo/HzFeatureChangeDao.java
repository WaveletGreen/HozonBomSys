package com.connor.hozon.bom.bomSystem.dao.cfg.vwo;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;

@Configuration
public interface HzFeatureChangeDao {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

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
     * @param id
     * @return
     */

    HzFeatureChangeBean selectByPrimaryKey(Long id);

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzFeatureChangeBean record);

}