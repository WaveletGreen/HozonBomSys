/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.data;

import com.connor.hozon.bom.resources.domain.dto.response.HzWorkListBasicInfoRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzWorkListBasicInfoQuery;

import java.util.List;

public interface HzWorkListBasicInfoService {
    /**
     * 查询EWO基本信息
     * @param query
     * @return
     */
    HzWorkListBasicInfoRespDTO findHzWorkListBasicInfo(HzWorkListBasicInfoQuery query);
    /**
     * 查询列表-待办事项
     * @param query
     * @return
     */
    List<HzWorkListBasicInfoRespDTO> findHzWorkList(HzWorkListBasicInfoQuery query);
}
