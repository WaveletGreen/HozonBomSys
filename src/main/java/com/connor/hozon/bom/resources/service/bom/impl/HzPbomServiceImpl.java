package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzBomStateDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzPbomRecordDAOImpl;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzPbomByPageQuery;
import com.connor.hozon.bom.resources.query.HzPbomTreeQuery;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import share.bean.PreferenceSetting;
import share.bean.RedisBomBean;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.*;
import sql.redis.SerializeUtil;

import java.util.*;

/**
 * Created by haozt on 2018/5/24
 */
@Service("HzPbomService")
public class HzPbomServiceImpl implements HzPbomService {
    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzBomDataDao hzBomDataDao;

    @Autowired
    private HzBomStateDAO hzBomStateDAO;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    private HzEPLManageRecordService hzEPLManageRecordService;


    @Override
    public OperateResultMessageRespDTO insertHzPbomRecord(AddHzPbomRecordReqDTO recordReqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            User user = UserInfo.getUser();
            if(user.getGroupId()!=9){
                respDTO.setErrMsg("你当前没有权限执行此操作!");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
//            HzPbomRecord record = hzPbomRecordDAO.getHzPbomByEbomPuid(recordReqDTO.geteBomPuid());
//            if(record!=null){
//                respDTO.setErrMsg("当前插入的对象已存在,编辑属性请点击修改按钮进行操作!");
//                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
//                return respDTO;
//            }

            HzPbomRecord hzPbomRecord = new HzPbomRecord();
            hzPbomRecord.setCreateName(UserInfo.getUser().getUserName());
            hzPbomRecord.setUpdateName(UserInfo.getUser().getUserName());
            String buyUnit = recordReqDTO.getBuyUnit();
            String colorPart = recordReqDTO.getColorPart();
            String type = recordReqDTO.getType();
            if("Y".equals(buyUnit)){
                hzPbomRecord.setBuyUnit(0);
            }else if("N".equals(buyUnit)){
                hzPbomRecord.setBuyUnit(1);
            }else{
                hzPbomRecord.setBuyUnit(2);
            }
            if("Y".equals(colorPart)){
                hzPbomRecord.setColorPart(0);
            }else if("N".equals(colorPart)){
                hzPbomRecord.setColorPart(1);
            }else{
                hzPbomRecord.setColorPart(2);
            }
            if("Y".equals(type)){
                hzPbomRecord.setType(0);
            }else if("N".equals(type)){
                hzPbomRecord.setType(1);
            }else{
                hzPbomRecord.setType(2);
            }
            hzPbomRecord.setMouldType(recordReqDTO.getMouldType());
            hzPbomRecord.setOuterPart(recordReqDTO.getOuterPart());
            hzPbomRecord.setProductLine(recordReqDTO.getProductLine());
            hzPbomRecord.setStation(recordReqDTO.getStation());
            hzPbomRecord.setWorkShop1(recordReqDTO.getWorkShop1());
            hzPbomRecord.setWorkShop2(recordReqDTO.getWorkShop2());
            hzPbomRecord.seteBomPuid(recordReqDTO.geteBomPuid());
            Map<String,Object> map = new HashMap<>();
            map.put("pPuid",recordReqDTO.geteBomPuid());
            map.put("projectId",recordReqDTO.getProjectId());
            HzPbomLineRecord hzPbomLineRecord= hzPbomRecordDAO.getPbomById(map);
            if(hzPbomLineRecord!=null){
                int i = hzPbomRecordDAO.update(hzPbomRecord);
                if(i>0){
                    return OperateResultMessageRespDTO.getSuccessResult();
                }else {
                   return OperateResultMessageRespDTO.getFailResult();
                }
            }
            hzPbomRecord.setPuid(UUID.randomUUID().toString());
            int maxOrderNum = hzPbomRecordDAO.getHzPbomMaxOrderNum();
            hzPbomRecord.setOrderNum(++maxOrderNum);
            int i = hzPbomRecordDAO.insert(hzPbomRecord);
            if(i>0){
               respDTO.setErrMsg("操作成功！");
               respDTO.setErrCode(OperateResultMessageRespDTO.SUCCESS_CODE);
               return  respDTO;
            }else {
                respDTO.setErrMsg("操作失败，请稍后重试！");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
        }catch (Exception e){
            respDTO.setErrMsg("操作失败，请稍后重试！");
            respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
            return respDTO;
        }
    }

    @Override
    public OperateResultMessageRespDTO updateHzPbomRecord(UpdateHzPbomRecordReqDTO recordReqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            User user = UserInfo.getUser();
            if(user.getGroupId()!=9){
                respDTO.setErrMsg("你当前没有权限执行此操作!");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            HzPbomRecord hzPbomRecord = new HzPbomRecord();
            hzPbomRecord.setUpdateName(UserInfo.getUser().getUserName());
            hzPbomRecord.seteBomPuid(recordReqDTO.geteBomPuid());
//            hzPbomRecord.setPuid(UUID.randomUUID().toString());
            String buyUnit = recordReqDTO.getBuyUnit();
            String colorPart = recordReqDTO.getColorPart();
            String type = recordReqDTO.getType();
            if(buyUnit.equals("Y")){
                hzPbomRecord.setBuyUnit(0);
            }else if(buyUnit.equals("N")){
                hzPbomRecord.setBuyUnit(1);
            }else{
                hzPbomRecord.setBuyUnit(2);
            }
            if(colorPart.equals("Y")){
                hzPbomRecord.setColorPart(0);
            }else if(colorPart.equals("N")){
                hzPbomRecord.setColorPart(1);
            }else{
                hzPbomRecord.setColorPart(2);
            }
            if(type.equals("Y")){
                hzPbomRecord.setType(0);
            }else if(type.equals("N")){
                hzPbomRecord.setType(1);
            }else{
                hzPbomRecord.setType(2);
            }
            hzPbomRecord.setMouldType(recordReqDTO.getMouldType());
            hzPbomRecord.setOuterPart(recordReqDTO.getOuterPart());
            hzPbomRecord.setProductLine(recordReqDTO.getProductLine());
            hzPbomRecord.setStation(recordReqDTO.getStation());
            hzPbomRecord.setWorkShop1(recordReqDTO.getWorkShop1());
            hzPbomRecord.setWorkShop2(recordReqDTO.getWorkShop2());
            int i = hzPbomRecordDAO.update(hzPbomRecord);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }else {
                return OperateResultMessageRespDTO.getFailResult();
            }
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public OperateResultMessageRespDTO deleteHzPbomRecordByForeignId(String eBomPuid) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            User user = UserInfo.getUser();
            if(user.getGroupId()!=9){
                respDTO.setErrMsg("你当前没有权限执行此操作!");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            HzPbomRecord record = hzPbomRecordDAO.getHzPbomByEbomPuid(eBomPuid);
            if(null == record){
                record = new HzPbomRecord();
                record.seteBomPuid(eBomPuid);
                record.setPuid(UUID.randomUUID().toString());
                record.setStatus(1);
                int i =hzPbomRecordDAO.insert(record);
                if(i<0){
                    return OperateResultMessageRespDTO.getFailResult();
                }
            }
            int i = hzPbomRecordDAO.deleteByForeignId(eBomPuid);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }


    @Override
    public JSONArray getPbomForProcessCompose(HzPbomProcessComposeReqDTO reqDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", reqDTO.getProjectId());
        map.put("lineId", reqDTO.getLineId());
        HzPbomLineRecord record = hzPbomRecordDAO.getPbomById(map);
        if (record == null) {
            return null;
        }

        try {
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
        HzPreferenceSetting setting = new HzPreferenceSetting();
        setting.setSettingName("Hz_ExportBomPreferenceRedis");
        HzPbomLineRecord record = hzPbomRecordDAO.getPbomById(map);
        if (record == null) {
            return null;
        }
        try {
            JSONArray jsonArray = new JSONArray();
            setting.setBomMainRecordPuid(record.getBomDigifaxId());
            setting = hzBomDataDao.loadSetting(setting);
            byte[] btOfSetting = setting.getPreferencesettingblock();
            Object objOfSetting = SerializeUtil.unserialize(btOfSetting);
            if (objOfSetting instanceof PreferenceSetting) {
                String[] localName = ((PreferenceSetting) objOfSetting).getPreferenceLocal();
                String[] trueName = ((PreferenceSetting) objOfSetting).getPreferences();
                jsonArray.add(0, localName);
                jsonArray.add(1, trueName);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("puid", record.getpPuid());
            jsonObject.put("parentUid", record.getParentUid());
            jsonObject.put("hasChildren", record.getIsHas());
            Integer is2Y = record.getIs2Y();
            Integer hasChildren = record.getIsHas();
            String lineIndex = record.getLineIndex();
            String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
            jsonObject.put("level", strings[0]);
            jsonObject.put("rank", strings[1]);
            jsonObject.put("pBomOfWhichDept", record.getpBomOfWhichDept());
            jsonObject.put("groupNum", record.getLineId());
            jsonObject.put("eBomPuid", record.getPuid());
            jsonObject.put("lineId", record.getLineId());
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
            return jsonArray;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 添加工艺合件信息到bom
     * @param reqDTO
     * @return
     *  当前代码的事务 貌似并没起作用 原因正在找...
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int addPbomProcessCompose(final AddProcessComposeReqDTO reqDTO) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("puid", reqDTO.getPuid());
            map.put("projectId", reqDTO.getProjectPuid());
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
                return 0;
            }

            /**
             * 工艺合件
             * 1.获取合件的信息 零件信息 层级关系  需要更新父层的状态信息 haschildren 和ispart
             *   存数据库表里 返回id信息
             * 2.状态值存另一张表里 外键为 返回的id 需要采用事物管理机制
             * 3.存储数据库 默认为haschildren为0 ispart为1
             * 4.根据传进来的父id 计算层级关系 存数据库 2y
             * 5.lineindex 值 找出所有的父的子 看是否有下一层  有：列表全部显示 找出所有的lineindex 最大值 增0.1
             *   无：直接加1
             */

            //存工艺合件信息
            HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
            HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectPuid());
            if (hzBomMainRecord != null) {
                hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
            } else {
                return 0;
            }
            //EBOM的零件信息存大对象 P_BOM_LINE_BLOCK 中
            Map<String, Object> objectMap = reqDTO.geteBomContent();
            byte[] bytes = SerializeUtil.serialize(objectMap);
            hzBomLineRecord.setBomLineBlock(bytes);
            hzBomLineRecord.setIsPart(1);
            hzBomLineRecord.setIsHas(0);
            //获取当前对象的所有的子层 lineIndex值存长度相等的子层最大值末尾自增
            String lineIndex = record.getLineIndex();
            Map map1 = new HashMap();
            map1.put("projectId", reqDTO.getProjectPuid());
            map1.put("parentUid", record.getPuid());
            List<HzPbomLineRecord> bomLineRecords = hzPbomRecordDAO.getHzPbomById(map1);
            if (ListUtil.isEmpty(bomLineRecords)) {
                //1.1-1.1.1  1.2.2.2 -1.2.2.2.1
                StringBuffer stringBuffer = new StringBuffer(lineIndex);
                stringBuffer = stringBuffer.append(".1");
                hzBomLineRecord.setLineIndex(stringBuffer.toString());
            } else {
                int length = lineIndex.split("\\.").length + 1;
                List<String> list = new ArrayList<>();
                for (HzPbomLineRecord lineRecord : bomLineRecords) {
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
                max = max + 1;
                hzBomLineRecord.setLineIndex(new StringBuffer(lineIndex).append("." + max).toString());
            }
            //只有2Y层有这个玩意
            hzBomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
            int orderNum = hzBomLineRecordDao.findMaxBomOrderNum();
            hzBomLineRecord.setOrderNum(++orderNum);
            hzBomLineRecord.setParentUid(reqDTO.getPuid());
            hzBomLineRecord.setIs2Y(0);
            String puid = UUID.randomUUID().toString();
            hzBomLineRecord.setLinePuid(puid);
            hzBomLineRecord.setPuid(puid);
            hzBomLineRecord.setIsDept(0);
            hzBomLineRecord.setLineID(reqDTO.getLineId());
            hzBomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
            hzBomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
            int i = hzBomLineRecordDao.insert(hzBomLineRecord);
            //状态表中添加数据
            HzBomState state = new HzBomState();
            // 0 新增 1 更新 2 删除
            state.setpBomState(0);
            state.setPuid(UUID.randomUUID().toString());
            state.setpBomId(puid);
            int j = hzBomStateDAO.insert(state);
            //pbom表中添加数据否？ 暂时未定 后续测试出问题了再加进去
            if (i > 0 && j > 0) {
                return 1;
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 0;
    }

    @Override
    public Page<HzPbomLineRespDTO> getHzPbomRecordPage(HzPbomByPageQuery query) {

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

        int count = hzPbomRecordDAO.getHzBomLineCount(query.getProjectId());
        if(count<=0){
            List<HzBomLineRecord> lineRecords = hzBomLineRecordDao.getAllBomLineRecordByProjectId(query.getProjectId());
            if(ListUtil.isNotEmpty(lineRecords)){
                int size = lineRecords.size();
                //分批插入数据 一次1000条
                int i =0;
                int cou = 0;
                if(size>1000){
                    for(i =0;i<size/1000;i++){
                        List<HzPbomLineRecord> list = new ArrayList<>();

                        for(int j = 0;j<1000;j++){
                            HzPbomLineRecord hzPbomLineRecord =bomLineToPbomLine(lineRecords.get(cou));
                            list.add(hzPbomLineRecord);
                            cou++;
                        }
                        hzPbomRecordDAO.insertList(list);
                    }
                }
                if(i*1000<size){
                    List<HzPbomLineRecord> list = new ArrayList<>();
                    for(int j = 0;j<size-i*1000;j++){
                        HzPbomLineRecord hzPbomLineRecord =bomLineToPbomLine(lineRecords.get(cou));
                        list.add(hzPbomLineRecord);
                        cou++;
                    }
                    hzPbomRecordDAO.insertList(list);
                }
            }
        }
        try {
            Page<HzPbomLineRecord> recordPage =hzPbomRecordDAO.getHzPbomRecordByPage(query);
            if(recordPage == null || recordPage.getResult() == null){
                return null;
            }
            List<HzPbomLineRecord> records = recordPage.getResult();
            int num = (query.getPage()-1)*query.getLimit();
            List<HzPbomLineRespDTO> respDTOS = pbomLineRecordToRespDTOS(records,query.getProjectId(),num);
            return new Page<>(query.getPage(),query.getLimit(),recordPage.getTotalCount(),respDTOS);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public HzPbomLineRespDTO getHzPbomByPuid(String projectId, String puid) {
        try{
            Map<String,Object> map = new HashMap<>();
            map.put("projectId",projectId);
            map.put("pPuid",puid);
            HzPbomLineRecord record = hzPbomRecordDAO.getPbomById(map);
            if(record!=null){
                List<HzPbomLineRecord> records = new ArrayList<>();
                records.add(record);
                List<HzPbomLineRespDTO> respDTOS = pbomLineRecordToRespDTOS(records,projectId,0);
                return respDTOS.get(0);
            }
        }catch (Exception e){
            return  null;
        }
        return null;
    }

    @Override
    public List<HzPbomLineRecord> getHzPbomLineTree(HzPbomTreeQuery query) {
        return hzPbomRecordDAO.getHzPbomTree(query);
    }

    private HzPbomLineRecord bomLineToPbomLine(HzBomLineRecord record){
        HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
        hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzPbomLineRecord.setIsHas(record.getIsHas());
        hzPbomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzPbomLineRecord.seteBomPuid(record.getPuid());
        hzPbomLineRecord.setIsDept(record.getIsDept());
        hzPbomLineRecord.setLineId(record.getLineID());
        hzPbomLineRecord.setIsPart(record.getIsPart());
        hzPbomLineRecord.setIs2Y(record.getIs2Y());
        hzPbomLineRecord.setLineIndex(record.getLineIndex());
        hzPbomLineRecord.setParentUid(record.getParentUid());
        hzPbomLineRecord.setpBomLinePartClass(record.getpBomLinePartClass());
        hzPbomLineRecord.setpBomLinePartName(record.getpBomLinePartName());
        hzPbomLineRecord.setpBomOfWhichDept(record.getpBomOfWhichDept());
        hzPbomLineRecord.setpBomLinePartEnName(record.getpBomLinePartEnName());
        hzPbomLineRecord.setpBomLinePartResource(record.getpBomLinePartResource());
        hzPbomLineRecord.setOrderNum(record.getOrderNum());
        return hzPbomLineRecord;
    }

    /**
     * 获取bom系统的层级和级别
     *
     * @param lineIndex
     * @param is2Y
     * @param hasChildren
     * @return String[0]层级  String[1]级别
     */
    public static String[] getLevelAndRank(String lineIndex, Integer is2Y, Integer hasChildren) {
        int level = (lineIndex.split("\\.")).length;
        String line = "";
        int rank = 0;
        if (null != is2Y && is2Y.equals(1)) {
            line = "2Y";
            rank = 1;
        } else if (null != is2Y && is2Y.equals(0)) {
            if (hasChildren != null && hasChildren.equals(1)) {
                line = level + "Y";
                rank = level - 1;
            } else if (hasChildren != null && hasChildren.equals(0)) {
                line = String.valueOf(level);
                rank = level - 1;
            } else {
                line = "/";//错误数据
            }
        } else {
            line = "/";
        }
        return new String[]{line, String.valueOf(rank)};
    }

    private List<HzPbomLineRespDTO> pbomLineRecordToRespDTOS(List<HzPbomLineRecord> records,String projectId,int num) {
        try {
            List<HzPbomLineRespDTO> respDTOS = new ArrayList<>();
            for (HzPbomLineRecord record : records) {
                HzPbomLineRespDTO respDTO = new HzPbomLineRespDTO();
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                respDTO.setLevel(strings[0] == null ? "" : strings[0]);
                respDTO.setRank(strings[1] == null ? "" : strings[1]);
                respDTO.setLineId(record.getLineId() == null ? "" : record.getLineId());
                respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept() == null ? "" : record.getpBomOfWhichDept());
                //获取分组号
                String groupNum = record.getLineId();
                //这里在做一个递归查询
                if(groupNum.contains("-")){
                    groupNum =groupNum.split("-")[1].substring(0,4);
                }else{
                    String parentId = record.getParentUid();
                    groupNum = hzEPLManageRecordService.getGroupNum(projectId,parentId);
                }
                respDTO.setGroupNum(groupNum);

//                byte[] bomLineBlock =record.getBomLineBlock();
//                Object obj = SerializeUtil.unserialize(bomLineBlock);
//                Object h9_IsCommon = null;
//                Object H9_Mat_Status =null;
//                if (obj instanceof LinkedHashMap) {
//                    if (((LinkedHashMap) obj).size() > 0) {
//                         h9_IsCommon =((LinkedHashMap) obj).get("h9_IsCommon");
//                         H9_Mat_Status =((LinkedHashMap) obj).get("H9_Mat_Status");
//                    }
//                }
                respDTO.setpBomLinePartClass(record.getpBomLinePartClass());
                respDTO.setpBomLinePartResource(record.getpBomLinePartResource());
//                respDTO.setH9_IsCommon(h9_IsCommon);
//                respDTO.setH9_Mat_Status(H9_Mat_Status);
                respDTO.setNo(++num);
                respDTO.setResource(record.getResource() == null ? "/" : record.getResource());
                Integer type = record.getType();
                Integer buyUnit = record.getBuyUnit();
                Integer colorPart = record.getColorPart();
                if (Integer.valueOf(0).equals(type)) {
                    respDTO.setType("Y");
                } else if (Integer.valueOf(1).equals(type)) {
                    respDTO.setType("N");
                } else {
                    respDTO.setType("/");
                }
                if (Integer.valueOf(0).equals(buyUnit)) {
                    respDTO.setBuyUnit("Y");
                } else if (Integer.valueOf(1).equals(buyUnit)) {
                    respDTO.setBuyUnit("N");
                } else {
                    respDTO.setBuyUnit("/");
                }
                if (Integer.valueOf(0).equals(colorPart)) {
                    respDTO.setColorPart("Y");
                } else if (Integer.valueOf(1).equals(colorPart)) {
                    respDTO.setColorPart("N");
                } else {
                    respDTO.setColorPart("/");
                }
                respDTO.seteBomPuid(record.geteBomPuid());
                respDTO.setProductLine(record.getProductLine());
                respDTO.setWorkShop1(record.getWorkShop1() );
                respDTO.setWorkShop2(record.getWorkShop2());
                respDTO.setMouldType(record.getMouldType() );
                respDTO.setOuterPart(record.getOuterPart());
                respDTO.setStation(record.getStation());
                respDTO.setOrderNum(record.getOrderNum());
                respDTOS.add(respDTO);
            }
            return respDTOS;
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException(e.getMessage(),e);
        }
        return null;
    }
}
