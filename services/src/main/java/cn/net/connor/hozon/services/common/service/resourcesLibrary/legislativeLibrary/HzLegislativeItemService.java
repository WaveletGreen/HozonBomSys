package com.connor.hozon.bom.resources.service.resourcesLibrary.legislativeLibrary;

import com.connor.hozon.bom.resources.domain.dto.request.AddHzLegislativeReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzLegislativeItemResDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzLegislativeItemQuery;
import com.connor.hozon.bom.resources.page.Page;

public interface HzLegislativeItemService {
    /**
     * 分页获取法规件库整车级的数据
     * @param query
     * @return
     */
    Page<HzLegislativeItemResDTO> findHzLegislativeItemToPage(HzLegislativeItemQuery query);

    /**
     * 获得单条整车级数据
     * @param puid
     * @return
     */
    HzLegislativeItemResDTO findHzLegislativeItemById(String puid);


    /**
     * 添加一条法规件
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO addHzLegislativeRecord(AddHzLegislativeReqDTO reqDTO);
}
