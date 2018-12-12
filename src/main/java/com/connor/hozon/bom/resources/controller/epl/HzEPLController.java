package com.connor.hozon.bom.resources.controller.epl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.change.HzChangeOrderRecord;

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
    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void getEplTitle(HttpServletResponse response){
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No","序号");
        tableTitle.put("status","状态值");
        tableTitle.put("pBomOfWhichDept","专业" );
        tableTitle.put("lineId","零件号" );
        tableTitle.put("pBomLinePartName","名称" );
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
//        tableTitle.put("pUpc","UPC");
//        tableTitle.put("fna","FNA");
//        tableTitle.put("pFnaDesc","FNA描述" );
//
//        tableTitle.put("resource", "自制/采购");
//        tableTitle.put("type", "焊接/装配");
//        tableTitle.put("buyUnit", "采购单元");
//        tableTitle.put("workShop1", "车间1");
//        tableTitle.put("workShop2", "车间2");
//        tableTitle.put("productLine", "生产线");
//        tableTitle.put("mouldType", "模具类别");
//        tableTitle.put("outerPart", "外委件");
//        tableTitle.put("colorPart", "颜色件");
//        tableTitle.put("station","工位");
//
//        tableTitle.put("sparePart", "备件");
//        tableTitle.put("sparePartNum", "备件编号");
//        tableTitle.put("processRoute", "工艺路线");
//        tableTitle.put("laborHour", "人工工时");
//        tableTitle.put("rhythm", "节拍");
//        tableTitle.put("solderJoint", "焊点");
//        tableTitle.put("machineMaterial", "机物料");
//        tableTitle.put("standardPart", "标准件");
//        tableTitle.put("tools", "工具");
//        tableTitle.put("wasterProduct", "废品");
//        tableTitle.put("change", "变更");
//        tableTitle.put("changeNum", "变更号");
        toJSONResponse(Result.build(tableTitle), response);
    }


//    @RequestMapping(value = "record/page",method = RequestMethod.GET)
//    public void getHzEplRecordByPage(HzEPLByPageQuery query, HttpServletResponse response){
//        HzEPLByPageQuery ebomByPageQuery = query;
//        ebomByPageQuery.setPageSize(0);
//        try{
//            ebomByPageQuery.setPageSize(Integer.valueOf(query.getLimit()));
//        }catch (Exception e){
//
//        }
//        Page<HzEPLRecordRespDTO> recordRespDTOPage = hzEPLManageRecordService.getHzEPLRecordForPage(query);
//        List<HzEPLRecordRespDTO> recordRespDTOS =  recordRespDTOPage.getResult();
//        if (ListUtil.isEmpty(recordRespDTOS)) {
//            toJSONResponse(Result.build(false,"暂无数据",new Page<>(recordRespDTOPage.getPageNumber(),recordRespDTOPage.getPageSize(),0)),response);
//            return;
//        }
//        toJSONResponse(Result.build(recordRespDTOPage),response);
//    }


    @RequestMapping(value = "record",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHzEplRecord(HzEPLByPageQuery query){
        HzEPLByPageQuery ebomByPageQuery = query;
        ebomByPageQuery.setPageSize(0);
        try{
            ebomByPageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        }catch (Exception e){

        }
        Page<HzEPLRecordRespDTO> recordRespDTOPage = hzEPLManageRecordService.getHzEPLRecordForPage2(query);
        Map<String, Object> ret = new HashMap<>();
        if(recordRespDTOPage == null){
            return  ret;
        }
        List<HzEPLRecordRespDTO> recordRespDTOS =  recordRespDTOPage.getResult();

        if (ListUtil.isEmpty(recordRespDTOS)) {
            return ret;
        }
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        JSONArray array = recordRespDTOS.get(0).getJsonArray();
        for(int i =0;i<array.size();i++){
            JSONObject object = array.getJSONObject(i);
            map = object;
            list.add(map);
        }
        ret.put("totalCount", recordRespDTOPage.getTotalCount());
        ret.put("result", list);
        return ret;
        }

//    @RequestMapping(value = "title",method = RequestMethod.GET)
//    public void getEplTitle(String projectId ,HttpServletResponse response){
//        if(projectId == null){
//            toJSONResponse(Result.build(false,"非法参数！"), response);
//            return;
//        }
//        JSONArray array = hzEPLManageRecordService.getEPLTittle(projectId);
//        if(array==null){
//            toJSONResponse(Result.build(false,"网络错误！"), response);
//            return;
//        }
//        toJSONResponse(Result.build(array), response);
//    }





//    @RequestMapping(value = "epl/title2",method = RequestMethod.GET)
//    public void getEplTitle2(HttpServletResponse response){
//        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
//        tableTitle.put("No","序号");
//        tableTitle.put("status","状态值");
//        tableTitle.put("pBomOfWhichDept","专业" );
//        tableTitle.put("lineId","零件号" );
//
//        tableTitle.put("pBomLinePartName","名称" );
//        tableTitle.put("pBomLinePartEnName","英文名称");
//
//        tableTitle.put("pUnit","单位");
//        tableTitle.put("pPictureNo","图号");
//        tableTitle.put("pPictureSheet","图幅" );
//        tableTitle.put("pMaterialHigh","料厚" );
//        tableTitle.put("pMaterial1","材料1");
//        tableTitle.put("pMaterial2","材料2" );
//        tableTitle.put("pMaterial3","材料3" );
//        tableTitle.put("pDensity","密度");
//        tableTitle.put("pMaterialStandard","材料标准");
//        tableTitle.put("pSurfaceTreat","表面处理" );
//        tableTitle.put("pTextureColorNum","纹理编号/色彩编号");
//        tableTitle.put("pManuProcess","制造工艺");
//        tableTitle.put("pSymmetry","对称" );
//        tableTitle.put("pImportance","重要度");
//        tableTitle.put("pRegulationFlag","是否法规件");
//        tableTitle.put("p3cpartFlag","是否3C件" );
//        tableTitle.put("pRegulationCode","法规件型号");
//        tableTitle.put("pBwgBoxPart","黑白灰匣子件" );
//        tableTitle.put("pDevelopType","开发类别");
//        tableTitle.put("pDataVersion","数据版本" );
//        tableTitle.put("pTargetWeight","目标重量(kg)");
//        tableTitle.put("pFeatureWeight","预估重量(kg)");
//        tableTitle.put("pActualWeight","实际重量(kg)" );
//        tableTitle.put("pFastener","紧固件");
//        tableTitle.put("pFastenerStandard","紧固件规格");
//        tableTitle.put("pFastenerLevel","紧固件性能等级");
//        tableTitle.put("pTorque","扭矩" );
//        tableTitle.put("pDutyEngineer","责任工程师");
//        tableTitle.put("pSupply","供应商");
//        tableTitle.put("pSupplyCode","供应商代码" );
//        tableTitle.put("pBuyEngineer","采购工程师");
//        tableTitle.put("pRemark","备注");
//        tableTitle.put("pBomLinePartClass","零件分类" );
//        tableTitle.put("pBomLinePartResource","零件来源");
//        tableTitle.put("pInOutSideFlag","内外饰标识");
//        tableTitle.put("pUpc","UPC");
//        tableTitle.put("fna","FNA");
//        tableTitle.put("pFnaDesc","FNA描述" );
//
//        tableTitle.put("resource", "自制/采购");
//        tableTitle.put("type", "焊接/装配");
//        tableTitle.put("buyUnit", "采购单元");
//        tableTitle.put("workShop1", "车间1");
//        tableTitle.put("workShop2", "车间2");
//        tableTitle.put("productLine", "生产线");
//        tableTitle.put("mouldType", "模具类别");
//        tableTitle.put("outerPart", "外委件");
//        tableTitle.put("colorPart", "颜色件");
//        tableTitle.put("station","工位");
//
//        tableTitle.put("sparePart", "备件");
//        tableTitle.put("sparePartNum", "备件编号");
//        tableTitle.put("processRoute", "工艺路线");
//        tableTitle.put("laborHour", "人工工时");
//        tableTitle.put("rhythm", "节拍");
//        tableTitle.put("solderJoint", "焊点");
//        tableTitle.put("machineMaterial", "机物料");
//        tableTitle.put("standardPart", "标准件");
//        tableTitle.put("tools", "工具");
//        tableTitle.put("wasterProduct", "废品");
//        tableTitle.put("change", "变更");
//        tableTitle.put("changeNum", "变更号");
//        toJSONResponse(Result.build(tableTitle), response);
//    }
    /**
     * 跳转到EBOM选择变更单
     * @return
     */
    @RequestMapping(value = "order/choose",method = RequestMethod.GET)
    public String getOrderChooseToPage(String projectId,String puids,Model model){
        List<HzChangeOrderRecord> records = hzChangeOrderDAO.findHzChangeOrderRecordByProjectId(projectId);
        if(ListUtil.isNotEmpty(records)){
            model.addAttribute("data",records);
            model.addAttribute("puids",puids);
        }
        return "bomManage/epl/eplSetChangeForm";
    }
}
