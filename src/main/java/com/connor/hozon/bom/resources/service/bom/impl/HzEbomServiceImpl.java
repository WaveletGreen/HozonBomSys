package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.bean.HzExFullCfgWithCfg;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgWithCfgDao;
import com.connor.hozon.bom.bomSystem.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0ModelService;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0OfBomLineService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.*;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomLevelRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzLouRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.*;
import com.connor.hozon.bom.resources.domain.query.*;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.executors.ExecutorServices;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzApplicantChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.sys.entity.User;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sql.pojo.bom.*;
import sql.pojo.cfg.fullCfg.HzCfg0OfBomLineRecord;
import sql.pojo.cfg.fullCfg.HzFullCfgMain;
import sql.pojo.cfg.model.HzCfg0ModelRecord;
import sql.pojo.change.HzApplicantChangeRecord;
import sql.pojo.change.HzAuditorChangeRecord;
import sql.pojo.change.HzChangeDataRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.redis.SerializeUtil;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by haozt on 2018/06/06
 */
@Service("HzEbomService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class HzEbomServiceImpl implements HzEbomService {

    private ExecutorService pool = Executors.newFixedThreadPool(5);

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzMbomService hzMbomService;

    @Autowired
    private HzCfg0OfBomLineService hzCfg0OfBomLineService;

    @Autowired
    private HzEPLManageRecordService hzEPLManageRecordService;

    /**
     * 特性关联2Y
     */
    @Autowired
    HzFullCfgWithCfgDao hzFullCfgWithCfgDao;
    /**
     * 全配置主配置
     */
    @Autowired
    HzFullCfgMainDao hzFullCfgMainDao;

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    @Autowired
    private HzCfg0ModelService hzCfg0ModelService;

    @Autowired
    private HzChangeDataRecordDAO hzChangeDataRecordDAO;

    @Autowired
    private HzApplicantChangeDAO hzApplicantChangeDAO;

    @Autowired
    private HzAuditorChangeDAO hzAuditorChangeDAO;

    @Override
    public Page<HzEbomRespDTO> getHzEbomPage(HzEbomByPageQuery query) {
        try {
            int num = (query.getPage() - 1) * query.getPageSize();
            HzEbomRespDTO recordRespDTO = new HzEbomRespDTO();
            JSONArray array = new JSONArray();
            List<HzEbomRespDTO> recordRespDTOList = new ArrayList<>();
            String level = query.getLevel().trim();
            if (level != null && level != "") {
                if (level.toUpperCase().endsWith("Y")) {
                    int length = Integer.valueOf(level.replace("Y", ""));
                    query.setIsHas(1);
                    query.setLineIndex(String.valueOf(length - 1));
                } else {
                    query.setIsHas(0);
                    int length = Integer.valueOf(level);
                    query.setLineIndex(String.valueOf(length));
                }
            }
            Page<HzEPLManageRecord> recordPage;
            if(Integer.valueOf(1).equals(query.getShowBomStructure())){
                //展示BOM结构树 当前查询树结构平铺
                recordPage = hzEbomRecordDAO.getHzEbomTreeByPage(query);
            }else {
                //展示全部平铺结构
                recordPage = hzEbomRecordDAO.getHzEbomPage(query);
            }
            if (recordPage == null || recordPage.getResult() == null || recordPage.getResult().size() == 0) {
                return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), 0);
            }

            List<HzEPLManageRecord> records = recordPage.getResult();
            List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(query.getProjectId());
            for (HzEPLManageRecord record : records) {
                JSONObject jsonObject = HzEbomRecordFactory.bomLineRecordTORespDTO(record);
                //获取分组号
                String fastener = record.getpFastener();
                String groupNum = record.getLineID();
                try {
                    if (StringUtil.isEmpty(fastener)||fastener.equals("/")) {
                        if (groupNum.contains("-")) {
                            groupNum = groupNum.split("-")[1].substring(0, 4);
                        } else {
                            groupNum = "";
                        }

                    } else {
                        String parentId = record.getParentUid();
                        groupNum = hzEPLManageRecordService.getGroupNum(query.getProjectId(), parentId);
                    }
                } catch (Exception e) {
                    groupNum = "";
                }
                jsonObject.put("No", ++num);
                jsonObject.put("groupNum",groupNum);
                //单车用量 mmp 怎么设计感觉都不合理
//                List<HzSingleVehicleDosage> singleVehicleDosages = hzSingleVehicleDosageDAO.findSingleVehicleByBomPuid(record.getPuid(),query.getProjectId());
//                if(ListUtil.isNotEmpty(singleVehicleDosages) && ListUtil.isNotEmpty(hzCfg0ModelRecords)){
//                    for(int i = 0;i<hzCfg0ModelRecords.size();i++){
//                       for(HzSingleVehicleDosage dosage :singleVehicleDosages){
//                           if(hzCfg0ModelRecords.get(i).getPuid().equals(dosage.getCfg0ModelPuid())){
//                               jsonObject.put("title"+i,dosage.getDosage());
//                               break;
//                           }
//                       }
//                    }
//                }

                if(null != record.getSingleVehDosage() && ListUtil.isNotEmpty(hzCfg0ModelRecords)){
                    jsonObject = hzSingleVehiclesServices.singleVehDosage(record.getSingleVehDosage(),hzCfg0ModelRecords,jsonObject);
                }
                array.add(jsonObject);
            }
            recordRespDTO.setJsonArray(array);
            recordRespDTOList.add(recordRespDTO);
            return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), recordPage.getTotalCount(), recordRespDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HzEbomRespDTO fingEbomById(String puid, String projectId) {
        try {
            HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid, projectId);
            HzEbomRespDTO respDTO = new HzEbomRespDTO();
            List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(projectId);

            if (record != null) {
                respDTO = HzEbomRecordFactory.eplRecordToEbomRespDTO(record);
                Map<String,Object> object = new HashMap<>();
                if(ListUtil.isNotEmpty(hzCfg0ModelRecords)){
                    if(null != record.getSingleVehDosage()){
                        byte[] singleVehDosage = record.getSingleVehDosage();
                        Object obj = SerializeUtil.unserialize(singleVehDosage);
                        if(obj instanceof Map){
                            Map<String,Object> map = (Map)obj;
                            if(map.size()>0){
                                for(int i = 0;i<hzCfg0ModelRecords.size();i++){
                                    boolean find = false;
                                    for(Map.Entry<String,Object> entry:map.entrySet()){
                                        if(hzCfg0ModelRecords.get(i).getPuid().equals(entry.getKey())){
                                            find = true;
                                            if(null !=entry.getValue() && ""!=entry.getValue()){
                                                object.put(hzCfg0ModelRecords.get(i).getObjectName(),entry.getValue());
                                                break;
                                            }else {
                                                object.put(hzCfg0ModelRecords.get(i).getObjectName(),"");
                                                break;
                                            }

//                                        jsonArray.add(new JSONObject().put("title"+i,entry.getValue()));
                                        }
                                    }
                                    if(!find){
                                        object.put(hzCfg0ModelRecords.get(i).getObjectName(),"");
                                    }
                                }
                            }
                        }
                    }else {
                        for(HzCfg0ModelRecord cfg0ModelRecord :hzCfg0ModelRecords){
                            object.put(cfg0ModelRecord.getObjectName(),"");
                        }
                    }
                }
                respDTO.setMap(object);
            }
            return respDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HzEbomLevelRespDTO fingEbomLevelById(String puid, String projectId) {
        try {
            HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid, projectId);
            HzEbomLevelRespDTO respDTO = new HzEbomLevelRespDTO();
            if (record != null) {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = HzEbomRecordFactory.bomLineRecordTORespDTO(record);
                jsonArray.add(jsonObject);
                respDTO.setJsonArray(jsonArray);
            }
            return respDTO;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int findIsHasByPuid(String puid, String projectId) {
        int list = hzEbomRecordDAO.findIsHasByPuid(puid, projectId);
        if (list == 0)
            return 0;
        else
            return 1;
    }

    /**
     * 添加EBOM  最好使用事务
     * @param reqDTO
     * @return
     */
    @Override
    public WriteResultRespDTO addHzEbomRecord(AddHzEbomReqDTO reqDTO) {
        WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
        try {
            String parentId = reqDTO.getPuid();//父层puid
            String lineNo = "";
            if (reqDTO.getLineNo() != null && reqDTO.getLineNo() != "") {
                lineNo = reqDTO.getLineNo().replaceFirst("^0*", "");
            }

            if (parentId != null && parentId != "") {
                //增加到当前父结构下面 并且需要同步当前数据到MBOM 和PBOM 以及物料数据中
                //如果当前结构在多个地方应用，则需要将应用的地方全部添加该零件，保持结构的完整性
                Map<String, Object> map = new HashMap<>();
                map.put("puid", parentId);
                map.put("projectId", reqDTO.getProjectId());
//                map.put("pPuid", parentId);
                HzBomLineRecord record = hzBomLineRecordDao.findBomLine(map);

                //找到他们的父，并找到他们父的零件号，继而找出所有使用父零件号的部位，保持结构的完整性
                String bomLineId = "";
                if (record != null) {
                    bomLineId = record.getLineID();
                } else {
                    writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    writeResultRespDTO.setErrMsg("当前插入对象的父结构不存在！");
                    return writeResultRespDTO;
                }

                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());

                ArrayList<String> puids = new ArrayList<>();
                if (bomLineId != "") {
                    Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put("lineID", bomLineId);
                    objectMap.put("lineId", bomLineId);
                    objectMap.put("projectId", reqDTO.getProjectId());
                    List<HzEPLManageRecord> list1 = hzEbomRecordDAO.findEbom(objectMap);
                    List<HzPbomLineRecord> list2 = hzPbomRecordDAO.getPbomById(objectMap);
//                    List<HzMbomLineRecord> list3 = hzMbomRecordDAO.findHzMbomByPuid(objectMap);
                    String puid = "";

                    if (ListUtil.isNotEmpty(list1)) {
                        for (HzEPLManageRecord hzEPLManageRecord : list1) {
                            HzBomLineRecord hzBomLineRecord = HzEbomRecordFactory.addEbomDTOBomLineRecord(reqDTO);//EBOM

                            puid = UUID.randomUUID().toString();
                            puids.add(puid);
                            hzBomLineRecord.setIsPart(1);
                            hzBomLineRecord.setIsHas(0);
                            hzBomLineRecord.setPuid(puid);
                            hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                            hzBomLineRecord.setIs2Y(0);
                            hzBomLineRecord.setIsDept(0);
                            hzBomLineRecord.setParentUid(hzEPLManageRecord.getPuid());
                            //获取当前对象的所有的子层   lineIndex值比较复杂 比较抽象 但是很关键
                            String lineIndex = hzEPLManageRecord.getLineIndex();
                            HzEbomTreeQuery query = new HzEbomTreeQuery();
                            query.setProjectId(reqDTO.getProjectId());
                            query.setPuid(hzEPLManageRecord.getPuid());

                            List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(query);
                            if (ListUtil.isEmpty(records)) {
                                writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                                writeResultRespDTO.setErrMsg("当前插入对象的父结构不存在！");
                                return writeResultRespDTO;
                            }
                            if (records.size() == 1) {//当前父没有子
                                StringBuffer stringBuffer = new StringBuffer(lineIndex);
                                stringBuffer = stringBuffer.append(".10");
                                hzBomLineRecord.setLineIndex(stringBuffer.toString());
                                //double d = Double.parseDouble(hzEPLManageRecord.getSortNum()) + 1;//100

                                //找到比listEbom.get(i).getSortNum()大的最小排序号
                                String dd = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), hzEPLManageRecord.getSortNum());
                                if ("".equals(dd) || null == dd) {
                                    //返回null值时，找一个listEbom.get(i).getSortNum()到listEbom.get(i).getSortNum()+1间的数据
                                    dd = Double.parseDouble(hzEPLManageRecord.getSortNum()) + 1 + "";
                                }
                                //double d = Double.parseDouble(dd);
                                String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), hzEPLManageRecord.getSortNum(), dd);

                                hzBomLineRecord.setSortNum(sortNum);
                            } else {//找出当前父下的所有子一层
                                int length = lineIndex.split("\\.").length + 1;
                                List<HzEPLManageRecord> l = new ArrayList<>();//
                                List<HzEPLManageRecord> ll = new ArrayList<>();//拿排序号
                                for (int k = 0; k < records.size(); k++) {
                                    int len = records.get(k).getLineIndex().split("\\.").length;
                                    if (length == len) {
                                        l.add(records.get(k));
                                    }
                                    if(k==records.size()-1)
                                        ll.add(records.get(k));
                                }
                                if (lineNo.equals("")) {//用户没有输入查找编号，默认添加到末尾位置
                                    double max = 0;
                                    HzEPLManageRecord lastRecord = new HzEPLManageRecord();
                                    HzEPLManageRecord lastRecord2 = new HzEPLManageRecord();//拿排序号
                                    for (HzEPLManageRecord manageRecord : l) {
                                        double d = Double.parseDouble(manageRecord.getSortNum());
                                        if (max < d) {
                                            max = d;
                                            lastRecord = manageRecord;
                                        }
                                    }

                                    lastRecord2 = ll.get(0);

                                    String index = lastRecord.getLineIndex();
                                    String lineIndexExceptLastNum = index.substring(0, index.lastIndexOf("."));
                                    int lastNum = Integer.valueOf(index.split("\\.")[index.split("\\.").length - 1]);
                                    hzBomLineRecord.setLineIndex(lineIndexExceptLastNum + "." + (lastNum + 10));
//                                    String s1 = String.valueOf(max);
//                                    String s2 = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), s1);
//                                    if (s2 == null) {
                                        /*String dd = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),max+"");
                                        if ("".equals(dd) || null == dd) {
                                            //返回null值时，找一个max到max+1间的数据
                                            dd = Double.parseDouble(max+"") + 1 + "";
                                        }*/
                                        String dd = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),lastRecord2.getSortNum());
                                        if ("".equals(dd) || null == dd) {
                                            //返回null值时，找一个max到max+1间的数据
                                            dd = Double.parseDouble(lastRecord2.getSortNum()) + 1 + "";
                                        }
                                        //double d = Double.parseDouble(dd);
                                        String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), lastRecord2.getSortNum(), dd);
                                        hzBomLineRecord.setSortNum(sortNum);//
                                        //hzBomLineRecord.setSortNum(String.valueOf(max + 1));//100
//                                    } else {
//                                        String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), s1, s2);
//                                        hzBomLineRecord.setSortNum(sortNum);
//                                    }
                                } else {
                                    int lineNum = Integer.valueOf(lineNo);
                                    boolean find = false;
                                    HzEPLManageRecord re = new HzEPLManageRecord();
                                    String lineIn = lineIndex + "." + lineNo;
                                    boolean b = hzEbomRecordDAO.lineIndexRepeat(reqDTO.getProjectId(), lineIn);
                                    if (b) {
                                        writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                                        writeResultRespDTO.setErrMsg("当前要插入的位置已存在数据！");
                                        return writeResultRespDTO;
                                    }
                                    double max = Double.parseDouble(l.get(l.size() - 1).getSortNum());
                                    for (int i = 0; i < l.size() - 1; i++) {
                                        String index = l.get(i).getLineIndex();
                                        String nextIndex = l.get(i + 1).getLineIndex();
                                        int lastNum = Integer.valueOf(index.split("\\.")[index.split("\\.").length - 1]);
                                        int nextLastNum = Integer.valueOf(nextIndex.split("\\.")[index.split("\\.").length - 1]);
                                        if (lineNum > lastNum && lineNum < nextLastNum) {
                                            find = true;
                                            re = l.get(i);
                                            break;
                                        }
                                    }

                                    if (find) {//找出当前合适的插入位置
                                        String index = re.getLineIndex();
                                        String lineIndexExceptLastNum = index.substring(0, index.lastIndexOf("."));
                                        String s1 = String.valueOf(re.getSortNum());
                                        String s2 = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), s1);
                                        if (s2 == null) {
                                            hzBomLineRecord.setSortNum(String.valueOf(max + 100));
                                        } else {
                                            String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), s1, s2);
                                            hzBomLineRecord.setSortNum(sortNum);
                                        }
                                        hzBomLineRecord.setLineIndex(lineIndexExceptLastNum + "." + (Integer.valueOf(lineNo)));
                                    } else {
                                        re = l.get(l.size() - 1);
                                        int lastNum = Integer.valueOf(re.getLineIndex().split("\\.")[re.getLineIndex().split("\\.").length - 1]);
                                        hzBomLineRecord.setLineIndex(hzEPLManageRecord.getLineIndex() + "." + lineNo);
                                        String s1 = "";
                                        if (lineNum > lastNum) {
                                            s1 = re.getSortNum();
                                        } else {
                                            s1 = hzEPLManageRecord.getSortNum();
                                        }
                                        String s2 = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), s1);
                                        if (s2 == null) {
                                            hzBomLineRecord.setSortNum(String.valueOf(max + 100));
                                        } else {
                                            String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), s1, s2);
                                            hzBomLineRecord.setSortNum(sortNum);
                                        }
                                    }
                                }
                            }

                            //判断子加父情况
                            String lineId = hzBomLineRecord.getLineID();
                            //先找到2Y层，在获取2Y的结构树，遍历结构树与新加零件的lineId相同则再判断其lineIndex是否是包含关系
                            HzBomLineRecord hzBomLineRecord2Y = findParentFor2Y(reqDTO.getProjectId(),parentId);
                            //hzBomLineRecord2Y
                            HzEbomTreeQuery queryE = new HzEbomTreeQuery();
                            queryE.setProjectId(reqDTO.getProjectId());
                            queryE.setPuid(hzBomLineRecord2Y.getPuid());
                            List<HzEPLManageRecord> ebomRecords = hzEbomRecordDAO.getHzBomLineChildren(queryE);
                            for(int i=0;i<ebomRecords.size();i++){
                                if(lineId.equals(ebomRecords.get(i).getLineID())){
                                    if(hzBomLineRecord.getLineIndex().contains(ebomRecords.get(i).getLineIndex())){
                                        writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                                        writeResultRespDTO.setErrMsg("当前要插入的零件已是直系父层，请查证后再添加！");
                                        return writeResultRespDTO;
                                    }
                                }
                            }

                            if (hzEPLManageRecord.getIsHas().equals(0) || hzEPLManageRecord.getIsPart().equals(1)) {
                                HzBomLineRecord hzBomLineRecord1 = new HzBomLineRecord();
                                hzBomLineRecord1.setIsHas(1);
                                hzBomLineRecord1.setIsPart(0);
                                hzBomLineRecord1.setPuid(hzEPLManageRecord.getPuid());
                                //更新数据
                                hzBomLineRecordDao.update(hzBomLineRecord1);
                            }

                            hzBomLineRecordDao.insert(hzBomLineRecord);
//                            hzBomLineRecord.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
//                            hzBomLineRecordDao.insert(hzBomLineRecord);
                        }
                    }

                    List<String> stringList = hzMbomService.loadingCarPartType();
                    if (stringList.contains(reqDTO.getpBomLinePartResource())) {
                        if (ListUtil.isNotEmpty(list2)) {
                            List<HzPbomLineRecord> pbomLineRecords = new ArrayList<>();
                            for (int i = 0; i < list2.size(); i++) {
                                HzPbomLineRecord hzPbomLineRecord = HzPbomRecordFactory.addHzEbomReqDTOPbomRecord(reqDTO);//PBOM
                                if (list2.get(i).getIsHas().equals(0) || list2.get(i).getIsPart().equals(1)) {
                                    list2.get(i).setIsHas(1);
                                    list2.get(i).setIsPart(0);
                                    //更新数据
                                    hzPbomRecordDAO.update(list2.get(i));
                                }
                                if (puids.size() >= list2.size()) {
                                    hzPbomLineRecord.seteBomPuid(puids.get(i));
                                } else {
                                    hzPbomLineRecord.seteBomPuid(puids.get(0));
                                }
                                hzPbomLineRecord.setParentUid(list2.get(i).geteBomPuid());
                                hzPbomLineRecord.setIs2Y(0);
                                hzPbomLineRecord.setIsDept(0);
                                hzPbomLineRecord.setIsHas(0);
                                hzPbomLineRecord.setIsPart(1);
                                hzPbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());

                                HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                                hzPbomTreeQuery.setPuid(list2.get(i).geteBomPuid());
                                hzPbomTreeQuery.setProjectId(reqDTO.getProjectId());
                                List<HzPbomLineRecord> records1 = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
                                String lineIndex1 = list2.get(i).getLineIndex();
                                if (ListUtil.isEmpty(records1)) {
                                    continue;
                                }

                                if (records1.size() == 1) {
                                    hzPbomLineRecord.setLineIndex(lineIndex1 + "." + 10);
                                    //double d = Double.parseDouble(records1.get(0).getSortNum()) + 100;
                                    //hzPbomLineRecord.setSortNum(String.valueOf(d));

                                    String dd = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(),records1.get(0).getSortNum());
                                    if ("".equals(dd) || null == dd) {
                                        //返回null值时，找一个max到max+1间的数据
                                        dd = Double.parseDouble(records1.get(0).getSortNum()) + 1 + "";
                                    }

                                    String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), records1.get(0).getSortNum(), dd);
                                    hzPbomLineRecord.setSortNum(sortNum);
                                } else {
                                    String d = "";//sort_num
                                    int length = lineIndex1.split("\\.").length + 1;
                                    List<HzPbomLineRecord> l = new ArrayList<>();
                                    for (int k = 0; k < records1.size(); k++) {
                                        int len = records1.get(k).getLineIndex().split("\\.").length;
                                        if (length == len) {
                                            l.add(records1.get(k));
                                            if (records1.get(k).getIsHas() != 0) {
                                                HzPbomTreeQuery query = new HzPbomTreeQuery();
                                                query.setProjectId(reqDTO.getProjectId());
                                                query.setPuid(records1.get(k).geteBomPuid());
                                                List<HzPbomLineRecord> parentRecors = hzPbomRecordDAO.getHzPbomTree(query);
                                                //l.add(parentRecors.get(parentRecors.size()-1));//层级的最后一个
                                                d = parentRecors.get(parentRecors.size()-1).getSortNum();
                                            }else {
                                                d = records1.get(k).getSortNum();
                                            }
                                        }
                                    }
                                    if (ListUtil.isEmpty(l)) {
                                        continue;
                                    }
                                    if (lineNo.equals("")) {//用户没有输入查找编号，默认添加到末尾位置
                                        double max = 0;
                                        HzPbomLineRecord lastRecord = new HzPbomLineRecord();
                                        for (HzPbomLineRecord manageRecord : l) {
                                            double sortNum = Double.parseDouble(manageRecord.getSortNum());
                                            if (max < sortNum) {
                                                max = sortNum;
                                                lastRecord = manageRecord;
                                            }
                                        }
                                        String index = lastRecord.getLineIndex();
                                        String lineIndexExceptLastNum = index.substring(0, index.lastIndexOf("."));
                                        int lastNum = Integer.valueOf(index.split("\\.")[index.split("\\.").length - 1]);
                                        hzPbomLineRecord.setLineIndex(lineIndexExceptLastNum + "." + (lastNum + 10));

                                        //String s1 = String.valueOf(max);
                                        String s1 = d;
                                        String s2 = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), s1);
                                        if (s2 == null) {
                                            hzPbomLineRecord.setSortNum(String.valueOf((Double.parseDouble(d) + 1)));
                                        } else {
                                            String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), s1, s2);
                                            hzPbomLineRecord.setSortNum(sortNum);
                                        }
                                    } else {
                                        int lineNum = Integer.valueOf(lineNo);
                                        boolean find = false;
                                        HzPbomLineRecord re = new HzPbomLineRecord();
                                        double max = Double.parseDouble(l.get(l.size() - 1).getSortNum());
                                        for (int j = 0; j < l.size() - 1; j++) {
                                            String index = l.get(j).getLineIndex();
                                            String nextIndex = l.get(j + 1).getLineIndex();
                                            int lastNum = Integer.valueOf(index.split("\\.")[index.split("\\.").length - 1]);
                                            int nextLastNum = Integer.valueOf(nextIndex.split("\\.")[index.split("\\.").length - 1]);
                                            if (lineNum > lastNum && lineNum < nextLastNum) {
                                                find = true;
                                                re = l.get(j);
                                                break;
                                            }
                                        }

                                        if (find) {//找出当前合适的插入位置
                                            String index = re.getLineIndex();
                                            String lineIndexExceptLast = index.substring(0, index.lastIndexOf("."));
                                            String s1 = String.valueOf(re.getSortNum());
                                            String s2 = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), s1);
                                            if (s2 == null) {
                                                hzPbomLineRecord.setSortNum(String.valueOf(max + 100));
                                            } else {
                                                String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), s1, s2);
                                                hzPbomLineRecord.setSortNum(sortNum);
                                            }
                                            hzPbomLineRecord.setLineIndex(lineIndexExceptLast + "." + (Integer.valueOf(lineNo)));
                                        } else {
                                            re = l.get(l.size() - 1);
                                            int lastNum = Integer.valueOf(re.getLineIndex().split("\\.")[re.getLineIndex().split("\\.").length - 1]);
                                            hzPbomLineRecord.setLineIndex(lineIndex1 + "." + lineNo);
                                            String s1 = "";
                                            if (lineNum > lastNum) {
                                                s1 = re.getSortNum();
                                            } else {
                                                s1 = list2.get(i).getSortNum();
                                            }
                                            String s2 = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), s1);
                                            if (s2 == null) {
                                                hzPbomLineRecord.setSortNum(String.valueOf(max + 100));
                                            } else {
                                                String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), s1, s2);
                                                hzPbomLineRecord.setSortNum(sortNum);
                                            }
                                        }
                                    }
                                }
                                pbomLineRecords.add(hzPbomLineRecord);
                            }
                            hzPbomRecordDAO.insertList(pbomLineRecords);
                        }

                    }
                }
                return WriteResultRespDTO.getSuccessResult();

            } else {
                //自己搭建父结构 默认为2Y层
                boolean isRepeat = hzEbomRecordDAO.checkItemIdIsRepeat(reqDTO.getProjectId(), reqDTO.getLineId());
                if (isRepeat) {
                    WriteResultRespDTO respDTO = new WriteResultRespDTO();
                    respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    respDTO.setErrMsg("当前零件号已存在,请重新添加！");
                    return respDTO;
                }
                //项目
                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
                if (hzBomMainRecord == null) {
                    return WriteResultRespDTO.getFailResult();
                }
                HzBomLineRecord hzBomLineRecord = HzEbomRecordFactory.addEbomDTOBomLineRecord(reqDTO);//EBOM
                hzBomLineRecord.setIsPart(0);
                hzBomLineRecord.setIsHas(0);
                //找出全部的2y信息，新增的EBOM的层级 按2y层的最大值开始增
                Integer maxFirstLineIndex = hzBomLineRecordDao.getMaxLineIndexFirstNum(reqDTO.getProjectId());
                if (maxFirstLineIndex == null) {
                    hzBomLineRecord.setLineIndex("10.10");
                } else {
                    maxFirstLineIndex += 10;
                    hzBomLineRecord.setLineIndex(maxFirstLineIndex + ".10");
                }
                Double maxGroupNum = hzBomLineRecordDao.findMaxBomOrderNum(reqDTO.getProjectId());
                if (maxGroupNum == null) {
                    maxGroupNum = 100.0;
                    hzBomLineRecord.setSortNum(String.valueOf(maxGroupNum));
                } else {
                    maxGroupNum += 100;
                    int m = maxGroupNum.intValue();
                    hzBomLineRecord.setSortNum(String.valueOf(m));
                }
                String puid = UUID.randomUUID().toString();
                hzBomLineRecord.setPuid(puid);
                hzBomLineRecord.setIsDept(1);
                hzBomLineRecord.setIs2Y(1);
                hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                hzBomLineRecordDao.insert(hzBomLineRecord);

//                hzBomLineRecord.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
//                hzBomLineRecordDao.insert(hzBomLineRecord);

                List<String> stringList = hzMbomService.loadingCarPartType();
                if (stringList.contains(reqDTO.getpBomLinePartResource())) {
                    HzPbomLineRecord hzPbomLineRecord = HzPbomRecordFactory.addHzEbomReqDTOPbomRecord(reqDTO);//PBOM
                    Double num = hzPbomRecordDAO.getHzPbomMaxOrderNum(reqDTO.getProjectId());
                    if (num == null) {
                        num = 100.0;
                        hzPbomLineRecord.setSortNum(String.valueOf(num));
                    } else {
                        num += 100;
                        int m = num.intValue();
                        hzPbomLineRecord.setSortNum(String.valueOf(m));
                    }
                    hzPbomLineRecord.seteBomPuid(puid);
                    hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
                    hzPbomLineRecord.setIs2Y(1);
                    hzPbomLineRecord.setIsDept(1);
                    hzPbomLineRecord.setIsHas(0);
                    hzPbomLineRecord.setIsPart(0);
                    Integer maxLineIndexFirstNum = hzPbomRecordDAO.getMaxLineIndexFirstNum(reqDTO.getProjectId());
                    if (maxLineIndexFirstNum == null) {
                        hzPbomLineRecord.setLineIndex("10.10");
                    } else {
                        maxLineIndexFirstNum += 10;
                        hzPbomLineRecord.setLineIndex(maxLineIndexFirstNum + ".10");
                    }
                    hzPbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                    List<HzPbomLineRecord> pbomLineRecords = new ArrayList<>();
                    pbomLineRecords.add(hzPbomLineRecord);
                    hzPbomRecordDAO.insertList(pbomLineRecords);
                }
                return WriteResultRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }


    @Override
    public WriteResultRespDTO updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO) {
        try {
            /**
             * 业务涉及到变更 需要走流程进行审核，走流程时需要查看变更前和变更后的数据，所以需要记录变更前的数据；
             * 这里每次走更新数据的时候先将原来未更新的数据保存一份，更新后的数据也保存一份，类似于查看变更历史记录的
             * 功能。
             */
            if(!PrivilegeUtil.writePrivilege()){
                return WriteResultRespDTO.getFailPrivilege();
            }
            if(StringUtil.isEmpty(reqDTO.getProjectId())){
                return WriteResultRespDTO.IllgalArgument();
            }
            boolean isRepeat = hzEbomRecordDAO.checkItemIdIsRepeat(reqDTO.getProjectId(), reqDTO.getLineId());
            if (isRepeat) {
                HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(reqDTO.getPuid(), reqDTO.getProjectId());
                if (!record.getLineID().equals(reqDTO.getLineId())) {
                    WriteResultRespDTO respDTO = new WriteResultRespDTO();
                    respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    respDTO.setErrMsg("非法操作,要修改的零件号已存在！");
                    return respDTO;
                }
            }
            Map<String, Object> bomLineMap = new HashMap<>();
            bomLineMap.put("puid", reqDTO.getPuid());
            bomLineMap.put("projectId", reqDTO.getProjectId());
            HzBomLineRecord bomLineRecord = hzBomLineRecordDao.findBomLine(bomLineMap);//未修改前的数据
            String lineId = "";
            if (bomLineRecord != null) {
                lineId = bomLineRecord.getLineID();
                }

            HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
            HzBomLineRecord hzBomLineRecord = HzEbomRecordFactory.updateHzEbomDTOLineRecord(reqDTO);
            Map<String,Object> m = reqDTO.getUpdateDosage();
            List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(reqDTO.getProjectId());
            byte [] bytes = null;
            if(ListUtil.isNotEmpty(hzCfg0ModelRecords)){
                Map<String,Object> map = new HashMap<>();
                for(HzCfg0ModelRecord record:hzCfg0ModelRecords){
                    if(null != m.get(record.getObjectName())){
                        map.put(record.getPuid(),m.get(record.getObjectName()));
                    }
                }
                bytes = SerializeUtil.serialize(map);
                hzBomLineRecord.setSingleVehDosage(bytes);
            }
            hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());

//            hzBomLineRecord.setTableName(null);
//                hzBomLineRecordDao.update(hzBomLineRecord);
            Map<String, Object> map = new HashMap<>();
            map.put("projectId", reqDTO.getProjectId());
            map.put("lineID", lineId);
            map.put("lineId", lineId);
            List<HzEPLManageRecord> hzEPLManageRecords = hzEbomRecordDAO.findEbom(map);

            //pbom  要同步更新数据  MBOM 和物料中不再做同步数据更新
            List<HzPbomLineRecord> hzPbomLineRecords = hzPbomRecordDAO.getPbomById(map);

            List<String> type = hzMbomService.loadingCarPartType();
            if (type.contains(reqDTO.getpBomLinePartResource())) {
                HzEPLManageRecord record = new HzEPLManageRecord();
                    for (HzEPLManageRecord hzEPLManageRecord : hzEPLManageRecords) {
                        if (hzEPLManageRecord.getPuid().equals(reqDTO.getPuid())) {
                            record = hzEPLManageRecord;
                        }
                        break;
                    }

                HzPbomLineRecord hzPbomLineRecord = HzPbomRecordFactory.updateHzEbomReqDTOPbomRecord(reqDTO);
                hzPbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                hzPbomLineRecord.setSingleVehDosage(bytes);
                if (ListUtil.isNotEmpty(hzPbomLineRecords)) {
                    hzPbomLineRecords.forEach(re -> {
                        hzPbomLineRecord.seteBomPuid(re.geteBomPuid());
                        hzPbomRecordDAO.update(hzPbomLineRecord);
                    });
                } else {
                    if(record!=null){

                        if(StringUtil.isEmpty(record.getParentUid())){//2Y层
                            List<HzPbomLineRecord> records = new ArrayList<>();
                            hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
                            hzPbomLineRecord.setSortNum(record.getSortNum());
                            hzPbomLineRecord.setLineIndex(record.getLineIndex());
                            hzPbomLineRecord.setIs2Y(record.getIs2Y());
                            hzPbomLineRecord.setIsPart(record.getIsPart());
                            hzPbomLineRecord.setIsHas(record.getIsHas());
                            hzPbomLineRecord.setParentUid(record.getParentUid());
                            hzPbomLineRecord.setIsDept(record.getIsDept());
                            hzPbomLineRecord.seteBomPuid(record.getPuid());
                            hzPbomLineRecord.setLinePuid(record.getLinePuid());
                            hzPbomLineRecord.setSingleVehDosage(bytes);
                            records.add(hzPbomLineRecord);
                            hzPbomRecordDAO.insertList(records);
                        }else {
                            //查询其父看有没有子
                            Map<String,Object> map1 = new HashMap<>();
                            map1.put("projectId",reqDTO.getProjectId());
                            map1.put("eBomPuid",record.getParentUid());
                            List<HzPbomLineRecord> recordList = hzPbomRecordDAO.findPbom(map1);
                            if(ListUtil.isNotEmpty(recordList)){
                                if(Integer.valueOf(0).equals(recordList.get(0).getIsHas())){
                                    recordList.get(0).setIsHas(1);
                                    hzPbomRecordDAO.update(recordList.get(0));
                                }
                                List<HzPbomLineRecord> records = new ArrayList<>();
                                hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
                                hzPbomLineRecord.setSortNum(record.getSortNum());
                                hzPbomLineRecord.setLineIndex(record.getLineIndex());
                                hzPbomLineRecord.setIs2Y(record.getIs2Y());
                                hzPbomLineRecord.setIsPart(record.getIsPart());
                                hzPbomLineRecord.setIsHas(0);
                                hzPbomLineRecord.setParentUid(record.getParentUid());
                                hzPbomLineRecord.setIsDept(record.getIsDept());
                                hzPbomLineRecord.seteBomPuid(record.getPuid());
                                hzPbomLineRecord.setLinePuid(record.getLinePuid());
                                hzPbomLineRecord.setSingleVehDosage(bytes);
                                records.add(hzPbomLineRecord);
                                hzPbomRecordDAO.insertList(records);
                            }
                        }
                    }

                }

            }
            else {

                if (ListUtil.isNotEmpty(hzPbomLineRecords)) {
                    hzPbomLineRecords.forEach(hzPbomLineRecord -> {
                        hzPbomRecordDAO.delete(hzPbomLineRecord.geteBomPuid());
                    });
                }
            }

            hzEPLManageRecords.forEach(record -> {
                hzBomLineRecord.setPuid(record.getPuid());
                hzBomLineRecordDao.update(hzBomLineRecord);
            });

            return WriteResultRespDTO.getSuccessResult();

        } catch (Exception e) {
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public WriteResultRespDTO testbomLevelChange(UpdateHzEbomLeveReqDTO reqDTO) {
        WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
        // 调层级关系
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("lineID", reqDTO.getLineId());//页面传回来的零件ID（新父）
        objectMap.put("projectId", reqDTO.getProjectId());
        List<HzEPLManageRecord> listEbom = hzEbomRecordDAO.findEbom(objectMap);//新父

        Map<String, Object> objectMap2 = new HashMap<>();
        objectMap2.put("lineId", reqDTO.getLineId());//页面传回来的零件ID
        objectMap2.put("projectId", reqDTO.getProjectId());
        List<HzPbomLineRecord> listPbom = hzPbomRecordDAO.getPbomById(objectMap2);//新父

        if (ListUtil.isEmpty(listEbom)) {
            writeResultRespDTO.setErrMsg("所填父零件号不存在！");
            writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
            return writeResultRespDTO;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("puid", reqDTO.getPuid());
        map.put("projectId", reqDTO.getProjectId());
        List<HzEPLManageRecord> selectedListEbom = hzEbomRecordDAO.findEbom(map);//1--选中行信息

        List<HzPbomLineRecord> selectedListPbom = new ArrayList<>();//选中行信息
        if (ListUtil.isNotEmpty(listPbom)) {
            Map<String, Object> map2 = new HashMap<>();
            map2.put("pPuid", reqDTO.getPuid());//勾选行--P_E_BOM_PUID
            map2.put("projectId", reqDTO.getProjectId());
            selectedListPbom = hzPbomRecordDAO.getPbomById(map2);
        }

        //根据新父层零件ID找父层信息(puid)
        List<HzEPLManageRecord> parent = hzEbomRecordDAO.findEbomByItemId(reqDTO.getLineId(), reqDTO.getProjectId());
        Map<String, Object> map1 = new HashMap<>();
        map1.put("puid", parent.get(0).getPuid());
        map1.put("projectId", reqDTO.getProjectId());
        List<HzEPLManageRecord> newList = hzEbomRecordDAO.findEbom(map1);//1

        String lineNo = "";
        if (reqDTO.getLineNo() != null && reqDTO.getLineNo() != "") {
            //界面传回来的查找编号
            lineNo = reqDTO.getLineNo().replaceFirst("^0*", "");
        }
        String lineIndex = newList.get(0).getLineIndex();//新父的lineIndex
        String lineIn = lineIndex + "." + lineNo;
        boolean b = hzEbomRecordDAO.lineIndexRepeat(reqDTO.getProjectId(), lineIn);
        if (b) {
            writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
            writeResultRespDTO.setErrMsg("当前要插入的位置已存在数据！");
            return writeResultRespDTO;
        }

        //查选中零件的树结构ebomRecords
        List<HzEPLManageRecord> ebomRecords = new ArrayList<>();
        if (ListUtil.isNotEmpty(selectedListEbom)) {
            HzEbomTreeQuery queryE = new HzEbomTreeQuery();
            queryE.setProjectId(reqDTO.getProjectId());
            queryE.setPuid(selectedListEbom.get(0).getPuid());
            ebomRecords = hzEbomRecordDAO.getHzBomLineChildren(queryE);
        }
        //查选中零件的树结构pbomRecords
        List<HzPbomLineRecord> pbomRecords = new ArrayList<>();
        if (ListUtil.isNotEmpty(selectedListPbom)) {
            HzPbomTreeQuery queryP = new HzPbomTreeQuery();
            queryP.setProjectId(reqDTO.getProjectId());
            queryP.setPuid(selectedListPbom.get(0).geteBomPuid());
            pbomRecords = hzPbomRecordDAO.getHzPbomTree(queryP);
        }

        //自己层级下挂自己情况
        for (int i = 0; i < ebomRecords.size(); i++) {//选中零件树结构
            //reqDTO.getLineId()--listEbom.get(0).getLineID()
            if (reqDTO.getLineId().equals(ebomRecords.get(i).getLineID())) {
                writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                writeResultRespDTO.setErrMsg("当前要插入的位置与勾选的结构冲突，请查证后再调整！");
                return writeResultRespDTO;
            }
        }

        //（零件来源）记录Pbom中P_E_BOM_PUID及记录Mbom中P_BOM_PUID(EBOM中新增的零件的PUID)
        List<String> bom_Puid = new ArrayList<>();

        //EBOM
        if (ListUtil.isNotEmpty(listEbom)) {
            for (int i = 0; i < listEbom.size(); i++) {
                //新父下增加选中层级结构
                if (ListUtil.isNotEmpty(selectedListEbom)) {
                    StringBuffer sb = new StringBuffer();//拼接lineIndex
                    String line_index = ebomRecords.get(0).getLineIndex();//先保存勾选行的p_line_index
                    String d = "";//排序号
                    String parentPuid = "";//不同层级
                    String parentPuid2 = "";//同层级
                    int num = 0;
                    for (int j = 0; j < ebomRecords.size(); j++) {
                        //String s2 = reqDTO.getLineNo().replaceFirst("^0*", "");//界面返回的查找编号
                        //修改p_line_index、sort_num属性
                        List<String> carParts = hzMbomService.loadingCarPartType();
                        HzEPLManageRecord newRecord = new HzEPLManageRecord();
                        if (j == 0) {//勾选的父层零件
                            //新父的p_line_index
                            String s1 = listEbom.get(i).getLineIndex();
                            sb.append(s1).append(".").append(lineNo);
                            if (listEbom.get(i).getIsHas() == 0) {
                                //找到比listEbom.get(i).getSortNum()大的最小排序号
                                String dd = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), listEbom.get(i).getSortNum());
                                if ("".equals(dd) || null == dd) {
                                    //返回null值时，找一个listEbom.get(i).getSortNum()到listEbom.get(i).getSortNum()+1间的数据
                                    dd = Double.parseDouble(listEbom.get(i).getSortNum()) + 1 + "";
                                }
                                d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), listEbom.get(i).getSortNum(), dd);
                            } else {
                                //比较查找编号
                                HzEbomTreeQuery query = new HzEbomTreeQuery();
                                query.setProjectId(reqDTO.getProjectId());
                                query.setPuid(listEbom.get(i).getPuid());//要插入的层级结构
                                List<HzEPLManageRecord> parentRecors = hzEbomRecordDAO.getHzBomLineChildren(query);

                                for (int k = 1; k < parentRecors.size(); k++) {//从第一个子开始
                                    int lever1 = parentRecors.get(k).getLineIndex().split("\\.").length;//层级的深度
                                    String str = parentRecors.get(k).getLineIndex();
                                    int begin = str.lastIndexOf(".");
                                    int end = str.length();
                                    String result = str.substring(begin + 1, end);//获取层级中的最后一层进行跟查找编号对比
                                    if (Double.parseDouble(result) > Double.parseDouble(lineNo)) {
                                        //插在第一个子层的位子
                                        d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), parentRecors.get(k - 1).getSortNum(), parentRecors.get(k).getSortNum());
                                        num = k;//
                                        break;
                                    } else if (Double.parseDouble(result) < Double.parseDouble(lineNo)) {
                                        //寻找插入的合适位置
                                        if (k + 1 < parentRecors.size()) {
                                            int lever2 = parentRecors.get(k + 1).getLineIndex().split("\\.").length;
                                            //用层级长度判断是否是同一层级
                                            if (lever2 > lever1) {//不是同一层级，获取第一个子层的结构树
                                                //获取当前零件的结构树
                                                HzEbomTreeQuery isQuery = new HzEbomTreeQuery();
                                                isQuery.setProjectId(reqDTO.getProjectId());
                                                isQuery.setPuid(parentRecors.get(k).getPuid());//
                                                List<HzEPLManageRecord> isParentRecors = hzEbomRecordDAO.getHzBomLineChildren(isQuery);
                                                k = k + isParentRecors.size() - 1;//直接跳到与第一个子层同一层级的下一个子层
                                            }
                                        }
                                        if (k == parentRecors.size() - 1) {
                                            //插入到最后一个
                                            //String dd = Double.parseDouble(parentRecors.get(k).getSortNum()) + 1 + "";
                                            String dd = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), parentRecors.get(k).getSortNum());
                                            if ("".equals(dd) || null == dd) {
                                                dd = Double.parseDouble(parentRecors.get(k).getSortNum()) + 1 + "";
                                            }
                                            d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), parentRecors.get(k).getSortNum(), dd);
                                            num = k;
                                        }
                                    }
                                }
                            }
                            //puid重新生成
                            parentPuid = UUID.randomUUID().toString();
                            newRecord.setPuid(parentPuid);
                            if(carParts.contains(ebomRecords.get(j).getpBomLinePartResource())){
                                bom_Puid.add(parentPuid);
                            }

                            newRecord.setParentUid(listEbom.get(i).getPuid());//勾选的第一次变，其子层不变
                            newRecord.setLineIndex(sb.toString());
                            newRecord.setSortNum(d);
                            newRecord.setIs2Y(0);
                        } else {
                            //子层
                            String index = ebomRecords.get(j).getLineIndex().replaceFirst(line_index, sb.toString());
                            if (listEbom.get(i).getIsHas() == 0) {
                                //获取比上一个排序号大的最小排序号
                                String dd = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), d);
                                if ("".equals(dd) || null == dd) {
                                    dd = Double.parseDouble(d) + 1 + "";
                                }
                                d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), d, dd);
                            } else {
                                //比较查找编号
                                if (num > 0) {
                                    String dd = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), d);
                                    if ("".equals(dd) || null == dd) {
                                        dd = Double.parseDouble(d) + 1 + "";
                                    }
                                    d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), d, dd);
                                }
                            }
                            //puid重新生成
                            String temp = UUID.randomUUID().toString();
                            newRecord.setPuid(temp);
                            if(carParts.contains(ebomRecords.get(j).getpBomLinePartResource())){
                                bom_Puid.add(temp);
                            }
                            //判断是否统一层级
                            if (ebomRecords.get(j).getLineIndex().split("\\.").length > ebomRecords.get(j - 1).getLineIndex().split("\\.").length) {
                                newRecord.setParentUid(parentPuid);//不同层级赋值
                                parentPuid2 = parentPuid;//同层级
                            } else{
                                newRecord.setParentUid(parentPuid2);//同层级赋值
                            }
                            parentPuid = temp;//不同层级
                            newRecord.setLineIndex(index);
                            newRecord.setSortNum(d);
                            newRecord.setIs2Y(0);
                        }
                        //代码重复部分
                        newRecord.setIsDept(ebomRecords.get(j).getIsDept());
                        newRecord.setBomDigifaxId(ebomRecords.get(j).getBomDigifaxId());
                        newRecord.setLinePuid(ebomRecords.get(j).getLinePuid());
                        newRecord.setLineID(ebomRecords.get(j).getLineID());
                        newRecord.setIsHas(ebomRecords.get(j).getIsHas());
                        newRecord.setIsPart(ebomRecords.get(j).getIsPart());
                        newRecord.setpBomOfWhichDept(ebomRecords.get(j).getpBomOfWhichDept());
                        newRecord.setpBomLinePartName(ebomRecords.get(j).getpBomLinePartName());
                        newRecord.setpBomLinePartClass(ebomRecords.get(j).getpBomLinePartClass());
                        newRecord.setStatus(ebomRecords.get(j).getStatus());
                        newRecord.setItemResource(ebomRecords.get(j).getItemResource());
                        newRecord.setResource(ebomRecords.get(j).getResource());
                        newRecord.setType(ebomRecords.get(j).getType());
                        newRecord.setBuyUnit(ebomRecords.get(j).getBuyUnit());
                        newRecord.setWorkShop1(ebomRecords.get(j).getWorkShop1());
                        newRecord.setWorkShop2(ebomRecords.get(j).getWorkShop2());
                        newRecord.setProductLine(ebomRecords.get(j).getProductLine());
                        newRecord.setMouldType(ebomRecords.get(j).getMouldType());
                        newRecord.setOuterPart(ebomRecords.get(j).getOuterPart());
                        newRecord.setColorPart(ebomRecords.get(j).getColorPart());
                        newRecord.setSparePart(ebomRecords.get(j).getSparePart());
                        newRecord.setSparePartNum(ebomRecords.get(j).getSparePartNum());
                        newRecord.setProcessRoute(ebomRecords.get(j).getProcessRoute());
                        newRecord.setLaborHour(ebomRecords.get(j).getLaborHour());
                        newRecord.setRhythm(ebomRecords.get(j).getRhythm());
                        newRecord.setSolderJoint(ebomRecords.get(j).getSolderJoint());
                        newRecord.setMachineMaterial(ebomRecords.get(j).getMachineMaterial());
                        newRecord.setStandardPart(ebomRecords.get(j).getStandardPart());
                        newRecord.setTools(ebomRecords.get(j).getTools());
                        newRecord.setWasterProduct(ebomRecords.get(j).getWasterProduct());
                        newRecord.setChange(ebomRecords.get(j).getChange());
                        newRecord.setChangeNum(ebomRecords.get(j).getChangeNum());
                        newRecord.setFna(ebomRecords.get(j).getFna());
                        newRecord.setpBomLinePartEnName(ebomRecords.get(j).getpBomLinePartEnName());
                        newRecord.setpBomLinePartResource(ebomRecords.get(j).getpBomLinePartResource());
                        newRecord.setpCreateTime(ebomRecords.get(j).getpCreateTime());
                        newRecord.setpUpdateTime(ebomRecords.get(j).getpUpdateTime());
                        newRecord.setpFastener(ebomRecords.get(j).getpFastener());
                        newRecord.setP3cpartFlag(ebomRecords.get(j).getP3cpartFlag());
                        newRecord.setpInOutSideFlag(ebomRecords.get(j).getpInOutSideFlag());
                        newRecord.setpUpc(ebomRecords.get(j).getpUpc());
                        newRecord.setpFnaDesc(ebomRecords.get(j).getpFnaDesc());
                        newRecord.setpCreateName(ebomRecords.get(j).getpCreateName());
                        newRecord.setpUpdateName(ebomRecords.get(j).getpUpdateName());
                        newRecord.setpUnit(ebomRecords.get(j).getpUnit());
                        newRecord.setpPictureNo(ebomRecords.get(j).getpPictureNo());
                        newRecord.setpPictureSheet(ebomRecords.get(j).getpPictureSheet());
                        newRecord.setpMaterialHigh(ebomRecords.get(j).getpMaterialHigh());
                        newRecord.setpMaterial1(ebomRecords.get(j).getpMaterial1());
                        newRecord.setpMaterial2(ebomRecords.get(j).getpMaterial2());
                        newRecord.setpMaterial3(ebomRecords.get(j).getpMaterial3());
                        newRecord.setpDensity(ebomRecords.get(j).getpDensity());
                        newRecord.setpMaterialStandard(ebomRecords.get(j).getpMaterialStandard());
                        newRecord.setpSurfaceTreat(ebomRecords.get(j).getpSurfaceTreat());
                        newRecord.setpTextureColorNum(ebomRecords.get(j).getpTextureColorNum());
                        newRecord.setpManuProcess(ebomRecords.get(j).getpManuProcess());
                        newRecord.setpSymmetry(ebomRecords.get(j).getpSymmetry());
                        newRecord.setpImportance(ebomRecords.get(j).getpImportance());
                        newRecord.setpRegulationFlag(ebomRecords.get(j).getpRegulationFlag());
                        newRecord.setpBwgBoxPart(ebomRecords.get(j).getpBwgBoxPart());
                        newRecord.setpDevelopType(ebomRecords.get(j).getpDevelopType());
                        newRecord.setpDataVersion(ebomRecords.get(j).getpDataVersion());
                        newRecord.setpTargetWeight(ebomRecords.get(j).getpTargetWeight());
                        newRecord.setpFeatureWeight(ebomRecords.get(j).getpFeatureWeight());
                        newRecord.setpActualWeight(ebomRecords.get(j).getpActualWeight());
                        newRecord.setpFastenerStandard(ebomRecords.get(j).getpFastenerStandard());
                        newRecord.setpFastenerLevel(ebomRecords.get(j).getpFastenerLevel());
                        newRecord.setpTorque(ebomRecords.get(j).getpTorque());
                        newRecord.setpDutyEngineer(ebomRecords.get(j).getpDutyEngineer());
                        newRecord.setpSupply(ebomRecords.get(j).getpSupply());
                        newRecord.setpSupplyCode(ebomRecords.get(j).getpSupplyCode());
                        newRecord.setpRemark(ebomRecords.get(j).getpRemark());
                        newRecord.setpRegulationCode(ebomRecords.get(j).getpRegulationCode());
                        newRecord.setNumber(ebomRecords.get(j).getNumber());
                        newRecord.setpBuyEngineer(ebomRecords.get(j).getpBuyEngineer());
                        newRecord.setTableName(ebomRecords.get(j).getTableName());
                        newRecord.setpLouaFlag(ebomRecords.get(j).getpLouaFlag());
                        hzEbomRecordDAO.insert2(newRecord);
                    }
                }
                //修改新父isHas属性,判断新父是否带子层，如果新父之前不带子层，则新父的层级变成对应的Y级，反之不变
                if (listEbom.get(i).getIsHas() == 0) {
                    HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                    hzBomLineRecord.setIsHas(1);
                    hzBomLineRecord.setPuid(listEbom.get(i).getPuid());
                    hzBomLineRecordDao.update(hzBomLineRecord);
                }
                //修改新父isPart属性,判断新父是part，如果新父之前是，则新父的层级变成不是，反之不变
                if (listEbom.get(i).getIsPart() == 1) {
                    HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                    hzBomLineRecord.setIsPart(0);
                    hzBomLineRecord.setPuid(listEbom.get(i).getPuid());
                    hzBomLineRecordDao.update(hzBomLineRecord);
                }
            }
        }

        //PBOM
        int pbomPuid_index = 0;
        String pbomParentuid = "";//不同层级
        String pbomParentuid2 = "";//同层级
        String pbomParentuid3 = "";//合件--不同层级
        String pbomParentuid4 = "";//合件--同层级
        if (ListUtil.isNotEmpty(selectedListPbom)) {
            for (int i = 0; i < listPbom.size(); i++) {
                //新父下增加选中层级结构
                StringBuffer sb = new StringBuffer();
                //先保存勾选行的p_line_index
                String line_index = pbomRecords.get(0).getLineIndex();
                String d = "";//排序号
                int num = 0;
                for (int j = 0; j < pbomRecords.size(); j++) {
                    HzPbomLineRecord newRecord = new HzPbomLineRecord();
                    //修改p_line_index、sort_num属性
                    if (j == 0) {//勾选的父层零件
                        //新父的p_line_index
                        String s1 = listPbom.get(i).getLineIndex();
                        //界面传回来的查找编号
                        String s2 = reqDTO.getLineNo().replaceFirst("^0*", "");
                        sb.append(s1).append(".").append(s2);
                        if (listPbom.get(i).getIsHas() == 0) {
                            //获取比listPbom.get(i).getSortNum()排序号大的最小排序号
                            String dd = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), listPbom.get(i).getSortNum());
                            if ("".equals(dd) || null == dd) {
                                dd = Double.parseDouble(listPbom.get(i).getSortNum()) + 1 + "";
                            }
                            d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), listPbom.get(i).getSortNum(), dd);
                        } else {
                            //比较查找编号
                            HzPbomTreeQuery query = new HzPbomTreeQuery();
                            query.setProjectId(reqDTO.getProjectId());
                            query.setPuid(listPbom.get(i).geteBomPuid());
                            List<HzPbomLineRecord> parentRecors = hzPbomRecordDAO.getHzPbomTree(query);
                            for (int k = 1; k < parentRecors.size(); k++) {
                                int lever1 = parentRecors.get(k).getLineIndex().split("\\.").length;
                                String str = parentRecors.get(k).getLineIndex();
                                int begin = str.lastIndexOf(".");
                                int end = str.length();
                                String result = str.substring(begin + 1, end);

                                if (Double.parseDouble(result) > Double.parseDouble(s2)) {
                                    d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), parentRecors.get(k - 1).getSortNum(), parentRecors.get(k).getSortNum());
                                    num = k;
                                    break;
                                } else if (Double.parseDouble(result) < Double.parseDouble(s2)) {
                                    if (k + 1 < parentRecors.size()) {
                                        int lever2 = parentRecors.get(k + 1).getLineIndex().split("\\.").length;
                                        if (lever2 > lever1) {
                                            //获取当前零件结构树，直接跳到结构树的最后一个子层
                                            HzPbomTreeQuery isQuery = new HzPbomTreeQuery();
                                            isQuery.setProjectId(reqDTO.getProjectId());
                                            isQuery.setPuid(parentRecors.get(k).geteBomPuid());//
                                            List<HzPbomLineRecord> isParentRecors = hzPbomRecordDAO.getHzPbomTree(isQuery);
                                            k = k + isParentRecors.size() - 1;
                                        }
                                    }
                                    if (k == parentRecors.size() - 1) {
                                        //添加到最后一个
                                        String dd = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), parentRecors.get(k).getSortNum());
                                        if ("".equals(dd) || null == dd) {
                                            dd = Double.parseDouble(parentRecors.get(k).getSortNum()) + 1 + "";
                                        }
                                        d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), parentRecors.get(k).getSortNum(), dd);
                                        num = k;
                                    }
                                }
                            }
                        }
                        //puid重新生成
                        newRecord.setPuid(UUID.randomUUID().toString());
                        newRecord.setParentUid(listPbom.get(i).geteBomPuid());
                        newRecord.setLineIndex(sb.toString());
                        newRecord.setSortNum(d);
                        newRecord.setIs2Y(0);
                        //获取新的EBOM_puid
                        //还得判断是否PBOM新添加件**********（选中零件的第一层不可能是工艺合件）
                        pbomParentuid = bom_Puid.get(pbomPuid_index++);
                        newRecord.seteBomPuid(pbomParentuid);
                        //newRecord.seteBomPuid(bom_Puid.get(pbomPuid_index++));
                    } else {//子层
                        String index = pbomRecords.get(j).getLineIndex().replaceFirst(line_index, sb.toString());
                        if (listPbom.get(i).getIsHas() == 0) {
                            //获取比上一个排序号大的最小排序号
                            String dd = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), d);
                            if ("".equals(dd) || null == dd) {
                                dd = Double.parseDouble(d) + 1 + "";
                            }
                            d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), d, dd);
                        } else {
                            //比较查找编号
                            if (num > 0) {
                                String dd = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), d);
                                if ("".equals(dd) || null == dd) {
                                    dd = Double.parseDouble(d) + 1 + "";
                                }
                                d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), d, dd);
                            }
                        }
                        //puid重新生成
                        newRecord.setPuid(UUID.randomUUID().toString());

                        //判断是否统一层级
                        if (pbomRecords.get(j).getLineIndex().split("\\.").length > pbomRecords.get(j - 1).getLineIndex().split("\\.").length) {
                            newRecord.setParentUid(pbomParentuid);//不同层级
                            pbomParentuid2 = pbomParentuid;
                        } else {
                            newRecord.setParentUid(pbomParentuid2);//同层级
                        }
                        newRecord.setLineIndex(index);
                        newRecord.setSortNum(d);
                        newRecord.setIs2Y(0);
                        //获取新的EBOM_puid
                        //还得判断是否PBOM新添加件**********
                        if(pbomRecords.get(j).getIsNewPart()==1){
//                            newRecord.seteBomPuid(pbomRecords.get(j).getPuid());
//                            pbomParentuid3 = pbomRecords.get(j).getPuid();
                            newRecord.seteBomPuid(newRecord.getPuid());
                            pbomParentuid3 = newRecord.getPuid();
                        }else{
                            //顺序不能反，是p_e_bom_puid同p_parent_uid
                            pbomParentuid = bom_Puid.get(pbomPuid_index++);//???
                            newRecord.seteBomPuid(pbomParentuid);
                        }
                    }
                    //代码重复部分
                    newRecord.setIsNewPart(pbomRecords.get(j).getIsNewPart());
                    newRecord.setIsDept(pbomRecords.get(j).getIsDept());
                    newRecord.setBomDigifaxId(pbomRecords.get(j).getBomDigifaxId());
                    newRecord.setLinePuid(pbomRecords.get(j).getLinePuid());
                    newRecord.setBomLineBlock(pbomRecords.get(j).getBomLineBlock());
                    newRecord.setIsPart(pbomRecords.get(j).getIsPart());
                    newRecord.setOrderNum(pbomRecords.get(j).getOrderNum());
                    newRecord.setProjectPuid(pbomRecords.get(j).getProjectPuid());
                    newRecord.setpBomLinePartName(pbomRecords.get(j).getpBomLinePartName());
                    newRecord.setpBomLinePartClass(pbomRecords.get(j).getpBomLinePartClass());
                    newRecord.setpBomLinePartEnName(pbomRecords.get(j).getpBomLinePartEnName());
                    newRecord.setpBomLinePartResource(pbomRecords.get(j).getpBomLinePartResource());
                    newRecord.setLineId(pbomRecords.get(j).getLineId());
                    newRecord.setIsHas(pbomRecords.get(j).getIsHas());
                    newRecord.setpBomOfWhichDept(pbomRecords.get(j).getpBomOfWhichDept());
                    newRecord.setResource(pbomRecords.get(j).getResource());
                    newRecord.setType(pbomRecords.get(j).getType());
                    newRecord.setBuyUnit(pbomRecords.get(j).getBuyUnit());
                    newRecord.setWorkShop1(pbomRecords.get(j).getWorkShop1());
                    newRecord.setWorkShop2(pbomRecords.get(j).getWorkShop2());
                    newRecord.setProductLine(pbomRecords.get(j).getProductLine());
                    newRecord.setMouldType(pbomRecords.get(j).getMouldType());
                    newRecord.setOuterPart(pbomRecords.get(j).getOuterPart());
                    newRecord.setColorPart(pbomRecords.get(j).getColorPart());
                    newRecord.setStation(pbomRecords.get(j).getStation());
                    newRecord.setStatus(pbomRecords.get(j).getStatus());
                    newRecord.setCreateTime(pbomRecords.get(j).getCreateTime());
                    newRecord.setUpdateTime(pbomRecords.get(j).getUpdateTime());
                    newRecord.setCreateName(pbomRecords.get(j).getCreateName());
                    newRecord.setUpdateName(pbomRecords.get(j).getUpdateName());
                    newRecord.setpLouaFlag(pbomRecords.get(j).getpLouaFlag());
                    hzPbomRecordDAO.insert2(newRecord);
                    //PBOM下的工艺合件
                    if(pbomRecords.get(j).getIsNewPart()==1){
                        //其子层都调整过去
                        //1.先获取pbomRecords.get(j)的结构树
                        HzPbomTreeQuery query = new HzPbomTreeQuery();
                        query.setProjectId(reqDTO.getProjectId());
                        query.setPuid(pbomRecords.get(j).geteBomPuid());
                        List<HzPbomLineRecord> recors = hzPbomRecordDAO.getHzPbomTree(query);
                        //2.循环结构树insert其子层
                        String temp = "";
                        String parentUid = "";
                        for(int k=1;k<recors.size();k++){
                            //////////////////////////////////
                            //line_index：勾选行的p_line_index
                            String index = recors.get(k).getLineIndex().replaceFirst(line_index, sb.toString());
                            if (listPbom.get(i).getIsHas() == 0) {
                                //获取比上一个排序号大的最小排序号
                                String dd = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), d);
                                if ("".equals(dd) || null == dd) {
                                    dd = Double.parseDouble(d) + 1 + "";
                                }
                                d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), d, dd);
                            } else {
                                //比较查找编号
                                if (num > 0) {
                                    String dd = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), d);
                                    if ("".equals(dd) || null == dd) {
                                        dd = Double.parseDouble(d) + 1 + "";
                                    }
                                    d = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), d, dd);
                                }
                            }

                            HzPbomLineRecord newRecord2 = new HzPbomLineRecord();//合件的树结构子层
                            newRecord2.setPuid(UUID.randomUUID().toString());
                            //获取新的EBOM_puid
                            //还得判断是否PBOM新添加件**********
                            if(pbomRecords.get(j).getIsNewPart()==1){
                                temp = recors.get(k).getPuid();
                                newRecord2.seteBomPuid(temp);
                            }else{
                                //pbomParentuid = bom_Puid.get(pbomPuid_index++);
                                //newRecord2.seteBomPuid(pbomParentuid3);
                                //比EBOM中多的时候不能从之前的零件直接copy，不然会导致查询死循环
                                temp = UUID.randomUUID().toString();
                                newRecord2.seteBomPuid(temp);
                            }
                            //判断是否统一层级
                            if (recors.get(k).getLineIndex().split("\\.").length > recors.get(k - 1).getLineIndex().split("\\.").length) {
                                if(k==1){
                                    newRecord2.setParentUid(pbomParentuid3);//不同层级
                                    pbomParentuid4 = pbomParentuid3;
                                }else{
                                    newRecord2.setParentUid(parentUid);
                                    //为下一层级做准备
                                    //parentUid = recors.get(k).geteBomPuid();
                                    //pbomParentuid4 = pbomParentuid3;
                                }
                            } else {
                                if(recors.get(k).getLineIndex().split("\\.").length == recors.get(1).getLineIndex().split("\\.").length)
                                    newRecord2.setParentUid(pbomParentuid4);
                                else
                                    newRecord2.setParentUid(parentUid);//同层级
                            }
                            parentUid = temp;
                            newRecord2.setLineIndex(index);
                            newRecord2.setSortNum(d);
                            newRecord2.setIs2Y(0);

                            newRecord2.setIsNewPart(recors.get(k).getIsNewPart());
                            newRecord2.setIsDept(recors.get(k).getIsDept());
                            newRecord2.setBomDigifaxId(recors.get(k).getBomDigifaxId());
                            newRecord2.setLinePuid(recors.get(k).getLinePuid());
                            newRecord2.setBomLineBlock(recors.get(k).getBomLineBlock());
                            newRecord2.setIsPart(recors.get(k).getIsPart());
                            newRecord2.setOrderNum(recors.get(k).getOrderNum());
                            newRecord2.setProjectPuid(recors.get(k).getProjectPuid());
                            newRecord2.setpBomLinePartName(recors.get(k).getpBomLinePartName());
                            newRecord2.setpBomLinePartClass(recors.get(k).getpBomLinePartClass());
                            newRecord2.setpBomLinePartEnName(recors.get(k).getpBomLinePartEnName());
                            newRecord2.setpBomLinePartResource(recors.get(k).getpBomLinePartResource());
                            newRecord2.setLineId(recors.get(k).getLineId());
                            newRecord2.setIsHas(recors.get(k).getIsHas());
                            newRecord2.setpBomOfWhichDept(recors.get(k).getpBomOfWhichDept());
                            newRecord2.setResource(recors.get(k).getResource());
                            newRecord2.setType(recors.get(k).getType());
                            newRecord2.setBuyUnit(recors.get(k).getBuyUnit());
                            newRecord2.setWorkShop1(recors.get(k).getWorkShop1());
                            newRecord2.setWorkShop2(recors.get(k).getWorkShop2());
                            newRecord2.setProductLine(recors.get(k).getProductLine());
                            newRecord2.setMouldType(recors.get(k).getMouldType());
                            newRecord2.setOuterPart(recors.get(k).getOuterPart());
                            newRecord2.setColorPart(recors.get(k).getColorPart());
                            newRecord2.setStation(recors.get(k).getStation());
                            newRecord2.setStatus(recors.get(k).getStatus());
                            newRecord2.setCreateTime(recors.get(k).getCreateTime());
                            newRecord2.setUpdateTime(recors.get(k).getUpdateTime());
                            newRecord2.setCreateName(recors.get(k).getCreateName());
                            newRecord2.setUpdateName(recors.get(k).getUpdateName());
                            newRecord2.setpLouaFlag(recors.get(k).getpLouaFlag());
                            hzPbomRecordDAO.insert2(newRecord2);
                            //////////////////////////////////
                            j++;
                        }
                    }
                }

                //修改新父isHas属性,判断新父是否带子层，如果新父之前不带子层，则新父的层级变成对应的Y级，反之不变
                if (listPbom.get(i).getIsHas() == 0) {
                    HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
                    hzPbomLineRecord.setIsHas(1);
                    hzPbomLineRecord.seteBomPuid(listPbom.get(i).geteBomPuid());//P_E_BOM_PUID
                    hzPbomRecordDAO.update(hzPbomLineRecord);
                }
                //修改新父isPart属性,判断新父是part，如果新父之前是，则新父的层级变成不是，反之不变
                if (listPbom.get(i).getIsPart() == 1) {
                    HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
                    hzPbomLineRecord.setIsPart(0);
                    hzPbomLineRecord.seteBomPuid(listPbom.get(i).geteBomPuid());
                    hzPbomRecordDAO.update(hzPbomLineRecord);
                }
            }
        }
        return WriteResultRespDTO.getSuccessResult();
    }

    @Override
    public WriteResultRespDTO deleteHzEbomRecordById(DeleteHzEbomReqDTO reqDTO) {
        WriteResultRespDTO respDTO = new WriteResultRespDTO();
        try {
//            boolean b = PrivilegeUtil.writePrivilege();
//            if (!b) {
//                return WriteResultRespDTO.getFailPrivilege();
//            }
            if (StringUtil.isEmpty(reqDTO.getPuids()) || StringUtil.isEmpty(reqDTO.getProjectId())) {
                respDTO.setErrMsg("非法参数！");
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                return respDTO;
            }
            String bomPuids[] = reqDTO.getPuids().trim().split(",");
            /**关联特性的不允许删除*/
            StringBuilder sb = null;
            if (null != (sb = checkConnectWithFeature(bomPuids, reqDTO.getProjectId()))) {
                respDTO.setErrMsg(sb.toString());
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                return respDTO;
            }
            /**
             * 1.EBOM 删除 需要同步删除PBOM数据
             * 2.为了保证结构的一致性，改删除是带子层的删除
             * 3.删除数据时，需要判断该零件与其分层之间的引用关系，删除时需要解除全部的引用关系
             * 4.删除数据时，删除的记录要走流程进行审核，需要记录将要删除的数据
             */
            for (String puid : bomPuids) {
                //查找自己信息
                Map<String, Object> map = new HashMap<>();
                map.put("puid", puid);
                map.put("projectId", reqDTO.getProjectId());
                HzBomLineRecord record = hzBomLineRecordDao.findBomLine(map);
                if(record == null){
                    continue;
                }
                String lineId = record.getLineID();
                String parentId = record.getParentUid();
                Set<String> willBeDeletedPuids  = new HashSet<>();
                Set<String> willBeDeletedPuidsForPbom = new HashSet<>();
                if(StringUtil.isEmpty(parentId)){//删除2Y层
                    HzEbomTreeQuery query = new HzEbomTreeQuery();
                    query.setPuid(puid);
                    query.setProjectId(reqDTO.getProjectId());
                    List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(query);
                    if(ListUtil.isNotEmpty(records)){
                        records.forEach(eplManageRecord -> {
                            willBeDeletedPuids.add(eplManageRecord.getPuid());
                        });
                    }

                    HzPbomTreeQuery pbomTreeQuery = new HzPbomTreeQuery();
                    pbomTreeQuery.setPuid(puid);
                    pbomTreeQuery.setProjectId(reqDTO.getProjectId());
                    List<HzPbomLineRecord> recordList = hzPbomRecordDAO.getHzPbomTree(pbomTreeQuery);
                    if(ListUtil.isNotEmpty(recordList)){
                        recordList.forEach(pbomLineRecord -> {
                            willBeDeletedPuidsForPbom.add(pbomLineRecord.geteBomPuid());
                        });
                    }

                }else {//删除 非2Y层
                    //查找其父信息
                    String parentLineId = "";//父零件号
                    Map<String,Object> parentMap = new HashMap<>();
                    parentMap.put("puid",parentId);
                    parentMap.put("projectId",reqDTO.getProjectId());
                    List<HzEPLManageRecord> parents = hzEbomRecordDAO.findEbom(parentMap);
                    if(ListUtil.isNotEmpty(parents)){
                        parentLineId = parents.get(0).getLineID();
                        //根据其父零件号和其之间的引用关系，需要解除全部的引用关系
                        Map<String,Object> map1 = new HashMap<>();
                        map1.put("projectId",reqDTO.getProjectId());
                        map1.put("lineId",parentLineId);
                        map1.put("lineID",parentLineId);

                        /** 和自己有引用关系的零件*/

                        /**   EBOM    */
                        List<HzEPLManageRecord> allReferenceParents = hzEbomRecordDAO.findEbom(map1);
                        HzEbomTreeQuery currentTreeQuery = new HzEbomTreeQuery();
                        currentTreeQuery.setProjectId(reqDTO.getProjectId());
                        currentTreeQuery.setPuid(puid);

                        List<HzEPLManageRecord> currentSelf = hzEbomRecordDAO.getHzBomLineChildren(currentTreeQuery);//自己EBOM

                        if(ListUtil.isNotEmpty(allReferenceParents)){ //EBOM
                            for(HzEPLManageRecord manageRecord : allReferenceParents){
                                HzEbomTreeQuery parentQuery = new HzEbomTreeQuery();
                                parentQuery.setProjectId(reqDTO.getProjectId());
                                parentQuery.setPuid(manageRecord.getPuid());
                                List<HzEPLManageRecord> list = hzEbomRecordDAO.getHzBomLineChildren(parentQuery);//父亲
                                if(ListUtil.isNotEmpty(list) && ListUtil.isNotEmpty(currentSelf)){
                                    if(list.size() - currentSelf.size() == 1){
                                        HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                                        hzBomLineRecord.setIsHas(0);
                                        hzBomLineRecord.setIsPart(1);
                                        hzBomLineRecord.setPuid(manageRecord.getPuid());
                                        hzBomLineRecord.setStatus(2);
                                        hzBomLineRecordDao.update(hzBomLineRecord);
                                    }
                                    String currentPuid = ""; //找出应用关系所对应的puid
                                    for(HzEPLManageRecord r : list){
                                        if(r.getLineID().equals(lineId)){
                                            currentPuid = r.getPuid();
                                            break;
                                        }
                                    }
                                    parentQuery.setPuid(currentPuid);
                                    list = hzEbomRecordDAO.getHzBomLineChildren(parentQuery);//父亲
                                    if(ListUtil.isNotEmpty(list)){
                                        list.forEach(eplManageRecord->{
                                            willBeDeletedPuids.add(eplManageRecord.getPuid());
                                        });
                                    }
                                }
                            }
                        }

                        /**  PBOM  */
                        List<HzPbomLineRecord> pbomAllReferenceParents = hzPbomRecordDAO.getPbomById(map1);
                        HzPbomTreeQuery currentPbomTreeQuery = new HzPbomTreeQuery();
                        currentPbomTreeQuery.setPuid(puid);
                        currentPbomTreeQuery.setProjectId(reqDTO.getProjectId());
                        List<HzPbomLineRecord> currentSelf2 = hzPbomRecordDAO.getHzPbomTree(currentPbomTreeQuery);//自己PBOM


                        if(ListUtil.isNotEmpty(pbomAllReferenceParents)){//PBOM
                            for(HzPbomLineRecord manageRecord : pbomAllReferenceParents){
                                HzPbomTreeQuery pbomTreeQuery = new HzPbomTreeQuery();
                                pbomTreeQuery.setPuid(manageRecord.geteBomPuid());
                                pbomTreeQuery.setProjectId(reqDTO.getProjectId());
                                List<HzPbomLineRecord> list2 = hzPbomRecordDAO.getHzPbomTree(pbomTreeQuery);//父亲 PBOM
                                if(ListUtil.isNotEmpty(list2) && ListUtil.isNotEmpty(currentSelf2)){
                                    if(list2.size() - currentSelf2.size() == 1){
                                        HzPbomLineRecord hzBomLineRecord = new HzPbomLineRecord();
                                        hzBomLineRecord.setIsHas(0);
                                        hzBomLineRecord.setIsPart(1);
                                        hzBomLineRecord.setPuid(manageRecord.geteBomPuid());
                                        hzBomLineRecord.setStatus(1);
                                        hzPbomRecordDAO.update(hzBomLineRecord);
                                    }

                                    String currentPuid = ""; //找出应用关系所对应的puid
                                    for(HzPbomLineRecord r : list2){
                                        if(r.getLineId().equals(lineId)){
                                            currentPuid = r.geteBomPuid();
                                            break;
                                        }
                                    }
                                    pbomTreeQuery.setPuid(currentPuid);
                                    list2 = hzPbomRecordDAO.getHzPbomTree(pbomTreeQuery);//父亲
                                    if(ListUtil.isNotEmpty(list2)){
                                        list2.forEach(pbomLineRecord->{
                                            willBeDeletedPuidsForPbom.add(pbomLineRecord.geteBomPuid());
                                        });
                                    }
                                }

                            }
                        }

                    }
                }


                StringBuffer eBomBuffer = new StringBuffer();
                StringBuffer pBomBuffer = new StringBuffer();

                for (String s : willBeDeletedPuids) {
                    eBomBuffer.append(s+",");
                }

                for (String s : willBeDeletedPuidsForPbom) {
                    pBomBuffer.append(s+",");
                }

                //mabatis 做批量更新时 返回值为-1 所以这里根据异常情况来判断成功与否
                if (ListUtil.isNotEmpty(willBeDeletedPuids)) {
                    hzEbomRecordDAO.deleteList(eBomBuffer.toString());
                }

                if (ListUtil.isNotEmpty(willBeDeletedPuidsForPbom)) {
                    hzPbomRecordDAO.deleteByPuids(pBomBuffer.toString());
                }

            }
            return WriteResultRespDTO.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
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
            int num = (query.getPage() - 1) * query.getPageSize();
            Page<HzEPLManageRecord> recordPage = hzEbomRecordDAO.getHzRecycleRecord(query);
            if (recordPage == null || recordPage.getResult() == null || recordPage.getResult().size() == 0) {
                return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), 0);
            }
            HzEbomRespDTO recordRespDTO = new HzEbomRespDTO();
            JSONArray array = new JSONArray();
            List<HzEbomRespDTO> recordRespDTOList = new ArrayList<>();
            List<HzEPLManageRecord> records = recordPage.getResult();
            for (HzEPLManageRecord record : records) {
                JSONObject jsonObject = HzEbomRecordFactory.bomLineRecordTORespDTO(record);
                array.add(jsonObject);
            }
            recordRespDTO.setJsonArray(array);
            recordRespDTOList.add(recordRespDTO);
            return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), recordPage.getTotalCount(), recordRespDTOList);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public WriteResultRespDTO recoverDeleteEbomRecord(String projectId, String puid) {
        WriteResultRespDTO respDTO = new WriteResultRespDTO();
        try {
            if (projectId == null || projectId == "" || puid == null || puid == "") {
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                respDTO.setErrMsg("非法参数");
                return respDTO;
            }
            HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid, projectId);
            if (record != null) {
                respDTO.setErrMsg("当前要恢复对象已存在bom系统中！");
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                return respDTO;
            }

            record = hzEbomRecordDAO.getHasDeletedBom(puid, projectId);
            if (record != null) {
                if (record.getLineIndex().split("\\.").length == 2) {
                    respDTO.setErrMsg("2Y层结构无法恢复！");
                    respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    return respDTO;
                }
                HzEPLManageRecord manageRecord = hzEbomRecordDAO.findEbomById(record.getParentUid(), projectId);
                if (manageRecord == null) {
                    respDTO.setErrMsg("当前要恢复对象的父结构不存在，无法恢复！");
                    respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    return respDTO;
                } else {
                    HzBomLineRecord bomLineRecord = new HzBomLineRecord();
                    if (manageRecord.getIsHas().equals(0)) {
                        bomLineRecord.setIsHas(1);
                        bomLineRecord.setIsPart(0);
                        if (manageRecord.getLineIndex().split("\\.").length == 2 && manageRecord.getIs2Y().equals(0)) {
                            bomLineRecord.setIs2Y(1);
                        }
                        int i = hzBomLineRecordDao.update(bomLineRecord);
                        if (i <= 0) {
                            return WriteResultRespDTO.getFailResult();
                        }
                    }
                    HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                    hzBomLineRecord.setPuid(record.getPuid());
                    int i = hzBomLineRecordDao.update(hzBomLineRecord);
                    if (i > 0) {
                        return WriteResultRespDTO.getSuccessResult();
                    }
                }
            }
            return WriteResultRespDTO.getFailResult();
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public HzBomLineRecord findParentFor2Y(String projectId, String puid) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("puid", puid);
        List<HzBomLineRecord> records = hzBomLineRecordDao.findBomListForChange(map);
        if (ListUtil.isNotEmpty(records)) {
            if (records.get(0).getIs2Y().equals(1)) {
                return records.get(0);
            } else if (records.get(0).getParentUid() == null) {
                return records.get(0);
            } else {
                return findParentFor2Y(projectId, records.get(0).getParentUid());
            }
        } else {
            return null;
        }

    }

    @Override
    public WriteResultRespDTO setCurrentBomAsLou(SetLouReqDTO reqDTO) {
        try {
            /**
             * 设置为LOU 或者取消设置为LOU
             */
            if (reqDTO.getLineIds() == null || reqDTO.getProjectId() == null) {
                return WriteResultRespDTO.IllgalArgument();
            }
//            boolean b = PrivilegeUtil.writePrivilege();
//            if (!b) {
//                return WriteResultRespDTO.getFailPrivilege();
//            }
            String[] lineIds = reqDTO.getLineIds().split(",");
            for (String lineId : lineIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("lineID", lineId);
                map.put("lineId", lineId);
                map.put("projectId", reqDTO.getProjectId());
                List<HzEPLManageRecord> ebomList = hzEbomRecordDAO.findEbom(map);
                List<HzPbomLineRecord> pbomList = hzPbomRecordDAO.getPbomById(map);
                List<HzMbomLineRecord> mbomList = hzMbomRecordDAO.findHzMbomByPuid(map);
                if (ListUtil.isNotEmpty(ebomList)) {
                    List<HzBomLineRecord> list1 = new ArrayList<>();
                    Set<HzBomLineRecord> set = new HashSet<>();
                    ebomList.forEach(record -> {
                        HzEbomTreeQuery treeQuery = new HzEbomTreeQuery();
                        treeQuery.setProjectId(reqDTO.getProjectId());
                        treeQuery.setPuid(record.getPuid());
                        List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(treeQuery);
                        if (ListUtil.isNotEmpty(records) && records.size() > 1) {//父层设置为LOU，子层全部为LOA
                            HzEPLManageRecord r = records.get(0);
                            for (int i = 1; i < records.size(); i++) {
                                HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                                if (Integer.valueOf(1).equals(r.getpLouaFlag())) {
                                    hzBomLineRecord.setpLouaFlag(2);
                                } else {
                                    hzBomLineRecord.setpLouaFlag(0);
                                }
                                hzBomLineRecord.setPuid(records.get(i).getPuid());
//                                hzBomLineRecord.setTableName("HZ_BOM_LINE_RECORD");
                                set.add(hzBomLineRecord);
                            }

                        }
                        HzBomLineRecord record1 = new HzBomLineRecord();
                        record1.setPuid(record.getPuid());
                        if (Integer.valueOf(1).equals(record.getpLouaFlag())) {//是lou，则取消设置，否则，就设置为LOU
                            record1.setpLouaFlag(2);
                        } else {
                            record1.setpLouaFlag(1);
                        }
//                        record1.setTableName("HZ_BOM_LINE_RECORD");
                        list1.add(record1);
                    });

                    hzBomLineRecordDao.updateBatch(list1);
                    List<HzBomLineRecord> list2 = new ArrayList<>(set);
                    hzBomLineRecordDao.updateBatch(list2);
                }

                if (ListUtil.isNotEmpty(pbomList)) {
                    pbomList.forEach(hzPbomLineRecord -> {
                        if (Integer.valueOf(1).equals(hzPbomLineRecord.getpLouaFlag())) {
                            hzPbomLineRecord.setpLouaFlag(2);
                        } else {
                            hzPbomLineRecord.setpLouaFlag(1);
                        }
                        hzPbomRecordDAO.update(hzPbomLineRecord);
                    });
                }
                if (ListUtil.isNotEmpty(mbomList)) {
                    mbomList.forEach(hzMbomLineRecord -> {
                        hzMbomLineRecord.setpLouaFlag(Integer.valueOf(1).equals(hzMbomLineRecord.getpLouaFlag()) ? 2 : 1);
                        hzMbomRecordDAO.update(hzMbomLineRecord);
                    });
                }

            }
            return WriteResultRespDTO.getSuccessResult();
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public HzLouRespDTO getHzLouInfoById(HzLouaQuery query) {
        try {
            HzLouRespDTO respDTO = new HzLouRespDTO();
            HzBomLineRecord hzBomLineRecord = findParentFor2Y(query.getProjectId(), query.getPuid());
            if (hzBomLineRecord != null) {
                HzCfg0OfBomLineRecord record = hzCfg0OfBomLineService.doSelectByBLUidAndPrjUid(query.getProjectId(), hzBomLineRecord.getPuid());
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
            return null;
        }
    }



    @Override
    @Transactional
    public WriteResultRespDTO dataToChangeOrder(AddDataToChangeOrderReqDTO reqDTO) {
        if(StringUtil.isEmpty(reqDTO.getPuids()) || StringUtil.isEmpty(reqDTO.getProjectId())
                || null == reqDTO.getOrderId()){
            return WriteResultRespDTO.IllgalArgument();
        }
        try {
            //获取申请人信息
            User user = UserInfo.getUser();
            Long applicantId = Long.valueOf(user.getId());

            //表单id
            Long orderId = reqDTO.getOrderId();

            //获取审核人信息
//            Long auditorId = reqDTO.getAuditorId();
            //数据库表名
            String tableName = ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName();
            //获取数据信息
            List<String> puids = Lists.newArrayList(reqDTO.getPuids().split(","));

            //统计操作数据
            Map<String,Object> map = new HashMap<>();

            //查询EBOM表数据 保存历史记录
            HzChangeDataDetailQuery query  = new HzChangeDataDetailQuery();
            query.setProjectId(reqDTO.getProjectId());
            query.setPuids(puids);
            query.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());
            List<HzEPLManageRecord> records = hzEbomRecordDAO.getEbomRecordsByPuids(query);
            List<HzEPLManageRecord> afterRecords = new ArrayList<>();
            if(ListUtil.isNotEmpty(records)){
                records.forEach(record -> {
                    HzEPLManageRecord manageRecord = record;
                    manageRecord.setOrderId(orderId);
                    afterRecords.add(manageRecord);
                });
                map.put("ebomAfter",afterRecords);

                //修改发起流程后状态值
                List<HzBomLineRecord> bomLineRecords = new ArrayList<>();
                for(HzEPLManageRecord record:records){
                    HzBomLineRecord lineRecord = HzEbomRecordFactory.eplRecordToBomLineRecord(record);
                    if(Integer.valueOf(2).equals(record.getStatus())){//草稿状态---->审核状态
                        lineRecord.setStatus(5);
                    }else if(Integer.valueOf(4).equals(record.getStatus())){// 删除状态----->审核状态
                        lineRecord.setStatus(6);
                    }
                    lineRecord.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());
                    bomLineRecords.add(lineRecord);
                }

                map.put("ebomBefore",bomLineRecords);
                //保存以上获取信息
                //变更数据
                List<HzChangeDataRecord> dataRecords = new ArrayList<>();
                for(String puid:puids){
                    HzChangeDataRecord record = new HzChangeDataRecord();
                    record.setApplicantId(applicantId);
//                    record.setAuditorId(auditorId);
                    record.setOrderId(reqDTO.getOrderId());
                    record.setPuid(puid);
                    record.setTableName(tableName);
                    dataRecords.add(record);
                }
                map.put("changeData",dataRecords);
                //申请人
                HzApplicantChangeRecord applicantChangeRecord = new HzApplicantChangeRecord();
                applicantChangeRecord.setApplicantId(applicantId);
                applicantChangeRecord.setOrderId(reqDTO.getOrderId());
                applicantChangeRecord.setTableName(tableName);

                map.put("applicant",applicantChangeRecord);
                //审核人
//                HzAuditorChangeRecord auditorChangeRecord = new HzAuditorChangeRecord();
//                auditorChangeRecord.setAuditorId(auditorId);
//                auditorChangeRecord.setOrderId(reqDTO.getOrderId());
//                auditorChangeRecord.setTableName(tableName);
//
//                map.put("auditor",auditorChangeRecord);


                //启动线程进行插入操作
                List<ExecutorServices> services = new ArrayList<>();
                for(Map.Entry<String,Object> entry:map.entrySet()){
                    ExecutorServices executorServices = new ExecutorServices(map.size()) {
                        @Override
                        public void action() {
                            switch (entry.getKey()){
                                case "ebomAfter":
                                    hzEbomRecordDAO.insertList((List<HzEPLManageRecord>) entry.getValue(),tableName);
                                    break;
                                case "ebomBefore":
                                    hzEbomRecordDAO.updateList((List<HzBomLineRecord>) entry.getValue());
                                    break;
                                case "changeData":
                                    hzChangeDataRecordDAO.insertList((List<HzChangeDataRecord>) entry.getValue());
                                    break;
                                case "applicant":
                                    hzApplicantChangeDAO.insert((HzApplicantChangeRecord) entry.getValue());
                                    break;
//                                case "auditor" :
//                                    hzAuditorChangeDAO.insert((HzAuditorChangeRecord) entry.getValue());
//                                    break;
                                default:break;
                            }
                        }
                    };
                    services.add(executorServices);
                }
                if(ListUtil.isNotEmpty(services)){
                    for(ExecutorServices s:services){
                        s.execute();
                    }
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getSuccessResult();
    }

    /**
     * 撤销EBOM数据到上一个生效版本
     * @param reqDTO
     * @return
     */
    @Override
    public WriteResultRespDTO backBomUtilLastValidState(BomBackReqDTO reqDTO) {
        try{
            List<String> puids = Lists.newArrayList(reqDTO.getPuids().split(","));
            HzChangeDataDetailQuery query = new HzChangeDataDetailQuery();
            query.setProjectId(reqDTO.getProjectId());
            query.setPuids(puids);
            query.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());
            List<String> deletePuids = new ArrayList<>();
            List<HzEPLManageRecord> updateRecords = new ArrayList<>();
            List<HzBomLineRecord> updateList = new ArrayList<>();
            Set<HzEPLManageRecord> set = new HashSet<>();
            pool.submit(()->{
                List<HzEPLManageRecord> list = hzEbomRecordDAO.getEbomRecordsByPuids(query);
                //带子层撤销
                //撤销 1找不存在版本记录的--删除    2找存在记录-直接更新数据为上个版本生效数据
                if(ListUtil.isNotEmpty(list)){
                    list.forEach(r->{
                        if(1==r.getIsHas()){
                            HzEbomTreeQuery ebomTreeQuery = new HzEbomTreeQuery();
                            ebomTreeQuery.setProjectId(reqDTO.getProjectId());
                            ebomTreeQuery.setPuid(r.getPuid());
                            List<HzEPLManageRecord> l = hzEbomRecordDAO.getHzBomLineChildren(ebomTreeQuery);
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
                            deletePuids.add(record.getPuid());
                        }else {
                            updateRecords.add(record);
                        }
                    });
                }
                if(ListUtil.isNotEmpty(updateRecords)){
                    HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
                    dataDetailQuery.setRevision(true);
                    dataDetailQuery.setProjectId(reqDTO.getProjectId());
                    dataDetailQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_BEFORE.getTableName());
                    dataDetailQuery.setStatus(1);
                    updateRecords.forEach(record -> {
                        dataDetailQuery.setRevisionNo(record.getRevision());
                        HzEPLManageRecord manageRecord = hzEbomRecordDAO.getEBomRecordByPuidAndRevision(dataDetailQuery);
                        if(manageRecord!=null){
                            updateList.add(HzEbomRecordFactory.eplRecordToBomLineRecord(manageRecord));
                        }
                    });
                }
                if(ListUtil.isNotEmpty(updateList)){
                    hzEbomRecordDAO.updateList(updateList);
                }
                if(ListUtil.isNotEmpty(deletePuids)){
                    hzEbomRecordDAO.deleteByPuids(deletePuids);
                }

            });
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }


    /**
     * 检查是否关联特性
     *
     * @param puids
     * @param projectUid
     * @return
     */
    private StringBuilder checkConnectWithFeature(String[] puids, String projectUid) {
        HzFullCfgMain hzFullCfgMain = hzFullCfgMainDao.selectByProjectId(projectUid);
        StringBuilder sb = null;
        if(hzFullCfgMain == null){
            return sb;
        }
        HzExFullCfgWithCfg cfg = null;
        for (int i = 0; i < puids.length; i++) {
            if (null != (cfg = hzFullCfgWithCfgDao.selectByBLOutWithCfgAndBL(hzFullCfgMain.getId(), puids[i]))) {
                if (cfg.getCfg() == null) {
                    continue;
                }
                if (sb == null) {
                    sb = new StringBuilder();
                }
                HzEbomRespDTO dto = fingEbomById(puids[i], projectUid);
                sb.append("2Y层:" + cfg.getBomLine().getLineID() + " 已关联特性值" + cfg.getCfg().getpCfg0ObjectId() + "，请在全配置BOM一级清单中将" + cfg.getBomLine().getLineID() + "去除绑定特性值<br>");
            }
        }
        return sb;

    }


    private boolean insert(AddDataToChangeOrderReqDTO reqDTO){
        String tableName = ChangeTableNameEnum.HZ_EBOM_BEFORE.getTableName();
        List<String> puids = Lists.newArrayList(reqDTO.getPuids().split(","));

        //统计操作数据
        Map<String,Object> map = new HashMap<>();

        //查询EBOM表数据 保存历史记录
        HzChangeDataDetailQuery query  = new HzChangeDataDetailQuery();
        query.setProjectId(reqDTO.getProjectId());
        query.setPuids(puids);
        query.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());
        List<HzEPLManageRecord> records = hzEbomRecordDAO.getEbomRecordsByPuids(query);
        hzEbomRecordDAO.insertList(records,tableName);
        return false;
    }
}
