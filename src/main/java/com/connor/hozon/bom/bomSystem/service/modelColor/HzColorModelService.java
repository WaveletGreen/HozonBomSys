/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.modelColor;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorModelDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzColorModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.modelColor.HzCfg0ModelColorDetail;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzColorModelService")
public class HzColorModelService implements IHzColorModelService {
    @Autowired
    HzColorModelDao hzColorModelDao;

    /**
     * 主键删除
     *
     * @param puid 主键
     * @return
     */
    @Override
    public int doDeleteByPrimaryKey(String puid) {
        return hzColorModelDao.deleteByPrimaryKey(puid);
    }

    /**
     * 插入
     *
     * @param record 颜色+配置对象
     * @return
     */
    @Override
    public int doInsert(HzCfg0ModelColorDetail record) {
        return hzColorModelDao.insert(record);
    }

    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */
    @Override
    public HzCfg0ModelColorDetail doSelectByPrimaryKey(String puid) {
        return hzColorModelDao.selectByPrimaryKey(puid);
    }


    /**
     * 主键更新
     *
     * @param record 需要更新的对象
     * @return
     */
    @Override
    public int doUpdateByPrimaryKey(HzCfg0ModelColorDetail record) {
        return hzColorModelDao.updateByPrimaryKey(record);
    }

    /**
     * 根据颜色模型
     *
     * @param modelUid
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> doSelectByModelUid(String modelUid) {
        return hzColorModelDao.selectByModelUid(modelUid);
    }

    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> doSelectByModelUidWithColor(String modelUid) {
        return hzColorModelDao.selectByModelUidWithColor(modelUid);
    }

    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> doSelectByModelUidWithColor2(String modelUid) {
        return hzColorModelDao.selectByModelUidWithColor2(modelUid);
    }


    /**
     * 根据项目ID查找
     *
     * @param projectUid
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> doSelectByCfgMainUid(String projectUid) {
        return hzColorModelDao.selectByCfgMainUid(projectUid);
    }

    /**
     * 批量插入
     *
     * @param colorModels 颜色+配置记录集合
     * @return
     */
    @Override
    public int doInsertByBatch(List<HzCfg0ModelColorDetail> colorModels) {
        return hzColorModelDao.insertByBatch(colorModels);
    }

    /**
     * 根据颜色车型和配置更新颜色信息
     *
     * @param model 模型集合
     * @return
     */
    @Override
    public boolean doUpdateColorModelWithCfg(HzCfg0ModelColorDetail model) {
        return hzColorModelDao.updateColorModelWithCfg(model) > 0 ? true : false;
    }


}
