package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.query.HzEbomByPageQuery;
import com.connor.hozon.bom.resources.query.HzEbomTreeQuery;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import share.bean.PreferenceSetting;
import share.bean.RedisBomBean;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.redis.SerializeUtil;

import java.util.*;

import static com.connor.hozon.bom.resources.service.bom.impl.HzPbomServiceImpl.getLevelAndRank;

/**
 * Created by haozt on 2018/06/06
 */
@Service("HzEbomService")
public class HzEbomServiceImpl implements HzEbomService {

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    private HzBomDataDao hzBomDataDao;

    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Override
    public Page<HzEbomRespDTO> getHzEbomPage(HzEbomByPageQuery query) {
        try{
            int num = (query.getPage()-1)*query.getPageSize();
            HzEbomRespDTO recordRespDTO = new HzEbomRespDTO();
            JSONArray array = new JSONArray();
            List<HzEbomRespDTO> recordRespDTOList = new ArrayList<>();
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

            Page<HzEPLManageRecord> recordPage = hzEbomRecordDAO.getHzEbomPage(query);
            if(recordPage == null || recordPage.getResult() == null || recordPage.getResult().size()==0){
                return new Page<>(recordPage.getPageNumber(),recordPage.getPageSize(),0);
            }
            List<HzEPLManageRecord> records = recordPage.getResult();
            for(HzEPLManageRecord record:records){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("puid", record.getPuid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                jsonObject.put("No",++num);
                jsonObject.put("level", strings[0]);
                jsonObject.put("rank", strings[1]);
                jsonObject.put("pBomOfWhichDept", record.getpBomOfWhichDept());
                //获取分组号
                String groupNum = record.getLineID();
                //这里在做一个递归查询
//                if(groupNum.contains("-")){
//                    groupNum =groupNum.split("-")[1].substring(0,4);
//                }
//                else{
//                    String parentId = record.getParentUid();
//                    groupNum = hzEPLManageRecordService.getGroupNum(query.getProjectId(),parentId);
//                }
                byte[] bomLineBlock = record.getBomLineBlock();
                Object obj = SerializeUtil.unserialize(bomLineBlock);
                if (obj instanceof LinkedHashMap) {
                    if (((LinkedHashMap) obj).size() > 0) {
                        ((LinkedHashMap) obj).forEach((key, value) -> {

                            jsonObject.put((String) key, value);
                        });
                    }
                } else if (obj instanceof RedisBomBean) {
                    List<String> pSets = ((RedisBomBean) obj).getpSets();
                    List<String> pValues = ((RedisBomBean) obj).getpValues();
                    if (null != pSets && pSets.size() > 0 && null != pValues && pValues.size() > 0)
                        for (int i = 0; i < pSets.size(); i++) {
                            jsonObject.put(pSets.get(i), pValues.get(i));
                        }
                }
                jsonObject.put("groupNum", groupNum);
                jsonObject.put("lineId", record.getLineID());
                jsonObject.put("fna",record.getFna());


                jsonObject.put("pBomLinePartName", record.getpBomLinePartName());
                jsonObject.put("pBomLinePartClass", record.getpBomLinePartClass());
                jsonObject.put("pBomLinePartEnName",record.getpBomLinePartEnName());
                jsonObject.put("pBomLinePartResource", record.getpBomLinePartResource());
                jsonObject.put("pFastener", record.getpFastener());
                if(record.getIs2Y().equals(1)){
                    jsonObject.put("pLouaFlag","LOU");
                }else{
                    jsonObject.put("pLouaFlag","LOA");
                }
//                if(record.getP3cpartFlag().equals(0)){
//                    jsonObject.put("p3cpartFlag", "Y");
//                }else {
//                    jsonObject.put("p3cpartFlag", "N");
//                }
//                if(record.getpInOutSideFlag().equals(0)){
//                    jsonObject.put("pInOutSideFlag", "内饰件");
//                }else {
//                    jsonObject.put("pInOutSideFlag", "外饰件");
//                }
//                jsonObject.put("pUpc",record.getpUpc());
//                jsonObject.put("pFnaDesc", record.getpFnaDesc());
//                jsonObject.put("pUnit", record.getpUnit());
//                jsonObject.put("pPictureNo",record.getpPictureNo());
//                jsonObject.put("pPictureSheet", record.getpPictureSheet());
//                jsonObject.put("pMaterialHigh", record.getpMaterialHigh());
//                jsonObject.put("pMaterial1",record.getpMaterial1());
//                jsonObject.put("pMaterial2", record.getpMaterial2());
//                jsonObject.put("pMaterial3", record.getpMaterial3());
//                jsonObject.put("pDensity",record.getpDensity());
//                jsonObject.put("pMaterialStandard", record.getpMaterialStandard());
//                jsonObject.put("pSurfaceTreat", record.getpSurfaceTreat());
//                jsonObject.put("pTextureColorNum",record.getpTextureColorNum());
//                jsonObject.put("pManuProcess", groupNum);
//                jsonObject.put("pSymmetry", record.getLineID());
//                jsonObject.put("pImportance",record.getFna());
//                jsonObject.put("pRegulationFlag", groupNum);
//                jsonObject.put("pBwgBoxPart", record.getLineID());
//                jsonObject.put("pDevelopType",record.getFna());
//                jsonObject.put("pDataVersion", groupNum);
//                jsonObject.put("pTargetWeight", record.getLineID());
//                jsonObject.put("pFeatureWeight",record.getFna());
//                jsonObject.put("pActualWeight", groupNum);
//                jsonObject.put("pFastenerStandard", record.getLineID());
//                jsonObject.put("pFastenerLevel",record.getFna());
//
//                jsonObject.put("pTorque", record.getLineID());
//                jsonObject.put("pDutyEngineer",record.getFna());
//                jsonObject.put("pSupply", groupNum);
//                jsonObject.put("pSupplyCode", record.getLineID());
//                jsonObject.put("pRemark",record.getFna());
//                jsonObject.put("pRegulationCode", groupNum);


                array.add(jsonObject);
            }
            recordRespDTO.setJsonArray(array);
            recordRespDTOList.add(recordRespDTO);
            return new Page<>(recordPage.getPageNumber(),recordPage.getPageSize(),recordPage.getTotalCount(),recordRespDTOList);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public JSONArray getEbomTitle(String projectId) {
        try{
            JSONArray array = new JSONArray();
            int appendCount = 8;
            HzPreferenceSetting setting = new HzPreferenceSetting();
            setting.setSettingName("Hz_ExportBomPreferenceRedis");
            HZBomMainRecord main = hzBomMainRecordDao.selectByProjectPuid(projectId);
            if(main == null){
                return null;
            }
            setting.setBomMainRecordPuid(main.getPuid());
            setting = hzBomDataDao.loadSetting(setting);
            byte[] btOfSetting = setting.getPreferencesettingblock();
            Object objOfSetting = SerializeUtil.unserialize(btOfSetting);
            if (objOfSetting instanceof PreferenceSetting) {
                String[] localName = ((PreferenceSetting) objOfSetting).getPreferenceLocal();
                String[] trueName = ((PreferenceSetting) objOfSetting).getPreferences();
                String[] appendLocalName = new String[localName.length + appendCount];
                String[] appendTrueName = new String[trueName.length + appendCount];

                appendLocalName[0] = "序号";
                appendLocalName[1] = "puid";
                appendLocalName[2] = "层级";
                appendLocalName[3] = "专业";
                appendLocalName[4] = "级别";
                appendLocalName[5] = "分组号";
                appendLocalName[6] = "FNA";
                appendLocalName[6] = "零部件来源";

                appendTrueName[0] = "No";
                appendTrueName[1] = "puid";
                appendTrueName[2] = "level";
                appendTrueName[3] = "pBomOfWhichDept";
                appendTrueName[4] = "rank";
                appendTrueName[5] = "groupNum";
                appendTrueName[6] = "fna";
                appendTrueName[6] = "pBomLinePartResource";
                System.arraycopy(localName, 0, appendLocalName, appendCount, localName.length);
                System.arraycopy(trueName, 0, appendTrueName, appendCount, trueName.length);

                array.add(0, appendLocalName);
                array.add(1, appendTrueName);
            }
            return array;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public HzEbomRespDTO fingEbomById(String puid,String projectId) {
        try{
            HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid,projectId);
            HzEbomRespDTO respDTO = new HzEbomRespDTO();
            if(record!=null){
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
                jsonObject.put("lineId", record.getLineID());
                byte[] bomLineBlock = record.getBomLineBlock();
                Object obj = SerializeUtil.unserialize(bomLineBlock);
                if (obj instanceof LinkedHashMap) {
                    if (((LinkedHashMap) obj).size() > 0) {
                        ((LinkedHashMap) obj).forEach((key, value) -> {

                            jsonObject.put((String) key, value);
                        });
                    }
                } else if (obj instanceof RedisBomBean) {
                    List<String> pSets = ((RedisBomBean) obj).getpSets();
                    List<String> pValues = ((RedisBomBean) obj).getpValues();
                    if (null != pSets && pSets.size() > 0 && null != pValues && pValues.size() > 0)
                        for (int i = 0; i < pSets.size(); i++) {
                            jsonObject.put(pSets.get(i), pValues.get(i));
                        }
                }
                jsonArray.add(jsonObject);
                respDTO.setJsonArray(jsonArray);
            }
            return respDTO;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 添加EBOM  最好使用事务
     * @param reqDTO
     * @return
     */
    @Override
    public OperateResultMessageRespDTO addHzEbomRecord(AddHzEbomReqDTO reqDTO) {
        try{
            Map<String,Object> ebomContent = reqDTO.getMap();
            String pBomOfWhichDept="";
            String itemId = "";
            String parentPuid ="";
            if(ebomContent.containsKey("pBomOfWhichDept")){
                pBomOfWhichDept = (String)ebomContent.get("pBomOfWhichDept");
                ebomContent.remove("pBomOfWhichDept");
            }
            if(ebomContent.containsKey("bl_item_item_id")){
                itemId = (String)ebomContent.get("bl_item_item_id");
            }
            if(ebomContent.containsKey("item_id")){
                itemId = (String)ebomContent.get("item_id");
            }
            if(ebomContent.containsKey("puid")){
                parentPuid = (String)ebomContent.get("puid");
                ebomContent.remove("puid");
            }
            String parentId =parentPuid;
            boolean isRepeat = hzEbomRecordDAO.checkItemIdIsRepeat(reqDTO.getProjectId(),itemId);
            if(isRepeat){
                OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                respDTO.setErrMsg("当前零件号已存在,请重新添加！");
                return respDTO;
            }
            int i;
            if(parentId != null && parentId!=""){
                //增加到当前父结构下面
                Map<String, Object> map = new HashMap<>();
                map.put("puid", parentId);
                map.put("projectId", reqDTO.getProjectId());
                HzBomLineRecord record = hzBomLineRecordDao.findBomLineByPuid(map);
                if (record != null) {
                    if (record.getIsHas().equals(0) || record.getIsPart().equals(1)) {
                        record.setIsHas(new Integer(1));
                        record.setIsPart(new Integer(0));
                        if (record.getLineIndex().length() == 1) {
                            record.setIs2Y(1);
                        }
                        //更新数据
                        hzBomLineRecordDao.update(record);
                    }
                } else {
                    return OperateResultMessageRespDTO.getFailResult();
                }

                HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
                if (hzBomMainRecord != null) {
                    hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                } else {
                    return OperateResultMessageRespDTO.getFailResult();
                }
                //EBOM的零件信息存大对象 P_BOM_LINE_BLOCK 中
                Map<String, Object> objectMap = ebomContent;
                byte[] bytes = SerializeUtil.serialize(objectMap);
                hzBomLineRecord.setBomLineBlock(bytes);
                hzBomLineRecord.setIsPart(1);
                hzBomLineRecord.setIsHas(0);
                //获取当前对象的所有的子层 lineIndex值存长度相等的子层最大值末尾自增
                String lineIndex = record.getLineIndex();
                HzEbomTreeQuery query = new HzEbomTreeQuery();
                query.setProjectId(reqDTO.getProjectId());
                query.setPuid(parentId);
                List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(query);
                if (ListUtil.isEmpty(records)) {
                    //1.1-1.1.1  1.2.2.2 -1.2.2.2.1
                    StringBuffer stringBuffer = new StringBuffer(lineIndex);
                    stringBuffer = stringBuffer.append(".1");
                    hzBomLineRecord.setLineIndex(stringBuffer.toString());
                } else {
                    int length = lineIndex.split("\\.").length + 1;
                    List<String> list = new ArrayList<>();
                    for (HzEPLManageRecord lineRecord : records) {
                        int len = lineRecord.getLineIndex().split("\\.").length;
                        if (length == len) {
                            list.add(lineRecord.getLineIndex());
                        }
                    }
                    Integer max = 0;
                    //找出lineindex 末尾最大值
                    for (int j = 0; j < list.size() - 1; j++) {
                        String str = list.get(j).split("\\.")[list.get(j).split("\\.").length - 1];
                        if (max < Integer.valueOf(str)) {
                            max = Integer.valueOf(str);
                        }
                    }
                    max = max + 1;
                    hzBomLineRecord.setLineIndex(new StringBuffer(lineIndex).append("." + max).toString());
                }
                //只有2Y层有这个玩意
                hzBomLineRecord.setpBomOfWhichDept(pBomOfWhichDept);
                int orderNum = hzBomLineRecordDao.findMaxBomOrderNum();
                hzBomLineRecord.setOrderNum(++orderNum);
                hzBomLineRecord.setParentUid(parentId);
                hzBomLineRecord.setIs2Y(0);
                String puid = UUID.randomUUID().toString();
                hzBomLineRecord.setLinePuid(puid);
                hzBomLineRecord.setPuid(puid);
                hzBomLineRecord.setIsDept(0);
                hzBomLineRecord.setLineID(itemId);
                hzBomLineRecord.setpBomLinePartClass("");
                hzBomLineRecord.setpBomLinePartName("");
                i = hzBomLineRecordDao.insert(hzBomLineRecord);
                if(i>0){
                    return OperateResultMessageRespDTO.getSuccessResult();
                }
            }else{
                //自己搭建父结构 默认为2层 有子层时更新为2Y层
                HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
                if(hzBomMainRecord == null){
                    return OperateResultMessageRespDTO.getFailResult();
                }
                hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());

                byte[] bytes = SerializeUtil.serialize(ebomContent);
                hzBomLineRecord.setBomLineBlock(bytes);
                hzBomLineRecord.setIsPart(1);
                hzBomLineRecord.setIsHas(0);
                Map<String,Object> map = new HashMap();
                map.put("projectId",reqDTO.getProjectId());
                //找出全部的2y或者2层信息，新增的EBOM的层级 按2y或者2层的最大值开始增
                List<String> list = hzBomLineRecordDao.findBomLineIndex(map);
                List<Integer> lists = new ArrayList<>();
                for(String str:list){
                    lists.add(Integer.valueOf(str.split("\\.")[0]));
                }
                Integer max = 0;
                for(Integer in:lists){
                    if(max<in){
                        max = in;
                    }
                }
                max = max+1;
                StringBuffer buffer = new StringBuffer(String.valueOf(max));
                hzBomLineRecord.setLineIndex(buffer.append(".1").toString());
                int maxGroupNum =hzBomLineRecordDao.findMaxBomOrderNum();
                hzBomLineRecord.setOrderNum(++maxGroupNum);
                String puid = UUID.randomUUID().toString();
                hzBomLineRecord.setPuid(puid);
                hzBomLineRecord.setpBomOfWhichDept(pBomOfWhichDept);
                hzBomLineRecord.setLinePuid(puid);
                hzBomLineRecord.setLineID(itemId);
                hzBomLineRecord.setIsDept(0);
                hzBomLineRecord.setIs2Y(0);
                i = hzBomLineRecordDao.insert(hzBomLineRecord);
//                HzBomState hzBomState = new HzBomState();
//                hzBomState.setpBomId(puid);
//                hzBomState.setPuid(UUID.randomUUID().toString());
//                hzBomState.setpBomState(0);
//               int j =  hzBomStateDAO.insert(hzBomState);
               if(i>0){
                   return OperateResultMessageRespDTO.getSuccessResult();
               }
            }
            return OperateResultMessageRespDTO.getFailResult();
        }catch (Exception e){
            return  OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public OperateResultMessageRespDTO updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO) {
        try{
            Map<String,Object> ebomContent = reqDTO.getUpdateContent();
            String puid = "";
            String pBomOfWhichDept = "";
            String itemId="";
            if(ebomContent.containsKey("puid")){
                puid = (String)ebomContent.get("puid");
                ebomContent.remove("puid");
            }
            if(ebomContent.containsKey("pBomOfWhichDept")){
                pBomOfWhichDept = (String)ebomContent.get("pBomOfWhichDept");
                ebomContent.remove("pBomOfWhichDept");
            }

            if(ebomContent.containsKey("lineId")){
                itemId = (String)ebomContent.get("lineId");
            }
//            boolean isRepeat = hzEbomRecordDAO.checkItemIdIsRepeat(reqDTO.getProjectId(),itemId);
//            if(isRepeat){
//                OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
//                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
//                respDTO.setErrMsg("当前零件号已存在,请重新添加！");
//                return respDTO;
//            }
            HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid((String)ebomContent.get("projectPuid"));
            HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
            hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());

            byte[] bytes = SerializeUtil.serialize(ebomContent);
            hzBomLineRecord.setLineID(itemId);
            hzBomLineRecord.setpBomOfWhichDept(pBomOfWhichDept);
            hzBomLineRecord.setBomLineBlock(bytes);
            hzBomLineRecord.setPuid(puid);
            hzBomLineRecord.setpBomLinePartResource((String)ebomContent.get("pBomLinePartResource"));
            int i =hzBomLineRecordDao.update(hzBomLineRecord);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO deleteHzEbomRecordById(DeleteHzEbomReqDTO reqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            User user = UserInfo.getUser();
            if (user.getGroupId() != 9) {
                respDTO.setErrMsg("你当前没有权限执行此操作");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            if(reqDTO.getPuids() == null || reqDTO.getPuids().equals("") || reqDTO.getProjectId() ==null || reqDTO.getProjectId().equals("")){
                respDTO.setErrMsg("非法参数！");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            String bomPuids[] = reqDTO.getPuids().trim().split(",");
            //需要判断层级关系 并更改层级关系
            for(String puid :bomPuids){
                HzEbomTreeQuery treeQuery = new HzEbomTreeQuery();
                treeQuery.setProjectId(reqDTO.getProjectId());
                treeQuery.setPuid(puid);
                List<HzEPLManageRecord> lineRecords = hzEbomRecordDAO.getHzBomLineChildren(treeQuery);//自己
                Set<String> set = new HashSet<>();//去除重复
                if(ListUtil.isNotEmpty(lineRecords)){
                    for(int i = 0;i<lineRecords.size();i++){
                        HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
                        hzEbomTreeQuery.setPuid(lineRecords.get(0).getParentUid());
                        hzEbomTreeQuery.setProjectId(reqDTO.getProjectId());
                        List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);//父亲
                        if(ListUtil.isNotEmpty(records)){
                            if(records.size() -lineRecords.size()==1){
                                HzEPLManageRecord hzEPLManageRecord = records.get(0);
                                HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                                hzBomLineRecord.setIsHas(0);
                                hzBomLineRecord.setIsPart(1);
                                if(hzEPLManageRecord.getIs2Y().equals(1)){
                                    hzBomLineRecord.setIs2Y(0);
                                }
                                hzBomLineRecordDao.update(hzBomLineRecord);
                            }

                        }
                        set.add(lineRecords.get(i).getPuid());
                    }
                }
                List<DeleteHzEbomReqDTO> list = new ArrayList<>();
                for(String s:set){
                    DeleteHzEbomReqDTO deleteHzEbomReqDTO = new DeleteHzEbomReqDTO();
                    deleteHzEbomReqDTO.setPuid(s);
                    list.add(deleteHzEbomReqDTO);
                }
                if(ListUtil.isNotEmpty(list)){
                    hzEbomRecordDAO.deleteList(list);//mabatis 做批量更新时 返回值为-1 所以这里根据异常情况来判断成功与否
                }
            }
            return OperateResultMessageRespDTO.getSuccessResult();
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public List<HzEPLManageRecord> findCurrentBomChildren(HzEbomTreeQuery query) {
        List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(query);
        return records;
    }

    @Override
    public Page<HzEbomRespDTO> getHzEbomRecycleByPage(HzBomRecycleByPageQuery query) {
        try {
            int num = (query.getPage()-1)*query.getPageSize();
            Page<HzEPLManageRecord> recordPage = hzEbomRecordDAO.getHzRecycleRecord(query);
            if(recordPage == null || recordPage.getResult() == null || recordPage.getResult().size()==0){
                return new Page<>(recordPage.getPageNumber(),recordPage.getPageSize(),0);
            }
            HzEbomRespDTO recordRespDTO = new HzEbomRespDTO();
            JSONArray array = new JSONArray();
            List<HzEbomRespDTO> recordRespDTOList = new ArrayList<>();
            List<HzEPLManageRecord> records = recordPage.getResult();
            for(HzEPLManageRecord record:records){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("puid", record.getPuid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                jsonObject.put("No",++num);
                jsonObject.put("level", strings[0]);
                jsonObject.put("rank", strings[1]);
                jsonObject.put("pBomOfWhichDept", record.getpBomOfWhichDept());
                //获取分组号
//                String groupNum = record.getLineID();
                //这里在做一个递归查询
//                if(groupNum.contains("-")){
//                    groupNum =groupNum.split("-")[1].substring(0,4);
//                }
//                jsonObject.put("groupNum", groupNum);
                jsonObject.put("lineId", record.getLineID());
                jsonObject.put("fna",record.getFna());
                byte[] bomLineBlock = record.getBomLineBlock();
                Object obj = SerializeUtil.unserialize(bomLineBlock);
                if (obj instanceof LinkedHashMap) {
                    if (((LinkedHashMap) obj).size() > 0) {
                        ((LinkedHashMap) obj).forEach((key, value) -> {

                            jsonObject.put((String) key, value);
                        });
                    }
                } else if (obj instanceof RedisBomBean) {
                    List<String> pSets = ((RedisBomBean) obj).getpSets();
                    List<String> pValues = ((RedisBomBean) obj).getpValues();
                    if (null != pSets && pSets.size() > 0 && null != pValues && pValues.size() > 0)
                        for (int i = 0; i < pSets.size(); i++) {
                            jsonObject.put(pSets.get(i), pValues.get(i));
                        }
                }
                array.add(jsonObject);
            }
            recordRespDTO.setJsonArray(array);
            recordRespDTOList.add(recordRespDTO);
            return new Page<>(recordPage.getPageNumber(),recordPage.getPageSize(),recordPage.getTotalCount(),recordRespDTOList);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public OperateResultMessageRespDTO RecoverDeleteEbomRecord(String projectId, String puid) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try{
            if(projectId == null || projectId == "" || puid == null || puid == ""){
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                respDTO.setErrMsg("非法参数");
                return  respDTO;
            }
            HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid,projectId);
            if(record!=null){
                respDTO.setErrMsg("当前要恢复对象已存在bom系统中！");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return  respDTO;
            }

            record =hzEbomRecordDAO.getHasDeletedBom(puid,projectId);
            if(record !=null){
                if(record.getLineIndex().split("\\.").length == 2){
                    respDTO.setErrMsg("2Y层结构无法恢复！");
                    respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    return  respDTO;
                }
                HzEPLManageRecord manageRecord = hzEbomRecordDAO.findEbomById(record.getParentUid(),projectId);
                if(manageRecord == null){
                    respDTO.setErrMsg("当前要恢复对象的父结构不存在，无法恢复！");
                    respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    return  respDTO;
                }else{
                    HzBomLineRecord bomLineRecord = new HzBomLineRecord();
                    if(manageRecord.getIsHas().equals(0)){
                        bomLineRecord.setIsHas(1);
                        bomLineRecord.setIsPart(0);
                        if(manageRecord.getLineIndex().split("\\.").length == 2 && manageRecord.getIs2Y().equals(0)){
                            bomLineRecord.setIs2Y(1);
                        }
                        int i = hzBomLineRecordDao.update(bomLineRecord);
                        if(i<=0){
                            return OperateResultMessageRespDTO.getFailResult();
                        }
                    }
                    HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                    hzBomLineRecord.setPuid(record.getPuid());
                    int i =hzBomLineRecordDao.update(hzBomLineRecord);
                    if(i>0){
                        return OperateResultMessageRespDTO.getSuccessResult();
                    }
                }
            }
            return OperateResultMessageRespDTO.getFailResult();
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

}
