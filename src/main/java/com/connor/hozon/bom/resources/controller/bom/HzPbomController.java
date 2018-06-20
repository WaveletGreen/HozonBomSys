package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineMaintainRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by haozt on 2018/5/24
 */
@Controller
@RequestMapping(value = "/pbom")
public class HzPbomController extends BaseController {

    @Autowired
    private HzPbomService hzPbomService;
    @Autowired
    HzBomDataDao hzBomDataDao;

    /**
     * 获取PBOM 在线维护信息详情
     *
     * @param response
     */
    @RequestMapping(value = "getMaintain/detail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPbomLineMaintainDetail(HttpServletResponse response) {
        List<HzPbomLineMaintainRespDTO> responseDTOList = hzPbomService.getHzPbomMaintainRecord();
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, String>> _list = new ArrayList<>();
        responseDTOList.forEach(dto -> {
            Map<String, String> _res = new HashMap<>();
            _res.put("pBomPuid", dto.getpBomPuid());
            _res.put("level", dto.getLevel());
            _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
            _res.put("lineId", dto.getLineId());
            _res.put("bomDigifaxId", dto.getBomDigifaxId());
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
        ret.put("totalCount", responseDTOList.size());
        ret.put("result", _list);
        return ret;
//        if(ListUtil.isEmpty(responseDTOList)){
//            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无符合数据！"),response);
//        }
//        return responseDTOList;
//        writeAjaxJSONResponse(ResultMessageBuilder.build(responseDTOList),response);
    }

    /**
     * 获取PBOM维护的表头数据
     *
     * @param response
     */
    @RequestMapping(value = "maintain/title")
    public void getPbomMaintainTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("level", "层级");
        tableTitle.put("pBomOfWhichDept", "专业");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("bomDigifaxId", "名称");//这个字段暂时是一个替代品，后续要改
        tableTitle.put("sparePart", "备件");
        tableTitle.put("sparePartNum", "备件编号");
        tableTitle.put("processRoute", "工艺路线");
        tableTitle.put("laborHour", "人工工时");
        tableTitle.put("rhythm", "节拍");
        tableTitle.put("solderJoint", "焊点");
        tableTitle.put("machineMaterial", "机物料");
        tableTitle.put("standardPart", "标准件");
        tableTitle.put("tools", "工艺");
        tableTitle.put("wasterProduct", "废品");
        tableTitle.put("change", "变更");
        tableTitle.put("changeNum", "变更号");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }


    @RequestMapping(value = "insert/list/maintain", method = RequestMethod.GET)
    public String insertPbomLineMaintainRecordList() {
        return "bomManage/pbomManage/pbomMaintenance/addPbomMaintenance";
    }


    /**
     * 新增
     *
     * @param response
     */
    @RequestMapping(value = "insert/maintain", method = RequestMethod.POST)
    public void insertPbomLineMaintainRecord(InsertHzPbomMaintainRecordReqDTO reqDTO, HttpServletResponse response) {
        System.out.println("++++++++————————+——+————（）*（（（");
        writeAjaxJSONResponse(ResultMessageBuilder.build(false, "接口后续在定义"), response);
    }

    @RequestMapping(value = "update/list/maintain",method = RequestMethod.GET)
    public  String updatePbomLineMaintainRecordList(Model model){
        Map<String ,String> map =  new HashMap<>();
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");



        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        model.addAttribute("entity",map);
        return "pbom/pbomMaintenance/updatePbomMaintenance";
    }

    /**
     * 编辑
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "update/maintain", method = RequestMethod.POST)
    public void updatePbomLineMaintainRecord(UpdateHzPbomMaintainRecordReqDTO reqDTO, HttpServletResponse response) {
        System.out.println("社会猪，小佩奇");
        writeAjaxJSONResponse(ResultMessageBuilder.build(false, "接口后续在定义"), response);
    }

    /**
     * 删除
     *
     * @param pPuid
     * @param response
     */
    @RequestMapping(value = "delete/maintain", method = RequestMethod.POST)
    public void deletePbomLineMaintainRecord(String pPuid, HttpServletResponse response) {
        writeAjaxJSONResponse(ResultMessageBuilder.build(false, "接口后续在定义"), response);
    }


    @RequestMapping(value = "manage/title", method = RequestMethod.GET)
    public void getPbomLineTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("level", "层级");
        tableTitle.put("pBomOfWhichDept", "专业");
        tableTitle.put("rank", "级别");
        tableTitle.put("groupNum", "分组号");
        tableTitle.put("lineId", "零件号");//这个字段暂时是一个替代品，后续要改
        tableTitle.put("itemType", "零件分类");
        tableTitle.put("itemResource", "零部件来源");
        tableTitle.put("resource", "自制/采购");
        tableTitle.put("type", "焊接/装配");
        tableTitle.put("buyUnit", "采购单元");
        tableTitle.put("workShop1", "车间1");
        tableTitle.put("workShop2", "车间2");
        tableTitle.put("productLine", "生产线");
        tableTitle.put("mouldType", "模具类别");
        tableTitle.put("outerPart", "外委件");
        tableTitle.put("colorPart", "颜色件");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }

    /**
     * 查询PBOM管理信息
     *
     * @param response
     */
    @RequestMapping(value = "getBomManage", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPbomLineRecord(HzPbomProcessComposeReqDTO reqDTO,HttpServletResponse response) {
        List<HzPbomLineRespDTO> respDTOS = hzPbomService.getHzPbomLineRecord(reqDTO);
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, String>> _list = new ArrayList<>();
        respDTOS.forEach(dto -> {
            Map<String, String> _res = new HashMap<>();
            _res.put("eBomPuid", dto.geteBomPuid());
            _res.put("level", dto.getLevel());
            _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
            _res.put("rank", dto.getRank());
            _res.put("groupNum", dto.getGroupNum());
            _res.put("lineId", dto.getLineId());
            _res.put("itemType", dto.getItemType());
            _res.put("itemResource", dto.getItemResource());
            _res.put("resource", dto.getResource());
            _res.put("type", dto.getType());
            _res.put("buyUnit", dto.getBuyUnit());
            _res.put("workShop1", dto.getWorkShop1());
            _res.put("workShop2", dto.getWorkShop2());
            _res.put("productLine", dto.getMouldType());
            _res.put("mouldType", dto.getMouldType());
            _res.put("outerPart", dto.getOuterPart());
            _res.put("colorPart", dto.getColorPart());
            _list.add(_res);
        });
        ret.put("totalCount", respDTOS.size());
        ret.put("result", _list);
        return ret;
//        if(ListUtil.isEmpty(respDTOS)){
//            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无符合数据"),response);
//        }
//        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTOS),response);
//    }
    }

    /**
     * 搜索PBOM在线维护
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "searchMaintain/detail", method = RequestMethod.GET)
    public void searchPbomMaintainRecord(SearchPbomDetailReqDTO reqDTO, HttpServletResponse response) {
        List<HzPbomLineMaintainRespDTO> respDTOS = hzPbomService.searchPbomLineMaintainRecord(reqDTO);
        if (ListUtil.isEmpty(respDTOS)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, "暂无数据"), response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTOS), response);
    }

    /**
     * 搜索PBOM管理维护信息
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "searchManage/detail", method = RequestMethod.GET)
    public void searchPbomManageRecord(SearchPbomDetailReqDTO reqDTO, HttpServletResponse response) {
        List<HzPbomLineRespDTO> respDTOS = hzPbomService.searchPbomLineManageRecord(reqDTO);
        if (ListUtil.isEmpty(respDTOS)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, "暂无数据"), response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTOS), response);
    }


    @RequestMapping(value = "insert/manage", method = RequestMethod.POST)
    public void insertPbomManageRecord(InsertHzPbomRecordReqDTO reqDTO, HttpServletResponse response) {
        writeAjaxJSONResponse(ResultMessageBuilder.build(false, "接口后续在定义"), response);
    }

    @RequestMapping(value = "updateManageProcess", method = RequestMethod.GET)
    public String updatePbomManageRecordProcess() {
        return "bomManage/pbom/pbomManage/updateProcess";
    }

    @RequestMapping(value = "updateManageBom", method = RequestMethod.GET)
    public String updatePbomManageRecordBom() {
        return "bomManage/pbom/pbomManage/updateBom";
    }

    @RequestMapping(value = "update/manage", method = RequestMethod.POST)
    public void updatePbomManageRecord(UpdateHzPbomRecordReqDTO reqDTO, HttpServletResponse response) {
        writeAjaxJSONResponse(ResultMessageBuilder.build(false, "接口后续在定义"), response);
    }

    @RequestMapping(value = "delete/manage", method = RequestMethod.POST)
    public void deletePbomManageRecord(String eBomPuid, HttpServletResponse response) {
        writeAjaxJSONResponse(ResultMessageBuilder.build(false, "接口后续在定义"), response);
    }

    /**
     * 获取PBOM结构树
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "processComposeTree",method = RequestMethod.GET)
    public void findProcessComposeTree(HzPbomProcessComposeReqDTO reqDTO,HttpServletResponse response){
         if(reqDTO==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
        }
        if(reqDTO.getProjectId() == null ||reqDTO.getLineId()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);

        }
        JSONArray jsonArray = hzPbomService.getPbomForProcessCompose(reqDTO);
        writeAjaxJSONResponse(jsonArray,response);
    }

    /**
     * 获取一条PBOM 信息
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "detail",method = RequestMethod.GET)
    public void findPbomDetail(HzPbomProcessComposeReqDTO reqDTO,HttpServletResponse response){
        if(reqDTO==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
        }
        if(reqDTO.getProjectId() == null ||reqDTO.getLineId()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);

        }
        JSONArray object = hzPbomService.getPbomByLineId(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(true,object),response);
    }

    /**
     * 合成工艺合件
     * @param
     * @param response
     * AddProcessComposeReqDTO reqDTO,
     */
    @RequestMapping(value = "/add/processCompose",method = RequestMethod.POST)
    public void addProcessCompose(@RequestBody  Map<String,Object> obj, AddProcessComposeReqDTO reqDTO1, HttpServletResponse response){
        if(reqDTO1.getProjectPuid()==null || reqDTO1.getLineId() == null || obj == null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
        }
        AddProcessComposeReqDTO reqDTO=new AddProcessComposeReqDTO();
        reqDTO.seteBomContent(obj);
        reqDTO.setLineId(reqDTO1.getLineId());
        reqDTO.setPuid(reqDTO1.getPuid());
        reqDTO.setProjectPuid(reqDTO1.getProjectPuid());
        reqDTO.setpBomLinePartClass(reqDTO1.getpBomLinePartClass());
        reqDTO.setpBomLinePartName(reqDTO1.getpBomLinePartName());
        int i = hzPbomService.addPbomProcessCompose(reqDTO);
        if(i!=1){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"出错啦！"),response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(true,"操作成功！"),response);

    }
}
