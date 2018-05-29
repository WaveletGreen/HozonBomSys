package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0ColorSet;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 14:48
 */
@Service("hzCfg0ColorSetDao")
public class HzCfg0ColorSetDaoImpl implements HzCfg0ColorSetDao {

    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public List<HzCfg0ColorSet> queryAll2() {
        List<HzCfg0ColorSet> colorSet = baseSQLUtil.executeQuery(new HzCfg0ColorSet(), "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.selectAll");
        return colorSet;
    }

    @Override
    public HzCfg0ColorSet selectById(HzCfg0ColorSet entity) {
        return baseSQLUtil.executeQueryById(entity, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.selectByPrimaryKey");
    }

    @Override
    public int updateOne(HzCfg0ColorSet entity) {
        return baseSQLUtil.executeUpdate(entity, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.updateByPrimaryKey");
    }

    @Override
    public int deleteByList(List<HzCfg0ColorSet> entity) {
        return baseSQLUtil.executeDelete(entity, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.deleteByBatch");
    }

    @Override
    public int insertOne(HzCfg0ColorSet entity) {
        return baseSQLUtil.executeInsert(entity, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.insertOne");
    }

    private final void preCheck() {
        if (baseSQLUtil == null) {
            baseSQLUtil = new BaseSQLUtil();
            System.out.println(IBaseSQLUtil.class.getName() + "没有自动装配");
        }
    }
}
