package com.connor.hozon.bom.bomSystem.dao.cfg.vwo;

import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.vwo.HzVwoInformChanges;

import java.util.List;

public interface HzVwoInformChangesDao {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") Long id);

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    int insert(HzVwoInformChanges record);

    /**
     * 选择性插入
     *
     * @param record
     * @return
     */
    int insertSelective(HzVwoInformChanges record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    HzVwoInformChanges selectByPrimaryKey(@Param("id") Long id);

    /**
     * 根据VWO主键查询所有关联的人员
     *
     * @param vwoId
     * @return
     */
    List<HzVwoInformChanges> selectByVwoId(@Param("vwoId") Long vwoId);

    /**
     * 选择性更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(HzVwoInformChanges record);

    /**
     * 主键更新，无选择性更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzVwoInformChanges record);

    /**
     * 获取VWO号下的总关联数
     *
     * @param vwoId
     * @return
     */
    Long tellMeHowManyOfIt(@Param("vwoId") Long vwoId);
}