/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzCfg0OfBomLineRecordDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzCfg0OfBomLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.fullCfg.HzCfg0OfBomLineRecord;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzCfg0OfBomLineService")
public class HzCfg0OfBomLineService implements IHzCfg0OfBomLineService {
    @Autowired
    HzCfg0OfBomLineRecordDao hzCfg0OfBomLineRecordDao;

    /**
     * 主键删除
     *
     * @param puid 主键值
     * @return
     */
    @Override
    public boolean doDeleteByPrimaryKey(String puid) {
        return hzCfg0OfBomLineRecordDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    /**
     * 插入单条数据
     *
     * @param record 配置值对应的bom行
     * @return
     */
    @Override
    public boolean doInsert(HzCfg0OfBomLineRecord record) {
        return hzCfg0OfBomLineRecordDao.insert(record) > 0 ? true : false;
    }

    /**
     * 根据主键搜索
     *
     * @param puid 主键值
     * @return
     */
    @Override
    public HzCfg0OfBomLineRecord doSelectByPrimaryKey(String puid) {
        return hzCfg0OfBomLineRecordDao.selectByPrimaryKey(puid);
    }

    /**
     * 根据主键更新
     *
     * @param record @{@link HzCfg0OfBomLineRecord}
     * @return
     */
    @Override
    public boolean doUpdateByPrimaryKey(HzCfg0OfBomLineRecord record) {
        return hzCfg0OfBomLineRecordDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    /**
     * 批量插入
     *
     * @param records @{@link HzCfg0OfBomLineRecord}的集合，进行批量插入
     * @return
     */
    @Override
    public boolean doInsertByBatch(List<HzCfg0OfBomLineRecord> records) {
        return hzCfg0OfBomLineRecordDao.insertByBatch(records) > 0 ? true : false;
    }

    /**
     * 根据项目ID和Bomline UID查找一条配置+BOMLine关联数据
     *
     * @param projectUid 项目UID
     * @param bomLineUid BOM行UID
     * @return
     */
    @Override
    public HzCfg0OfBomLineRecord doSelectByBLUidAndPrjUid(String projectUid, String bomLineUid) {
        HzCfg0OfBomLineRecord record = new HzCfg0OfBomLineRecord();
        record.setProjectUid(projectUid);
        record.setpBomlinepuid(bomLineUid);
        return hzCfg0OfBomLineRecordDao.selectByBLUidAndPrjUid(record);
    }
}
