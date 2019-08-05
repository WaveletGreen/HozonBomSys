package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgWithCfgChangeDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgWithCfgChange;

import java.util.List;

@Repository
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
