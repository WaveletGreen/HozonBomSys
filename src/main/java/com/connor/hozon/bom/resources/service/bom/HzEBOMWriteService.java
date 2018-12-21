package com.connor.hozon.bom.resources.service.bom;

import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomLeveReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;

/**
 * @Author: haozt
 * @Date: 2018/12/20
 * @Description: 业务变更较大 将EBOM写服务单独提出来
 */
public interface HzEBOMWriteService {
    /**
     * 新增EBOM
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO addHzEbomRecord(AddHzEbomReqDTO reqDTO);

    /**
     * 更新EBOM
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO);

    /**
     * EBOM引用层级
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO extendsBomStructureInNewParent(UpdateHzEbomLeveReqDTO reqDTO);

    /**
     * 删除EBOM
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deleteHzEbomRecordById(DeleteHzEbomReqDTO reqDTO);
}
