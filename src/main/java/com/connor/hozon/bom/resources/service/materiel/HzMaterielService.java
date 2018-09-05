package com.connor.hozon.bom.resources.service.materiel;

import com.connor.hozon.bom.resources.dto.request.EditHzMaterielReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMaterielRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.query.HzMaterielQuery;

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
    OperateResultMessageRespDTO editHzMateriel(EditHzMaterielReqDTO editHzMaterielReqDTO);

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
    Page<HzMaterielRespDTO> findHzMaterielForPage(HzMaterielByPageQuery query);

    HzMaterielRespDTO getHzMateriel(HzMaterielQuery query);
}
