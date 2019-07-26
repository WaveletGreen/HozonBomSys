package com.connor.hozon.bom.resources.service.change;


import com.connor.hozon.bom.resources.domain.dto.request.InitiatingProcessReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEWOChangeRecordQuery;

import java.util.List;

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
    WriteResultRespDTO initiatingProcessForEwoChange(InitiatingProcessReqDTO reqDTO);


    /**
     * EWO变更信息表单
     * @param query
     * @return
     */
    List<HzEbomRespDTO> getEWOChangeFormRecord(HzEWOChangeRecordQuery query);

    /**
     * 添加新零件到变更表单
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO addNewItemToChangeForm(InitiatingProcessReqDTO reqDTO);

    /**
     * 从变更表单删除零件
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deleteItemFromChangeFrom(InitiatingProcessReqDTO reqDTO);


}
