package com.connor.hozon.resources.service.resourcesLibrary.legislativeLibrary;

import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import cn.net.connor.hozon.services.response.dipository.legislativeLibrary.HzLegislativeItemResDTO;
import com.connor.hozon.resources.domain.dto.request.AddHzLegislativeReqDTO;
import com.connor.hozon.resources.domain.dto.request.UpdateHzLegislativeReqDTO;
import com.connor.hozon.resources.domain.query.HzLegislativeItemQuery;
import com.connor.hozon.resources.page.Page;

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


    /**
     * 修改一条法规件
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateHzLegislativeRecord(UpdateHzLegislativeReqDTO reqDTO);

}
