package com.connor.hozon.bom.bomSystem.dao.modelColor;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import sql.pojo.cfg.modelColor.HzCmcrDetailChange;

public interface HzCmcrDetailChangeDao extends BasicDao<HzCmcrDetailChange>{
    /**
     * 插入变更后数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    int insertDetailAfter(HzCmcrDetailChange cmcr) throws Exception;

    /**
     * 插入变更前数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    int insertDetailBefore(HzCmcrDetailChange cmcr) throws Exception;

    /**
     * 插入变更后数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    int insertDetailAfterSelective(HzCmcrDetailChange cmcr) throws Exception;

    /**
     * 插入变更前数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    int insertDetailBeforeSelective(HzCmcrDetailChange cmcr) throws Exception;

    /**
     * 变更后数据主键查找
     *
     * @param id 主键
     * @return 变更对象
     */
    HzCmcrDetailChange selectDetailAfterByPrimaryKey(Long id) throws Exception;

    /**
     * 变更前数据主键查找
     *
     * @param id 主键
     * @return 变更对象
     */
    HzCmcrDetailChange selectDetailBeforeByPrimaryKey(Long id) throws Exception;

    /**
     * 主键删除变更后数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteDetailAfterByPrimaryKey(Long id) throws Exception;

    /**
     * 主键删除变更前数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteDetailBeforeByPrimaryKey(Long id) throws Exception;

    /**
     * 主键更新变更后数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    int updateDetailAfterByPrimaryKey(HzCmcrDetailChange cmcr) throws Exception;

    /**
     * 主键更新变更前数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    int updateDetailBeforeByPrimaryKey(HzCmcrDetailChange cmcr) throws Exception;

    /**
     * 主键选择更新变更前数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    int updateDetailAfterByPrimaryKeySelective(HzCmcrDetailChange cmcr) throws Exception;

    /**
     * 主键选择更新变更后数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    int updateDetailBeforeByPrimaryKeySelective(HzCmcrDetailChange cmcr) throws Exception;
}