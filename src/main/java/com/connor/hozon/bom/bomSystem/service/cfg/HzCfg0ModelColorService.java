package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0ColorSet;
import sql.pojo.cfg.HzCfg0ModelColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 10:54
 */
@Service("hzCfg0ModelColorService")
public class HzCfg0ModelColorService {
    @Autowired
    HzCfg0ModelColorDao hzCfg0ModelColorDao;

    public List<HzCfg0ModelColor> doLoadModelColorByMainId(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.selectByMainId(color);
    }

    public boolean doUpdateOne(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.updateByPrimaryKey(color) == 1 ? true : false;
    }

    public boolean doInsertOne(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.insertOne(color) > 0 ? true : false;
    }

    public HzCfg0ModelColor doGetById(HzCfg0ModelColor color) {
        return hzCfg0ModelColorDao.selectByPrimaryKey(color);
    }

    public Map<String, Object> doLoadAll() {
        Map<String, Object> result = new HashMap<>();
        List<HzCfg0ModelColor> colorSet = hzCfg0ModelColorDao.selectAll();
        result.put("totalCount", colorSet.size());
        result.put("result", colorSet);
        return result;
    }

    public int doDelete(List<HzCfg0ModelColor> colors) {
        return hzCfg0ModelColorDao.deleteByBatch(colors);
    }
}
