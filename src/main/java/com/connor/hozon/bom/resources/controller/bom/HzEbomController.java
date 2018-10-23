package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0ModelService;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomLeveReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomLevelRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEbomByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.cfg.model.HzCfg0ModelRecord;

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

    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;
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
        tableTitle.put("colorPart","是否颜色件");
        //获取该项目下的所有车型模型
        tableTitle.putAll(hzSingleVehiclesServices.singleVehDosageTitle(projectId));
        toJSONResponse(Result.build(tableTitle), response);
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
        int isHas = hzEbomService.findIsHasByPuid(puid,projectId);

        //recordRespDTO传给updateEbomLevelManage界面的值，而非界面传回后台的值
        HzEbomLevelRespDTO recordRespDTO = hzEbomService.fingEbomLevelById(puid,projectId);
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
        boolean b = PrivilegeUtil.writePrivilege();
        if(!b){//管理员权限
            toJSONResponse(Result.build(false,"您没有权限进行当前操作！"), response);
            return;
        }

        //WriteResultRespDTO respDTO= hzEbomService.updateHzEbomLevelRecord(reqDTO);
        //测试
        WriteResultRespDTO respDTO= hzEbomService.testbomLevelChange(reqDTO);

        toJSONResponse(Result.build(
                WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }


    @RequestMapping(value = "updateEbom",method = RequestMethod.GET)
    public String updateEbom(String projectId,String puid,Model model) {
        if(StringUtil.isEmpty(projectId)){
            return "";
        }
        HzEbomRespDTO recordRespDTO = hzEbomService.fingEbomById(puid,projectId);
        model.addAttribute("data",recordRespDTO);

        return "bomManage/ebom/ebomManage/updateEbomManage";
    }
    /**
     * 添加ebom信息
     * @param reqDTO
     * @param
     * @param response
     */
    @RequestMapping(value = "add/ebom",method = RequestMethod.POST)
    public void addEbomToDB(@RequestBody AddHzEbomReqDTO reqDTO, HttpServletResponse response){
        if(reqDTO.getProjectId()==null){
            toJSONResponse(Result.build(false,"非法参数！"), response);
            return;
        }
        boolean b = PrivilegeUtil.writePrivilege();
        if(!b){//管理员权限
            toJSONResponse(Result.build(false,"您没有权限进行当前操作！"), response);
            return;
        }
        WriteResultRespDTO respDTO = hzEbomService.addHzEbomRecord(reqDTO);
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
        WriteResultRespDTO respDTO= hzEbomService.updateHzEbomRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
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
            toJSONResponse(Result.build(false,"非法参数！"), response);
            return;
        }
        boolean b = PrivilegeUtil.writePrivilege();
        if(!b){//管理员权限
            toJSONResponse(Result.build(false,"您没有权限进行当前操作！"), response);
            return;
        }
        WriteResultRespDTO respDTO = hzEbomService.deleteHzEbomRecordById(reqDTO);
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
     * 直接生效EBOM 临时用
     * @param
     * @param
     * @param response
     */
    @RequestMapping(value = "vaild/direct",method = RequestMethod.POST)
    public void validDirect(String puids, HttpServletResponse response){
        if(puids==null){
            toJSONResponse(Result.build(false,"非法参数！"), response);
            return;
        }
        boolean b = PrivilegeUtil.writePrivilege();
        if(!b){//管理员权限
            toJSONResponse(Result.build(false,"您没有权限进行当前操作！"), response);
            return;
        }

        String[] ps = puids.split(",");
        List<HzBomLineRecord> list = new ArrayList<>();
        for(String s:ps){
            HzBomLineRecord record = new HzBomLineRecord();
            record.setTableName("HZ_BOM_LINE_RECORD");
            record.setPuid(s);
            record.setStatus(1);
            list.add(record);
        }
        int i = hzBomLineRecordDao.updateBatch(list);
        if(i>0){
            toJSONResponse(Result.build(true,"操作成功！"), response);
        }else {
            toJSONResponse(Result.build(false,"操作失败！"), response);

        }
    }
}