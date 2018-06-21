package com.connor.hozon.bom.resources.service.bom;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.page.Page;

/**
 * Created by haozt on 2018/06/06
 */
public interface HzEbomService {

    Page<HzEbomRespDTO> getHzEbomPage(FindForPageReqDTO recordReqDTO);

    JSONArray getEbomTitle(FindForPageReqDTO recordReqDTO);
}
