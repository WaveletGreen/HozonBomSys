package com.connor.hozon.bom.resources.controller.change.ewo;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.UpdateHzEWOBasicInfoReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEWOBasicInfoRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.query.HzEWOBasicInfoQuery;
import com.connor.hozon.bom.resources.service.change.HzEWOBasicInfoService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/8
 * @Description:
 */
@Controller
@RequestMapping("/ewo/base")
public class HzEWOBaseInfoController extends BaseController {
    @Autowired
    private HzEWOBasicInfoService hzEWOBasicInfoService;

    /**
     * 编辑EWO表单基本信息
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void updateHzEWOBaseInfo(@RequestBody UpdateHzEWOBasicInfoReqDTO reqDTO, HttpServletResponse response){
        OperateResultMessageRespDTO respDTO = hzEWOBasicInfoService.updateHzEWOBasicInfo(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }


    /**
     * 获取EWO表单基本信息
     * @param query
     * @param
     */
    @RequestMapping(value = "info",method = RequestMethod.GET)
    public String getHzEWOBasicInfo(HzEWOBasicInfoQuery query, Model model){
        if(query.getId() == null){
            return"" ;
        }
        HzEWOBasicInfoRespDTO respDTO = hzEWOBasicInfoService.findHzEWOBasicInfo(query);
        if(respDTO == null){
//            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无数据！"),response);
            return "";
        }
        model.addAttribute("data",respDTO);
        return "changeManage/ewo/ewoBasicInformation";

//        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTO),response);
    }


    /**
     * 获取EWO表单基本信息列表
     * @param query
     * @param response
     */
    @RequestMapping(value = "infoList",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getHzEWOBasicInfoList(HzEWOBasicInfoQuery query, HttpServletResponse response){
        if(query.getProjectId() == null || query.getProjectId() == ""){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return new JSONObject();
        }
        List<HzEWOBasicInfoRespDTO> respDTOs = hzEWOBasicInfoService.findHzEWOList(query);
        if(ListUtil.isEmpty(respDTOs)){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无数据！"),response);
            return new JSONObject();
        }
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        respDTOs.forEach(hzEWOBasicInfoRespDTO -> {
            JSONObject object = new JSONObject();
            object.put("ewoNo",hzEWOBasicInfoRespDTO.getEwoNo());
            object.put("formCreateTime",hzEWOBasicInfoRespDTO.getFormCreateTime());
            object.put("id",hzEWOBasicInfoRespDTO.getId());
            object.put("originator",hzEWOBasicInfoRespDTO.getOriginator());
            object.put("reasonDesc",hzEWOBasicInfoRespDTO.getReasonDesc());
            object.put("changeDesc",hzEWOBasicInfoRespDTO.getChangeDesc());
            object.put("dept",hzEWOBasicInfoRespDTO.getDept());
            list.add(object);
        });
        jsonObject.put("result",list);
        return jsonObject;
    }
}
