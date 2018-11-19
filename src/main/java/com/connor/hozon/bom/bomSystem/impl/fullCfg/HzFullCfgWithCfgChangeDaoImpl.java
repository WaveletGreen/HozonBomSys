package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgWithCfgChangeDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfg;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfgChange;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class HzFullCfgWithCfgChangeDaoImpl extends BasicDaoImpl<HzFullCfgWithCfgChange> implements HzFullCfgWithCfgChangeDao {

    public HzFullCfgWithCfgChangeDaoImpl() {
        clz = HzFullCfgWithCfgChangeDao.class;
        clzName = clz.getCanonicalName()+".";
    }

    @Override
    public int deleteByPrimaryKey(BigDecimal id) {
        return 0;
    }

    @Override
    public HzFullCfgWithCfgChange selectByPrimaryKey(BigDecimal id) {
        return null;
    }

    @Override
    public int insertList(List<HzFullCfgWithCfgChange> hzFullCfgWithCfgChanges) {
        return baseSQLUtil.executeInsert(hzFullCfgWithCfgChanges,clzName+"insertList");
    }

    @Override
    public HzFullCfgWithCfg selectBy2Yid(HzFullCfgWithCfg hzFullCfgWithCfg) {
        return baseSQLUtil.executeQueryById(hzFullCfgWithCfg, clzName+"selectBy2Yid");
    }
}
