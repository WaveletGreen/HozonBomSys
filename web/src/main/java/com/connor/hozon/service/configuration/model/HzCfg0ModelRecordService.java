/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.configuration.model;

import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 13:57
 * @Modified By:
 */
public interface HzCfg0ModelRecordService {
    HzCfg0ModelRecord doGetById(String puid);

    boolean updateBasic(HzCfg0ModelRecord modelRecord);

    boolean updateModelName(HzCfg0ModelRecord modelRecord);


    boolean insert(List<HzCfg0ModelRecord> modelRecord);

    /**
     * 根据项目的ID，查找所有的车型
     *
     * @param projectUid 项目ID
     * @return 一组车型模型
     */
    List<HzCfg0ModelRecord> selectByProjectUid(String projectUid);

    /**
     * 主键删除一个基本模型
     *
     * @param puid
     * @return
     */
    boolean deleteByUid(String puid);
}
