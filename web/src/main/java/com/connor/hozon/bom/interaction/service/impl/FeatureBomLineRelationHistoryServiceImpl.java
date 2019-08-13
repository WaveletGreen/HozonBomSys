/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.interaction.service.impl;

import cn.net.connor.hozon.dao.dao.interaction.FeatureBomLineRelationHistoryDao;
import cn.net.connor.hozon.dao.pojo.interaction.FeatureBomLineRelationHistory;
import cn.net.connor.hozon.dao.pojo.interaction.HzConfigBomLineBean;
import cn.net.connor.hozon.dao.dao.interaction.HzConfigBomColorDao;
import com.connor.hozon.bom.interaction.service.FeatureBomLineMapper;
import com.connor.hozon.bom.interaction.service.FeatureBomLineRelationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/8 17:30
 * @Modified By:
 */
@Service
public class FeatureBomLineRelationHistoryServiceImpl implements FeatureBomLineRelationHistoryService {
    @Autowired
    HzConfigBomColorDao hzConfigBomColorDao;
    @Autowired
    FeatureBomLineRelationHistoryDao featureBomLineRelationHistoryDao;

    @Transactional
    @Override
    public void saveHistoryRelation(String projectId) {
        //查询出当前发布过的正式进行结算的bomline对应的特性值数据，保留一份数据
        List<HzConfigBomLineBean> cache = hzConfigBomColorDao.selectAllConfigToBomline(projectId);
        List<FeatureBomLineRelationHistory> list = FeatureBomLineMapper.INSTANCE.mapToList(cache);
        featureBomLineRelationHistoryDao.deleteByProjectId(projectId);
        //批量插入数据
        featureBomLineRelationHistoryDao.insertList(list);
        //手动回滚
        //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

    @Transactional
    @Override
    public void insertList(List<FeatureBomLineRelationHistory> list) {
        int max = 1000;
        int total = list.size();
        if (total <= max) {
            featureBomLineRelationHistoryDao.insertList(list);
        } else {
            int count = (int) Math.ceil(total * 1.0 / max);
            int index = -1;
            int currentIndex = -1;
            for (int i = 0; i < count; i++) {
                index = i + 1;//下一个节点
                currentIndex = index * max;//终末位置
                currentIndex = currentIndex > total ? total : currentIndex;//如果大于最大长度，则取最大长度
                list.subList(i * max, currentIndex);
            }
        }
    }
}
