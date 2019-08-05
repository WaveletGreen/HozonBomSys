//package com.connor.hozon.bom.bomSystem.impl.derivative;
//
//import cn.net.connor.hozon.dao.dao.configuration.derivative.HzDMDetailChangeDao;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import org.springframework.stereotype.Repository;
//import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDMBasicChangeBean;
//import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDMDetailChangeBean;
//
//import java.util.List;
//
//@Repository
//public class HzDMDetailChangeDaoImpl extends BasicDaoImpl<HzDMDetailChangeBean> implements HzDMDetailChangeDao {
//
//    private final static HzDMDetailChangeBean BASIC = new HzDMDetailChangeBean();
//
//    public HzDMDetailChangeDaoImpl() {
//        clz = HzDMDetailChangeDao.class;
//    }
//
//    @Override
//    public int insertList(List<HzDMDetailChangeBean> hzDMDetailChangeBeans) {
//        return baseSQLUtil.executeInsert(hzDMDetailChangeBeans,clz.getCanonicalName() + ".insertList");
//    }
//
//    @Override
//    public List<HzDMDetailChangeBean> selectByBasic(List<HzDMBasicChangeBean> hzDMBasicChangeBeansBefor) {
//        return baseSQLUtil.executeQueryByPass(new HzDMDetailChangeBean(), hzDMBasicChangeBeansBefor,clz.getCanonicalName() + ".selectByBasic");
//    }
//}
