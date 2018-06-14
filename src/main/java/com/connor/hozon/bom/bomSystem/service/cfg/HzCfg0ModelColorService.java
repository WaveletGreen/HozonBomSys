package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0ModelColor;
import sql.pojo.cfg.HzCfg0OptionFamily;
import sql.redis.SerializeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 10:54
 */
@Service("hzCfg0ModelColorService")
public class HzCfg0ModelColorService {
    @Autowired
    HzCfg0ModelColorDao hzCfg0ModelColorDao;
    @Autowired
    HzCfg0OptionFamilyDao hzCfg0OptionFamilyDao;

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

    public Map<String, Object> doLoadAll(String projectPuid) {
        Map<String, Object> result = new HashMap<>();
        List<HzCfg0ModelColor> colorSet = hzCfg0ModelColorDao.selectAll(projectPuid);
        result.put("totalCount", colorSet.size());
        List<Map<String, String>> res = new ArrayList<>();
        colorSet.forEach(color -> {
            Map<String, String> _result = new HashMap<>();
            _result.put("puid", color.getPuid());
            _result.put("codeOfColorModel", color.getpCodeOfColorfulModel());
            _result.put("descOfColorModel", color.getpDescOfColorfulModel());
            _result.put("modelShell", color.getpModelShellOfColorfulModel());
            _result.put("modeColorIsMultiply", color.getpColorIsMultiply());
            Object o = SerializeUtil.unserialize(color.getpColorfulMapBlock());
            if (o instanceof HashMap) {
                HashMap<String, String> _map = (HashMap) o;
                _map.forEach((key, value) ->
                        _result.put(key==null?"":key, value==null?"":key)
                );
            }
            res.add(_result);
        });
        result.put("result", res);
        return result;
    }

    public int doDelete(List<HzCfg0ModelColor> colors) {
        return hzCfg0ModelColorDao.deleteByBatch(colors);
    }
}
