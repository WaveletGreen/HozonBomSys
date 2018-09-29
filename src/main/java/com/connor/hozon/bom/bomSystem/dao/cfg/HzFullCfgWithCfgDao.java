package com.connor.hozon.bom.bomSystem.dao.cfg;

import com.connor.hozon.bom.bomSystem.bean.HzExFullCfgWithCfg;
import sql.pojo.cfg.HzFullCfgWithCfg;

import java.math.BigDecimal;
import java.util.List;

public interface HzFullCfgWithCfgDao {

    int deleteByPrimaryKey(Long id);

    int insert(List<HzFullCfgWithCfg> hzFullCfgWithCfgs);

    int insertSelective(HzFullCfgWithCfg record);

    HzFullCfgWithCfg selectByPrimaryKey(Long id);

    List<HzFullCfgWithCfg> selectByMainID(BigDecimal flCfgVersion);

    int updateByPrimaryKeySelective(HzFullCfgWithCfg record);

    int updateByPrimaryKey(HzFullCfgWithCfg record);

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