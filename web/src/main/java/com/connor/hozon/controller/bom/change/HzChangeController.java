package com.connor.hozon.controller.bom.change;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.controller.bom.BaseController;
import com.connor.hozon.resources.domain.dto.request.EditHzChangeOrderReqDTO;
import com.connor.hozon.resources.domain.dto.response.HzChangeOrderRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.change.HzChangeOrderService;
import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.resources.util.Result;
import com.connor.hozon.resources.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description:
 */
@Controller
@RequestMapping(value = "change")
public class HzChangeController extends BaseController {

    @Autowired
    private HzChangeOrderService hzChangeOrderService;

    @RequestMapping(value = "addPage",method = RequestMethod.GET)
    public String getAddChangeFormToPage(){

        return "change/changeForm/addChangeForm";
    }
    @RequestMapping(value = "updatePage",method = RequestMethod.GET)
    public String getUpdateChangeFormToPage(Long id, Model model){
        HzChangeOrderRespDTO respDTO = hzChangeOrderService.getHzChangeOrderRecordById(id);
        if(respDTO != null){
            model.addAttribute("data",respDTO);
        }
        return "change/changeForm/updateChangeForm";
    }
    @RequestMapping(value = "ToChangeOrder",method = RequestMethod.GET)
    public String toChangeOrderPage(Long id,Model model){
        HzChangeOrderRespDTO respDTO = hzChangeOrderService.getHzChangeOrderRecordById(id);
        if(respDTO != null){
            model.addAttribute("data",respDTO);
        }
        return "change/changeOrder/changeOrder";
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public void addChangeFrom(@RequestBody EditHzChangeOrderReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO resultRespDTO = hzChangeOrderService.insertChangeOrderRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(resultRespDTO),resultRespDTO.getErrMsg()),response);
    }



    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void updateChangeFrom(@RequestBody EditHzChangeOrderReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO resultRespDTO = hzChangeOrderService.updateChangeOrderRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(resultRespDTO),resultRespDTO.getErrMsg()),response);
    }

    @RequestMapping(value = "delete",method = RequestMethod.DELETE)
    public void deleteChangeFrom(Long id, HttpServletResponse response){
        WriteResultRespDTO resultRespDTO = hzChangeOrderService.deleteChangeOrderById(id);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(resultRespDTO),resultRespDTO.getErrMsg()),response);
    }


    @RequestMapping(value = "exist",method = RequestMethod.POST)
    public void deleteChangeFrom(String changeNo, HttpServletResponse response){
        WriteResultRespDTO resultRespDTO = hzChangeOrderService.changeNoExist(changeNo.trim());
        JSONObject object = new JSONObject();
        if(WriteResultRespDTO.isSuccess(resultRespDTO)){
            object.put("valid",true);
        }else {
            object.put("valid",false);
        }
        toJSONResponse(object,response);
    }

    /**
     * 获取变更表单列表
     * @param query
     */
    @RequestMapping(value = "order/list",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getHzChangeOrderList(HzChangeOrderByPageQuery query){
        if(StringUtil.isEmpty(query.getProjectId())){
            return new JSONObject();
        }
        Page<HzChangeOrderRespDTO> page = hzChangeOrderService.getHzChangeOrderPage(query);
        if(ListUtils.isEmpty(page.getResult())){
            return new JSONObject();
        }
        List<HzChangeOrderRespDTO> respDTOS = page.getResult();
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        respDTOS.forEach(hzChangeOrderRespDTO -> {
            JSONObject object = new JSONObject();
            object.put("resource",hzChangeOrderRespDTO.getOrderResource());
            object.put("isFromTc",hzChangeOrderRespDTO.getIsFromTc());
            object.put("status",hzChangeOrderRespDTO.getStatus());
            object.put("changeNo",hzChangeOrderRespDTO.getChangeNo());
            object.put("changeType",hzChangeOrderRespDTO.getChangeType());
            object.put("createName",hzChangeOrderRespDTO.getCreateName());
            object.put("auditor",hzChangeOrderRespDTO.getAuditor());
            object.put("originator",hzChangeOrderRespDTO.getOriginator());
            object.put("id",hzChangeOrderRespDTO.getId());
            object.put("marketType",hzChangeOrderRespDTO.getMarketType());
            object.put("createNo",hzChangeOrderRespDTO.getChangeNo());
            object.put("tel",hzChangeOrderRespDTO.getTel());
            object.put("state",hzChangeOrderRespDTO.getState());
            object.put("relationChangeNo",hzChangeOrderRespDTO.getRelationChangeNo());
            object.put("originTime",hzChangeOrderRespDTO.getOriginTime());
            object.put("createTime",hzChangeOrderRespDTO.getCreateTime());
            object.put("remark",hzChangeOrderRespDTO.getRemark());
            object.put("projectStage",hzChangeOrderRespDTO.getProjectStage());
            object.put("deptName",hzChangeOrderRespDTO.getDeptName());
            list.add(object);
        });
        jsonObject.put("totalCount",page.getTotalCount());
        jsonObject.put("result",list);
        return jsonObject;
    }

    /**
     * 查询变更表单是否关联变更数据
     * @param orderId
     * @param response
     */
    @RequestMapping(value = "related/data",method = RequestMethod.GET)
    public void changeOrderRelatedData(Long orderId,HttpServletResponse response){
        boolean b = hzChangeOrderService.changeOrderRelatedChangeData(orderId);
        if(b){
            toJSONResponse(Result.build(true,"已关联变更数据"),response);
            return;
        }
        toJSONResponse(Result.build(false,"空表单,当前变更表单未找到变更数据!"),response);
    }

}
