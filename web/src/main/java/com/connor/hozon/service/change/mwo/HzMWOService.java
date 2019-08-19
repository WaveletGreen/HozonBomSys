/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.mwo;

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
