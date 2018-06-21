package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * \* User: xulf
 * \* Date: 2018/6/4
 * \* Time: 13:01
 * \
 */
@Controller
@RequestMapping(value = "/ebom")
public class HzEbomController extends BaseController {

    @Autowired
    private HzEbomService hzEbomService;

    @RequestMapping(value = "/ebomTitle",method = RequestMethod.GET)
    public void getEbomTitle(FindForPageReqDTO recordReqDTO, HttpServletResponse response) {
        if(recordReqDTO.getProjectId()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"), response);
        }
        JSONArray array = hzEbomService.getEbomTitle(recordReqDTO);
        if(array==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"网络错误！"), response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(array), response);
    }

    @RequestMapping(value = "getEBom/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEbomList(FindForPageReqDTO reqDTO) {
        Page<HzEbomRespDTO> recordRespDTOPage = hzEbomService.getHzEbomPage(reqDTO);
        Map<String, Object> ret = new HashMap<>();
        if(recordRespDTOPage == null){
            return ret;
        }
        List<HzEbomRespDTO> recordRespDTOS =  recordRespDTOPage.getResult();
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

    /**
     * 获取当前登录用户信息
     */
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public void getUser(HttpServletResponse  response){
        User user = UserInfo.getUser();
        writeAjaxJSONResponse(ResultMessageBuilder.build(user),response);
        System.out.println(JSON.toJSONString(user));
    }
}