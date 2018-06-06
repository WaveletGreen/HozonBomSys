package com.connor.hozon.bom.resources.controller.bom;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * \* User: xulf
 * \* Date: 2018/6/6
 * \* Time: 12:58
 * \
 */

@Controller
@RequestMapping(value = "/mbom")
public class HzMbomController extends BaseController {

    @RequestMapping(value = "superBomTitel",method = RequestMethod.GET)
    public void getMbomSuperBomGetTitel(HttpServletResponse response){
        LinkedHashMap<String,String> titel = new LinkedHashMap<>();
        titel.put("sparePart","备件");
        titel.put("sparePartNum","备件编号");
        titel.put("processRoute","工艺路线");
        titel.put("laborHour","人工工时");
        titel.put("rhythm","节拍");
        titel.put("solderJoint","焊点");
        titel.put("machineMaterial","机物料");
        titel.put("standardPart","标准件");
        titel.put("tools","工具");
        titel.put("wasterProduct","废品");
        writeAjaxJSONResponse(ResultMessageBuilder.build(titel),response);
    }


    @RequestMapping(value = "workCenterTitel",method = RequestMethod.GET)
    public void getMbomWorkCenterTitel(HttpServletResponse response){
        LinkedHashMap<String,String> titel = new LinkedHashMap<>();
        titel.put("factory","工厂");
        titel.put("workHours","工作工时");
        titel.put("jobCentreCategory","工作中心类别");
        titel.put("describe","描述");
        titel.put("use","用途");
        titel.put("standardCode","标准值码");
        titel.put("controlCode","控制码");
        titel.put("directLabor","直接人工");
        titel.put("indirectLabor","间接人工");
        titel.put("machineHour","机器工时 ");
        titel.put("combustion","燃动");
        titel.put("machineMaterial","机物料");
        titel.put("otherCosts","其它费用");
        titel.put("processingFormula","加工公式");
        titel.put("powerType","能力类别");
        titel.put("startTime","开始时间");
        titel.put("endTime","结束时间");
        writeAjaxJSONResponse(ResultMessageBuilder.build(titel),response);
    }
}
