package com.connor.hozon.bom.resources.service.bom;

import com.connor.hozon.bom.resources.domain.dto.request.AnalysisSingleVehicleBOMReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzSingleVehiclesReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/9/26
 * @Description:
 */
public interface HzSingleVehiclesServices {
    /**
     * 获取单车清单列表
     * @param projectId
     * @return
     */
    List<HzSingleVehiclesRespDTO> singleVehiclesList(String projectId);

    /**
     * 更改单车信息
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO updateSingleVehicle(UpdateHzSingleVehiclesReqDTO reqDTO);

    /**
     * 从配置中同步单车信息
     * @param projectId
     * @return
     */
    OperateResultMessageRespDTO refreshSingleVehicle(String projectId);

    /**
     * 获取单条单车信息
     * @param projectId
     * @param id
     * @return
     */
    HzSingleVehiclesRespDTO getSingleVehiclesById(String projectId,Long id);


}
