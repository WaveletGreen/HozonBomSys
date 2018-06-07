package com.connor.hozon.bom.resources.controller.epl;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.FindHzEPLRecordReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by haozt on 2018/06/05
 */
@Controller
@RequestMapping(value = "/epl")
public class HzEPLController extends BaseController {

    @Autowired
    private HzEPLManageRecordService hzEPLManageRecordService;

    @RequestMapping(value = "record/page",method = RequestMethod.GET)
    public void getHzEplRecordByPage(FindHzEPLRecordReqDTO recordReqDTO, HttpServletResponse response){
        Page<HzEPLRecordRespDTO> recordRespDTOPage = hzEPLManageRecordService.getHzEPLRecordForPage(recordReqDTO);
        List<HzEPLRecordRespDTO> recordRespDTOS =  recordRespDTOPage.getResult();
        if (ListUtil.isEmpty(recordRespDTOS)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无数据",new Page<>(recordReqDTO.getPageNum(),recordReqDTO.getPageSize(),0)),response);

        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(recordRespDTOPage),response);
    }


    @RequestMapping(value = "record",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHzEplRecord(FindHzEPLRecordReqDTO recordReqDTO, HttpServletResponse response){
        Page<HzEPLRecordRespDTO> recordRespDTOPage = hzEPLManageRecordService.getHzEPLRecordForPage(recordReqDTO);
        List<HzEPLRecordRespDTO> recordRespDTOS =  recordRespDTOPage.getResult();
//        if(ListUtil.isEmpty(recordRespDTOS)){
//            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无符合数据"),response);
//        }
//        writeAjaxJSONResponse(ResultMessageBuilder.build(recordRespDTOS),response);
        Map<String, Object> ret = new HashMap<>();
        if (ListUtil.isEmpty(recordRespDTOS)) {
            return ret;
        }
        List<Map<String, String>> _list = new ArrayList<>();
        recordRespDTOS.forEach(dto -> {
            Map<String, String> _res = new HashMap<>();
            _res.put("puid", dto.getPuid());
            _res.put("level", dto.getLevel());
            _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
            _res.put("rank", dto.getRank());
            _res.put("groupNum",dto.getGroupNum() );
            _res.put("lineId",dto.getLineId() );
            _res.put("nameZh", dto.getNameZh());
            _res.put("nameEn",dto.getNameEn() );
            _res.put("pUnit",dto.getpUnit() );
            _res.put("pRentLow",dto.getpRentLow()) ;
            _res.put("pRentHigh",dto.getpRentHigh() );
            _res.put("pPictureNo",dto.getpPictureNo() );
            _res.put("pInstallPictureNo",dto.getpInstallPictureNo() );
            _res.put("pMap",dto.getpMap() );
            _res.put("pMaterialHigh",dto.getpMaterialHigh() );
            _res.put("pMaterial1",dto.getpMaterial1());
            _res.put("pMaterial2",dto.getpMaterial2());
            _res.put("pMaterial3",dto.getpMaterial3() );
            _res.put("pDensity",dto.getpDensity() );
            _res.put("pMaterialStandard",dto.getpMaterialStandard() );
            _res.put("pSurfaceManage",dto.getpSurfaceManage() );
            _res.put("pTextureNo",dto.getpTextureNo());
            _res.put("pMadeArt", dto.getpMadeArt());
            _res.put("pSymmetric",dto.getpSymmetric());
            _res.put("pImportance", dto.getpImportance());
            _res.put("pIsRulePart",dto.getpIsRulePart());
            _res.put("pRulePartNo",dto.getpRulePartNo() );
            _res.put("pCasketPart",dto.getpCasketPart() );
            _res.put("pDevelopType",dto.getpDevelopType() );
            _res.put("pDataVersion", dto.getpDataVersion());
            _res.put("pTargetHeight", dto.getpTargetHeight());
            _res.put("pEstimateHeight", dto.getpEstimateHeight());
            _res.put("pActualHeight",dto.getpActualHeight() );
            _res.put("pFixture", dto.getpFixture());
            _res.put("pFixtureSpec", dto.getpFixtureSpec());
            _res.put("pFixtureLevel", dto.getpFixtureLevel());
            _res.put("pTorque",dto.getpTorque() );
            _res.put("pMajorDept",dto.getpMajorDept() );
            _res.put("pDutyEngineer", dto.getpDutyEngineer());
            _res.put("pSupplier", dto.getpSupplier());
            _res.put("pSupplierNo", dto.getpSupplierNo());
            _res.put("pBuyEngineer", dto.getpBuyEngineer());
            _res.put("pRemark", dto.getpRemark());
            _res.put("pItemClassification", dto.getpItemClassification());
            _res.put("pItemResource", dto.getpItemResource());
            _res.put("pSupplyState", dto.getpSupplyState());
            _res.put("resource",dto.getResource() );
            _res.put("type", dto.getType());
            _res.put("buyUnit", dto.getBuyUnit());
            _res.put("workShop1", dto.getWorkShop1());
            _res.put("workShop2", dto.getWorkShop2());
            _res.put("productLine",dto.getProductLine());
            _res.put("mouldType", dto.getMouldType());
            _res.put("outerPart",dto.getOuterPart());
            _res.put("colorPart", dto.getColorPart());
            _res.put("station",dto.getStation());
            _res.put("sparePart", dto.getSparePart());
            _res.put("sparePartNum", dto.getSparePartNum());
            _res.put("processRoute", dto.getProcessRoute());
            _res.put("laborHour",dto.getLaborHour());
            _res.put("rhythm", dto.getRhythm());
            _res.put("solderJoint", dto.getSolderJoint());
            _res.put("machineMaterial",dto.getMachineMaterial());
            _res.put("standardPart", dto.getStandardPart());
            _res.put("tools", dto.getTools());
            _res.put("wasterProduct",dto.getWasterProduct());
            _res.put("change", dto.getChange());
            _res.put("changeNum",dto.getChangeNum());
            _list.add(_res);
        });
        ret.put("totalCount", recordRespDTOPage.getTotalCount());
        ret.put("result", _list);
        return ret;
        }

    @RequestMapping(value = "title")
    public void getEplTitle(HttpServletResponse response){
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("pState","状态值");
        tableTitle.put("level", "层级");
        tableTitle.put("pBomOfWhichDept", "专业");
        tableTitle.put("rank", "级别");
        tableTitle.put("groupNum", "分组号");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("nameZh", "名称");
        tableTitle.put("nameEn", "英文名称");
        tableTitle.put("pUnit", "单位");
        tableTitle.put("pRentLow", "分时租赁低配");
        tableTitle.put("pRentHigh", "分时租赁高配");
        tableTitle.put("pPictureNo", "图号");
        tableTitle.put("pInstallPictureNo", "安装图号");
        tableTitle.put("pMap", "图幅");
        tableTitle.put("pMaterialHigh", "料厚");
        tableTitle.put("pMaterial1", "材料1");
        tableTitle.put("pMaterial2", "材料2");
        tableTitle.put("pMaterial3", "材料3");
        tableTitle.put("pDensity", "密度");
        tableTitle.put("pMaterialStandard", "材料标准");
        tableTitle.put("pSurfaceManage", "表面处理");
        tableTitle.put("pTextureNo", "纹理编号/色彩编号");
        tableTitle.put("pMadeArt", "制造工艺");
        tableTitle.put("pSymmetric", "对称");
        tableTitle.put("pImportance", "重要度");
        tableTitle.put("pIsRulePart", "是否法规件");
        tableTitle.put("pRulePartNo", "法规件型号");
        tableTitle.put("pCasketPart", "黑白灰匣子件");
        tableTitle.put("pDevelopType", "开发类型");
        tableTitle.put("pDataVersion", "数据版本");
        tableTitle.put("pTargetHeight", "目标重量");
        tableTitle.put("pEstimateHeight", "预估重量");
        tableTitle.put("pActualHeight", "实际重量");
        tableTitle.put("pFixture", "紧固件");
        tableTitle.put("pFixtureSpec", "紧固件规格");
        tableTitle.put("pFixtureLevel", "紧固件性能等级");
        tableTitle.put("pTorque", "扭矩");
        tableTitle.put("pMajorDept", "专业部门");
        tableTitle.put("pDutyEngineer", "责任工程师");
        tableTitle.put("pSupplier", "供应商");
        tableTitle.put("pSupplierNo", "供应商代码");
        tableTitle.put("pBuyEngineer", "采购工程师");
        tableTitle.put("pRemark", "备注");
        tableTitle.put("pItemClassification", "零件分类");
        tableTitle.put("pItemResource", "零部件来源");
        tableTitle.put("pSupplyState", "供货状态");
        tableTitle.put("resource", "自制/采购");
        tableTitle.put("type", "焊接/装配");
        tableTitle.put("buyUnit", "采购单元");
        tableTitle.put("workShop1", "车间1");
        tableTitle.put("workShop2", "车间2");
        tableTitle.put("productLine", "生产线");
        tableTitle.put("mouldType", "模具类别");
        tableTitle.put("outerPart", "外委件");
        tableTitle.put("colorPart", "颜色件");
        tableTitle.put("station","工位");
        tableTitle.put("sparePart", "备件");
        tableTitle.put("sparePartNum", "备件编号");
        tableTitle.put("processRoute", "工艺路线");
        tableTitle.put("laborHour", "人工工时");
        tableTitle.put("rhythm", "节拍");
        tableTitle.put("solderJoint", "焊点");
        tableTitle.put("machineMaterial", "机物料");
        tableTitle.put("standardPart", "标准件");
        tableTitle.put("tools", "工艺");
        tableTitle.put("wasterProduct", "废品");
        tableTitle.put("change", "变更");
        tableTitle.put("changeNum", "变更号");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }
}
