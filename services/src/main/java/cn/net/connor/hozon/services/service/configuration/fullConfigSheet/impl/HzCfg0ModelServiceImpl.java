/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */
package cn.net.connor.hozon.services.service.configuration.fullConfigSheet.impl;

import cn.net.connor.hozon.dao.dao.configuration.model.HzCfg0ModelDetailDao;
import cn.net.connor.hozon.dao.dao.configuration.model.HzCfg0ModelRecordDao;
import cn.net.connor.hozon.services.service.configuration.fullConfigSheet.HzCfg0ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelDetail;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzCfg0ModelService")
public class HzCfg0ModelServiceImpl implements HzCfg0ModelService {
    @Autowired
    HzCfg0ModelDetailDao hzCfg0ModelDetailDao;
    @Autowired
    HzCfg0ModelRecordDao hzCfg0ModelRecordDao;

//    @Deprecated
//    public boolean isExist(HzCfg0ModelDetail entity) {
//        return getOneByModelId(entity) != null ? true : false;
//    }

    /**
     * @param entity
     * @return sql.pojo.feature.model.HzCfg0ModelDetail
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据模型的ID，获取到相关的详细模型数据
     * @Date: 2018/5/21 17:06
     */
    public HzCfg0ModelDetail getOneByModelId(HzCfg0ModelDetail entity) {
        return hzCfg0ModelDetailDao.selectByModelId(entity);
    }

    /**
     * @param entity
     * @return sql.pojo.feature.model.HzCfg0ModelDetail
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据模型的ID，获取到相关的详细模型数据
     * @Date: 2018/5/21 17:06
     */
    public HzCfg0ModelDetail getOneByModelId2(HzCfg0ModelDetail entity) {
        return hzCfg0ModelDetailDao.selectByModelId2(entity);
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
        return hzCfg0ModelDetailDao.updateByPrimaryKey(detail) == 1 ? true : false;
    }

    /**
     * @param detail 1条模型详细信息数据
     * @return boolean
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行插入1条模型详细信息数据
     * @Date: 2018/5/21 17:05
     */
    public boolean doInsert(HzCfg0ModelDetail detail) {
        return hzCfg0ModelDetailDao.insertSelective(detail) > 0 ? true : false;
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

    /**
     * 根据模型ID删除模式数据
     *
     * @param modelId
     * @return
     */
    public int deleteModelById(String modelId) {
        return hzCfg0ModelRecordDao.deleteModelById(modelId);
    }
}
