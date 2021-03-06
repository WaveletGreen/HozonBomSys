//package com.connor.hozon.controller.bom.bom;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.connor.hozon.controller.bom.BaseController;
//import com.connor.hozon.resources.domain.dto.request.RecoverHzBomReqDTO;
//import com.connor.hozon.resources.domain.dto.response.HzEbomRespDTO;
//import com.connor.hozon.resources.domain.dto.response.HzMbomRecordRespDTO;
//import com.connor.hozon.resources.domain.dto.response.HzPbomLineRespDTO;
//import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
//import com.connor.hozon.resources.domain.query.HzBomRecycleByPageQuery;
//import com.connor.hozon.resources.page.Page;
//import com.connor.hozon.resources.service.bom.HzEBOMReadService;
//import com.connor.hozon.resources.service.bom.HzMbomService;
//import com.connor.hozon.resources.service.bom.HzPbomService;
//import cn.net.connor.hozon.common.util.ListUtils;
//import com.connor.hozon.resources.util.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: haozt
// * @Date: 2018/7/12
// * @Description: bom 删除数据回收站
// */
//@Controller
//@RequestMapping(value = "recycle")
//public class HzBomRecycleController extends BaseController {
//
//    @Autowired
//    private HzEBOMReadService hzEBOMReadService;
//
//    @Autowired
//    private HzMbomService hzMbomService;
//
//    @Autowired
//    private HzPbomService hzPbomService;
//    /**
//     * 获取bom 回收站信息
//     * @param query
//     */
//    @RequestMapping(value = "record",method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String,Object> getHzBomRecycleRecord(HzBomRecycleByPageQuery query){
//        HzBomRecycleByPageQuery pageQuery =query;
//        pageQuery.setPageSize(0);
//        try{
//            pageQuery.setPageSize(Integer.valueOf(query.getLimit()));
//        }catch (Exception e){
//
//        }
//        if(query.getType() == 1){
//            Page<HzEbomRespDTO> recordRespDTOPage = hzEBOMReadService.getHzEbomRecycleByPage(query);
//            Map<String, Object> ret = new HashMap<>();
//            if(recordRespDTOPage == null){
//                return ret;
//            }
//            List<HzEbomRespDTO> recordRespDTOS =  recordRespDTOPage.getResult();
//            if (ListUtils.isEmpty(recordRespDTOS)) {
//                return ret;
//            }
//            List<Map<String,Object>> list = new ArrayList<>();
//            Map<String,Object> map = new HashMap<>();
//            JSONArray array = recordRespDTOS.get(0).getJsonArray();
//            for(int i =0;i<array.size();i++){
//                JSONObject object = array.getJSONObject(i);
//                map = object;
//                list.add(map);
//            }
//            ret.put("totalCount", recordRespDTOPage.getTotalCount());
//            ret.put("result", list);
//            return ret;
//        }else if(query.getType() == 2){
//            Page<HzPbomLineRespDTO> respDTOPage = hzPbomService.getHzPbomRecycleByPage(query);
//            List<HzPbomLineRespDTO> respDTOS = respDTOPage.getResult();
//            if(respDTOS == null){
//                return new HashMap<>();
//            }
//            Map<String, Object> ret = new HashMap<>();
//            List<Map<String, Object>> _list = new ArrayList<>();
//            respDTOS.forEach(dto -> {
//                Map<String, Object> _res = new HashMap<>();
//                _res.put("eBomPuid", dto.getEBomPuid());
//                _res.put("No",dto.getNo());
//                _res.put("level", dto.getLevel());
//                _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
//                _res.put("rank", dto.getRank());
//                _res.put("groupNum", dto.getGroupNum());
//                _res.put("lineId", dto.getLineId());
//                _res.put("pBomLinePartName",dto.getpBomLinePartName());
//                _res.put("pBomLinePartEnName",dto.getpBomLinePartEnName());
//                _res.put("pBomLinePartClass",dto.getpBomLinePartClass());
//                _res.put("pBomLinePartResource",dto.getpBomLinePartResource());
//                _res.put("resource", dto.getResource());
//                _res.put("type", dto.getType());
//                _res.put("buyUnit", dto.getBuyUnit());
//                _res.put("workShop1", dto.getWorkShop1());
//                _res.put("workShop2", dto.getWorkShop2());
//                _res.put("productLine", dto.getProductLine());
//                _res.put("mouldType", dto.getMouldType());
//                _res.put("outerPart", dto.getOuterPart());
//                _res.put("colorPart", dto.getColorPart());
//                _res.put("station",dto.getStation());
//                _list.add(_res);
//            });
//            ret.put("totalCount", respDTOPage.getTotalCount());
//            ret.put("result", _list);
//            return ret;
//        }else if(query.getType() == 3){
//            Page<HzMbomRecordRespDTO> page = hzMbomService.getHzMbomRecycleByPage(query);
//            if (page == null) {
//                return new HashMap<>();
//            }
//            List<HzMbomRecordRespDTO> list = page.getResult();
//            Map<String, Object> ret = new HashMap<>();
//            List<Map<String, Object>> _list = new ArrayList<>();
//            list.forEach(dto -> {
//                Map<String, Object> _res = new HashMap<>();
//                _res.put("eBomPuid", dto.getEBomPuid());
//                _res.put("No", dto.getNo());
//                _res.put("level", dto.getLevel());
//                _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
//                _res.put("lineId", dto.getLineId());
//                _res.put("pBomLinePartName", dto.getpBomLinePartName());
//                _res.put("sparepart", dto.getSparePart());
//                _res.put("sparePartNum", dto.getSparePartNum());
//                _res.put("processRoute", dto.getProcessRoute());
//                _res.put("laborHour", dto.getLaborHour());
//                _res.put("rhythm", dto.getRhythm());
//                _res.put("solderJoint", dto.getSolderJoint());
//                _res.put("machineMaterial", dto.getMachineMaterial());
//                _res.put("standardPart", dto.getStandardPart());
//                _res.put("tools", dto.getTools());
//                _res.put("wasterProduct", dto.getWasterProduct());
//                _res.put("change", dto.getChange());
//                _res.put("changeNum", dto.getChangeNum());
//                _res.put("pFactoryCode", dto.getpFactoryCode());
//                _res.put("pStockLocation",dto.getPStockLocation());
//                _res.put("pBomType", dto.getPBomType());
//                _list.add(_res);
//            });
//            ret.put("totalCount", page.getTotalCount());
//            ret.put("result", _list);
//            return ret;
//        }else {
//            return new HashMap<>();
//        }
//    }
//
//    /**
//     * 删除 恢复
//     * @param recoverHzBomReqDTO
//     * @param response
//     */
//    @RequestMapping(value = "recover",method = RequestMethod.POST)
//    public void recoverHasDeletedBom(@RequestBody RecoverHzBomReqDTO recoverHzBomReqDTO, HttpServletResponse response){
//        if(recoverHzBomReqDTO.getType() ==1){
//            WriteResultRespDTO respDTO = hzEBOMReadService.recoverDeleteEbomRecord(recoverHzBomReqDTO.getProjectId(),recoverHzBomReqDTO.getId());
//            toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
//            return;
//        }else if(recoverHzBomReqDTO.getType() ==2){
//            WriteResultRespDTO respDTO = hzPbomService.recoverDeletePbomRecord(recoverHzBomReqDTO.getProjectId(),recoverHzBomReqDTO.getId());
//            toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
//            return;
//        }else if(recoverHzBomReqDTO.getType() == 3){
//            WriteResultRespDTO respDTO = hzMbomService.recoverDeleteMbomRecord(recoverHzBomReqDTO.getProjectId(),recoverHzBomReqDTO.getId());
//            toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
//            return;
//        }else{
//            toJSONResponse(Result.build(false,"非法参数！"),response);
//        }
//    }
//}
