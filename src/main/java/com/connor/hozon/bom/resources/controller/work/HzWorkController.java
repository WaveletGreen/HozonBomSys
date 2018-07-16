package com.connor.hozon.bom.resources.controller.work;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.AddWorkCenterReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateWorkCenterReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzWorkCenterRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzWorkByPageQuery;
import com.connor.hozon.bom.resources.service.work.HzWorkService;
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
 * \* Date: 2018/7/2
 * \* Time: 16:49
 * \
 */
@Controller
@RequestMapping(value = "work")
public class HzWorkController extends BaseController {
    @Autowired
    private HzWorkService hzWorkService;

    /**
     * 获取标题
     * @param projectId
     * @param response
     */
    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void getWorkTitel(String projectId,HttpServletResponse response){
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No","序号");
        tableTitle.put("factoryCode","工厂代码");
        tableTitle.put("pWorkCode","工作中心代码");
        tableTitle.put("pWorkDesc","工作中心描述");
        tableTitle.put("pWorkType","工作中心类别");
        tableTitle.put("pPurpose","用途");
        tableTitle.put("pStandardCode","标准值码");
        tableTitle.put("pControlCode","控制码");
        tableTitle.put("pDirectLabor","直接人工工时");
        tableTitle.put("pIndirectLabor","间接人工工时");
        tableTitle.put("pMachineLabor","机器工时");
        tableTitle.put("pBurn","燃动");
        tableTitle.put("pMachineMaterial","机物料");
        tableTitle.put("pOtherCost","其他费用");
        tableTitle.put("pProcessExression","加工公式");
        tableTitle.put("pAbilityType","能力类别");
        tableTitle.put("pStartTime","开始时间");
        tableTitle.put("pEndTime","结束时间");
        tableTitle.put("pRestTime","休息时间");
        tableTitle.put("pAbilityCount","能力数量");
        tableTitle.put("pDispatchExpression","调度公式");
        tableTitle.put("pCostCenter","成本中心");
        tableTitle.put("pTaskType1","作业类型1");
        tableTitle.put("pTaskType2","作业类型2");
        tableTitle.put("pTaskType3","作业类型3");
        tableTitle.put("pTaskType4","作业类型4");
        tableTitle.put("pTaskType5","作业类型5");
        tableTitle.put("pTaskType6","作业类型6");
        tableTitle.put("pExperssion1","公式1");
        tableTitle.put("pExperssion2","公式2");
        tableTitle.put("pExperssion3","公式3");
        tableTitle.put("pExperssion4","公式4");
        tableTitle.put("pExperssion5","公式5");
        tableTitle.put("pExperssion6","公式6");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }

    /**
     * 分页获取数据
     * @param query
     * @return
     */
    @RequestMapping(value = "record",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getWorkPageRecord(HzWorkByPageQuery query){
        HzWorkByPageQuery ebomByPageQuery = query;
        ebomByPageQuery.setPageSize(0);
        try{
            ebomByPageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        }catch (Exception e){

        }
        Page<HzWorkCenterRespDTO> page = hzWorkService.findHzWorkPage(query);
        if (page==null){
            return new HashMap<>();
        }
        List<HzWorkCenterRespDTO> list = page.getResult();
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        list.forEach(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("puid",dto.getPuid());
            map.put("No",dto.getNo());
            map.put("factoryCode",dto.getFactoryCode());
            map.put("pWorkCode",dto.getpWorkCode());
            map.put("pWorkDesc",dto.getpWorkDesc());
            map.put("pWorkType",dto.getpWorkType());
            map.put("pPurpose",dto.getpPurpose());
            map.put("pStandardCode",dto.getpStandardCode());
            map.put("pControlCode",dto.getpControlCode());
            map.put("pDirectLabor",dto.getpDirectLabor());
            map.put("pIndirectLabor",dto.getpIndirectLabor());
            map.put("pMachineLabor",dto.getpMachineLabor());
            map.put("pBurn",dto.getpBurn());
            map.put("pMachineMaterial",dto.getpMachineMaterial());
            map.put("pOtherCost",dto.getpOtherCost());
            map.put("pProcessExression",dto.getpProcessExression());
            map.put("pAbilityType",dto.getpAbilityType());
            map.put("pStartTime",dto.getpStartTime());
            map.put("pEndTime",dto.getpEndTime());
            map.put("pRestTime",dto.getpRestTime());
            map.put("pAbilityCount",dto.getpAbilityCount());
            map.put("pDispatchExpression",dto.getpDispatchExpression());
            map.put("pCostCenter",dto.getpCostCenter());
            map.put("pTaskType1",dto.getpTaskType1());
            map.put("pTaskType2",dto.getpTaskType2());
            map.put("pTaskType3",dto.getpTaskType3());
            map.put("pTaskType4",dto.getpTaskType4());
            map.put("pTaskType5",dto.getpTaskType5());
            map.put("pTaskType6",dto.getpTaskType6());
            map.put("pExperssion1",dto.getpExperssion1());
            map.put("pExperssion2",dto.getpExperssion2());
            map.put("pExperssion3",dto.getpExperssion3());
            map.put("pExperssion4",dto.getpExperssion4());
            map.put("pExperssion5",dto.getpExperssion5());
            map.put("pExperssion6",dto.getpExperssion6());
            _list.add(map);
        });
        ret.put("totalCount", page.getTotalCount());
        ret.put("result", _list);
        return ret;
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping(value = "addWork",method = RequestMethod.GET)
    public String addWorkToPage(){
        return "bomManage/mbom/processCenter/addProcessCenter";
    }

    /**
     * 跳转到修改页面
     * @param projectId
     * @param puid
     * @param model
     * @return
     */
    @RequestMapping(value = "updateWork",method = RequestMethod.GET)
    public String updateWrokToPage(String projectId,String puid,Model model){
        HzWorkCenterRespDTO respDTO = hzWorkService.findHzWorkByPuid(projectId,puid);
        if(respDTO== null){
            return "";
        }
        model.addAttribute("data",respDTO);
        return "bomManage/mbom/processCenter/updateProcessCenter";
    }
    /**
     * 添加一条数据
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public void addWorkToDB(@RequestBody AddWorkCenterReqDTO reqDTO, HttpServletResponse response){
        OperateResultMessageRespDTO respDTO = hzWorkService.insertHzWorkRecord(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 编辑一条数据
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void updateWorkToDB(@RequestBody UpdateWorkCenterReqDTO reqDTO, HttpServletResponse response){
        OperateResultMessageRespDTO respDTO = hzWorkService.updateHzWorkRecord(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 删除一条数据
     * @param puid
     * @param response
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void deleteWork(String puid,HttpServletResponse response){
        OperateResultMessageRespDTO respDTO = hzWorkService.deleteHzWorkRecord(puid);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }
}
