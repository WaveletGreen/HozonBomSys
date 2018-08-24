package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.dto.request.EditEWOImpactDeptReqDTO;
import com.connor.hozon.bom.resources.dto.request.EditImpactDeptEmpReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEWOImpactDeptEmpRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzEWOImpactDeptRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.query.HzEWOImpactDeptQuery;

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
    OperateResultMessageRespDTO saveImpactDept(EditEWOImpactDeptReqDTO reqDTO);

    OperateResultMessageRespDTO saveImpactDeptEmp(EditImpactDeptEmpReqDTO reqDTO);

    List<HzEWOImpactDeptRespDTO> getAllImpactDept(HzEWOImpactDeptQuery query);

    List<HzEWOImpactDeptEmpRespDTO> getAllImpactDeptEmp(HzEWOImpactDeptQuery query);
}