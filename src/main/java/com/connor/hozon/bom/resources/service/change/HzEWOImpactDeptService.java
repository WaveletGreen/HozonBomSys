package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.dto.request.EditEWOImpactDeptReqDTO;
import com.connor.hozon.bom.resources.dto.request.EditImpactDeptEmpReqDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;

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


}
