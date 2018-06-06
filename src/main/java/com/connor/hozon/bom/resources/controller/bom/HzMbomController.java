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
}
