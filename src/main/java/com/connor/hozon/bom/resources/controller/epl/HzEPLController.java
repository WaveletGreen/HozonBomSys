package com.connor.hozon.bom.resources.controller.epl;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.FindHzEPLRecordReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by haozt on 2018/06/05
 */
@Controller
@RequestMapping(value = "/epl")
public class HzEPLController extends BaseController {

    @Autowired
    private HzEPLManageRecordService hzEPLManageRecordService;

    @RequestMapping(value = "record",method = RequestMethod.GET)
    public void getHzEplRecord(FindHzEPLRecordReqDTO recordReqDTO, HttpServletResponse response){
        List<HzEPLRecordRespDTO> recordRespDTOS =  hzEPLManageRecordService.getHzEPLRecord(recordReqDTO);
        if(ListUtil.isEmpty(recordRespDTOS)){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无符合数据"),response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(recordRespDTOS),response);
    }
}
