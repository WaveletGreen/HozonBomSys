package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzPbomByPageQuery;
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


    @RequestMapping(value = "manage/title", method = RequestMethod.GET)
    public void getPbomLineTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No", "序号");
        tableTitle.put("level", "层级");
        tableTitle.put("pBomOfWhichDept", "专业");
        tableTitle.put("rank", "级别");
        tableTitle.put("groupNum", "分组号");
        tableTitle.put("lineId", "零件号");//这个字段暂时是一个替代品，后续要改
        tableTitle.put("h9_IsCommon", "零件分类");
        tableTitle.put("H9_Mat_Status", "零部件来源");
        tableTitle.put("resource", "自制/采购");
        tableTitle.put("type", "焊接/装配");
        tableTitle.put("buyUnit", "采购单元");
        tableTitle.put("workShop1", "车间1");
        tableTitle.put("workShop2", "车间2");
        tableTitle.put("productLine", "生产线");
        tableTitle.put("mouldType", "模具类别");
        tableTitle.put("outerPart", "外委件");
        tableTitle.put("colorPart", "颜色件");
        tableTitle.put("station","工位");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }

    /**
     * 查询PBOM管理信息
     *
     * @param
     */
    @RequestMapping(value = "getBomManage", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPbomLineRecord(HzPbomByPageQuery query) {
        Page<HzPbomLineRespDTO> respDTOPage = hzPbomService.getHzPbomRecordPage(query);
        List<HzPbomLineRespDTO> respDTOS = respDTOPage.getResult();
        if(respDTOS == null){
            return new HashMap<>();
        }
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        respDTOS.forEach(dto -> {
            Map<String, Object> _res = new HashMap<>();
            _res.put("eBomPuid", dto.geteBomPuid());
            _res.put("No",dto.getNo());
            _res.put("level", dto.getLevel());
            _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
            _res.put("rank", dto.getRank());
            _res.put("groupNum", dto.getGroupNum());
            _res.put("lineId", dto.getLineId());
            _res.put("h9_IsCommon",dto.getH9_IsCommon());
            _res.put("H9_Mat_Status",dto.getH9_Mat_Status());
            _res.put("resource", dto.getResource());
            _res.put("type", dto.getType());
            _res.put("buyUnit", dto.getBuyUnit());
            _res.put("workShop1", dto.getWorkShop1());
            _res.put("workShop2", dto.getWorkShop2());
            _res.put("productLine", dto.getProductLine());
            _res.put("mouldType", dto.getMouldType());
            _res.put("outerPart", dto.getOuterPart());
            _res.put("colorPart", dto.getColorPart());
            _res.put("station",dto.getStation());
            _list.add(_res);
        });
        ret.put("totalCount", respDTOPage.getTotalCount());
        ret.put("result", _list);
        return ret;
    }

    /**
     * 搜索PBOM在线维护
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "searchMaintain/detail", method = RequestMethod.GET)
    public void searchPbomMaintainRecord(SearchPbomDetailReqDTO reqDTO, HttpServletResponse response) {
        List<HzMbomRecordRespDTO> respDTOS = hzPbomService.searchPbomLineMaintainRecord(reqDTO);
        if (ListUtil.isEmpty(respDTOS)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, "暂无数据"), response);
        }else {
            writeAjaxJSONResponse(ResultMessageBuilder.build(respDTOS), response);

        }
    }

    /**
     * 搜索PBOM管理维护信息
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "searchManage/detail", method = RequestMethod.GET)
    public void searchPbomManageRecord(SearchPbomDetailReqDTO reqDTO, HttpServletResponse response) {
        List<HzPbomLineRespDTO> respDTOS = hzPbomService.searchPbomManageRecord(reqDTO);
        if (ListUtil.isEmpty(respDTOS)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, "暂无数据"), response);
        }else
        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTOS), response);
    }

    /**
     * 跳转到PBOM管理添加页面
     * @return
     */
    @RequestMapping(value = "addPbomManage", method = RequestMethod.GET)
    public String addPbomManageRecordToPage(String eBomPuid, String projectId, Model model) {
        HzPbomLineRespDTO respDTO = hzPbomService.getHzPbomByPuid(projectId,eBomPuid);
        if(respDTO == null){
            return "";
        }
        HzPbomLineRespDTO hzPbomLineRespDTO = new HzPbomLineRespDTO();
        hzPbomLineRespDTO.setLineId(respDTO.getLineId());
        hzPbomLineRespDTO.seteBomPuid(respDTO.geteBomPuid());
        hzPbomLineRespDTO.setGroupNum(respDTO.getGroupNum());
        hzPbomLineRespDTO.setpBomOfWhichDept(respDTO.getpBomOfWhichDept());
        hzPbomLineRespDTO.setRank(respDTO.getRank());
        hzPbomLineRespDTO.setLevel(respDTO.getLevel());
        hzPbomLineRespDTO.setH9_IsCommon(respDTO.getH9_IsCommon());
        hzPbomLineRespDTO.setH9_Mat_Status(respDTO.getH9_Mat_Status());
        model.addAttribute("data",hzPbomLineRespDTO);
        return "bomManage/pbom/pbomManage/addPbomManage";
    }
    /**
     * 跳转到PBOM管理修改页面
     * @return
     */
    @RequestMapping(value = "updatePbomManage", method = RequestMethod.GET)
    public String updatePbomManageRecordToPage(String projectId,String eBomPuid,Model model) {
        HzPbomLineRespDTO respDTO = hzPbomService.getHzPbomByPuid(projectId,eBomPuid);
        if(respDTO == null){
            return "";
        }
        model.addAttribute("data",respDTO);
        return "bomManage/pbom/pbomManage/updatePbomManage";
    }



    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public void addPbomToDB(@RequestBody AddHzPbomRecordReqDTO reqDTO, HttpServletResponse response) {
        OperateResultMessageRespDTO respDTO = hzPbomService.insertHzPbomRecord(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public void updatePbomRecord(@RequestBody UpdateHzPbomRecordReqDTO reqDTO, HttpServletResponse response) {
        OperateResultMessageRespDTO respDTO =hzPbomService.updateHzPbomRecord(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }


    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void deletePbomRecord(String eBomPuid, HttpServletResponse response) {
        OperateResultMessageRespDTO respDTO = hzPbomService.deleteHzPbomRecordByForeignId(eBomPuid);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
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
             return;
         }
        if(reqDTO.getProjectId() == null ||reqDTO.getLineId()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return;
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
            return;
        }
        if(reqDTO.getProjectId() == null ||reqDTO.getLineId()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return;
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
            return;
        }
        if(reqDTO1.getLineId().contains("-")){
            if(reqDTO1.getLineId().split("-")[1].length()<4){
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, "零件号-后面的长度不能小于4！"), response);
                return;
            }
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
        }else
        writeAjaxJSONResponse(ResultMessageBuilder.build(true,"操作成功！"),response);

    }
}
