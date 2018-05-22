package com.connor.hozon.bom.resources.service;

import com.connor.hozon.bom.resources.dbdo.config.HzCfg0Record;
import com.connor.hozon.bom.resources.dto.request.FindHzFeatureConfigReqDTO;

import java.util.List;

/**
 * Created by haozt on 2018/5/22
 * 特性配置服务
 */
public interface HzConfigRecordService {
    /**
     * 查询特性配置信息
     * @return
     */
    List<HzCfg0Record> getHzFeatureConfig(FindHzFeatureConfigReqDTO reqDTO);
}
