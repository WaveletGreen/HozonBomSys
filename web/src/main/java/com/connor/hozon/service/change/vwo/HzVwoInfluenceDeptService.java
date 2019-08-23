/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.vwo;

import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoInfluenceDept;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface HzVwoInfluenceDeptService {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int doDeleteByPrimaryKey(Long id);

    /**
     * 全插入
     *
     * @param record
     * @return
     */
    int doInsert(HzVwoInfluenceDept record);

    /**
     * 非空插入
     *
     * @param record
     * @return
     */
    int doInsertSelective(HzVwoInfluenceDept record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */

    HzVwoInfluenceDept doSelectByPrimaryKey(Long id);
    /**
     * 根据VWO ID查找影响部门
     *
     * @param vwoId
     * @return
     */
    HzVwoInfluenceDept doSelectByVwoId(@Param("vwoId") Long vwoId);
    /**
     * 主键+非空更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKeySelective(HzVwoInfluenceDept record);

    /**
     * 主键全更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKey(HzVwoInfluenceDept record);
}
