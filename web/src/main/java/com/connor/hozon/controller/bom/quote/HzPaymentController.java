package com.connor.hozon.controller.bom.quote;

import com.connor.hozon.resources.domain.model.HzPaymentBean;
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
