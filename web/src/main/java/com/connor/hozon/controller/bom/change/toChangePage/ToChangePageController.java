package com.connor.hozon.controller.bom.change.toChangePage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(value = "toMyListJob")
public class ToChangePageController {
    @RequestMapping(value = "ebom/page")
    public String ebomDataTOPage(Model model, Long orderId){
        model.addAttribute("orderId",orderId);
        return "myListJob/changeEbomTable";
    }
    @RequestMapping(value = "pbom/page")
    public String pbomDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "myListJob/changePbomTable";
    }
    @RequestMapping(value = "mbom/page")
    public String mbomDataTOPage(Model model,Long orderId,Integer type){
        model.addAttribute("orderId",orderId);
        model.addAttribute("type",type);
        return "myListJob/changeMbomTable";
    }
    @RequestMapping(value = "material/page")
    public String materialDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "myListJob/changeMaterialTable";
    }
    @RequestMapping(value = "routing/page")
    public String routingDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "myListJob/changeRoutingTable";
    }
    @RequestMapping(value = "feature/page")
    public String featureDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "myListJob/changeFeatureTable";
    }
    @RequestMapping(value = "modelColorCfg/page")
    public String modelColorCfgDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "myListJob/changeColorCfgTable";
    }
    @RequestMapping(value = "materielFeature/page")
    public String materielFeatureDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "myListJob/changeMaterielFeatureTable";
    }
    @RequestMapping(value = "bomCfg/page")
    public String bomCfgDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "myListJob/changeBomCfgTable";
    }
    @RequestMapping(value = "relevance/page")
    public String relevancePage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "myListJob/changeRelevanceTable";
    }
}
