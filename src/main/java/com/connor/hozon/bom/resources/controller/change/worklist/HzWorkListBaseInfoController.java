package com.connor.hozon.bom.resources.controller.change.worklist;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.response.HzWorkListBasicInfoRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzWorkListBasicInfoQuery;
import com.connor.hozon.bom.resources.service.change.HzWorkListBasicInfoService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/worklist/base")
public class HzWorkListBaseInfoController extends BaseController {
    @Autowired
    private HzWorkListBasicInfoService hzWorkListBasicInfoService;

    /**
     * 获取WorkList表单基本信息
     * @param query
     * @param
     */
    @RequestMapping(value = "info",method = RequestMethod.GET)
    public String getHzWorkListBasicInfo(HzWorkListBasicInfoQuery query, Model model){
        if(query.getId() == null){
            return"" ;
        }
        HzWorkListBasicInfoRespDTO respDTO = hzWorkListBasicInfoService.findHzWorkListBasicInfo(query);
        if(respDTO == null){
            return "";
        }
        model.addAttribute("data",respDTO);
        return "changeManage/ewo/ewoBasicInformation";//html换*******

    }

    /**
     * 获取WorkList表单基本信息列表-待办事项
     * @param query
     */
    @RequestMapping(value = "infoList",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getHzWorkListBasicInfoList(HzWorkListBasicInfoQuery query){
        if(StringUtil.isEmpty(query.getProjectId())){
            return new JSONObject();
        }
        List<HzWorkListBasicInfoRespDTO> respDTOs = hzWorkListBasicInfoService.findHzWorkList(query);
        if(ListUtil.isEmpty(respDTOs)){
            return new JSONObject();
        }
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        respDTOs.forEach(hzWorkListBasicInfoRespDTO -> {
            JSONObject object = new JSONObject();
            object.put("changeNum",hzWorkListBasicInfoRespDTO.getChangeNum());
            object.put("launchTime",hzWorkListBasicInfoRespDTO.getLaunchTime());
            object.put("id",hzWorkListBasicInfoRespDTO.getId());
            object.put("launcherDep",hzWorkListBasicInfoRespDTO.getLauncherDep());
            object.put("changeType",hzWorkListBasicInfoRespDTO.getChangeType());
            object.put("launcher",hzWorkListBasicInfoRespDTO.getLauncher());
            //object.put("dept",hzWorkListBasicInfoRespDTO.getDept());
            list.add(object);
        });
        jsonObject.put("result",list);
        return jsonObject;
    }
}
