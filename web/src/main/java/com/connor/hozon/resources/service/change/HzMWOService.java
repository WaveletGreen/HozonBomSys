package com.connor.hozon.resources.service.change;

import com.connor.hozon.resources.domain.dto.request.InitiatingProcessReqDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;

public interface HzMWOService {
    /**
     * MWO发起流程
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO initiatingProcessForMwoChange(InitiatingProcessReqDTO reqDTO);
}
