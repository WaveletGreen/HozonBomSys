/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzCfg0OfBomLineRecordDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzCfg0OfBomLineRecord;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public class HzCfg0OfBomLineRecordDaoImpl extends BasicDaoImpl<HzCfg0OfBomLineRecord> implements HzCfg0OfBomLineRecordDao {

    private final static HzCfg0OfBomLineRecord RECORD = new HzCfg0OfBomLineRecord();


    public HzCfg0OfBomLineRecordDaoImpl() {
        clz = HzCfg0OfBomLineRecordDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 主键删除
     *
     * @param puid
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, clzName + ".deleteByPrimaryKey");
    }


    /**
     * 根据主键搜索
     *
     * @param puid
     * @return
     */
    @Override
    public HzCfg0OfBomLineRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(RECORD, puid,
                clzName + ".selectByPrimaryKey", true);
    }

    /**
     * 根据项目ID和Bomline UID查找一条配置+BOMLine关联数据
     *
     * @param record
     * @return
     */
    @Override
    public HzCfg0OfBomLineRecord selectByBLUidAndPrjUid(HzCfg0OfBomLineRecord record) {
        return baseSQLUtil.executeQueryById(record,
                clzName + ".selectByBLUidAndPrjUid");
    }

    /**
     * 批量插入
     *
     * @param records
     * @return
     */
    @Override
    public int insertByBatch(List<HzCfg0OfBomLineRecord> records) {
        return baseSQLUtil.executeInsert(records,
                clzName + ".insertByBatch");
    }
}
