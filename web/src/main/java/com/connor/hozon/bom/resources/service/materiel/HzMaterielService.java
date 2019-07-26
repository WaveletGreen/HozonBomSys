package com.connor.hozon.bom.resources.service.materiel;

import com.connor.hozon.bom.resources.domain.dto.request.AddDataToChangeOrderReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.BomBackReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzMaterielReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzMaterielRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.page.Page;

/**
 * @Author: haozt
 * @Date: 2018/7/3
 * @Description:
 */
public interface HzMaterielService {
    /**
     * 编辑一条物料记录
     * @param editHzMaterielReqDTO
     * @return
     */
    WriteResultRespDTO editHzMateriel(EditHzMaterielReqDTO editHzMaterielReqDTO);

    /**
     * 删除记录
     * @param puids
     * @return
     */
    WriteResultRespDTO deleteHzMateriel(String puids);

    /**
     * 分页获取物料信息
     * @param query
     * @return
     */
    Page<HzMaterielRespDTO> findHzMaterielForPage(HzMaterielByPageQuery query);


    HzMaterielRespDTO getHzMateriel(HzMaterielQuery query);

    /**
     * 物料数据走变更流程
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO dataToChangeOrder(AddDataToChangeOrderReqDTO reqDTO);

    /**
     * 数据撤销
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO backBomUtilLastValidState(BomBackReqDTO reqDTO);
}