package com.connor.hozon.bom.resources.mybatis.epl;

import com.connor.hozon.bom.resources.dto.request.FindHzEPLRecordReqDTO;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;

/**
 * Created by haozt on 2018/06/05
 */
public interface HzEplMangeRecordDAO {
    List<HzEPLManageRecord> getHzEplManageRecord();

    Page<HzEPLManageRecord> getEPLListForPage(FindHzEPLRecordReqDTO recordReqDTO);
}