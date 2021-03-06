/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.vwo;

import org.apache.ibatis.annotations.Param;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoInformChanges;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface HzVwoInformChangesService {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int doDeleteByPrimaryKey(@Param("id") Long id);

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    int doInsert(HzVwoInformChanges record);

    /**
     * 选择性插入
     *
     * @param record
     * @return
     */
    int doInsertSelective(HzVwoInformChanges record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    HzVwoInformChanges doSelectByPrimaryKey(@Param("id") Long id);

    /**
     * 根据VWO主键查询所有关联的人员
     *
     * @param vwoId
     * @return
     */
    List<HzVwoInformChanges> doSelectByVwoId(@Param("vwoId") Long vwoId);

    /**
     * 选择性更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKeySelective(HzVwoInformChanges record);

    /**
     * 主键更新，无选择性更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKey(HzVwoInformChanges record);

    /**
     * 获取VWO号下的总关联数
     *
     * @param vwoId
     * @return
     */
    Long tellMeHowManyOfIt(@Param("vwoId") Long vwoId);
}
