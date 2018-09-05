package com.connor.hozon.bom.bomSystem.controller.integrate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.connor.hozon.bom.bomSystem.service.integrate.SynProcessRouteService;
import com.connor.hozon.bom.resources.dto.response.HzWorkProcessRespDTO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/work")
public class SynProcessRouteController extends ExtraIntegrate {

    @Autowired
    SynProcessRouteService synProcessRouteService;
    @RequestMapping("/process/submit")
    public String submit( String[] materielIds, String projectId, Model model){
        JSONObject result =  synProcessRouteService.addRouting(materielIds, projectId);
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }
    @RequestMapping("/process/updata")
    public String updata( String[] materielIds, String projectId, Model model){
        JSONObject result =  synProcessRouteService.updateRouting(materielIds, projectId);
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }
    @RequestMapping("/process/delete1")
    public String delete( String[] materielIds, String projectId, Model model){
        JSONObject result =  synProcessRouteService.deleteRouting(materielIds, projectId);
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }

}
