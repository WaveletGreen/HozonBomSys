package com.connor.hozon.bom.resources.controller.change.changeForm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/9
 * Time: 10:55
 */

@Controller
@RequestMapping(value = "change")
public class HzChangeFormContrller {

    @RequestMapping(value = "addPage",method = RequestMethod.GET)
    public String getAddChangeFormToPage(){

        return "change/changeForm/addChangeForm";
    }
    @RequestMapping(value = "updatePage",method = RequestMethod.GET)
    public String getUpdateChangeFormToPage(){

        return "change/changeForm/updateChangeForm";
    }
    @RequestMapping(value = "ToChangeOrder",method = RequestMethod.GET)
    public String getToChangeOrderToPage(){

        return "change/ChangeOrder/ChangeOrder";
    }
    @RequestMapping(value = "ToUntreatedForm",method = RequestMethod.GET)
    public String getToUntreatedFormToPage(){

        return "myListJob/untreated/untreatedForm";
    }
}
