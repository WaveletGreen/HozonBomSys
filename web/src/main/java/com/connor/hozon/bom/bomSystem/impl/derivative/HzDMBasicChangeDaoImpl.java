//package com.connor.hozon.bom.bomSystem.impl.derivative;
//
//import cn.net.connor.hozon.dao.dao.configuration.derivative.HzDMBasicChangeDao;
//import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzComposeDelDto;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import org.springframework.stereotype.Repository;
//import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDMBasicChangeBean;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class HzDMBasicChangeDaoImpl extends BasicDaoImpl<HzDMBasicChangeBean> implements HzDMBasicChangeDao {
//
//    private final static HzDMBasicChangeBean BASIC = new HzDMBasicChangeBean();
//
//    public HzDMBasicChangeDaoImpl() {
//        clz = HzDMBasicChangeDao.class;
//    }
//
//    @Override
//    public int insertList(List<HzDMBasicChangeBean> hzDMBasicChangeBeans) {
//        return baseSQLUtil.executeInsert(hzDMBasicChangeBeans,clz.getCanonicalName() + ".insertList");
//    }
//
//    @Override
//    public List<HzDMBasicChangeBean> selectByFormid(Long changeFromId) {
//        return baseSQLUtil.executeQueryByPass(new HzDMBasicChangeBean(),changeFromId, clz.getCanonicalName() + ".selectByFormid");
//    }
//
//    @Override
//    public HzDMBasicChangeBean selectBefor(HzDMBasicChangeBean hzDMBasicChangeBeanAfter) {
//        return baseSQLUtil.executeQueryById(hzDMBasicChangeBeanAfter, clz.getCanonicalName() + ".selectBefor");
//    }
//
//    @Override
//    public List<HzDMBasicChangeBean> selectAfter(Long formId) {
//        HzDMBasicChangeBean hzDMBasicChangeBean = new HzDMBasicChangeBean();
//        hzDMBasicChangeBean.setFormId(formId);
//        return baseSQLUtil.executeQuery(hzDMBasicChangeBean, clz.getCanonicalName() + ".selectAfter");
//    }
//
//    @Override
//    public List<HzDMBasicChangeBean> selectLastById(List<HzComposeDelDto> delDtos) {
//        return baseSQLUtil.executeQueryByPass(new HzDMBasicChangeBean(),delDtos,clz.getCanonicalName()+".selectLastById");
//    }
//
//    @Override
//    public int updateStatusByOrderId(Long orderId, int status) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("orderId",orderId);
//        map.put("status",status);
//        return baseSQLUtil.executeUpdate(map,clz.getCanonicalName()+".updateStatusByOrderId");
//    }
//
//    @Override
//    public int deleteByChangeIds(List<HzDMBasicChangeBean> hzDMBasicChangeBeans) {
//        return baseSQLUtil.executeDelete(hzDMBasicChangeBeans,clz.getCanonicalName()+".deleteByChangeIds");
//    }
//
//    @Override
//    public List<HzDMBasicChangeBean> selectNotEffect(List<Long> changeMaterielFeatureIds) {
//        return baseSQLUtil.executeQueryByPass(new HzDMBasicChangeBean(),changeMaterielFeatureIds,clz.getCanonicalName()+".selectNotEffect");
//    }
//}
