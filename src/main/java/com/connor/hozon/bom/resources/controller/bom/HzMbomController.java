package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.AddMbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzMbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateMbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSuperMbomRecordRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzVehicleModelRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzMbomByIdQuery;
import com.connor.hozon.bom.resources.domain.query.HzMbomByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
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
     * MBOM管理标题
     *
     * @param response
     */
    @RequestMapping(value = "manage/title", method = RequestMethod.GET)
    public void getPbomLineTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No", "序号");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("pBomLinePartName", "名称");
        tableTitle.put("level", "层级");
        tableTitle.put("pBomOfWhichDept", "专业");
        tableTitle.put("lineNo","查找编号");
        tableTitle.put("pLouaFlag","LOU/LOA");
        tableTitle.put("pBomLinePartClass", "零件分类");
        tableTitle.put("pBomLinePartResource", "零部件来源");
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
        Page<HzMbomRecordRespDTO> page = hzMbomService.findHzMbomForPage(query);
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
            _res.put("lineNo",dto.getLineNo());
            _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
            _res.put("lineId", dto.getLineId());
            _res.put("pBomLinePartName", dto.getpBomLinePartName());
            _res.put("pBomLinePartClass", dto.getpBomLinePartClass());
            _res.put("pBomLinePartResource", dto.getpBomLinePartResource());
            _res.put("sparePart", dto.getSparePart());
            _res.put("sparePartNum", dto.getSparePartNum());
            _res.put("processRoute", dto.getProcessRoute());
            _res.put("laborHour", dto.getLaborHour());
            _res.put("rhythm", dto.getRhythm());
            _res.put("pLouaFlag",dto.getpLouaFlag());
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
            _res.put("status",dto.getStatus());
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
        HzMbomByIdQuery query = new HzMbomByIdQuery();
        query.setProjectId(projectId);
        query.setPuid(eBomPuid);
        HzMbomRecordRespDTO respDTO = hzMbomService.findHzMbomByPuid(query);
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
        HzMbomByIdQuery query = new HzMbomByIdQuery();
        query.setProjectId(projectId);
        query.setPuid(eBomPuid);
        HzMbomRecordRespDTO respDTO =hzMbomService.findHzMbomByPuid(query);
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
    public void addMbomToDB(@RequestBody AddMbomReqDTO reqDTO, HttpServletResponse response){
        OperateResultMessageRespDTO respDTO = hzMbomService.insertMbomRecord(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 编辑一条记录
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void updateMbomToDB(@RequestBody UpdateMbomReqDTO reqDTO, HttpServletResponse response){
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
    @RequestMapping(value = "refresh",method = RequestMethod.POST)
    public void refreshMbom(String projectId,HttpServletResponse response){
        OperateResultMessageRespDTO resultMessageRespDTO = hzMbomService.refreshHzMbom(projectId);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(resultMessageRespDTO),resultMessageRespDTO.getErrMsg()),response);
    }

    /**
     * 跳转到白车身生产的修改页面
     * @return
     */
    @RequestMapping(value = "updateProduction", method = RequestMethod.GET)
    public String updateWhiteBodyProduction(String projectId,String eBomPuid,Integer type,Model model) {
        HzMbomByIdQuery query = new HzMbomByIdQuery();
        query.setProjectId(projectId);
        query.setType(type);
        query.setPuid(eBomPuid);
        HzMbomRecordRespDTO respDTO =hzMbomService.findHzMbomByPuid(query);
        if(respDTO== null){
            return "";
        }
        model.addAttribute("data",respDTO);
        return"bomManage/mbom/mbomMaintenance/updateProduction";
    }

    /**
     * 跳转到白车身财务的修改页面
     * @return
     */
    @RequestMapping(value = "updateFinancial", method = RequestMethod.GET)
    public String updateWhiteBodyFinancial(String projectId,String eBomPuid,Integer type,Model model) {
        HzMbomByIdQuery query = new HzMbomByIdQuery();
        query.setProjectId(projectId);
        query.setType(type);
        query.setPuid(eBomPuid);
        HzMbomRecordRespDTO respDTO =hzMbomService.findHzMbomByPuid(query);
        if(respDTO== null){
            return "";
        }
        model.addAttribute("data",respDTO);
        return"bomManage/mbom/mbomMaintenance/updateFinancial";
    }
}


