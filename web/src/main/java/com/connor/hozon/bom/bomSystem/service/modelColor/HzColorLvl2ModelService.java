/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.modelColor;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorLvl2ModelDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzColorLvl2ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.modelColor.HzColorLvl2Model;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/8/3 11:04
 * @Modified By:
 */
@Service("hzColorLvl2ModelService")
public class HzColorLvl2ModelService implements IHzColorLvl2ModelService {
    @Autowired
    HzColorLvl2ModelDao hzColorLvl2ModelDao;

    /**
     * 主键删除
     *
     * @param puid 主键
     * @return
     */
    @Override
    public int doDeleteByPrimaryKey(String puid) {
        return hzColorLvl2ModelDao.deleteByPrimaryKey(puid);
    }

    /**
     * 插入
     *
     * @param record 二级配色方案
     * @return
     */
    @Override
    public int doInsert(HzColorLvl2Model record) throws Exception {
        return hzColorLvl2ModelDao.insert(record);
    }

    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */
    @Override
    public HzColorLvl2Model doSelectByPrimaryKey(String puid) {
        return hzColorLvl2ModelDao.selectByPrimaryKey(puid);
    }

    /**
     * 主键更新
     *
     * @param record 二级配色方案
     * @return
     */
    @Override
    public int doUpdateByPrimaryKey(HzColorLvl2Model record) throws Exception {
        return hzColorLvl2ModelDao.updateByPrimaryKey(record);
    }

    public HzColorLvl2Model doSelectByModelAndFunctionLvl(HzColorLvl2Model model) {
        return hzColorLvl2ModelDao.selectByModelAndFunctionLvl(model);
    }

    /**
     * 根据模型寻找所有2级配色方案
     *
     * @param modelUid
     * @return
     */
    public List<HzColorLvl2Model> doSelectByModelUid(String modelUid) {
        return hzColorLvl2ModelDao.selectByModelUid(modelUid);
    }
}
