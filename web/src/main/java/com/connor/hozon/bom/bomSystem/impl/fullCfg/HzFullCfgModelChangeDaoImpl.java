package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgModelChangeDao;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgModelChange;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;

import java.util.List;

@Repository
public class HzFullCfgModelChangeDaoImpl extends BasicDaoImpl<HzFullCfgModelChange> implements HzFullCfgModelChangeDao {

    public HzFullCfgModelChangeDaoImpl() {
        clz = HzFullCfgModelChangeDao.class;
        clzName = clz.getCanonicalName()+".";
    }
    @Override
    public int insertList(List<HzFullCfgModelChange> hzFullCfgModelChanges) {
        return baseSQLUtil.executeInsert(hzFullCfgModelChanges, clzName+"insertList");
    }

    @Override
    public List<HzFullCfgModelChange> selectByMainId(Integer id) {
        return baseSQLUtil.executeQueryByPass(new HzFullCfgModelChange(), id, clzName+"selectByMainId");
    }

}
