package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzBomStateDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
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

    //走流程 这个比较麻烦
    @Override
    public int insertPbomLineMaintainRecords(List<InsertHzMbomMaintainRecordReqDTO> recordInsertBatchReqDTO) {

        List<HzMbomRecord> records = new ArrayList<>();
        for (InsertHzMbomMaintainRecordReqDTO recordReqDTO : recordInsertBatchReqDTO) {
            HzMbomRecord record = new HzMbomRecord();
            record.setChange(recordReqDTO.getChange());
            record.setChangeNum(recordReqDTO.getChangeNum());
            //这里需要注意一下  到时候转换成毫秒值存到数据库中
            record.setLaborHour(recordReqDTO.getLaborHour());
            record.setMachineMaterial(recordReqDTO.getMachineMaterial());
            record.setProcessRoute(recordReqDTO.getProcessRoute());
            record.setRhythm(recordReqDTO.getRhythm());
            record.setSolderJoint(recordReqDTO.getSolderJoint());
            record.setSparePart(recordReqDTO.getSparePart());
            record.setSparePartNum(recordReqDTO.getSparePartNum());
            record.setStandardPart(recordReqDTO.getStandardPart());
            record.setTools(recordReqDTO.getTools());
            record.setWasterProduct(recordReqDTO.getWasterProduct());
            record.setpBomPuid(recordReqDTO.getpBomPuid());
            record.setPuid(UUID.randomUUID().toString());
            records.add(record);
        }

        return hzMbomRecordDAO.insertList(records);
    }

    @Override
    public int updatePbomLineMaintainRecord(UpdateHzPbomMaintainRecordReqDTO recordReqDTO) {
        return 0;
    }



    @Override
    public List<HzPbomLineRespDTO> getHzPbomLineRecord(HzPbomProcessComposeReqDTO reqDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", reqDTO.getProjectId());
        List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomRecord(map);
        if (ListUtil.isEmpty(records)) {
            return null;
        }
        return pbomLineRecordToRespDTOS(records,"",0);
    }

    @Override
    public List<HzMbomRecordRespDTO> searchPbomLineMaintainRecord(SearchPbomDetailReqDTO reqDTO) {

        // 3Y---1.1.3    8Y   判断 带Y  和不带Y  前面的数字
        HzBomLineRecord record = new HzBomLineRecord();
        record.setLineID(reqDTO.getLineId());
        record.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
        String level = reqDTO.getLevel();
        if (null != level) {
            //这里+1 为了和数据库sql length函数做对应
            int length = level.charAt(0) - 48;
            if (level.toUpperCase().endsWith("Y")) {
                record.setIsHas(new Integer(1));
            } else {
                record.setIsHas(new Integer(0));
            }
            record.setLineIndex(String.valueOf(2 * length - 1));
        }
        //reqDTO.getName();//这个字段数据库表中暂时没有
        List<HzMbomLineRecord> records = hzMbomRecordDAO.searchMbomMaintainDetail(record);
        if (ListUtil.isEmpty(records)) {
            return null;
        }
        return pbomLineMaintailRecordToRespDTOS(records);
    }

    @Override
    public List<HzPbomLineRespDTO> searchPbomManageRecord(SearchPbomDetailReqDTO reqDTO) {
        HzBomLineRecord record = new HzBomLineRecord();
        record.setLineID(reqDTO.getLineId());
        record.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
        //分组号和零件分类暂时没有 后期加
        Integer rank = reqDTO.getRank();
        String level = reqDTO.getLevel();
        if (rank != null && level != null) {
            int length = level.charAt(0) - 48;
            if (!rank.equals(length)) {
                return null;
            }
        }
        if (level != null) {
            int length = level.charAt(0) - 48;
            if (level.toUpperCase().endsWith("Y")) {
                record.setIsHas(Integer.valueOf(1));
            } else {
                record.setIsHas(Integer.valueOf(0));
            }
            //1.1 3   1.1.1 5    2.2.2.2.2 9  1.1.1.1 7  2.2.2.2.2.2 11
            //2-3  3-5  4-7  5-9 6-11
            // an = 2n-1
            record.setLineIndex(String.valueOf(2 * length - 1));
        }
        if (rank != null) {
            //2  3y/3   1.1.1      2.1.1  3.1.1  5
            //4  5y/5  1.1.1.1.1   1.2.3.1.1  2.2.1.1.1 9
            //5  6y/6  11
            record.setLineIndex(String.valueOf(2 * rank + 1));
        }
        List<HzPbomLineRecord> records = hzPbomRecordDAO.searchPbomLineDetail(record);
        if (ListUtil.isEmpty(records)) {
            return null;
        }
        return pbomLineRecordToRespDTOS(records,"",0);
    }

    @Override
    public int insertHzPbomRecord(InsertHzPbomRecordReqDTO recordReqDTO) {
        return 0;
    }

    @Override
    public int updateHzPbomRecord(UpdateHzPbomRecordReqDTO recordReqDTO) {
        return 0;
    }

    @Override
    public int deleteHzPbomRecordByForeignId(String foreignPuid) {
        return 0;
    }


    @Override
    public JSONArray getHzPbomRecord(HzPbomProcessComposeReqDTO reqDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", reqDTO.getProjectId());
        HzPreferenceSetting setting = new HzPreferenceSetting();
        setting.setSettingName("Hz_ExportBomPreferenceRedis");
        List<HzPbomLineRecord> pbomLineRecords = hzPbomRecordDAO.getPbomRecord(map);
        if (ListUtil.isEmpty(pbomLineRecords)) {
            return null;
        }
        try {
            JSONArray jsonArray = new JSONArray();
            setting.setBomMainRecordPuid(pbomLineRecords.get(0).getBomDigifaxId());
            setting = hzBomDataDao.loadSetting(setting);
            byte[] btOfSetting = setting.getPreferencesettingblock();
            Object objOfSetting = SerializeUtil.unserialize(btOfSetting);
            if (objOfSetting instanceof PreferenceSetting) {
                String[] localName = ((PreferenceSetting) objOfSetting).getPreferenceLocal();
                String[] trueName = ((PreferenceSetting) objOfSetting).getPreferences();
                jsonArray.add(0, localName);
                jsonArray.add(1, trueName);
            }
            for (HzPbomLineRecord record : pbomLineRecords) {
                JSONObject jsonObject = new JSONObject();
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                jsonObject.put("level", strings[0]);
                jsonObject.put("rank", strings[1]);
                jsonObject.put("pBomOfWhichDept", record.getpBomOfWhichDept());
                //需要截取 计算而得
                String lineId = record.getLineId();
                jsonObject.put("groupNum", record.getLineId());
                jsonObject.put("type", record.getType());
                jsonObject.put("puid", record.getpPuid());
                jsonObject.put("eBomPuid", record.getPuid() == null ? "" : record.getPuid());
                jsonObject.put("lineId", record.getLineId());
                jsonObject.put("resource", record.getResource() == null ? "/" : record.getResource());
                jsonObject.put("type", record.getType() == null ? "" : record.getType());
                jsonObject.put("buyUnit", record.getBuyUnit() == null ? "" : record.getBuyUnit());
                jsonObject.put("workShop1", record.getWorkShop1() == null ? "" : record.getWorkShop1());
                jsonObject.put("workShop2", record.getWorkShop2() == null ? "" : record.getWorkShop2());
                jsonObject.put("productLine", record.getProductLine() == null ? "" : record.getProductLine());
                jsonObject.put("mouldType", record.getMouldType() == null ? "" : record.getMouldType());
                jsonObject.put("outerPart", record.getOuterPart() == null ? "" : record.getOuterPart());
                jsonObject.put("colorPart", record.getColorPart() == null ? "" : record.getColorPart());


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
            }
            return jsonArray;

        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public JSONArray getPbomForProcessCompose(HzPbomProcessComposeReqDTO reqDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", reqDTO.getProjectId());
        map.put("lineId", reqDTO.getLineId());
        List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomRecord(map);
        if (ListUtil.isEmpty(records)) {
            return null;
        }
        try {
            HzPbomLineRecord record = records.get(0);
            List<HzPbomLineRecord> recordList = getChildLineRecord(reqDTO.getProjectId(), record, new ArrayList<>());
            JSONArray jsonArray = new JSONArray();
            for (HzPbomLineRecord lineRecord : recordList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("puid", lineRecord.getpPuid());
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
        List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomRecord(map);
        if (ListUtil.isEmpty(records)) {
            return null;
        }
        try {
            HzPbomLineRecord lineRecord = records.get(0);
            JSONArray jsonArray = new JSONArray();
            setting.setBomMainRecordPuid(lineRecord.getBomDigifaxId());
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
            jsonObject.put("puid", lineRecord.getpPuid());
            jsonObject.put("parentUid", lineRecord.getParentUid());
            jsonObject.put("hasChildren", lineRecord.getIsHas());
            Integer is2Y = lineRecord.getIs2Y();
            Integer hasChildren = lineRecord.getIsHas();
            String lineIndex = lineRecord.getLineIndex();
            String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
            jsonObject.put("level", strings[0]);
            jsonObject.put("rank", strings[1]);
            jsonObject.put("pBomOfWhichDept", lineRecord.getpBomOfWhichDept());
            jsonObject.put("groupNum", lineRecord.getLineId());
            jsonObject.put("eBomPuid", lineRecord.getPuid());
            jsonObject.put("lineId", lineRecord.getLineId());
            jsonObject.put("itemName", lineRecord.getpBomLinePartName());
            jsonObject.put("itemPart", lineRecord.getpBomLinePartClass());
            jsonObject.put("resource", lineRecord.getResource());
            jsonObject.put("type", lineRecord.getType());
            jsonObject.put("buyUnit", lineRecord.getBuyUnit());
            jsonObject.put("workShop1", lineRecord.getWorkShop1());
            jsonObject.put("workShop2", lineRecord.getWorkShop2());
            jsonObject.put("productLine", lineRecord.getProductLine());
            jsonObject.put("mouldType", lineRecord.getMouldType());
            jsonObject.put("outerPart", lineRecord.getOuterPart());
            jsonObject.put("colorPart", lineRecord.getColorPart());

            byte[] bomLineBlock = lineRecord.getBomLineBlock();
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
     *  当前代码的事务 貌似并没起作用 原因再找...
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int addPbomProcessCompose(final AddProcessComposeReqDTO reqDTO) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("puid", reqDTO.getPuid());
            map.put("projectId", reqDTO.getProjectPuid());
            HzBomLineRecord record = hzBomLineRecordDao.findBobLineByPuid(map);
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
//            hzBomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
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
    public Page<HzPbomLineRespDTO> getHzPbomRecordPage(FindForPageReqDTO reqDTO) {
        Page<HzPbomLineRecord> recordPage =hzPbomRecordDAO.getHzPbomRecordByPage(reqDTO);
        if(recordPage == null || recordPage.getResult() == null){
            return  null;
        }
        try {
            List<HzPbomLineRecord> records = recordPage.getResult();
            int num = (reqDTO.getPage()-1)*reqDTO.getLimit();
            List<HzPbomLineRespDTO> respDTOS = pbomLineRecordToRespDTOS(records,reqDTO.getProjectId(),num);
            return new Page<>(reqDTO.getPage(),reqDTO.getLimit(),recordPage.getTotalCount(),respDTOS);
        }catch (Exception e){
            return null;
        }
    }



    /**
     * 根据id 获取所有的子bom
     *
     * @return
     */
    public List<HzPbomLineRecord> getChildLineRecord(String projectId, HzPbomLineRecord record, List<HzPbomLineRecord> recordList) {
        Integer hasChildren = record.getIsHas();
        recordList.add(record);
        if (hasChildren.equals(1)) {
            Map<String, Object> map = new HashMap<>();
            map.put("projectId", projectId);
            map.put("parentUid", record.getpPuid());
            List<HzPbomLineRecord> records = hzPbomRecordDAO.getHzPbomById(map);
            if (ListUtil.isNotEmpty(records)) {
                for (HzPbomLineRecord lineRecord : records) {
                    getChildLineRecord(projectId, lineRecord, recordList);
                }
            }
        }

        return recordList;
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

    private List<HzMbomRecordRespDTO> pbomLineMaintailRecordToRespDTOS(List<HzMbomLineRecord> records) {
        List<HzMbomRecordRespDTO> respDTOS = new ArrayList<>();
        for (HzMbomLineRecord record : records) {
            HzMbomRecordRespDTO responseDTO = new HzMbomRecordRespDTO();
            //层级
            String lineIndex = record.getLineIndex();
            Integer is2Y = record.getIs2Y();
            Integer hasChildren = record.getIsHas();
            String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
            responseDTO.setLevel(strings[0]);
            responseDTO.setChange(record.getChange() == null ? "" : record.getChange());
            responseDTO.setWasterProduct(record.getWasterProduct() == null ? "" : record.getWasterProduct());
            responseDTO.setTools(record.getTools() == null ? "" : record.getTools());
            responseDTO.setChangeNum(record.getChangeNum() == null ? "" : record.getChangeNum());
            //这里需要转换一下，数据库存储毫秒值  暂时不知道页面显示为分钟还是小时 待定
            responseDTO.setLaborHour(record.getLaborHour() == null ? "" : record.getLaborHour());
            responseDTO.setMachineMaterial(record.getMachineMaterial() == null ? "" : record.getMachineMaterial());
            responseDTO.setLineId(record.getLineID() == null ? "" : record.getLineID());//零件号
            responseDTO.setEbomPuid(record.getpPuid());
            responseDTO.setStandardPart(record.getStandardPart() == null ? "" : record.getStandardPart());
            responseDTO.setSparePartNum(record.getSparePartNum() == null ? "" : record.getSparePartNum());
            responseDTO.setSolderJoint(record.getSolderJoint() == null ? "" : record.getSolderJoint());
            responseDTO.setRhythm(record.getRhythm() == null ? "" : record.getRhythm());
            responseDTO.setSparePart(record.getSparePart() == null ? "" : record.getSparePart());
            responseDTO.setpBomOfWhichDept(record.getpBomOfWhichDept() == null ? "" : record.getpBomOfWhichDept());
            responseDTO.setProcessRoute(record.getProcessRoute() == null ? "" : record.getProcessRoute());
            respDTOS.add(responseDTO);
        }
        return respDTOS;
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
                respDTO.setGroupNum(groupNum);//这个暂时没有

                byte[] bomLineBlock =record.getBomLineBlock();
                Object obj = SerializeUtil.unserialize(bomLineBlock);
                Object h9_IsCommon = null;
                Object H9_Mat_Status =null;
                if (obj instanceof LinkedHashMap) {
                    if (((LinkedHashMap) obj).size() > 0) {
                         h9_IsCommon =((LinkedHashMap) obj).get("h9_IsCommon");
                         H9_Mat_Status =((LinkedHashMap) obj).get("H9_Mat_Status");
                    }
                }
                respDTO.setH9_IsCommon(h9_IsCommon);
                respDTO.setH9_Mat_Status(H9_Mat_Status);
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
                respDTO.seteBomPuid(record.getpPuid() == null ? "" : record.getpPuid());
                respDTO.setProductLine(record.getProductLine() == null ? "" : record.getProductLine());
                respDTO.setWorkShop1(record.getWorkShop1() == null ? "" : record.getWorkShop1());
                respDTO.setWorkShop2(record.getWorkShop2() == null ? "" : record.getWorkShop2());
                respDTO.setMouldType(record.getMouldType() == null ? "" : record.getMouldType());
                respDTO.setOuterPart(record.getOuterPart() == null ? "" : record.getOuterPart());
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
