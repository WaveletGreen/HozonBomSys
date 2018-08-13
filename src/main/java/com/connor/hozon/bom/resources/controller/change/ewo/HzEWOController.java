package com.connor.hozon.bom.resources.controller.change.ewo;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.InitiatingProcessReqDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.query.HzEWOChangeRecordQuery;
import com.connor.hozon.bom.resources.service.change.HzEWOService;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * \* User: xulf
 * \* Date: 2018/7/30
 * \* Time: 15:50
 * \
 */
@Controller
@RequestMapping(value = "/ewo")
public class HzEWOController extends BaseController {

    @Autowired
    private HzEWOService hzEWOService;
    /**
     * EWO表单发起流程
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "initiating/process",method = RequestMethod.POST)
    public void initiatingProcessForChange(InitiatingProcessReqDTO reqDTO, HttpServletResponse response){
        if(reqDTO == null || null == reqDTO.getPuids() || reqDTO.getPuids().equals("")){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return;
        }

        OperateResultMessageRespDTO respDTO = hzEWOService.initiatingProcessForEwoChange(reqDTO);

        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO),response);
    }

    /**
     * 流程变更表单记录
     */
    @RequestMapping(value = "change/record",method = RequestMethod.GET)
    public void changeProcessHistory(HzEWOChangeRecordQuery query){

    }

}
