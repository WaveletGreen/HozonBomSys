package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgWithCfgChangeDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgModelChange;
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
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public HzFullCfgWithCfgChange selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int insertList(List<HzFullCfgWithCfgChange> hzFullCfgWithCfgChanges) {
        return baseSQLUtil.executeInsert(hzFullCfgWithCfgChanges,clzName+"insertList");
    }

    @Override
    public List<HzFullCfgWithCfgChange> selectByMainId(Integer id) {
        return baseSQLUtil.executeQueryByPass(new HzFullCfgWithCfgChange(), id,clzName+"selectByMainId");
    }
}
