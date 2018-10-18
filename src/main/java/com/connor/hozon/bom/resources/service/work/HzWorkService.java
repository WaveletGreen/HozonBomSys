package com.connor.hozon.bom.resources.service.work;


import com.connor.hozon.bom.resources.domain.dto.request.AddWorkCenterReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateWorkCenterReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzWorkCenterRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzWorkByPageQuery;
import com.connor.hozon.bom.resources.page.Page;

/**
 * \* User: xulf
 * \* Date: 2018/7/2
 * \* Time: 13:13
 * \
 */
public interface HzWorkService {
    /**
     * 分页获取工作中心数据
     * @param query
     * @return
     */
    Page<HzWorkCenterRespDTO> findHzWorkPage(HzWorkByPageQuery query);

    /**
     * 添加一条数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO insertHzWorkRecord(AddWorkCenterReqDTO reqDTO);

    /**
     * 根据Id查询一条数据
     * @param projectId
     * @param puid
     * @return
     */
    HzWorkCenterRespDTO findHzWorkByPuid(String projectId,String puid);
    /**
     * 编辑一条数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateHzWorkRecord(UpdateWorkCenterReqDTO reqDTO);

    /**
     * 删除一条数据
     * @param puid
     * @return
     */
    WriteResultRespDTO deleteHzWorkRecord(String puid);
}
