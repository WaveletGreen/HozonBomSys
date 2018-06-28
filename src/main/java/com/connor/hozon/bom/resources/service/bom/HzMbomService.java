package com.connor.hozon.bom.resources.service.bom;

import com.connor.hozon.bom.resources.dto.request.AddMbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateMbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzSuperMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzVehicleModelRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMbomByPageQuery;
import sql.pojo.bom.HzMbomLineRecord;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/6/20
 * @Description:
 */
public interface HzMbomService {
    /**
     * 分页获取mbom信息
     * @param query
     * @return
     */
    Page<HzMbomRecordRespDTO> fingHzMbomForPage(HzMbomByPageQuery query);

    /**
     * 获取一条MBOM
     * @param projectId
     * @param puid
     * @return
     */
    HzMbomRecordRespDTO findHzMbomByPuid(String projectId,String puid);

    /**
     * 插入一条MBOM信息
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO insertMbomRecord(AddMbomReqDTO reqDTO);

    /**
     * 编辑一条MBOM
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO updateMbomRecord(UpdateMbomReqDTO reqDTO);

    /**
     * 删除一条MBOM
     * @param puid
     * @return
     */
    OperateResultMessageRespDTO deleteMbomRecord(String puid);

    /**
     * 获取超级Mbom信息
     * @param query
     * @return
     */
    Page<HzSuperMbomRecordRespDTO> getHzSuperMbomPage(HzMbomByPageQuery query);

    /**
     * 获取车辆模型信息
     * @param projectId
     * @return
     */
    List<HzVehicleModelRespDTO> getHzVehicleModelByProjectId(String projectId);


}
