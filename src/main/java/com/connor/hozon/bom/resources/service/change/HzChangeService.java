package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.domain.dto.request.EditHzChangeOrderReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.page.Page;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description: 变更
 */
public interface HzChangeService {
    /**
     * 新增变更表单
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO insertChangeOrderRecord(EditHzChangeOrderReqDTO reqDTO);

    /**
     * 编辑变更 表单
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateChangeOrderRecord(EditHzChangeOrderReqDTO reqDTO);

    /**
     * 删除变更表单
     * @param id
     * @return
     */
    WriteResultRespDTO deleteChangeOrderById(Long id);

    /**
     * 变更单号是否已存在
     * @param changeNo
     * @return
     */
    WriteResultRespDTO changeNoExist(String changeNo);

    /**
     * 获取分页数据
     * @param query
     * @return
     */
    Page<HzChangeOrderRespDTO> getHzChangeOrderPage(HzChangeOrderByPageQuery query);
}
