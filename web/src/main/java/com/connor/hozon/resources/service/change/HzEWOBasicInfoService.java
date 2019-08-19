package com.connor.hozon.resources.service.change;

import com.connor.hozon.resources.domain.dto.request.UpdateHzEWOBasicInfoReqDTO;
import com.connor.hozon.resources.domain.dto.response.HzEWOBasicInfoRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.query.HzEWOBasicInfoQuery;

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
    WriteResultRespDTO updateHzEWOBasicInfo(UpdateHzEWOBasicInfoReqDTO reqDTO);

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
