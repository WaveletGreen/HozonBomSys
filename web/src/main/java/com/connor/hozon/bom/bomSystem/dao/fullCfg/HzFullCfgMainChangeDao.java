package com.connor.hozon.bom.bomSystem.dao.fullCfg;


import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgMain;
import sql.pojo.cfg.fullCfg.HzFullCfgMainChange;

import java.util.List;

public interface HzFullCfgMainChangeDao {
    int insert(HzFullCfgMainChange record);

    int insertSelective(HzFullCfgMainChange record);

    HzFullCfgMainChange selectByChangeId(Integer orderChangeId);

    HzFullCfgMainChange selectLast(Long srcMainId);

    HzFullCfgMainChange selectLastByProjectUid(String projectUid);

    int updateStatusByOrderId(Long orderId, int status);

    int deleteById(Long mainId);

    List<HzFullCfgMainChange> selectNotEffectByProjectUid(String projectId);
}
