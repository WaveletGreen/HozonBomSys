/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.configuration.dao.modelColor.impl;

import com.connor.hozon.bom.configuration.dao.modelColor.HzColorLvl2ModelDao;
import com.connor.hozon.bom.configuration.dao.BasicDaoImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzColorLvl2Model;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Repository
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
