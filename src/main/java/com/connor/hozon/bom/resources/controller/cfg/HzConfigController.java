package com.connor.hozon.bom.resources.controller.cfg;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dbdo.config.HzCfg0Record;
import com.connor.hozon.bom.resources.dto.request.FindHzFeatureConfigReqDTO;
import com.connor.hozon.bom.resources.service.cfg.HzConfigRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by haozt on 2018/5/22
 */
@Controller
@RequestMapping(value = "/config")
public class HzConfigController extends BaseController {

    @Autowired
    private HzConfigRecordService hzConfigRecordService;
    /**
     * 获取特性配置表
     */

    @RequestMapping(value = "/getFeature",method = RequestMethod.GET)
    public void getHzFeatureConfig(FindHzFeatureConfigReqDTO dto, HttpServletResponse response){
        if(StringUtil.isEmpty(dto.getpCfg0MainItemPuid())){
           writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
        }
        List<HzCfg0Record> recordList = hzConfigRecordService.getHzFeatureConfig(dto);
        if(ListUtil.isNotEmpty(recordList)){
            writeAjaxJSONResponse(ResultMessageBuilder.build(true,recordList),response);
        }else{
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无数据！"),response);
        }
    }

}
