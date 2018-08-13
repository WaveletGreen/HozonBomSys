package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.dto.request.InitiatingProcessReqDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;

/**
 * @Author: haozt
 * @Date: 2018/8/13
 * @Description:
 */
public interface HzEWOService {

    /**
     * EWO发起流程
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO initiatingProcessForEwoChange(InitiatingProcessReqDTO reqDTO);

}
