/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.modelColor;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.modelColor.HzColorLvl2Model;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 二级配色方案
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzColorLvl2ModelDao  extends BasicDao<HzColorLvl2Model>{
    /**
     * 主键删除
     *
     * @param puid 主键
     * @return
     */
    int deleteByPrimaryKey(String puid);


    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */
    HzColorLvl2Model selectByPrimaryKey(String puid);

    /**
     * 模型主键筛选
     *
     * @param puid 模型主键
     * @return
     */
    List<HzColorLvl2Model> selectByModelUid(String puid);


    /**
     * 根据模型和功能层查找是否已存在二级配色方案
     *
     * @param model
     * @return
     */
    HzColorLvl2Model selectByModelAndFunctionLvl(HzColorLvl2Model model);
}