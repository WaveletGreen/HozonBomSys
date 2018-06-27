package com.connor.hozon.bom.resources.controller.bom;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.AddMbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateMbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
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

    @RequestMapping(value = "superBomTitel", method = RequestMethod.GET)
    public void getMbomSuperBomGetTitel(HttpServletResponse response) {
        LinkedHashMap<String, String> titel = new LinkedHashMap<>();
        titel.put("sparePart", "备件");
        titel.put("sparePartNum", "备件编号");
        titel.put("processRoute", "工艺路线");
        titel.put("laborHour", "人工工时");
        titel.put("rhythm", "节拍");
        titel.put("solderJoint", "焊点");
        titel.put("machineMaterial", "机物料");
        titel.put("standardPart", "标准件");
        titel.put("tools", "工具");
        titel.put("wasterProduct", "废品");
        writeAjaxJSONResponse(ResultMessageBuilder.build(titel), response);
    }


    @RequestMapping(value = "workCenterTitel", method = RequestMethod.GET)
    public void getMbomWorkCenterTitel(HttpServletResponse response) {
        LinkedHashMap<String, String> titel = new LinkedHashMap<>();
        titel.put("factory", "工厂");
        titel.put("workHours", "工作工时");
        titel.put("jobCentreCategory", "工作中心类别");
        titel.put("describe", "描述");
        titel.put("use", "用途");
        titel.put("standardCode", "标准值码");
        titel.put("controlCode", "控制码");
        titel.put("directLabor", "直接人工");
        titel.put("indirectLabor", "间接人工");
        titel.put("machineHour", "机器工时 ");
        titel.put("combustion", "燃动");
        titel.put("machineMaterial", "机物料");
        titel.put("otherCosts", "其它费用");
        titel.put("processingFormula", "加工公式");
        titel.put("powerType", "能力类别");
        titel.put("startTime", "开始时间");
        titel.put("endTime", "结束时间");
        writeAjaxJSONResponse(ResultMessageBuilder.build(titel), response);
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
        tableTitle.put("object_name", "名称");
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
        hzMbomRecordRespDTO.setObject_name(respDTO.getObject_name());
        hzMbomRecordRespDTO.setLevel(respDTO.getLevel());
        hzMbomRecordRespDTO.setLineId(respDTO.getLineId());
        hzMbomRecordRespDTO.setpBomOfWhichDept(respDTO.getpBomOfWhichDept());
        model.addAttribute("data",hzMbomRecordRespDTO);
        return"bomManage/mbom/mbomMaintenance/addMbomMaintenance";
    }

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
     * 删除一条记录
     * @param eBomPuid
     * @param response
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void deleteMbom(String eBomPuid,HttpServletResponse response){
       OperateResultMessageRespDTO respDTO =  hzMbomService.deleteMbomRecord(eBomPuid);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }
}
