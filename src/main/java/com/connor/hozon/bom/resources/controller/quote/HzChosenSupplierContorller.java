package com.connor.hozon.bom.resources.controller.quote;


import com.connor.hozon.bom.resources.domain.model.HzChosenSupplier;
import com.connor.hozon.bom.sys.service.HzChosenSupplierService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chosenSupplier")
public class HzChosenSupplierContorller {

    @Autowired
    private HzChosenSupplierService hzChosenSupplierService;

    @RequestMapping("/loadChosenSupplier")
    @ResponseBody
    public JSONObject load(HzChosenSupplier hzChosenSupplier){
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
    public JSONObject update(@RequestBody HzChosenSupplier hzChosenSupplier){
        return hzChosenSupplierService.update(hzChosenSupplier);
    }
}
