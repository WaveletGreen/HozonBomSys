/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.epl;

import com.connor.hozon.bom.resources.domain.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.bom.resources.page.Page;

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
