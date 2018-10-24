/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.fullCfg;

import com.connor.hozon.bom.bomSystem.bean.HzExFullCfgWithCfg;
import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfg;

import java.math.BigDecimal;
import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 全配置BOM一级清单2Y层对应的配置项
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzFullCfgWithCfgDao extends BasicDao<HzFullCfgWithCfg> {

    int deleteByPrimaryKey(Long id);

    int insert(List<HzFullCfgWithCfg> hzFullCfgWithCfgs);

    HzFullCfgWithCfg selectByPrimaryKey(Long id);

    List<HzFullCfgWithCfg> selectByMainID(BigDecimal flCfgVersion);

    int insertBomLine(List<HzFullCfgWithCfg> hzFullCfgWithCfgs);

    int updateByBomLinePuid(HzFullCfgWithCfg hzFullCfgWithCfg);

    List<HzFullCfgWithCfg> query2YCfgByProjectId(String projectId);

    HzFullCfgWithCfg selectByBomLineUidWithVersion(BigDecimal version, String puid);

    HzFullCfgWithCfg query2YCfgByBomLineId(String bomLineId);

    /**
     * 根据2Y和全配主键一起查询，连带出特性值对象
     *
     * @param version
     * @param puid
     * @return
     */
    HzExFullCfgWithCfg selectByBLOutWithCfg(BigDecimal version, String puid);
    /**
     * 根据2Y和全配主键一起查询，连带出特性值对象和BOMLine对象
     *
     * @param version
     * @param puid
     * @return
     */
    HzExFullCfgWithCfg selectByBLOutWithCfgAndBL(BigDecimal version, String puid);

}