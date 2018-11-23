/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.cfg;

import org.springframework.stereotype.Service;
import sql.pojo.cfg.modelColor.HzColorLvl2Model;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
public interface IHzColorLvl2ModelService {
    /**
     * 主键删除
     *
     * @param puid 主键
     * @return
     */
    int doDeleteByPrimaryKey(String puid);

    /**
     * 插入
     *
     * @param record 二级配色方案
     * @return
     */
    int doInsert(HzColorLvl2Model record) throws Exception;

    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */
    HzColorLvl2Model doSelectByPrimaryKey(String puid);

    /**
     * 主键更新
     *
     * @param record 二级配色方案
     * @return
     */
    int doUpdateByPrimaryKey(HzColorLvl2Model record) throws Exception;

    /**
     * 根据模型寻找所有2级配色方案
     * @param modelUid
     * @return
     */
    List<HzColorLvl2Model> doSelectByModelUid(String modelUid);

}
