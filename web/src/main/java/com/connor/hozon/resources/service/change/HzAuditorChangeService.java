package com.connor.hozon.resources.service.change;

import com.connor.hozon.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.change.change.HzAuditorChangeRecord;

import java.util.List;

public interface HzAuditorChangeService {
    /**
     * 查询列表-待办事项
     * @return
     */
    List<HzChangeOrderRespDTO> findChangeOrderList(HzChangeOrderByPageQuery query, HzAuditorChangeRecord record);

    /**
     * 查询列表-已处理事项
     * @return
     */
    List<HzChangeOrderRespDTO> findChangeOrderList2(HzChangeOrderByPageQuery query, HzAuditorChangeRecord record);

    /**
     * 获取分页数据-待办事项
     * @param query
     * @return
     */
    Page<HzChangeOrderRespDTO> getHzChangeOrderPageUn(HzChangeOrderByPageQuery query, HzAuditorChangeRecord record);

    /**
     * 获取分页数据-已处理事项
     * @param query
     * @return
     */
    Page<HzChangeOrderRespDTO> getHzChangeOrderPagePr(HzChangeOrderByPageQuery query, HzAuditorChangeRecord record);
}
