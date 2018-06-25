package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomDataDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzBomStateDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzBomStateDAOImpl;
import com.connor.hozon.bom.resources.page.Page;
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
    public Page<HzEbomRespDTO> getHzEbomPage(FindForPageReqDTO recordReqDTO) {
        try{
            int num = (recordReqDTO.getPage()-1)*recordReqDTO.getLimit();
            HzEbomRespDTO recordRespDTO = new HzEbomRespDTO();
            JSONArray array = new JSONArray();
            List<HzEbomRespDTO> recordRespDTOList = new ArrayList<>();

            Page<HzEPLManageRecord> recordPage = hzEbomRecordDAO.getHzEbomPage(recordReqDTO);
            if(recordPage == null || recordPage.getResult() == null || recordPage.getResult().size()==0){
                return new Page<>(recordReqDTO.getPage(),recordReqDTO.getLimit(),0);
            }
            List<HzEPLManageRecord> records = recordPage.getResult();
            for(HzEPLManageRecord record:records){
                //过滤删除掉的ebom信息
                if(Integer.valueOf(2).equals(record.getpState())){
                    continue;
                }
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
                    groupNum = hzEPLManageRecordService.getGroupNum(recordReqDTO.getProjectId(),parentId);
                }
                jsonObject.put("groupNum", groupNum);
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
                array.add(jsonObject);
            }
            recordRespDTO.setJsonArray(array);
            recordRespDTOList.add(recordRespDTO);
            return new Page<>(recordReqDTO.getPage(),recordReqDTO.getLimit(),recordPage.getTotalCount(),recordRespDTOList);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public JSONArray getEbomTitle(String projectId) {
        try{
            JSONArray array = new JSONArray();
            int appendCount = 6;
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

                appendTrueName[0] = "No";
                appendTrueName[1] = "puid";
                appendTrueName[2] = "level";
                appendTrueName[3] = "pBomOfWhichDept";
                appendTrueName[4] = "rank";
                appendTrueName[5] = "groupNum";

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
    public int addHzEbomRecord(AddHzEbomReqDTO reqDTO) {
        try{
            String parentId = reqDTO.getParentPuid();
            int i;
            if(parentId != null && parentId!=""){
                //增加到当前父结构下面
                AddProcessComposeReqDTO addProcessComposeReqDTO = new AddProcessComposeReqDTO();
                addProcessComposeReqDTO.setPuid(parentId);
                addProcessComposeReqDTO.setProjectPuid(reqDTO.getProjectId());
                addProcessComposeReqDTO.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
                addProcessComposeReqDTO.seteBomContent(reqDTO.getMap());
                i = hzPbomService.addPbomProcessCompose(addProcessComposeReqDTO);
                if(i>0){
                    return 1;
                }
            }else{
                //自己搭建父结构 默认为2层 有子层时更新为2Y层
                HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
                if(hzBomMainRecord == null){
                    return 0;
                }
                hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getBomDigifax());
                Map<String, Object> objectMap = reqDTO.getMap();

                byte[] bytes = SerializeUtil.serialize(objectMap);
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
                hzBomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
                hzBomLineRecord.setLineID((String) objectMap.get("item_id"));
                hzBomLineRecord.setIsDept(0);
                i = hzBomLineRecordDao.insert(hzBomLineRecord);
                HzBomState hzBomState = new HzBomState();
                hzBomState.setpBomId(puid);
                hzBomState.setPuid(UUID.randomUUID().toString());
                hzBomState.setpBomState(0);
               int j =  hzBomStateDAO.insert(hzBomState);
               if(i>0 && j>0){
                   return 1;
               }
            }
            return 0;
        }catch (Exception e){
            return  0;
        }
    }

    @Override
    public int updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO) {
        try{
            HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
            HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
            hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getBomDigifax());
            Map<String,Object> map = new HashMap<>();
            byte[] bytes = SerializeUtil.serialize(map);
            hzBomLineRecord.setLineID((String)map.get("item_id"));
            hzBomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
            hzBomLineRecord.setBomLineBlock(bytes);
            hzBomLineRecord.setPuid(reqDTO.getPuid());
            int i =hzBomLineRecordDao.update(hzBomLineRecord);
            HzBomState hzBomState = new HzBomState();
            hzBomState.setpBomId(reqDTO.getPuid());
            hzBomState.setpBomState(1);
            int j =hzBomStateDAO.update(hzBomState);
            if(i>0 && j>0){
                return 1;
            }
        }catch (Exception e){
            return 0;
        }
        return 0;
    }

    @Override
    public int deleteHzEbomRecordById(DeleteHzEbomReqDTO reqDTO) {
        try{
            //删除数据时 需要把当前bom下的所有子层数据一起删除
            HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(reqDTO.getPuid(),reqDTO.getProjectId());
            HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
            HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
            //判断当前bom下有没有子，需要调整层级关系
            if(record != null){
                List<HzEPLManageRecord> records = findCurrentBomChildren(reqDTO.getProjectId(),record);
                List<HzEPLManageRecord> recordList = new ArrayList<>();
                //过滤掉已删除的bom
                for(HzEPLManageRecord eplManageRecord :records){
                    if(!eplManageRecord.getpState().equals(2)){
                        recordList.add(eplManageRecord);
                    }
                }
                for(HzEPLManageRecord eplManageRecord :recordList){
                    hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getBomDigifax());
                    hzBomLineRecord.setPuid(eplManageRecord.getPuid());
                    hzBomLineRecord.setIsHas(0);
                    hzBomLineRecord.setIsPart(1);
                    hzBomLineRecordDao.update(hzBomLineRecord);
                    //状态值更新
                    HzBomState bomState = new HzBomState();
                    bomState.setpBomId(eplManageRecord.getPuid());
                    bomState.setpBomState(2);
                    HzBomState hzBomState = hzBomStateDAO.findStateById(eplManageRecord.getPuid());
                    if (hzBomState == null) {
                        bomState.setPuid(UUID.randomUUID().toString());
                        hzBomStateDAO.insert(bomState);
                    } else {
                        hzBomStateDAO.update(bomState);
                    }

                }
            }
        }catch (Exception e){
            return  0;
        }
        return 0;
    }

    @Override
    public List<HzEPLManageRecord> findCurrentBomChildren(String projectId,HzEPLManageRecord record) {
        List<HzEPLManageRecord> recordList = new ArrayList<>();
        recordList.add(record);
        if(record.getIsHas().equals(1)){
            Map<String,Object> map1 = new HashMap<>();
            map1.put("projectId",projectId);
            map1.put("parentUid",record.getPuid());
            List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(map1);
            if(ListUtil.isNotEmpty(records)){
                for(HzEPLManageRecord eplManageRecord:records){
                    findCurrentBomChildren(projectId,eplManageRecord);
                }
            }
        }
        return recordList;
    }

}
