package com.connor.hozon.bom.resources.service.bom;

import com.connor.hozon.bom.resources.dto.request.AddMbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateMbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomRecord;

/**
 * @Author: haozt
 * @Date: 2018/6/20
 * @Description:
 */
public interface HzMbomService {
    /**
     * 分页获取mbom信息
     * @param reqDTO
     * @return
     */
    Page<HzMbomRecordRespDTO> fingHzMbomForPage(FindForPageReqDTO reqDTO);

    HzMbomRecordRespDTO findHzMbomByPuid(String projectId,String puid);

    OperateResultMessageRespDTO insertMbomRecord(AddMbomReqDTO reqDTO);

    OperateResultMessageRespDTO updateMbomRecord(UpdateMbomReqDTO reqDTO);

    OperateResultMessageRespDTO deleteMbomRecord(String puid);
}
