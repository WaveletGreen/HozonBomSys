/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgModelDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgModel;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgWithCfg;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Repository
public class HzFullCfgModelDaoImpl extends BasicDaoImpl<HzFullCfgModel> implements HzFullCfgModelDao {
    private static final HzFullCfgModel HZ_FULL_CFG_MODEL = new HzFullCfgModel();

    public HzFullCfgModelDaoImpl() {
        clz = HzFullCfgModelDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        HZ_FULL_CFG_MODEL.setId(id);
        return baseSQLUtil.executeDelete(HZ_FULL_CFG_MODEL,
                clzName + ".deleteByPrimaryKey");
    }

    @Override
    public HzFullCfgModel selectByPrimaryKey(Long id) {
        HZ_FULL_CFG_MODEL.setId(id);
        return baseSQLUtil.executeQueryById(HZ_FULL_CFG_MODEL,
                clzName + ".selectByPrimaryKey");

    }

    @Override
    public List<String> selectCfg(String puid) {
        return baseSQLUtil.executeQuery(puid,
                clzName + ".selectCfg");
    }

    @Override
    public void insertCfgs(List<HzFullCfgModel> cfgs) {
        baseSQLUtil.executeInsert(cfgs,
                clzName + ".insertCfgs");
    }

    @Override
    public List<HzFullCfgModel> selectByMainPuid(Long mainPuid) {
        HZ_FULL_CFG_MODEL.setFlModVersion(mainPuid);
        return baseSQLUtil.executeQuery(HZ_FULL_CFG_MODEL,
                clzName + ".selectByMainPuid");
    }

    @Override
    public int updateByHzFullCfgModelList(List<HzFullCfgModel> hzFullCfgModels) {
        return baseSQLUtil.executeUpdate(hzFullCfgModels,
                clzName + ".updateByHzFullCfgModelList");
    }

    @Override
    public int updateByBomLinePuid(HzFullCfgWithCfg hzFullCfgWithCfg) {
        return baseSQLUtil.executeUpdate(hzFullCfgWithCfg,
                clzName + ".updateByBomLinePuid");
    }

    @Override
    public List<HzFullCfgModel> selectByModelUid(String modelUid) {
        HZ_FULL_CFG_MODEL.setModModelUid(modelUid);
        return baseSQLUtil.executeQuery(HZ_FULL_CFG_MODEL,
                clzName + ".selectByModelUid");
    }

    @Override
    public List<HzFullCfgModel> selectByModelUidWithMarks(HzFullCfgModel model) {
        return baseSQLUtil.executeQuery(model,
                clzName + ".selectByModelUidWithMarks");
    }

    @Override
    public int updateByHzFullCfgModelListCfg(List<HzFullCfgModel> hzFullCfgModels) {
        return  baseSQLUtil.executeUpdate(hzFullCfgModels, clzName+".updateByHzFullCfgModelListCfg");
    }

    @Override
    public int insertListAll(List<HzFullCfgModel> hzFullCfgModels) {
        return baseSQLUtil.executeInsert(hzFullCfgModels,clzName+".insertListAll");
    }
}
