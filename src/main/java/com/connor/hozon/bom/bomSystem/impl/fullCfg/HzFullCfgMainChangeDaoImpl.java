package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainChangeDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgMain;
import sql.pojo.cfg.fullCfg.HzFullCfgMainChange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public HzFullCfgMainChange selectLastByProjectUid(String projectUid) {
        HzFullCfgMainChange hzFullCfgMainChange = new HzFullCfgMainChange();
        hzFullCfgMainChange.setProjectUid(projectUid);
        return baseSQLUtil.executeQueryById(hzFullCfgMainChange,clzName+"selectLastByProjectUid");
    }

    @Override
    public int updateStatusByOrderId(Long orderId, int status) {
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("orderId",orderId);
        return baseSQLUtil.executeUpdate(map,clzName+"updateStatusByOrderId");
    }

    @Override
    public int deleteById(Long mainId) {
        return baseSQLUtil.executeDelete(mainId,clzName+"deleteById");
    }

    @Override
    public List<HzFullCfgMainChange> selectNotEffectByProjectUid(String projectId) {
        HzFullCfgMainChange hzFullCfgMainChange = new HzFullCfgMainChange();
        hzFullCfgMainChange.setProjectUid(projectId);
        hzFullCfgMainChange.setStatus(0);
        return baseSQLUtil.executeQuery(hzFullCfgMainChange,clzName+"selectNotEffectByProjectUid");
    }
}
