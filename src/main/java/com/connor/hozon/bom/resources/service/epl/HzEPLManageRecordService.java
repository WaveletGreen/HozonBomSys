package com.connor.hozon.bom.resources.service.epl;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzEPLByPageQuery;

/**
 * Created by haozt on 2018/06/05
 */
public interface HzEPLManageRecordService {
    /**
     * 分页获取epl信息
     * @param query
     * @return
     */
    Page<HzEPLRecordRespDTO> getHzEPLRecordForPage(HzEPLByPageQuery query);

    JSONArray getEPLTittle(String projectId);

    String  getGroupNum(String projectId,String parentId);

}
