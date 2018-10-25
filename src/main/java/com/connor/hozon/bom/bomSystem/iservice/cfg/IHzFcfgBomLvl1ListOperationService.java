/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.cfg;

import sql.pojo.cfg.fullCfg.HzFcfgBomLvl1ListOperation;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface IHzFcfgBomLvl1ListOperationService {
    /**
     * 根据主键删除，真的删除
     * @param puid 主键，来自bomline
     * @return
     */
    boolean doDeleteByPrimaryKey(String puid);
    /**
     * 根据主键删除，逻辑删除，这只改状态值status=0
     * @param record 记录，包含主键，来自bomline
     * @return
     */
    boolean doLogicDeleteByPrimaryKey(HzFcfgBomLvl1ListOperation record);

    /**
     * 插入1条操作信息，插入应该为新增，反之为更新
     * @param record
     * @return
     */
    boolean doInsert(HzFcfgBomLvl1ListOperation record);

    /**
     * 根据主键获取操作信息
     * @param puid 主键，来自bomline
     * @return
     */
    HzFcfgBomLvl1ListOperation doSelectByPrimaryKey(String puid);

    /**
     * 更新操作，应设置P_OPRATION_TYPE_NAME为更新，操作人和操作日期
     * @param record 1条操作信息
     * @return
     */
    boolean doUpdateByPrimaryKey(HzFcfgBomLvl1ListOperation record);
}
