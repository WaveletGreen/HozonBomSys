/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.bean.HzExFullCfgWithCfg;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgWithCfgDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfg;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public class HzFullCfgWithCfgDaoImpl extends BasicDaoImpl<HzFullCfgWithCfg> implements HzFullCfgWithCfgDao {
    private static final HzFullCfgWithCfg WITH_CFG = new HzFullCfgWithCfg();

    public HzFullCfgWithCfgDaoImpl() {
        clz = HzFullCfgWithCfgDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        WITH_CFG.setId(id);
        return baseSQLUtil.executeDelete(WITH_CFG,
                clzName + ".deleteByPrimaryKey");
    }

    @Override
    public int insert(List<HzFullCfgWithCfg> hzFullCfgWithCfgs) {
        return baseSQLUtil.executeInsert(hzFullCfgWithCfgs,
                clzName + ".insert");
    }


    @Override
    public HzFullCfgWithCfg selectByPrimaryKey(Long id) {
        WITH_CFG.setId(id);
        return baseSQLUtil.executeQueryById(WITH_CFG,
                clzName + ".selectByPrimaryKey");
    }

    @Override
    public List<HzFullCfgWithCfg> selectByMainID(Long flCfgVersion) {
        WITH_CFG.setFlCfgVersion(flCfgVersion);
        return baseSQLUtil.executeQuery(WITH_CFG,
                clzName + ".selectByMainID");
    }

    @Override
    public int insertBomLine(List<HzFullCfgWithCfg> hzFullCfgWithCfgs) {
        return baseSQLUtil.executeInsert(hzFullCfgWithCfgs,
                clzName + ".insertBomLine");
    }

    @Override
    public int updateByBomLinePuid(HzFullCfgWithCfg hzFullCfgWithCfg) {
        return baseSQLUtil.executeUpdate(hzFullCfgWithCfg,
                clzName + ".updateByBomLinePuid");
    }

    @Override
    public List<HzFullCfgWithCfg> query2YCfgByProjectId(String projectId) {
        return baseSQLUtil.executeQueryByPass(new HzFullCfgWithCfg(), projectId,
                clzName + ".query2YCfgByProjectId");
    }

    @Override
    public HzFullCfgWithCfg selectByBomLineUidWithVersion(Long version, String puid) {
        HzFullCfgWithCfg cfg = new HzFullCfgWithCfg();
        cfg.setFlCfgVersion(version);
        cfg.setCfgBomlineUid(puid);
        return baseSQLUtil.executeQueryById(cfg,
                clzName + ".selectByBomLineUidWithVersion");
    }

    @Override
    public HzFullCfgWithCfg query2YCfgByBomLineId(String bomLineId) {
        return baseSQLUtil.executeQueryByPass(new HzFullCfgWithCfg(), bomLineId,
                clzName + ".query2YCfgByBomLineId", true);
    }

    @Override
    public HzExFullCfgWithCfg selectByBLOutWithCfg(Long version, String puid) {
        HzExFullCfgWithCfg cfg = new HzExFullCfgWithCfg();
        cfg.setFlCfgVersion(version);
        cfg.setCfgBomlineUid(puid);
        return baseSQLUtil.executeQueryById(cfg,
                clzName + ".selectByBLOutWithCfg");
    }

    /**
     * 根据2Y和全配主键一起查询，连带出特性值对象和BOMLine对象
     *
     * @param version
     * @param puid
     * @return
     */
    @Override
    public HzExFullCfgWithCfg selectByBLOutWithCfgAndBL(Long version, String puid) {
        HzExFullCfgWithCfg cfg = new HzExFullCfgWithCfg();
        cfg.setFlCfgVersion(version);
        cfg.setCfgBomlineUid(puid);
        return baseSQLUtil.executeQueryById(cfg,
                clzName + ".selectByBLOutWithCfgAndBL");
    }

    @Override
    public int insertAll(List<HzFullCfgWithCfg> hzFullCfgWithCfgs) {
        return baseSQLUtil.executeInsert(hzFullCfgWithCfgs,clzName+".insertAll");
    }

    @Override
    public HzFullCfgWithCfg selectBy2Yid(HzFullCfgWithCfg hzFullCfgWithCfg) {
        return baseSQLUtil.executeQueryById(hzFullCfgWithCfg,clzName+".selectBy2Yid");
    }


}
