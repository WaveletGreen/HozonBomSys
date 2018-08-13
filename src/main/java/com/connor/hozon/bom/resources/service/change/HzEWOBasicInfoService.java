package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.dto.request.UpdateHzEWOBasicInfoReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEWOBasicInfoRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.query.HzEWOBasicInfoQuery;
import sql.pojo.change.HzEWOBasicInfo;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/8
 * @Description:
 */
public interface HzEWOBasicInfoService {
    /**
     * 编辑EWO变更单基本信息
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO updateHzEWOBasicInfo(UpdateHzEWOBasicInfoReqDTO reqDTO);

    /**
     * 查询EWO基本信息
     * @param query
     * @return
     */
    HzEWOBasicInfoRespDTO findHzEWOBasicInfo(HzEWOBasicInfoQuery query);

    /**
     * 查询列表
     * @param query
     * @return
     */
    List<HzEWOBasicInfoRespDTO> findHzEWOList(HzEWOBasicInfoQuery query);
}
