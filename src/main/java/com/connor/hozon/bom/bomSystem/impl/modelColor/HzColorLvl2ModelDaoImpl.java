/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.modelColor;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorLvl2ModelDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.modelColor.HzColorLvl2Model;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public class HzColorLvl2ModelDaoImpl extends BasicDaoImpl<HzColorLvl2Model> implements HzColorLvl2ModelDao {
    private final static HzColorLvl2Model lvl2Model = new HzColorLvl2Model();

    public HzColorLvl2ModelDaoImpl() {
        clz = HzColorLvl2ModelDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 主键删除
     *
     * @param puid 主键
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid,
                clzName + ".deleteByPrimaryKey");
    }


    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */
    @Override
    public HzColorLvl2Model selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(lvl2Model, puid,
                clzName + ".selectByPrimaryKey", true);
    }

    /**
     * 模型主键筛选
     *
     * @param pModelUid 模型主键
     * @return
     */
    @Override
    public List<HzColorLvl2Model> selectByModelUid(@Param("pModelUid") String pModelUid) {
        return baseSQLUtil.executeQueryByPass(lvl2Model, pModelUid,
                clzName + ".selectByModelUid");
    }


    /**
     * 根据模型和功能层查找是否已存在二级配色方案
     *
     * @param model
     * @return
     */
    @Override
    public HzColorLvl2Model selectByModelAndFunctionLvl(HzColorLvl2Model model) {
        return baseSQLUtil.executeQueryById(model,
                clzName + ".selectByModelAndFunctionLvl");
    }


}
