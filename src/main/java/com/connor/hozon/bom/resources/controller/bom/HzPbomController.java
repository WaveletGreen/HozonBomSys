package com.connor.hozon.bom.resources.controller.bom;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineMaintainRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by haozt on 2018/5/24
 */
@Controller
@RequestMapping(value = "/pbom")
public class HzPbomController  extends BaseController {

    @Autowired
    private HzPbomService hzPbomService;

    /**
     * 获取PBOM 在线维护信息详情
     * @param response
     */
    @RequestMapping(value = "getMaintain/detail", method = RequestMethod.GET)
    public void getPbomLineMaintainDetail(HttpServletResponse response){
        List<HzPbomLineMaintainRespDTO> responseDTOList = hzPbomService.getHzPbomMaintainRecord();
        if(ListUtil.isEmpty(responseDTOList)){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无符合数据！"),response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(responseDTOList),response);
    }

    /**
     * 获取PBOM维护的表头数据
     * @param response
     */
    @RequestMapping(value = "maintain/title")
    public void getPbomMaintainTitle(HttpServletResponse response){
        Map<String,String> tableTitle = new HashMap<>();
        tableTitle.put("1","序号");
        tableTitle.put("2","层级");
        tableTitle.put("3","专业");
        tableTitle.put("4","零件号");
        tableTitle.put("5","名称");//这个字段暂时是一个替代品，后续要改
        tableTitle.put("6","备件");
        tableTitle.put("7","备件编号");
        tableTitle.put("8","工艺路线");
        tableTitle.put("9","人工工时");
        tableTitle.put("10","节拍");
        tableTitle.put("11","焊点");
        tableTitle.put("12","机物料");
        tableTitle.put("13","标准件");
        tableTitle.put("14","工艺");
        tableTitle.put("15","废品");
        tableTitle.put("16","变更");
        tableTitle.put("17","变更号");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle),response);
    }

    /**
     * 新增
     * @param response
     */
    @RequestMapping(value = "insert/maintain",method = RequestMethod.POST)
    public void insertPbomLineMaintainRecord(InsertHzPbomMaintainRecordReqDTO reqDTO,HttpServletResponse response){
        writeAjaxJSONResponse(ResultMessageBuilder.build(false,"接口后续在定义"),response);
    }

    /**
     * 编辑
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "update/maintain",method = RequestMethod.POST)
    public void updatePbomLineMaintainRecord(UpdateHzPbomMaintainRecordReqDTO reqDTO ,HttpServletResponse response){
        writeAjaxJSONResponse(ResultMessageBuilder.build(false,"接口后续在定义"),response);
    }

    /**
     * 删除
     * @param pPuid
     * @param response
     */
    @RequestMapping(value = "delete/maintain",method = RequestMethod.POST)
    public void deletePbomLineMaintainRecord(String pPuid,HttpServletResponse response){
        writeAjaxJSONResponse(ResultMessageBuilder.build(false,"接口后续在定义"),response);
    }


    @RequestMapping(value = "manage/title",method = RequestMethod.GET)
    public void getPbomLineTitle(HttpServletResponse response){
        Map<String,String> tableTitle = new HashMap<>();
        tableTitle.put("1","层级");
        tableTitle.put("2","专业");
        tableTitle.put("3","级别");
        tableTitle.put("4","分组号");
        tableTitle.put("5","零件号");//这个字段暂时是一个替代品，后续要改
        tableTitle.put("6","零件分类");
        tableTitle.put("7","零部件来源");
        tableTitle.put("8","自制/采购");
        tableTitle.put("9","焊接/装配");
        tableTitle.put("10","采购单元");
        tableTitle.put("11","车间1");
        tableTitle.put("12","车间2");
        tableTitle.put("13","生产线");
        tableTitle.put("14","模具类别");
        tableTitle.put("15","外委件");
        tableTitle.put("16","颜色件");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle),response);
    }

    /**
     * 查询PBOM管理信息
     * @param response
     */
    @RequestMapping(value = "getBomManage",method = RequestMethod.GET)
    public void getPbomLineRecord(HttpServletResponse response){
        List<HzPbomLineRespDTO> respDTOS = hzPbomService.getHzPbomLineRecord();
        if(ListUtil.isEmpty(respDTOS)){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无符合数据"),response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTOS),response);
    }

    /**
     * 搜索PBOM在线维护
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "searchMaintain/detail",method = RequestMethod.GET)
    public void searchPbomMaintainRecord(SearchPbomDetailReqDTO reqDTO,HttpServletResponse response){
        List<HzPbomLineMaintainRespDTO> respDTOS = hzPbomService.searchPbomLineMaintainRecord(reqDTO);
        if(ListUtil.isEmpty(respDTOS)){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无数据"),response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTOS),response);
    }

    /**
     * 搜索PBOM管理维护信息
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "searchManage/detail",method = RequestMethod.GET)
    public void searchPbomManageRecord(SearchPbomDetailReqDTO reqDTO,HttpServletResponse response){
        List<HzPbomLineRespDTO> respDTOS = hzPbomService.searchPbomLineManageRecord(reqDTO);
        if(ListUtil.isEmpty(respDTOS)){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无数据"),response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTOS),response);
    }

    @RequestMapping(value = "insert/manage",method = RequestMethod.POST)
    public void insertPbomManageRecord(InsertHzPbomRecordReqDTO reqDTO, HttpServletResponse response){
        writeAjaxJSONResponse(ResultMessageBuilder.build(false,"接口后续在定义"),response);
    }
    @RequestMapping(value = "update/manage",method = RequestMethod.POST)
    public void updatePbomManageRecord(UpdateHzPbomRecordReqDTO reqDTO,HttpServletResponse response){
        writeAjaxJSONResponse(ResultMessageBuilder.build(false,"接口后续在定义"),response);
    }
    @RequestMapping(value = "delete/manage",method = RequestMethod.POST)
    public void deletePbomManageRecord(String eBomPuid,HttpServletResponse response){
        writeAjaxJSONResponse(ResultMessageBuilder.build(false,"接口后续在定义"),response);
    }
}
