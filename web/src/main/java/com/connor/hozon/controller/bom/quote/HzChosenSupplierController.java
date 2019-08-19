package com.connor.hozon.controller.bom.quote;


import com.connor.hozon.resources.domain.model.HzChosenSupplier;
import com.connor.hozon.resources.service.quote.impl.HzChosenSupplierServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/chosenSupplier")
public class HzChosenSupplierController {

    @Autowired
    private HzChosenSupplierServiceImpl hzChosenSupplierService;

    @RequestMapping("/loadChosenSupplier")
    @ResponseBody
    public Map<String, Object> load(HzChosenSupplier hzChosenSupplier){
        return hzChosenSupplierService.load(hzChosenSupplier);
    }

    @RequestMapping("/addPage")
    public String addPage(String projectPuid, Model model){
        model.addAttribute("projectPuid",projectPuid);
        model.addAttribute("action","./chosenSupplier/add");
        return "quote/offer/addOfferManage";
    }

    @RequestMapping("/add")
    @ResponseBody
    public JSONObject add(@RequestBody HzChosenSupplier hzChosenSupplier){
        return hzChosenSupplierService.add(hzChosenSupplier);
    }

    @RequestMapping("/updatePage")
    public String updatePage(String projectPuid,Long id, Model model){
        HzChosenSupplier hzChosenSupplier = hzChosenSupplierService.selectById(id);
        model.addAttribute("hzChosenSupplier",hzChosenSupplier);
        model.addAttribute("projectPuid",projectPuid);
        model.addAttribute("action","./chosenSupplier/update");
        return "quote/offer/updateOfferManage";
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody HzChosenSupplier hzChosenSupplier){
        return hzChosenSupplierService.update(hzChosenSupplier);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject delete(String ids){
        return hzChosenSupplierService.delete(ids);
    }

}
