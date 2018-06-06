package com.connor.hozon.bom.resources.controller.bom;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * \* User: xulf
 * \* Date: 2018/6/4
 * \* Time: 13:01
 * \
 */
@Controller
@RequestMapping(value = "/ebom")
public class HzEbomController extends BaseController {

@RequestMapping(value = "/ebomTitle",method = RequestMethod.GET)
    public void getEbomTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("level", "层级");
        tableTitle.put("pBomOfWhichDept", "专业");
        tableTitle.put("rank", "级别");
        tableTitle.put("groupNum", "分组号");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("nameZh", "名称");//这个字段暂时是一个替代品，后续要改
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
    writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }
}