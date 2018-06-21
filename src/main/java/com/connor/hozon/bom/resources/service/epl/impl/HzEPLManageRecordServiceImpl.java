package com.connor.hozon.bom.resources.service.epl.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.resources.dto.request.FindHzEPLRecordReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.epl.HzEplMangeRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.impl.HzPbomServiceImpl;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
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
    public Page<HzEPLRecordRespDTO> getHzEPLRecordForPage(FindHzEPLRecordReqDTO recordReqDTO) {
        try{
            int num = (recordReqDTO.getPage()-1)*recordReqDTO.getLimit();
            HzEPLRecordRespDTO recordRespDTO = new HzEPLRecordRespDTO();
            JSONArray array = new JSONArray();
            List<HzEPLRecordRespDTO> recordRespDTOList = new ArrayList<>();
            Page<HzEPLManageRecord> recordPage = hzEplMangeRecordDAO.getEPLListForPage(recordReqDTO);
            if(recordPage == null || recordPage.getResult() == null || recordPage.getResult().size()==0){
                return new Page<>(recordReqDTO.getPage(),recordReqDTO.getLimit(),0);
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
                    groupNum = getGroupNum(recordReqDTO.getProjectId(),parentId);
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
            return new Page<>(recordReqDTO.getPage(),recordReqDTO.getLimit(),recordPage.getTotalCount(),recordRespDTOList);
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public JSONArray getEPLTittle(FindHzEPLRecordReqDTO recordReqDTO) {
        try{
            JSONArray array = new JSONArray();
            int appendCount = 29;
            HzPreferenceSetting setting = new HzPreferenceSetting();
            setting.setSettingName("Hz_ExportBomPreferenceRedis");
            HZBomMainRecord main = hzBomMainRecordDao.selectByProjectPuid(recordReqDTO.getProjectId());
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


                appendLocalName[6] = "专业部门";
                appendLocalName[7] = "自制/采购";
                appendLocalName[8] = "焊接/装配";
                appendLocalName[9] = "采购单元";
                appendLocalName[10] = "车间1";

                appendTrueName[6] = "deptPart";//鬼知道它现在有没有
                appendTrueName[7] = "resource";
                appendTrueName[8] = "type";
                appendTrueName[9] = "buyUnit";
                appendTrueName[10] = "workShop1";

                appendLocalName[11] = "车间2";
                appendLocalName[12] = "生产线";
                appendLocalName[13] = "模具类别";
                appendLocalName[14] = "外委件";
                appendLocalName[15] = "颜色件";

                appendTrueName[11] = "workShop2";
                appendTrueName[12] = "productLine";
                appendTrueName[13] = "mouldType";
                appendTrueName[14] = "outerPart";
                appendTrueName[15] = "colorPart";

                appendLocalName[16] = "工位";
                appendLocalName[17] = "备件";
                appendLocalName[18] = "备件编号";
                appendLocalName[19] = "工艺路线";
                appendLocalName[20] = "人工工时";

                appendTrueName[16] = "gongwei";//数据库暂时没有
                appendTrueName[17] = "sparePart";
                appendTrueName[18] = "sparePartNum";
                appendTrueName[19] = "processRoute";
                appendTrueName[20] = "laborHour";


                appendLocalName[21] = "节拍";
                appendLocalName[22] = "焊点";
                appendLocalName[23] = "机物料";
                appendLocalName[24] = "标准件";
                appendLocalName[25] = "工具";

                appendTrueName[21] = "rhythm";
                appendTrueName[22] = "solderJoint";
                appendTrueName[23] = "machineMaterial";
                appendTrueName[24] = "standardPart";
                appendTrueName[25] = "tools";

                appendLocalName[26] = "废品";
                appendLocalName[27] = "变更";
                appendLocalName[28] = "变更号";

                appendTrueName[26] = "wasterProduct";
                appendTrueName[27] = "change";
                appendTrueName[28] = "changeNum";


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
    public String  getGroupNum(String projectId,String parentId){
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("puid",parentId);
        HzBomLineRecord record =hzBomLineRecordDao.findBobLineByPuid(map);
        String groupNum = record.getLineID();
        if(groupNum.contains("-")){
            return groupNum.split("-")[1].substring(0,4);
        }else{
            return getGroupNum(projectId,record.getParentUid());
        }
    }
}
