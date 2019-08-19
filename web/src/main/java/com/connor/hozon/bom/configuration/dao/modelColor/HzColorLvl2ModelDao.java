/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.configuration.dao.modelColor;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzColorLvl2Model;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 二级配色方案
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
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