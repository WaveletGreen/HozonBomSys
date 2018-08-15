package com.connor.hozon.bom.resources.controller.change.ewo;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.InitiatingProcessReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEWOChangeFormRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.query.HzEWOChangeRecordQuery;
import com.connor.hozon.bom.resources.query.HzEWOImpactReferenceQuery;
import com.connor.hozon.bom.resources.service.change.HzEWOImpactReferenceService;
import com.connor.hozon.bom.resources.service.change.HzEWOService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.change.HzEWOImpactReference;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @Autowired
    private HzEWOImpactReferenceService hzEWOImpactReferenceService;
    /**
     * EWO表单发起流程
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "initiating/process",method = RequestMethod.POST)
    public void initiatingProcessForChange(@RequestBody InitiatingProcessReqDTO reqDTO, HttpServletResponse response){
        if(null == reqDTO.getPuids() || reqDTO.getPuids().equals("")){
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
    public void changeProcessHistory(HzEWOChangeRecordQuery query,HttpServletResponse response){
        if(query.getEwoNo() == null || query.getProjectId() == null
                || query.getProjectId() == "" || query.getEwoNo() == ""){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return ;
        }
        List<HzEWOChangeFormRespDTO> respDTOList = hzEWOService.getEWOChangeFormRecord(query);
        if(ListUtil.isEmpty(respDTOList)){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无数据！"),response);
            return ;
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTOList),response);
    }

    @RequestMapping(value = "impact/reference",method = RequestMethod.GET)
    public void impactReferenceRecord(HzEWOImpactReferenceQuery query, HttpServletResponse response){
        if(query.getEwoNo() == null || query.getProjectId() == null
                || query.getProjectId() == "" || query.getEwoNo() == ""){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return ;
        }
        List<HzEWOImpactReference> respDTOList = hzEWOImpactReferenceService.getHzEWOImpactReferences(query);
        if(ListUtil.isEmpty(respDTOList)){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无数据！"),response);
            return ;
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTOList),response);
    }
}
