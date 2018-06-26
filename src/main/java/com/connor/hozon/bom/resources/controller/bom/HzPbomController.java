package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }

    /**
     * 查询PBOM管理信息
     *
     * @param
     */
    @RequestMapping(value = "getBomManage", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPbomLineRecord(FindForPageReqDTO reqDTO) {
        Page<HzPbomLineRespDTO> respDTOPage = hzPbomService.getHzPbomRecordPage(reqDTO);
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
            _res.put("productLine", dto.getMouldType());
            _res.put("mouldType", dto.getMouldType());
            _res.put("outerPart", dto.getOuterPart());
            _res.put("colorPart", dto.getColorPart());
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


    @RequestMapping(value = "insert/manage", method = RequestMethod.POST)
    public void insertPbomManageRecord(InsertHzPbomRecordReqDTO reqDTO, HttpServletResponse response) {
        writeAjaxJSONResponse(ResultMessageBuilder.build(false, "接口后续在定义"), response);
    }

    /**
     * 跳转到PBOM管理添加页面
     * @return
     */
    @RequestMapping(value = "addPbomManage", method = RequestMethod.GET)
    public String updatePbomManageRecordProcess() {
        return "bomManage/pbom/pbomManage/addPbomManage";
    }
    /**
     * 跳转到PBOM管理修改页面
     * @return
     */
    @RequestMapping(value = "updatePbomManage", method = RequestMethod.GET)
    public String updatePbomManageRecordBom() {
        return "bomManage/pbom/pbomManage/updatePbomManage";
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
        }else
        writeAjaxJSONResponse(ResultMessageBuilder.build(true,"操作成功！"),response);

    }
}
