package com.connor.hozon.bom.resources.controller.erp;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by haozt on 2018/5/29
 */
@Controller
@RequestMapping(value = "/sap")
public class SapController extends BaseController {
    /**
     * 接口暂未实现 只是定义
     * @param response
     */
    @RequestMapping(value = "tc/bom",method = RequestMethod.GET)
    public void bomForSap(HttpServletResponse response){
        writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无数据"),response);
    }
}

