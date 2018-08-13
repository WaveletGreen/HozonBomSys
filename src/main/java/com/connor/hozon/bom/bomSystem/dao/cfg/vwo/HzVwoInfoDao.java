package com.connor.hozon.bom.bomSystem.dao.cfg.vwo;

import sql.pojo.cfg.vwo.HzVwoInfo;

import java.math.BigDecimal;

public interface HzVwoInfoDao {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入1条vwo
     *
     * @param record
     * @return
     */
    int insert(HzVwoInfo record);

    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    HzVwoInfo selectByPrimaryKey(Long id);

    /**
     * 更新vwo
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzVwoInfo record);

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    HzVwoInfo findMaxAreaVwoNum();
}