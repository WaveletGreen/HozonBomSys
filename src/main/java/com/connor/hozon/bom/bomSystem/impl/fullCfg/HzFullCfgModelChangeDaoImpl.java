package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgModelChangeDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgModelChange;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;

import java.util.List;

@Configuration
public class HzFullCfgModelChangeDaoImpl extends BasicDaoImpl<HzFullCfgModelChange> implements HzFullCfgModelChangeDao {

    public HzFullCfgModelChangeDaoImpl() {
        clz = HzFullCfgModelChangeDao.class;
        clzName = clz.getCanonicalName()+".";
    }
    @Override
    public int insertList(List<HzFullCfgModelChange> hzFullCfgModelChanges) {
        return baseSQLUtil.executeInsert(hzFullCfgModelChanges, clzName+"insertList");
    }
}
