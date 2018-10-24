/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.cfg.vwo;

import sql.pojo.cfg.vwo.HzVwoInfluenceUser;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface IHzVwoInfluenceUserService {
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
    int doInsert(HzVwoInfluenceUser record);

    /**
     * 非空插入
     *
     * @param record
     * @return
     */
    int doInsertSelective(HzVwoInfluenceUser record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */

    HzVwoInfluenceUser doSelectByPrimaryKey(Long id);

    /**
     * 根据VWO主键进行查询
     *
     * @param vwoId
     * @return
     */
    HzVwoInfluenceUser doSelectByVwoId(Long vwoId);

    /**
     * 主键+非空更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKeySelective(HzVwoInfluenceUser record);

    /**
     * 全更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKey(HzVwoInfluenceUser record);
}
