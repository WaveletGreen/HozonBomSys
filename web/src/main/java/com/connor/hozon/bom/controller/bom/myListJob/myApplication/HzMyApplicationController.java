package com.connor.hozon.bom.controller.bom.myListJob.myApplication;

import cn.net.connor.hozon.common.util.ListUtils;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeListDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.change.HzApplicationChangeService;
import com.connor.hozon.bom.resources.service.change.HzChangeOrderService;
import com.connor.hozon.bom.resources.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.net.connor.hozon.dao.pojo.change.change.HzApplicantChangeRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzAuditorChangeRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeListRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/13
 * Time: 16:21
 */
@Controller
@RequestMapping(value = "myApplication")
public class HzMyApplicationController {

    @Autowired
    private HzApplicationChangeService hzApplicationChangeService;

    @Autowired
    private HzChangeOrderService hzChangeOrderService;

    @Autowired
    private HzAuditorChangeDAO hzAuditorChangeDAO;

    @Autowired
    private HzChangeListDAO hzChangeListDAO;

    @RequestMapping(value = "ToMyApplicationForm",method = RequestMethod.GET)
    public String getToMyApplicationFormToPage(Long id, Long auditId,Model model){
        HzChangeOrderRespDTO respDTO = hzChangeOrderService.getHzChangeOrderRecordById(id);
        HzAuditorChangeRecord record = new HzAuditorChangeRecord();
        record.setAuditorId(auditId);
        List<HzAuditorChangeRecord> infos =  hzAuditorChangeDAO.findAuditorListById(auditId);

        //List<HzAuditorChangeRecord> infos =  hzAuditorChangeDAO.findAuditorList2(record);
        for(int i=0;i<infos.size();i++){
            if(id.equals(infos.get(i).getOrderId())){
                //infos.get(i).setAuditTime(DateUtil.formatTimestampDate(infos.get(i).getAuditTime()));
                model.addAttribute("timeString",DateUtil.formatTimestampDate(infos.get(i).getAuditTime()));
                model.addAttribute("result",infos.get(i));
                break;
            }else{
                if(i==infos.size()-1){
                    //我的申请还未审批情况
                    model.addAttribute("timeString","");
                    model.addAttribute("result",record);
                }
            }
        }
        List<HzChangeListRecord> changeList = hzChangeListDAO.findChangeList(respDTO.getChangeNo());

        if(changeList.size()>0){
            List<Map<String,String>> resultMaps = new ArrayList<>();
            for(HzChangeListRecord hzChangeListRecord : changeList){
                Map<String,String> map = new HashMap<>();
                map.put("itemId",hzChangeListRecord.getItemId());
                map.put("itemRevision",hzChangeListRecord.getItemRevision());
                resultMaps.add(map);
            }
            model.addAttribute("changeList",resultMaps);
        }
        if(respDTO != null){
            model.addAttribute("data",respDTO);
        }
        return "myListJob/myApplication/myApplicationForm";
    }

    /**
     * 获取ChangeOrder表单基本信息列表-我的申请
     * @param query
     */
    @RequestMapping(value = "infoList",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getChangeOrderList(HzChangeOrderByPageQuery query, HzApplicantChangeRecord record){
//        if(StringUtil.isEmpty(query.getProjectId())){
//            return new JSONObject();
//        }
        HzChangeOrderByPageQuery pageQuery = query;
        try {
            pageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        }catch (Exception e){
        }

        Page<HzChangeOrderRespDTO> page = hzApplicationChangeService.getHzChangeOrderPage(pageQuery,record);
        if(ListUtils.isEmpty(page.getResult())){
            return new JSONObject();
        }

        List<HzChangeOrderRespDTO> respDTOS = page.getResult();
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        respDTOS.forEach(hzChangeOrderRespDTO -> {
            JSONObject object = new JSONObject();
            object.put("createName",hzChangeOrderRespDTO.getCreateName());
            object.put("marketType",hzChangeOrderRespDTO.getMarketType());
            object.put("createNo",hzChangeOrderRespDTO.getChangeNo());
            object.put("tel",hzChangeOrderRespDTO.getTel());
            object.put("relationChangeNo",hzChangeOrderRespDTO.getRelationChangeNo());
            object.put("createTime",hzChangeOrderRespDTO.getCreateTime());
            object.put("remark",hzChangeOrderRespDTO.getRemark());
            object.put("projectState",hzChangeOrderRespDTO.getProjectStage());

            object.put("changeNo",hzChangeOrderRespDTO.getChangeNo());
            object.put("originTime",hzChangeOrderRespDTO.getOriginTime());
            object.put("id",hzChangeOrderRespDTO.getId());
            object.put("deptName",hzChangeOrderRespDTO.getDeptName());
            object.put("changeType",hzChangeOrderRespDTO.getChangeType());
            object.put("originator",hzChangeOrderRespDTO.getOriginator());
            object.put("projectName",hzChangeOrderRespDTO.getProjectName());
            object.put("source",hzChangeOrderRespDTO.getSource());
            object.put("auditTime",hzChangeOrderRespDTO.getAuditTime());
            object.put("state",hzChangeOrderRespDTO.getState());
            object.put("auditId",hzChangeOrderRespDTO.getAuditId());
            list.add(object);
        });
        jsonObject.put("totalCount",page.getTotalCount());
        jsonObject.put("result",list);
        return jsonObject;

        /*List<HzChangeOrderRespDTO> respDTOs = hzApplicationChangeService.findChangeOrderList(query,record);
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
            object.put("projectName",hzChangeOrderRespDTO.getProjectName());
            object.put("source",hzChangeOrderRespDTO.getSource());
            object.put("auditTime",hzChangeOrderRespDTO.getAuditTime());
            object.put("state",hzChangeOrderRespDTO.getState());
            object.put("auditId",hzChangeOrderRespDTO.getAuditId());
            list.add(object);
        });
        jsonObject.put("result",list);
        return jsonObject;*/
    }
}
