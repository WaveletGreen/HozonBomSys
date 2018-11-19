package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import sql.pojo.change.HzApplicantChangeRecord;

import java.util.List;

public interface HzApplicationChangeService {
    /**
     * 查询列表-我的申请
     * @return
     */
    List<HzChangeOrderRespDTO> findChangeOrderList(HzChangeOrderByPageQuery query, HzApplicantChangeRecord record);

}
