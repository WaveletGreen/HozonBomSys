package com.connor.hozon.bom.resources.controller.myListJob.untreated;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.task.HzTasksDao;
import com.connor.hozon.bom.bomSystem.service.task.HzTasksService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzChangeOrderReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.HzAuditorChangeDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeListDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.change.HzAuditorChangeService;
import com.connor.hozon.bom.resources.service.change.HzChangeOrderService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.Result;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.change.HzAuditorChangeRecord;
import sql.pojo.change.HzChangeListRecord;
import sql.pojo.change.HzChangeOrderRecord;
import sql.pojo.task.HzTasks;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/13
 * Time: 16:18
 */
@Controller
@RequestMapping(value = "untreated")
public class HzUntreatedContrller extends BaseController {

    @Autowired
    private HzAuditorChangeService hzAuditorChangeService;

    @Autowired
    private HzChangeOrderService hzChangeOrderService;

    @Autowired
    private HzAuditorChangeDAO hzAuditorChangeDAO;

    @Autowired
    private HzChangeListDAO hzChangeListDAO;

    @Autowired
    private HzTasksDao hzTasksDao;

    /**
     * 获取ChangeOrder表单详细信息-待办事项
     */
    @RequestMapping(value = "ToUntreatedForm",method = RequestMethod.GET)
    public String getToUntreatedFormToPage(Long id,Long auditId,Model model){
        HzChangeOrderRespDTO respDTO = null;
        if(auditId==null){
            respDTO = hzChangeOrderService.getHzChangeOrderRecordById(id);
        }else{
            respDTO = hzChangeOrderService.getHzChangeOrderRespDTOById(id,auditId);
        }
        //HzChangeOrderRespDTO respDTO = hzChangeOrderService.getHzChangeOrderRecordById(id);
        //HzChangeOrderRespDTO respDTO = hzChangeOrderService.getHzChangeOrderRespDTOById(id,auditId);

        //HzChangeOrderByPageQuery query = new HzChangeOrderByPageQuery();
        //HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordById(query,id);
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
        return "myListJob/untreated/untreatedForm";
    }
    /**
     * 获取ChangeOrder表单基本信息列表-待办事项
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

        Page<HzChangeOrderRespDTO> page = hzAuditorChangeService.getHzChangeOrderPageUn(pageQuery,record);
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
            //object.put("auditTime",hzChangeOrderRespDTO.getAuditTime());//审批时间
            object.put("state",hzChangeOrderRespDTO.getState());
            object.put("auditId",hzChangeOrderRespDTO.getAuditId());
            object.put("changeAccepter",hzChangeOrderRespDTO.getChangeAccepter());
            list.add(object);
        });
        jsonObject.put("totalCount",page.getTotalCount());
        jsonObject.put("result",list);
        return jsonObject;

        /*List<HzChangeOrderRespDTO> respDTOs = hzAuditorChangeService.findChangeOrderList(query,record);

        if(ListUtil.isEmpty(respDTOs)){
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
            object.put("state",hzChangeOrderRespDTO.getState());
            object.put("isFromTc",hzChangeOrderRespDTO.getIsFromTc());
            object.put("auditTime",hzChangeOrderRespDTO.getAuditTime());
            list.add(object);
        });
        jsonObject.put("result",list);
        return jsonObject;*/
    }
    /**
     * 待办事项
     * 从TC同步的数据手动修改状态
     */
    @RequestMapping(value = "TCUntreatedUpdate",method = RequestMethod.GET)
    public String getTCUntreatedUpdate(Long id,Model model){
        User user = UserInfo.getUser();
        HzAuditorChangeRecord respDTO = hzAuditorChangeDAO.findByOrderId(id,Long.valueOf(user.getId()));
        //HzChangeOrderRespDTO respDTO = hzChangeOrderService.getHzChangeOrderRecordById(id);
        if(respDTO != null){
            model.addAttribute("data",respDTO);
        }
        return "myListJob/untreated/updateUntreated";
    }

    @RequestMapping(value = "updateAuditResult",method = RequestMethod.POST)
    public void updateChangeFrom(@RequestBody HzAuditorChangeDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO resultRespDTO = null;

        int i = hzAuditorChangeDAO.updateAuditorRecord(reqDTO);
        if(i>0){
            resultRespDTO = WriteResultRespDTO.getSuccessResult();
        }

        //task表也要修改TASK_STATUS为999
        HzTasks hzTasks = new HzTasks();
        hzTasks.setTaskStatus(999);
        hzTasks.setTaskTargetId(Long.parseLong(reqDTO.getOrderId()));
        hzTasksDao.updateTargetStatus(hzTasks);

        //WriteResultRespDTO resultRespDTO = hzChangeOrderService.updateChangeOrderRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(resultRespDTO),resultRespDTO.getErrMsg()),response);
    }

}
