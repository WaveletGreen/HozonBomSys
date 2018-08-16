package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.SetLouReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzLoaRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.query.*;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.epl.HzEPLManageRecord;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.connor.hozon.bom.resources.service.bom.impl.HzPbomServiceImpl.getLevelAndRank;

/**
 * @Author: haozt
 * @Date: 2018/7/18
 * @Description: 查询LOA信息
 */
@Controller
@RequestMapping("/loa")
public class LoaContorller extends BaseController {
    @Autowired
    private HzEbomService hzEbomService;

    @Autowired
    private HzPbomService hzPbomService;

    @Autowired
    private HzMbomService hzMbomService;

    @RequestMapping(value = "ebom",method = RequestMethod.POST)
    @ResponseBody
    public void getHzEbomLoa(@RequestBody HzLoaQuery query, HttpServletResponse response){
        if(query.getProjectId() == null || query.getPuid() == null || query.getPuid() == "" || query.getProjectId() == ""){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return;
        }
         JSONObject jsonObject = new JSONObject();
         HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
         hzEbomTreeQuery.setPuid(query.getPuid());
         hzEbomTreeQuery.setProjectId(query.getProjectId());
         List<HzLoaRespDTO> loaRespDTOS = new ArrayList<>();
         List<HzEPLManageRecord> recordList = hzEbomService.findCurrentBomChildren(hzEbomTreeQuery);//子
        List<HzEPLManageRecord> hzEPLManageRecords = new ArrayList<>();
        HzEbomRespDTO respDTO = new HzEbomRespDTO();
         if(ListUtil.isNotEmpty(recordList)){
           HzEPLManageRecord record = recordList.get(0);
           int  length = record.getLineIndex().split("\\.").length+1;
           for(HzEPLManageRecord eplManageRecord:recordList){
               if(eplManageRecord.getLineIndex().split("\\.").length == length){
                   hzEPLManageRecords.add(eplManageRecord);
               }
           }
           String parentId = record.getParentUid();
           if(parentId != null)
           respDTO = hzEbomService.fingEbomById(parentId,query.getProjectId());//父
         }
        hzEPLManageRecords.forEach(record -> {
             HzLoaRespDTO hzLoaRespDTO = new HzLoaRespDTO();
             Integer is2Y = record.getIs2Y();
             Integer hasChildren = record.getIsHas();
             String index = record.getLineIndex();
             String[] strings = getLevelAndRank(index, is2Y, hasChildren);
             hzLoaRespDTO.setChildLevel(strings[0]);
             hzLoaRespDTO.setChildLineId(record.getLineID());
             hzLoaRespDTO.setChildName(record.getpBomLinePartName());
             loaRespDTOS.add(hzLoaRespDTO);
         });

        if(respDTO .getJsonArray() !=null){
            HzLoaRespDTO hzLoaRespDTO = new HzLoaRespDTO();
            JSONObject object = (JSONObject) respDTO.getJsonArray().get(0);
            hzLoaRespDTO.setParentLevel(object.getString("level"));
            hzLoaRespDTO.setParentLineId(object.getString("lineId"));
            hzLoaRespDTO.setParentName(object.getString("pBomLinePartName"));

            jsonObject.put("parent",hzLoaRespDTO);
        }else {
            jsonObject.put("parent",new HzLoaRespDTO());
        }
        jsonObject.put("child",loaRespDTOS);
        writeAjaxJSONResponse(ResultMessageBuilder.build(jsonObject),response);
    }


    @RequestMapping(value = "pbom",method = RequestMethod.POST)
    public void getHzPbomLoa(@RequestBody  HzLoaQuery query, HttpServletResponse response){
        if(query.getProjectId() == null || query.getPuid() == null || query.getPuid() == "" || query.getProjectId() == ""){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
        hzPbomTreeQuery.setPuid(query.getPuid());
        hzPbomTreeQuery.setProjectId(query.getProjectId());
        List<HzLoaRespDTO> loaRespDTOS = new ArrayList<>();
        List<HzPbomLineRecord> recordList = hzPbomService.getHzPbomLineTree(hzPbomTreeQuery);//子
        List<HzPbomLineRecord> hzPbomLineRecordList = new ArrayList<>();
        HzPbomLineRespDTO respDTO = new HzPbomLineRespDTO();
        if(ListUtil.isNotEmpty(recordList)){
            HzPbomLineRecord record = recordList.get(0);
            int  length = record.getLineIndex().split("\\.").length+1;
            for(HzPbomLineRecord hzPbomLineRecord:recordList){
                if(hzPbomLineRecord.getLineIndex().split("\\.").length == length){
                    hzPbomLineRecordList.add(hzPbomLineRecord);
                }
            }
            String parentId = record.getParentUid();
            if(parentId != null)
            respDTO = hzPbomService.getHzPbomByPuid(query.getProjectId(),parentId);//父
        }
        hzPbomLineRecordList.forEach(record -> {
            HzLoaRespDTO hzLoaRespDTO = new HzLoaRespDTO();
            Integer is2Y = record.getIs2Y();
            Integer hasChildren = record.getIsHas();
            String lineIndex = record.getLineIndex();
            String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
            hzLoaRespDTO.setChildLevel(strings[0]);
            hzLoaRespDTO.setChildLineId(record.getLineId());
            hzLoaRespDTO.setChildName(record.getpBomLinePartName());
            loaRespDTOS.add(hzLoaRespDTO);
        });

        if(respDTO !=null){
            HzLoaRespDTO hzLoaRespDTO = new HzLoaRespDTO();
            hzLoaRespDTO.setParentLevel(respDTO.getLevel());
            hzLoaRespDTO.setParentLineId(respDTO.getLineId());
            hzLoaRespDTO.setParentName(respDTO.getpBomLinePartName());
            jsonObject.put("parent",hzLoaRespDTO);
        }else {
            jsonObject.put("parent",new HzLoaRespDTO());
        }
        jsonObject.put("child",loaRespDTOS);
        writeAjaxJSONResponse(ResultMessageBuilder.build(jsonObject),response);
    }

    @RequestMapping(value = "mbom",method = RequestMethod.POST)
    public void getHzMbomLoa(@RequestBody HzLoaQuery query, HttpServletResponse response){
        if(query.getProjectId() == null || query.getPuid() == null || query.getPuid() == "" || query.getProjectId() == ""){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"),response);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        HzMbomTreeQuery hzMbomTreeQuery = new HzMbomTreeQuery();
        hzMbomTreeQuery.setPuid(query.getPuid());
        hzMbomTreeQuery.setProjectId(query.getProjectId());
        List<HzLoaRespDTO> loaRespDTOS = new ArrayList<>();
        List<HzMbomLineRecord> recordList = hzMbomService.getHzMbomTree(hzMbomTreeQuery);//子
        HzMbomRecordRespDTO respDTO = new HzMbomRecordRespDTO();
        List<HzMbomLineRecord> recordArrayList = new ArrayList<>();
        if(ListUtil.isNotEmpty(recordList)){
            HzMbomLineRecord record = recordList.get(0);
            int  length = record.getLineIndex().split("\\.").length+1;
            for(HzMbomLineRecord hzMbomLineRecord:recordList){
                if(hzMbomLineRecord.getLineIndex().split("\\.").length == length){
                    recordArrayList.add(hzMbomLineRecord);
                }
            }
            String parentId = record.getParentUid();
            if(parentId != null){
                respDTO = hzMbomService.findHzMbomByPuid(query.getProjectId(),parentId);//父
            }
        }
        recordArrayList.forEach(record -> {
            HzLoaRespDTO hzLoaRespDTO = new HzLoaRespDTO();
            Integer is2Y = record.getIs2Y();
            Integer hasChildren = record.getIsHas();
            String lineIndex = record.getLineIndex();
            String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
            hzLoaRespDTO.setChildLevel(strings[0]);
            hzLoaRespDTO.setChildLineId(record.getLineId());
            hzLoaRespDTO.setChildName(record.getpBomLinePartName());
            loaRespDTOS.add(hzLoaRespDTO);
        });

        if(respDTO !=null){
            HzLoaRespDTO hzLoaRespDTO = new HzLoaRespDTO();
            hzLoaRespDTO.setParentLevel(respDTO.getLevel());
            hzLoaRespDTO.setParentLineId(respDTO.getLineId());
            hzLoaRespDTO.setParentName(respDTO.getpBomLinePartName());
            jsonObject.put("parent",hzLoaRespDTO);
        }else {
            jsonObject.put("parent",new HzLoaRespDTO());
        }
        jsonObject.put("child",loaRespDTOS);
        writeAjaxJSONResponse(ResultMessageBuilder.build(jsonObject),response);
    }

    @RequestMapping(value = "setLou",method = RequestMethod.POST)
    public void setBomAsLou(@RequestBody SetLouReqDTO reqDTO,HttpServletResponse response){
        writeAjaxJSONResponse(ResultMessageBuilder.build("111"),response);
    }



    
}
