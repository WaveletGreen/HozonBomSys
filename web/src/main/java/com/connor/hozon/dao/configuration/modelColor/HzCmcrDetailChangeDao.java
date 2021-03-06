/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.dao.configuration.modelColor;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCmcrChange;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCmcrDetailChange;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 基础车型详情数据变更dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
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

    /**
     *批量插入变更前从数据
     * @param hzCmcrDetailChangesAfter
     * @return
     * @throws Exception
     */
    int insertDetailAfterList(List<HzCmcrDetailChange> hzCmcrDetailChangesAfter) throws Exception;

    /**
     * 查询最近一次变更后从数据
     * @param hzCmcrDetailChangesQuery
     * @return
     * @throws Exception
     */
    List<HzCmcrDetailChange> selectLastAfter(List<HzCmcrDetailChange> hzCmcrDetailChangesQuery)throws Exception;

    /**
     * 批量插入变更前从数据
     * @param hzCmcrDetailChangesQuery
     * @return
     * @throws Exception
     */
    int insertDetailBeforeList(List<HzCmcrDetailChange> hzCmcrDetailChangesQuery) throws Exception;

    List<HzCmcrDetailChange> doQueryCmcrDetailChangBeforAndAfter(List<HzCmcrDetailChange> hzCmcrDetailChanges);

    List<HzCmcrDetailChange> doQueryCmcrDetailChangBefor(HzCmcrChange hzCmcrChange);

    List<HzCmcrDetailChange> doQueryCmcrDetailChangFirst(HzCmcrChange hzCmcrChange);

    List<HzCmcrDetailChange> doQueryCmcrDetailChangFirstAfter(HzCmcrChange hzCmcrChange);

    List<HzCmcrDetailChange> doQueryCmcrDetailChangAfter(HzCmcrChange hzCmcrChange);


    List<HzCmcrDetailChange> doQueryCmcrDetailByMainChange(List<HzCmcrChange> hzCmcrChangeListBefor);

}