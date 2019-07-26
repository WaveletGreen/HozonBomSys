package com.connor.hozon.bom.resources.controller.change.codpwo;

import com.connor.hozon.bom.common.base.entity.QueryBase;
import com.connor.hozon.bom.resources.service.change.HzBreakPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sql.pojo.integration.HzBreakPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/codpwo")
public class HZCodpwoController {
    @Autowired
    HzBreakPointService hzBreakPointService;

    @RequestMapping(value = "codpwoFromList" ,method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getCodpwoFromList(QueryBase queryBase){
        Map<String,Object> result=new HashMap<>();
        queryBase.setSort(HzBreakPoint.reflectToDBField(queryBase.getSort()));
        List<HzBreakPoint>  list=hzBreakPointService.selectAll(queryBase);
        //int totalCount = hzBreakPointService.selectAll().size();
        result.put("result",list);//当前页
        result.put("totalCount",hzBreakPointService.doTellMeHowManyOfIt());//断点信息总数
        return result;
    }

}
