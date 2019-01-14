package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0ModelService;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0OfBomLineService;
import com.connor.hozon.bom.bomSystem.service.interaction.HzCraftService;
import com.connor.hozon.bom.bomSystem.iservice.interaction.IHzCraftService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.constant.BOMTransConstants;
import com.connor.hozon.bom.resources.domain.constant.ChangeConstants;
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
import com.connor.hozon.bom.resources.mybatis.epl.HzEPLDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.sys.entity.User;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import sql.pojo.accessories.HzAccessoriesLibs;
import sql.pojo.bom.*;
import sql.pojo.cfg.fullCfg.HzCfg0OfBomLineRecord;
import sql.pojo.cfg.model.HzCfg0ModelRecord;
import sql.pojo.change.HzApplicantChangeRecord;
import sql.pojo.change.HzAuditorChangeRecord;
import sql.pojo.change.HzChangeDataRecord;
import sql.pojo.epl.HzEPLRecord;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.connor.hozon.bom.resources.domain.model.HzBomSysFactory.getLevelAndRank;

/**
 * Created by haozt on 2018/5/24
 */
@Service("hzPbomService")
public class HzPbomServiceImpl implements HzPbomService {
    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzCfg0OfBomLineService hzCfg0OfBomLineService;

    //工艺辅料库
    @Autowired
    private HzAccessoriesLibsDAO hzAccessoriesLibsDAO;

    @Autowired
    IHzCraftService iHzCraftService;

    @Autowired
    private HzCfg0ModelService hzCfg0ModelService;

    @Autowired
    private HzChangeDataRecordDAO hzChangeDataRecordDAO;

    @Autowired
    private HzEPLDAO hzEPLDAO;

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    private TransactionTemplate configTransactionTemplate;
    @Autowired
    public void setConfigTransactionTemplate(TransactionTemplate configTransactionTemplate) {
        this.configTransactionTemplate = configTransactionTemplate;
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
            String buyUnit = recordReqDTO.getBuyUnit();//采购单元
            String type = recordReqDTO.getType();//焊接/装配
            hzPbomRecord.setBuyUnit(BOMTransConstants.constantStringToInteger(buyUnit));
            hzPbomRecord.setType(BOMTransConstants.constantStringToInteger(type));
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
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public WriteResultRespDTO deleteHzPbomRecordByForeignId(DeleteHzPbomReqDTO reqDTO) {
        try {
            if (StringUtils.isBlank(reqDTO.getProjectId()) || StringUtils.isBlank(reqDTO.getPuids())) {
                return WriteResultRespDTO.IllgalArgument();
            }
            String bomPuids[] = reqDTO.getPuids().trim().split(",");
            String projectId = reqDTO.getProjectId();
            StringBuilder deletePuids = new StringBuilder();
            StringBuilder updatePuids = new StringBuilder();
            List<String> pbomHasChildren = new ArrayList<>();
            List<HzPbomLineRecord> updatePbomList = new ArrayList<>();

            for (String puid : bomPuids) {
                //PBOM 查找
                boolean deleteFlag = true;
                HzPbomLineRecord pbomRecord = hzPbomRecordDAO.getHzPbomByEbomPuid(puid,projectId);
                if(pbomRecord != null){
                    if(StringUtils.isNotBlank(pbomRecord.getRevision())){
                        deleteFlag = false;
                    }
                    pbomHasChildren.add(pbomRecord.getParentUid());
                    if(Integer.valueOf(1).equals(pbomRecord.getIsHas())){
                        HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                        hzPbomTreeQuery.setPuid(puid);
                        hzPbomTreeQuery.setProjectId(projectId);
                        List<HzPbomLineRecord> list = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
                        if(ListUtil.isNotEmpty(list)){
                            if(deleteFlag){
                                for(HzPbomLineRecord record : list){
                                    deletePuids.append(record.getPuid()+",");
                                }
                            }else {
                                for(HzPbomLineRecord record : list){
                                    updatePuids.append(record.getPuid()+",");
                                }
                            }
                        }
                    }else {
                        if(deleteFlag){
                            deletePuids.append(pbomRecord.getPuid()+",");
                        }else {
                            updatePuids.append(pbomRecord.getPuid()+",");
                        }

                    }
                }
            }

            configTransactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus status) {
                    if(StringUtils.isNotBlank(deletePuids.toString())){
                        hzPbomRecordDAO.deleteListByPuids(deletePuids.toString(),ChangeTableNameEnum.HZ_PBOM.getTableName());
                    }
                    if(StringUtils.isNotBlank(updatePuids.toString())){
                        hzPbomRecordDAO.deleteByPuids(updatePuids.toString());
                    }
                    // 如果删除后 元素的父没有子 需要更改isHas 属性为0 2Y 层除外
                    if(ListUtil.isNotEmpty(pbomHasChildren)){
                        pbomHasChildren.forEach(puid->{
                            HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                            hzPbomTreeQuery.setProjectId(projectId);
                            hzPbomTreeQuery.setPuid(puid);
                            List<HzPbomLineRecord> list = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
                            if(ListUtil.isNotEmpty(list) && list.size() ==1){
                                HzPbomLineRecord record = new HzPbomLineRecord();
                                record.setPuid(list.get(0).getPuid());
                                if(!Integer.valueOf(1).equals(list.get(0).getIs2Y())){
                                    record.setIsHas(0);
                                    updatePbomList.add(record);
                                }
                            }
                        });
                    }
                    if(ListUtil.isNotEmpty(updatePbomList)){
                        hzPbomRecordDAO.updateListByPuids(updatePbomList);
                    }
                    return null;
                }
            });
            return WriteResultRespDTO.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            jsonObject.put("pBomOfWhichDept", record.getpBomOfWhichDept());
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
            jsonObject.put("type", BOMTransConstants.integerToYNString(type));
            jsonObject.put("buyUnit",BOMTransConstants.integerToYNString(buyUnit));
            jsonArray.add(jsonObject);
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Page<HzPbomLineRespDTO> getHzPbomRecordPage(HzPbomByPageQuery query) {
        query = HzBomSysFactory.bomQueryLevelTrans(query);
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

            List<HzPbomLineRespDTO> respDTOS = pbomLineRecordToRespDTOS(records,hzCfg0ModelRecords,num);
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
                List<HzPbomLineRespDTO> respDTOS = pbomLineRecordToRespDTOS(records,null, 0);
                return respDTOS.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public List<HzPbomLineRecord> getHzPbomLineTree(HzPbomTreeQuery query) {
        return hzPbomRecordDAO.getHzPbomTree(query);
    }

    @Override
    public WriteResultRespDTO setCurrentBomAsLou(SetLouReqDTO reqDTO) {

        if(StringUtils.isBlank(reqDTO.getPuid()) || StringUtils.isBlank(reqDTO.getProjectId())){
            return WriteResultRespDTO.IllgalArgument();
        }

        String projectId = reqDTO.getProjectId();
        String puid = reqDTO.getPuid();
        //判断 如果已设置为LOU 则是取消设置 否则进行设置 父层为LOU 子层全部为LOA
        HzPbomTreeQuery hzPbomTreeQuery  = new HzPbomTreeQuery();
        hzPbomTreeQuery.setProjectId(projectId);
        hzPbomTreeQuery.setPuid(puid);
        List<HzPbomLineRecord> hzPbomLineRecords = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
        List<HzPbomLineRecord> recordList = new ArrayList<>();
        if(ListUtil.isNotEmpty(hzPbomLineRecords)){
            HzPbomLineRecord record = hzPbomLineRecords.get(0);
            if(Integer.valueOf(1).equals(record.getpLouaFlag())){//已设置为LOU 执行取消操作
                hzPbomLineRecords.forEach(hzPbomLineRecord -> {
                    HzPbomLineRecord updateLouRecord = new HzPbomLineRecord();
                    updateLouRecord.setpLouaFlag(2);
                    updateLouRecord.setPuid(hzPbomLineRecord.getPuid());
                    recordList.add(updateLouRecord);
                });
            }else if(Integer.valueOf(0).equals(record.getpLouaFlag())){//为LOA 进行提示
                return WriteResultRespDTO.failResultRespDTO("当前BOM结构中已存在LOU设置,取消后可以进行重新设置!");
            }else {//设置为LOU
                HzPbomLineRecord updateLouRecord  = new HzPbomLineRecord();
                updateLouRecord.setpLouaFlag(1);
                updateLouRecord.setPuid(record.getPuid());
                recordList.add(updateLouRecord);
                for(int i = 1;i < hzPbomLineRecords.size(); i++){
                    HzPbomLineRecord updateLoaRecord = new HzPbomLineRecord();
                    updateLoaRecord.setPuid(hzPbomLineRecords.get(i).getPuid());
                    updateLoaRecord.setpLouaFlag(0);
                    recordList.add(updateLoaRecord);
                }
            }
        }

        try {
            if(ListUtil.isNotEmpty(recordList)){
                hzPbomRecordDAO.updateListByPuids(recordList);
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }


    private List<HzPbomLineRespDTO> pbomLineRecordToRespDTOS(List<HzPbomLineRecord> records,List<HzCfg0ModelRecord> list, int num) {
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
                // todo 获取分组号
                respDTO.setGroupNum("");
                respDTO.setpBomLinePartClass(record.getpBomLinePartClass());
                respDTO.setpBomLinePartResource(record.getpBomLinePartResource());
                respDTO.setNo(++num);
                respDTO.setResource(record.getResource());
                Integer type = record.getType();
                Integer buyUnit = record.getBuyUnit();
                respDTO.setType(BOMTransConstants.integerToYNString(type));
                respDTO.setBuyUnit(BOMTransConstants.integerToYNString(buyUnit));
                respDTO.seteBomPuid(record.geteBomPuid());
                respDTO.setPuid(record.getPuid());
                respDTO.setProductLine(record.getProductLine());
                respDTO.setWorkShop1(record.getWorkShop1());
                respDTO.setWorkShop2(record.getWorkShop2());
                respDTO.setMouldType(record.getMouldType());
                respDTO.setOuterPart(record.getOuterPart());
                respDTO.setStation(record.getStation());
                respDTO.setpBomLinePartName(record.getpBomLinePartName());
                respDTO.setpBomLinePartEnName(record.getpBomLinePartEnName());
                respDTO.setEffectTime(DateUtil.formatDefaultDate(record.getEffectTime()));
                if (Integer.valueOf(1).equals(record.getpLouaFlag())) {
                    respDTO.setpLouaFlag("LOU");
                }else if(Integer.valueOf(0).equals(record.getpLouaFlag())){
                    respDTO.setpLouaFlag("LOA");
                }
                respDTO.setObject(hzSingleVehiclesServices.singleVehNum(record.getVehNum(),list));
                respDTO.setStatus(record.getStatus());
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
                    lineRecord.setStatus(ChangeConstants.VERIFY_STATUS);

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
                        dataDetailQuery.setPuid(record.getPuid());
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
                    hzPbomRecordDAO.deleteListByPuids(deletePuids.toString(),ChangeTableNameEnum.HZ_PBOM.getTableName());
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
            String num = HzBomSysFactory.generateBomSortNum(upNum, downNum);
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
            hzPbomLineRecordAddSon.setIsNewPart(1);
            hzPbomLineRecordsAddSons.add(hzPbomLineRecordAddSon);
            //修改父的isHas
            if (hzPbomLineRecord.getIsHas() != 1) {
                hzPbomLineRecord.setIsHas(1);
                hzPbomRecordDAO.update(hzPbomLineRecord);
            }
        }
        if (ListUtil.isNotEmpty(hzPbomLineRecordsAddSons) ) {
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

        HzEPLRecord hzEPLRecord = new HzEPLRecord();
        hzEPLRecord.setPartId(hzAccessoriesLibs.getpMaterielCode());
        hzEPLRecord.setPartName(hzAccessoriesLibs.getpMaterielName());
        hzEPLRecord.setProjectId(projectId);
        if(hzEPLDAO.insert(hzEPLRecord)<=0){
            result.put("success",false);
            result.put("errMsg", "添加失败");
        }

        return result;
    }


}
