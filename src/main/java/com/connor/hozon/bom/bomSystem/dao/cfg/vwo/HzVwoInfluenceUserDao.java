package com.connor.hozon.bom.bomSystem.dao.cfg.vwo;

import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.vwo.HzVwoInfluenceUser;
public interface HzVwoInfluenceUserDao {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") Long id);

    /**
     * 全插入
     *
     * @param record
     * @return
     */
    int insert(HzVwoInfluenceUser record);

    /**
     * 非空插入
     *
     * @param record
     * @return
     */
    int insertSelective(HzVwoInfluenceUser record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */

    HzVwoInfluenceUser selectByPrimaryKey(@Param("id") Long id);

    /**
     * 根据VWO主键进行查询
     *
     * @param vwoId
     * @return
     */
    HzVwoInfluenceUser selectByVwoId(@Param("vwoId") Long vwoId);

    /**
     * 主键+非空更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(HzVwoInfluenceUser record);

    /**
     * 全更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzVwoInfluenceUser record);
}
