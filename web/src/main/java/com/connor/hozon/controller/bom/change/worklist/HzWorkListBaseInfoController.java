/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.controller.bom.change.worklist;

import cn.net.connor.hozon.common.util.ListUtils;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.controller.bom.BaseController;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzWorkListBasicInfoRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzWorkListBasicInfoQuery;
import com.connor.hozon.service.change.data.HzAuditorChangeService;
import com.connor.hozon.service.change.data.HzWorkListBasicInfoService;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.net.connor.hozon.dao.pojo.change.change.HzAuditorChangeRecord;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/worklist/base")
public class HzWorkListBaseInfoController extends BaseController {
    @Autowired
    private HzWorkListBasicInfoService hzWorkListBasicInfoService;

    @Autowired
    private HzAuditorChangeService hzAuditorChangeService;

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
    public JSONObject getChangeOrderList(HzChangeOrderByPageQuery query,HzAuditorChangeRecord record){
        if(StringUtil.isEmpty(query.getProjectId())){
            return new JSONObject();
        }

        List<HzChangeOrderRespDTO> respDTOs = hzAuditorChangeService.findChangeOrderList(query,record);


        //List<HzWorkListBasicInfoRespDTO> respDTOs = hzWorkListBasicInfoService.findHzWorkList(query);
        if(ListUtils.isEmpty(respDTOs)){
            return new JSONObject();
        }
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        respDTOs.forEach(hzChangeOrderRespDTO -> {
            JSONObject object = new JSONObject();
            object.put("changeNo",hzChangeOrderRespDTO.getChangeNo());
            object.put("originTime",hzChangeOrderRespDTO.getOriginTime());
            object.put("id",hzChangeOrderRespDTO.getId());
            object.put("deptName",hzChangeOrderRespDTO.getDeptName());
            object.put("changeType",hzChangeOrderRespDTO.getChangeType());
            object.put("originator",hzChangeOrderRespDTO.getOriginator());
            //object.put("dept",hzChangeOrderRespDTO.getDept());
            list.add(object);
        });
        jsonObject.put("result",list);
        return jsonObject;
    }
}
