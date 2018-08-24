package com.connor.hozon.bom.bomSystem.dao.cfg.vwo;

import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.vwo.HzVwoInfluenceDept;

public interface HzVwoInfluenceDeptDao {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 全插入
     *
     * @param record
     * @return
     */
    int insert(HzVwoInfluenceDept record);

    /**
     * 非空插入
     *
     * @param record
     * @return
     */
    int insertSelective(HzVwoInfluenceDept record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */

    HzVwoInfluenceDept selectByPrimaryKey(Long id);

    /**
     * 根据VWO ID查找影响部门
     *
     * @param vwoId
     * @return
     */
    HzVwoInfluenceDept selectByVwoId(@Param("vwoId") Long vwoId);

    /**
     * 主键+非空更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(HzVwoInfluenceDept record);

    /**
     * 主键全更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzVwoInfluenceDept record);
}