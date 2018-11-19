package com.connor.hozon.bom.bomSystem.impl.derivative;

import com.connor.hozon.bom.bomSystem.dao.derivative.HzDMBasicChangeDao;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzDMDetailChangeDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.derivative.HzDMBasicChangeBean;
import sql.pojo.cfg.derivative.HzDMDetailChangeBean;

import java.util.List;

@Configuration
public class HzDMDetailChangeDaoImpl extends BasicDaoImpl<HzDMDetailChangeBean> implements HzDMDetailChangeDao {

    private final static HzDMDetailChangeBean BASIC = new HzDMDetailChangeBean();

    public HzDMDetailChangeDaoImpl() {
        clz = HzDMDetailChangeDao.class;
    }

    @Override
    public int insertList(List<HzDMDetailChangeBean> hzDMDetailChangeBeans) {
        return baseSQLUtil.executeInsert(hzDMDetailChangeBeans,clz.getCanonicalName() + ".insertList");
    }

    @Override
    public List<HzDMDetailChangeBean> selectByBasic(List<HzDMBasicChangeBean> hzDMBasicChangeBeansBefor) {
        return baseSQLUtil.executeQueryByPass(new HzDMDetailChangeBean(), hzDMBasicChangeBeansBefor,clz.getCanonicalName() + ".selectByBasic");
    }
}
