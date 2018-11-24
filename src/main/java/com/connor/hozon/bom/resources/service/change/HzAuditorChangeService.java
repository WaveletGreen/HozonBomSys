package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.domain.dto.request.EditHzChangeOrderReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.HzAuditorChangeDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import sql.pojo.change.HzAuditorChangeRecord;

import java.util.List;

public interface HzAuditorChangeService {
    /**
     * 查询列表-待办事项
     * @return
     */
    List<HzChangeOrderRespDTO> findChangeOrderList(HzChangeOrderByPageQuery query, HzAuditorChangeRecord record);

    /**
     * 查询列表-待办事项
     * @return
     */
    List<HzChangeOrderRespDTO> findChangeOrderList2(HzChangeOrderByPageQuery query, HzAuditorChangeRecord record);

}
