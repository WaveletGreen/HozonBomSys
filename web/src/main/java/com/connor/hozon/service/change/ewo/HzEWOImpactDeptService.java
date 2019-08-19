/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.ewo;


import com.connor.hozon.bom.resources.domain.dto.request.EditEWOImpactDeptReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.EditImpactDeptEmpReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEWOImpactDeptEmpRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEWOImpactDeptRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEWOImpactDeptQuery;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:
 */
public interface HzEWOImpactDeptService {
    /**
     * 影响部门保存
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO saveImpactDept(EditEWOImpactDeptReqDTO reqDTO);

    WriteResultRespDTO saveImpactDeptEmp(EditImpactDeptEmpReqDTO reqDTO);

    List<HzEWOImpactDeptRespDTO> getAllImpactDept(HzEWOImpactDeptQuery query);

    List<HzEWOImpactDeptEmpRespDTO> getAllImpactDeptEmp(HzEWOImpactDeptQuery query);
}
