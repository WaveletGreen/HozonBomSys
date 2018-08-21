package com.connor.hozon.bom.resources.controller.change.ewo;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.UpdateHzEWOBasicInfoReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEWOBasicInfoRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzEWOChangeFormRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.query.HzEWOBasicInfoQuery;
import com.connor.hozon.bom.resources.query.HzEWOChangeRecordQuery;
import com.connor.hozon.bom.resources.query.HzEWOImpactReferenceQuery;
import com.connor.hozon.bom.resources.service.change.HzEWOBasicInfoService;
import com.connor.hozon.bom.resources.service.change.HzEWOImpactReferenceService;
import com.connor.hozon.bom.resources.service.change.HzEWOService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.change.HzEWOImpactReference;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    @Autowired
    private HzEWOService hzEWOService;
    @Autowired
    private HzEWOImpactReferenceService hzEWOImpactReferenceService;

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
    /*
    * 流程变更表单标题
    * */
    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void getEbomTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
//        tableTitle.put("No","序号");
//        tableTitle.put("lineId","零件号" );
//        tableTitle.put("changeFlag","变更前/变更后");
        tableTitle.put("pBomLinePartName","名称" );
        tableTitle.put("level","层级" );
        tableTitle.put("pBomOfWhichDept","专业" );
        tableTitle.put("rank","级别" );
        tableTitle.put("groupNum","分组号");
//        tableTitle.put("lineNo","查找编号");
        tableTitle.put("pBomLinePartEnName","英文名称");
        tableTitle.put("pLouaFlag","LOU/LOA");

        tableTitle.put("pUnit","单位");
        tableTitle.put("pPictureNo","图号");
        tableTitle.put("pPictureSheet","图幅" );
        tableTitle.put("pMaterialHigh","料厚" );
        tableTitle.put("pMaterial1","材料1");
        tableTitle.put("pMaterial2","材料2" );
        tableTitle.put("pMaterial3","材料3" );
        tableTitle.put("pDensity","密度");
        tableTitle.put("pMaterialStandard","材料标准");
        tableTitle.put("pSurfaceTreat","表面处理" );
        tableTitle.put("pTextureColorNum","纹理编号/色彩编号");
        tableTitle.put("pManuProcess","制造工艺");
        tableTitle.put("pSymmetry","对称" );
        tableTitle.put("pImportance","重要度");
        tableTitle.put("pRegulationFlag","是否法规件");
        tableTitle.put("p3cpartFlag","是否3C件" );

        tableTitle.put("pRegulationCode","法规件型号");
        tableTitle.put("pBwgBoxPart","黑白灰匣子件" );
        tableTitle.put("pDevelopType","开发类别");
        tableTitle.put("pDataVersion","数据版本" );
        tableTitle.put("pTargetWeight","目标重量(kg)");
        tableTitle.put("pFeatureWeight","预估重量(kg)");
        tableTitle.put("pActualWeight","实际重量(kg)" );
        tableTitle.put("pFastener","紧固件");
        tableTitle.put("pFastenerStandard","紧固件规格");
        tableTitle.put("pFastenerLevel","紧固件性能等级");
        tableTitle.put("pTorque","扭矩" );
        tableTitle.put("pDutyEngineer","责任工程师");
        tableTitle.put("pSupply","供应商");
        tableTitle.put("pSupplyCode","供应商代码" );

        tableTitle.put("pBuyEngineer","采购工程师");
        tableTitle.put("pRemark","备注");
        tableTitle.put("pBomLinePartClass","零件分类" );
        tableTitle.put("pBomLinePartResource","零件来源");
        tableTitle.put("pInOutSideFlag","内外饰标识");
        tableTitle.put("pUpc","UPC");
        tableTitle.put("fna","FNA");
        tableTitle.put("pFnaDesc","FNA描述" );
        tableTitle.put("number","数量" );
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
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















}
