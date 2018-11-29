package com.connor.hozon.bom.resources.controller.myListJob.processed;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.domain.dto.request.HzAuditorChangeDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeListDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.change.HzAuditorChangeService;
import com.connor.hozon.bom.resources.service.change.HzChangeOrderService;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.change.HzAuditorChangeRecord;
import sql.pojo.change.HzChangeListRecord;
import sql.pojo.change.HzChangeOrderRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/13
 * Time: 16:19
 */
@Controller
@RequestMapping(value = "processed")
public class HzProcessedContrller {

    @Autowired
    private HzAuditorChangeService hzAuditorChangeService;

    @Autowired
    private HzChangeOrderService hzChangeOrderService;

    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    @Autowired
    private HzAuditorChangeDAO hzAuditorChangeDAO;

    @Autowired
    private HzChangeListDAO hzChangeListDAO;

    /**
     * 获取ChangeOrder表单详细信息-已处理事项
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "ToProcessedForm",method = RequestMethod.GET)
    public String getToProcessedFormToPage(Long id, Long auditId, Model model) {
        HzChangeOrderRespDTO respDTO = hzChangeOrderService.getHzChangeOrderRecordById(id);
        HzAuditorChangeRecord record = new HzAuditorChangeRecord();

        record.setAuditorId(auditId);
        List<HzAuditorChangeRecord> infos =  hzAuditorChangeDAO.findAuditorListById(auditId);//

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
            for(int i=0;i<infos.size();i++){
                if(id==infos.get(i).getOrderId()){
                    //infos.get(i).setAuditTime(DateUtil.formatTimestampDate(infos.get(i).getAuditTime()));
                    model.addAttribute("timeString",DateUtil.formatTimestampDate(infos.get(i).getAuditTime()));
                    model.addAttribute("result",infos.get(i));
                }
            }
        }
        return "myListJob/processed/processedForm";
    }

    /**
     * 获取ChangeOrder表单信息列表-已处理事项
     * @param query
     */
    @RequestMapping(value = "infoList",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getChangeOrderList(HzChangeOrderByPageQuery query, HzAuditorChangeRecord record){
//        if(StringUtil.isEmpty(query.getProjectId())){
//            return new JSONObject();
//        }

        HzChangeOrderByPageQuery pageQuery = query;
        try {
            pageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        }catch (Exception e){
        }

        Page<HzChangeOrderRespDTO> page = hzAuditorChangeService.getHzChangeOrderPagePr(pageQuery,record);
        if(ListUtil.isEmpty(page.getResult())){
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

        /*List<HzChangeOrderRespDTO> respDTOs = hzAuditorChangeService.findChangeOrderList2(query,record);
        if(ListUtil.isEmpty(respDTOs)){
            return new JSONObject();
        }
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        respDTOs.forEach(hzChangeOrderRespDTO -> {
            JSONObject object = new JSONObject();
            object.put("source",hzChangeOrderRespDTO.getSource());
            object.put("changeNo",hzChangeOrderRespDTO.getChangeNo());
            object.put("originTime",hzChangeOrderRespDTO.getOriginTime());
            object.put("id",hzChangeOrderRespDTO.getId());
            object.put("deptName",hzChangeOrderRespDTO.getDeptName());
            object.put("changeType",hzChangeOrderRespDTO.getChangeType());
            object.put("originator",hzChangeOrderRespDTO.getOriginator());
            object.put("projectName",hzChangeOrderRespDTO.getProjectName());
            object.put("state",hzChangeOrderRespDTO.getState());
            object.put("auditTime",hzChangeOrderRespDTO.getAuditTime());
            object.put("auditId",hzChangeOrderRespDTO.getAuditId());
            list.add(object);
        });
        jsonObject.put("result",list);
        return jsonObject;*/
    }
}
