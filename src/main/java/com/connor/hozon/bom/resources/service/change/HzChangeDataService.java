package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.domain.dto.response.HzChangeDataDetailRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeDataRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataQuery;

import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:变更表单数据
 */
public interface HzChangeDataService {

    /**
     * 获取变更表单变更数据 超链接
     * @param query
     * @return
     */
    @Deprecated
    List<HzChangeDataRespDTO> getChangeDataHyperRecord(HzChangeDataQuery query);

    /**
     * 获取超链接下的变更数据
     * @param query
     * @return
     */
    List<HzEbomRespDTO> getChangeDataRecordForEBOM(HzChangeDataQuery query);


}
