package com.connor.hozon.bom.resources.service.bom;

import com.connor.hozon.bom.resources.dto.request.FindHzEbomRecordReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;

import java.util.List;

/**
 * Created by haozt on 2018/06/06
 */
public interface HzEbomService {
    List<HzEbomRespDTO> getHzEbomList(FindHzEbomRecordReqDTO reqDTO);
}
