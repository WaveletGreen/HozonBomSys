package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0ModelColor;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 10:53
 */
@Service("hzCfg0ModelColorDao")
public class HzCfg0ModelColorDaoImpl implements HzCfg0ModelColorDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public List<HzCfg0ModelColor> selectByMainId(HzCfg0ModelColor color) {
        return baseSQLUtil.executeQuery(color, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao.selectByMainId");
    }

    @Override
    public int updateByPrimaryKey(HzCfg0ModelColor color) {
        return baseSQLUtil.executeUpdate(color, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao.updateByPrimaryKey");
    }

    @Override
    public HzCfg0ModelColor selectByPrimaryKey(HzCfg0ModelColor color) {
        return baseSQLUtil.executeQueryById(color, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao.selectByPrimaryKey");
    }

    @Override
    public List<HzCfg0ModelColor> selectAll(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0ModelColor(), projectPuid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao.selectAll");
    }

    @Override
    public int insertOne(HzCfg0ModelColor color) {
        return baseSQLUtil.executeInsert(color, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao.insertOne");
    }

    /**
     * 更新旧数据，将旧数据的大对象设置为null
     *
     * @param color 旧数据
     * @return
     */
    @Override
    public int updateOldData(HzCfg0ModelColor color) {
        return baseSQLUtil.executeUpdate(color, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao.updateOldData");
    }

    @Override
    public int deleteByBatch(List<HzCfg0ModelColor> colors) {
        return baseSQLUtil.executeDelete(colors, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao.deleteByBatch");
    }

    @Override
    public List<HzCfg0ModelColor> selectByPuids(List<HzCfg0ModelColor> colors) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0ModelColor(),colors,"com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao.selectByPuids");
    }

    @Override
    public int updateListData(List<HzCfg0ModelColor> hzCfg0ModelColors) {
        return baseSQLUtil.executeUpdate(hzCfg0ModelColors, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao.updateListData");
    }
}
