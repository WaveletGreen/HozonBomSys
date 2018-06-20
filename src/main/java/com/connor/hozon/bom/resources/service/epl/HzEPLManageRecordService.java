package com.connor.hozon.bom.resources.service.epl;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.dto.request.FindHzEPLRecordReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.bom.resources.page.Page;

import java.util.List;

/**
 * Created by haozt on 2018/06/05
 */
public interface HzEPLManageRecordService {
    /**
     * 分页获取epl信息
     * @param recordReqDTO
     * @return
     */
    Page<HzEPLRecordRespDTO> getHzEPLRecordForPage(FindHzEPLRecordReqDTO recordReqDTO);

    JSONArray getEPLTittle(FindHzEPLRecordReqDTO recordReqDTO);
}
