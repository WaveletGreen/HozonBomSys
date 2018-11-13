package com.connor.hozon.bom.bomSystem.impl.derivative;

import com.connor.hozon.bom.bomSystem.dao.derivative.HzDMBasicChangeDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.derivative.HzDMBasicChangeBean;
import sql.pojo.cfg.derivative.HzDerivativeMaterielBasic;

import java.util.List;

@Configuration
public class HzDMBasicChangeDaoImpl extends BasicDaoImpl<HzDMBasicChangeBean> implements HzDMBasicChangeDao {

    private final static HzDMBasicChangeBean BASIC = new HzDMBasicChangeBean();

    public HzDMBasicChangeDaoImpl() {
        clz = HzDMBasicChangeDao.class;
    }

    @Override
    public int insertList(List<HzDMBasicChangeBean> hzDMBasicChangeBeans) {
        return baseSQLUtil.executeInsert(hzDMBasicChangeBeans,clz.getCanonicalName() + ".insertList");
    }
}
