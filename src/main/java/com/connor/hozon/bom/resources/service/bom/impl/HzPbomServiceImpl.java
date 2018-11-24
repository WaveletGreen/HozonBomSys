package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0ModelService;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0OfBomLineService;
import com.connor.hozon.bom.bomSystem.service.interaction.HzCraftService;
import com.connor.hozon.bom.bomSystem.iservice.interaction.IHzCraftService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.*;
import com.connor.hozon.bom.resources.domain.dto.response.HzLouRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSimulateCraftingPartDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.bom.resources.domain.model.HzPbomRecordFactory;
import com.connor.hozon.bom.resources.domain.query.*;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.executors.ExecutorServices;
import com.connor.hozon.bom.resources.mybatis.accessories.HzAccessoriesLibsDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzApplicantChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.sys.entity.User;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sql.pojo.accessories.HzAccessoriesLibs;
import sql.pojo.bom.*;
import sql.pojo.cfg.fullCfg.HzCfg0OfBomLineRecord;
import sql.pojo.cfg.model.HzCfg0ModelRecord;
import sql.pojo.change.HzApplicantChangeRecord;
import sql.pojo.change.HzAuditorChangeRecord;
import sql.pojo.change.HzChangeDataRecord;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.connor.hozon.bom.resources.domain.model.HzBomSysFactory.getLevelAndRank;

/**
 * Created by haozt on 2018/5/24
 */
@Service("HzPbomService")
public class HzPbomServiceImpl implements HzPbomService {
    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzCfg0OfBomLineService hzCfg0OfBomLineService;

    @Autowired
    private HzEPLManageRecordService hzEPLManageRecordService;
    //工艺辅料库
    @Autowired
    private HzAccessoriesLibsDAO hzAccessoriesLibsDAO;

    @Autowired
    IHzCraftService iHzCraftService;

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    @Autowired
    private HzCfg0ModelService hzCfg0ModelService;

    @Autowired
    private HzChangeDataRecordDAO hzChangeDataRecordDAO;

    @Autowired
    private HzApplicantChangeDAO hzApplicantChangeDAO;

    @Override
    public WriteResultRespDTO insertHzPbomRecord(AddHzPbomRecordReqDTO recordReqDTO) {
        WriteResultRespDTO respDTO = new WriteResultRespDTO();
        try {
            boolean b = PrivilegeUtil.writePrivilege();
            if (!b) {
                return WriteResultRespDTO.getFailPrivilege();
            }
            HzPbomLineRecord hzPbomRecord = new HzPbomLineRecord();
            hzPbomRecord.setCreateName(UserInfo.getUser().getUserName());
            hzPbomRecord.setUpdateName(UserInfo.getUser().getUserName());
            String buyUnit = recordReqDTO.getBuyUnit();
            String type = recordReqDTO.getType();
            if ("Y".equals(buyUnit)) {
                hzPbomRecord.setBuyUnit(1);
            } else if ("N".equals(buyUnit)) {
                hzPbomRecord.setBuyUnit(0);
            } else {
                hzPbomRecord.setBuyUnit(2);
            }
            if ("Y".equals(type)) {
                hzPbomRecord.setType(1);
            } else if ("N".equals(type)) {
                hzPbomRecord.setType(0);
            } else {
                hzPbomRecord.setType(2);
            }
            hzPbomRecord.setMouldType(recordReqDTO.getMouldType());
            hzPbomRecord.setOuterPart(recordReqDTO.getOuterPart());
            hzPbomRecord.setProductLine(recordReqDTO.getProductLine());
            hzPbomRecord.setStation(recordReqDTO.getStation());
            hzPbomRecord.setWorkShop1(recordReqDTO.getWorkShop1());
            hzPbomRecord.setWorkShop2(recordReqDTO.getWorkShop2());
            hzPbomRecord.seteBomPuid(recordReqDTO.geteBomPuid());
            Map<String, Object> map = new HashMap<>();
            map.put("pPuid", recordReqDTO.geteBomPuid());
            map.put("projectId", recordReqDTO.getProjectId());
            List<HzPbomLineRecord> hzPbomLineRecords = hzPbomRecordDAO.getPbomById(map);
            if (ListUtil.isNotEmpty(hzPbomLineRecords)) {
                int i = hzPbomRecordDAO.update(hzPbomRecord);
                if (i > 0) {
                    return WriteResultRespDTO.getSuccessResult();
                } else {
                    return WriteResultRespDTO.getFailResult();
                }
            }
            hzPbomRecord.setPuid(UUID.randomUUID().toString());
            Double maxOrderNum = hzPbomRecordDAO.getHzPbomMaxOrderNum(recordReqDTO.getProjectId());
            if (maxOrderNum == null) {
                maxOrderNum = 100.0;
            }
            hzPbomRecord.setOrderNum(maxOrderNum.intValue() + 100);
            List<HzPbomLineRecord> records = new ArrayList<>();
            records.add(hzPbomRecord);
            int i = hzPbomRecordDAO.insertList(records);
            if (i > 0) {
                respDTO.setErrMsg("操作成功！");
                respDTO.setErrCode(WriteResultRespDTO.SUCCESS_CODE);
                return respDTO;
            } else {
                respDTO.setErrMsg("操作失败，请稍后重试！");
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                return respDTO;
            }
        } catch (Exception e) {
            respDTO.setErrMsg("操作失败，请稍后重试！");
            respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
            return respDTO;
        }
    }

    @Override
    public WriteResultRespDTO updateHzPbomRecord(UpdateHzPbomRecordReqDTO recordReqDTO) {
        try {
            HZBomMainRecord record = hzBomMainRecordDao.selectByProjectPuid(recordReqDTO.getProjectId());
            if(record == null){
                return WriteResultRespDTO.getFailResult();
            }
            HzPbomLineRecord hzPbomRecord = new HzPbomLineRecord();
            hzPbomRecord.setUpdateName(UserInfo.getUser().getUserName());
            String buyUnit = recordReqDTO.getBuyUnit();
            String type = recordReqDTO.getType();
            if (buyUnit.equals("Y")) {
                hzPbomRecord.setBuyUnit(1);
            } else if (buyUnit.equals("N")) {
                hzPbomRecord.setBuyUnit(0);
            } else {
                hzPbomRecord.setBuyUnit(null);
            }
            if (type.equals("Y")) {
                hzPbomRecord.setType(1);
            } else if (type.equals("N")) {
                hzPbomRecord.setType(0);
            } else {
                hzPbomRecord.setType(null);
            }
            hzPbomRecord.setMouldType(recordReqDTO.getMouldType());
            hzPbomRecord.setOuterPart(recordReqDTO.getOuterPart());
            hzPbomRecord.setProductLine(recordReqDTO.getProductLine());
            hzPbomRecord.setStation(recordReqDTO.getStation());
            hzPbomRecord.setWorkShop1(recordReqDTO.getWorkShop1());
            hzPbomRecord.setWorkShop2(recordReqDTO.getWorkShop2());
            hzPbomRecord.setResource(recordReqDTO.getResource());
            hzPbomRecord.setStatus(2);
            hzPbomRecord.setBomDigifaxId(record.getPuid());
            hzPbomRecord.setLineId(recordReqDTO.getLineId());
            List<HzPbomLineRecord> recordList = new ArrayList<>();
            recordList.add(hzPbomRecord);
            int i = hzPbomRecordDAO.updateList(recordList);
            if (i > 0) {
                return WriteResultRespDTO.getSuccessResult();
            } else {
                return WriteResultRespDTO.getFailResult();
            }
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public WriteResultRespDTO deleteHzPbomRecordByForeignId(DeleteHzPbomReqDTO reqDTO) {
        try {

            if (StringUtils.isBlank(reqDTO.getProjectId())|| StringUtils.isBlank(reqDTO.getProjectId())) {
                return WriteResultRespDTO.IllgalArgument();
            }
            String bomPuids[] = reqDTO.getPuids().trim().split(",");
            for (String puid : bomPuids) {
                HzPbomTreeQuery treeQuery = new HzPbomTreeQuery();
                treeQuery.setProjectId(reqDTO.getProjectId());
                treeQuery.setPuid(puid);
                List<HzPbomLineRecord> lineRecords = hzPbomRecordDAO.getHzPbomTree(treeQuery);//自己
                Set<String> set = new HashSet<>();//去除重复
                if (ListUtil.isNotEmpty(lineRecords)) {
                    for (int i = 0; i < lineRecords.size(); i++) {
                        HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                        hzPbomTreeQuery.setPuid(lineRecords.get(0).getParentUid());
                        hzPbomTreeQuery.setProjectId(reqDTO.getProjectId());
                        List<HzPbomLineRecord> records = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);//父亲
                        if (ListUtil.isNotEmpty(records)) {
                            if (records.size() - lineRecords.size() == 1) {
                                HzPbomLineRecord hzPbomLineRecord = records.get(0);
                                hzPbomLineRecord.setIsHas(0);
                                hzPbomLineRecord.setIsPart(1);
                                if (hzPbomLineRecord.getIs2Y().equals(1)) {
                                    hzPbomLineRecord.setIs2Y(0);
                                }
                                hzPbomRecordDAO.update(hzPbomLineRecord);
                            }

                        }
                        set.add(lineRecords.get(i).geteBomPuid());
                    }
                }
                List<DeleteHzPbomReqDTO> list = new ArrayList<>();
                for (String s : set) {
                    DeleteHzPbomReqDTO deleteHzPbomReqDTO = new DeleteHzPbomReqDTO();
                    deleteHzPbomReqDTO.seteBomPuid(s);
                    list.add(deleteHzPbomReqDTO);
                }
                if (ListUtil.isNotEmpty(list)) {
                    hzPbomRecordDAO.deleteList(list);//mabatis 做批量更新时 返回值为-1 所以这里根据异常情况来判断成功与否
                }
            }
            return WriteResultRespDTO.getSuccessResult();
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
    }


    @Override
    public JSONArray getPbomForProcessCompose(HzPbomProcessComposeReqDTO reqDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", reqDTO.getProjectId());
        map.put("lineId", null == reqDTO.getLineId() ? null : reqDTO.getLineId().trim());
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
        map.put("pPuid", reqDTO.getPuid());
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
            jsonObject.put("pBomOfWhichDept", record.getpBomOfWhichDept() == null ? "" : record.getpBomOfWhichDept());
            String groupNum = "";
            //这里在做一个递归查询
            if (groupNum.contains("-")) {
                try {
                    groupNum = groupNum.split("-")[1].substring(0, 4);
                } catch (Exception e) {
                    groupNum = "-";
                }
            } else {
                String parentId = record.getParentUid();
                groupNum = hzEPLManageRecordService.getGroupNum(reqDTO.getProjectId(), parentId);
            }
            jsonObject.put("groupNum", groupNum);
            jsonObject.put("eBomPuid", record.geteBomPuid());
            jsonObject.put("lineId", record.getLineId());

            jsonObject.put("pBomLinePartName", record.getpBomLinePartName());
            jsonObject.put("pBomLinePartClass", record.getpBomLinePartClass());
            jsonObject.put("pBomLinePartResource", record.getpBomLinePartResource());
            jsonObject.put("pBomLinePartEnName", record.getpBomLinePartEnName());

            jsonObject.put("resource", record.getResource());
            jsonObject.put("workShop1", record.getWorkShop1());
            jsonObject.put("workShop2", record.getWorkShop2());
            jsonObject.put("productLine", record.getProductLine());
            jsonObject.put("mouldType", record.getMouldType());
            jsonObject.put("outerPart", record.getOuterPart());
            jsonObject.put("station", record.getStation());
            //            测试用
            if (HzCraftService.CRAFT_DEBUG){
                jsonObject.put("lineIndex", record.getLineIndex());
                jsonObject.put("sortNum", record.getSortNum());
            }
            Integer type = record.getType();
            Integer buyUnit = record.getBuyUnit();
            if (Integer.valueOf(0).equals(type)) {
                jsonObject.put("type", "N");
            } else if (Integer.valueOf(1).equals(type)) {
                jsonObject.put("type", "Y");
            } else {
                jsonObject.put("type", "");
            }
            if (Integer.valueOf(0).equals(buyUnit)) {
                jsonObject.put("buyUnit", "N");
            } else if (Integer.valueOf(1).equals(buyUnit)) {
                jsonObject.put("buyUnit", "Y");
            } else {
                jsonObject.put("buyUnit", "");
            }
            jsonArray.add(jsonObject);
            return jsonArray;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public Page<HzPbomLineRespDTO> getHzPbomRecordPage(HzPbomByPageQuery query) {

        String level = query.getLevel();
        if (StringUtils.isNotBlank(level)) {
            if (level.trim().toUpperCase().endsWith("Y")) {
                int length = Integer.valueOf(level.replace("Y", ""));
                query.setIsHas(1);
                query.setLineIndex(String.valueOf(length - 1));
            } else {
                query.setIsHas(0);
                int length = Integer.valueOf(level.trim());
                query.setLineIndex(String.valueOf(length));
            }
        }
        //数据同步  临时
//        int count = hzPbomRecordDAO.getHzBomLineCount(query.getProjectId());
//        if (count <= 0) {
//            List<HzBomLineRecord> lineRecords = hzBomLineRecordDao.getLoadingCarPartBom(query.getProjectId());
//            if (ListUtil.isNotEmpty(lineRecords)) {
//                int size = lineRecords.size();
//                //分批插入数据 一次1000条
//                int i = 0;
//                int cou = 0;
//                if (size > 1000) {
//                    for (i = 0; i < size / 1000; i++) {
//                        List<HzPbomLineRecord> list = new ArrayList<>();
//                        for (int j = 0; j < 1000; j++) {
//                            HzPbomLineRecord hzPbomLineRecord = bomLineToPbomLine(lineRecords.get(cou));
//                            if(Integer.valueOf(1).equals(hzPbomLineRecord.getIsHas())){
//                                boolean findChildren = false;
//                                for(HzBomLineRecord r :lineRecords){
//                                    if(hzPbomLineRecord.geteBomPuid().equals(r.getParentUid())){
//                                        findChildren = true;
//                                        break;
//                                    }
//                                }
//                                if(!findChildren){
//                                    hzPbomLineRecord.setIsHas(0);
//                                }
//                            }
//                            list.add(hzPbomLineRecord);
//                            cou++;
//                        }
//                        hzPbomRecordDAO.insertList(list);
//                    }
//                }
//                if (i * 1000 < size) {
//                    List<HzPbomLineRecord> list = new ArrayList<>();
//                    for (int j = 0; j < size - i * 1000; j++) {
//                        HzPbomLineRecord hzPbomLineRecord = bomLineToPbomLine(lineRecords.get(cou));
//                        if(Integer.valueOf(1).equals(hzPbomLineRecord.getIsHas())){
//                            boolean findChildren = false;
//                            for(HzBomLineRecord r :lineRecords){
//                                if(hzPbomLineRecord.geteBomPuid().equals(r.getParentUid())){
//                                    findChildren = true;
//                                    break;
//                                }
//                            }
//                            if(!findChildren){
//                                hzPbomLineRecord.setIsHas(0);
//                            }
//                        }
//                        list.add(hzPbomLineRecord);
//                        cou++;
//                    }
//                    hzPbomRecordDAO.insertList(list);
//                }
//            }
//        }
        try {
            Page<HzPbomLineRecord> recordPage;
            if(Integer.valueOf(1).equals(query.getShowBomStructure())){
                //展示BOM结构树 当前查询树结构平铺
                 recordPage = hzPbomRecordDAO.getPbomTreeByPage(query);
            }else {
                //展示全部平铺结构
                 recordPage = hzPbomRecordDAO.getHzPbomRecordByPage(query);
            }
            if (recordPage == null || recordPage.getResult() == null) {
                return null;
            }
            List<HzPbomLineRecord> records = recordPage.getResult();
            int num = (recordPage.getPageNumber() - 1) * recordPage.getPageSize();
            List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(query.getProjectId());

            List<HzPbomLineRespDTO> respDTOS = pbomLineRecordToRespDTOS(records, query.getProjectId(),hzCfg0ModelRecords,num);
            return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), recordPage.getTotalCount(), respDTOS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HzPbomLineRespDTO getHzPbomByPuid(String projectId, String puid) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("projectId", projectId);
            map.put("pPuid", puid);
            List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomById(map);
            if (ListUtil.isNotEmpty(records)) {
                HzPbomLineRecord record = records.get(0);
                records = new ArrayList<>();
                records.add(record);
                List<HzPbomLineRespDTO> respDTOS = pbomLineRecordToRespDTOS(records, projectId,null, 0);
                return respDTOS.get(0);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public List<HzPbomLineRecord> getHzPbomLineTree(HzPbomTreeQuery query) {
        return hzPbomRecordDAO.getHzPbomTree(query);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WriteResultRespDTO andProcessCompose(AddHzPbomRecordReqDTO recordReqDTO) {
        WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
        try {
            if (recordReqDTO.getProjectId() == null || recordReqDTO.getProjectId().equals("")) {
                writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                writeResultRespDTO.setErrMsg("非法参数！");
                return writeResultRespDTO;
            }

            if (recordReqDTO.getPuids() == null || recordReqDTO.getPuids().equals("")) {
                writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                writeResultRespDTO.setErrMsg("请至少选择一个合成零件！");
                return writeResultRespDTO;
            }
            if (recordReqDTO.getLineId() != null) {//第一次合成 选择零件添加到新将要合成零件编号下
                if (recordReqDTO.getLineId().contains("-")) {
                    if (recordReqDTO.getLineId().split("-")[1].length() < 4) {
                        writeResultRespDTO.setErrMsg("零件号-后面的长度不能小于4！");
                        writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                        return writeResultRespDTO;
                    }
                }
                boolean b = hzPbomRecordDAO.checkItemIdIsRepeat(recordReqDTO.getProjectId(), recordReqDTO.getLineId());
                if (b) {
                    writeResultRespDTO.setErrMsg("当前零件号已存在，请重新输入！");
                    writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    return writeResultRespDTO;
                }
                String puid = UUID.randomUUID().toString();
                HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(recordReqDTO.getProjectId());
                if (hzBomMainRecord == null) {
                    return WriteResultRespDTO.getFailResult();
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
                Double num = hzPbomRecordDAO.getHzPbomMaxOrderNum(recordReqDTO.getProjectId());
                if (num == null) {
                    num = 100.0;
                }
                hzPbomLineRecord.setSortNum(String.valueOf(num + 100));
                hzPbomLineRecord.seteBomPuid(puid);
                Integer maxLineIndexFirstNum = hzPbomRecordDAO.getMaxLineIndexFirstNum(recordReqDTO.getProjectId());
                if (maxLineIndexFirstNum == null) {
                    maxLineIndexFirstNum = 10;
                }
                StringBuffer buffer = new StringBuffer(String.valueOf(++maxLineIndexFirstNum));

                hzPbomLineRecord.setLineIndex(buffer.append(".10").toString());
                String lineId = recordReqDTO.getLineId();
                hzPbomLineRecord.setLineId(lineId);
                hzPbomLineRecord.setIsDept(0);
                hzPbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                hzPbomLineRecord.setCreateName(UserInfo.getUser().getUserName());
                hzPbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
                String buyUnit = recordReqDTO.getBuyUnit();
                String type = recordReqDTO.getType();
                if ("Y".equals(buyUnit)) {
                    hzPbomLineRecord.setBuyUnit(1);
                } else if ("N".equals(buyUnit)) {
                    hzPbomLineRecord.setBuyUnit(0);
                } else {
                    hzPbomLineRecord.setBuyUnit(2);
                }
                if ("Y".equals(type)) {
                    hzPbomLineRecord.setType(1);
                } else if ("N".equals(type)) {
                    hzPbomLineRecord.setType(0);
                } else {
                    hzPbomLineRecord.setType(null);
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
                    return WriteResultRespDTO.getFailResult();
                }
                String[] puids = recordReqDTO.getPuids().trim().split(",");
                for (String puidStr : puids) {
                    HzPbomLineRecord record = hzPbomRecordDAO.getHzPbomByEbomPuid(puidStr, recordReqDTO.getProjectId());
                    if (record == null) {
                        if (recordReqDTO.getPuids() == null || recordReqDTO.getPuids().equals("")) {
                            writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                            writeResultRespDTO.setErrMsg("选择要合成的零件不存在！");
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
                        max = max + 10;
                        record.setLineIndex(new StringBuffer(recordList.get(0).getLineIndex()).append("." + max).toString());
                        hzPbomRecordDAO.update(record);
                    } else {
                        return WriteResultRespDTO.getFailResult();
                    }
                }
                return WriteResultRespDTO.getSuccessResult();
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
                            return WriteResultRespDTO.getFailResult();
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
                            int length = lineIndex.split("\\.").length;
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
                                max = max + 10;
                                parentLineIndex = new StringBuffer(lineIndex).append("." + max).toString();
                                if (childRecord.getIs2Y().equals(1) && childRecord.getLineIndex().split("\\.").length == 2) {
                                    childRecord.setIs2Y(0);
                                }
                                childRecord.setLineIndex(parentLineIndex);
                            } else {
                                writeResultRespDTO.setErrMsg("要合成的节点不存在，请核对改节点是否被删除！");
                                writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                                return writeResultRespDTO;
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
                                    int length = lineIndex.split("\\.").length;
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
                                    max = max + 10;
                                    parentLineIndex = new StringBuffer(lineIndex).append("." + max).toString();
                                    childRecord.setLineIndex(parentLineIndex);
                                    hzPbomRecordDAO.update(childRecord);
                                }
                            }
                        }
                    }
                    int count = 10;
                    for (String child : children) {
                        if (childrenParentPuid.equals(child)) {
                            continue;
                        }
                        HzPbomLineRecord childRecord = hzPbomRecordDAO.getHzPbomByEbomPuid(child, recordReqDTO.getProjectId());
                        if (childRecord.getLineIndex().split("\\.").length > 2) {//找合成 子层
                            StringBuffer buffer = new StringBuffer(parentLineIndex);
                            buffer.append("." + count);
                            count += 10;
                            childRecord.setLineIndex(buffer.toString());
                            hzPbomRecordDAO.update(childRecord);
                        }
                    }
                } else {
                    writeResultRespDTO.setErrMsg("要合成的节点不存在，请核对改节点是否被删除！");
                    writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    return writeResultRespDTO;
                }
            }
            return WriteResultRespDTO.getSuccessResult();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public Page<HzPbomLineRespDTO> getHzPbomRecycleByPage(HzBomRecycleByPageQuery query) {
        try {
            Page<HzPbomLineRecord> recordPage = hzPbomRecordDAO.getHzPbomRecycleRecord(query);
            if (recordPage == null || recordPage.getResult() == null) {
                return null;
            }
            List<HzPbomLineRecord> records = recordPage.getResult();
            int num = (recordPage.getPageNumber() - 1) * recordPage.getPageSize();
            List<HzPbomLineRespDTO> respDTOS = pbomLineRecordToRespDTOS(records, query.getProjectId(),null, num);
            return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), recordPage.getTotalCount(), respDTOS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public WriteResultRespDTO recoverDeletePbomRecord(String projectId, String puid) {
        WriteResultRespDTO respDTO = new WriteResultRespDTO();
        try {
            if (StringUtil.isEmpty(projectId) || StringUtil.isEmpty(puid)) {
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                respDTO.setErrMsg("非法参数");
                return respDTO;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("projectId", projectId);
            map.put("pPuid", puid);
            List<HzPbomLineRecord> recordList = hzPbomRecordDAO.getPbomById(map);
            if (ListUtil.isNotEmpty(recordList)) {
                respDTO.setErrMsg("当前要恢复对象已存在bom系统中！");
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                return respDTO;
            }
            map.put("status", 0);//已删除的bom
            recordList = hzPbomRecordDAO.getPbomById(map);
            if (ListUtil.isNotEmpty(recordList)) {
                HzPbomLineRecord record = recordList.get(0);
                if (record.getLineIndex().split("\\.").length == 2) {
                    respDTO.setErrMsg("2Y层结构无法恢复！");
                    respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    return respDTO;
                }
                Map<String, Object> map1 = new HashMap<>();
                map1.put("projectId", projectId);
                map1.put("pPuid", record.getParentUid());
                recordList = hzPbomRecordDAO.getPbomById(map1);
                if (ListUtil.isEmpty(recordList)) {
                    respDTO.setErrMsg("当前要恢复对象的父结构不存在，无法恢复！");
                    respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    return respDTO;
                } else {
                    if (recordList.get(0).getIsHas().equals(0)) {
                        HzPbomLineRecord lineRecord = recordList.get(0);
                        lineRecord.setIsHas(1);
                        lineRecord.setIsPart(0);
                        if (lineRecord.getLineIndex().split("\\.").length == 2 && lineRecord.getIs2Y().equals(0)) {
                            lineRecord.setIs2Y(1);
                        }
                        int i = hzPbomRecordDAO.update(lineRecord);
                        if (i <= 0) {
                            return WriteResultRespDTO.getFailResult();
                        }
                    }
                    int i = hzPbomRecordDAO.recoverBomById(record.geteBomPuid());
                    if (i > 0) {
                        return WriteResultRespDTO.getSuccessResult();
                    }
                }
            }
            return WriteResultRespDTO.getFailResult();
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public WriteResultRespDTO setCurrentBomAsLou(SetLouReqDTO reqDTO) {
        try {
            if (reqDTO.getLineIds() == null || reqDTO.getProjectId() == null) {
                return WriteResultRespDTO.IllgalArgument();
            }
            boolean b = PrivilegeUtil.writePrivilege();
            if (!b) {
                return WriteResultRespDTO.getFailPrivilege();
            }
            String[] lineIds = reqDTO.getLineIds().split(",");
            for (String lineId : lineIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("lineId", lineId);
                map.put("projectId", reqDTO.getProjectId());
                List<HzPbomLineRecord> pbomList = hzPbomRecordDAO.getPbomById(map);
                List<HzMbomLineRecord> mbomList = hzMbomRecordDAO.findHzMbomByPuid(map);
                if (ListUtil.isNotEmpty(pbomList)) {
                    pbomList.forEach(hzPbomLineRecord -> {
                        if (Integer.valueOf(1).equals(hzPbomLineRecord.getpLouaFlag())) {
                            hzPbomLineRecord.setpLouaFlag(2);
                        } else {
                            hzPbomLineRecord.setpLouaFlag(1);
                        }
                        hzPbomRecordDAO.update(hzPbomLineRecord);
                    });
                }
                if (ListUtil.isNotEmpty(mbomList)) {
                    mbomList.forEach(hzMbomLineRecord -> {
                        hzMbomLineRecord.setpLouaFlag(Integer.valueOf(1).equals(hzMbomLineRecord.getpLouaFlag()) ? 2 : 1);
                        hzMbomRecordDAO.update(hzMbomLineRecord);
                    });
                }

            }
            return WriteResultRespDTO.getSuccessResult();
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public HzPbomLineRecord findParentUtil2Y(String projectId, String puid) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("pPuid", puid);
        List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomById(map);
        if (ListUtil.isNotEmpty(records)) {
            if (records.get(0).getIs2Y().equals(1)) {
                return records.get(0);
            } else if (records.get(0).getParentUid() == null) {
                return records.get(0);
            } else {
                return findParentUtil2Y(projectId, records.get(0).getParentUid());
            }
        } else {
            return null;
        }
    }


    @Override
    public HzLouRespDTO getHzLouInfoById(HzLouaQuery query) {
        try {
            HzLouRespDTO respDTO = new HzLouRespDTO();
            HzPbomLineRecord hzBomLineRecord = findParentUtil2Y(query.getProjectId(), query.getPuid());
            if (hzBomLineRecord != null) {
                HzCfg0OfBomLineRecord record = hzCfg0OfBomLineService.doSelectByBLUidAndPrjUid(query.getProjectId(), hzBomLineRecord.geteBomPuid());
                if (record != null) {
                    respDTO.setCfg0Desc(record.getCfg0Desc());
                    respDTO.setCfg0FamilyDesc(record.getCfg0FamilyDesc());
                    respDTO.setpCfg0name(record.getpCfg0name());
                    respDTO.setpCfg0familyname(record.getpCfg0familyname());
                    return respDTO;
                }
            }
            return respDTO;
        } catch (Exception e) {
            return null;
        }
    }


    private List<HzPbomLineRespDTO> pbomLineRecordToRespDTOS(List<HzPbomLineRecord> records, String projectId,List<HzCfg0ModelRecord> list, int num) {
        try {
            List<HzPbomLineRespDTO> respDTOS = new ArrayList<>();
            for (HzPbomLineRecord record : records) {
                HzPbomLineRespDTO respDTO = new HzPbomLineRespDTO();
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                respDTO.setLevel(strings[0]);
                respDTO.setRank(strings[1]);
                respDTO.setLineNo(strings[2]);
                respDTO.setLineId(record.getLineId());
                respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept());
                //获取分组号
                String groupNum = "";
                //这里在做一个递归查询
                if (groupNum.contains("-")) {
                    try {
                        groupNum = groupNum.split("-")[1].substring(0, 4);
                    } catch (Exception e) {
                        groupNum = "-";
                    }
                } else {
                    String parentId = record.getParentUid();
                    groupNum = hzEPLManageRecordService.getGroupNum(projectId, parentId);
                }
                respDTO.setGroupNum(groupNum);

                respDTO.setpBomLinePartClass(record.getpBomLinePartClass());
                respDTO.setpBomLinePartResource(record.getpBomLinePartResource());
                respDTO.setNo(++num);
                respDTO.setResource(record.getResource());
                Integer type = record.getType();
                Integer buyUnit = record.getBuyUnit();
                if (Integer.valueOf(0).equals(type)) {
                    respDTO.setType("N");
                } else if (Integer.valueOf(1).equals(type)) {
                    respDTO.setType("Y");
                } else {
                    respDTO.setType("");
                }
                if (Integer.valueOf(0).equals(buyUnit)) {
                    respDTO.setBuyUnit("N");
                } else if (Integer.valueOf(1).equals(buyUnit)) {
                    respDTO.setBuyUnit("Y");
                } else {
                    respDTO.setBuyUnit("");
                }
                respDTO.seteBomPuid(record.geteBomPuid());
                respDTO.setPuid(record.getPuid());
                respDTO.setProductLine(record.getProductLine());
                respDTO.setWorkShop1(record.getWorkShop1());
                respDTO.setWorkShop2(record.getWorkShop2());
                respDTO.setMouldType(record.getMouldType());
                respDTO.setOuterPart(record.getOuterPart());
                respDTO.setStation(record.getStation());
                respDTO.setOrderNum(record.getOrderNum());
                respDTO.setpBomLinePartName(record.getpBomLinePartName());
                respDTO.setpBomLinePartEnName(record.getpBomLinePartEnName());
                if (Integer.valueOf(1).equals(record.getpLouaFlag())) {
                    respDTO.setpLouaFlag("LOU");
                }
                respDTO.setStatus(record.getStatus());
                if(null != record.getSingleVehDosage()){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject = hzSingleVehiclesServices.singleVehDosage(record.getSingleVehDosage(),list,jsonObject);
                    respDTO.setObject(jsonObject);
                }
                respDTOS.add(respDTO);
            }
            return respDTOS;
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 开始模拟合成工艺合件，不进行pBom数据的影响
     *
     * @param param
     * @return
     * @Author Fancyears·Maylos·Mayways
     */
    @Override
    public JSONObject simulateCraftingPart(Map<String, Object> param) {
        JSONObject result = new JSONObject();
        result.put("status", false);
        result.put("msg", "合成失败");
        if (param != null && param.size() > 0) {
            Object parentUidsObj = param.get("parentUids");
            Object childrenUidsObj = param.get("childrenUids");
            Object dataObj = param.get("data");
            Object projectUidObj = param.get("projectUid");
            //父层数据
            HzPbomTreeQuery query = new HzPbomTreeQuery();

            //新件的UID
            String newPartUid = UUIDHelper.generateUpperUid();

            List<HzSimulateCraftingPartDTO> dtos = new ArrayList<>();
            //偷偷过来的数据
            if (projectUidObj instanceof String) {
                query.setProjectId((String) projectUidObj);
            }
            //采集过来的数据
            if (dataObj instanceof LinkedHashMap) {
                HzSimulateCraftingPartDTO dto = new HzSimulateCraftingPartDTO();
                dto.setLineId((String) ((LinkedHashMap) dataObj).get("lineId"));
                dto.setParentUid("#");
                dto.setPuid(newPartUid);
                dtos.add(dto);
            }
            if (parentUidsObj instanceof ArrayList) {
                if (parentUidsObj != null && !((ArrayList) parentUidsObj).isEmpty()) {
                    loopToGetBom((ArrayList) parentUidsObj, query, newPartUid, dtos);
                }
            }
            //子层数据
            if (childrenUidsObj instanceof ArrayList) {
                if (childrenUidsObj != null && !((ArrayList) childrenUidsObj).isEmpty()) {
                    loopToGetBom((ArrayList) childrenUidsObj, query, newPartUid, dtos);
                }
            }

            result.put("status", true);
            result.put("data", dtos);
            result.put("collectedData", dataObj);
            result.put("projectUid", projectUidObj);
            result.put("msg", "合成成功");
        }
        return result;
    }

    /**
     * 真的生成工艺合件
     *
     * @param param
     * @return
     */
    @Override
    public JSONObject doGenerateProcessCompose(Map<String, Object> param) {
        JSONObject result = new JSONObject();
        if (param == null || param.size() != 5) {
            result.put("status", false);
            result.put("msg", "合成失败，操作数据不完全");
        }
        Object projectUidObj = param.get("projectUid");
        Object parentUidsObj = param.get("parentUids");
        Object childrenUidsObj = param.get("childrenUids");
        Object targetPointPuidsObj = param.get("targetPointPuids");
        Object collectedDataObj = param.get("collectedData");

        String projectUid = null;
        List<String> parentUids = null;
        List<String> childrenUids = null;
        List<String> targetUids = null;
        Map<String, String> collectedData = null;

        /**项目UID*/
        if (projectUidObj instanceof String) {
            projectUid = (String) projectUidObj;
        }
        /**合成源父层*/
        if (parentUidsObj instanceof ArrayList) {
            parentUids = (List<String>) parentUidsObj;
        }
        /**合成源子层*/
        if (childrenUidsObj instanceof ArrayList) {
            childrenUids = (List<String>) childrenUidsObj;
        }
        /**合成目标层*/
        if (targetPointPuidsObj instanceof ArrayList) {
            targetUids = (List<String>) targetPointPuidsObj;
        }
        /**新件数据*/
        if (collectedDataObj instanceof LinkedHashMap) {
            collectedData = (Map<String, String>) collectedDataObj;
        }
        if (iHzCraftService.autoCraft(projectUid, parentUids, childrenUids, targetUids, collectedData)) {
            result.put("status", true);
            List<String> codes = iHzCraftService.getTargetPartCodes();
            StringBuilder sb = null;
            if (codes != null && codes.size() > 0) {
                sb = new StringBuilder();
                for (int i = 0; i < codes.size(); i++) {
                    if (i != codes.size() - 1) {
                        sb.append(codes + "、");
                    } else {
                        sb.append(codes.get(i));
                    }
                }
            }
            if (sb != null) {
                result.put("msg", "合成新件:" + collectedData.get("lineId") + "成功，并成功挂载到" + sb + "上");
            } else {
                result.put("msg", "合成新件:" + collectedData.get("lineId") + "成功");
            }
        }
        return result;

    }

    @Override
    public WriteResultRespDTO dataToChangeOrder(AddDataToChangeOrderReqDTO reqDTO) {
        if(StringUtil.isEmpty(reqDTO.getPuids()) || StringUtil.isEmpty(reqDTO.getProjectId())
                || null == reqDTO.getOrderId()){
            return WriteResultRespDTO.IllgalArgument();
        }

        try {
            //获取申请人信息
//            User user = UserInfo.getUser();
//            Long applicantId = Long.valueOf(user.getId());

            //表单id
            Long orderId = reqDTO.getOrderId();

            //获取审核人信息
//            Long auditorId = reqDTO.getAuditorId();
            //数据库表名
            String tableName = ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName();
            //获取数据信息
            List<String> puids = Lists.newArrayList(reqDTO.getPuids().split(","));

            //统计操作数据
            Map<String,Object> map = new HashMap<>();

            //查询PBOM表数据 保存历史记录
            HzChangeDataDetailQuery query  = new HzChangeDataDetailQuery();
            query.setProjectId(reqDTO.getProjectId());
            query.setPuids(puids);
            query.setTableName(ChangeTableNameEnum.HZ_PBOM.getTableName());
            List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomRecordsByPuids(query);
            List<HzPbomLineRecord> afterRecords = new ArrayList<>();
            if(ListUtil.isNotEmpty(records)){
                //到 after表中查询看是否存在记录
                //存在记录则过滤 不存在记录则插入
                HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
                dataDetailQuery.setProjectId(reqDTO.getProjectId());
                dataDetailQuery.setOrderId(orderId);
                dataDetailQuery.setTableName(ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName());
                List<HzPbomLineRecord> recordList = hzPbomRecordDAO.getPbomRecordsByOrderId(dataDetailQuery);
                if(ListUtil.isEmpty(recordList)){
                    records.forEach(record -> {
                        HzPbomLineRecord manageRecord = HzPbomRecordFactory.bomLineRecordToBomRecord(record);
                        manageRecord.setOrderId(orderId);
                        afterRecords.add(manageRecord);
                    });
                }else {
                    for(int i=0;i<records.size();i++){
                        records.get(i).setOrderId(orderId);
                        for(HzPbomLineRecord record:recordList){
                            if(records.get(i).equals(record)){
                                records.remove(records.get(i));
                                i--;
                                break;
                            }
                        }
                    }
                    afterRecords.addAll(records);
                }

                map.put("pbomAfter",afterRecords);


                //修改发起流程后状态值
                List<HzPbomLineRecord> bomLineRecords = new ArrayList<>();
                for(HzPbomLineRecord record:records){
                    HzPbomLineRecord lineRecord = HzPbomRecordFactory.bomLineRecordToBomRecord(record);
                   //状态改为审核中
                    lineRecord.setStatus(5);

//                lineRecord.setTableName(ChangeTableNameEnum.HZ_PBOM.getTableName());
                    bomLineRecords.add(lineRecord);
                }


                map.put("pbomBefore",bomLineRecords);
                //保存以上获取信息
                //变更数据
                HzChangeDataRecord record = new HzChangeDataRecord();
                record.setOrderId(reqDTO.getOrderId());
                record.setTableName(tableName);
                map.put("changeData",record);
                //申请人
//                HzApplicantChangeRecord applicantChangeRecord = new HzApplicantChangeRecord();
//                applicantChangeRecord.setApplicantId(applicantId);
//                applicantChangeRecord.setOrderId(reqDTO.getOrderId());
//                applicantChangeRecord.setTableName(tableName);
//
//                map.put("applicant",applicantChangeRecord);
                //审核人
//                HzAuditorChangeRecord auditorChangeRecord = new HzAuditorChangeRecord();
//                auditorChangeRecord.setAuditorId(auditorId);
//                auditorChangeRecord.setOrderId(reqDTO.getOrderId());
//                auditorChangeRecord.setTableName(tableName);
//
//                map.put("auditor",auditorChangeRecord);


                //启动线程进行插入操作
                for(Map.Entry<String,Object> entry:map.entrySet()){
                    new ExecutorServices(1) {
                        @Override
                        public void action() {
                            switch (entry.getKey()){
                                case "pbomAfter":
                                    hzPbomRecordDAO.insertListForChange((List<HzPbomLineRecord>) entry.getValue(),tableName);
                                    break;
                                case "pbomBefore":
                                    hzPbomRecordDAO.updateList((List<HzPbomLineRecord>) entry.getValue());
                                    break;
                                case "changeData":
                                    hzChangeDataRecordDAO.insert((HzChangeDataRecord) entry.getValue());
                                    break;
//                                case "applicant":
//                                    hzApplicantChangeDAO.insert((HzApplicantChangeRecord) entry.getValue());
//                                    break;
//                                case "auditor" :
//                                    hzAuditorChangeDAO.insert((HzAuditorChangeRecord) entry.getValue());
//                                    break;
                                default:break;
                            }
                        }
                    }.execute();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getSuccessResult();
    }

    @Override
    public WriteResultRespDTO backBomUtilLastValidState(BomBackReqDTO reqDTO) {
        try{
            List<String> puids = Lists.newArrayList(reqDTO.getPuids().split(","));
            HzChangeDataDetailQuery query = new HzChangeDataDetailQuery();
            query.setProjectId(reqDTO.getProjectId());
            query.setPuids(puids);
            query.setTableName(ChangeTableNameEnum.HZ_PBOM.getTableName());
            StringBuffer deletePuids = new StringBuffer();
            List<HzPbomLineRecord> updateRecords = new ArrayList<>();
            List<HzPbomLineRecord> updateList = new ArrayList<>();
            Set<HzPbomLineRecord> set = new HashSet<>();
                List<HzPbomLineRecord> list = hzPbomRecordDAO.getPbomRecordsByPuids(query);
                //撤销 1找不存在版本记录的--删除    2找存在记录-直接更新数据为上个版本生效数据
                if(ListUtil.isNotEmpty(list)){
                    list.forEach(r->{
                        if(1==r.getIsHas()){
                            HzPbomTreeQuery ebomTreeQuery = new HzPbomTreeQuery();
                            ebomTreeQuery.setProjectId(reqDTO.getProjectId());
                            ebomTreeQuery.setPuid(r.geteBomPuid());
                            List<HzPbomLineRecord> l = hzPbomRecordDAO.getHzPbomTree(ebomTreeQuery);
                            if(ListUtil.isNotEmpty(l))
                                set.addAll(l);
                        }else {
                            set.add(r);
                        }
                    });
                }
                if(ListUtil.isNotEmpty(set)){
                    set.forEach(record -> {
                        if(StringUtils.isBlank(record.getRevision())){
                            deletePuids.append(record.getPuid()+",");
                        }else {
                            updateRecords.add(record);
                        }
                    });
                }
                if(ListUtil.isNotEmpty(updateRecords)){
                    HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
                    dataDetailQuery.setRevision(true);
                    dataDetailQuery.setProjectId(reqDTO.getProjectId());
                    dataDetailQuery.setTableName(ChangeTableNameEnum.HZ_PBOM_BEFORE.getTableName());
                    dataDetailQuery.setStatus(1);
                    updateRecords.forEach(record -> {
                        dataDetailQuery.setRevisionNo(record.getRevision());
                        HzPbomLineRecord manageRecord = hzPbomRecordDAO.getPBomRecordByPuidAndRevision(dataDetailQuery);
                        if(manageRecord!=null){
                            updateList.add(HzPbomRecordFactory.bomLineRecordToBomRecord(manageRecord));
                        }
                    });
                }
                if(ListUtil.isNotEmpty(updateList)){
                    hzPbomRecordDAO.updateList(updateList);
                }
                if(StringUtils.isNotBlank(deletePuids.toString())){
                    hzPbomRecordDAO.deleteListByPuids(deletePuids.toString());
                }
                return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    /**
     * 从List中获取树结构
     *
     * @param list       源List
     * @param query      查询树结构条件
     * @param newPartUid 新件UID
     * @param dtos       结果
     * @return
     */
    private List<HzSimulateCraftingPartDTO> loopToGetBom(ArrayList list, HzPbomTreeQuery query, String newPartUid, List<HzSimulateCraftingPartDTO> dtos) {
        for (int i = 0; i < ((ArrayList<String>) list).size(); i++) {
            String uid = (String) list.get(i);
            query.setPuid(uid);
            List<HzPbomLineRecord> pBomTree = hzPbomRecordDAO.getHzPbomTree(query);
            if (pBomTree != null) {
                for (int i1 = 0; i1 < pBomTree.size(); i1++) {
                    if (pBomTree.get(i1).geteBomPuid().equals(uid)) {
                        pBomTree.get(i1).setParentUid(newPartUid);
                    }
                    HzSimulateCraftingPartDTO dto = new HzSimulateCraftingPartDTO();
                    dto.setLineId(pBomTree.get(i1).getLineId());
                    dto.setParentUid(pBomTree.get(i1).getParentUid());
                    dto.setPuid(pBomTree.get(i1).geteBomPuid());
                    dtos.add(dto);
                }
            }
        }
        return dtos;
    }


    @Override
    public JSONObject queryAccessories(String materielCode) {
        HzAccessoriesLibs hzAccessoriesLibs = hzAccessoriesLibsDAO.queryAccessoriesByMaterielCode(materielCode);
        JSONObject jsonData = (JSONObject) JSONObject.toJSON(hzAccessoriesLibs);
        return jsonData;
    }

    @Override
    public JSONObject addAccessories(String puid, String materielCode, String projectId) {
        JSONObject result = new JSONObject();
        //projectPuid
        HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(projectId);
        String projectPuid = hzBomMainRecord.getPuid();

        //根据父的puid查找出所有跟父BomLineId相同的PBOM
        List<HzPbomLineRecord> hzPbomLineRecords = hzPbomRecordDAO.queryAllBomLineIdByPuid(puid, projectId);
        //根据materielCode查找工艺辅料
        HzAccessoriesLibs hzAccessoriesLibs = hzAccessoriesLibsDAO.queryAccessoriesByMaterielCode(materielCode);
        //生成子PBOM
        List<HzPbomLineRecord> hzPbomLineRecordsAddSons = new ArrayList<HzPbomLineRecord>();
        //循环父，生成父id、index、num不同的子
        for (HzPbomLineRecord hzPbomLineRecord : hzPbomLineRecords) {
            HzPbomLineRecord hzPbomLineRecordAddSon = new HzPbomLineRecord();
            //puid
            String puidSon = UUIDHelper.generateUid();
            //父id
            String fatherPuid = hzPbomLineRecord.geteBomPuid();
            hzPbomLineRecordAddSon.setParentUid(fatherPuid);


            //index
            //构建查询对象
            HzPbomTreeQuery query = new HzPbomTreeQuery();
            query.setPuid(fatherPuid);
            query.setProjectId(projectId);
            List<HzPbomLineRecord> pbomsons = hzPbomRecordDAO.getHzPbomTree(query);
            //父index位数
            int fatherIndexLength = hzPbomLineRecord.getLineIndex().split("\\.").length;
            String maxIndexStr = "";
            int maxIndexEnd = 0;
            String upNum = "";
            if (pbomsons.size() == 1) {
                maxIndexStr = pbomsons.get(0).getLineIndex() + ".";
                upNum = pbomsons.get(0).getSortNum();
            } else {
                for (HzPbomLineRecord pbomSon : pbomsons) {
                    String[] indexStrs = pbomSon.getLineIndex().split("\\.");
                    int pbomSonIndexLength = indexStrs.length;
                    if (pbomSonIndexLength - 1 == fatherIndexLength) {
                        Integer indexEnd = Integer.valueOf(indexStrs[indexStrs.length - 1]);
                        if (indexEnd > maxIndexEnd) {
                            maxIndexEnd = indexEnd;
                            maxIndexStr = pbomSon.getLineIndex();
                            upNum = pbomSon.getSortNum();
                        }
                    }
                }
            }

            int splitIndex = maxIndexStr.lastIndexOf(".");
            String indexStr = maxIndexStr.substring(0, splitIndex) + "." + String.valueOf(maxIndexEnd + 10);
            hzPbomLineRecordAddSon.setLineIndex(indexStr);

            //num
            String downNum = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectId, upNum);
            String num = HzBomSysFactory.generateBomSortNum(projectId, upNum, downNum);
            hzPbomLineRecordAddSon.setSortNum(num);

            //PUID
            hzPbomLineRecordAddSon.setPuid(puidSon);
            //P_E_BOM_PUID
            hzPbomLineRecordAddSon.seteBomPuid(puidSon);
            //P_BOM_LINE_PART_NAME
            hzPbomLineRecordAddSon.setpBomLinePartName(hzAccessoriesLibs.getpMaterielName());
            //P_BOM_LINE_ID
            hzPbomLineRecordAddSon.setLineId(hzAccessoriesLibs.getpMaterielCode());
            //P_BOM_DIGIFAX_ID
            hzPbomLineRecordAddSon.setBomDigifaxId(projectPuid);
            //is2Y
            hzPbomLineRecordAddSon.setIs2Y(0);
            //isHas
            hzPbomLineRecordAddSon.setIsHas(0);
            hzPbomLineRecordsAddSons.add(hzPbomLineRecordAddSon);
            //修改父的isHas
            if (hzPbomLineRecord.getIsHas() != 1) {
                hzPbomLineRecord.setIsHas(1);
                hzPbomRecordDAO.update(hzPbomLineRecord);
            }
        }
        if (hzPbomLineRecordsAddSons.size() > 0 && hzPbomLineRecordsAddSons != null) {
            int insertFlag = hzPbomRecordDAO.insertList(hzPbomLineRecordsAddSons);
            if (insertFlag == 1) {
                result.put("success", true);
            } else {
                result.put("success", false);
                result.put("errMsg", "添加失败");
            }
        } else {
            result.put("success", false);
            result.put("errMsg", "添加失败");
        }

        return result;
    }


}
