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
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.*;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
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
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.project.HzMaterielRecord;
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
    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;
    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;
    @Autowired
    private HzMaterielDAO hzMaterielDAO;
    @Autowired
    private HzMbomService hzMbomService;
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
//                byte[] bomLineBlock = record.getBomLineBlock();
//                Object obj = SerializeUtil.unserialize(bomLineBlock);
//                if (obj instanceof LinkedHashMap) {
//                    if (((LinkedHashMap) obj).size() > 0) {
//                        ((LinkedHashMap) obj).forEach((key, value) -> {
//
//                            jsonObject.put((String) key, value);
//                        });
//                    }
//                } else if (obj instanceof RedisBomBean) {
//                    List<String> pSets = ((RedisBomBean) obj).getpSets();
//                    List<String> pValues = ((RedisBomBean) obj).getpValues();
//                    if (null != pSets && pSets.size() > 0 && null != pValues && pValues.size() > 0)
//                        for (int i = 0; i < pSets.size(); i++) {
//                            jsonObject.put(pSets.get(i), pValues.get(i));
//                        }
//                }
                jsonObject.put("groupNum", groupNum);
                jsonObject.put("lineId", record.getLineID());
                jsonObject.put("fna",record.getFna());


                jsonObject.put("pBomLinePartName", record.getpBomLinePartName());
                jsonObject.put("pBomLinePartClass", record.getpBomLinePartClass());
                jsonObject.put("pBomLinePartEnName",record.getpBomLinePartEnName());
                jsonObject.put("pBomLinePartResource", record.getpBomLinePartResource());
                jsonObject.put("pFastener", record.getpFastener());
                if(Integer.valueOf(1).equals(record.getIs2Y())){
                    jsonObject.put("pLouaFlag","LOU");
                }else{
                    jsonObject.put("pLouaFlag","LOA");
                }
                if(Integer.valueOf(1).equals(record.getP3cpartFlag())){
                    jsonObject.put("p3cpartFlag", "Y");
                }else {
                    jsonObject.put("p3cpartFlag", "N");
                }
                if(Integer.valueOf(1).equals(record.getpInOutSideFlag())){
                    jsonObject.put("pInOutSideFlag", "内饰件");
                }else {
                    jsonObject.put("pInOutSideFlag", "外饰件");
                }
                jsonObject.put("pUpc",record.getpUpc());
                jsonObject.put("pFnaDesc", record.getpFnaDesc());
                jsonObject.put("pUnit", record.getpUnit());
                jsonObject.put("pPictureNo",record.getpPictureNo());
                jsonObject.put("pPictureSheet", record.getpPictureSheet());
                jsonObject.put("pMaterialHigh", record.getpMaterialHigh());
                jsonObject.put("pMaterial1",record.getpMaterial1());
                jsonObject.put("pMaterial2", record.getpMaterial2());
                jsonObject.put("pMaterial3", record.getpMaterial3());
                jsonObject.put("pDensity",record.getpDensity());
                jsonObject.put("pMaterialStandard", record.getpMaterialStandard());
                jsonObject.put("pSurfaceTreat", record.getpSurfaceTreat());
                jsonObject.put("pTextureColorNum",record.getpTextureColorNum());
                jsonObject.put("pManuProcess", record.getpManuProcess());
                jsonObject.put("pSymmetry", record.getpSymmetry());
                jsonObject.put("pImportance",record.getpImportance());
                if(Integer.valueOf(1).equals(record.getpRegulationFlag())){
                    jsonObject.put("pRegulationFlag", "Y");
                }else{
                    jsonObject.put("pRegulationFlag", "N");
                }
                jsonObject.put("pBwgBoxPart", record.getpBwgBoxPart());
                jsonObject.put("pDevelopType",record.getpDevelopType());
                jsonObject.put("pDataVersion", record.getpDataVersion());
                jsonObject.put("pTargetWeight", record.getpTargetWeight());
                jsonObject.put("pFeatureWeight",record.getpFeatureWeight());
                jsonObject.put("pActualWeight", record.getpActualWeight());
                jsonObject.put("pFastenerStandard", record.getpFastenerStandard());
                jsonObject.put("pFastenerLevel",record.getpFastenerLevel());

                jsonObject.put("pTorque", record.getpTorque());
                jsonObject.put("pDutyEngineer",record.getpDutyEngineer());
                jsonObject.put("pSupply", record.getpSupply());
                jsonObject.put("pSupplyCode", record.getpSupplyCode());
                jsonObject.put("pRemark",record.getpRemark());
                jsonObject.put("pRegulationCode", record.getpRegulationCode());
                jsonObject.put("number",record.getNumber());
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
                appendLocalName[7] = "零部件来源";

                appendTrueName[0] = "No";
                appendTrueName[1] = "puid";
                appendTrueName[2] = "level";
                appendTrueName[3] = "pBomOfWhichDept";
                appendTrueName[4] = "rank";
                appendTrueName[5] = "groupNum";
                appendTrueName[6] = "fna";
                appendTrueName[7] = "pBomLinePartResource";
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

//                byte[] bomLineBlock = record.getBomLineBlock();
//                Object obj = SerializeUtil.unserialize(bomLineBlock);
//                if (obj instanceof LinkedHashMap) {
//                    if (((LinkedHashMap) obj).size() > 0) {
//                        ((LinkedHashMap) obj).forEach((key, value) -> {
//
//                            jsonObject.put((String) key, value);
//                        });
//                    }
//                } else if (obj instanceof RedisBomBean) {
//                    List<String> pSets = ((RedisBomBean) obj).getpSets();
//                    List<String> pValues = ((RedisBomBean) obj).getpValues();
//                    if (null != pSets && pSets.size() > 0 && null != pValues && pValues.size() > 0)
//                        for (int i = 0; i < pSets.size(); i++) {
//                            jsonObject.put(pSets.get(i), pValues.get(i));
//                        }
//                }
                jsonObject.put("fna",record.getFna());

                jsonObject.put("pBomLinePartName", record.getpBomLinePartName());
                jsonObject.put("pBomLinePartClass", record.getpBomLinePartClass());
                jsonObject.put("pBomLinePartEnName",record.getpBomLinePartEnName());
                jsonObject.put("pBomLinePartResource", record.getpBomLinePartResource());
                jsonObject.put("pFastener", record.getpFastener());
                if(Integer.valueOf(0).equals(record.getIs2Y())){
                    jsonObject.put("pLouaFlag","LOU");
                }else{
                    jsonObject.put("pLouaFlag","LOA");
                }
                if(Integer.valueOf(1).equals(record.getP3cpartFlag())){
                    jsonObject.put("p3cpartFlag", "Y");
                }else {
                    jsonObject.put("p3cpartFlag", "N");
                }
                if(Integer.valueOf(1).equals(record.getpInOutSideFlag())){
                    jsonObject.put("pInOutSideFlag", "内饰件");
                }else {
                    jsonObject.put("pInOutSideFlag", "外饰件");
                }
                jsonObject.put("pUpc",record.getpUpc());
                jsonObject.put("pFnaDesc", record.getpFnaDesc());
                jsonObject.put("pUnit", record.getpUnit());
                jsonObject.put("pPictureNo",record.getpPictureNo());
                jsonObject.put("pPictureSheet", record.getpPictureSheet());
                jsonObject.put("pMaterialHigh", record.getpMaterialHigh());
                jsonObject.put("pMaterial1",record.getpMaterial1());
                jsonObject.put("pMaterial2", record.getpMaterial2());
                jsonObject.put("pMaterial3", record.getpMaterial3());
                jsonObject.put("pDensity",record.getpDensity());
                jsonObject.put("pMaterialStandard", record.getpMaterialStandard());
                jsonObject.put("pSurfaceTreat", record.getpSurfaceTreat());
                jsonObject.put("pTextureColorNum",record.getpTextureColorNum());
                jsonObject.put("pManuProcess", record.getpManuProcess());
                jsonObject.put("pSymmetry", record.getpSymmetry());
                jsonObject.put("pImportance",record.getpImportance());
                if(Integer.valueOf(1).equals(record.getpRegulationFlag())){
                    jsonObject.put("pRegulationFlag", "Y");
                }else{
                    jsonObject.put("pRegulationFlag", "N");
                }
                jsonObject.put("pBwgBoxPart", record.getpBwgBoxPart());
                jsonObject.put("pDevelopType",record.getpDevelopType());
                jsonObject.put("pDataVersion", record.getpDataVersion());
                jsonObject.put("pTargetWeight", record.getpTargetWeight());
                jsonObject.put("pFeatureWeight",record.getpFeatureWeight());
                jsonObject.put("pActualWeight", record.getpActualWeight());
                jsonObject.put("pFastenerStandard", record.getpFastenerStandard());
                jsonObject.put("pFastenerLevel",record.getpFastenerLevel());

                jsonObject.put("pTorque", record.getpTorque());
                jsonObject.put("pDutyEngineer",record.getpDutyEngineer());
                jsonObject.put("pSupply", record.getpSupply());
                jsonObject.put("pSupplyCode", record.getpSupplyCode());
                jsonObject.put("pRemark",record.getpRemark());
                jsonObject.put("pRegulationCode", record.getpRegulationCode());
                jsonObject.put("number", record.getNumber());
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
     * @return 有很大的可优化性 时间紧迫 2期在优化
     */
    @Override
    public OperateResultMessageRespDTO addHzEbomRecord(AddHzEbomReqDTO reqDTO) {
        OperateResultMessageRespDTO operateResultMessageRespDTO = new OperateResultMessageRespDTO();
        try{
            String parentId =reqDTO.getPuid();
            if(parentId != null && parentId!=""){
                //增加到当前父结构下面 并且需要同步当前数据到MBOM 和PBOM 以及物料数据中
                //如果当前结构在多个地方应用，则需要将应用的地方全部添加该零件，保持结构的完整性
                Map<String, Object> map = new HashMap<>();
                map.put("puid", parentId);
                map.put("projectId", reqDTO.getProjectId());
                map.put("pPuid",parentId);
                HzBomLineRecord record = hzBomLineRecordDao.findBomLineByPuid(map);

                //找到他们的父，并找到他们父的零件号，继而找出所有使用父零件号的部位，保持结构的完整性
                String bomLineId = "";
                if (record != null) {
                    bomLineId = record.getLineID();
                }else {
                    operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    operateResultMessageRespDTO.setErrMsg("当前插入对象的父结构不存在！");
                    return operateResultMessageRespDTO;
                }


                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());

                ArrayList<String> puids = new ArrayList<>();
                if(bomLineId !=""){
                    Map<String,Object> objectMap = new HashMap<>();
                    objectMap.put("lineID",bomLineId);
                    objectMap.put("lineId",bomLineId);
                    objectMap.put("projectId",reqDTO.getProjectId());
                    List<HzEPLManageRecord> list1 =  hzEbomRecordDAO.findEbom(objectMap);
                    List<HzPbomLineRecord> list2  =  hzPbomRecordDAO.getPbomById(objectMap);
                    List<HzMbomLineRecord> list3  =  hzMbomRecordDAO.findHzMbomByPuid(objectMap);
                    String puid = "";

                    if(ListUtil.isNotEmpty(list1)){
                        for(HzEPLManageRecord hzEPLManageRecord :list1){
                            HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();//EBOM
                            if (record.getIsHas().equals(0) || record.getIsPart().equals(1)) {
                                HzBomLineRecord hzBomLineRecord1 = new HzBomLineRecord();
                                hzBomLineRecord1.setIsHas(new Integer(1));
                                hzBomLineRecord1.setIsPart(new Integer(0));
                                if (hzBomLineRecord1.getLineIndex().length() == 1) {
                                    hzBomLineRecord1.setIs2Y(1);
                                }
                                hzBomLineRecord.setPuid(hzEPLManageRecord.getPuid());
                                //更新数据
                                hzBomLineRecordDao.update(hzBomLineRecord1);
                            }
                            puid = UUID.randomUUID().toString();
                            puids.add(puid);
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
                            hzBomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
                            int orderNum = hzBomLineRecordDao.findMaxBomOrderNum();
                            hzBomLineRecord.setOrderNum(++orderNum);
                            hzBomLineRecord.setParentUid(hzEPLManageRecord.getPuid());
                            hzBomLineRecord.setIs2Y(0);
                            hzBomLineRecord.setLinePuid(puid);
                            hzBomLineRecord.setPuid(puid);
                            hzBomLineRecord.setIsDept(0);
                            hzBomLineRecord.setLineID(reqDTO.getLineId());
                            hzBomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
                            hzBomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
                            if(reqDTO.getP3cpartFlag().equals("Y")){
                                hzBomLineRecord.setP3cpartFlag(1);
                            }else {
                                hzBomLineRecord.setP3cpartFlag(0);
                            }
                            hzBomLineRecord.setpActualWeight(reqDTO.getpActualWeight());
                            hzBomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
                            hzBomLineRecord.setpBwgBoxPart(reqDTO.getpBwgBoxPart());
                            hzBomLineRecord.setpDataVersion(reqDTO.getpDataVersion());
                            hzBomLineRecord.setpDensity(reqDTO.getpDensity());
                            hzBomLineRecord.setpCreateName(UserInfo.getUser().getUserName());
                            hzBomLineRecord.setpUpdateName(UserInfo.getUser().getUserName());
                            hzBomLineRecord.setpDevelopType(reqDTO.getpDevelopType());
                            hzBomLineRecord.setpDutyEngineer(reqDTO.getpDutyEngineer());
                            hzBomLineRecord.setpFastener(reqDTO.getpFastener());
                            hzBomLineRecord.setpFastenerLevel(reqDTO.getpFastenerLevel());
                            hzBomLineRecord.setpFastenerStandard(reqDTO.getpFastenerStandard());
                            hzBomLineRecord.setpFeatureWeight(reqDTO.getpFeatureWeight());
                            hzBomLineRecord.setpFnaDesc(reqDTO.getpFnaDesc());
                            hzBomLineRecord.setpFnaInfo(reqDTO.getFna());
                            if(reqDTO.getpInOutSideFlag().equals("内饰件")){
                                hzBomLineRecord.setpInOutSideFlag(1);
                            }else {
                                hzBomLineRecord.setpInOutSideFlag(0);
                            }

                            hzBomLineRecord.setpImportance(reqDTO.getpImportance());
                            hzBomLineRecord.setpManuProcess(reqDTO.getpManuProcess());
                            hzBomLineRecord.setpMaterial1(reqDTO.getpMaterial1());
                            hzBomLineRecord.setpMaterial2(reqDTO.getpMaterial2());
                            hzBomLineRecord.setpMaterial3(reqDTO.getpMaterial3());
                            hzBomLineRecord.setpMaterialHigh(reqDTO.getpMaterialHigh());
                            hzBomLineRecord.setpMaterialStandard(reqDTO.getpMaterialStandard());
                            hzBomLineRecord.setpPictureNo(reqDTO.getpPictureNo());
                            hzBomLineRecord.setpPictureSheet(reqDTO.getpPictureSheet());
                            hzBomLineRecord.setpRegulationCode(reqDTO.getpRegulationCode());
                            hzBomLineRecord.setpUpc(reqDTO.getpUpc());
                            hzBomLineRecord.setpUnit(reqDTO.getpUnit());
                            hzBomLineRecord.setpRemark(reqDTO.getpRemark());
                            hzBomLineRecord.setpSymmetry(reqDTO.getpSymmetry());
                            hzBomLineRecord.setpTextureColorNum(reqDTO.getpTextureColorNum());
                            hzBomLineRecord.setpSurfaceTreat(reqDTO.getpSurfaceTreat());
                            hzBomLineRecord.setpTargetWeight(reqDTO.getpTargetWeight());
                            hzBomLineRecord.setpSupplyCode(reqDTO.getpSupplyCode());
                            hzBomLineRecord.setpSupply(reqDTO.getpSupply());
                            hzBomLineRecord.setpTorque(reqDTO.getpTorque());
                            hzBomLineRecord.setBomLineBlock(SerializeUtil.serialize(0));
                            hzBomLineRecord.setNumber(reqDTO.getNumber());
                            if(reqDTO.getpRegulationFlag().equals("Y")){
                                hzBomLineRecord.setpRegulationFlag(1);
                            }else {
                                hzBomLineRecord.setpRegulationFlag(0);
                            }

                            hzBomLineRecordDao.insert(hzBomLineRecord);

                        }

                    }


                    if(ListUtil.isNotEmpty(list2)){
                        List<HzPbomLineRecord> pbomLineRecords = new ArrayList<>();
                       for(int i = 0;i<list2.size();i++){
                           HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();//PBOM
                           if (list2.get(i).getIsHas().equals(0) || list2.get(i).getIsPart().equals(1)) {
                               list2.get(i).setIsHas(new Integer(1));
                               list2.get(i).setIsPart(new Integer(0));
                               if (list2.get(i).getLineIndex().length() == 1) {
                                   list2.get(i).setIs2Y(1);
                               }
                               //更新数据
                               hzPbomRecordDAO.update(list2.get(i));
                           }


                           int num = hzPbomRecordDAO.getHzPbomMaxOrderNum();
                           hzPbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
                           hzPbomLineRecord.setCreateName(UserInfo.getUser().getUserName());
                           hzPbomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
                           hzPbomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
                           hzPbomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
                           hzPbomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
                           hzPbomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
                           hzPbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                           hzPbomLineRecord.setLineId(reqDTO.getLineId());
                           hzPbomLineRecord.seteBomPuid(puids.get(i));
                           hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
                           hzPbomLineRecord.setOrderNum(num++);
                           hzPbomLineRecord.setParentUid(parentId);
                           hzPbomLineRecord.setIs2Y(0);
                           hzPbomLineRecord.setLinePuid(puids.get(i));
                           hzPbomLineRecord.setIsDept(0);
                           hzPbomLineRecord.setIsHas(0);
                           hzPbomLineRecord.setIsPart(1);

                           HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                           hzPbomTreeQuery.setPuid(parentId);
                           hzPbomTreeQuery.setProjectId(reqDTO.getProjectId());
                           List<HzPbomLineRecord> records1 = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
                           String lineIndex1 = list2.get(i).getLineIndex();
                           if (ListUtil.isEmpty(records1)) {
                               //1.1-1.1.1  1.2.2.2 -1.2.2.2.1
                               StringBuffer stringBuffer = new StringBuffer(lineIndex1);
                               stringBuffer = stringBuffer.append(".1");
                               hzPbomLineRecord.setLineIndex(stringBuffer.toString());
                           } else {
                               int length = lineIndex1.split("\\.").length + 1;
                               List<String> list = new ArrayList<>();
                               for (HzPbomLineRecord lineRecord : records1) {
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
                               hzPbomLineRecord.setLineIndex(new StringBuffer(lineIndex1).append("." + max).toString());
                           }
                           pbomLineRecords.add(hzPbomLineRecord);
                       }
                        hzPbomRecordDAO.insertList(pbomLineRecords);
                    }

                    List<String> stringList = hzMbomService.loadingCarPartType();
                    if(ListUtil.isNotEmpty(list3)){
                        List<HzMbomLineRecord> hzMbomLineRecords = new ArrayList<>();

                        for(int i = 0;i<list3.size();i++){
                            HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();//MBOM
                            if (list3.get(i).getIsHas().equals(0) || list3.get(i).getIsPart().equals(1)) {
                                list3.get(i).setIsHas(new Integer(1));
                                list3.get(i).setIsPart(new Integer(0));
                                if (list3.get(i).getLineIndex().length() == 1) {
                                    list3.get(i).setIs2Y(1);
                                }
                                //更新数据
                                hzMbomRecordDAO.update(list3.get(i));
                            }

                            if( stringList.contains(reqDTO.getpBomLinePartResource()) ){
                                int num = hzMbomRecordDAO.getHzMbomMaxOrderNum(reqDTO.getProjectId());
                                hzMbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
                                hzMbomLineRecord.setCreateName(UserInfo.getUser().getUserName());
                                hzMbomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
                                hzMbomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
                                hzMbomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
                                hzMbomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
                                hzMbomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
                                hzMbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                                hzMbomLineRecord.setLineId(reqDTO.getLineId());
                                hzMbomLineRecord.seteBomPuid(puids.get(i));
                                hzMbomLineRecord.setPuid(UUID.randomUUID().toString());
                                hzMbomLineRecord.setOrderNum(++num);
                                hzMbomLineRecord.setParentUid(parentId);
                                hzMbomLineRecord.setIs2Y(0);
                                hzMbomLineRecord.setLinePuid(puids.get(i));
                                hzMbomLineRecord.setIsDept(0);
                                hzMbomLineRecord.setIsHas(0);
                                hzMbomLineRecord.setIsPart(1);

                                HzMbomTreeQuery hzMbomTreeQuery = new HzMbomTreeQuery();
                                hzMbomTreeQuery.setPuid(parentId);
                                hzMbomTreeQuery.setProjectId(reqDTO.getProjectId());
                                List<HzMbomLineRecord> records2 = hzMbomRecordDAO.getHzMbomTree(hzMbomTreeQuery);
                                String lineIndex1 = list3.get(i).getLineIndex();
                                if (ListUtil.isEmpty(records2)) {
                                    StringBuffer stringBuffer = new StringBuffer(lineIndex1);
                                    stringBuffer = stringBuffer.append(".1");
                                    hzMbomLineRecord.setLineIndex(stringBuffer.toString());
                                } else {
                                    int length = lineIndex1.split("\\.").length + 1;
                                    List<String> list = new ArrayList<>();
                                    for (HzMbomLineRecord lineRecord : records2) {
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
                                    hzMbomLineRecord.setLineIndex(new StringBuffer(lineIndex1).append("." + max).toString());
                                }
                        }
                        hzMbomLineRecords.add(hzMbomLineRecord);
                    }
                    hzMbomRecordDAO.insertList(hzMbomLineRecords);
                }
            }
            return OperateResultMessageRespDTO.getSuccessResult();

            }else{
                //自己搭建父结构 默认为2层 有子层时更新为2Y层
                boolean isRepeat = hzEbomRecordDAO.checkItemIdIsRepeat(reqDTO.getProjectId(),reqDTO.getLineId());
                if(isRepeat){
                    OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
                    respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    respDTO.setErrMsg("当前零件号已存在,请重新添加！");
                    return respDTO;
                }

                HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();//EBOM
                HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();//物料
                HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();//PBOM
                HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();//MBOM


                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
                if(hzBomMainRecord == null){
                    return OperateResultMessageRespDTO.getFailResult();
                }
                hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
//
//                byte[] bytes = SerializeUtil.serialize(ebomContent);
//                hzBomLineRecord.setBomLineBlock(bytes);
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
                hzBomLineRecord.setLinePuid(puid);
                hzBomLineRecord.setLineID(reqDTO.getLineId());
                hzBomLineRecord.setIsDept(0);
                hzBomLineRecord.setIs2Y(0);
                hzBomLineRecord.setLinePuid(puid);
                hzBomLineRecord.setPuid(puid);
                hzBomLineRecord.setIsDept(0);
                hzBomLineRecord.setLineID(reqDTO.getLineId());
                hzBomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
                hzBomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
                if(reqDTO.getP3cpartFlag().equals("Y")){
                    hzBomLineRecord.setP3cpartFlag(1);
                }else {
                    hzBomLineRecord.setP3cpartFlag(0);
                }
                hzBomLineRecord.setpActualWeight(reqDTO.getpActualWeight());
                hzBomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
                hzBomLineRecord.setpBwgBoxPart(reqDTO.getpBwgBoxPart());
                hzBomLineRecord.setpDataVersion(reqDTO.getpDataVersion());
                hzBomLineRecord.setpDensity(reqDTO.getpDensity());
                hzBomLineRecord.setpCreateName(UserInfo.getUser().getUserName());
                hzBomLineRecord.setpUpdateName(UserInfo.getUser().getUserName());
                hzBomLineRecord.setpDevelopType(reqDTO.getpDevelopType());
                hzBomLineRecord.setpDutyEngineer(reqDTO.getpDutyEngineer());
                hzBomLineRecord.setpFastener(reqDTO.getpFastener());
                hzBomLineRecord.setpFastenerLevel(reqDTO.getpFastenerLevel());
                hzBomLineRecord.setpFastenerStandard(reqDTO.getpFastenerStandard());
                hzBomLineRecord.setpFeatureWeight(reqDTO.getpFeatureWeight());
                hzBomLineRecord.setpFnaDesc(reqDTO.getpFnaDesc());
                hzBomLineRecord.setpFnaInfo(reqDTO.getFna());
                hzBomLineRecord.setpTorque(reqDTO.getpTorque());
                hzBomLineRecord.setpSymmetry(reqDTO.getpSymmetry());
                hzBomLineRecord.setpTextureColorNum(reqDTO.getpTextureColorNum());
                hzBomLineRecord.setpSurfaceTreat(reqDTO.getpSurfaceTreat());
                hzBomLineRecord.setpTargetWeight(reqDTO.getpTargetWeight());
                hzBomLineRecord.setpSupplyCode(reqDTO.getpSupplyCode());
                hzBomLineRecord.setpSupply(reqDTO.getpSupply());
                if(reqDTO.getpInOutSideFlag().equals("内饰件")){
                    hzBomLineRecord.setpInOutSideFlag(1);
                }else {
                    hzBomLineRecord.setpInOutSideFlag(0);
                }

                hzBomLineRecord.setpImportance(reqDTO.getpImportance());
                hzBomLineRecord.setpManuProcess(reqDTO.getpManuProcess());
                hzBomLineRecord.setpMaterial1(reqDTO.getpMaterial1());
                hzBomLineRecord.setpMaterial2(reqDTO.getpMaterial2());
                hzBomLineRecord.setpMaterial3(reqDTO.getpMaterial3());
                hzBomLineRecord.setpMaterialHigh(reqDTO.getpMaterialHigh());
                hzBomLineRecord.setpMaterialStandard(reqDTO.getpMaterialStandard());
                hzBomLineRecord.setpPictureNo(reqDTO.getpPictureNo());
                hzBomLineRecord.setpPictureSheet(reqDTO.getpPictureSheet());
                hzBomLineRecord.setpRegulationCode(reqDTO.getpRegulationCode());
                hzBomLineRecord.setpUpc(reqDTO.getpUpc());
                hzBomLineRecord.setpUnit(reqDTO.getpUnit());
                hzBomLineRecord.setpRemark(reqDTO.getpRemark());
                hzBomLineRecord.setBomLineBlock(SerializeUtil.serialize(0));
                hzBomLineRecord.setNumber(reqDTO.getNumber());
                if(reqDTO.getpRegulationFlag().equals("Y")){
                    hzBomLineRecord.setpRegulationFlag(1);
                }else {
                    hzBomLineRecord.setpRegulationFlag(0);
                }

                 hzBomLineRecordDao.insert(hzBomLineRecord);

                int num = hzPbomRecordDAO.getHzPbomMaxOrderNum();
                hzPbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
                hzPbomLineRecord.setCreateName(UserInfo.getUser().getUserName());
                hzPbomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
                hzPbomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
                hzPbomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
                hzPbomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
                hzPbomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
                hzPbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                hzPbomLineRecord.setLineId(reqDTO.getLineId());
                hzPbomLineRecord.seteBomPuid(puid);
                hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
                hzPbomLineRecord.setOrderNum(num++);
                hzPbomLineRecord.setIs2Y(0);
                hzPbomLineRecord.setLinePuid(puid);
                hzPbomLineRecord.setIsDept(0);
                hzPbomLineRecord.setIsHas(0);
                hzPbomLineRecord.setIsPart(1);

                int maxLineIndexFirstNum = hzPbomRecordDAO.getMaxLineIndexFirstNum(reqDTO.getProjectId());
                StringBuffer stringBuffer = new StringBuffer(String.valueOf(++maxLineIndexFirstNum));
                hzPbomLineRecord.setLineIndex(stringBuffer.append(".1").toString());
                List<HzPbomLineRecord> pbomLineRecords = new ArrayList<>();
                pbomLineRecords.add(hzPbomLineRecord);
                hzPbomRecordDAO.insertList(pbomLineRecords);


                List<String> stringList = hzMbomService.loadingCarPartType();
                if(stringList.contains(reqDTO.getpBomLinePartResource()) ){
                    int mbomMaxOrderNum = hzMbomRecordDAO.getHzMbomMaxOrderNum(reqDTO.getProjectId());
                    int maxIndexFirstNum = hzMbomRecordDAO.getMaxLineIndexFirstNum(reqDTO.getProjectId());
                    hzMbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
                    hzMbomLineRecord.setCreateName(UserInfo.getUser().getUserName());
                    hzMbomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
                    hzMbomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
                    hzMbomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
                    hzMbomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
                    hzMbomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
                    hzMbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                    hzMbomLineRecord.setLineId(reqDTO.getLineId());
                    hzMbomLineRecord.seteBomPuid(puid);
                    hzMbomLineRecord.setPuid(UUID.randomUUID().toString());
                    hzMbomLineRecord.setOrderNum(++mbomMaxOrderNum);
                    hzMbomLineRecord.setIs2Y(0);
                    hzMbomLineRecord.setLinePuid(puid);
                    hzMbomLineRecord.setIsDept(0);
                    hzMbomLineRecord.setIsHas(0);
                    hzMbomLineRecord.setIsPart(1);
                    stringBuffer = new StringBuffer(String.valueOf(++maxIndexFirstNum));
                    hzMbomLineRecord.setLineIndex(stringBuffer.append(".1").toString());
                    hzMaterielRecord.setpMaterielCode(reqDTO.getLineId());
                    hzMaterielRecord.setpMaterielDesc(reqDTO.getpBomLinePartName());
                    hzMaterielRecord.setpMaterielDescEn(reqDTO.getpBomLinePartEnName());
                    hzMaterielRecord.setpBasicUnitMeasure(reqDTO.getpUnit());
                    hzMaterielRecord.setpHeight(reqDTO.getpActualWeight());
                    if(reqDTO.getpInOutSideFlag().equals("Y")){
                        hzMaterielRecord.setpInOutSideFlag(1);
                    }else {
                        hzMaterielRecord.setpInOutSideFlag(0);
                    }
                    if(reqDTO.getP3cpartFlag().equals("Y")){
                        hzMaterielRecord.setP3cPartFlag(1);
                    }else {
                        hzMaterielRecord.setP3cPartFlag(0);
                    }
                    hzMaterielRecord.setpPartImportantDegree(reqDTO.getpImportance());
                    hzMaterielRecord.setMaterielResourceId(puid);
                    hzMaterielRecord.setPuid(UUID.randomUUID().toString());
                    List<HzMbomLineRecord> mbomLineRecords = new ArrayList<>();
                    List<HzMaterielRecord> hzMaterielRecords = new ArrayList<>();
                    mbomLineRecords.add(hzMbomLineRecord);
                    hzMaterielRecords.add(hzMaterielRecord);
                    hzMbomRecordDAO.insertList(mbomLineRecords);
                    hzMaterielDAO.insertList(hzMaterielRecords);
                }

                return OperateResultMessageRespDTO.getSuccessResult();

            }
        }catch (Exception e){
            return  OperateResultMessageRespDTO.getFailResult();
        }
    }



    @Override
    public OperateResultMessageRespDTO updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO) {
        try{
            HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
            HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
            hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
//            byte[] bytes = SerializeUtil.serialize(ebomContent);
            hzBomLineRecord.setLineID(reqDTO.getLineId());
            hzBomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
//            hzBomLineRecord.setBomLineBlock(bytes);
//            hzBomLineRecord.setPuid(reqDTO.getPuid());
            hzBomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());

            hzBomLineRecord.setLineID(reqDTO.getLineId());
            hzBomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
            hzBomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
            if(reqDTO.getP3cpartFlag().equals("Y")){
                hzBomLineRecord.setP3cpartFlag(1);
            }else {
                hzBomLineRecord.setP3cpartFlag(0);
            }
            hzBomLineRecord.setpActualWeight(reqDTO.getpActualWeight());
            hzBomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
            hzBomLineRecord.setpBwgBoxPart(reqDTO.getpBwgBoxPart());
            hzBomLineRecord.setpDataVersion(reqDTO.getpDataVersion());
            hzBomLineRecord.setpDensity(reqDTO.getpDensity());
            hzBomLineRecord.setpUpdateName(UserInfo.getUser().getUserName());
            hzBomLineRecord.setpDevelopType(reqDTO.getpDevelopType());
            hzBomLineRecord.setpDutyEngineer(reqDTO.getpDutyEngineer());
            hzBomLineRecord.setpFastener(reqDTO.getpFastener());
            hzBomLineRecord.setpFastenerLevel(reqDTO.getpFastenerLevel());
            hzBomLineRecord.setpFastenerStandard(reqDTO.getpFastenerStandard());
            hzBomLineRecord.setpFeatureWeight(reqDTO.getpFeatureWeight());
            hzBomLineRecord.setpFnaDesc(reqDTO.getpFnaDesc());
            hzBomLineRecord.setpFnaInfo(reqDTO.getFna());
            if(reqDTO.getpInOutSideFlag().equals("内饰件")){
                hzBomLineRecord.setpInOutSideFlag(1);
            }else {
                hzBomLineRecord.setpInOutSideFlag(0);
            }

            hzBomLineRecord.setpImportance(reqDTO.getpImportance());
            hzBomLineRecord.setpManuProcess(reqDTO.getpManuProcess());
            hzBomLineRecord.setpMaterial1(reqDTO.getpMaterial1());
            hzBomLineRecord.setpMaterial2(reqDTO.getpMaterial2());
            hzBomLineRecord.setpMaterial3(reqDTO.getpMaterial3());
            hzBomLineRecord.setpMaterialHigh(reqDTO.getpMaterialHigh());
            hzBomLineRecord.setpMaterialStandard(reqDTO.getpMaterialStandard());
            hzBomLineRecord.setpPictureNo(reqDTO.getpPictureNo());
            hzBomLineRecord.setpPictureSheet(reqDTO.getpPictureSheet());
            hzBomLineRecord.setpRegulationCode(reqDTO.getpRegulationCode());
            if(reqDTO.getpRegulationFlag().equals("Y")){
                hzBomLineRecord.setpRegulationFlag(1);
            }else {
                hzBomLineRecord.setpRegulationFlag(0);
            }
            hzBomLineRecord.setpUpc(reqDTO.getpUpc());
            hzBomLineRecord.setpRemark(reqDTO.getpRemark());
            hzBomLineRecord.setpRemark(reqDTO.getpRemark());
            hzBomLineRecord.setpUnit(reqDTO.getpUnit());
            hzBomLineRecord.setpTorque(reqDTO.getpTorque());
            hzBomLineRecord.setpSymmetry(reqDTO.getpSymmetry());
            hzBomLineRecord.setpTextureColorNum(reqDTO.getpTextureColorNum());
            hzBomLineRecord.setpSurfaceTreat(reqDTO.getpSurfaceTreat());
            hzBomLineRecord.setpTargetWeight(reqDTO.getpTargetWeight());
            hzBomLineRecord.setpSupplyCode(reqDTO.getpSupplyCode());
            hzBomLineRecord.setpSupply(reqDTO.getpSupply());
            hzBomLineRecord.setBomLineBlock(SerializeUtil.serialize(0));
            hzBomLineRecord.setNumber(reqDTO.getNumber());
            Map<String,Object> map = new HashMap<>();
            map.put("projectId",reqDTO.getProjectId());
            map.put("lineID",reqDTO.getLineId());
            map.put("lineId",reqDTO.getLineId());
            List<HzEPLManageRecord> hzEPLManageRecords = hzEbomRecordDAO.findEbom(map);
            //pbom mbom  物料数据 也要同步更新数据
            List<HzPbomLineRecord> hzPbomLineRecords = hzPbomRecordDAO.getPbomById(map);




            List<String> type = hzMbomService.loadingCarPartType();
            List<HzMbomLineRecord> hzMbomLineRecords = hzMbomRecordDAO.findHzMbomByPuid(map);

            HzMaterielQuery hzMaterielQuery = new HzMaterielQuery();
            hzMaterielQuery.setProjectId(reqDTO.getProjectId());
            hzMaterielQuery.setMaterielResourceId(reqDTO.getPuid());
            List<HzMaterielRecord> hzMaterielRecords = hzMaterielDAO.findHzMaterielForList(hzMaterielQuery);

            if(type.contains(reqDTO.getpBomLinePartResource())){
                HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();
                hzMbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
                hzMbomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
                hzMbomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
                hzMbomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
                hzMbomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
                hzMbomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
                hzMbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                hzMbomLineRecord.setLineId(reqDTO.getLineId());
                if(ListUtil.isEmpty(hzMbomLineRecords)){
                    hzMbomLineRecord.setpPuid(UUID.randomUUID().toString());
                    List<HzMbomLineRecord> records = new ArrayList<>();
                    records.add(hzMbomLineRecord);
                    hzMbomRecordDAO.insertList(records);
                }else{
                    hzMbomLineRecords.forEach(record -> {
                        hzMbomLineRecord.seteBomPuid(record.geteBomPuid());
                        hzMbomRecordDAO.update(hzMbomLineRecord);
                    });
                }


                HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();
                hzMaterielRecord.setpMaterielCode(reqDTO.getLineId());
                hzMaterielRecord.setpMaterielDesc(reqDTO.getpBomLinePartName());
                hzMaterielRecord.setpMaterielDescEn(reqDTO.getpBomLinePartEnName());
                hzMaterielRecord.setpBasicUnitMeasure(reqDTO.getpUnit());
                hzMaterielRecord.setpHeight(reqDTO.getpActualWeight());
                if(reqDTO.getpInOutSideFlag().equals("Y")){
                    hzMaterielRecord.setpInOutSideFlag(1);
                }else {
                    hzMaterielRecord.setpInOutSideFlag(0);
                }
                if(reqDTO.getP3cpartFlag().equals("Y")){
                    hzMaterielRecord.setP3cPartFlag(1);
                }else {
                    hzMaterielRecord.setP3cPartFlag(0);
                }
                hzMaterielRecord.setpPartImportantDegree(reqDTO.getpImportance());
                if(ListUtil.isNotEmpty(hzMaterielRecords)){
                    hzMaterielRecords.forEach(record -> {
                        hzMaterielRecord.setPuid(record.getPuid());
                        hzMaterielDAO.update(record);
                    });
                }
            }else {
                if(ListUtil.isNotEmpty(hzMbomLineRecords)){
                    hzMbomLineRecords.forEach(hzMbomLineRecord -> {
                        hzMbomRecordDAO.delete(hzMbomLineRecord.geteBomPuid());
                    });
                }
                if(ListUtil.isNotEmpty(hzMaterielRecords)){
                    hzMaterielRecords.forEach(hzMaterielRecord->{
                        hzMaterielDAO.delete(hzMaterielRecord.getMaterielResourceId());
                    });
                }

            }

            HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
            hzPbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
            hzPbomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
            hzPbomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
            hzPbomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
            hzPbomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
            hzPbomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
            hzPbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
            hzPbomLineRecord.setLineId(reqDTO.getLineId());
            hzEPLManageRecords.forEach(record -> {
                hzBomLineRecord.setPuid(record.getPuid());
                hzBomLineRecordDao.update(hzBomLineRecord);
            });
            hzPbomLineRecords.forEach(record -> {
                hzPbomLineRecord.seteBomPuid(record.geteBomPuid());
                hzPbomRecordDAO.update(hzPbomLineRecord);
            });

            return OperateResultMessageRespDTO.getSuccessResult();
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
//        return OperateResultMessageRespDTO.getFailResult();
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
                jsonObject.put("pBomLinePartName", record.getpBomLinePartName());
                jsonObject.put("pBomLinePartClass", record.getpBomLinePartClass());
                jsonObject.put("pBomLinePartEnName",record.getpBomLinePartEnName());
                jsonObject.put("pBomLinePartResource", record.getpBomLinePartResource());
                jsonObject.put("pFastener", record.getpFastener());
//                if(Integer.valueOf(1).equals(record.getIs2Y())){
////                    jsonObject.put("pLouaFlag","LOU");
////                }else{
////                    jsonObject.put("pLouaFlag","LOA");
////                }
                if(Integer.valueOf(1).equals(record.getP3cpartFlag())){
                    jsonObject.put("p3cpartFlag", "Y");
                }else {
                    jsonObject.put("p3cpartFlag", "N");
                }
                if(Integer.valueOf(1).equals(record.getpInOutSideFlag())){
                    jsonObject.put("pInOutSideFlag", "内饰件");
                }else {
                    jsonObject.put("pInOutSideFlag", "外饰件");
                }
                jsonObject.put("pUpc",record.getpUpc());
                jsonObject.put("pFnaDesc", record.getpFnaDesc());
                jsonObject.put("pUnit", record.getpUnit());
                jsonObject.put("pPictureNo",record.getpPictureNo());
                jsonObject.put("pPictureSheet", record.getpPictureSheet());
                jsonObject.put("pMaterialHigh", record.getpMaterialHigh());
                jsonObject.put("pMaterial1",record.getpMaterial1());
                jsonObject.put("pMaterial2", record.getpMaterial2());
                jsonObject.put("pMaterial3", record.getpMaterial3());
                jsonObject.put("pDensity",record.getpDensity());
                jsonObject.put("pMaterialStandard", record.getpMaterialStandard());
                jsonObject.put("pSurfaceTreat", record.getpSurfaceTreat());
                jsonObject.put("pTextureColorNum",record.getpTextureColorNum());
                jsonObject.put("pManuProcess", record.getpManuProcess());
                jsonObject.put("pSymmetry", record.getpSymmetry());
                jsonObject.put("pImportance",record.getpImportance());
                if(Integer.valueOf(1).equals(record.getpRegulationFlag())){
                    jsonObject.put("pRegulationFlag", "Y");
                }else{
                    jsonObject.put("pRegulationFlag", "N");
                }
                jsonObject.put("pBwgBoxPart", record.getpBwgBoxPart());
                jsonObject.put("pDevelopType",record.getpDevelopType());
                jsonObject.put("pDataVersion", record.getpDataVersion());
                jsonObject.put("pTargetWeight", record.getpTargetWeight());
                jsonObject.put("pFeatureWeight",record.getpFeatureWeight());
                jsonObject.put("pActualWeight", record.getpActualWeight());
                jsonObject.put("pFastenerStandard", record.getpFastenerStandard());
                jsonObject.put("pFastenerLevel",record.getpFastenerLevel());

                jsonObject.put("pTorque", record.getpTorque());
                jsonObject.put("pDutyEngineer",record.getpDutyEngineer());
                jsonObject.put("pSupply", record.getpSupply());
                jsonObject.put("pSupplyCode", record.getpSupplyCode());
                jsonObject.put("pRemark",record.getpRemark());
                jsonObject.put("pRegulationCode", record.getpRegulationCode());
//                byte[] bomLineBlock = record.getBomLineBlock();
//                Object obj = SerializeUtil.unserialize(bomLineBlock);
//                if (obj instanceof LinkedHashMap) {
//                    if (((LinkedHashMap) obj).size() > 0) {
//                        ((LinkedHashMap) obj).forEach((key, value) -> {
//
//                            jsonObject.put((String) key, value);
//                        });
//                    }
//                } else if (obj instanceof RedisBomBean) {
//                    List<String> pSets = ((RedisBomBean) obj).getpSets();
//                    List<String> pValues = ((RedisBomBean) obj).getpValues();
//                    if (null != pSets && pSets.size() > 0 && null != pValues && pValues.size() > 0)
//                        for (int i = 0; i < pSets.size(); i++) {
//                            jsonObject.put(pSets.get(i), pValues.get(i));
//                        }
//                }
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
