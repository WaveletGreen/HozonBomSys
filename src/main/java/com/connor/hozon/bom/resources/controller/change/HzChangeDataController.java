package com.connor.hozon.bom.resources.controller.change;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeDataRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataQuery;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.service.change.HzChangeDataService;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:
 */
@Controller
@RequestMapping("change/data")
public class HzChangeDataController extends BaseController {

    @Autowired
    private HzChangeDataService hzChangeDataService;

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    @RequestMapping(value = "ebom/title",method = RequestMethod.GET)
    public void getEbomTitle(String projectId,HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("state","状态");
        tableTitle.put("changeType","变更类型");
        tableTitle.put("lineId","零件号" );
        tableTitle.put("pBomLinePartName","名称" );
        tableTitle.put("level","层级" );
        tableTitle.put("pBomOfWhichDept","专业" );
        tableTitle.put("pBomLinePartEnName","英文名称");

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
        tableTitle.put("colorPart","是否颜色件");

        //获取该项目下的所有车型模型
        tableTitle.putAll(hzSingleVehiclesServices.singleVehDosageTitle("1c128c60-84a2-4076-9b1c-f7093e56e4df"));
        toJSONResponse(Result.build(tableTitle), response);
    }

    @RequestMapping(value = "order/hyper",method = RequestMethod.GET)
    public void getChangeOrderData(HzChangeDataQuery query,HttpServletResponse response){
        List<HzChangeDataRespDTO> respDTOS = hzChangeDataService.getChangeDataHyperRecord(query);
        if(ListUtil.isNotEmpty(respDTOS)){
            toJSONResponse(respDTOS,response);
            return;
        }
        toJSONResponse(null,response);
    }


    @RequestMapping(value = "ebom/page")
    public String ebomDataTOPage(Model model,String projectId,Long orderId){
        return "change/ChangeOrder/changeEbomTable";
    }

    @RequestMapping(value = "ebom/data",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getChangeDataDetail(HzChangeDataQuery query){
        query.setOrderId(1L);
        query.setProjectId("1c128c60-84a2-4076-9b1c-f7093e56e4df");
        List<HzEbomRespDTO> list = hzChangeDataService.getChangeDataRecordForEBOM(query);
        Map<String, Object> ret = new HashMap<>();
        if(ListUtil.isNotEmpty(list)){
            List<Map<String, Object>> _list = new ArrayList<>();
            list.forEach(dto -> {
                Map<String, Object> _res = new HashMap<>();
                _res.put("state",dto.getState());
                _res.put("changeType",dto.getChangeType());
                _res.put("lineId",dto.getLineId() );
                _res.put("pBomLinePartName",dto.getpBomLinePartName() );
                _res.put("level",dto.getLevel() );
                _res.put("pBomOfWhichDept",dto.getpBomOfWhichDept() );
                _res.put("pBomLinePartEnName",dto.getpBomLinePartEnName());

                _res.put("pUnit",dto.getpUnit());
                _res.put("pPictureNo",dto.getpPictureNo());
                _res.put("pPictureSheet",dto.getpPictureSheet() );
                _res.put("pMaterialHigh",dto.getpMaterialHigh() );
                _res.put("pMaterial1",dto.getpMaterial1());
                _res.put("pMaterial2",dto.getpMaterial2() );
                _res.put("pMaterial3",dto.getpMaterial3());
                _res.put("pDensity",dto.getpDensity());
                _res.put("pMaterialStandard",dto.getpMaterialStandard());
                _res.put("pSurfaceTreat",dto.getpSurfaceTreat() );
                _res.put("pTextureColorNum",dto.getpTextureColorNum());
                _res.put("pManuProcess",dto.getpManuProcess());
                _res.put("pSymmetry",dto.getpSymmetry() );
                _res.put("pImportance",dto.getpImportance());
                _res.put("pRegulationFlag",dto.getpRegulationFlag());
                _res.put("p3cpartFlag",dto.getP3cpartFlag() );

                _res.put("pRegulationCode",dto.getpRegulationCode());
                _res.put("pBwgBoxPart",dto.getpBwgBoxPart() );
                _res.put("pDevelopType",dto.getpDevelopType());
                _res.put("pDataVersion",dto.getpDataVersion() );
                _res.put("pTargetWeight",dto.getpTargetWeight());
                _res.put("pFeatureWeight",dto.getpFeatureWeight());
                _res.put("pActualWeight",dto.getpActualWeight() );
                _res.put("pFastener",dto.getpFastener());
                _res.put("pFastenerStandard",dto.getpFastenerStandard());
                _res.put("pFastenerLevel",dto.getpFastenerLevel());
                _res.put("pTorque",dto.getpTorque() );
                _res.put("pDutyEngineer",dto.getpDutyEngineer());
                _res.put("pSupply",dto.getpSupply());
                _res.put("pSupplyCode",dto.getpSupplyCode() );

                _res.put("pBuyEngineer",dto.getpBuyEngineer());
                _res.put("pRemark",dto.getpRemark());
                _res.put("pBomLinePartClass",dto.getpBomLinePartClass() );
                _res.put("pBomLinePartResource",dto.getpBomLinePartResource());
                _res.put("pInOutSideFlag",dto.getpInOutSideFlag());
                _res.put("pUpc",dto.getpUpc());
                _res.put("fna",dto.getFna());
                _res.put("pFnaDesc",dto.getpFnaDesc() );
                _res.put("number",dto.getNumber() );
                _res.put("colorPart",dto.getColorPart());
                _list.add(_res);
            });
            ret.put("result", _list);
            return ret;
        }
        return null;
    }

}
