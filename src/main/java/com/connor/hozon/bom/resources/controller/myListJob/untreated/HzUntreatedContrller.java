package com.connor.hozon.bom.resources.controller.myListJob.untreated;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeListDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.service.change.HzAuditorChangeService;
import com.connor.hozon.bom.resources.service.change.HzChangeOrderService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.change.HzAuditorChangeRecord;
import sql.pojo.change.HzChangeListRecord;
import sql.pojo.change.HzChangeOrderRecord;

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
public class HzUntreatedContrller {

    @Autowired
    private HzAuditorChangeService hzAuditorChangeService;

    @Autowired
    private HzChangeOrderService hzChangeOrderService;

    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    @Autowired
    private HzChangeListDAO hzChangeListDAO;

    @RequestMapping(value = "ToUntreatedForm",method = RequestMethod.GET)
    public String getToUntreatedFormToPage(Long id,Model model){
        HzChangeOrderRespDTO respDTO = hzChangeOrderService.getHzChangeOrderRecordById(id);
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

        List<HzChangeOrderRespDTO> respDTOs = hzAuditorChangeService.findChangeOrderList(query,record);

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
            list.add(object);
        });
        jsonObject.put("result",list);
        return jsonObject;
    }
}
