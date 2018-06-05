package com.connor.hozon.bom.resources.service.epl;

import com.connor.hozon.bom.resources.dto.request.FindHzEPLRecordReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEPLRecordRespDTO;

import java.util.List;

/**
 * Created by haozt on 2018/06/05
 */
public interface HzEPLManageRecordService {
    List<HzEPLRecordRespDTO> getHzEPLRecord(FindHzEPLRecordReqDTO recordReqDTO);
}
