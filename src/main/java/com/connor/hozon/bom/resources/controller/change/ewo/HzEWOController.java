package com.connor.hozon.bom.resources.controller.change.ewo;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.InitiatingProcessReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEWOChangeRecordQuery;
import com.connor.hozon.bom.resources.domain.query.HzEWOImpactReferenceQuery;
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
import sql.pojo.change.HzEWOAllImpactDept;
import sql.pojo.change.HzEWOImpactReference;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    @ResponseBody
    public JSONObject changeProcessHistory(HzEWOChangeRecordQuery query){
        if(query.getEwoNo() == null || query.getProjectId() == null
                || query.getProjectId() == "" || query.getEwoNo() == ""){
            return new JSONObject();
        }
        List<HzEbomRespDTO> respDTOList = hzEWOService.getEWOChangeFormRecord(query);
        if(ListUtil.isEmpty(respDTOList)){
            return new JSONObject();
        }
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        respDTOList.forEach(hzEbomRespDTO -> {
            JSONObject object = new JSONObject();
            object.put("puid", hzEbomRespDTO.getPuid());
            object.put("level", hzEbomRespDTO.getLevel());
            object.put("rank", hzEbomRespDTO.getRank());
            object.put("pBomOfWhichDept", hzEbomRespDTO.getpBomOfWhichDept());
            object.put("lineId", hzEbomRespDTO.getLineId());
            object.put("fna",hzEbomRespDTO.getFna());
            object.put("lineNo",hzEbomRespDTO.getLineNo());
            object.put("pBomLinePartName", hzEbomRespDTO.getpBomLinePartName());
            object.put("pBomLinePartClass", hzEbomRespDTO.getpBomLinePartClass());
            object.put("pBomLinePartEnName",hzEbomRespDTO.getpBomLinePartEnName());
            object.put("pBomLinePartResource", hzEbomRespDTO.getpBomLinePartResource());
            object.put("pFastener", hzEbomRespDTO.getpFastener());
            if(Integer.valueOf(1).equals(hzEbomRespDTO.getP3cpartFlag())){
                object.put("p3cpartFlag", "Y");
            }else {
                object.put("p3cpartFlag", "N");
            }
            if(Integer.valueOf(1).equals(hzEbomRespDTO.getpInOutSideFlag())){
                object.put("pInOutSideFlag", "内饰件");
            }else {
                object.put("pInOutSideFlag", "外饰件");
            }
            object.put("pUpc",hzEbomRespDTO.getpUpc());
            object.put("pFnaDesc", hzEbomRespDTO.getpFnaDesc());
            object.put("pUnit", hzEbomRespDTO.getpUnit());
            object.put("pPictureNo",hzEbomRespDTO.getpPictureNo());
            object.put("pPictureSheet", hzEbomRespDTO.getpPictureSheet());
            object.put("pMaterialHigh", hzEbomRespDTO.getpMaterialHigh());
            object.put("pMaterial1",hzEbomRespDTO.getpMaterial1());
            object.put("pMaterial2", hzEbomRespDTO.getpMaterial2());
            object.put("pMaterial3", hzEbomRespDTO.getpMaterial3());
            object.put("pDensity",hzEbomRespDTO.getpDensity());
            object.put("pMaterialStandard", hzEbomRespDTO.getpMaterialStandard());
            object.put("pSurfaceTreat", hzEbomRespDTO.getpSurfaceTreat());
            object.put("pTextureColorNum",hzEbomRespDTO.getpTextureColorNum());
            object.put("pManuProcess", hzEbomRespDTO.getpManuProcess());
            object.put("pSymmetry", hzEbomRespDTO.getpSymmetry());
            object.put("pImportance",hzEbomRespDTO.getpImportance());
            if(Integer.valueOf(1).equals(hzEbomRespDTO.getpRegulationFlag())){
                object.put("pRegulationFlag", "Y");
            }else{
                object.put("pRegulationFlag", "N");
            }
            object.put("pBwgBoxPart", hzEbomRespDTO.getpBwgBoxPart());
            object.put("pDevelopType",hzEbomRespDTO.getpDevelopType());
            object.put("pDataVersion", hzEbomRespDTO.getpDataVersion());
            object.put("pTargetWeight", hzEbomRespDTO.getpTargetWeight());
            object.put("pFeatureWeight",hzEbomRespDTO.getpFeatureWeight());
            object.put("pActualWeight", hzEbomRespDTO.getpActualWeight());
            object.put("pFastenerStandard", hzEbomRespDTO.getpFastenerStandard());
            object.put("pFastenerLevel",hzEbomRespDTO.getpFastenerLevel());

            object.put("pTorque", hzEbomRespDTO.getpTorque());
            object.put("pDutyEngineer",hzEbomRespDTO.getpDutyEngineer());
            object.put("pSupply", hzEbomRespDTO.getpSupply());
            object.put("pSupplyCode", hzEbomRespDTO.getpSupplyCode());
            object.put("pRemark",hzEbomRespDTO.getpRemark());
            object.put("pRegulationCode", hzEbomRespDTO.getpRegulationCode());
            object.put("number",hzEbomRespDTO.getNumber());
            object.put("pBuyEngineer",hzEbomRespDTO.getpBuyEngineer());
            object.put("status",hzEbomRespDTO.getStatus());
            object.put("changeFlag",hzEbomRespDTO.getChangeFlag());
            list.add(object);
        });
        jsonObject.put("result",list);
        return jsonObject;
    }


    /**
     * 影响分析
     * @param query
     * @param response
     */
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


    /**
     * 影响分析保存
     * @param reference
     * @param response
     */
    @RequestMapping(value = "impact/save",method = RequestMethod.POST)
    public void impactReferenceRecord(HzEWOImpactReference reference, HttpServletResponse response){
        if(reference.getEwoNo() == null || reference.getProjectId() == null
                || reference.getProjectId() == "" || reference.getEwoNo() == ""){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return ;
        }
        OperateResultMessageRespDTO respDTO = hzEWOImpactReferenceService.editHzEWOImpactReference(reference);
        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTO),response);
    }




}
