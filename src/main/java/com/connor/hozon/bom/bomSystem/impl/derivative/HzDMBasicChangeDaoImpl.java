package com.connor.hozon.bom.bomSystem.impl.derivative;

import com.connor.hozon.bom.bomSystem.dao.derivative.HzDMBasicChangeDao;
import com.connor.hozon.bom.bomSystem.dto.cfg.compose.HzComposeDelDto;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import sql.pojo.cfg.derivative.HzDMBasicChangeBean;
import sql.pojo.cfg.derivative.HzDMDetailChangeBean;
import sql.pojo.cfg.derivative.HzDerivativeMaterielBasic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HzDMBasicChangeDaoImpl extends BasicDaoImpl<HzDMBasicChangeBean> implements HzDMBasicChangeDao {

    private final static HzDMBasicChangeBean BASIC = new HzDMBasicChangeBean();

    public HzDMBasicChangeDaoImpl() {
        clz = HzDMBasicChangeDao.class;
    }

    @Override
    public int insertList(List<HzDMBasicChangeBean> hzDMBasicChangeBeans) {
        return baseSQLUtil.executeInsert(hzDMBasicChangeBeans,clz.getCanonicalName() + ".insertList");
    }

    @Override
    public List<HzDMBasicChangeBean> selectByFormid(Long changeFromId) {
        return baseSQLUtil.executeQueryByPass(new HzDMBasicChangeBean(),changeFromId, clz.getCanonicalName() + ".selectByFormid");
    }

    @Override
    public List<HzDMBasicChangeBean> selectBefor(Long formId) {
        Map<String,Long> map = new HashMap<>();
        map.put("formId",formId);
        map.put("status",1L);
        return baseSQLUtil.executeQueryByPass(new HzDMBasicChangeBean(), map, clz.getCanonicalName() + ".selectBefor");
    }

    @Override
    public List<HzDMBasicChangeBean> selectAfter(Long formId) {
        Map<String,Long> map = new HashMap<>();
        map.put("formId",formId);
        map.put("status",0L);
        return baseSQLUtil.executeQueryByPass(new HzDMBasicChangeBean(), map, clz.getCanonicalName() + ".selectBefor");
    }

    @Override
    public List<HzDMBasicChangeBean> selectLastByPuid(List<HzComposeDelDto> delDtos) {
        return baseSQLUtil.executeQueryByPass(new HzDMBasicChangeBean(),delDtos,clz.getCanonicalName()+".selectLastByPuid");
    }

    @Override
    public int updateStatusByOrderId(Long orderId, int status) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("status",status);
        return baseSQLUtil.executeUpdate(map,clz.getCanonicalName()+".updateStatusByOrderId");
    }

    @Override
    public int updateByChangeIds(List<Long> changeMaterielFeatureIds) {
        return baseSQLUtil.executeDelete(changeMaterielFeatureIds,clz.getCanonicalName()+".updateByChangeIds");
    }
}
