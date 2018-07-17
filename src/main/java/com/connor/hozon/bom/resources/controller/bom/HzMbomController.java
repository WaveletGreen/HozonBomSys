package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.AddMbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.DeleteHzMbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateMbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzSuperMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzVehicleModelRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMbomByPageQuery;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
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
 * \* Date: 2018/6/6
 * \* Time: 12:58
 * \
 */

@Controller
@RequestMapping(value = "/mbom")
public class HzMbomController extends BaseController {

    @Autowired
    private HzMbomService hzMbomService;

    /**
     * 超级MBOM标题
     * @param projectId
     * @param response
     */
    @RequestMapping(value = "superMbomTitle", method = RequestMethod.GET)
    public void getHzSuperMBomTitle(String projectId,HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No", "序号");
        tableTitle.put("level", "层级");
        tableTitle.put("pBomOfWhichDept", "专业");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("object_name", "名称");
        tableTitle.put("objectName", "车型名称");
        tableTitle.put("objectDesc", "车型描述");
        tableTitle.put("cfg0Desc", "配置描述");
        tableTitle.put("cfg0FamilyName", "选项族名称");
        tableTitle.put("cfg0FamilyDesc", "选项族描述");
        tableTitle.put("cfg0ModelBasicDetail", "基本信息");
        tableTitle.put("sparePart", "备件");
        tableTitle.put("sparePartNum", "备件编号");
        tableTitle.put("processRoute", "工艺路线");
        tableTitle.put("laborHour", "人工工时");
        tableTitle.put("rhythm", "节拍");
        tableTitle.put("solderJoint", "焊点");
        tableTitle.put("machineMaterial", "机物料");
        tableTitle.put("standardPart", "标准件");
        tableTitle.put("tools", "工具");
        tableTitle.put("wasterProduct", "废品");
        tableTitle.put("change", "变更");
        tableTitle.put("changeNum", "变更号");
        List<HzVehicleModelRespDTO> respDTOS = hzMbomService.getHzVehicleModelByProjectId(projectId);
        JSONObject object = new JSONObject();
        object.put("model",respDTOS);
        object.put("data",tableTitle);
        writeAjaxJSONResponse(object, response);
    }


    /**
     * 分页获取超级MBOM 记录
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "super/record", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSuperMbomRecord(HzMbomByPageQuery query) {
        Page<HzSuperMbomRecordRespDTO> page = hzMbomService.getHzSuperMbomPage(query);
        if (page == null) {
            return new HashMap<>();
        }
        List<HzVehicleModelRespDTO> respDTOS = hzMbomService.getHzVehicleModelByProjectId(query.getProjectId());
        List<HzSuperMbomRecordRespDTO> list = page.getResult();
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        list.forEach(dto -> {
            Map<String, Object> _res = new HashMap<>();
            _res.put("eBomPuid", dto.geteBomPuid());
            _res.put("No", dto.getNo());
            _res.put("level", dto.getLevel());
            _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
            _res.put("lineId", dto.getLineId());
            _res.put("object_name", dto.getObject_name());
            _res.put("sparePart", dto.getSparePart());
            _res.put("sparePartNum", dto.getSparePartNum());
            _res.put("processRoute", dto.getProcessRoute());
            _res.put("laborHour", dto.getLaborHour());
            _res.put("rhythm", dto.getRhythm());
            _res.put("solderJoint", dto.getSolderJoint());
            _res.put("machineMaterial", dto.getMachineMaterial());
            _res.put("standardPart", dto.getStandardPart());
            _res.put("tools", dto.getTools());
            _res.put("wasterProduct", dto.getWasterProduct());
            _res.put("change", dto.getChange());
            _res.put("changeNum", dto.getChangeNum());
            _res.put("cfg0Desc", dto.getCfg0Desc());
            _res.put("cfg0FamilyName", dto.getCfg0FamilyName());
            _res.put("cfg0FamilyDesc", dto.getCfg0FamilyDesc());
            _res.put("objectName", dto.getObjectName());
            _res.put("objectDesc", dto.getObjectDesc());
            _res.put("cfg0ModelBasicDetail",dto.getCfg0ModelBasicDetail());
            _list.add(_res);
        });
        ret.put("totalCount", page.getTotalCount());
        ret.put("result", _list);
        ret.put("model",respDTOS);
        return ret;
    }



    /**
     * MBOM管理标题
     *
     * @param response
     */
    @RequestMapping(value = "manage/title", method = RequestMethod.GET)
    public void getPbomLineTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No", "序号");
        tableTitle.put("level", "层级");
        tableTitle.put("pBomOfWhichDept", "专业");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("pBomLinePartName", "名称");
        tableTitle.put("sparePart", "备件");
        tableTitle.put("sparePartNum", "备件编号");
        tableTitle.put("processRoute", "工艺路线");
        tableTitle.put("laborHour", "人工工时");
        tableTitle.put("rhythm", "节拍");
        tableTitle.put("solderJoint", "焊点");
        tableTitle.put("machineMaterial", "机物料");
        tableTitle.put("standardPart", "标准件");
        tableTitle.put("tools", "工具");
        tableTitle.put("wasterProduct", "废品");
        tableTitle.put("change", "变更");
        tableTitle.put("changeNum", "变更号");
        tableTitle.put("pFactoryCode", "工厂代码");
        tableTitle.put("pStockLocation", "发货料库存地点");
        tableTitle.put("pBomType", "BOM类型");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }


    /**
     * 分页获取MBOM 记录
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "record", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMbomLineRecord(HzMbomByPageQuery query) {
        HzMbomByPageQuery ebomByPageQuery = query;
        ebomByPageQuery.setPageSize(0);
        try{
            ebomByPageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        }catch (Exception e){

        }
        Page<HzMbomRecordRespDTO> page = hzMbomService.fingHzMbomForPage(query);
        if (page == null) {
            return new HashMap<>();
        }
        List<HzMbomRecordRespDTO> list = page.getResult();
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        list.forEach(dto -> {
            Map<String, Object> _res = new HashMap<>();
            _res.put("eBomPuid", dto.geteBomPuid());
            _res.put("puid", dto.getPuid());
            _res.put("No", dto.getNo());
            _res.put("level", dto.getLevel());
            _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
            _res.put("lineId", dto.getLineId());
            _res.put("pBomLinePartName", dto.getpBomLinePartName());
            _res.put("sparePart", dto.getSparePart());
            _res.put("sparePartNum", dto.getSparePartNum());
            _res.put("processRoute", dto.getProcessRoute());
            _res.put("laborHour", dto.getLaborHour());
            _res.put("rhythm", dto.getRhythm());
            _res.put("solderJoint", dto.getSolderJoint());
            _res.put("machineMaterial", dto.getMachineMaterial());
            _res.put("standardPart", dto.getStandardPart());
            _res.put("tools", dto.getTools());
            _res.put("wasterProduct", dto.getWasterProduct());
            _res.put("change", dto.getChange());
            _res.put("changeNum", dto.getChangeNum());
            _res.put("pFactoryCode", dto.getpFactoryCode());
            _res.put("pStockLocation",dto.getpStockLocation());
            _res.put("pBomType", dto.getpBomType());
            _list.add(_res);
        });
        ret.put("totalCount", page.getTotalCount());
        ret.put("result", _list);
        return ret;
    }

    /**
     * 跳转到MBOM管理的添加页面
     * @return
     */
    @RequestMapping(value = "addMBom", method = RequestMethod.GET)
    public String addMBomToPage(String projectId, String eBomPuid, Model model) {
        HzMbomRecordRespDTO respDTO = hzMbomService.findHzMbomByPuid(projectId,eBomPuid);
        if(respDTO == null){
            return "";
        }
        HzMbomRecordRespDTO hzMbomRecordRespDTO = new HzMbomRecordRespDTO();
        hzMbomRecordRespDTO.seteBomPuid(respDTO.geteBomPuid());
        hzMbomRecordRespDTO.setObject_name(respDTO.getpBomLinePartName());
        hzMbomRecordRespDTO.setLevel(respDTO.getLevel());
        hzMbomRecordRespDTO.setLineId(respDTO.getLineId());
        hzMbomRecordRespDTO.setpBomOfWhichDept(respDTO.getpBomOfWhichDept());
        model.addAttribute("data",hzMbomRecordRespDTO);
        return"bomManage/mbom/mbomMaintenance/addMbomMaintenance";
    }

    /**
     * 跳转到MBOM管理的修改页面
     * @param projectId
     * @param eBomPuid
     * @param model
     * @return
     */
    @RequestMapping(value = "updateMBom", method = RequestMethod.GET)
    public String updateMbomToPage(String projectId,String eBomPuid,Model model) {
        HzMbomRecordRespDTO respDTO =hzMbomService.findHzMbomByPuid(projectId,eBomPuid);
        if(respDTO== null){
            return "";
        }
        model.addAttribute("data",respDTO);
        return"bomManage/mbom/mbomMaintenance/updateMbomMaintenance";
    }

    /**
     * 插入一条记录
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public void addMbomToDB(@RequestBody AddMbomReqDTO reqDTO,HttpServletResponse response){
        OperateResultMessageRespDTO respDTO = hzMbomService.insertMbomRecord(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 编辑一条记录
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void updateMbomToDB(@RequestBody UpdateMbomReqDTO reqDTO,HttpServletResponse response){
        OperateResultMessageRespDTO respDTO = hzMbomService.updateMbomRecord(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 批量删除记录
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void deleteMbom(@RequestBody DeleteHzMbomReqDTO reqDTO, HttpServletResponse response){
       OperateResultMessageRespDTO respDTO =  hzMbomService.deleteMbomRecord(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }
}


