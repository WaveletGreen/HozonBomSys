package com.connor.hozon.bom.resources.service.bom;

import com.connor.hozon.bom.resources.dto.request.AddMbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateMbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMbomByPageQuery;

/**
 * @Author: haozt
 * @Date: 2018/6/20
 * @Description:
 */
public interface HzMbomService {
    /**
     * 分页获取mbom信息
     * @param query
     * @return
     */
    Page<HzMbomRecordRespDTO> fingHzMbomForPage(HzMbomByPageQuery query);

    HzMbomRecordRespDTO findHzMbomByPuid(String projectId,String puid);

    OperateResultMessageRespDTO insertMbomRecord(AddMbomReqDTO reqDTO);

    OperateResultMessageRespDTO updateMbomRecord(UpdateMbomReqDTO reqDTO);

    OperateResultMessageRespDTO deleteMbomRecord(String puid);
}
