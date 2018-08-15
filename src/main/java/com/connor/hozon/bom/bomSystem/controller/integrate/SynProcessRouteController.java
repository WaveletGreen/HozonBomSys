package com.connor.hozon.bom.bomSystem.controller.integrate;

import com.connor.hozon.bom.bomSystem.service.integrate.SynProcessRouteService;
import com.connor.hozon.bom.resources.dto.response.HzWorkProcessRespDTO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/work")
public class SynProcessRouteController {

    @Autowired
    SynProcessRouteService synProcessRouteService;
    @RequestMapping("/process/submit")
    @ResponseBody
    public JSONObject submit(@RequestBody HzWorkProcessRespDTO[] respDTOS, String projectId){
        List<String> materielIds = new ArrayList<String>();
        for(HzWorkProcessRespDTO respDTO : respDTOS){
            materielIds.add(respDTO.getMaterielId());
        }
        return synProcessRouteService.addRouting(materielIds, projectId);
    }

}
