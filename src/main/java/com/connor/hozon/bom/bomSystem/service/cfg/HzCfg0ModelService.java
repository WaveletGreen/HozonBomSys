package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelDetailDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0ModelDetail;
import sql.pojo.cfg.HzCfg0ModelRecord;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 14:42
 */
@Service("hzCfg0ModelService")
public class HzCfg0ModelService {
    @Autowired
    HzCfg0ModelDetailDao hzCfg0ModelDetailDao;
    @Autowired
    HzCfg0ModelRecordDao hzCfg0ModelRecordDao;

    @Deprecated
    public boolean isExist(HzCfg0ModelDetail entity) {
        return getOneByModelId(entity) != null ? true : false;
    }

    /**
     * @param entity
     * @return sql.pojo.cfg.HzCfg0ModelDetail
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据模型的ID，获取到相关的详细模型数据
     * @Date: 2018/5/21 17:06
     */
    public HzCfg0ModelDetail getOneByModelId(HzCfg0ModelDetail entity) {
        return hzCfg0ModelDetailDao.selectByModelId(entity);
    }

    /**
     * 根据主键查找1个车型模型
     *
     * @param puid
     * @return
     */
    public HzCfg0ModelRecord getModelByPuid(String puid) {
        return hzCfg0ModelRecordDao.selectByPrimaryKey(puid);
    }

    /**
     * @param detail
     * @return boolean
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行更新1条模型详细信息数据
     * @Date: 2018/5/21 17:06
     */
    public boolean doUpdateOne(HzCfg0ModelDetail detail) {
        return hzCfg0ModelDetailDao.updateOne(detail) == 1 ? true : false;
    }

    /**
     * @param detail 1条模型详细信息数据
     * @return boolean
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行插入1条模型详细信息数据
     * @Date: 2018/5/21 17:05
     */
    public boolean doInsertOne(HzCfg0ModelDetail detail) {
        return hzCfg0ModelDetailDao.insertOne(detail) > 0 ? true : false;
    }

    /**
     * 根据项目puid获取到车型集合数据
     *
     * @param projectPuid
     * @return
     */
    public List<HzCfg0ModelRecord> doSelectByProjectPuid(String projectPuid) {
        return hzCfg0ModelRecordDao.selectByProjectPuid(projectPuid);
    }

    public int deleteModelById(String modelId) {
        return hzCfg0ModelRecordDao.deleteModelById(modelId);
    }
}
