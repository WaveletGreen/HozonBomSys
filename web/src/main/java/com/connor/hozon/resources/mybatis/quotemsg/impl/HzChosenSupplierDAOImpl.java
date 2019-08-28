package com.connor.hozon.resources.mybatis.quotemsg.impl;

import com.connor.hozon.dao.BasicDaoImpl;
import com.connor.hozon.resources.domain.model.HzChosenSupplier;
import com.connor.hozon.resources.mybatis.quotemsg.HzChosenSupplierDAO;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class HzChosenSupplierDAOImpl extends BasicDaoImpl<HzChosenSupplier> implements HzChosenSupplierDAO {

    public HzChosenSupplierDAOImpl(){
        clz = HzChosenSupplierDAO.class;
        clzName = clz.getCanonicalName()+".";
    }

    @Override
    public int insert(HzChosenSupplier hzChosenSupplier) {
        return baseSQLUtil.executeInsert(hzChosenSupplier,clzName+"insert");
    }

    @Override
    public int insertSelective(HzChosenSupplier record) {
        return 0;
    }

    @Override
    public List<HzChosenSupplier> selectPage(HzChosenSupplier hzChosenSupplier) {
        return baseSQLUtil.executeQuery(hzChosenSupplier,clzName+"selectPage");
    }

    @Override
    public HzChosenSupplier selectById(Long id) {
        HzChosenSupplier hzChosenSupplier = new HzChosenSupplier();
        hzChosenSupplier.setId(id);
        return baseSQLUtil.executeQueryById(hzChosenSupplier,clzName+"selectById");
    }

    @Override
    public List<HzChosenSupplier> selectProjectId(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HzChosenSupplier(),projectPuid,clzName+"selectProjectId");
    }

    @Override
    public int update(HzChosenSupplier hzChosenSupplier) {
        return baseSQLUtil.executeUpdate(hzChosenSupplier,clzName+"update");
    }

    @Override
    public int delete(String[] ids) {
        List<HzChosenSupplier> hzChosenSuppliers = new ArrayList<>();
        for(String id : ids){
            HzChosenSupplier hzChosenSupplier = new HzChosenSupplier();
            hzChosenSupplier.setId(Long.valueOf(id));
            hzChosenSuppliers.add(hzChosenSupplier);
        }
        return baseSQLUtil.executeDelete(hzChosenSuppliers,clzName+"delete");
//        return baseSQLUtil.executeDelete(hzChosenSuppliers,clzName+"delete");
    }

    @Override
    public List<HzChosenSupplier> selectCreator(HzChosenSupplier hzChosenSupplierQuery) {
        return baseSQLUtil.executeQuery(hzChosenSupplierQuery,clzName+"selectCreator");
    }
}
