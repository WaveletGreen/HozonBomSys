/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.configuration.modelColor.impl;

import com.connor.hozon.service.configuration.modelColor.HzColorLvl2ModelService;
import com.connor.hozon.dao.modelColor.HzColorLvl2ModelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzColorLvl2Model;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/8/3 11:04
 * @Modified By:
 */
@Service
public class HzColorLvl2ModelServiceImpl implements HzColorLvl2ModelService {
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
