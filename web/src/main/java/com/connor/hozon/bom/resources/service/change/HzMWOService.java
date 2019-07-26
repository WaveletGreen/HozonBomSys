package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.domain.dto.request.InitiatingProcessReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;

public interface HzMWOService {
    /**
     * MWO发起流程
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO initiatingProcessForMwoChange(InitiatingProcessReqDTO reqDTO);
}
