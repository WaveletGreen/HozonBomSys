package com.connor.hozon.resources.service.change;

import com.connor.hozon.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.change.change.HzApplicantChangeRecord;

import java.util.List;

public interface HzApplicationChangeService {
    /**
     * 查询列表-我的申请
     * @return
     */
    List<HzChangeOrderRespDTO> findChangeOrderList(HzChangeOrderByPageQuery query, HzApplicantChangeRecord record);

    /**
     * 获取分页数据-我的申请
     * @param query
     * @return
     */
    Page<HzChangeOrderRespDTO> getHzChangeOrderPage(HzChangeOrderByPageQuery query, HzApplicantChangeRecord record);

}
