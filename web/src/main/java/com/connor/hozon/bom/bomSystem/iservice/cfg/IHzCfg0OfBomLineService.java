/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.cfg;

import org.springframework.context.annotation.Configuration;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzCfg0OfBomLineRecord;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public interface IHzCfg0OfBomLineService {

    /**
     * 主键删除
     *
     * @param puid
     * @return
     */
    boolean doDeleteByPrimaryKey(String puid);

    /**
     * 插入单条数据
     *
     * @param record
     * @return
     */
    boolean doInsert(HzCfg0OfBomLineRecord record);

    /**
     * 根据主键搜索
     *
     * @param puid
     * @return
     */
    HzCfg0OfBomLineRecord doSelectByPrimaryKey(String puid);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    boolean doUpdateByPrimaryKey(HzCfg0OfBomLineRecord record);

    /**
     * 批量插入
     *
     * @param records
     * @return
     */
    boolean doInsertByBatch(List<HzCfg0OfBomLineRecord> records);

    /**
     * 根据项目ID和Bomline UID查找一条配置+BOMLine关联数据
     *
     * @param projectUid 项目UID
     * @param bomLineUid BOM行UID
     * @return
     */
    HzCfg0OfBomLineRecord doSelectByBLUidAndPrjUid(String projectUid, String bomLineUid);
}
