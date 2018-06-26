package com.connor.hozon.bom.resources.controller.epl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.FindHzEPLRecordReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by haozt on 2018/06/05
 */
@Controller
@RequestMapping(value = "/epl")
public class HzEPLController extends BaseController {

    @Autowired
    private HzEPLManageRecordService hzEPLManageRecordService;

    @RequestMapping(value = "record/page",method = RequestMethod.GET)
    public void getHzEplRecordByPage(FindHzEPLRecordReqDTO recordReqDTO, HttpServletResponse response){
        Page<HzEPLRecordRespDTO> recordRespDTOPage = hzEPLManageRecordService.getHzEPLRecordForPage(recordReqDTO);
        List<HzEPLRecordRespDTO> recordRespDTOS =  recordRespDTOPage.getResult();
        if (ListUtil.isEmpty(recordRespDTOS)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"暂无数据",new Page<>(recordReqDTO.getPage(),recordReqDTO.getLimit(),0)),response);

        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(recordRespDTOPage),response);
    }


    @RequestMapping(value = "record",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHzEplRecord(FindHzEPLRecordReqDTO recordReqDTO){
        Page<HzEPLRecordRespDTO> recordRespDTOPage = hzEPLManageRecordService.getHzEPLRecordForPage(recordReqDTO);
        Map<String, Object> ret = new HashMap<>();
        if(recordRespDTOPage == null){
            return  ret;
        }
        List<HzEPLRecordRespDTO> recordRespDTOS =  recordRespDTOPage.getResult();

        if (ListUtil.isEmpty(recordRespDTOS)) {
            return ret;
        }
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        JSONArray array = recordRespDTOS.get(0).getJsonArray();
        for(int i =0;i<array.size();i++){
            JSONObject object = array.getJSONObject(i);
            map = object;
            list.add(map);
        }
        ret.put("totalCount", recordRespDTOPage.getTotalCount());
        ret.put("result", list);
        return ret;
        }

    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void getEplTitle(FindHzEPLRecordReqDTO recordReqDTO,HttpServletResponse response){
        if(recordReqDTO.getProjectId()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"), response);
            return;
        }
        JSONArray array = hzEPLManageRecordService.getEPLTittle(recordReqDTO);
        if(array==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"网络错误！"), response);
            return;
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(array), response);
    }

}
