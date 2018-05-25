package com.connor.hozon.bom.resources.controller.bom;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineMaintainResponseDTO;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by haozt on 2018/5/24
 */
@Controller
@RequestMapping(value = "/pbom")
public class HzPbomController  extends BaseController {

    @Autowired
    private HzPbomService hzPbomService;

    /**
     * 获取PBOM 在线维护信息详情
     * @param response
     */
    @RequestMapping(value = "getMaintain/detail", method = RequestMethod.GET)
    public void getPbomLineMaintainDetail(HttpServletResponse response){
        List<HzPbomLineMaintainResponseDTO> responseDTOList = hzPbomService.getHzPbomMaintainRecord();
        if(ListUtil.isEmpty(responseDTOList)){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无符合数据！"),response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(true,responseDTOList),response);
    }

}
