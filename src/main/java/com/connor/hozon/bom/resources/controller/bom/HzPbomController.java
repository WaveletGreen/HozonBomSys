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
        tableTitle.put("lineId", "零件号");

        tableTitle.put("pBomLinePartName", "名称");
        tableTitle.put("pBomLinePartEnName", "英文名称");
        tableTitle.put("pBomLinePartClass", "零件分类");
        tableTitle.put("pBomLinePartResource", "零部件来源");

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
            _res.put("puid",dto.getPuid());
            _res.put("eBomPuid", dto.geteBomPuid());
            _res.put("No",dto.getNo());
            _res.put("level", dto.getLevel());
            _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
            _res.put("rank", dto.getRank());
            _res.put("groupNum", dto.getGroupNum());
            _res.put("lineId", dto.getLineId());

            _res.put("pBomLinePartName",dto.getpBomLinePartName());
            _res.put("pBomLinePartEnName",dto.getpBomLinePartEnName());
            _res.put("pBomLinePartClass",dto.getpBomLinePartClass());
            _res.put("pBomLinePartResource",dto.getpBomLinePartResource());

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
        hzPbomLineRespDTO.setpBomLinePartClass(respDTO.getpBomLinePartClass());
        hzPbomLineRespDTO.setpBomLinePartResource(respDTO.getpBomLinePartResource());
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
        if(reqDTO.getProjectId() == null ||reqDTO.getPuid()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return;
        }

        JSONArray object = hzPbomService.getPbomByLineId(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(object),response);
    }

    @RequestMapping(value = "updataProcessOfFitting", method = RequestMethod.GET)
    public String updateRecordToPage(String puids,Model model) {
        HzPbomLineRespDTO respDTO = new HzPbomLineRespDTO();
        respDTO.setPuids(puids);
        model.addAttribute("data",respDTO);
        return "bomManage/pbom/processOfFitting/updataProcessOfFitting";
    }

    /**
     * 合成工艺合件
     * @param
     * @param response
     */
    @RequestMapping(value = "/add/processCompose",method = RequestMethod.POST)
    public void addProcessCompose(@RequestBody List<AddHzPbomRecordReqDTO> recordReqDTOS,String projectId,  HttpServletResponse response){
        if(projectId==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return;
        }
        OperateResultMessageRespDTO operateResultMessageRespDTO = hzPbomService.andProcessCompose(recordReqDTOS,projectId);

        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(operateResultMessageRespDTO),operateResultMessageRespDTO.getErrMsg()),response);

    }


}
