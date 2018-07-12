package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzBomStateDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzPbomRecordDAOImpl;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzPbomByPageQuery;
import com.connor.hozon.bom.resources.query.HzPbomTreeQuery;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import share.bean.PreferenceSetting;
import share.bean.RedisBomBean;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.*;
import sql.redis.SerializeUtil;

import java.util.*;

/**
 * Created by haozt on 2018/5/24
 */
@Service("HzPbomService")
public class HzPbomServiceImpl implements HzPbomService {
    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzBomStateDAO hzBomStateDAO;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    private HzEPLManageRecordService hzEPLManageRecordService;


    @Override
    public OperateResultMessageRespDTO insertHzPbomRecord(AddHzPbomRecordReqDTO recordReqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            User user = UserInfo.getUser();
            if(user.getGroupId()!=9){
                respDTO.setErrMsg("你当前没有权限执行此操作!");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
//            HzPbomRecord record = hzPbomRecordDAO.getHzPbomByEbomPuid(recordReqDTO.geteBomPuid());
//            if(record!=null){
//                respDTO.setErrMsg("当前插入的对象已存在,编辑属性请点击修改按钮进行操作!");
//                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
//                return respDTO;
//            }

            HzPbomLineRecord hzPbomRecord = new HzPbomLineRecord();
            hzPbomRecord.setCreateName(UserInfo.getUser().getUserName());
            hzPbomRecord.setUpdateName(UserInfo.getUser().getUserName());
            String buyUnit = recordReqDTO.getBuyUnit();
            String colorPart = recordReqDTO.getColorPart();
            String type = recordReqDTO.getType();
            if("Y".equals(buyUnit)){
                hzPbomRecord.setBuyUnit(0);
            }else if("N".equals(buyUnit)){
                hzPbomRecord.setBuyUnit(1);
            }else{
                hzPbomRecord.setBuyUnit(2);
            }
            if("Y".equals(colorPart)){
                hzPbomRecord.setColorPart(0);
            }else if("N".equals(colorPart)){
                hzPbomRecord.setColorPart(1);
            }else{
                hzPbomRecord.setColorPart(2);
            }
            if("Y".equals(type)){
                hzPbomRecord.setType(0);
            }else if("N".equals(type)){
                hzPbomRecord.setType(1);
            }else{
                hzPbomRecord.setType(2);
            }
            hzPbomRecord.setMouldType(recordReqDTO.getMouldType());
            hzPbomRecord.setOuterPart(recordReqDTO.getOuterPart());
            hzPbomRecord.setProductLine(recordReqDTO.getProductLine());
            hzPbomRecord.setStation(recordReqDTO.getStation());
            hzPbomRecord.setWorkShop1(recordReqDTO.getWorkShop1());
            hzPbomRecord.setWorkShop2(recordReqDTO.getWorkShop2());
            hzPbomRecord.seteBomPuid(recordReqDTO.geteBomPuid());
            Map<String,Object> map = new HashMap<>();
            map.put("pPuid",recordReqDTO.geteBomPuid());
            map.put("projectId",recordReqDTO.getProjectId());
            List<HzPbomLineRecord> hzPbomLineRecords= hzPbomRecordDAO.getPbomById(map);
            if(ListUtil.isNotEmpty(hzPbomLineRecords)){
                int i = hzPbomRecordDAO.update(hzPbomRecord);
                if(i>0){
                    return OperateResultMessageRespDTO.getSuccessResult();
                }else {
                   return OperateResultMessageRespDTO.getFailResult();
                }
            }
            hzPbomRecord.setPuid(UUID.randomUUID().toString());
            int maxOrderNum = hzPbomRecordDAO.getHzPbomMaxOrderNum();
            hzPbomRecord.setOrderNum(++maxOrderNum);
            List<HzPbomLineRecord> records = new ArrayList<>();
            records.add(hzPbomRecord);
            int i = hzPbomRecordDAO.insertList(records);
            if(i>0){
               respDTO.setErrMsg("操作成功！");
               respDTO.setErrCode(OperateResultMessageRespDTO.SUCCESS_CODE);
               return  respDTO;
            }else {
                respDTO.setErrMsg("操作失败，请稍后重试！");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
        }catch (Exception e){
            respDTO.setErrMsg("操作失败，请稍后重试！");
            respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
            return respDTO;
        }
    }

    @Override
    public OperateResultMessageRespDTO updateHzPbomRecord(UpdateHzPbomRecordReqDTO recordReqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            User user = UserInfo.getUser();
            if(user.getGroupId()!=9){
                respDTO.setErrMsg("你当前没有权限执行此操作!");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            HzPbomLineRecord hzPbomRecord = new HzPbomLineRecord();
            hzPbomRecord.setUpdateName(UserInfo.getUser().getUserName());
            hzPbomRecord.seteBomPuid(recordReqDTO.geteBomPuid());
//            hzPbomRecord.setPuid(UUID.randomUUID().toString());
            String buyUnit = recordReqDTO.getBuyUnit();
            String colorPart = recordReqDTO.getColorPart();
            String type = recordReqDTO.getType();
            if(buyUnit.equals("Y")){
                hzPbomRecord.setBuyUnit(0);
            }else if(buyUnit.equals("N")){
                hzPbomRecord.setBuyUnit(1);
            }else{
                hzPbomRecord.setBuyUnit(2);
            }
            if(colorPart.equals("Y")){
                hzPbomRecord.setColorPart(0);
            }else if(colorPart.equals("N")){
                hzPbomRecord.setColorPart(1);
            }else{
                hzPbomRecord.setColorPart(2);
            }
            if(type.equals("Y")){
                hzPbomRecord.setType(0);
            }else if(type.equals("N")){
                hzPbomRecord.setType(1);
            }else{
                hzPbomRecord.setType(2);
            }
            hzPbomRecord.setMouldType(recordReqDTO.getMouldType());
            hzPbomRecord.setOuterPart(recordReqDTO.getOuterPart());
            hzPbomRecord.setProductLine(recordReqDTO.getProductLine());
            hzPbomRecord.setStation(recordReqDTO.getStation());
            hzPbomRecord.setWorkShop1(recordReqDTO.getWorkShop1());
            hzPbomRecord.setWorkShop2(recordReqDTO.getWorkShop2());
            int i = hzPbomRecordDAO.update(hzPbomRecord);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }else {
                return OperateResultMessageRespDTO.getFailResult();
            }
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public OperateResultMessageRespDTO deleteHzPbomRecordByForeignId(DeleteHzPbomReqDTO reqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            User user = UserInfo.getUser();
            if(user.getGroupId()!=9){
                respDTO.setErrMsg("你当前没有权限执行此操作!");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            if(reqDTO.getPuids() == null || reqDTO.getPuids().equals("") || reqDTO.getProjectId() ==null || reqDTO.getProjectId().equals("")){
                respDTO.setErrMsg("非法参数！");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            String bomPuids[] = reqDTO.getPuids().trim().split(",");
            for(String puid :bomPuids){
                HzPbomTreeQuery treeQuery = new HzPbomTreeQuery();
                treeQuery.setProjectId(reqDTO.getProjectId());
                treeQuery.setPuid(puid);
                List<HzPbomLineRecord> lineRecords = hzPbomRecordDAO.getHzPbomTree(treeQuery);//自己
                Set<String> set = new HashSet<>();//去除重复
                if(ListUtil.isNotEmpty(lineRecords)){
                    for(int i = 0;i<lineRecords.size();i++){
                        HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                        hzPbomTreeQuery.setPuid(lineRecords.get(0).getParentUid());
                        hzPbomTreeQuery.setProjectId(reqDTO.getProjectId());
                        List<HzPbomLineRecord> records = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);//父亲
                        if(ListUtil.isNotEmpty(records)){
                            if(records.size() -lineRecords.size()==1){
                                HzPbomLineRecord hzPbomLineRecord = records.get(0);
                                hzPbomLineRecord.setIsHas(0);
                                hzPbomLineRecord.setIsPart(1);
                                if(hzPbomLineRecord.getIs2Y().equals(1)){
                                    hzPbomLineRecord.setIs2Y(0);
                                }
                                hzPbomRecordDAO.update(hzPbomLineRecord);
                            }

                        }
                        set.add(lineRecords.get(i).geteBomPuid());
                    }
                }
                List<DeleteHzPbomReqDTO> list = new ArrayList<>();
                for(String s:set){
                    DeleteHzPbomReqDTO deleteHzPbomReqDTO = new DeleteHzPbomReqDTO();
                    deleteHzPbomReqDTO.seteBomPuid(s);
                    list.add(deleteHzPbomReqDTO);
                }
                hzPbomRecordDAO.deleteList(list);//mabatis 做批量更新时 返回值为-1 所以这里根据异常情况来判断成功与否
            }
            return OperateResultMessageRespDTO.getSuccessResult();
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
    }


    @Override
    public JSONArray getPbomForProcessCompose(HzPbomProcessComposeReqDTO reqDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", reqDTO.getProjectId());
        map.put("lineId", reqDTO.getLineId());
        List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomById(map);
        if (ListUtil.isEmpty(records)) {
            return null;
        }

        try {
            HzPbomLineRecord record = records.get(0);
            HzPbomTreeQuery query = new HzPbomTreeQuery();
            query.setProjectId(reqDTO.getProjectId());
            query.setPuid(record.geteBomPuid());
            List<HzPbomLineRecord> recordList = getHzPbomLineTree(query);
//            List<HzPbomLineRecord> recordList =getChildLineRecord(reqDTO.getProjectId(),record,new ArrayList<>());
            JSONArray jsonArray = new JSONArray();
            for (HzPbomLineRecord lineRecord : recordList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("puid", lineRecord.geteBomPuid());
                jsonObject.put("parentUid", lineRecord.getParentUid());
                jsonObject.put("lineId", lineRecord.getLineId());
                jsonArray.add(jsonObject);
            }
            return jsonArray;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public JSONArray getPbomByLineId(HzPbomProcessComposeReqDTO reqDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", reqDTO.getProjectId());
        map.put("lineId", reqDTO.getLineId());
        map.put("pPuid",reqDTO.getPuid());
        List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomById(map);
        if (ListUtil.isEmpty(records)) {
            return null;
        }
        try {
            HzPbomLineRecord record = records.get(0);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("puid", record.getPuid());
            Integer is2Y = record.getIs2Y();
            Integer hasChildren = record.getIsHas();
            String lineIndex = record.getLineIndex();
            String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
            jsonObject.put("level", strings[0]);
            jsonObject.put("rank", strings[1]);
            jsonObject.put("pBomOfWhichDept", record.getpBomOfWhichDept());
            jsonObject.put("groupNum", record.getLineId());
            jsonObject.put("eBomPuid", record.geteBomPuid());
            jsonObject.put("lineId", record.getLineId());

            jsonObject.put("pBomLinePartName", record.getpBomLinePartName());
            jsonObject.put("pBomLinePartClass", record.getpBomLinePartClass());
            jsonObject.put("pBomLinePartResource", record.getpBomLinePartResource());
            jsonObject.put("pBomLinePartEnName", record.getpBomLinePartEnName());

            jsonObject.put("resource", record.getResource());
            jsonObject.put("type", record.getType());
            jsonObject.put("buyUnit", record.getBuyUnit());
            jsonObject.put("workShop1", record.getWorkShop1());
            jsonObject.put("workShop2", record.getWorkShop2());
            jsonObject.put("productLine", record.getProductLine());
            jsonObject.put("mouldType", record.getMouldType());
            jsonObject.put("outerPart", record.getOuterPart());
            jsonObject.put("colorPart", record.getColorPart());
            jsonArray.add(jsonObject);
            return jsonArray;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public Page<HzPbomLineRespDTO> getHzPbomRecordPage(HzPbomByPageQuery query) {

        String level = query.getLevel();
        if (level != null && level!="") {
            if(level.length()==1 && level.toUpperCase().endsWith("Y")){
                query.setIsHas(Integer.valueOf(1));
            }else {
                int length = level.charAt(0) - 48;
                if (level.toUpperCase().endsWith("Y")) {
                    query.setIsHas(Integer.valueOf(1));
                } else {
                    query.setIsHas(Integer.valueOf(0));
                }
                query.setLineIndex(String.valueOf(length - 1));
            }
        }

        int count = hzPbomRecordDAO.getHzBomLineCount(query.getProjectId());
        if(count<=0){
            List<HzBomLineRecord> lineRecords = hzBomLineRecordDao.getAllBomLineRecordByProjectId(query.getProjectId());
            if(ListUtil.isNotEmpty(lineRecords)){
                int size = lineRecords.size();
                //分批插入数据 一次1000条
                int i =0;
                int cou = 0;
                if(size>1000){
                    for(i =0;i<size/1000;i++){
                        List<HzPbomLineRecord> list = new ArrayList<>();

                        for(int j = 0;j<1000;j++){
                            HzPbomLineRecord hzPbomLineRecord =bomLineToPbomLine(lineRecords.get(cou));
                            list.add(hzPbomLineRecord);
                            cou++;
                        }
                        hzPbomRecordDAO.insertList(list);
                    }
                }
                if(i*1000<size){
                    List<HzPbomLineRecord> list = new ArrayList<>();
                    for(int j = 0;j<size-i*1000;j++){
                        HzPbomLineRecord hzPbomLineRecord =bomLineToPbomLine(lineRecords.get(cou));
                        list.add(hzPbomLineRecord);
                        cou++;
                    }
                    hzPbomRecordDAO.insertList(list);
                }
            }
        }
        try {
            Page<HzPbomLineRecord> recordPage =hzPbomRecordDAO.getHzPbomRecordByPage(query);
            if(recordPage == null || recordPage.getResult() == null){
                return null;
            }
            List<HzPbomLineRecord> records = recordPage.getResult();
            int num = (query.getPage()-1)*query.getLimit();
            List<HzPbomLineRespDTO> respDTOS = pbomLineRecordToRespDTOS(records,query.getProjectId(),num);
            return new Page<>(query.getPage(),query.getLimit(),recordPage.getTotalCount(),respDTOS);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public HzPbomLineRespDTO getHzPbomByPuid(String projectId, String puid) {
        try{
            Map<String,Object> map = new HashMap<>();
            map.put("projectId",projectId);
            map.put("pPuid",puid);
            List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomById(map);
            if(ListUtil.isNotEmpty(records)){
                HzPbomLineRecord record = records.get(0);
                records = new ArrayList<>();
                records.add(record);
                List<HzPbomLineRespDTO> respDTOS = pbomLineRecordToRespDTOS(records,projectId,0);
                return respDTOS.get(0);
            }
        }catch (Exception e){
            return  null;
        }
        return null;
    }

    @Override
    public List<HzPbomLineRecord> getHzPbomLineTree(HzPbomTreeQuery query) {
        return hzPbomRecordDAO.getHzPbomTree(query);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public OperateResultMessageRespDTO andProcessCompose(AddHzPbomRecordReqDTO recordReqDTO) {
        OperateResultMessageRespDTO operateResultMessageRespDTO = new OperateResultMessageRespDTO();
        try {
            if (recordReqDTO.getProjectId() == null || recordReqDTO.getProjectId().equals("")) {
                operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                operateResultMessageRespDTO.setErrMsg("非法参数！");
                return operateResultMessageRespDTO;
            }

            if (recordReqDTO.getPuids() == null || recordReqDTO.getPuids().equals("")) {
                operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                operateResultMessageRespDTO.setErrMsg("请至少选择一个合成零件！");
                return operateResultMessageRespDTO;
            }
            if (recordReqDTO.getLineId() != null) {//第一次合成 选择零件添加到新将要合成零件编号下
                if (recordReqDTO.getLineId().contains("-")) {
                    if (recordReqDTO.getLineId().split("-")[1].length() < 4) {
                        operateResultMessageRespDTO.setErrMsg("零件号-后面的长度不能小于4！");
                        operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                        return operateResultMessageRespDTO;
                    }
                }
                boolean b = hzPbomRecordDAO.checkItemIdIsRepeat(recordReqDTO.getProjectId(), recordReqDTO.getLineId());
                if (b) {
                    operateResultMessageRespDTO.setErrMsg("当前零件号已存在，请重新输入！");
                    operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    return operateResultMessageRespDTO;
                }
                String puid = UUID.randomUUID().toString();
                HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(recordReqDTO.getProjectId());
                if (hzBomMainRecord == null) {
                    return OperateResultMessageRespDTO.getFailResult();
                }
                hzPbomLineRecord.setIs2Y(1);
                hzPbomLineRecord.setIsPart(0);
                hzPbomLineRecord.setIsHas(1);
                hzPbomLineRecord.setPuid(puid);
                hzPbomLineRecord.setpBomLinePartResource(recordReqDTO.getpBomLinePartResource());
                hzPbomLineRecord.setpBomLinePartEnName(recordReqDTO.getpBomLinePartEnName());
                hzPbomLineRecord.setpBomOfWhichDept(recordReqDTO.getpBomOfWhichDept());
                hzPbomLineRecord.setpBomLinePartName(recordReqDTO.getpBomLinePartName());
                hzPbomLineRecord.setpBomLinePartClass(recordReqDTO.getpBomLinePartClass());
                int num = hzPbomRecordDAO.getHzPbomMaxOrderNum();
                hzPbomLineRecord.setOrderNum(++num);
                hzPbomLineRecord.seteBomPuid(puid);
                int maxLineIndexFirstNum = hzPbomRecordDAO.getMaxLineIndexFirstNum(recordReqDTO.getProjectId());
                StringBuffer buffer = new StringBuffer(String.valueOf(++maxLineIndexFirstNum));

                hzPbomLineRecord.setLineIndex(buffer.append(".1").toString());
                String lineId = recordReqDTO.getLineId();
                hzPbomLineRecord.setLineId(lineId);
                hzPbomLineRecord.setIsDept(0);
                hzPbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                hzPbomLineRecord.setCreateName(UserInfo.getUser().getUserName());
                hzPbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
                String buyUnit = recordReqDTO.getBuyUnit();
                String colorPart = recordReqDTO.getColorPart();
                String type = recordReqDTO.getType();
                if ("Y".equals(buyUnit)) {
                    hzPbomLineRecord.setBuyUnit(0);
                } else if ("N".equals(buyUnit)) {
                    hzPbomLineRecord.setBuyUnit(1);
                } else {
                    hzPbomLineRecord.setBuyUnit(2);
                }
                if ("Y".equals(colorPart)) {
                    hzPbomLineRecord.setColorPart(0);
                } else if ("N".equals(colorPart)) {
                    hzPbomLineRecord.setColorPart(1);
                } else {
                    hzPbomLineRecord.setColorPart(2);
                }
                if ("Y".equals(type)) {
                    hzPbomLineRecord.setType(0);
                } else if ("N".equals(type)) {
                    hzPbomLineRecord.setType(1);
                } else {
                    hzPbomLineRecord.setType(2);
                }
                hzPbomLineRecord.setMouldType(recordReqDTO.getMouldType());
                hzPbomLineRecord.setOuterPart(recordReqDTO.getOuterPart());
                hzPbomLineRecord.setProductLine(recordReqDTO.getProductLine());
                hzPbomLineRecord.setStation(recordReqDTO.getStation());
                hzPbomLineRecord.setWorkShop1(recordReqDTO.getWorkShop1());
                hzPbomLineRecord.setWorkShop2(recordReqDTO.getWorkShop2());
                hzPbomLineRecord.setStation(recordReqDTO.getStation());
                hzPbomLineRecord.setProductLine(recordReqDTO.getProductLine());
                hzPbomLineRecord.setResource(recordReqDTO.getResource());
                List<HzPbomLineRecord> list = new ArrayList<>();
                list.add(hzPbomLineRecord);
                int i = hzPbomRecordDAO.insertList(list);
                if (i < 0) {
                    return OperateResultMessageRespDTO.getFailResult();
                }
                String[] puids = recordReqDTO.getPuids().trim().split(",");
                for (String puidStr : puids) {
                    HzPbomLineRecord record = hzPbomRecordDAO.getHzPbomByEbomPuid(puidStr, recordReqDTO.getProjectId());
                    if (record == null) {
                        if (recordReqDTO.getPuids() == null || recordReqDTO.getPuids().equals("")) {
                            operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                            operateResultMessageRespDTO.setErrMsg("选择要合成的零件不存在！");
                            continue;
                        }

                    }
                    //判断要合成的零件号的父的子的情况
                    HzPbomTreeQuery query = new HzPbomTreeQuery();
                    query.setProjectId(recordReqDTO.getProjectId());
                    query.setPuid(record.getParentUid());
                    List<HzPbomLineRecord> recordList = getHzPbomLineTree(query);
                    if (recordList.size() <= 2) {
                        HzPbomLineRecord pbomLineRecord = recordList.get(0);
                        pbomLineRecord.setIsHas(0);
                        pbomLineRecord.setIsPart(1);
                        if (pbomLineRecord.getIs2Y().equals(1)) {
                            pbomLineRecord.setIs2Y(0);
                        }
                        hzPbomRecordDAO.update(pbomLineRecord);

                    }
                    record.setParentUid(puid);
                    record.setIs2Y(0);
                    record.setIsPart(1);
                    record.setIsHas(0);
                    //找出目标位置信息 计算将要添加零件号所属的层级关系
                    query = new HzPbomTreeQuery();
                    query.setProjectId(recordReqDTO.getProjectId());
                    query.setPuid(puid);
                    recordList = getHzPbomLineTree(query);
                    if (ListUtil.isNotEmpty(recordList)) {
                        int length = recordList.get(0).getLineIndex().split("\\.").length + 1;
                        List<String> list1 = new ArrayList<>();
                        for (HzPbomLineRecord lineRecord : recordList) {
                            int len = lineRecord.getLineIndex().split("\\.").length;
                            if (length == len) {
                                list1.add(lineRecord.getLineIndex());
                            }
                        }
                        Integer max = 0;
                        //找出lineindex 末尾最大值
                        for (int j = 0; j < list.size() - 1; j++) {
                            String str = list1.get(j).split("\\.")[list1.get(j).split("\\.").length - 1];
                            if (max < Integer.valueOf(str)) {
                                max = Integer.valueOf(str);
                            }
                        }
                        max = max + 1;
                        record.setLineIndex(new StringBuffer(recordList.get(0).getLineIndex()).append("." + max).toString());
                        hzPbomRecordDAO.update(record);
                    } else {
                        return OperateResultMessageRespDTO.getFailResult();
                    }
                }
                return OperateResultMessageRespDTO.getSuccessResult();
            } else {//第二次合成
                //2 puid(父)  puids(子)
                String parentId = recordReqDTO.geteBomPuid();
                String childrenIds = recordReqDTO.getPuids().trim();//需要找出他们的父 2Y层为父
                String parentLineIndex = "";//第一次合成后的lineIndex进行转换为第二次合成结束的lineIndex
                //父
                HzPbomLineRecord record = hzPbomRecordDAO.getHzPbomByEbomPuid(parentId, recordReqDTO.getProjectId());
                if (null != record) {
                    String lineIndex = record.getLineIndex();
                    if (record.getIsHas().equals(0) || record.getIsPart().equals(1)) {
                        record.setIsHas(new Integer(1));
                        record.setIsPart(new Integer(0));
                        record.setUpdateName(UserInfo.getUser().getUserName());
                        int length = record.getLineIndex().split("\\.").length;
                        if (record.getIs2Y().equals(0) && length == 2) {
                            record.setIs2Y(1);
                        }
                        int i = hzPbomRecordDAO.update(record);
                        if (i < 0) {
                            return OperateResultMessageRespDTO.getFailResult();
                        }
                    }

                    String[] children = childrenIds.split(",");
                    HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                    hzPbomTreeQuery.setPuid(record.geteBomPuid());
                    hzPbomTreeQuery.setProjectId(recordReqDTO.getProjectId());
                    List<HzPbomLineRecord> records = getHzPbomLineTree(hzPbomTreeQuery);
                    String childrenParentPuid = "";
                    for (int k = 0; k < children.length; k++) {
                        HzPbomLineRecord childRecord = hzPbomRecordDAO.getHzPbomByEbomPuid(children[k], recordReqDTO.getProjectId());
                        if (childRecord.getLineIndex().split("\\.").length == 2) {//找合成的父层
                            childrenParentPuid = childRecord.geteBomPuid();
                            childRecord.setParentUid(record.geteBomPuid());
                            List<String> list = new ArrayList<>();
                            int length = lineIndex.split("\\.").length ;
                            if (ListUtil.isNotEmpty(records)) {
                                for (HzPbomLineRecord lineRecord : records) {
                                    int len = lineRecord.getLineIndex().split("\\.").length;
                                    if (length == len) {
                                        list.add(lineRecord.getLineIndex());
                                    }
                                }
                                Integer max = 0;
                                //找出lineindex 末尾最大值
                                for (int i = 0; i < list.size() - 1; i++) {
                                    String str = list.get(i).split("\\.")[list.get(i).split("\\.").length - 1];
                                    if (max < Integer.valueOf(str)) {
                                        max = Integer.valueOf(str);
                                    }
                                }
                                max = max + 1;
                                parentLineIndex = new StringBuffer(lineIndex).append("." + max).toString();
                                if (childRecord.getIs2Y().equals(1) && childRecord.getLineIndex().split("\\.").length == 2) {
                                    childRecord.setIs2Y(0);
                                }
                                childRecord.setLineIndex(parentLineIndex);
                            } else {
                                operateResultMessageRespDTO.setErrMsg("要合成的节点不存在，请核对改节点是否被删除！");
                                operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                                return operateResultMessageRespDTO;
                            }
                            hzPbomRecordDAO.update(childRecord);
                            break;
                        } else {
                            if (k == children.length - 1) {
                                for (String child : children) {//直接从A bom 剪切数据 到B bom
                                    childRecord = hzPbomRecordDAO.getHzPbomByEbomPuid(child, recordReqDTO.getProjectId());
                                    //判断要合成的零件号的父的子的情况
                                    HzPbomTreeQuery query = new HzPbomTreeQuery();
                                    query.setProjectId(recordReqDTO.getProjectId());
                                    query.setPuid(childRecord.getParentUid());
                                    List<HzPbomLineRecord> recordList = getHzPbomLineTree(query);
                                    if (recordList.size() <= 2) {
                                        HzPbomLineRecord pbomLineRecord = recordList.get(0);
                                        pbomLineRecord.setIsHas(0);
                                        pbomLineRecord.setIsPart(1);
                                        if (pbomLineRecord.getIs2Y().equals(1)) {
                                            pbomLineRecord.setIs2Y(0);
                                        }
                                        hzPbomRecordDAO.update(pbomLineRecord);
                                    }
                                    childRecord.setParentUid(record.geteBomPuid());
                                    List<String> list = new ArrayList<>();
                                    int length = lineIndex.split("\\.").length ;
                                    for (HzPbomLineRecord lineRecord : records) {
                                        int len = lineRecord.getLineIndex().split("\\.").length;
                                        if (length == len) {
                                            list.add(lineRecord.getLineIndex());
                                        }
                                    }
                                    Integer max = 0;
                                    //找出lineindex 末尾最大值
                                    for (int i = 0; i < list.size() - 1; i++) {
                                        String str = list.get(i).split("\\.")[list.get(i).split("\\.").length - 1];
                                        if (max < Integer.valueOf(str)) {
                                            max = Integer.valueOf(str);
                                        }
                                    }
                                    max = max + 1;
                                    parentLineIndex = new StringBuffer(lineIndex).append("." + max).toString();
                                    childRecord.setLineIndex(parentLineIndex);
                                    hzPbomRecordDAO.update(childRecord);
                                }
                            }
                        }
                    }
                    int count = 1;
                    for (String child : children) {
                        if(childrenParentPuid.equals(child)){
                            continue;
                        }
                        HzPbomLineRecord childRecord = hzPbomRecordDAO.getHzPbomByEbomPuid(child, recordReqDTO.getProjectId());
                        if (childRecord.getLineIndex().split("\\.").length > 2) {//找合成 子层
                            StringBuffer buffer = new StringBuffer(parentLineIndex);
                            buffer.append("." + count);
                            count++;
                            childRecord.setLineIndex(buffer.toString());
                            hzPbomRecordDAO.update(childRecord);
                        }
                    }
                } else {
                    operateResultMessageRespDTO.setErrMsg("要合成的节点不存在，请核对改节点是否被删除！");
                    operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    return operateResultMessageRespDTO;
                }
            }
            return OperateResultMessageRespDTO.getSuccessResult();
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    private HzPbomLineRecord bomLineToPbomLine(HzBomLineRecord record){
        HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
        hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzPbomLineRecord.setIsHas(record.getIsHas());
        hzPbomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzPbomLineRecord.seteBomPuid(record.getPuid());
        hzPbomLineRecord.setIsDept(record.getIsDept());
        hzPbomLineRecord.setLineId(record.getLineID());
        hzPbomLineRecord.setIsPart(record.getIsPart());
        hzPbomLineRecord.setIs2Y(record.getIs2Y());
        hzPbomLineRecord.setLineIndex(record.getLineIndex());
        hzPbomLineRecord.setParentUid(record.getParentUid());
        hzPbomLineRecord.setpBomLinePartClass(record.getpBomLinePartClass());
        hzPbomLineRecord.setpBomLinePartName(record.getpBomLinePartName());
        hzPbomLineRecord.setpBomOfWhichDept(record.getpBomOfWhichDept());
        hzPbomLineRecord.setpBomLinePartEnName(record.getpBomLinePartEnName());
        hzPbomLineRecord.setpBomLinePartResource(record.getpBomLinePartResource());
        hzPbomLineRecord.setOrderNum(record.getOrderNum());
        return hzPbomLineRecord;
    }

    /**
     * 获取bom系统的层级和级别
     *
     * @param lineIndex
     * @param is2Y
     * @param hasChildren
     * @return String[0]层级  String[1]级别
     */
    public static String[] getLevelAndRank(String lineIndex, Integer is2Y, Integer hasChildren) {
        int level = (lineIndex.split("\\.")).length;
        String line = "";
        int rank = 0;
        if (null != is2Y && is2Y.equals(1)) {
            line = "2Y";
            rank = 1;
        } else if (null != is2Y && is2Y.equals(0)) {
            if (hasChildren != null && hasChildren.equals(1)) {
                line = level + "Y";
                rank = level - 1;
            } else if (hasChildren != null && hasChildren.equals(0)) {
                line = String.valueOf(level);
                rank = level - 1;
            } else {
                line = "/";//错误数据
            }
        } else {
            line = "/";
        }
        return new String[]{line, String.valueOf(rank)};
    }

    private List<HzPbomLineRespDTO> pbomLineRecordToRespDTOS(List<HzPbomLineRecord> records,String projectId,int num) {
        try {
            List<HzPbomLineRespDTO> respDTOS = new ArrayList<>();
            for (HzPbomLineRecord record : records) {
                HzPbomLineRespDTO respDTO = new HzPbomLineRespDTO();
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                respDTO.setLevel(strings[0] == null ? "" : strings[0]);
                respDTO.setRank(strings[1] == null ? "" : strings[1]);
                respDTO.setLineId(record.getLineId() == null ? "" : record.getLineId());
                respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept() == null ? "" : record.getpBomOfWhichDept());
                //获取分组号
                String groupNum = record.getLineId();
                //这里在做一个递归查询
                if(groupNum.contains("-")){
                    groupNum =groupNum.split("-")[1].substring(0,4);
                }else{
                    String parentId = record.getParentUid();
                    groupNum = hzEPLManageRecordService.getGroupNum(projectId,parentId);
                }
                respDTO.setGroupNum(groupNum);

                respDTO.setpBomLinePartClass(record.getpBomLinePartClass());
                respDTO.setpBomLinePartResource(record.getpBomLinePartResource());
                respDTO.setNo(++num);
                respDTO.setResource(record.getResource() == null ? "/" : record.getResource());
                Integer type = record.getType();
                Integer buyUnit = record.getBuyUnit();
                Integer colorPart = record.getColorPart();
                if (Integer.valueOf(0).equals(type)) {
                    respDTO.setType("Y");
                } else if (Integer.valueOf(1).equals(type)) {
                    respDTO.setType("N");
                } else {
                    respDTO.setType("/");
                }
                if (Integer.valueOf(0).equals(buyUnit)) {
                    respDTO.setBuyUnit("Y");
                } else if (Integer.valueOf(1).equals(buyUnit)) {
                    respDTO.setBuyUnit("N");
                } else {
                    respDTO.setBuyUnit("/");
                }
                if (Integer.valueOf(0).equals(colorPart)) {
                    respDTO.setColorPart("Y");
                } else if (Integer.valueOf(1).equals(colorPart)) {
                    respDTO.setColorPart("N");
                } else {
                    respDTO.setColorPart("/");
                }
                respDTO.seteBomPuid(record.geteBomPuid());
                respDTO.setPuid(record.getPuid());
                respDTO.setProductLine(record.getProductLine());
                respDTO.setWorkShop1(record.getWorkShop1() );
                respDTO.setWorkShop2(record.getWorkShop2());
                respDTO.setMouldType(record.getMouldType() );
                respDTO.setOuterPart(record.getOuterPart());
                respDTO.setStation(record.getStation());
                respDTO.setOrderNum(record.getOrderNum());
                respDTOS.add(respDTO);
            }
            return respDTOS;
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException(e.getMessage(),e);
        }
        return null;
    }
}
