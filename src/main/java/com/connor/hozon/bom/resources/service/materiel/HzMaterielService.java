package com.connor.hozon.bom.resources.service.materiel;

import com.connor.hozon.bom.resources.dto.request.AddHzMaterielReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzMaterielReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateMbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMaterielRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMaterialByPageQuery;

/**
 * @Author: haozt
 * @Date: 2018/7/3
 * @Description:
 */
public interface HzMaterielService {
    /**
     * 插入一条物料记录
     * @param addHzMaterielReqDTO
     * @return
     */
    OperateResultMessageRespDTO addHzMateriel(AddHzMaterielReqDTO addHzMaterielReqDTO);

    /**
     * 更新一条物料记录
     * @param updateHzMaterielReqDTO
     * @return
     */
    OperateResultMessageRespDTO updateHzMateriel(UpdateHzMaterielReqDTO updateHzMaterielReqDTO);

    /**
     * 删除一条记录
     * @param puid
     * @return
     */
    OperateResultMessageRespDTO deleteHzMateriel(String puid);

    /**
     * 分页获取物料信息
     * @param query
     * @return
     */
    Page<HzMaterielRespDTO> findHzMaterielForPage(HzMaterialByPageQuery query);
}
