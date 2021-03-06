package com.connor.hozon.resources.service.epl;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.resources.domain.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.resources.page.Page;

/**
 * Created by haozt on 2018/06/05
 */
@Deprecated
public interface HzEPLManageRecordService {
    /**
     * 分页获取epl信息
     * @param
     * @return
     */
//    Page<HzEPLRecordRespDTO> getHzEPLRecordForPage(HzEPLByPageQuery query);

//    JSONArray getEPLTittle(String projectId);

    String  getGroupNum(String projectId,String parentId);

    Page<HzEPLRecordRespDTO> getHzEPLRecordForPage2(HzEPLByPageQuery query);
}
