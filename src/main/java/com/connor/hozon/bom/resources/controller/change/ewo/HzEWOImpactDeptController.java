package com.connor.hozon.bom.resources.controller.change.ewo;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.EditEWOImpactDeptReqDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOImpactDeptDAO;
import com.connor.hozon.bom.resources.service.change.HzEWOImpactDeptService;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sql.pojo.change.HzEWOAllImpactDept;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:
 */
@Controller
@RequestMapping(value = "impact/dept")
public class HzEWOImpactDeptController extends BaseController {
    @Autowired
    private HzEWOImpactDeptDAO hzEWOImpactDeptDAO;
    @Autowired
    private HzEWOImpactDeptService hzEWOImpactDeptService;
    /**
     * 勾选的影响部门保存
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public void saveImpactDept(@RequestBody EditEWOImpactDeptReqDTO reqDTO, HttpServletResponse response){
        OperateResultMessageRespDTO respDTO = hzEWOImpactDeptService.saveImpactDept(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO),response);
    }

    /**
     * 全部的影响部门信息 目前部门是固定的
     * @param response
     */
    @RequestMapping("all")
    public void findAllImpactDept(HttpServletResponse response){
        List<HzEWOAllImpactDept> list = hzEWOImpactDeptDAO.findEWOAllImpactDept();
        writeAjaxJSONResponse(ResultMessageBuilder.build(list),response);
    }
}
