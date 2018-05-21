package com.connor.hozon.bom.banchOfFancyears.service.cfg;

import com.connor.hozon.bom.banchOfFancyears.dao.cfg.HzCfg0ColorSetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0ColorSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 14:42
 */
@Service("hzCfg0ColorSerService")
public class HzCfg0ColorSerService {
    @Autowired
    HzCfg0ColorSetDao hzCfg0ColorSetDao;

    /**
     * @return 颜色集合
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 搜索出所有的颜色信息
     * @Date: 2018/5/21 17:08
     */
    public Map<String, Object> queryAll2() {

        Map<String, Object> result = new HashMap<>();
        List<HzCfg0ColorSet> colorSet = hzCfg0ColorSetDao.queryAll2();
        result.put("totalCount", colorSet.size());
        result.put("result", colorSet);
        return result;
    }

    /**
     * @param entity
     * @return boolean
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据puid检查是否有颜色信息
     * @Date: 2018/5/21 17:08
     */
    public HzCfg0ColorSet getById(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.selectById(entity);
    }

    /**
     * @param entity 颜色对象
     * @return boolean 只更新1个
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行更细颜色信息，只更新1个
     * @Date: 2018/5/21 17:08
     */
    public boolean doUpdate(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.updateOne(entity) == 1 ? true : false;
    }

    /**
     * @param entity 颜色信息的集合
     * @return boolean
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行批量删除颜色信息
     * @Date: 2018/5/21 17:07
     */
    public boolean doDeleteByList(List<HzCfg0ColorSet> entity) {
        return hzCfg0ColorSetDao.deleteByList(entity) > 0 ? true : false;
    }

    /**
     * @param entity
     * @return boolean
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 添加一个颜色信息
     * @Date: 2018/5/21 17:07
     */
    public boolean doAddOne(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.insertOne(entity) > 0 ? true : false;
    }
}
