package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomDataDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzBomStateDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzBomStateDAOImpl;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzEbomByPageQuery;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import share.bean.PreferenceSetting;
import share.bean.RedisBomBean;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzBomState;
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
    private HzEPLManageRecordService hzEPLManageRecordService;

    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Autowired
    private HzBomStateDAO hzBomStateDAO;

    @Autowired
    private HzPbomService hzPbomService;

    @Override
    public Page<HzEbomRespDTO> getHzEbomPage(HzEbomByPageQuery query) {
        try{
            int num = (query.getPage()-1)*query.getLimit();
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
                return new Page<>(query.getPage(),query.getLimit(),0);
            }
            List<HzEPLManageRecord> records = recordPage.getResult();
            for(HzEPLManageRecord record:records){
                //过滤删除掉的ebom信息
//                if(Integer.valueOf(2).equals(record.getpState())){
//                    continue;
//                }
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
                if(groupNum.contains("-")){
                    groupNum =groupNum.split("-")[1].substring(0,4);
                }else{
                    String parentId = record.getParentUid();
                    groupNum = hzEPLManageRecordService.getGroupNum(query.getProjectId(),parentId);
                }
                jsonObject.put("groupNum", groupNum);
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
            return new Page<>(query.getPage(),query.getLimit(),recordPage.getTotalCount(),recordRespDTOList);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public JSONArray getEbomTitle(String projectId) {
        try{
            JSONArray array = new JSONArray();
            int appendCount = 7;
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

                appendTrueName[0] = "No";
                appendTrueName[1] = "puid";
                appendTrueName[2] = "level";
                appendTrueName[3] = "pBomOfWhichDept";
                appendTrueName[4] = "rank";
                appendTrueName[5] = "groupNum";
                appendTrueName[6] = "fna";
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
            if(record!=null && !(Integer.valueOf(2).equals(record.getpState()))){
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
                        //状态值也要更新
                        HzBomState bomState = new HzBomState();
                        bomState.setpBomId(record.getPuid());
                        bomState.setpBomState(1);
                        HzBomState hzBomState = hzBomStateDAO.findStateById(record.getPuid());
                        if (hzBomState == null) {
                            bomState.setPuid(UUID.randomUUID().toString());
                            hzBomStateDAO.insert(bomState);
                        } else {
                            hzBomStateDAO.update(bomState);
                        }
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
                Map map1 = new HashMap();
                map1.put("projectId", reqDTO.getProjectId());
                map1.put("parentUid",parentId);
                List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(map);
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
                //状态表中添加数据
                HzBomState state = new HzBomState();
                // 0 新增 1 更新 2 删除
                state.setpBomState(0);
                state.setPuid(UUID.randomUUID().toString());
                state.setpBomId(puid);
                int j = hzBomStateDAO.insert(state);
                //pbom表中添加数据否？ 暂时未定 后续测试出问题了再加进去
                if (i > 0 && j > 0) {
                    return OperateResultMessageRespDTO.getSuccessResult();
                }
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
                HzBomState hzBomState = new HzBomState();
                hzBomState.setpBomId(puid);
                hzBomState.setPuid(UUID.randomUUID().toString());
                hzBomState.setpBomState(0);
               int j =  hzBomStateDAO.insert(hzBomState);
               if(i>0 && j>0){
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

            if(ebomContent.containsKey("bl_item_item_id")){
                itemId = (String)ebomContent.get("bl_item_item_id");
            }
            if(ebomContent.containsKey("item_id")){
                itemId = (String)ebomContent.get("item_id");
            }
//            boolean isRepeat = hzEbomRecordDAO.checkItemIdIsRepeat(reqDTO.getProjectId(),itemId);
//            if(isRepeat){
//                OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
//                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
//                respDTO.setErrMsg("当前零件号已存在,请重新添加！");
//                return respDTO;
//            }
            HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
            HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
            hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());

            byte[] bytes = SerializeUtil.serialize(ebomContent);
            hzBomLineRecord.setLineID(itemId);
            hzBomLineRecord.setpBomOfWhichDept(pBomOfWhichDept);
            hzBomLineRecord.setBomLineBlock(bytes);
            hzBomLineRecord.setPuid(puid);
            int i =hzBomLineRecordDao.update(hzBomLineRecord);

            HzBomState bomState = new HzBomState();
            bomState.setpBomId(puid);
            bomState.setpBomState(1);
            HzBomState hzBomState = hzBomStateDAO.findStateById(puid);
            int j = 0;
            if (hzBomState == null) {
                bomState.setPuid(UUID.randomUUID().toString());
                j = hzBomStateDAO.insert(bomState);
            } else {
               j =  hzBomStateDAO.update(bomState);
            }
            if(i>0 && j>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO deleteHzEbomRecordById(DeleteHzEbomReqDTO reqDTO) {
        try{
            String puids[] = reqDTO.getPuids().trim().split(",");
            for(String puid:puids){
                //删除数据时 需要把当前bom下的所有子层数据一起删除
                HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid,reqDTO.getProjectId());
                HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
                //判断当前bom下有没有子，需要调整层级关系
                if(record != null){
                    List<HzEPLManageRecord> records = findCurrentBomChildren(reqDTO.getProjectId(),record);//自己
                    int j = 0;
                    for(HzEPLManageRecord eplManageRecord :records){
                        Map<String,Object> map  = new HashMap<>();
                        map.put("projectId",reqDTO.getProjectId());
                        map.put("puid",records.get(0).getParentUid());
                        List<HzEPLManageRecord> record1s = hzEbomRecordDAO.getHzBomLineChildren(map);//父亲
                        if(record1s.size() -records.size() ==1){
                            HzEPLManageRecord hzEPLManageRecord = record1s.get(0);
                            hzBomLineRecord.setIsHas(0);
                            hzBomLineRecord.setIsPart(1);
                            hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                            hzBomLineRecord.setPuid(hzEPLManageRecord.getPuid());
                            if(hzEPLManageRecord.getIs2Y().equals(1)){
                                hzBomLineRecord.setIs2Y(0);
                            }
//                        hzBomLineRecord.setIsHas(0);
//                        hzBomLineRecord.setIsPart(1);
                            hzBomLineRecordDao.update(hzBomLineRecord);
                        }
                        //状态值更新
                        HzBomState bomState = new HzBomState();
                        bomState.setpBomId(eplManageRecord.getPuid());
                        bomState.setpBomState(2);
                        HzBomState hzBomState = hzBomStateDAO.findStateById(eplManageRecord.getPuid());
                        if (hzBomState == null) {
                            bomState.setPuid(UUID.randomUUID().toString());
                            hzBomStateDAO.insert(bomState);
                            j++;
                        } else {
                            hzBomStateDAO.update(bomState);
                            j++;
                        }
                    }
                }
            }
            return OperateResultMessageRespDTO.getSuccessResult();
        }catch (Exception e){
            return  OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public List<HzEPLManageRecord> findCurrentBomChildren(String projectId,HzEPLManageRecord record) {
        Map<String,Object> map1 = new HashMap<>();
        map1.put("projectId",projectId);
        map1.put("puid",record.getPuid());
        List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(map1);
        if(ListUtil.isEmpty(records)){
            return null;
        }
        return records;
    }

}
