package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import share.bean.PreferenceSetting;
import share.bean.RedisBomBean;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.redis.SerializeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
            if(record!=null && !record.getpState().equals(2)){
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
}
