/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.interaction.service.impl;

import cn.net.connor.hozon.dao.dao.integration.FeatureBomLineRelationHistoryDao;
import cn.net.connor.hozon.dao.pojo.interaction.FeatureBomLineRelationHistory;
import cn.net.connor.hozon.dao.pojo.interaction.HzConfigBomLineBean;
import com.connor.hozon.bom.interaction.dao.HzConfigBomColorDao;
import com.connor.hozon.bom.interaction.service.FeatureBomLineMapper;
import com.connor.hozon.bom.interaction.service.FeatureBomLineRelationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

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
    public String checkStatus(String projectId, Long vehiclesId, Model model) {
        //查询出当前发布过的正式进行结算的bomline对应的特性值数据，保留一份数据
        List<HzConfigBomLineBean> cache = hzConfigBomColorDao.selectAllConfigToBomline(projectId);
        List<FeatureBomLineRelationHistory> list = FeatureBomLineMapper.INSTANCE.mapToList(cache);
        featureBomLineRelationHistoryDao.insertList(list);
        return "ok";
    }
}
