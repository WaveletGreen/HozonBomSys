package com.connor.hozon.controller.bom.bom;

import cn.net.connor.hozon.common.util.ListUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.controller.bom.BaseController;
import com.connor.hozon.resources.domain.dto.request.*;
import com.connor.hozon.resources.domain.dto.response.HzEbomLevelRespDTO;
import com.connor.hozon.resources.domain.dto.response.HzEbomRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.query.HzEbomByPageQuery;
import com.connor.hozon.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.bom.HzEBOMReadService;
import com.connor.hozon.resources.service.bom.HzEBOMWriteService;
import com.connor.hozon.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.resources.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.hibernate.jpa.internal.QueryImpl.LOG;

/**
 * \* User: xulf
 * \* Date: 2018/6/4
 * \* Time: 13:01
 * \
 */
@Controller
@RequestMapping(value = "/ebom")
@Scope("session")//因为controller里使用了全局变量，并发的时候会造成全局变量的异常
public class HzEbomController extends BaseController {

    @Autowired
    private HzEBOMReadService hzEBOMReadService;

    @Autowired
    private HzEBOMWriteService hzEBOMWriteService;

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    private LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();

    private Map<String,Object> orderDataObject = new HashMap<>();

    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void getEbomTitle(String projectId,HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No","序号");
        tableTitle.put("lineId","零件号" );
        tableTitle.put("pBomLinePartName","名称" );
        tableTitle.put("level","层级" );
        tableTitle.put("pBomOfWhichDept","专业" );
        tableTitle.put("rank","级别" );
        tableTitle.put("groupNum","分组号");
        tableTitle.put("lineNo","查找编号");
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
        tableTitle.put("sparePart","备件");
        tableTitle.put("sparePartNum","备件编号");
        tableTitle.put("colorPart","是否颜色件");
        tableTitle.put("effectTime","生效时间");
        //获取该项目下的所有车型模型
        tableTitle.putAll(hzSingleVehiclesServices.singleVehDosageTitle(projectId));
        this.tableTitle = tableTitle;
        toJSONResponse(Result.build(true,tableTitle), response);
    }
    

    @RequestMapping(value = "getEBom/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getEbomList(HzEbomByPageQuery query) {
        Page<HzEbomRespDTO> recordRespDTOPage = hzEBOMReadService.getHzEbomPage(query);
        JSONObject object = new JSONObject();
        if(recordRespDTOPage == null){
            return object;
        }
        List<HzEbomRespDTO> recordRespDTOS =  recordRespDTOPage.getResult();
        if (ListUtils.isEmpty(recordRespDTOS)) {
            return object;
        }
        List<JSONObject> list = new ArrayList<>();
        JSONArray array = recordRespDTOS.get(0).getJsonArray();
        for(int i =0;i<array.size();i++){
            JSONObject o = array.getJSONObject(i);
            list.add(o);
        }
        object.put("totalCount", recordRespDTOPage.getTotalCount());
        object.put("result", list);
        return object;
    }

    @RequestMapping(value = "getEBom", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getEbomById(String puid,String projectId ) {
        JSONObject object = new JSONObject();
        if(puid == null || projectId == null){
            return object;
        }
        HzEbomRespDTO recordRespDTO = hzEBOMReadService.fingEbomById(puid,projectId);
        if(recordRespDTO == null){
            return object;
        }
        JSONArray array = recordRespDTO.getJsonArray();
        return array.getJSONObject(0);
    }


    @RequestMapping(value = "addEbom",method = RequestMethod.GET)
    public String addEbom(String puid,Model model) {
        HzEbomRespDTO respDTO = new HzEbomRespDTO();
        respDTO.setPuid(puid);
        model.addAttribute("data",respDTO);
        return "bomManage/ebom/ebomManage/addebomManage";
    }

    /**
     * EBOM-PBOM-MBOM调整层级
     * @param projectId
     * @param puid
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "updateEbomLevel",method = RequestMethod.GET)
    public String updateEbomLevel(String projectId,String puid,String id,Model model){
        if(projectId == null || puid == null){
            return "";
        }
        //判断勾选零件是否存在子层
        int isHas = hzEBOMReadService.findIsHasByPuid(puid,projectId);

        //recordRespDTO传给updateEbomLevelManage界面的值，而非界面传回后台的值
        HzEbomLevelRespDTO recordRespDTO = hzEBOMReadService.fingEbomLevelById(puid,projectId);
        recordRespDTO.setPuid(puid);//调整层级的当前零件puid
        recordRespDTO.setProjectId(projectId);//项目id
        //recordRespDTO.setLineId(object.getString("lineId"));//父层零件号
        //recordRespDTO.setFindNum(object.getString("lineNo"));//查找编号
        recordRespDTO.setIsHas(isHas);//判断是否有子层

        model.addAttribute("data",recordRespDTO);
        return "bomManage/ebom/ebomManage/updateEbomLevelManage";
    }

    @RequestMapping(value = "update/ebomLevel",method = RequestMethod.POST)
    public void updateEbomLevelToDB(@RequestBody UpdateHzEbomLeveReqDTO reqDTO, HttpServletResponse response){

        WriteResultRespDTO respDTO= hzEBOMWriteService.extendsBomStructureInNewParent(reqDTO);

        toJSONResponse(Result.build(
                WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

    /**
     * 下载EBOM
     */
    @RequestMapping(value = "excelExport",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listDownLoad(@RequestBody  List<HzEbomRespDTO> dtos, HttpServletRequest request) {
        boolean flag=true;
        JSONObject result=new JSONObject();
        try {
            //static/files/tableExport.xlsx
            String fileName = "tableExport.xlsx";//文件名-tableExport
            Object[] temp = tableTitle.values().toArray();

            String[] title = new String[temp.length];
            for(int i=0;i<temp.length;i++){
                title[i] = temp[i].toString();
            }
            //当前页的数据
            List<String[]> dataList = new ArrayList<String[]>();
            int index=1;
            for (HzEbomRespDTO hzEbomRespDTO : dtos) {
                String[] cellArr = new String[title.length];
                cellArr[0] = index+"";
                index++;
                cellArr[1] = hzEbomRespDTO.getLineId();
                cellArr[2] = hzEbomRespDTO.getpBomLinePartName();
                cellArr[3] = hzEbomRespDTO.getLevel();
                cellArr[4] = hzEbomRespDTO.getpBomOfWhichDept();
                cellArr[5] = hzEbomRespDTO.getRank();
                cellArr[6] = hzEbomRespDTO.getGroupNum();
                cellArr[7] = hzEbomRespDTO.getLineNo();
                cellArr[8] = hzEbomRespDTO.getpBomLinePartEnName();
                cellArr[9] = hzEbomRespDTO.getpLouaFlag();
                cellArr[10] = hzEbomRespDTO.getpUnit();
                cellArr[11] = hzEbomRespDTO.getpPictureNo();
                cellArr[12] = hzEbomRespDTO.getpPictureSheet();
                cellArr[13] = hzEbomRespDTO.getpMaterialHigh();
                cellArr[14] = hzEbomRespDTO.getpMaterial1();
                cellArr[15] = hzEbomRespDTO.getpMaterial2();
                cellArr[16] = hzEbomRespDTO.getpMaterial3();
                cellArr[17] = hzEbomRespDTO.getpDensity();
                cellArr[18] = hzEbomRespDTO.getpMaterialStandard();
                cellArr[19] = hzEbomRespDTO.getpSurfaceTreat();
                cellArr[20] = hzEbomRespDTO.getpTextureColorNum();
                cellArr[21] = hzEbomRespDTO.getpManuProcess();
                cellArr[22] = hzEbomRespDTO.getpSymmetry();
                cellArr[23] = hzEbomRespDTO.getpImportance();
                cellArr[24] = hzEbomRespDTO.getpRegulationFlag();
                cellArr[25] = hzEbomRespDTO.getP3cpartFlag();
                cellArr[26] = hzEbomRespDTO.getpRegulationCode();
                cellArr[27] = hzEbomRespDTO.getpBwgBoxPart();
                cellArr[28] = hzEbomRespDTO.getpDevelopType();
                cellArr[29] = hzEbomRespDTO.getpDataVersion();
                cellArr[30] = hzEbomRespDTO.getpTargetWeight();
                cellArr[31] = hzEbomRespDTO.getpFeatureWeight();
                cellArr[32] = hzEbomRespDTO.getpActualWeight();
                cellArr[33] = hzEbomRespDTO.getpFastener();
                cellArr[34] = hzEbomRespDTO.getpFastenerStandard();
                cellArr[35] = hzEbomRespDTO.getpFastenerLevel();
                cellArr[36] = hzEbomRespDTO.getpTorque();
                cellArr[37] = hzEbomRespDTO.getpDutyEngineer();
                cellArr[38] = hzEbomRespDTO.getpSupply();
                cellArr[39] = hzEbomRespDTO.getpSupplyCode();
                cellArr[40] = hzEbomRespDTO.getpBuyEngineer();
                cellArr[41] = hzEbomRespDTO.getpRemark();
                cellArr[42] = hzEbomRespDTO.getpBomLinePartClass();
                cellArr[43] = hzEbomRespDTO.getpBomLinePartResource();
                cellArr[44] = hzEbomRespDTO.getpInOutSideFlag();
                cellArr[45] = hzEbomRespDTO.getpUpc();
                cellArr[46] = hzEbomRespDTO.getFna();
                cellArr[47] = hzEbomRespDTO.getpFnaDesc();
                cellArr[48] = hzEbomRespDTO.getNumber();
                cellArr[49] = hzEbomRespDTO.getSparePart();
                cellArr[50] = hzEbomRespDTO.getSparePartNum();
                cellArr[51] = hzEbomRespDTO.getColorPart();
                cellArr[52] = hzEbomRespDTO.getEffectTime();
                if(hzEbomRespDTO.getMap().size()>0){
                    //动态获取单车配置用量表头
                    for(int i = 0; i< hzEbomRespDTO.getMap().size(); i++){
                        cellArr[53+i] = hzEbomRespDTO.getMap().values().toArray()[i].toString();
                    }
                }
                dataList.add(cellArr);
            }
            flag = ExcelUtil.writeExcel(fileName, title, dataList,"ebom",request);

            if(flag){
                LOG.info(fileName+",文件创建成功");
                result.put("status",flag);
                result.put("msg","成功");
                result.put("path","./files/tableExport.xlsx");
            }else{
                LOG.info(fileName+",文件创建失败");
                result.put("status",flag);
                result.put("msg","失败");
            }
        } catch (Exception e) {
            if(LOG.isTraceEnabled())//isErrorEnabled()
                LOG.error(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "updateEbom",method = RequestMethod.GET)
    public String updateEbom(String projectId,String puid,Integer updateType,Model model) {
        if(StringUtil.isEmpty(projectId)){
            return "";
        }
        HzEbomRespDTO recordRespDTO = hzEBOMReadService.fingEbomById(puid,projectId);
        model.addAttribute("data",recordRespDTO);

        return "bomManage/ebom/ebomManage/updateEbomManage";
    }

    /**
     * 跳转到快速添加的页面
     * @param projectId
     * @param puid
     * @param updateType
     * @param model
     * @return
     */
    @RequestMapping(value = "QuickAddEbom",method = RequestMethod.GET)
    public String QuickAddEbom(String projectId,String puid,Integer updateType,Model model) {
        if(StringUtil.isEmpty(projectId)){
            return "";
        }
        HzEbomRespDTO recordRespDTO = hzEBOMReadService.fingEbomById(puid,projectId);
        model.addAttribute("data",recordRespDTO);

        return "bomManage/ebom/ebomManage/quickAddEbomManage";

    }


    /**
     * 跳转派生EBOM界面
     * @param projectId 项目ID
     * @param puid 已选零件号puid
     * @param model
     * @return
     */
    @RequestMapping(value = "deriveEbom",method = RequestMethod.GET)
    public String deriveEbom(String projectId,String puid,Model model){
        if(projectId == null || puid == null){
            return "";
        }
        DeriveHzEbomReqDTO recordRespDTO=new DeriveHzEbomReqDTO();
        recordRespDTO.setProjectId(projectId);
        recordRespDTO.setPuid(puid);
        model.addAttribute("data",recordRespDTO);
        return "bomManage/ebom/ebomManage/deriveEbomManage";
    }

    /**
     * 派生ebom信息
     * @param reqDTO
     * @param
     * @param response
     */
    @RequestMapping(value = "derive/ebom",method = RequestMethod.POST)
    public void deriveEbomToDB(@RequestBody DeriveHzEbomReqDTO reqDTO, HttpServletResponse response){

        WriteResultRespDTO respDTO= hzEBOMWriteService.deriveHzEbomRecord(reqDTO);

        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }


    /**
     * 快速添加ebom信息
     * @param quickReqDTO
     * @param
     * @param response
     */
    @RequestMapping(value = "add/quickAddEbom",method = RequestMethod.POST)
    public void quickAddEbomToDB(@RequestBody QuickAddHzEbomReqDTO quickReqDTO, HttpServletResponse response){

        WriteResultRespDTO respDTO = hzEBOMWriteService.quickAddHzEbomRecord(quickReqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }


    /**
     * 添加ebom信息
     * @param reqDTO
     * @param
     * @param response
     */
    @RequestMapping(value = "add/ebom",method = RequestMethod.POST)
    public void addEbomToDB(@RequestBody AddHzEbomReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzEBOMWriteService.addHzEbomRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

    /**
     * 更新ebom信息
     * @param reqDTO
     * @param
     * @param response
     */
    @RequestMapping(value = "update/ebom",method = RequestMethod.POST)
    public void updateEbomToDB(@RequestBody UpdateHzEbomReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO= hzEBOMWriteService.updateHzEbomRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }


    /**
     * 删除ebom信息
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "delete/ebom",method = RequestMethod.POST)
    public void deleteEbomToDB(@RequestBody DeleteHzEbomReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzEBOMWriteService.deleteHzEbomRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

    /**
     * EBOM发起变更数据到变更单
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "data/change",method = RequestMethod.POST)
    public void ebomDataToChangeOrder(@RequestBody AddDataToChangeOrderReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzEBOMWriteService.dataToChangeOrder(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

    /**
     * 跳转到Excel导入页面
     * @return
     */
    @RequestMapping(value = "importExcel",method = RequestMethod.GET)
    public String getExcelImport(){
        return "bomManage/ebom/ebomManage/excelImport";
    }

    /**
     * 获取变更表单
     * @return
     */
    @RequestMapping(value = "find/choose",method = RequestMethod.POST)
    public void getOrderChooseToPage(@RequestBody AddDataToChangeOrderReqDTO reqDTO,HttpServletResponse response){
        List<HzChangeOrderRecord> records = hzChangeOrderDAO.findHzChangeOrderRecordByProjectId(reqDTO.getProjectId());
        this.orderDataObject = new HashMap<>();
        this.orderDataObject.put("data",records);
        this.orderDataObject.put("puids",reqDTO.getPuids());
        toJSONResponse(Result.build(orderDataObject),response);
    }

    /**
     * 跳转到EBOM选择变更单
     * @return
     */
    @RequestMapping(value = "order/choose",method = RequestMethod.GET)
    public String getOrderChooseToPage(Model model){
        model.addAttribute("data",this.orderDataObject.get("data"));
        model.addAttribute("puids",this.orderDataObject.get("puids"));
        return "bomManage/ebom/ebomManage/ebomSetChangeForm";
    }
    /**
     * EBOM撤销
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "cancel",method = RequestMethod.POST)
    public void ebomCancel(@RequestBody BomBackReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzEBOMWriteService.backBomUtilLastValidState(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

//    /**  已废除
//     * MBOM跳转到EBOM的方法查询底层零部件
//     * @return
//     */
//    @RequestMapping("selectEbom")
//    public String selectEbom (String puid,Model model){
//        model.addAttribute("puid",puid);
//        return "bomManage/mbom/mbomMaintenance/selectEbom";
//    }
}