package com.connor.hozon.bom.resources.service.work;

import com.connor.hozon.bom.resources.dto.request.AddHzProcessReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzProcessReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzWorkProcessRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzWorkProcessByPageQuery;

/**
 * @Author: haozt
 * @Date: 2018/7/5
 * @Description:
 */
public interface HzWorkProcessService {
    /**
     * 新增一条数据
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO addHzWorkProcess(AddHzProcessReqDTO reqDTO);

    /**
     * 编辑一条数据
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO updateHzWorkProcess(UpdateHzProcessReqDTO reqDTO);

    /**
     * 删除一条数据
     * @param puid
     * @return
     */
    OperateResultMessageRespDTO deleteHzWorkProcess(String puid);

    /**
     * 分页获取数据
     * @param query
     * @return
     */
    Page<HzWorkProcessRespDTO> findHzWorkProcessForPage(HzWorkProcessByPageQuery query);

    HzWorkProcessRespDTO findHzWorkProcess(String materielId,String projectId);
}
