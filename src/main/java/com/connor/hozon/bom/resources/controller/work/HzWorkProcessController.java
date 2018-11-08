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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.work.HzWorkProcedure;

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

    /**
     * 跳转到工艺路线修改页面
     * @return
     */
    @RequestMapping(value = "updateWorkProcess2",method = RequestMethod.GET)
    public String updateWorkProcessToPage(String materielId, String projectId,String procedureDesc, Model model){
        HzWorkProcessRespDTO respDTO = hzWorkProcessService.findHzWorkProcess2(materielId, projectId, procedureDesc);
        model.addAttribute("data",respDTO);
        return  "bomManage/mbom/routingData/updateRoutingData2";
    }

    /**
     * 编辑一条记录
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "update2",method = RequestMethod.POST)
    public void updateHzWorkProcessToDB2(@RequestBody UpdateHzProcessReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzWorkProcessService.updateHzWorkProcess2(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 删除一条记录
     * @param datas
     * @param response
     */
    @RequestMapping(value = "delete2",method = RequestMethod.POST)
    public void deleteHzWorkProcess(@RequestBody Map<String, List<String>> datas,HttpServletResponse response){
        WriteResultRespDTO respDTO =  hzWorkProcessService.deleteHzWorkProcesses(datas);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 分页获取工艺数据 记录
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "record/page2", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHzWorkProcessRecordForPage2(HzWorkProcessByPageQuery query) {
        hzWorkProcessService.initProcess(query.getProjectId());
        HzWorkProcessByPageQuery ebomByPageQuery = query;
        ebomByPageQuery.setPageSize(0);
        try{
            ebomByPageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        }catch (Exception e){

        }
        Page<HzWorkProcessRespDTO> page = hzWorkProcessService.findHzWorkProcessForPage2(query);
        if (page == null) {
            return new HashMap<>();
        }
        List<HzWorkProcessRespDTO> list = page.getResult();
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        list.forEach(dto -> {
            Map<String, Object> _res = new HashMap<>();
            _res.put("no", dto.getNo());
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
     * 跳转到修改四大工艺的修改页面
     * @return
     */
    @RequestMapping(value = "/four2", method = RequestMethod.GET)
    public String updateWorkProcessFourToPage(String puids,String projectId,String type, String processDescs, Model model){
        List<String> puidList = new ArrayList<String>();
        List<HzWorkProcedure> hzWorkProcedureList = new ArrayList<HzWorkProcedure>();
        String[] puidsArr = puids.split(",");
        String[] processDescArr = processDescs.split(",");
        for(int i=0;i<puidsArr.length;i++){
            for(int j=i+1;j<puidsArr.length;j++){
                if(puidsArr[i].equals(puidsArr[j])){
                    model.addAttribute("error","请不要选择重复物料");
                }
            }
        }
        for(int i=0;i<puidsArr.length;i++){
            puidList.add(puidsArr[i]);
            HzWorkProcedure hzWorkProcedure = new HzWorkProcedure();
            hzWorkProcedure.setMaterielId(puidsArr[i]);
            hzWorkProcedure.setpProcedureDesc("null".equals(processDescArr[i])?null:processDescArr[i]);
            hzWorkProcedureList.add(hzWorkProcedure);
        }
        List<String> processDescHeadList = hzWorkProcessService.queryProcessDesc(puidList);
        List<HzWorkProcedure> hzWorkProcedures = hzWorkProcessService.queryProcedures(hzWorkProcedureList);
        model.addAttribute("hzWorkProcedures",hzWorkProcedures);
        model.addAttribute("data",puids);
        model.addAttribute("type",type);
        for(String processDescHead : processDescHeadList){
            if("冲压".equals(processDescHead)){
                model.addAttribute("cy",processDescHead);
            }else if("焊装".equals(processDescHead)){
                model.addAttribute("hz",processDescHead);
            }else if("涂装".equals(processDescHead)){
                model.addAttribute("tz",processDescHead);
            }else if("总装".equals(processDescHead)){
                model.addAttribute("zz",processDescHead);
            }
        }
        return  "bomManage/mbom/routingData/updateFourProcess";
    }


    @RequestMapping(value = "add/four",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateWorkProcessFour(@RequestBody Map<String,Object> data) throws  Exception{
        JSONObject result = new JSONObject();
        String puids = (String)data.get("puids");
        String projectId = (String)data.get("projectId");
        List<String> routings = (List<String>)data.get("routing");
        String hzWorkProceduresJson = (String) data.get("hzWorkProceduresJson");

        JSONArray json = JSONArray.fromObject(hzWorkProceduresJson);
        List<HzWorkProcedure> hzWorkProcedureList= (List<HzWorkProcedure>)JSONArray.toCollection(json, HzWorkProcedure.class);

        String[] puidArr = puids.split(",");

        List<HzWorkProcedure> hzWorkProceduresAdd = new ArrayList<HzWorkProcedure>();
        for(int i=0;i<puidArr.length;i++){
            for(String routing : routings){
                HzWorkProcedure hzWorkProcedure1 = hzWorkProcedureList.get(i).clone();
                String puid1 = UUID.randomUUID().toString();
                hzWorkProcedure1.setPuid(puid1);
                hzWorkProcedure1.setMaterielId(puidArr[i]);
                hzWorkProcedure1.setProjectId(projectId);
                hzWorkProcedure1.setpFactoryId("1001");
                hzWorkProcedure1.setpProcedureDesc(routing+"-时间维度");

                HzWorkProcedure hzWorkProcedure2 = hzWorkProcedureList.get(i).clone();
                String puid2 = UUID.randomUUID().toString();
                hzWorkProcedure2.setPuid(puid2);
                hzWorkProcedure2.setMaterielId(puidArr[i]);
                hzWorkProcedure2.setProjectId(projectId);
                hzWorkProcedure2.setpFactoryId("1001");
                hzWorkProcedure2.setpProcedureDesc(routing+"-数量维度");
                hzWorkProceduresAdd.add(hzWorkProcedure1);
                hzWorkProceduresAdd.add(hzWorkProcedure2);
            }
        }
        List<HzWorkProcedure> hzWorkProceduresDel = new ArrayList<HzWorkProcedure>();
        for(String puid : puidArr){
            HzWorkProcedure hzWorkProcedure = new HzWorkProcedure();
            hzWorkProcedure.setMaterielId(puid);
            hzWorkProcedure.setProjectId(projectId);
            hzWorkProceduresDel.add(hzWorkProcedure);
        }
        //如有初始工艺路线，将其删除
        hzWorkProcessService.deleteHzWorkProcessByMaterielIds(hzWorkProceduresDel);
        //添加新生工艺路线
        hzWorkProcessService.insertHzWorkProcedures(hzWorkProceduresAdd);

        result.put("success",true);
        return result;
    }
}
