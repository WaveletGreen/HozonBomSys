/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.interaction;

import cn.net.connor.hozon.dao.pojo.interaction.FeatureBomLineRelationHistory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/8 17:29
 * @Modified By:
 */
public interface FeatureBomLineRelationHistoryService {
    /**
     * 单车历史特性和bomline 2Y之间的对应关系
     */
    @Transactional
    void saveHistoryRelation(String projectId);

    /**
     * 批量插入
     * @param list
     */
    @Transactional
    void insertList(List<FeatureBomLineRelationHistory> list);
}