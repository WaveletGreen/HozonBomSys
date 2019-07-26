/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
package com.connor.hozon.bom.bomSystem.service.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.model.HzCfg0ModelDetailDao;
import com.connor.hozon.bom.bomSystem.dao.model.HzCfg0ModelRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.model.HzCfg0ModelDetail;
import sql.pojo.cfg.model.HzCfg0ModelRecord;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
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
     * @return sql.pojo.cfg0.model.HzCfg0ModelDetail
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据模型的ID，获取到相关的详细模型数据
     * @Date: 2018/5/21 17:06
     */
    public HzCfg0ModelDetail getOneByModelId(HzCfg0ModelDetail entity) {
        return hzCfg0ModelDetailDao.selectByModelId(entity);
    }

    /**
     * @param entity
     * @return sql.pojo.cfg0.model.HzCfg0ModelDetail
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

    public int deleteModelById(String modelId) {
        return hzCfg0ModelRecordDao.deleteModelById(modelId);
    }
}
