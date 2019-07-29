/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.fullCfg;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import sql.pojo.cfg.fullCfg.HzFullCfgModel;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfg;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 全配置BOM一级清单基础车型对应的配置项
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzFullCfgModelDao extends BasicDao<HzFullCfgModel> {

    int deleteByPrimaryKey(Long id);


    HzFullCfgModel selectByPrimaryKey(Long id);


    List<String> selectCfg(String puid);

    void insertCfgs(List<HzFullCfgModel> cfgs);

    List<HzFullCfgModel> selectByMainPuid(Long mainPuid);

    int updateByHzFullCfgModelList(List<HzFullCfgModel> hzFullCfgModels);

    int updateByBomLinePuid(HzFullCfgWithCfg hzFullCfgWithCfg);

    List<HzFullCfgModel> selectByModelUid(String modelUid);

    List<HzFullCfgModel> selectByModelUidWithMarks(HzFullCfgModel withCfg);

    int updateByHzFullCfgModelListCfg(List<HzFullCfgModel> hzFullCfgModels);

    int insertListAll(List<HzFullCfgModel> hzFullCfgModels);
}