package com.connor.hozon.bom.resources.controller.myListJob.changeList;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeListRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeListByPageQuery;
import com.connor.hozon.bom.resources.service.change.HzAuditorChangeService;
import com.connor.hozon.bom.resources.service.change.HzChangeListService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取从TC同步到BOM端的变更单被引用清单
 */
@Controller
@RequestMapping(value = "changeList")
public class HzChangeListContrller {
    @Autowired
    private HzChangeListService hzChangeListService;
    /**
     * 获取ChangeList表单引用信息列表
     * @param query
     */
    @RequestMapping(value = "infoList",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getChangeOrderList(HzChangeListByPageQuery query){

        List<HzChangeListRespDTO> respDTOs = hzChangeListService.findChangeList(query);

        if(ListUtil.isEmpty(respDTOs)){
            return new JSONObject();
        }
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        respDTOs.forEach(hzChangeListRespDTO -> {
            JSONObject object = new JSONObject();
            object.put("id",hzChangeListRespDTO.getId());
            object.put("formId",hzChangeListRespDTO.getFormId());
            object.put("itemId",hzChangeListRespDTO.getItemId());
            object.put("itemRevision",hzChangeListRespDTO.getItemRevision());
            list.add(object);
        });
        jsonObject.put("result",list);
        return jsonObject;
    }

}
