package com.connor.hozon.bom.resources.service.epl.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.resources.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.bom.resources.mybatis.epl.HzEplMangeRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzEPLByPageQuery;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
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
 * Created by haozt on 2018/06/05
 */
@Service("HzEPLManageRecordService")
public class HzEPLManageRecordServiceImpl implements HzEPLManageRecordService {

    @Autowired
    private HzEplMangeRecordDAO hzEplMangeRecordDAO;

    @Autowired
    private HzBomDataDao hzBomDataDao;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;
    @Override
    public Page<HzEPLRecordRespDTO> getHzEPLRecordForPage(HzEPLByPageQuery query) {
        try{
            HzEPLRecordRespDTO recordRespDTO = new HzEPLRecordRespDTO();
            JSONArray array = new JSONArray();
            List<HzEPLRecordRespDTO> recordRespDTOList = new ArrayList<>();
            //需要加入搜索功能
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
            Page<HzEPLManageRecord> recordPage = hzEplMangeRecordDAO.getEPLListForPage(query);
            int num = (recordPage.getPageNumber()-1)*recordPage.getPageSize();
            if(recordPage == null || recordPage.getResult() == null || recordPage.getResult().size()==0){
                return new Page<>(recordPage.getPageNumber(),recordPage.getPageSize(),0);
            }
            List<HzEPLManageRecord> records = recordPage.getResult();
            for(HzEPLManageRecord record:records){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("puid", record.getPuid());
                jsonObject.put("parentUid", record.getParentUid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                jsonObject.put("No",++num);
                jsonObject.put("level", strings[0]);
                jsonObject.put("rank", strings[1]);
                Integer state =record.getpState();
                String pState="";

                if(state==null){
                    pState = "-";
                }else if(state .equals(0)){
                    pState ="A";
                }else if(state.equals(1)){
                    pState ="U";
                }else{
                    pState="D";
                }
                jsonObject.put("pState",pState);
                jsonObject.put("pBomOfWhichDept", record.getpBomOfWhichDept());
                //获取分组号
                String groupNum = record.getLineID();

                //这里在做一个递归查询
                if(groupNum.contains("-")){
                    groupNum =groupNum.split("-")[1].substring(0,4);
                }else{
                    String parentId = record.getParentUid();
                    groupNum = getGroupNum(query.getProjectId(),parentId);
                }
                jsonObject.put("groupNum", groupNum);
                jsonObject.put("lineId", record.getLineID());
                jsonObject.put("itemName", record.getpBomLinePartName());
                jsonObject.put("itemPart", record.getpBomLinePartClass());
                jsonObject.put("resource", record.getResource());
                jsonObject.put("type", record.getType());
                jsonObject.put("buyUnit", record.getBuyUnit());
                jsonObject.put("workShop1", record.getWorkShop1());
                jsonObject.put("workShop2", record.getWorkShop2());
                jsonObject.put("productLine", record.getProductLine());
                jsonObject.put("mouldType", record.getMouldType());
                jsonObject.put("outerPart", record.getOuterPart());
                jsonObject.put("colorPart", record.getColorPart());

                jsonObject.put("sparePart", record.getSparePart());
                jsonObject.put("sparePartNum", record.getSparePartNum());
                jsonObject.put("processRoute", record.getProcessRoute());
                jsonObject.put("laborHour", record.getLaborHour());
                jsonObject.put("rhythm", record.getRhythm());
                jsonObject.put("solderJoint", record.getSolderJoint());
                jsonObject.put("machineMaterial", record.getMachineMaterial());
                jsonObject.put("standardPart", record.getStandardPart());
                jsonObject.put("tools", record.getTools());
                jsonObject.put("wasterProduct", record.getWasterProduct());
                jsonObject.put("change", record.getChange());
                jsonObject.put("changeNum", record.getChangeNum());
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
    public JSONArray getEPLTittle(String projectId) {
        try{
            JSONArray array = new JSONArray();
            int appendCount = 6;
            int appendNum = 23;
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

                String[] appendLastLocalName = new String[appendLocalName.length + appendNum];
                String[] appendLastTrueName = new String[appendTrueName.length + appendNum];

                int i =appendLocalName.length-1;
                int j = appendTrueName.length-1;

                appendLocalName[0] = "序号";
                appendLocalName[1] = "状态值";
                appendLocalName[2] = "层级";
                appendLocalName[3] = "专业";
                appendLocalName[4] = "级别";
                appendLocalName[5] = "分组号";

                appendTrueName[0] = "No";
                appendTrueName[1] = "pState";
                appendTrueName[2] = "level";
                appendTrueName[3] = "pBomOfWhichDept";
                appendTrueName[4] = "rank";
                appendTrueName[5] = "groupNum";


                appendLastLocalName[++i] = "专业部门";
                appendLastLocalName[++i] = "自制/采购";
                appendLastLocalName[++i] = "焊接/装配";
                appendLastLocalName[++i] = "采购单元";
                appendLastLocalName[++i] = "车间1";

                appendLastTrueName[++j] = "deptPart";//鬼知道它现在有没有
                appendLastTrueName[++j] = "resource";
                appendLastTrueName[++j] = "type";
                appendLastTrueName[++j] = "buyUnit";
                appendLastTrueName[++j] = "workShop1";

                appendLastLocalName[++i] = "车间2";
                appendLastLocalName[++i] = "生产线";
                appendLastLocalName[++i] = "模具类别";
                appendLastLocalName[++i] = "外委件";
                appendLastLocalName[++i] = "颜色件";

                appendLastTrueName[++j] = "workShop2";
                appendLastTrueName[++j] = "productLine";
                appendLastTrueName[++j] = "mouldType";
                appendLastTrueName[++j] = "outerPart";
                appendLastTrueName[++j] = "colorPart";

                appendLastLocalName[++i] = "工位";
                appendLastLocalName[++i] = "备件";
                appendLastLocalName[++i] = "备件编号";
                appendLastLocalName[++i] = "工艺路线";
                appendLastLocalName[++i] = "人工工时";

                appendLastTrueName[++j] = "station";
                appendLastTrueName[++j] = "sparePart";
                appendLastTrueName[++j] = "sparePartNum";
                appendLastTrueName[++j] = "processRoute";
                appendLastTrueName[++j] = "laborHour";


                appendLastLocalName[++i] = "节拍";
                appendLastLocalName[++i] = "焊点";
                appendLastLocalName[++i] = "机物料";
                appendLastLocalName[++i] = "标准件";
                appendLastLocalName[++i] = "工具";

                appendLastTrueName[++j] = "rhythm";
                appendLastTrueName[++j] = "solderJoint";
                appendLastTrueName[++j] = "machineMaterial";
                appendLastTrueName[++j] = "standardPart";
                appendLastTrueName[++j] = "tools";

                appendLastLocalName[++i] = "废品";
                appendLastLocalName[++i] = "变更";
                appendLastLocalName[++i] = "变更号";

                appendLastTrueName[++j] = "wasterProduct";
                appendLastTrueName[++j] = "change";
                appendLastTrueName[++j] = "changeNum";


                System.arraycopy(localName, 0, appendLocalName, appendCount, localName.length);
                System.arraycopy(trueName, 0, appendTrueName, appendCount, trueName.length);


                System.arraycopy(appendLocalName, 0, appendLastLocalName, 0, appendLocalName.length);
                System.arraycopy(appendTrueName, 0, appendLastTrueName, 0, appendTrueName.length);


                array.add(0, appendLastLocalName);
                array.add(1, appendLastTrueName);
            }
            return array;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String  getGroupNum(String projectId,String parentId){
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("puid",parentId);
        HzBomLineRecord record =hzBomLineRecordDao.findBomLineByPuid(map);
        if(record == null){
            return "-";
        }
        String groupNum = record.getLineID();
        if(groupNum.contains("-")){
            return groupNum.split("-")[1].substring(0,4);
        }else{
            return getGroupNum(projectId,record.getParentUid());
        }
    }
}
