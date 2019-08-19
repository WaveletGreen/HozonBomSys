package com.connor.hozon.bom.resources.service.bom;

import com.connor.hozon.bom.resources.domain.dto.request.*;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;

/**
 * @Author: haozt
 * @Date: 2018/12/20
 * @Description: 业务变更较大 将EBOM写服务单独提出来
 */
public interface HzEBOMWriteService {
    /**
     * 新增EBOM
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO addHzEbomRecord(AddHzEbomReqDTO reqDTO);


    /**
     * 快速新增EBOM
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO quickAddHzEbomRecord(QuickAddHzEbomReqDTO reqDTO);

    /**
     * 派生EBOM
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deriveHzEbomRecord(DeriveHzEbomReqDTO reqDTO);

    
    /**
     * 更新EBOM
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO);

    /**
     * EBOM引用层级
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO extendsBomStructureInNewParent(UpdateHzEbomLeveReqDTO reqDTO);




    /**
     * 删除EBOM
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deleteHzEbomRecord(DeleteHzEbomReqDTO reqDTO);


    /**
     * EBOM数据  到变更表单
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO dataToChangeOrder(AddDataToChangeOrderReqDTO reqDTO);
    /**
     * BOM数据撤销
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO backBomUtilLastValidState(BomBackReqDTO reqDTO);

    /**
     * 设置当前BOM为LOU
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO setCurrentBomAsLou(SetLouReqDTO reqDTO);


}
