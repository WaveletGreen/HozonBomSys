package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainChangeDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgMainChange;

@Configuration
public class HzFullCfgMainChangeDaoImpl  extends BasicDaoImpl<HzFullCfgMainChange> implements HzFullCfgMainChangeDao {

    public HzFullCfgMainChangeDaoImpl() {
        clz = HzFullCfgMainChangeDao.class;
        clzName = clz.getCanonicalName()+".";
    }

    @Override
    public HzFullCfgMainChange selectByChangeId(Integer orderChangeId) {
        HzFullCfgMainChange hzFullCfgMainChange = new HzFullCfgMainChange();
        hzFullCfgMainChange.setChangeOrderId(orderChangeId);
        return baseSQLUtil.executeQueryById(hzFullCfgMainChange, clzName+"selectByChangeId");
    }

    @Override
    public HzFullCfgMainChange selectLast(Long srcMainId) {
        HzFullCfgMainChange hzFullCfgMainChange = new HzFullCfgMainChange();
        hzFullCfgMainChange.setSrcMainId(srcMainId);
        return baseSQLUtil.executeQueryById(hzFullCfgMainChange,clzName+"selectLastFullCfg");
    }
}
