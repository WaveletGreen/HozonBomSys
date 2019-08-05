package com.connor.hozon.bom.resources.controller.change.ewo;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.EditEWOImpactDeptReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.EditImpactDeptEmpReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEWOImpactDeptEmpRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEWOImpactDeptRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEWOImpactDeptQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOImpactDeptDAO;
import com.connor.hozon.bom.resources.service.change.HzEWOImpactDeptService;
import com.connor.hozon.bom.resources.util.Result;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.net.connor.hozon.dao.pojo.change.change.HzEWOAllImpactDept;

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
        WriteResultRespDTO respDTO = hzEWOImpactDeptService.saveImpactDept(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO),response);
    }

    /**
     * 全部的影响部门信息 目前部门是固定的
     * @param response
     */
    @RequestMapping(value = "all",method = RequestMethod.GET)
    public void findAllImpactDept(HttpServletResponse response){
        List<HzEWOAllImpactDept> list = hzEWOImpactDeptDAO.findEWOAllImpactDept();
        toJSONResponse(Result.build(list),response);
    }

    /**
     * 保存影响部门信息 带人员信息
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "save/deptEmp",method = RequestMethod.POST)
    public void saveImpactDeptEmp(@RequestBody EditImpactDeptEmpReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzEWOImpactDeptService.saveImpactDeptEmp(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO),response);
    }


    /**
     * 获取影响部门详细
     * @param query
     * @param response
     */
    @RequestMapping(value = "get/detail",method = RequestMethod.GET)
    public void getImpactDeptDetail(HzEWOImpactDeptQuery query, HttpServletResponse response){
        if(StringUtil.isEmpty(query.getEwoNo())){
            toJSONResponse(Result.build(false,"非法参数！"),response);
            return;
        }
        List<HzEWOImpactDeptRespDTO> depts = hzEWOImpactDeptService.getAllImpactDept(query);
        toJSONResponse(Result.build(depts),response);
    }


    /**
     * 获取影响部门评估人员
     * @param query
     * @param response
     */
    @RequestMapping(value = "get/detail/emp",method = RequestMethod.GET)
    public void getImpactDeptEmp(HzEWOImpactDeptQuery query, HttpServletResponse response){
        if(StringUtil.isEmpty(query.getEwoNo())){
            toJSONResponse(Result.build(false,"非法参数！"),response);
            return;
        }
        List<HzEWOImpactDeptEmpRespDTO> depts = hzEWOImpactDeptService.getAllImpactDeptEmp(query);
        toJSONResponse(Result.build(depts),response);
    }
}
