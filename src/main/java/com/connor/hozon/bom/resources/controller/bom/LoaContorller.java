package com.connor.hozon.bom.resources.controller.bom;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.query.HzEbomTreeQuery;
import com.connor.hozon.bom.resources.query.HzLoaQuery;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sql.pojo.epl.HzEPLManageRecord;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/7/18
 * @Description: 查询LOA信息
 */
@Controller
@RequestMapping("/loa")
public class LoaContorller extends BaseController {
    @Autowired
    private HzEbomService hzEbomService;

    @Autowired
    private HzPbomService hzPbomService;

    @Autowired
    private HzMbomService hzMbomService;

    @RequestMapping(value = "ebom",method = RequestMethod.GET)
    public void getHzEbomLoa(@RequestBody HzLoaQuery query, HttpServletResponse response){
        HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
        hzEbomTreeQuery.setPuid(query.getPuid());
        hzEbomTreeQuery.setProjectId(query.getProjectId());
       List<HzEPLManageRecord> recordList = hzEbomService.findCurrentBomChildren(hzEbomTreeQuery);
       if(ListUtil.isNotEmpty(recordList)){
           HzEPLManageRecord record = recordList.get(0);
           String parentId = record.getParentUid();
           HzEbomRespDTO respDTO = hzEbomService.fingEbomById(parentId,query.getProjectId());
       }
    }
    @RequestMapping(value = "pbom",method = RequestMethod.GET)
    public void getHzPbomLoa(@RequestBody HzLoaQuery query, HttpServletResponse response){

    }
    @RequestMapping(value = "mbom",method = RequestMethod.GET)
    public void getHzMbomLoa(@RequestBody HzLoaQuery query, HttpServletResponse response){

    }
}
