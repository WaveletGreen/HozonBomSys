package com.connor.hozon.resources.service.change;


import com.connor.hozon.resources.domain.dto.request.EditEWOImpactDeptReqDTO;
import com.connor.hozon.resources.domain.dto.request.EditImpactDeptEmpReqDTO;
import com.connor.hozon.resources.domain.dto.response.HzEWOImpactDeptEmpRespDTO;
import com.connor.hozon.resources.domain.dto.response.HzEWOImpactDeptRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.query.HzEWOImpactDeptQuery;

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
