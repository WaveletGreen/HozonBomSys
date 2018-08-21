package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OfBomLineService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzLouRespDTO;
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
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
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
import sql.pojo.cfg.HzCfg0OfBomLineRecord;
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

    @Autowired
    private HzCfg0OfBomLineService hzCfg0OfBomLineService;
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
                jsonObject.put("lineNo",strings[2]);
                jsonObject.put("pBomLinePartName", record.getpBomLinePartName());
                jsonObject.put("pBomLinePartClass", record.getpBomLinePartClass());
                jsonObject.put("pBomLinePartEnName",record.getpBomLinePartEnName());
                jsonObject.put("pBomLinePartResource", record.getpBomLinePartResource());
                jsonObject.put("pFastener", record.getpFastener());
                if(Integer.valueOf(1).equals(record.getpLouaFlag())){
                    jsonObject.put("pLouaFlag","LOU");
                }else if(Integer.valueOf(0).equals(record.getpLouaFlag())){
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
                jsonObject.put("pBuyEngineer",record.getpBuyEngineer());
                jsonObject.put("status",record.getStatus());
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
                jsonObject.put("lineNo",strings[2]);
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
                if(Integer.valueOf(1).equals(record.getpLouaFlag())){
                    jsonObject.put("pLouaFlag","LOU");
                }else if(Integer.valueOf(0).equals(record.getpLouaFlag())){
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
                jsonObject.put("pBuyEngineer", record.getpBuyEngineer());
                jsonObject.put("status",record.getStatus());
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

            String lineNo = "";
            if(reqDTO.getLineNo()!= null &&reqDTO.getLineNo() != ""){
                lineNo = reqDTO.getLineNo().replaceFirst("^0*", "");
            }

            if(parentId != null && parentId!=""){
                //增加到当前父结构下面 并且需要同步当前数据到MBOM 和PBOM 以及物料数据中
                //如果当前结构在多个地方应用，则需要将应用的地方全部添加该零件，保持结构的完整性
                Map<String, Object> map = new HashMap<>();
                map.put("puid", parentId);
                map.put("projectId", reqDTO.getProjectId());
                map.put("pPuid",parentId);
                HzBomLineRecord record = hzBomLineRecordDao.findBomLine(map);

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
                            if (hzEPLManageRecord.getIsHas().equals(0) || hzEPLManageRecord.getIsPart().equals(1)) {
                                HzBomLineRecord hzBomLineRecord1 = new HzBomLineRecord();
                                hzBomLineRecord1.setIsHas(new Integer(1));
                                hzBomLineRecord1.setIsPart(new Integer(0));
                                if (hzEPLManageRecord.getLineIndex().split("\\.").length == 2) {
                                    hzBomLineRecord1.setIs2Y(1);
                                }
                                hzBomLineRecord1.setPuid(hzEPLManageRecord.getPuid());
                                //更新数据
                                hzBomLineRecordDao.update(hzBomLineRecord1);
                            }
                            puid = UUID.randomUUID().toString();
                            puids.add(puid);
                            hzBomLineRecord.setIsPart(1);
                            hzBomLineRecord.setIsHas(0);
                            //获取当前对象的所有的子层   lineIndex值比较复杂 比较抽象 但是很关键
                            String lineIndex = hzEPLManageRecord.getLineIndex();
                            HzEbomTreeQuery query = new HzEbomTreeQuery();
                            query.setProjectId(reqDTO.getProjectId());
                            query.setPuid(parentId);

                            List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(query);
                            if(ListUtil.isEmpty(records)){
                                operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                                operateResultMessageRespDTO.setErrMsg("当前插入对象的父结构不存在！");
                                return operateResultMessageRespDTO;
                            }
                            if (records.size() ==1) {
                                //1.1-1.1.1  1.2.2.2 -1.2.2.2.1
                                StringBuffer stringBuffer = new StringBuffer(lineIndex);
                                stringBuffer = stringBuffer.append(".10");
                                hzBomLineRecord.setLineIndex(stringBuffer.toString());
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
                                if(Integer.valueOf(lineIndex.split("\\.")[0]).equals(max)){
                                    hzBomLineRecord.setOrderNum(hzEPLManageRecord.getOrderNum()+100);
                                }else {
                                    hzBomLineRecord.setOrderNum(hzEPLManageRecord.getOrderNum()+50);
                                }


                            } else {

                                int length = lineIndex.split("\\.").length + 1;
                                List<String> list = new ArrayList<>();
                                List<HzEPLManageRecord> l = new ArrayList<>();
                                for (int k =0;k<records.size();k++) {
                                    int len = records.get(k).getLineIndex().split("\\.").length;
                                    if (length == len) {
                                        list.add(records.get(k).getLineIndex());
                                        l.add(records.get(k));
                                    }
                                }
                                Integer max = 0;
                                //找出lineindex 末尾最大值

                                if(!lineNo.equals("")){
                                    for (int j = 0; j < list.size(); j++) {
                                        String str = list.get(j).split("\\.")[list.get(j).split("\\.").length - 1];
                                            if (max < Integer.valueOf(str)) {
                                                if(Integer.valueOf(lineNo).equals(Integer.valueOf(str))){
                                                    operateResultMessageRespDTO.setErrMsg("不合法的查找编号输入！");
                                                    return operateResultMessageRespDTO;
                                                }
                                                max = Integer.valueOf(str);
                                            }


                                    }
                                    if(max<Integer.valueOf(lineNo)){
                                        hzBomLineRecord.setLineIndex(new StringBuffer(lineIndex).append("." + (max+10)).toString());
                                    }else {
                                        hzBomLineRecord.setLineIndex(new StringBuffer(lineIndex).append("." + lineNo).toString());
                                    }
                                }else {
                                    for (int j = 0; j < list.size(); j++) {
                                        String str = list.get(j).split("\\.")[list.get(j).split("\\.").length - 1];
                                        if (max < Integer.valueOf(str)) {
                                            max = Integer.valueOf(str);
                                        }
                                    }
                                    hzBomLineRecord.setLineIndex(new StringBuffer(lineIndex).append("." + (max+10)).toString());
                                }
                                for (int j = 0; j < l.size(); j++) {

                                    //设置排序号
                                    if(l.size() == 1){
                                        Integer order = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),l.get(0).getOrderNum());
                                        if(lineNo.equals("")){
                                            if(order == null){
                                                order =l.get(0).getOrderNum() +100;
                                                hzBomLineRecord.setOrderNum(order);
                                            }else {
                                                int i = (l.get(0).getOrderNum() +order)/2;
                                                hzBomLineRecord.setOrderNum(l.get(0).getOrderNum()+i);
                                            }
                                        } else {
                                            if(order == null) {
                                                order = l.get(0).getOrderNum() + 100;
                                            }
                                            int i1 = l.get(0).getLineIndex().split("\\.").length-1;
                                            int s1 = Integer.valueOf(l.get(0).getLineIndex().split("\\.")[i1]);
                                            if(Integer.valueOf(lineNo)<s1){
                                                int i = (l.get(0).getOrderNum() +records.get(0).getOrderNum())/2;
                                                hzBomLineRecord.setOrderNum(l.get(0).getOrderNum()+i);
                                            }else if(Integer.valueOf(lineNo)>s1){
                                                int i = (l.get(0).getOrderNum() +order)/2;
                                                hzBomLineRecord.setOrderNum(l.get(0).getOrderNum()+i);
                                            }else {
                                                operateResultMessageRespDTO.setErrMsg("不合法的查找编号输入！");
                                                return operateResultMessageRespDTO;
                                            }
                                        }

                                    } else{

                                        int i0 = l.get(0).getLineIndex().split("\\.").length-1;
                                        int s0 = Integer.valueOf(l.get(0).getLineIndex().split("\\.")[i0]);
                                        int iLast = l.get(l.size()-1).getLineIndex().split("\\.").length-1;
                                        int sLast = Integer.valueOf(l.get(l.size()-1).getLineIndex().split("\\.")[iLast]);
                                        if(lineNo.equals("")){
                                            Integer order = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),l.get(l.size()-1).getOrderNum());
                                            if(order == null){
                                                order =l.get(l.size()-1).getOrderNum()+100;
                                            }
                                            int i = (l.get(l.size()-1).getOrderNum() +order)/2;
                                            hzBomLineRecord.setOrderNum(l.get(l.size()-1).getOrderNum()+i);
                                            break;
                                        }else {
                                            if(Integer.valueOf(lineNo)< s0){//小于第一个子排序号
                                                Integer order = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),records.get(0).getOrderNum());
                                                if(order == null){
                                                    order = records.get(0).getOrderNum()+100;
                                                }
                                                int i = (l.get(0).getOrderNum() +order)/2;
                                                hzBomLineRecord.setOrderNum(l.get(0).getOrderNum()+i);
                                                break;
                                            }else if(Integer.valueOf(lineNo) >sLast){//大于最后一个子排序号
                                                Integer order = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),l.get(l.size()-1).getOrderNum());
                                                if(order == null){
                                                    order = l.get(l.size()-1).getOrderNum() +100;
                                                }
                                                int i = (l.get(l.size()-1).getOrderNum() +order)/2;
                                                hzBomLineRecord.setOrderNum(l.get(l.size()-1).getOrderNum()+i);
                                                break;
                                            }else {//一定在中间某个位置 可以插入
                                                if(j<=l.size()-2){
                                                    int i1 = l.get(j).getLineIndex().split("\\.").length-1;
                                                    int i2 = l.get(j+1).getLineIndex().split("\\.").length-1;
                                                    int s1 = Integer.valueOf(l.get(j).getLineIndex().split("\\.")[i1]);
                                                    int s2 = Integer.valueOf(l.get(j+1).getLineIndex().split("\\.")[i2]);
                                                    if(Integer.valueOf(lineNo)> s1 &&Integer.valueOf(lineNo)<s2){

                                                        Integer order = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),l.get(j).getOrderNum());
                                                        if(order == null){
                                                            order = l.get(j).getOrderNum()+100;
                                                        }
                                                        int i = (l.get(j).getOrderNum() +order)/2;
                                                        hzBomLineRecord.setOrderNum(l.get(j).getOrderNum()+i);
                                                        break;
                                                    }
                                                }else {
                                                    operateResultMessageRespDTO.setErrMsg("不合法的查找编号输入！");
                                                    return operateResultMessageRespDTO;
                                                }
                                            }

                                        }

                                    }

                                }

                            }
                            //只有2Y层有这个玩意
                            hzBomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
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
                            hzBomLineRecord.setpBuyEngineer(reqDTO.getpBuyEngineer());
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
                            hzBomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
                            hzBomLineRecord.setBomLineBlock(SerializeUtil.serialize(0));
                            hzBomLineRecord.setNumber(reqDTO.getNumber());
                            hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                            if(reqDTO.getpRegulationFlag().equals("Y")){
                                hzBomLineRecord.setpRegulationFlag(1);
                            }else {
                                hzBomLineRecord.setpRegulationFlag(0);
                            }

                            hzBomLineRecordDao.insert(hzBomLineRecord);
                            hzBomLineRecord.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
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
                               if (list2.get(i).getLineIndex().split("\\.").length == 2) {
                                   list2.get(i).setIs2Y(1);
                               }
                               //更新数据
                               hzPbomRecordDAO.update(list2.get(i));
                           }




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
                           if(ListUtil.isEmpty(records1)){
                               continue;
                           }
                           if (records1.size() ==1) {
                               //1.1-1.1.1  1.2.2.2 -1.2.2.2.1
                               StringBuffer stringBuffer = new StringBuffer(lineIndex1);
                               stringBuffer = stringBuffer.append(".10");
                               hzPbomLineRecord.setLineIndex(stringBuffer.toString());
                               int max = hzPbomRecordDAO.getMaxLineIndexFirstNum(reqDTO.getProjectId());
                               if(Integer.valueOf(lineIndex1.split("\\.")[0]).equals(max)){
                                   hzPbomLineRecord.setOrderNum(list2.get(i).getOrderNum()+100);
                               }else {
                                   hzPbomLineRecord.setOrderNum(list2.get(i).getOrderNum()+50);
                               }

                           } else {
                               int length = lineIndex1.split("\\.").length + 1;
                               List<String> list = new ArrayList<>();
                               List<HzPbomLineRecord> l = new ArrayList<>();
                               for (int k =0;k<records1.size();k++) {
                                   int len = records1.get(k).getLineIndex().split("\\.").length;
                                   if (length == len) {
                                       list.add(records1.get(k).getLineIndex());
                                       l.add(records1.get(k));
                                   }
                               }
                               Integer max = 0;
                               if(!lineNo.equals("")){
                                   //找出lineindex 末尾最大值
                                   for (int j = 0; j < list.size(); j++) {
                                       String str = list.get(j).split("\\.")[list.get(j).split("\\.").length - 1];

                                       if ( max < Integer.valueOf(str)) {
                                           if(Integer.valueOf(lineNo).equals(Integer.valueOf(str))){
                                               operateResultMessageRespDTO.setErrMsg("不合法的查找编号输入！");
                                               return operateResultMessageRespDTO;
                                           }
                                           max = Integer.valueOf(str);
                                       }
                                   }
                                   if(max<Integer.valueOf(lineNo)){
                                       hzPbomLineRecord.setLineIndex(new StringBuffer(lineIndex1).append("." + (max+10)).toString());
                                   }else {
                                       hzPbomLineRecord.setLineIndex(new StringBuffer(lineIndex1).append("." + lineNo).toString());
                                   }

                               }else {
                                   for (int j = 0; j < list.size(); j++) {
                                       String str = list.get(j).split("\\.")[list.get(j).split("\\.").length - 1];
                                       if ( max < Integer.valueOf(str)) {
                                           max = Integer.valueOf(str);
                                       }

                                   }
                                   hzPbomLineRecord.setLineIndex(new StringBuffer(lineIndex1).append("." + (max+10)).toString());

                               }

                               for (int j = 0; j < l.size(); j++) {
                                   //设置排序号
                                   if(l.size() == 1){

                                       Integer order = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),l.get(0).getOrderNum());
                                       if(lineNo.equals("")){
                                           if(order == null){
                                               order = l.get(0).getOrderNum()+100;
                                               hzPbomLineRecord.setOrderNum(order);
                                           }else {
                                               int k = (l.get(0).getOrderNum() +order)/2;
                                               hzPbomLineRecord.setOrderNum(l.get(0).getOrderNum()+k);
                                           }
                                       }else {
                                           if(order == null) {
                                               order = l.get(0).getOrderNum() + 100;
                                           }

                                           int i1 = l.get(0).getLineIndex().split("\\.").length-1;
                                           int s1 = Integer.valueOf(l.get(0).getLineIndex().split("\\.")[i1]);
                                           if(Integer.valueOf(lineNo)<s1){
                                               int k = (records1.get(0).getOrderNum() +order)/2;
                                               hzPbomLineRecord.setOrderNum(l.get(0).getOrderNum()+k);
                                           }else if(Integer.valueOf(lineNo)>s1){
                                               int k = (l.get(0).getOrderNum() +order)/2;
                                               hzPbomLineRecord.setOrderNum(l.get(0).getOrderNum()+k);
                                           }else {
                                               operateResultMessageRespDTO.setErrMsg("不合法的查找编号输入！");
                                               return operateResultMessageRespDTO;
                                           }
                                       }

                                   } else{

                                       int i0 = l.get(0).getLineIndex().split("\\.").length-1;
                                       int s0 = Integer.valueOf(l.get(0).getLineIndex().split("\\.")[i0]);
                                       int iLast = l.get(l.size()-1).getLineIndex().split("\\.").length-1;
                                       int sLast = Integer.valueOf(l.get(l.size()-1).getLineIndex().split("\\.")[iLast]);

                                       if(lineNo.equals("")){
                                           Integer order = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),l.get(l.size()-1).getOrderNum());
                                           if(order == null){
                                               order = l.get(l.size()-1).getOrderNum()+100;
                                           }
                                           int k = (l.get(l.size()-1).getOrderNum() +order)/2;
                                           hzPbomLineRecord.setOrderNum(l.get(l.size()-1).getOrderNum()+k);
                                           break;
                                       }else {
                                           if(Integer.valueOf(lineNo)< s0){//小于第一个子排序号
                                               Integer order = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),records1.get(0).getOrderNum());
                                               order = records1.get(0).getOrderNum()+100;
                                               int k = (l.get(0).getOrderNum() +order)/2;
                                               hzPbomLineRecord.setOrderNum(l.get(0).getOrderNum()+k);
                                               break;
                                           }else if(Integer.valueOf(lineNo) >sLast){//大于最后一个子排序号
                                               Integer order = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),l.get(l.size()-1).getOrderNum());
                                               if(order == null){
                                                   order = l.get(l.size()-1).getOrderNum()+100;
                                               }
                                               int k = (l.get(l.size()-1).getOrderNum() +order)/2;
                                               hzPbomLineRecord.setOrderNum(l.get(l.size()-1).getOrderNum()+k);
                                               break;
                                           }else {//一定在中间某个位置 可以插入
                                               if(j<=l.size()-2){
                                                   int i1 = l.get(j).getLineIndex().split("\\.").length-1;
                                                   int i2 = l.get(j+1).getLineIndex().split("\\.").length-1;
                                                   int s1 = Integer.valueOf(l.get(j).getLineIndex().split("\\.")[i1]);
                                                   int s2 = Integer.valueOf(l.get(j+1).getLineIndex().split("\\.")[i2]);
                                                   if(Integer.valueOf(lineNo)> s1 &&Integer.valueOf(lineNo)<s2){
                                                       Integer order = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),l.get(j).getOrderNum());
                                                       if(order == null){
                                                           order = l.get(j).getOrderNum()+100;
                                                       }
                                                       int k = (l.get(j).getOrderNum() +order)/2;
                                                       hzPbomLineRecord.setOrderNum(l.get(j).getOrderNum()+k);
                                                       break;
                                                   }
                                               }else {
                                                   operateResultMessageRespDTO.setErrMsg("不合法的查找编号输入！");
                                                   return operateResultMessageRespDTO;
                                               }
                                           }
                                       }
                                   }

                               }
                           }
                           pbomLineRecords.add(hzPbomLineRecord);
                       }
                        hzPbomRecordDAO.insertList(pbomLineRecords);
                    }




                    List<String> stringList = hzMbomService.loadingCarPartType();
                    if(ListUtil.isNotEmpty(list3)){

                        List<HzMbomLineRecord> hzMbomLineRecords = new ArrayList<>();
                            if( stringList.contains(reqDTO.getpBomLinePartResource()) ){
                                for(int i = 0;i<list3.size();i++){
                                    HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();//MBOM
                                    if (list3.get(i).getIsHas().equals(0) || list3.get(i).getIsPart().equals(1)) {
                                        list3.get(i).setIsHas(new Integer(1));
                                        list3.get(i).setIsPart(new Integer(0));
                                        if (list3.get(i).getLineIndex().split("\\.").length == 2) {
                                            list3.get(i).setIs2Y(1);
                                        }
                                        //更新数据
                                        hzMbomRecordDAO.update(list3.get(i));
                                    }
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
                                if(ListUtil.isEmpty(records2)){
                                    continue;
                                }
                                if (records2.size() ==1) {
                                    StringBuffer stringBuffer = new StringBuffer(lineIndex1);
                                    stringBuffer = stringBuffer.append(".10");
                                    hzMbomLineRecord.setLineIndex(stringBuffer.toString());
                                    int max = hzMbomRecordDAO.getMaxLineIndexFirstNum(reqDTO.getProjectId());
                                    if(Integer.valueOf(lineIndex1.split("\\.")[0]).equals(max)){
                                        hzMbomLineRecord.setOrderNum(records2.get(0).getOrderNum()+100);
                                    }else {
                                        hzMbomLineRecord.setOrderNum(records2.get(0).getOrderNum()+50);
                                    }
                                } else {
                                    int length = lineIndex1.split("\\.").length + 1;
                                    List<String> list = new ArrayList<>();
                                    List<HzMbomLineRecord> l = new ArrayList<>();
                                    for (int k =0;k<records2.size();k++) {
                                        int len = records2.get(k).getLineIndex().split("\\.").length;
                                        if (length == len) {
                                            list.add(records2.get(k).getLineIndex());
                                            l.add(records2.get(k));
                                        }
                                    }
                                    Integer max = 0;
                                    if(!lineNo.equals("")){
                                        //找出lineindex 末尾最大值
                                        for (int j = 0; j < list.size(); j++) {
                                            String str = list.get(j).split("\\.")[list.get(j).split("\\.").length - 1];
                                            if (max < Integer.valueOf(str)) {
                                                if(Integer.valueOf(lineNo).equals(Integer.valueOf(str))){
                                                    operateResultMessageRespDTO.setErrMsg("不合法的查找编号输入！");
                                                    return operateResultMessageRespDTO;
                                                }
                                                max = Integer.valueOf(str);
                                            }
                                        }
                                        if(max<Integer.valueOf(lineNo)){
                                            hzMbomLineRecord.setLineIndex(new StringBuffer(lineIndex1).append("." + (max+10)).toString());
                                        }else {
                                            hzMbomLineRecord.setLineIndex(new StringBuffer(lineIndex1).append("." + lineNo).toString());
                                        }
                                    }else {
                                        for (int j = 0; j < list.size(); j++) {
                                            String str = list.get(j).split("\\.")[list.get(j).split("\\.").length - 1];
                                            if (max < Integer.valueOf(str)) {
                                                max = Integer.valueOf(str);
                                            }
                                        }
                                        hzMbomLineRecord.setLineIndex(new StringBuffer(lineIndex1).append("." + (max+10)).toString());

                                    }


                                    for (int j = 0; j < l.size(); j++) {
                                        //设置排序号
                                        if (l.size() == 1) {
                                            Integer order = hzMbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), l.get(0).getOrderNum());
                                            if(lineNo.equals("")){
                                                if(order == null){
                                                    order = l.get(0).getOrderNum()+100;
                                                    hzMbomLineRecord.setOrderNum(order);

                                                }else {
                                                    int k = (l.get(0).getOrderNum() + order) / 2;
                                                    hzMbomLineRecord.setOrderNum(l.get(0).getOrderNum() + k);
                                                }
                                            } else {
                                                int i1 = l.get(0).getLineIndex().split("\\.").length - 1;
                                                int s1 = Integer.valueOf(l.get(0).getLineIndex().split("\\.")[i1]);
                                                if (Integer.valueOf(lineNo) < s1) {
                                                    int k = (records2.get(0).getOrderNum() + order) / 2;
                                                    hzMbomLineRecord.setOrderNum(l.get(0).getOrderNum() + k);
                                                } else if (Integer.valueOf(lineNo) > s1) {
                                                    int k = (l.get(0).getOrderNum() + order) / 2;
                                                    hzMbomLineRecord.setOrderNum(l.get(0).getOrderNum() + k);
                                                } else {
                                                    operateResultMessageRespDTO.setErrMsg("不合法的查找编号输入！");
                                                    return operateResultMessageRespDTO;
                                                }
                                            }

                                        } else {
                                            int i0 = l.get(0).getLineIndex().split("\\.").length - 1;
                                            int s0 = Integer.valueOf(l.get(0).getLineIndex().split("\\.")[i0]);
                                            int iLast = l.get(l.size() - 1).getLineIndex().split("\\.").length - 1;
                                            int sLast = Integer.valueOf(l.get(l.size() - 1).getLineIndex().split("\\.")[iLast]);

                                            if(lineNo.equals("")){
                                                Integer order = hzMbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), l.get(l.size() - 1).getOrderNum());
                                                if(order == null){
                                                    order = l.get(l.size() - 1).getOrderNum()+100;
                                                }
                                                int k = (l.get(l.size() - 1).getOrderNum() + order) / 2;
                                                hzMbomLineRecord.setOrderNum(l.get(l.size() - 1).getOrderNum() + k);
                                                break;
                                            }else {
                                                if (Integer.valueOf(lineNo) < s0) {//小于第一个子排序号
                                                    Integer order = hzMbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), records2.get(0).getOrderNum());
                                                    if(order == null){
                                                        order = records2.get(0).getOrderNum()+100;
                                                    }
                                                    int k = (l.get(0).getOrderNum() + order) / 2;
                                                    hzMbomLineRecord.setOrderNum(l.get(0).getOrderNum() + k);
                                                    break;
                                                } else if (Integer.valueOf(lineNo) > sLast) {//大于最后一个子排序号
                                                    Integer order = hzMbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), l.get(l.size() - 1).getOrderNum());
                                                    if(order == null){
                                                        order = l.get(l.size() - 1).getOrderNum()+100;
                                                    }
                                                    int k = (l.get(l.size() - 1).getOrderNum() + order) / 2;
                                                    hzMbomLineRecord.setOrderNum(l.get(l.size() - 1).getOrderNum() + k);
                                                    break;
                                                } else {//一定在中间某个位置 可以插入
                                                    if (j <= l.size() - 2) {
                                                        int i1 = l.get(j).getLineIndex().split("\\.").length - 1;
                                                        int i2 = l.get(j + 1).getLineIndex().split("\\.").length - 1;
                                                        int s1 = Integer.valueOf(l.get(j).getLineIndex().split("\\.")[i1]);
                                                        int s2 = Integer.valueOf(l.get(j + 1).getLineIndex().split("\\.")[i2]);
                                                        if (Integer.valueOf(lineNo) > s1 && Integer.valueOf(lineNo) < s2) {
                                                            Integer order = hzMbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), l.get(j).getOrderNum());
                                                            if(order == null){
                                                                order = l.get(j).getOrderNum()+100;
                                                            }
                                                            int k = (l.get(j).getOrderNum() + order) / 2;
                                                            hzMbomLineRecord.setOrderNum(l.get(j).getOrderNum() + k);
                                                            break;
                                                        }
                                                    } else {
                                                        operateResultMessageRespDTO.setErrMsg("不合法的查找编号输入！");
                                                        return operateResultMessageRespDTO;
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                                hzMbomLineRecords.add(hzMbomLineRecord);
                        }
                    }
                    if(ListUtil.isNotEmpty(hzMbomLineRecords)){
                        hzMbomRecordDAO.insertList(hzMbomLineRecords);
                    }
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
//                HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();//物料
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
                max = max+10;
                StringBuffer buffer = new StringBuffer(String.valueOf(max));
                hzBomLineRecord.setLineIndex(buffer.append(".10").toString());
                Integer maxGroupNum =hzBomLineRecordDao.findMaxBomOrderNum(reqDTO.getProjectId());
                if(maxGroupNum == null){
                    maxGroupNum = 100;
                    hzBomLineRecord.setOrderNum(maxGroupNum);
                }else {
                    hzBomLineRecord.setOrderNum(maxGroupNum+100);
                }

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

                hzBomLineRecord.setpBuyEngineer(reqDTO.getpBuyEngineer());
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
                hzBomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
                if(reqDTO.getpRegulationFlag().equals("Y")){
                    hzBomLineRecord.setpRegulationFlag(1);
                }else {
                    hzBomLineRecord.setpRegulationFlag(0);
                }

                 hzBomLineRecordDao.insert(hzBomLineRecord);

                hzBomLineRecord.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                hzBomLineRecordDao.insert(hzBomLineRecord);
                Integer num = hzPbomRecordDAO.getHzPbomMaxOrderNum(reqDTO.getProjectId());
                if(num == null){
                    num = 100;
                    hzPbomLineRecord.setOrderNum(num);
                }else {
                    hzPbomLineRecord.setOrderNum(num+100);
                }
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

                hzPbomLineRecord.setIs2Y(0);
                hzPbomLineRecord.setLinePuid(puid);
                hzPbomLineRecord.setIsDept(0);
                hzPbomLineRecord.setIsHas(0);
                hzPbomLineRecord.setIsPart(1);

                Integer maxLineIndexFirstNum = hzPbomRecordDAO.getMaxLineIndexFirstNum(reqDTO.getProjectId());
                StringBuffer stringBuffer = new StringBuffer();
                if(maxLineIndexFirstNum == null){
                    maxLineIndexFirstNum  =10;
                     stringBuffer = new StringBuffer(String.valueOf(maxLineIndexFirstNum));
                }else {
                     stringBuffer = new StringBuffer(String.valueOf(maxLineIndexFirstNum+10));
                }

                hzPbomLineRecord.setLineIndex(stringBuffer.append(".10").toString());
                List<HzPbomLineRecord> pbomLineRecords = new ArrayList<>();
                pbomLineRecords.add(hzPbomLineRecord);
                hzPbomRecordDAO.insertList(pbomLineRecords);


                List<String> stringList = hzMbomService.loadingCarPartType();
                if(stringList.contains(reqDTO.getpBomLinePartResource()) ){
                    Integer mbomMaxOrderNum = hzMbomRecordDAO.getHzMbomMaxOrderNum(reqDTO.getProjectId());
                    if(mbomMaxOrderNum == null){
                        mbomMaxOrderNum  =100;
                        hzMbomLineRecord.setOrderNum(mbomMaxOrderNum);
                    }else {
                        hzMbomLineRecord.setOrderNum(mbomMaxOrderNum+100);
                    }
                    Integer maxIndexFirstNum = hzMbomRecordDAO.getMaxLineIndexFirstNum(reqDTO.getProjectId());
                    if(maxIndexFirstNum == null){
                        maxIndexFirstNum  =10;
                        stringBuffer = new StringBuffer(String.valueOf(maxIndexFirstNum));
                    }else {
                        stringBuffer = new StringBuffer(String.valueOf(maxIndexFirstNum+10));
                    }
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

                    hzMbomLineRecord.setIs2Y(0);
                    hzMbomLineRecord.setLinePuid(puid);
                    hzMbomLineRecord.setIsDept(0);
                    hzMbomLineRecord.setIsHas(0);
                    hzMbomLineRecord.setIsPart(1);

                    hzMbomLineRecord.setLineIndex(stringBuffer.append(".10").toString());
//                    hzMaterielRecord.setpMaterielCode(reqDTO.getLineId());
//                    hzMaterielRecord.setpMaterielDesc(reqDTO.getpBomLinePartName());
//                    hzMaterielRecord.setpMaterielDescEn(reqDTO.getpBomLinePartEnName());
//                    hzMaterielRecord.setpBasicUnitMeasure(reqDTO.getpUnit());
//                    hzMaterielRecord.setpHeight(reqDTO.getpActualWeight());
//                    if(reqDTO.getpInOutSideFlag().equals("Y")){
//                        hzMaterielRecord.setpInOutSideFlag(1);
//                    }else {
//                        hzMaterielRecord.setpInOutSideFlag(0);
//                    }
//                    if(reqDTO.getP3cpartFlag().equals("Y")){
//                        hzMaterielRecord.setP3cPartFlag(1);
//                    }else {
//                        hzMaterielRecord.setP3cPartFlag(0);
//                    }
//                    hzMaterielRecord.setpPartImportantDegree(reqDTO.getpImportance());
//                    hzMaterielRecord.setMaterielResourceId(puid);
//                    hzMaterielRecord.setPuid(UUID.randomUUID().toString());
                    List<HzMbomLineRecord> mbomLineRecords = new ArrayList<>();
//                    List<HzMaterielRecord> hzMaterielRecords = new ArrayList<>();
                    mbomLineRecords.add(hzMbomLineRecord);
//                    hzMaterielRecords.add(hzMaterielRecord);
                    hzMbomRecordDAO.insertList(mbomLineRecords);
//                    hzMaterielDAO.insertList(hzMaterielRecords);
                }

                return OperateResultMessageRespDTO.getSuccessResult();

            }
        }catch (Exception e){
            return  OperateResultMessageRespDTO.getFailResult();
        }
    }



    @Override
    public OperateResultMessageRespDTO updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO) {
        OperateResultMessageRespDTO operateResultMessageRespDTO = new OperateResultMessageRespDTO();
        try{
            if(reqDTO.getParentLineId() != null && reqDTO.getParentLineId() !=""){//调层级关系
                String parentLineId = reqDTO.getParentLineId();
                Map<String,Object> objectMap = new HashMap<>();
                objectMap.put("lineID",parentLineId);
                objectMap.put("lineId",parentLineId);
                objectMap.put("projectId",reqDTO.getProjectId());
                List<HzEPLManageRecord> list1 =  hzEbomRecordDAO.findEbom(objectMap);//新父
                if(ListUtil.isEmpty(list1)){
                    operateResultMessageRespDTO.setErrMsg("当前父零件号不存在！");
                    operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    return operateResultMessageRespDTO;
                }
                String puid = list1.get(0).getPuid();
                //找原来的父
                Map<String,Object> map = new HashMap<>();
                map.put("lineID",reqDTO.getLineId());
                map.put("lineId",reqDTO.getLineId());
                map.put("projectId",reqDTO.getProjectId());
                List<HzEPLManageRecord> oldList1 =  hzEbomRecordDAO.findEbom(map);//原先的父
                List<HzPbomLineRecord> oldList2  =  hzPbomRecordDAO.getPbomById(map);
                List<HzMbomLineRecord> oldList3  =  hzMbomRecordDAO.findHzMbomByPuid(map);

                if(ListUtil.isNotEmpty(oldList1)){
                    for(HzEPLManageRecord record:oldList1){
                        HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
                        hzEbomTreeQuery.setProjectId(reqDTO.getProjectId());
                        hzEbomTreeQuery.setPuid(record.getParentUid());
                        List<HzEPLManageRecord> hzEPLManageRecords = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);
                        if(ListUtil.isNotEmpty(hzEPLManageRecords) && hzEPLManageRecords.size() ==2){
                            if (hzEPLManageRecords.get(0).getIsHas().equals(1) || hzEPLManageRecords.get(0).getIsPart().equals(0)) {
                                HzBomLineRecord hzBomLineRecord1 = new HzBomLineRecord();
                                hzBomLineRecord1.setIsHas(new Integer(0));
                                hzBomLineRecord1.setIsPart(new Integer(1));
                                if (hzEPLManageRecords.get(0).getLineIndex().split("\\.").length == 2) {
                                    hzBomLineRecord1.setIs2Y(0);
                                }
                                hzBomLineRecord1.setPuid(hzEPLManageRecords.get(0).getPuid());
                                //更新数据
                                hzBomLineRecordDao.update(hzBomLineRecord1);
                            }
                        }
                        hzBomLineRecordDao.delete(record.getPuid());
                    }

                }

                if(ListUtil.isNotEmpty(oldList2)){
                    for(HzPbomLineRecord record:oldList2){
                        HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                        hzPbomTreeQuery.setProjectId(reqDTO.getProjectId());
                        hzPbomTreeQuery.setPuid(record.geteBomPuid());
                        List<HzPbomLineRecord> hzPbomLineRecords = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
                        if(ListUtil.isNotEmpty(hzPbomLineRecords) && hzPbomLineRecords.size() ==2){
                            if (hzPbomLineRecords.get(0).getIsHas().equals(1) || hzPbomLineRecords.get(0).getIsPart().equals(0)) {
                                HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
                                hzPbomLineRecord.setIsHas(new Integer(0));
                                hzPbomLineRecord.setIsPart(new Integer(1));
                                if (hzPbomLineRecord.getLineIndex().split("\\.").length == 2) {
                                    hzPbomLineRecord.setIs2Y(0);
                                }
                                hzPbomLineRecord.setPuid(hzPbomLineRecords.get(0).getPuid());
                                //更新数据
                                hzPbomRecordDAO.update(hzPbomLineRecord);
                            }

                        }
                        hzPbomRecordDAO.delete(record.geteBomPuid());
                    }
                }

                if(ListUtil.isNotEmpty(oldList3)){
                    for(HzMbomLineRecord record:oldList3){
                        HzMbomTreeQuery hzMbomTreeQuery = new HzMbomTreeQuery();
                        hzMbomTreeQuery.setProjectId(reqDTO.getProjectId());
                        hzMbomTreeQuery.setPuid(record.geteBomPuid());
                        List<HzMbomLineRecord> hzMbomLineRecords = hzMbomRecordDAO.getHzMbomTree(hzMbomTreeQuery);
                        if(ListUtil.isNotEmpty(hzMbomLineRecords) && hzMbomLineRecords.size() ==2){
                            if (hzMbomLineRecords.get(0).getIsHas().equals(1) || hzMbomLineRecords.get(0).getIsPart().equals(0)) {
                                HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();
                                hzMbomLineRecord.setIsHas(new Integer(0));
                                hzMbomLineRecord.setIsPart(new Integer(1));
                                if (hzMbomLineRecord.getLineIndex().split("\\.").length == 2) {
                                    hzMbomLineRecord.setIs2Y(0);
                                }
                                hzMbomLineRecord.setPuid(hzMbomLineRecords.get(0).getPuid());
                                //更新数据
                                hzMbomRecordDAO.update(hzMbomLineRecord);
                            }

                        }
                        hzMbomRecordDAO.delete(record.geteBomPuid());
                    }

                }

                AddHzEbomReqDTO addHzEbomReqDTO = new AddHzEbomReqDTO();
                addHzEbomReqDTO.setPuid(puid);
                addHzEbomReqDTO.setFastener(reqDTO.getFastener());
                addHzEbomReqDTO.setFna(reqDTO.getFna());
                addHzEbomReqDTO.setLineId(reqDTO.getLineId());
                addHzEbomReqDTO.setLineNo(reqDTO.getLineNo());
                addHzEbomReqDTO.setNumber(reqDTO.getNumber());
                addHzEbomReqDTO.setP3cpartFlag(reqDTO.getP3cpartFlag());
                addHzEbomReqDTO.setpActualWeight(reqDTO.getpActualWeight());
                addHzEbomReqDTO.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
                addHzEbomReqDTO.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
                addHzEbomReqDTO.setpBomLinePartName(reqDTO.getpBomLinePartName());
                addHzEbomReqDTO.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
                addHzEbomReqDTO.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
                addHzEbomReqDTO.setpBwgBoxPart(reqDTO.getpBwgBoxPart());
                addHzEbomReqDTO.setpDataVersion(reqDTO.getpDataVersion());
                addHzEbomReqDTO.setpDensity(reqDTO.getpDensity());
                addHzEbomReqDTO.setpDevelopType(reqDTO.getpDevelopType());
                addHzEbomReqDTO.setpDutyEngineer(reqDTO.getpDutyEngineer());
                addHzEbomReqDTO.setpFastener(reqDTO.getpFastener());
                addHzEbomReqDTO.setpFastenerLevel(reqDTO.getpFastenerLevel());
                addHzEbomReqDTO.setpFastenerStandard(reqDTO.getpFastenerStandard());
                addHzEbomReqDTO.setpFeatureWeight(reqDTO.getpFeatureWeight());
                addHzEbomReqDTO.setpFnaDesc(reqDTO.getpFnaDesc());
                addHzEbomReqDTO.setpImportance(reqDTO.getpImportance());
                addHzEbomReqDTO.setpInOutSideFlag(reqDTO.getpInOutSideFlag());
                addHzEbomReqDTO.setpManuProcess(reqDTO.getpManuProcess());
                addHzEbomReqDTO.setpMaterial1(reqDTO.getpMaterial1());
                addHzEbomReqDTO.setpMaterial2(reqDTO.getpMaterial2());
                addHzEbomReqDTO.setpMaterial3(reqDTO.getpMaterial3());
                addHzEbomReqDTO.setpMaterialHigh(reqDTO.getpMaterialHigh());
                addHzEbomReqDTO.setpMaterialStandard(reqDTO.getpMaterialStandard());
                addHzEbomReqDTO.setpPictureNo(reqDTO.getpPictureNo());
                addHzEbomReqDTO.setpPictureSheet(reqDTO.getpPictureSheet());
                addHzEbomReqDTO.setpRegulationCode(reqDTO.getpRegulationCode());
                addHzEbomReqDTO.setpRegulationFlag(reqDTO.getpRegulationFlag());
                addHzEbomReqDTO.setpRemark(reqDTO.getpRemark());
                addHzEbomReqDTO.setProjectId(reqDTO.getProjectId());
                addHzEbomReqDTO.setpSupply(reqDTO.getpSupply());
                addHzEbomReqDTO.setpSupplyCode(reqDTO.getpSupplyCode());
                addHzEbomReqDTO.setpSurfaceTreat(reqDTO.getpSurfaceTreat());
                addHzEbomReqDTO.setpSymmetry(reqDTO.getpSymmetry());
                addHzEbomReqDTO.setpTargetWeight(reqDTO.getpTargetWeight());
                addHzEbomReqDTO.setpTextureColorNum(reqDTO.getpTextureColorNum());
                addHzEbomReqDTO.setpTorque(reqDTO.getpTorque());
                addHzEbomReqDTO.setpUnit(reqDTO.getpUnit());
                addHzEbomReqDTO.setpUpc(reqDTO.getpUpc());
                addHzEbomReqDTO.setpBuyEngineer(reqDTO.getpDutyEngineer());
                return addHzEbomRecord(addHzEbomReqDTO);

            }else {//不调层级关系
                /**
                 * 业务涉及到变更 需要走流程进行审核，走流程时需要查看变更前和变更后的数据，所以需要记录变更前的数据；
                 * 这里每次走更新数据的时候先将原来未更新的数据保存一份，更新后的数据也保存一份，类似于查看变更历史记录的
                 * 功能。
                 */
                boolean isRepeat = hzEbomRecordDAO.checkItemIdIsRepeat(reqDTO.getProjectId(),reqDTO.getLineId());
                if(isRepeat){
                    OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
                    respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    respDTO.setErrMsg("非法操作,要修改的零件号已存在！");
                    return respDTO;
                }
                Map<String,Object> bomLineMap = new HashMap<>();
                bomLineMap.put("puid",reqDTO.getPuid());
                bomLineMap.put("projectId",reqDTO.getProjectId());
                HzBomLineRecord bomLineRecord = hzBomLineRecordDao.findBomLineByPuid(bomLineMap);//未修改前的数据
                if(bomLineRecord != null){
                    bomLineMap.put("tableName","HZ_EBOM_REOCRD_BEFORE_CHANGE");
                    List<HzBomLineRecord> records = hzBomLineRecordDao.findBomListForChange(bomLineMap);
                    bomLineRecord.setTableName("HZ_EBOM_REOCRD_BEFORE_CHANGE");
                    if(ListUtil.isEmpty(records)){//不存在时 添加进去
                        hzBomLineRecordDao.insert(bomLineRecord);
                    }else{//存在记录 查看是否都有产生流程历史记录
                        boolean insert = true;
                       for(HzBomLineRecord record :records){
                           if(record.getEwoNo() == null || record.getEwoNo().equals("")){
                               insert = false;
                               break;
                           }
                       }
                       if(insert){
                        hzBomLineRecordDao.insert(bomLineRecord);
                       }
                    }

                }else {
                    return OperateResultMessageRespDTO.getFailResult();
                }

                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
                HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
//            byte[] bytes = SerializeUtil.serialize(ebomContent);
                hzBomLineRecord.setLineID(reqDTO.getLineId());
                hzBomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
//            hzBomLineRecord.setBomLineBlock(bytes);
//            hzBomLineRecord.setPuid(reqDTO.getPuid());
                hzBomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
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
                hzBomLineRecord.setpBuyEngineer(reqDTO.getpDutyEngineer());

                //需要记录数据 变更后的数据
                bomLineMap.put("tableName","HZ_EBOM_REOCRD_AFTER_CHANGE");
                List<HzBomLineRecord> rs = hzBomLineRecordDao.findBomListForChange(bomLineMap);
                HzBomLineRecord h = hzBomLineRecord;
                if(ListUtil.isEmpty(rs)){
                    h.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                    h.setPuid(reqDTO.getPuid());
                    h.setLineIndex(bomLineRecord.getLineIndex());
                    h.setOrderNum(bomLineRecord.getOrderNum());
                    h.setIsHas(bomLineRecord.getIsHas());
                    h.setIs2Y(bomLineRecord.getIs2Y());
                    h.setIsPart(bomLineRecord.getIsPart());
                    h.setLinePuid(bomLineRecord.getLinePuid());
                    hzBomLineRecordDao.insert(h);
                }else {
                    boolean update = false;
                    Long id = 0L;
                    for(HzBomLineRecord r :rs){
                        if(r.getEwoNo()!=null && "1".equals(r.getEwoNo())){
                            r.setEwoNo(null);
                        }
                        if(r.getEwoNo() == null || r.getEwoNo().equals("")){
                            id = r.getId();
                            update = true;
                            break;
                        }
                    }

                    if(update){
                        h.setLineIndex(bomLineRecord.getLineIndex());
                        h.setOrderNum(bomLineRecord.getOrderNum());
                        if(id!=0L){
                            h.setEwoNo("1");
                            h.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                            h.setPuid(reqDTO.getPuid());
                            hzBomLineRecordDao.update(h);
                        }

                    }

                }

                hzBomLineRecord.setTableName(null);
                hzBomLineRecordDao.update(hzBomLineRecord);
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
                        for(HzEPLManageRecord hzEPLManageRecord :hzEPLManageRecords){
                            if(hzEPLManageRecord.getPuid().equals(reqDTO.getPuid())){
                                hzMbomLineRecord.setOrderNum(hzEPLManageRecord.getOrderNum());
                                hzMbomLineRecord.setLineIndex(hzEPLManageRecord.getLineIndex());
                                hzMbomLineRecord.setIs2Y(hzEPLManageRecord.getIs2Y());
                                hzMbomLineRecord.setIsPart(hzEPLManageRecord.getIsPart());
                                hzMbomLineRecord.setIsHas(hzEPLManageRecord.getIsHas());
                                hzMbomLineRecord.setParentUid(hzEPLManageRecord.getParentUid());
                                hzMbomLineRecord.setIsDept(hzEPLManageRecord.getIsDept());
                                hzMbomLineRecord.seteBomPuid(hzEPLManageRecord.getPuid());
                                hzMbomLineRecord.setLinePuid(hzEPLManageRecord.getLinePuid());
                            }
                            break;
                        }
                        hzMbomLineRecord.setPuid(UUID.randomUUID().toString());
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

            }

        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
//        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO deleteHzEbomRecordById(DeleteHzEbomReqDTO reqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
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
                if(ListUtil.isNotEmpty(lineRecords)){//将删除数据在历史记录表里做备份,删除前的数据状态也要做记录
                    for(int i = 0;i<lineRecords.size();i++){
                        Map<String,Object> after = new HashMap<>();
                        after.put("tableName","HZ_EBOM_REOCRD_AFTER_CHANGE");
                        after.put("puid",lineRecords.get(i).getPuid());
                        after.put("projectId",reqDTO.getProjectId());

                        Map<String,Object> before = new HashMap<>();
                        before.put("tableName","HZ_EBOM_REOCRD_BEFORE_CHANGE");
                        before.put("puid",lineRecords.get(i).getPuid());
                        before.put("projectId",reqDTO.getProjectId());

                        HzBomLineRecord record  = hzBomLineRecordDao.findBomLineByPuid(after);
                        HzBomLineRecord beforeRecord  = hzBomLineRecordDao.findBomLineByPuid(before);
                        if(beforeRecord == null){//不存在 记录添加进去
                            HzEPLManageRecord be = lineRecords.get(i);
                            be.setTableName("HZ_EBOM_REOCRD_BEFORE_CHANGE");
                            hzEbomRecordDAO.insert(be);
                        }else {//存在记录 查看是否都有产生流程历史记录
                            if(beforeRecord.getEwoNo() != null && !beforeRecord.getEwoNo().equals("")){
                                HzEPLManageRecord be = lineRecords.get(i);
                                be.setTableName("HZ_EBOM_REOCRD_BEFORE_CHANGE");
                                hzEbomRecordDAO.insert(be);
                            }
                        }

                        if(record!=null &&record.getEwoNo()!=null&&record.getEwoNo().equals("1")){
                            if(!record.getStatus().equals(4)){
                                record.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                                record.setStatus(4);//删除状态
                                hzBomLineRecordDao.update(record);
                            }
                        }else if(record!=null && (null == record.getEwoNo() ||"".equals(record.getEwoNo()))) {
                            if(!record.getStatus().equals(4)){
                                record.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                                record.setStatus(4);//删除状态
                                hzBomLineRecordDao.update(record);
                            }
                        }else if(record == null){
                            HzEPLManageRecord r = lineRecords.get(i);
                            r.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                            r.setStatus(4);
                            hzEbomRecordDAO.insert(r);
                        }


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
                    deleteHzEbomReqDTO.setStatus(4);
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

    @Override
    public HzBomLineRecord findParentFor2Y(String projectId, String puid) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("puid",puid);
       List<HzBomLineRecord> records = hzBomLineRecordDao.findBomListForChange(map);
       if(ListUtil.isNotEmpty(records)){
           if(records.get(0).getIs2Y().equals(1)){
               return records.get(0);
           }else if(records.get(0).getParentUid() == null){
               return records.get(0);
           }
           else {
               return  findParentFor2Y(projectId,records.get(0).getParentUid());
           }
       }else {
           return null;
       }

    }

    @Override
    public OperateResultMessageRespDTO setCurrentBomAsLou(SetLouReqDTO reqDTO) {
        try {
            /**
             * 设置为LOU 或者取消设置为LOU
             */
            if(reqDTO.getLineIds()==null || reqDTO.getProjectId() == null){
                return OperateResultMessageRespDTO.IllgalArgument();
            }
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            String[] lineIds = reqDTO.getLineIds().split(",");
            for(String lineId:lineIds){
                Map<String,Object> map = new HashMap<>();
                map.put("lineID",lineId);
                map.put("lineId",lineId);
                map.put("projectId",reqDTO.getProjectId());
                List<HzEPLManageRecord> ebomList =  hzEbomRecordDAO.findEbom(map);
                List<HzPbomLineRecord> pbomList =  hzPbomRecordDAO.getPbomById(map);
                List<HzMbomLineRecord> mbomList  =  hzMbomRecordDAO.findHzMbomByPuid(map);
                if(ListUtil.isNotEmpty(ebomList)){
                    List<HzBomLineRecord> list1 = new ArrayList<>();
                    Set<HzBomLineRecord> set = new HashSet<>();
                    ebomList.forEach(record -> {
                        HzEbomTreeQuery treeQuery = new HzEbomTreeQuery();
                        treeQuery.setProjectId(reqDTO.getProjectId());
                        treeQuery.setPuid(record.getPuid());
                        List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(treeQuery);
                        if(ListUtil.isNotEmpty(records) && records.size() > 1){//父层设置为LOU，子层全部为LOA
                            HzEPLManageRecord r = records.get(0);
                            for(int i = 1;i<records.size();i++){
                                HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                                if(Integer.valueOf(1).equals(r.getpLouaFlag())){
                                    hzBomLineRecord.setpLouaFlag(2);
                                }else {
                                    hzBomLineRecord.setpLouaFlag(0);
                                }
                                hzBomLineRecord.setPuid(records.get(i).getPuid());
                                hzBomLineRecord.setTableName("HZ_BOM_LINE_RECORD");
                                set.add(hzBomLineRecord);
                            }

                        }
                        HzBomLineRecord record1 = new HzBomLineRecord();
                        record1.setPuid(record.getPuid());
                        if(Integer.valueOf(1).equals(record.getpLouaFlag())){//是lou，则取消设置，否则，就设置为LOU
                            record1.setpLouaFlag(2);
                        }else {
                            record1.setpLouaFlag(1);
                        }
                        record1.setTableName("HZ_BOM_LINE_RECORD");
                        list1.add(record1);
                    });

                    hzBomLineRecordDao.updateBatch(list1);
                    List<HzBomLineRecord> list2 = new ArrayList<>(set);
                    hzBomLineRecordDao.updateBatch(list2);
                }

                if(ListUtil.isNotEmpty(pbomList)){
                    pbomList.forEach(hzPbomLineRecord -> {
                        if(Integer.valueOf(1).equals(hzPbomLineRecord.getpLouaFlag())){
                            hzPbomLineRecord.setpLouaFlag(2);
                        }else {
                            hzPbomLineRecord.setpLouaFlag(1);
                        }
                        hzPbomRecordDAO.update(hzPbomLineRecord);
                    });
                }
                if(ListUtil.isNotEmpty(mbomList)){
                    mbomList.forEach(hzMbomLineRecord -> {
                        hzMbomLineRecord.setpLouaFlag(Integer.valueOf(1).equals(hzMbomLineRecord.getpLouaFlag())?2:1);
                        hzMbomRecordDAO.update(hzMbomLineRecord);
                    });
                }

            }
            return OperateResultMessageRespDTO.getSuccessResult();
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public HzLouRespDTO getHzLouInfoById(HzLouaQuery query) {
        try {
            HzLouRespDTO respDTO = new HzLouRespDTO();
            HzBomLineRecord hzBomLineRecord = findParentFor2Y(query.getProjectId(),query.getPuid());
            if(hzBomLineRecord != null){
                HzCfg0OfBomLineRecord record = hzCfg0OfBomLineService.doSelectByBLUidAndPrjUid(query.getProjectId(),hzBomLineRecord.getPuid());
                if(record != null){
                    respDTO.setCfg0Desc(record.getCfg0Desc());
                    respDTO.setCfg0FamilyDesc(record.getCfg0FamilyDesc());
                    respDTO.setpCfg0name(record.getpCfg0name());
                    respDTO.setpCfg0familyname(record.getpCfg0familyname());
                    return  respDTO;
                }
            }
           return respDTO;
        }catch (Exception e){
            return null;
        }
    }
}
