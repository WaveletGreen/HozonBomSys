package com.connor.hozon.bom.resources.controller.work;

import com.connor.hozon.bom.resources.controller.BaseController;

import com.connor.hozon.bom.resources.domain.dto.request.AddHzProcessReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.ApplyMbomDataTOHzMaterielReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzProcessReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzWorkProcessRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzWorkProcessByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.work.HzWorkProcessService;
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
 * @Date: 2018/7/5
 * @Description:
 */
@Controller
@RequestMapping(value = "work/process")
public class HzWorkProcessController extends BaseController {

    @Autowired
    private HzWorkProcessService hzWorkProcessService;
    /**
     * 工艺路线的标题
     * @param response
     */
    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void HzWorkProcessTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> title = new LinkedHashMap<>();
        title.put("No", "序号");
        title.put("pMaterielCode", "物料");//物料
        title.put("pMaterielDesc","物料名称");//物料名称
        title.put("factoryCode", "工厂");//工厂
        title.put("purpose", "用途");//用途
        title.put("state", "状态");//状态
        title.put("pProcedureCode", "工序号");//工序号
        title.put("pWorkCode", "工作中心");//工作中心
        title.put("pWorkDesc", "工作中心描述");//工作中心描述
        title.put("controlCode", "控制码");//控制码
        title.put("pProcedureDesc", "工序描述");//工序描述
        title.put("pCount", "基本数量 ");//基本数量
        title.put("pDirectLabor", "直接人工/机物料消耗");//直接人工时间
        title.put("pIndirectLabor", "间接人工/标准件消耗");//间接人工时间
        title.put("pMachineLabor", "折旧/工具消耗");//机器时间
        title.put("pBurn", "燃动费/废品损失");//燃动能
        title.put("pMachineMaterialLabor", "辅助人工/设备维修");//机物料消耗
        title.put("pOtherCost", "辅助折旧/辅助其他费用");//其他费用
        toJSONResponse(Result.build(title), response);
    }




    /**
     * 跳转到工艺路线添加页面
     * @return
     */
    @RequestMapping(value = "addWorkProcess",method = RequestMethod.GET)
    public String addWorkProcessToPage(String materielId, String projectId, Model model){
        HzWorkProcessRespDTO respDTO = hzWorkProcessService.findHzWorkProcess(materielId,projectId);
        model.addAttribute("data",respDTO);
        return  "bomManage/mbom/routingData/addRoutingData";
    }
    /**
     * 跳转到工艺路线修改页面
     * @return
     */
    @RequestMapping(value = "updateWorkProcess",method = RequestMethod.GET)
    public String updateWorkProcessToPage(String materielId, String projectId, Model model){
        HzWorkProcessRespDTO respDTO = hzWorkProcessService.findHzWorkProcess(materielId,projectId);
        model.addAttribute("data",respDTO);
      return  "bomManage/mbom/routingData/updateRoutingData";
    }


    /**
     * 插入一条记录
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public void addHzWorkProcessToDB(@RequestBody AddHzProcessReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzWorkProcessService.addHzWorkProcess(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 编辑一条记录
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void updateHzWorkProcessToDB(@RequestBody UpdateHzProcessReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzWorkProcessService.updateHzWorkProcess(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 删除一条记录
     * @param materielId
     * @param response
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void deleteHzWorkProcess(String materielId,HttpServletResponse response){
        WriteResultRespDTO respDTO =  hzWorkProcessService.deleteHzWorkProcess(materielId);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 分页获取工艺数据 记录
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "record/page", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHzWorkProcessRecordForPage(HzWorkProcessByPageQuery query) {
        HzWorkProcessByPageQuery ebomByPageQuery = query;
        ebomByPageQuery.setPageSize(0);
        try{
            ebomByPageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        }catch (Exception e){

        }
        Page<HzWorkProcessRespDTO> page = hzWorkProcessService.findHzWorkProcessForPage(query);
        if (page == null) {
            return new HashMap<>();
        }
        List<HzWorkProcessRespDTO> list = page.getResult();
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        list.forEach(dto -> {
            Map<String, Object> _res = new HashMap<>();
            _res.put("No", dto.getNo());
            _res.put("materielId", dto.getMaterielId());
            _res.put("pMaterielCode", dto.getpMaterielCode());
            _res.put("pMaterielDesc", dto.getpMaterielDesc());
            _res.put("factoryCode", dto.getFactoryCode());
            _res.put("purpose", dto.getPurpose());
            _res.put("state", dto.getState());
            _res.put("pProcedureCode", dto.getpProcedureCode());
            _res.put("pWorkCode", dto.getpWorkCode());
            _res.put("pWorkDesc", dto.getpWorkDesc());
            _res.put("controlCode", dto.getControlCode());
            _res.put("pProcedureDesc", dto.getpProcedureDesc());
            _res.put("pCount", dto.getpCount());
            _res.put("pDirectLabor", dto.getpDirectLabor());
            _res.put("pIndirectLabor", dto.getpIndirectLabor());
            _res.put("pMachineLabor", dto.getpMachineLabor());
            _res.put("pBurn", dto.getpBurn());
            _res.put("pMachineMaterialLabor", dto.getpMachineMaterialLabor());
            _res.put("pOtherCost", dto.getpOtherCost());

            _list.add(_res);
        });
        ret.put("totalCount", page.getTotalCount());
        ret.put("result", _list);
        return ret;
    }


    /**
     * 跳转到MBom选取数据
     * @return
     */
    @RequestMapping(value = "getMBom" ,method = RequestMethod.GET)
    public String getMBomToPage(){
        return "bomManage/mbom/mbomMaintenance/mbomMaintenance";
    }

    /**
     * 一键同步MBom数据到物料主数据
     */
    @RequestMapping(value = "apply/oneKey",method = RequestMethod.POST)
    public void applyMbomDataToHzMaterielOneKey(@RequestBody List<HzMbomRecordRespDTO> mbomRecordRespDTOS, String projectId, Integer type, HttpServletResponse response){
        ApplyMbomDataTOHzMaterielReqDTO reqDTO = new ApplyMbomDataTOHzMaterielReqDTO();
        reqDTO.setMbomRecordRespDTOS(mbomRecordRespDTOS);
        reqDTO.setProjectId(projectId);
        reqDTO.setType(type);
        WriteResultRespDTO respDTO =  hzWorkProcessService.applyMbomDataToHzMaterielOneKey(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }
    /**
     * 跳转到修改四大工艺的修改页面
     * @return
     */
    @RequestMapping(value = "four",method = RequestMethod.GET)
    public String updateWorkProcessFourToPage(String puids,String projectId,Model model){
        model.addAttribute("data",puids);
        return  "bomManage/mbom/routingData/updateFourProcess";
    }
}
