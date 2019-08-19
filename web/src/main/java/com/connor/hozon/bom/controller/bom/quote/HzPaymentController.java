package com.connor.hozon.bom.controller.bom.quote;

import com.connor.hozon.bom.resources.domain.model.HzPaymentBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("payment")
public class HzPaymentController {

    @RequestMapping("/loadPayment")
    public Map<String, Object> load(HzPaymentBean hzPaymentBean){
        return null;
    }
}
