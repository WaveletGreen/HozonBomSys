package com.connor.hozon.bom.resources.service.resourcesLibrary.legislativeLibrary;

import com.connor.hozon.bom.resources.domain.dto.response.HzLegislativeAutoTypeResDTO;
import com.connor.hozon.bom.resources.domain.query.HzLegislativeAutoTypeQuery;
import com.connor.hozon.bom.resources.page.Page;

public interface HzLegislativeAutoTypeService {
    /**
     * 分页获取法规件库整车级的数据
     * @param query
     * @return
     */
    Page<HzLegislativeAutoTypeResDTO> findHzLegislativeMotorcycleTypeToPage(HzLegislativeAutoTypeQuery query);

    /**
     * 获得单条整车级数据
     * @param puid
     * @return
     */
    HzLegislativeAutoTypeResDTO findHzLegislativeMotorcycleTypeById(String puid);
}
