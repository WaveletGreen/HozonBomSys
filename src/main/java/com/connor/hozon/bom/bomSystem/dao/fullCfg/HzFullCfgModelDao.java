package com.connor.hozon.bom.bomSystem.dao.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import sql.pojo.cfg.fullCfg.HzFullCfgModel;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfg;

import java.math.BigDecimal;
import java.util.List;

public interface HzFullCfgModelDao extends BasicDao<HzFullCfgModel> {

    int deleteByPrimaryKey(BigDecimal id);


    HzFullCfgModel selectByPrimaryKey(BigDecimal id);


    List<String> selectCfg(String puid);

    void insertCfgs(List<HzFullCfgModel> cfgs);

    List<HzFullCfgModel> selectByMainPuid(BigDecimal mainPuid);

    int updateByHzFullCfgModelList(List<HzFullCfgModel> hzFullCfgModels);

    int updateByBomLinePuid(HzFullCfgWithCfg hzFullCfgWithCfg);

    List<HzFullCfgModel> selectByModelUid(String modelUid);

    List<HzFullCfgModel> selectByModelUidWithMarks(HzFullCfgModel withCfg);

}