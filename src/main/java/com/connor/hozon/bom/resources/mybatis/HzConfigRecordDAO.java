package com.connor.hozon.bom.resources.mybatis;

import com.connor.hozon.bom.resources.dbdo.config.HzCfg0Record;
import com.connor.hozon.bom.resources.dto.request.FindHzFeatureConfigReqDTO;

import java.util.List;

/**
 * Created by haozt on 2018/5/22
 */
public interface HzConfigRecordDAO {
    /**
     * 查询特性配置表
     * @param dto
     * @return
     */
    List<HzCfg0Record> selectHzFeatureConfig(FindHzFeatureConfigReqDTO dto);
}
