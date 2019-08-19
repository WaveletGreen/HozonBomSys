/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.controller.bom.bom;

import cn.net.connor.hozon.common.util.ListUtils;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.controller.bom.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.SetLouReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.*;
import com.connor.hozon.bom.resources.domain.query.*;
import cn.net.connor.hozon.services.common.enumtype.MbomTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.service.bom.bom.HzEBOMReadService;
import com.connor.hozon.service.bom.bom.HzEBOMWriteService;
import com.connor.hozon.service.bom.bom.HzMbomService;
import com.connor.hozon.service.bom.bom.HzPbomService;
import com.connor.hozon.bom.resources.util.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzMbomLineRecord;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzPbomLineRecord;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.connor.hozon.bom.resources.domain.model.HzBomSysFactory.getLevelAndRank;

/**
 * @Author: haozt
 * @Date: 2018/7/18
 * @Description: 查询/设置 -> LOA/LOU信息
 */
@Controller
@RequestMapping("/loa")
public class LouaContorller extends BaseController {
    @Autowired
    private HzEBOMReadService hzEBOMReadService;

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    private HzEBOMWriteService hzEBOMWriteService;

    @Autowired
    private HzPbomService hzPbomService;

    @Autowired
    private HzMbomService hzMbomService;

    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;
    @RequestMapping(value = "ebom",method = RequestMethod.POST)
    @ResponseBody
    public void getHzEbomLoa(@RequestBody HzLouaQuery query, HttpServletResponse response){
        if(StringUtils.isBlank(query.getProjectId()) || StringUtils.isBlank(query.getPuid())) {
            toJSONResponse(Result.build(false, "非法参数！"), response);
            return;
        }
        toJSONResponse(Result.build(getLOAForEBOM(query)),response);
    }


    @RequestMapping(value = "pbom",method = RequestMethod.POST)
    public void getHzPbomLoa(@RequestBody HzLouaQuery query, HttpServletResponse response){
        if(StringUtils.isBlank(query.getProjectId()) || StringUtils.isBlank(query.getPuid())){
            toJSONResponse(Result.build(false,"非法参数！"),response);
            return;
        }
        toJSONResponse(Result.build(getLOAForPBOM(query)),response);
    }

    @RequestMapping(value = "mbom",method = RequestMethod.POST)
    public void getHzMbomLoa(@RequestBody HzLouaQuery query, HttpServletResponse response){
        if(StringUtils.isBlank(query.getProjectId()) || StringUtils.isBlank(query.getPuid())){
            toJSONResponse(Result.build(false,"非法参数！"),response);
            return;
        }
        toJSONResponse(Result.build(getLOAForMBOM(query)),response);
    }

    @RequestMapping(value = "setLou",method = RequestMethod.POST)
    public void setBomAsLou(@RequestBody SetLouReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzEBOMWriteService.setCurrentBomAsLou(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO),response);
    }

    @RequestMapping(value = "setLou/pBom",method = RequestMethod.POST)
    public void setPbomAsLou(@RequestBody SetLouReqDTO reqDTO,HttpServletResponse response){
        WriteResultRespDTO respDTO = hzPbomService.setCurrentBomAsLou(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO),response);
    }


    @RequestMapping(value = "getLou",method = RequestMethod.GET)
    public void getLou(HzLouaQuery query,HttpServletResponse response){
        if(StringUtils.isBlank(query.getProjectId()) || StringUtils.isBlank(query.getPuid())){
            toJSONResponse(Result.build(false,"非法参数！"),response);
            return;
        }
        HzLouRespDTO resp = hzEBOMReadService.getHzLouInfoById(query);
        JSONObject jsonObject = getLOAForEBOM(query);
        jsonObject.put("config",resp);
        toJSONResponse(Result.build(jsonObject),response);
    }



    @RequestMapping(value = "getLou/pBom",method = RequestMethod.GET)
    public void getPbomLou(HzLouaQuery query,HttpServletResponse response){
        if(StringUtils.isBlank(query.getProjectId()) || StringUtils.isBlank(query.getPuid())){
            toJSONResponse(Result.build(false,"非法参数！"),response);
            return;
        }
        HzLouRespDTO resp = hzPbomService.getHzLouInfoById(query);
        JSONObject jsonObject = getLOAForPBOM(query);
        jsonObject.put("config",resp);
        toJSONResponse(Result.build(jsonObject),response);
    }

    @RequestMapping(value = "getLou/mBom",method = RequestMethod.GET)
    public void getMbomLou(HzLouaQuery query,HttpServletResponse response){
        if(StringUtils.isBlank(query.getProjectId()) || StringUtils.isBlank(query.getPuid())){
            toJSONResponse(Result.build(false,"非法参数！"),response);
            return;
        }
        HzLouRespDTO resp = hzMbomService.getHzLouInfoById(query);
        JSONObject jsonObject = getLOAForMBOM(query);
        jsonObject.put("config",resp);
        toJSONResponse(Result.build(jsonObject),response);
    }


    /**
     * EBOM LOA 信息
     * @param query
     * @return
     */
    private JSONObject getLOAForEBOM(HzLouaQuery query){
        JSONObject jsonObject = new JSONObject();
        HzBOMQuery hzBOMQuery = new HzBOMQuery();
        hzBOMQuery.setParentId(query.getPuid());
        hzBOMQuery.setProjectId(query.getProjectId());
        List<HzLoaRespDTO> loaRespDTOS = new ArrayList<>();
        List<HzEPLManageRecord> recordList = hzEbomRecordDAO.findNextLevelRecordByParentId(hzBOMQuery);//只获取子一层记录
        if(ListUtils.isNotEmpty(recordList)){
            recordList.forEach(record -> {
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
        }
        HzEPLManageRecord eplManageRecord = hzEbomRecordDAO.findEbomById(query.getPuid(),query.getProjectId());
        if(eplManageRecord != null && StringUtils.isNotEmpty(eplManageRecord.getParentUid())){
            HzEbomRespDTO  respDTO =  hzEBOMReadService.fingEbomById(eplManageRecord.getParentUid(),query.getProjectId());
            if(respDTO != null){
                HzLoaRespDTO hzLoaRespDTO = new HzLoaRespDTO();
                hzLoaRespDTO.setParentLevel(respDTO.getLevel());
                hzLoaRespDTO.setParentLineId(respDTO.getLineId());
                hzLoaRespDTO.setParentName(respDTO.getpBomLinePartName());
                jsonObject.put("parent",hzLoaRespDTO);
            }else {
                jsonObject.put("parent",new HzLouRespDTO());
            }
        }else {
            jsonObject.put("parent",new HzLoaRespDTO());
        }
        jsonObject.put("child",loaRespDTOS);
        return jsonObject;
    }

    /**
     * PBOM LOA 信息
     * @param query
     * @return
     */
    private JSONObject getLOAForPBOM(HzLouaQuery query){
        JSONObject jsonObject = new JSONObject();
        List<HzLoaRespDTO> loaRespDTOS = new ArrayList<>();
        //查询自己
        HzPbomLineRecord hzPbomLineRecord = hzPbomRecordDAO.getHzPbomByEbomPuid(query.getPuid(),query.getProjectId());
        if(hzPbomLineRecord == null){
            return jsonObject;
        }
        List<HzPbomLineRecord> recordList =  hzPbomRecordDAO.getFirstLevelBomByParentId(query.getPuid(),query.getProjectId());//子一层
        HzPbomLineRespDTO respDTO = null;
        if(StringUtils.isNotEmpty(hzPbomLineRecord.getParentUid())){
            respDTO = hzPbomService.getHzPbomByPuid(query.getProjectId(),hzPbomLineRecord.getParentUid());//父
        }
        if(ListUtils.isNotEmpty(recordList)){
            recordList.forEach(record -> {
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
        }

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
        return jsonObject;
    }


    /**
     * MBOM LOA 信息
     * @param query
     * @return
     */
    private JSONObject  getLOAForMBOM(HzLouaQuery query){
        JSONObject jsonObject = new JSONObject();
        List<HzLoaRespDTO> loaRespDTOS = new ArrayList<>();
        //查询自己
        HzBOMQuery hzBOMQuery = new HzBOMQuery();
        hzBOMQuery.setProjectId(query.getProjectId());
        hzBOMQuery.setPuid(query.getPuid());
        hzBOMQuery.setTableName(MbomTableNameEnum.tableName(query.getType()));
        List<HzMbomLineRecord> hzMbomLineRecords = hzMbomRecordDAO.getHzMbomByBomQuery(hzBOMQuery);
        if(ListUtils.isEmpty(hzMbomLineRecords)){
            return jsonObject;
        }
        HzMbomLineRecord hzMbomLineRecord = hzMbomLineRecords.get(0);
        HzBOMQuery query1 = new HzBOMQuery();
        query1.setTableName(MbomTableNameEnum.tableName(query.getType()));
        query1.setProjectId(query.getProjectId());
        query1.setPuid(hzMbomLineRecord.geteBomPuid());
        query1.setColorId(hzMbomLineRecord.getColorId());
        List<HzMbomLineRecord> recordList =  hzMbomRecordDAO.getNextBomStructure(query1);//子一层
        if(ListUtils.isNotEmpty(recordList)){
            recordList.forEach(record -> {
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
        }

        //获取父层信息
        HzBOMQuery bomQuery = new HzBOMQuery();
        bomQuery.setParentId(hzMbomLineRecord.getParentUid());
        bomQuery.setColorId(hzMbomLineRecord.getColorId());
        bomQuery.setProjectId(query.getProjectId());
        List<HzMbomLineRecord> parentRecords = hzMbomRecordDAO.getHzMbomByBomQuery(hzBOMQuery);
        if(ListUtils.isNotEmpty(parentRecords)){
            HzLoaRespDTO hzLoaRespDTO = new HzLoaRespDTO();
            HzMbomLineRecord record = parentRecords.get(0);
            Integer is2Y = record.getIs2Y();
            Integer hasChildren = record.getIsHas();
            String lineIndex = record.getLineIndex();
            String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);

            hzLoaRespDTO.setParentLevel(strings[0]);
            hzLoaRespDTO.setParentLineId(record.getLineId());
            hzLoaRespDTO.setParentName(record.getpBomLinePartName());
            jsonObject.put("parent",hzLoaRespDTO);
        }else {
            jsonObject.put("parent",new HzLoaRespDTO());
        }
        jsonObject.put("child",loaRespDTOS);
        return jsonObject;
    }
}
