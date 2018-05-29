package com.connor.hozon.bom.resources.controller.bom;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineMaintainRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public Map<String,Object> getPbomLineMaintainDetail(HttpServletResponse response){
        List<HzPbomLineMaintainRespDTO> responseDTOList = hzPbomService.getHzPbomMaintainRecord();
        Map<String,Object> ret=new HashMap<>();
        List<Map<String,String>> _list=new ArrayList<>();
        responseDTOList.forEach(dto->{
            Map<String,String> _res=new HashMap<>();
            _res.put("level",dto.getLevel());
            _res.put("pBomOfWhichDept",dto.getpBomOfWhichDept());
            _res.put("lineId",dto.getLineId());
            _res.put("bomDigifaxId",dto.getBomDigifaxId());
            _res.put("sparePart",dto.getSparePart());
            _res.put("sparePartNum",dto.getSparePartNum());
            _res.put("processRoute",dto.getProcessRoute());
            _res.put("laborHour",dto.getLaborHour());
            _res.put("rhythm",dto.getRhythm());
            _res.put("solderJoint",dto.getSolderJoint());
            _res.put("machineMaterial",dto.getMachineMaterial());
            _res.put("standardPart",dto.getStandardPart());
            _res.put("tools",dto.getTools());
            _res.put("wasterProduct",dto.getWasterProduct());
            _res.put("change",dto.getChange());
            _res.put("changeNum",dto.getChangeNum());
            _list.add(_res);
        });
        ret.put("totalCount",responseDTOList.size());
        ret.put("result",_list);
        return ret;
//        if(ListUtil.isEmpty(responseDTOList)){
//            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无符合数据！"),response);
//        }
//        return responseDTOList;
//        writeAjaxJSONResponse(ResultMessageBuilder.build(responseDTOList),response);
    }

    /**
     * 获取PBOM维护的表头数据
     * @param response
     */
    @RequestMapping(value = "maintain/title")
    public void getPbomMaintainTitle(HttpServletResponse response){
        LinkedHashMap<String,String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("level","层级");
        tableTitle.put("pBomOfWhichDept","专业");
        tableTitle.put("lineId","零件号");
        tableTitle.put("bomDigifaxId","名称");//这个字段暂时是一个替代品，后续要改
        tableTitle.put("sparePart","备件");
        tableTitle.put("sparePartNum","备件编号");
        tableTitle.put("processRoute","工艺路线");
        tableTitle.put("laborHour","人工工时");
        tableTitle.put("rhythm","节拍");
        tableTitle.put("solderJoint","焊点");
        tableTitle.put("machineMaterial","机物料");
        tableTitle.put("standardPart","标准件");
        tableTitle.put("tools","工艺");
        tableTitle.put("wasterProduct","废品");
        tableTitle.put("change","变更");
        tableTitle.put("changeNum","变更号");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle),response);
    }

    @RequestMapping(value = "insert/batch",method = RequestMethod.POST)
    public void insertPbomLineMaintainRecord(HttpServletResponse response){
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
    @RequestMapping(value = "getBomManage",method = RequestMethod.GET)
    public void getPbomLineRecord(HttpServletResponse response){
        List<HzPbomLineRespDTO> respDTOS = hzPbomService.getHzPbomLineRecord();
        if(ListUtil.isEmpty(respDTOS)){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无符合数据"),response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(respDTOS),response);
    }


    

}
