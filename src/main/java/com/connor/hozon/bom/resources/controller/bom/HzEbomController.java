package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.DefaultPageQuery;
import com.connor.hozon.bom.resources.query.HzEbomByPageQuery;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import com.connor.hozon.bom.sys.entity.User;
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
 * \* User: xulf
 * \* Date: 2018/6/4
 * \* Time: 13:01
 * \
 */
@Controller
@RequestMapping(value = "/ebom")
public class HzEbomController extends BaseController {

    @Autowired
    private HzEbomService hzEbomService;

    @RequestMapping(value = "/ebomTitle",method = RequestMethod.GET)
    public void getEbomTitle(String projectId, HttpServletResponse response) {
        if(projectId==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"), response);
            return;
        }
        JSONArray array = hzEbomService.getEbomTitle(projectId);
        if(array==null){
        writeAjaxJSONResponse(ResultMessageBuilder.build(false,"网络错误！"), response);
    }else{
            writeAjaxJSONResponse(ResultMessageBuilder.build(array), response);
        }
    }

    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void getEbomTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No","No");
        tableTitle.put("level","层级" );
        tableTitle.put("rank","级别" );
        tableTitle.put("pBomOfWhichDept","专业" );
        tableTitle.put("groupNum","分组号");
        tableTitle.put("lineId","零件号" );
        tableTitle.put("fna","FNA");
        tableTitle.put("pBomLinePartName","名称" );
        tableTitle.put("pBomLinePartClass","零件分类" );
        tableTitle.put("pBomLinePartEnName","英文名称");
        tableTitle.put("pBomLinePartResource","零件来源");
        tableTitle.put("pFastener","紧固件");
        tableTitle.put("p3cpartFlag","是否3C件" );
        tableTitle.put("pInOutSideFlag","内外饰标识");
        tableTitle.put("pUpc","UPC");
        tableTitle.put("pFnaDesc","FNA描述" );
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
        tableTitle.put("pRegulationCode","法规件型号");
        tableTitle.put("pBwgBoxPart","黑白灰匣子件" );
        tableTitle.put("pDevelopType","开发类别");
        tableTitle.put("pDataVersion","数据版本" );
        tableTitle.put("pTargetWeight","目标重量(kg)");
        tableTitle.put("pFeatureWeight","预估重量(kg)");
        tableTitle.put("pActualWeight","实际重量(kg)" );
        tableTitle.put("pFastenerStandard","紧固件规格");
        tableTitle.put("pFastenerLevel","紧固件性能等级");
        tableTitle.put("pTorque","扭矩" );
        tableTitle.put("pDutyEngineer","责任工程师");
        tableTitle.put("pSupply","供应商");
        tableTitle.put("pSupplyCode","供应商代码" );
        tableTitle.put("pRemark","备注");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }
    

    @RequestMapping(value = "getEBom/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEbomList(HzEbomByPageQuery query) {
        HzEbomByPageQuery ebomByPageQuery = query;
        ebomByPageQuery.setPageSize(0);
        try{
            ebomByPageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        }catch (Exception e){

        }
        Page<HzEbomRespDTO> recordRespDTOPage = hzEbomService.getHzEbomPage(ebomByPageQuery);
        Map<String, Object> ret = new HashMap<>();
        if(recordRespDTOPage == null){
            return ret;
        }
        List<HzEbomRespDTO> recordRespDTOS =  recordRespDTOPage.getResult();
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

    @RequestMapping(value = "getEBom", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEbomById(String puid,String projectId ) {
        Map<String, Object> ret = new HashMap<>();
        if(puid == null || projectId == null){
            return ret;
        }
        HzEbomRespDTO recordRespDTO = hzEbomService.fingEbomById(puid,projectId);
        if(recordRespDTO == null){
            return ret;
        }
        JSONArray array = recordRespDTO.getJsonArray();
        JSONObject object = array.getJSONObject(0);
        ret = object;
        return ret;
    }


    @RequestMapping(value = "addEbom",method = RequestMethod.GET)
    public String addEbom(String projectId,String puid,Model model) {
//        if(projectId == null){
//            return "";
//        }
//        JSONArray array = hzEbomService.getEbomTitle(projectId);
//        if(array == null){
//            return "";
//        }
//
//        //过滤掉没必要的标题
//        JSONArray jsonArray = new JSONArray();
//        String[] strings1 = (String[]) array.get(0);
//        String[] strings2 = (String[]) array.get(1);
//
//        List<String> list1 = Arrays.asList(strings1);
//        List<String> list2 = Arrays.asList(strings2);
//        List<String> arrayList1=new ArrayList<String>(list1);
//        List<String> arrayList2=new ArrayList<String>(list2);
//        if(list1.contains("puid")){
//            arrayList1.remove("puid");
//        }
//        if(list1.contains("序号")){
//            arrayList1.remove("序号");
//        }
//        if(list1.contains("级别")){
//            arrayList1.remove("级别");
//        }
//        if(list1.contains("分组号")){
//            arrayList1.remove("分组号");
//        }
//        if(list1.contains("层级")){
//            arrayList1.remove("层级");
//        }
//
//        if(list2.contains("puid")){
//            arrayList2.remove("puid");
//        }
//        if(list2.contains("No")){
//            arrayList2.remove("No");
//        }
//        if(list2.contains("rank")){
//            arrayList2.remove("rank");
//        }
//        if(list2.contains("groupNum")){
//            arrayList2.remove("groupNum");
//        }
//        if(list2.contains("level")){
//            arrayList2.remove("level");
//        }
//        if(puid!=null){
//            HzEbomRespDTO recordRespDTO = hzEbomService.fingEbomById(puid,projectId);
//            if(array == null ||recordRespDTO == null){
//                return "";
//            }
//            JSONArray array1 = recordRespDTO.getJsonArray();
//            JSONObject object = array1.getJSONObject(0);
//            for(int i =0 ;i<strings2.length;i++){
//                if("puid".equals(strings2[i])){
//                    model.addAttribute("puid",object.getString(strings2[i]));
//                    break;
//                }
//            }
//        }
//        String[] strings3 = arrayList1.toArray(new String[0]);
//        String[] strings4 = arrayList2.toArray(new String[0]);
//        jsonArray.add(strings3);
//        jsonArray.add(strings4);
        model.addAttribute("data",puid);

        return "bomManage/ebom/ebomManage/addebomManage";
    }

    @RequestMapping(value = "updateEbom",method = RequestMethod.GET)
    public String updateEbom(String projectId,String puid,Model model) {
        if(projectId == null || puid == null){
            return "";
        }
        HzEbomRespDTO recordRespDTO = hzEbomService.fingEbomById(puid,projectId);
        JSONArray array1 = recordRespDTO.getJsonArray();
        JSONObject object = array1.getJSONObject(0);
        recordRespDTO.setPuid(puid);
        recordRespDTO.setpBomOfWhichDept(object.getString("pBomOfWhichDept"));
        recordRespDTO.setRank(object.getString("rank"));
        recordRespDTO.setGroupNum(object.getString("groupNum"));
        recordRespDTO.setLevel(object.getString("level"));
        recordRespDTO.setLineId(object.getString("lineId"));
        recordRespDTO.setFastener(object.getString("fastener"));
        recordRespDTO.setpBomLinePartResource(object.getString("pBomLinePartResource"));
        recordRespDTO.setpBomLinePartName(object.getString("pBomLinePartName"));
        recordRespDTO.setpBomLinePartEnName(object.getString("pBomLinePartEnName"));
        recordRespDTO.setpBomLinePartClass(object.getString("pBomLinePartClass"));
        recordRespDTO.setP3cpartFlag(object.getString("p3cpartFlag"));
        recordRespDTO.setpActualWeight(object.getString("pActualWeight"));
        recordRespDTO.setpBwgBoxPart(object.getString("pBwgBoxPart"));
        recordRespDTO.setpDataVersion(object.getString("pDataVersion"));
        recordRespDTO.setpDensity(object.getString("pDensity"));
        recordRespDTO.setpDevelopType(object.getString("pDevelopType"));
        recordRespDTO.setpDutyEngineer(object.getString("pDutyEngineer"));
        recordRespDTO.setpFastenerLevel(object.getString("pFastenerLevel"));
        recordRespDTO.setpFastenerStandard(object.getString("pFastenerStandard"));
        recordRespDTO.setpFeatureWeight(object.getString("pFeatureWeight"));
        recordRespDTO.setpFnaDesc(object.getString("pFnaDesc"));
        recordRespDTO.setpFnaInfo(object.getString("fna"));
        recordRespDTO.setpImportance(object.getString("pImportance"));
        recordRespDTO.setpInOutSideFlag(object.getString("pInOutSideFlag"));
        recordRespDTO.setpManuProcess(object.getString("pManuProcess"));
        recordRespDTO.setpMaterial1(object.getString("pMaterial1"));
        recordRespDTO.setpMaterial2(object.getString("pMaterial2"));
        recordRespDTO.setpMaterial3(object.getString("pMaterial3"));
        recordRespDTO.setpMaterialHigh(object.getString("pMaterialHigh"));
        recordRespDTO.setpMaterialStandard(object.getString("pMaterialStandard"));
        recordRespDTO.setpPictureNo(object.getString("pPictureNo"));
        recordRespDTO.setpPictureSheet(object.getString("pPictureSheet"));
        recordRespDTO.setpUpc(object.getString("pUpc"));
        recordRespDTO.setpTorque(object.getString("pTorque"));
        recordRespDTO.setpUnit(object.getString("pUnit"));
        recordRespDTO.setpTextureColorNum(object.getString("pTextureColorNum"));
        recordRespDTO.setpTargetWeight(object.getString("pTargetWeight"));
        recordRespDTO.setpSymmetry(object.getString("pSymmetry"));
        recordRespDTO.setpSurfaceTreat(object.getString("pSurfaceTreat"));
        recordRespDTO.setpSupplyCode(object.getString("pSupplyCode"));
        recordRespDTO.setpRemark(object.getString("pRemark"));
        recordRespDTO.setpRegulationFlag(object.getString("pRegulationFlag"));
        recordRespDTO.setpRegulationCode(object.getString("pRegulationCode"));
//        JSONArray array = hzEbomService.getEbomTitle(projectId);
//        if(array == null ||recordRespDTO == null){
//            return "";
//        }
//        //过滤掉没必要的标题
//        JSONArray jsonArray = new JSONArray();
//        String[] strings1 = (String[]) array.get(0);
//        String[] strings2 = (String[]) array.get(1);
//
//        List<String> list1 = Arrays.asList(strings1);
//        List<String> list2 = Arrays.asList(strings2);
//        List<String> arrayList1=new ArrayList<String>(list1);
//        List<String> arrayList2=new ArrayList<String>(list2);
//
//        if(list1.contains("序号")){
//            arrayList1.remove("序号");
//        }
//        if(list1.contains("级别")){
//            arrayList1.remove("级别");
//        }
//        if(list1.contains("层级")){
//            arrayList1.remove("层级");
//        }
//        if(list1.contains("分组号")){
//            arrayList1.remove("分组号");
//        }
//        if(list1.contains("puid")){
//            arrayList1.remove("puid");
//        }
//        if(list2.contains("No")){
//            arrayList2.remove("No");
//        }
//        if(list2.contains("rank")){
//            arrayList2.remove("rank");
//        }
//        if(list2.contains("level")){
//            arrayList2.remove("level");
//        }
//        if(list2.contains("groupNum")){
//            arrayList2.remove("groupNum");
//        }
//        if(list2.contains("puid")){
//            arrayList2.remove("puid");
//        }
//        String[] strings3 =arrayList1.toArray(new String[0]);;
//        String[] strings4 = arrayList2.toArray(new String[0]);
//        jsonArray.add(strings3);
//        jsonArray.add(strings4);
//
//
//        JSONArray array1 = recordRespDTO.getJsonArray();
//        JSONObject object = array1.getJSONObject(0);
//        String[] strings5 = new String[strings4.length];
//        for(int i =0 ;i<strings2.length;i++){
//            if("puid".equals(strings2[i])){
//                model.addAttribute("puid",object.getString(strings2[i]));
//                break;
//            }
//        }
//
//        for(int i =0;i<strings4.length;i++){
//            strings5[i] = object.getString(strings4[i])==null?"":object.getString(strings4[i]);
//        }
//        jsonArray.add(strings5);
        model.addAttribute("data",recordRespDTO);

        return "bomManage/ebom/ebomManage/updateEbomManage";
    }
    /**
     * 添加ebom信息
     * @param reqDTO
     * @param map
     * @param response
     */
    @RequestMapping(value = "add/ebom",method = RequestMethod.POST)
    public void addEbomToDB(AddHzEbomReqDTO reqDTO, @RequestBody Map<String,Object> map, HttpServletResponse response){
        if(reqDTO.getProjectId()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"), response);
            return;
        }
        if(map.containsKey("lineId")){
            String itemId = (String)map.get("lineId");
            if(itemId ==""||itemId == null) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, "零件号不能为空！"), response);
                return;
            }
            if(itemId.contains("-")){
                if(itemId.split("-")[1].length()<4){
                    writeAjaxJSONResponse(ResultMessageBuilder.build(false, "零件号-后面的长度不能小于4！"), response);
                    return;
                }
            }
        }
        User user = UserInfo.getUser();
        if(user.getGroupId()!=9){//管理员权限
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"您没有权限进行当前操作！"), response);
            return;
        }
        AddHzEbomReqDTO dto = new AddHzEbomReqDTO();
        dto.setMap(map);
        dto.setProjectId(reqDTO.getProjectId());
        OperateResultMessageRespDTO respDTO = hzEbomService.addHzEbomRecord(dto);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }


    /**
     * 更新ebom信息
     * @param reqDTO
     * @param map
     * @param response
     */
    @RequestMapping(value = "update/ebom",method = RequestMethod.POST)
    public void updateEbomToDB(UpdateHzEbomReqDTO reqDTO, @RequestBody Map<String,Object> map, HttpServletResponse response){
        if(map.get("projectPuid").equals("") || map.get("projectPuid")== null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"), response);
            return;
        }
        if(map.containsKey("lineId")){
            String itemId = (String)map.get("lineId");
            if(itemId ==""||itemId == null) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, "零件号不能为空！"), response);
                return;
            }
            if(itemId.contains("-")){
                if(itemId.split("-")[1].length()<4){
                    writeAjaxJSONResponse(ResultMessageBuilder.build(false, "零件号-后面的长度不能小于4！"), response);
                    return;
                }
            }

        }
        User user = UserInfo.getUser();
        if(user.getGroupId()!=9){//管理员权限
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"您没有权限进行当前操作！"), response);
            return;
        }
        UpdateHzEbomReqDTO dto = new UpdateHzEbomReqDTO();
        dto.setUpdateContent(map);
        dto.setProjectId(reqDTO.getProjectId());
        OperateResultMessageRespDTO respDTO= hzEbomService.updateHzEbomRecord(dto);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }


    /**
     * 删除ebom信息
     * @param reqDTO
     * @param
     * @param response
     */
    @RequestMapping(value = "delete/ebom",method = RequestMethod.POST)
    public void deleteEbomToDB(@RequestBody DeleteHzEbomReqDTO reqDTO, HttpServletResponse response){
        if(reqDTO.getProjectId()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"), response);
            return;
        }
        User user = UserInfo.getUser();
        if(user.getGroupId()!=9){//管理员权限
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"您没有权限进行当前操作！"), response);
            return;
        }
        OperateResultMessageRespDTO respDTO = hzEbomService.deleteHzEbomRecordById(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }


    /**
     * 获取当前登录用户信息
     */
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public void getUser(HttpServletResponse  response){
        User user = UserInfo.getUser();
        writeAjaxJSONResponse(ResultMessageBuilder.build(user),response);
        System.out.println(JSON.toJSONString(user));
    }
}