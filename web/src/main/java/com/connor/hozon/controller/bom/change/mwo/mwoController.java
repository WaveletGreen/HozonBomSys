package com.connor.hozon.controller.bom.change.mwo;

import com.connor.hozon.controller.bom.BaseController;
import com.connor.hozon.resources.domain.dto.request.InitiatingProcessReqDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.service.change.HzMWOService;
import com.connor.hozon.resources.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * \* User: xulf
 * \* Date: 2018/7/31
 * \* Time: 16:33
 * \
 */
@Controller
@RequestMapping(value = "/mwo")
public class mwoController extends BaseController {

    @Autowired
    private HzMWOService hzMWOService;

    @RequestMapping(value = "mwoFromList" ,method = RequestMethod.GET)
    public String getMwoFromList(){
        return "changeManage/mwo/mwoBasicInformation";
    }

    /**
     * MWO表单发起流程
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "initiating/process",method = RequestMethod.POST)
    public void initiatingProcessForChange(@RequestBody InitiatingProcessReqDTO reqDTO, HttpServletResponse response){
        if(null == reqDTO.getPuids() || reqDTO.getPuids().equals("")){
            toJSONResponse(Result.build(false,"非法参数！"),response);
            return;
        }

        WriteResultRespDTO respDTO = hzMWOService.initiatingProcessForMwoChange(reqDTO);

        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO),response);
    }
}
