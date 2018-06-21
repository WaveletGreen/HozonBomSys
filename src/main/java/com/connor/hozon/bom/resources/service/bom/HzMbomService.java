package com.connor.hozon.bom.resources.service.bom;

import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomRecord;

/**
 * @Author: haozt
 * @Date: 2018/6/20
 * @Description:
 */
public interface HzMbomService {
    /**
     * 分页获取mbom信息
     * @param reqDTO
     * @return
     */
    Page<HzMbomRecordRespDTO> fingHzMbomForPage(FindForPageReqDTO reqDTO);

}
