package com.connor.hozon.bom.resources.controller.change.codpwo;

import cn.net.connor.hozon.dao.pojo.integration.HzBreakPoint;
import cn.net.connor.hozon.dao.query.change.breakPoint.BreakPointQuery;
import com.connor.hozon.bom.resources.service.change.HzBreakPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Map<String,Object> getCodpwoFromList(BreakPointQuery queryBase){
        Map<String,Object> result=new HashMap<>();
        queryBase.setSort(HzBreakPoint.reflectToDBField(queryBase.getSort()));
        List<HzBreakPoint>  list=hzBreakPointService.selectByQueryObject(queryBase);
        //int totalCount = hzBreakPointService.selectByQueryObject().size();
        result.put("result",list);//当前页
        result.put("totalCount",hzBreakPointService.count());//断点信息总数
        return result;
    }

}
