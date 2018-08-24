package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.dto.request.InitiatingProcessReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEWOChangeFormRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.query.HzEWOBasicInfoQuery;
import com.connor.hozon.bom.resources.query.HzEWOChangeRecordQuery;

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
    OperateResultMessageRespDTO initiatingProcessForEwoChange(InitiatingProcessReqDTO reqDTO);


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
    OperateResultMessageRespDTO addNewItemToChangeForm(InitiatingProcessReqDTO reqDTO);

    /**
     * 从变更表单删除零件
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO deleteItemFromChangeFrom(InitiatingProcessReqDTO reqDTO);


}