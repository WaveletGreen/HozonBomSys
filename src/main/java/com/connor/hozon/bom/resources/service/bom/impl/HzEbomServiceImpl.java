package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.bean.HzExFullCfgWithCfg;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OfBomLineService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.SetLouReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzLouRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.model.*;
import com.connor.hozon.bom.resources.domain.query.*;
import com.connor.hozon.bom.resources.enumtype.BomResourceEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.sys.entity.User;
import io.swagger.models.auth.In;
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
import sql.pojo.cfg.HzFullCfgMain;
import sql.pojo.cfg.HzFullCfgWithCfg;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.project.HzMaterielRecord;
import sql.redis.SerializeUtil;

import java.util.*;

import static com.connor.hozon.bom.resources.domain.model.HzBomSysFactory.getLevelAndRank;

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

    @Override
    public Page<HzEbomRespDTO> getHzEbomPage(HzEbomByPageQuery query) {
        try {
            int num = (query.getPage() - 1) * query.getPageSize();
            HzEbomRespDTO recordRespDTO = new HzEbomRespDTO();
            JSONArray array = new JSONArray();
            List<HzEbomRespDTO> recordRespDTOList = new ArrayList<>();
            String level = query.getLevel();
            if (level != null && level != "") {
                if (level.trim().toUpperCase().endsWith("Y")) {
                    int length = Integer.valueOf(level.replace("Y", ""));
                    query.setIsHas(1);
                    query.setLineIndex(String.valueOf(length - 1));
                } else {
                    query.setIsHas(0);
                    int length = Integer.valueOf(level.trim());
                    query.setLineIndex(String.valueOf(length));
                }
            }

            Page<HzEPLManageRecord> recordPage = hzEbomRecordDAO.getHzEbomPage(query);
            if (recordPage == null || recordPage.getResult() == null || recordPage.getResult().size() == 0) {
                return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), 0);
            }
            List<HzEPLManageRecord> records = recordPage.getResult();
            for (HzEPLManageRecord record : records) {
                JSONObject jsonObject = HzEbomRecordFactory.bomLineRecordTORespDTO(record);
                //获取分组号
                String fastener = record.getpFastener();
                String groupNum = record.getLineID();
                try {
                    if (fastener.equals("/") || fastener.equals("")) {
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
                jsonObject.put("groupNum", groupNum);
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

    /**
     * 添加EBOM  最好使用事务
     *
     * @param reqDTO
     * @return
     */
    @Override
    public OperateResultMessageRespDTO addHzEbomRecord(AddHzEbomReqDTO reqDTO) {
        OperateResultMessageRespDTO operateResultMessageRespDTO = new OperateResultMessageRespDTO();
        try {
            String parentId = reqDTO.getPuid();
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
                map.put("pPuid", parentId);
                HzBomLineRecord record = hzBomLineRecordDao.findBomLine(map);

                //找到他们的父，并找到他们父的零件号，继而找出所有使用父零件号的部位，保持结构的完整性
                String bomLineId = "";
                if (record != null) {
                    bomLineId = record.getLineID();
                } else {
                    operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    operateResultMessageRespDTO.setErrMsg("当前插入对象的父结构不存在！");
                    return operateResultMessageRespDTO;
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
                            if (hzEPLManageRecord.getIsHas().equals(0) || hzEPLManageRecord.getIsPart().equals(1)) {
                                HzBomLineRecord hzBomLineRecord1 = new HzBomLineRecord();
                                hzBomLineRecord1.setIsHas(1);
                                hzBomLineRecord1.setIsPart(0);
                                hzBomLineRecord1.setPuid(hzEPLManageRecord.getPuid());
                                //更新数据
                                hzBomLineRecordDao.update(hzBomLineRecord1);
                            }
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
                                operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                                operateResultMessageRespDTO.setErrMsg("当前插入对象的父结构不存在！");
                                return operateResultMessageRespDTO;
                            }
                            if (records.size() == 1) {//当前父没有子
                                StringBuffer stringBuffer = new StringBuffer(lineIndex);
                                stringBuffer = stringBuffer.append(".10");
                                hzBomLineRecord.setLineIndex(stringBuffer.toString());
                                double d = Double.parseDouble(hzEPLManageRecord.getSortNum()) + 100;
                                hzBomLineRecord.setSortNum(String.valueOf(d));
                            } else {//找出当前父下的所有子一层
                                int length = lineIndex.split("\\.").length + 1;
                                List<HzEPLManageRecord> l = new ArrayList<>();
                                for (int k = 0; k < records.size(); k++) {
                                    int len = records.get(k).getLineIndex().split("\\.").length;
                                    if (length == len) {
                                        l.add(records.get(k));
                                    }
                                }
                                if (lineNo.equals("")) {//用户没有输入查找编号，默认添加到末尾位置
                                    double max = 0;
                                    HzEPLManageRecord lastRecord = new HzEPLManageRecord();
                                    for (HzEPLManageRecord manageRecord : l) {
                                        double d = Double.parseDouble(manageRecord.getSortNum());
                                        if (max < d) {
                                            max = d;
                                            lastRecord = manageRecord;
                                        }
                                    }
                                    String index = lastRecord.getLineIndex();
                                    String lineIndexExceptLastNum = index.substring(0, index.lastIndexOf("."));
                                    int lastNum = Integer.valueOf(index.split("\\.")[index.split("\\.").length - 1]);
                                    hzBomLineRecord.setLineIndex(lineIndexExceptLastNum + "." + (lastNum + 10));
                                    String s1 = String.valueOf(max);
                                    String s2 = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), s1);
                                    if (s2 == null) {
                                        hzBomLineRecord.setSortNum(String.valueOf(max + 100));
                                    } else {
                                        String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), s1, s2);
                                        hzBomLineRecord.setSortNum(sortNum);
                                    }
                                } else {
                                    int lineNum = Integer.valueOf(lineNo);
                                    boolean find = false;
                                    HzEPLManageRecord re = new HzEPLManageRecord();
                                    String lineIn = lineIndex + "." + lineNo;
                                    boolean b = hzEbomRecordDAO.lineIndexRepeat(reqDTO.getProjectId(), lineIn);
                                    if (b) {
                                        operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                                        operateResultMessageRespDTO.setErrMsg("当前要插入的位置已存在数据！");
                                        return operateResultMessageRespDTO;
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
                            hzBomLineRecordDao.insert(hzBomLineRecord);
                            hzBomLineRecord.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                            hzBomLineRecordDao.insert(hzBomLineRecord);
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
                                    //1.1-1.1.1  1.2.2.2 -1.2.2.2.1
                                    hzPbomLineRecord.setLineIndex(lineIndex1 + "." + 10);
                                    double d = Double.parseDouble(records1.get(0).getSortNum()) + 100;
                                    hzPbomLineRecord.setSortNum(String.valueOf(d));
                                } else {
                                    int length = lineIndex1.split("\\.").length + 1;
                                    List<HzPbomLineRecord> l = new ArrayList<>();
                                    for (int k = 0; k < records1.size(); k++) {
                                        int len = records1.get(k).getLineIndex().split("\\.").length;
                                        if (length == len) {
                                            l.add(records1.get(k));
                                        }
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
                                        String s1 = String.valueOf(max);
                                        String s2 = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), s1);
                                        if (s2 == null) {
                                            hzPbomLineRecord.setSortNum(String.valueOf(max + 100));
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

//                        if (ListUtil.isNotEmpty(list3)) {
//                            List<HzMbomLineRecord> hzMbomLineRecords = new ArrayList<>();
//                            for (int i = 0; i < list3.size(); i++) {
//                                HzMbomLineRecord hzMbomLineRecord = HzMbomRecordFactory.addHzEbomReqDTOMbomRecord(reqDTO);//MBOM
//                                if (list3.get(i).getIsHas().equals(0) || list3.get(i).getIsPart().equals(1)) {
//                                    list3.get(i).setIsHas(1);
//                                    list3.get(i).setIsPart(0);
//                                    //更新数据
//                                    hzMbomRecordDAO.update(list3.get(i));
//                                }
//
//                                hzMbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
//                                if (puids.size() >= list3.size()) {
//                                    hzMbomLineRecord.seteBomPuid(puids.get(i));
//                                } else {
//                                    hzMbomLineRecord.seteBomPuid(puids.get(0));
//                                }
//                                hzMbomLineRecord.setParentUid(list3.get(i).geteBomPuid());
//                                hzMbomLineRecord.setIs2Y(0);
//                                hzMbomLineRecord.setIsDept(0);
//                                hzMbomLineRecord.setIsHas(0);
//                                hzMbomLineRecord.setIsPart(1);
//
//                                HzMbomTreeQuery hzMbomTreeQuery = new HzMbomTreeQuery();
//                                hzMbomTreeQuery.setPuid(list3.get(i).geteBomPuid());
//                                hzMbomTreeQuery.setProjectId(reqDTO.getProjectId());
//                                List<HzMbomLineRecord> records2 = hzMbomRecordDAO.getHzMbomTree(hzMbomTreeQuery);
//                                String lineIndex1 = list3.get(i).getLineIndex();
//                                if (ListUtil.isEmpty(records2)) {
//                                    continue;
//                                }
//                                if (records2.size() == 1) {
//                                    hzMbomLineRecord.setLineIndex(lineIndex1 + "." + 10);
//                                    Double d = Double.parseDouble(list3.get(i).getSortNum()) + 100;
//                                    hzMbomLineRecord.setSortNum(String.valueOf(d));
//                                } else {
//                                    int length = lineIndex1.split("\\.").length + 1;
//                                    List<HzMbomLineRecord> l = new ArrayList<>();
//                                    for (int k = 0; k < records2.size(); k++) {
//                                        int len = records2.get(k).getLineIndex().split("\\.").length;
//                                        if (length == len) {
//                                            l.add(records2.get(k));
//                                        }
//                                    }
//                                    if (lineNo.equals("")) {//用户没有输入查找编号，默认添加到末尾位置
//                                        double max = 0;
//                                        HzMbomLineRecord lastRecord = new HzMbomLineRecord();
//                                        for (HzMbomLineRecord manageRecord : l) {
//                                            double d = Double.parseDouble(manageRecord.getSortNum());
//                                            if (max < d) {
//                                                max = d;
//                                                lastRecord = manageRecord;
//                                            }
//                                        }
//                                        String index = lastRecord.getLineIndex();
//                                        String lineIndexExceptLastNum = index.substring(0, index.lastIndexOf("."));
//                                        int lastNum = Integer.valueOf(index.split("\\.")[index.split("\\.").length - 1]);
//                                        hzMbomLineRecord.setLineIndex(lineIndexExceptLastNum + "." + (lastNum + 10));
//                                        String s1 = String.valueOf(max);
//                                        String s2 = hzMbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), s1);
//                                        if (s2 == null) {
//                                            hzMbomLineRecord.setSortNum(String.valueOf(max + 100));
//                                        } else {
//                                            String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), s1, s2);
//                                            hzMbomLineRecord.setSortNum(sortNum);
//                                        }
//                                    } else {
//                                        int lineNum = Integer.valueOf(lineNo);
//                                        boolean find = false;
//                                        HzMbomLineRecord re = new HzMbomLineRecord();
//                                        double max = Double.parseDouble(l.get(l.size() - 1).getSortNum());
//                                        for (int j = 0; j < l.size() - 1; j++) {
//                                            String index = l.get(j).getLineIndex();
//                                            String nextIndex = l.get(j + 1).getLineIndex();
//                                            int lastNum = Integer.valueOf(index.split("\\.")[index.split("\\.").length - 1]);
//                                            int nextLastNum = Integer.valueOf(nextIndex.split("\\.")[index.split("\\.").length - 1]);
//                                            if (lineNum > lastNum && lineNum < nextLastNum) {
//                                                find = true;
//                                                re = l.get(j);
//                                                break;
//                                            }
//                                        }
//
//                                        if (find) {//找出当前合适的插入位置
//                                            String index = re.getLineIndex();
//                                            String lineIndexExceptLast = index.substring(0, index.lastIndexOf("."));
//                                            String s1 = String.valueOf(re.getSortNum());
//                                            String s2 = hzMbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), s1);
//                                            if (s2 == null) {
//                                                hzMbomLineRecord.setSortNum(String.valueOf(max + 100));
//                                            } else {
//                                                String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), s1, s2);
//                                                hzMbomLineRecord.setSortNum(sortNum);
//                                            }
//                                            hzMbomLineRecord.setLineIndex(lineIndexExceptLast + "." + (Integer.valueOf(lineNo)));
//                                        } else {
//                                            re = l.get(l.size() - 1);
//                                            int lastNum = Integer.valueOf(re.getLineIndex().split("\\.")[re.getLineIndex().split("\\.").length - 1]);
//                                            hzMbomLineRecord.setLineIndex(lineIndex1 + "." + lineNo);
//                                            String s1 = "";
//                                            if (lineNum > lastNum) {
//                                                s1 = re.getSortNum();
//                                            } else {
//                                                s1 = list2.get(i).getSortNum();
//                                            }
//                                            String s2 = hzMbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(reqDTO.getProjectId(), s1);
//                                            if (s2 == null) {
//                                                hzMbomLineRecord.setSortNum(String.valueOf(max + 100));
//                                            } else {
//                                                String sortNum = HzBomSysFactory.generateBomSortNum(reqDTO.getProjectId(), s1, s2);
//                                                hzMbomLineRecord.setSortNum(sortNum);
//                                            }
//                                        }
//                                    }
//                                }
//                                hzMbomLineRecords.add(hzMbomLineRecord);
//                            }
//
//                            if (ListUtil.isNotEmpty(hzMbomLineRecords)) {
//                                hzMbomRecordDAO.insertList(hzMbomLineRecords);
//                            }
//                        }
//                        List<HzMaterielRecord> list = new ArrayList<>();
//                        HzMaterielRecord materielRecord = HzMaterielFactory.addHzEbomReqDTOMaterielRecord(reqDTO);
//                        materielRecord.setpMaterielDataType(BomResourceEnum.enumTypeToMaterielTypeNum(reqDTO.getpBomLinePartResource(), 0));
//                        materielRecord.setMaterielResourceId(puid);
//                        HzMaterielQuery hzMaterielQuery = new HzMaterielQuery();
//                        hzMaterielQuery.setProjectId(reqDTO.getProjectId());
//                        hzMaterielQuery.setpMaterielCode(reqDTO.getLineId());
//                        boolean repeat = hzMaterielDAO.isRepeat(hzMaterielQuery);
//                        if (!repeat) {
//                            list.add(materielRecord);
//                            hzMaterielDAO.insertList(list);
//                        }
                    }
                }
                return OperateResultMessageRespDTO.getSuccessResult();

            } else {
                //自己搭建父结构 默认为2Y层
                boolean isRepeat = hzEbomRecordDAO.checkItemIdIsRepeat(reqDTO.getProjectId(), reqDTO.getLineId());
                if (isRepeat) {
                    OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
                    respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    respDTO.setErrMsg("当前零件号已存在,请重新添加！");
                    return respDTO;
                }

                HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
                if (hzBomMainRecord == null) {
                    return OperateResultMessageRespDTO.getFailResult();
                }
                HzBomLineRecord hzBomLineRecord = HzEbomRecordFactory.addEbomDTOBomLineRecord(reqDTO);//EBOM
                hzBomLineRecord.setIsPart(0);
                hzBomLineRecord.setIsHas(1);
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

                hzBomLineRecord.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                hzBomLineRecordDao.insert(hzBomLineRecord);

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
                    hzPbomLineRecord.setIsHas(1);
                    hzPbomLineRecord.setIsPart(0);
                    Integer maxLineIndexFirstNum = hzPbomRecordDAO.getMaxLineIndexFirstNum(reqDTO.getProjectId());
                    if (maxLineIndexFirstNum == null) {
                        hzPbomLineRecord.setLineIndex("10.10");
                    } else {
                        maxLineIndexFirstNum = +10;
                        hzPbomLineRecord.setLineIndex(maxLineIndexFirstNum + ".10");
                    }
                    hzPbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
                    List<HzPbomLineRecord> pbomLineRecords = new ArrayList<>();
                    pbomLineRecords.add(hzPbomLineRecord);
                    hzPbomRecordDAO.insertList(pbomLineRecords);


//                    HzMbomLineRecord hzMbomLineRecord = HzMbomRecordFactory.addHzEbomReqDTOMbomRecord(reqDTO);//MBOM
//                    Double mbomMaxOrderNum = hzMbomRecordDAO.getHzMbomMaxOrderNum(reqDTO.getProjectId());
//                    if (mbomMaxOrderNum == null) {
//                        mbomMaxOrderNum = 100.0;
//                        hzMbomLineRecord.setSortNum(String.valueOf(mbomMaxOrderNum));
//                    } else {
//                        mbomMaxOrderNum += 100;
//                        int m = mbomMaxOrderNum.intValue();
//                        hzMbomLineRecord.setSortNum(String.valueOf(m));
//                    }
//                    Integer maxIndexFirstNum = hzMbomRecordDAO.getMaxLineIndexFirstNum(reqDTO.getProjectId());
//                    if (maxIndexFirstNum == null) {
//                        hzMbomLineRecord.setLineIndex("10.10");
//                    } else {
//                        maxIndexFirstNum += 10;
//                        hzMbomLineRecord.setLineIndex(maxIndexFirstNum + ".10");
//                    }
//                    hzMbomLineRecord.seteBomPuid(puid);
//                    hzMbomLineRecord.setIs2Y(1);
//                    hzMbomLineRecord.setIsDept(1);
//                    hzMbomLineRecord.setIsHas(1);
//                    hzMbomLineRecord.setIsPart(0);
//                    hzMbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
//                    List<HzMbomLineRecord> mbomLineRecords = new ArrayList<>();
//                    mbomLineRecords.add(hzMbomLineRecord);
//                    hzMbomRecordDAO.insertList(mbomLineRecords);
//
//
//                    HzMaterielRecord hzMaterielRecord = HzMaterielFactory.addHzEbomReqDTOMaterielRecord(reqDTO);
//                    hzMaterielRecord.setpMaterielDataType(BomResourceEnum.enumTypeToMaterielTypeNum(reqDTO.getpBomLinePartResource(), 1));
//                    hzMaterielRecord.setMaterielResourceId(puid);
//
//                    HzMaterielQuery hzMaterielQuery = new HzMaterielQuery();
//                    hzMaterielQuery.setProjectId(reqDTO.getProjectId());
//                    hzMaterielQuery.setpMaterielCode(reqDTO.getLineId());
//                    boolean repeat = hzMaterielDAO.isRepeat(hzMaterielQuery);
//                    if (!repeat) {
////                        List<HzMaterielRecord> list = hzMaterielDAO.findHzMaterielForList(hzMaterielQuery);
////                        list.forEach(record -> {
////                            hzMaterielRecord.setPuid(record.getPuid());
////                            hzMaterielDAO.update(hzMaterielRecord);
////                        });
//                        List<HzMaterielRecord> hzMaterielRecords = new ArrayList<>();
//                        hzMaterielRecords.add(hzMaterielRecord);
//                        hzMaterielDAO.insertList(hzMaterielRecords);
//                    }
                }
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
    }


    @Override
    public OperateResultMessageRespDTO updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO) {
        try {
            /**
             * 业务涉及到变更 需要走流程进行审核，走流程时需要查看变更前和变更后的数据，所以需要记录变更前的数据；
             * 这里每次走更新数据的时候先将原来未更新的数据保存一份，更新后的数据也保存一份，类似于查看变更历史记录的
             * 功能。
             */
            boolean isRepeat = hzEbomRecordDAO.checkItemIdIsRepeat(reqDTO.getProjectId(), reqDTO.getLineId());
            if (isRepeat) {
                HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(reqDTO.getPuid(), reqDTO.getProjectId());
                if (!record.getLineID().equals(reqDTO.getLineId())) {
                    OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
                    respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
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
                bomLineMap.put("tableName", "HZ_EBOM_REOCRD_BEFORE_CHANGE");
                List<HzBomLineRecord> records = hzBomLineRecordDao.findBomListForChange(bomLineMap);
                bomLineRecord.setTableName("HZ_EBOM_REOCRD_BEFORE_CHANGE");
                if (bomLineRecord.getEwoNo() != null && !bomLineRecord.getEwoNo().equals("1")) {
                    bomLineRecord.setEwoNo(null);
                }
                if (ListUtil.isEmpty(records)) {//不存在时 添加进去
                    hzBomLineRecordDao.insert(bomLineRecord);
                } else {//存在记录 查看是否都有产生流程历史记录
                    boolean insert = true;
                    for (HzBomLineRecord record : records) {
                        if (record.getEwoNo() == null || record.getEwoNo().equals("")) {
                            insert = false;
                            break;
                        }
                    }
                    if (insert) {
                        hzBomLineRecordDao.insert(bomLineRecord);
                    }
                }

            } else {
                return OperateResultMessageRespDTO.getFailResult();
            }

            HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(reqDTO.getProjectId());
            HzBomLineRecord hzBomLineRecord = HzEbomRecordFactory.updateHzEbomDTOLineRecord(reqDTO);
            hzBomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());

            //需要记录数据 变更后的数据
            bomLineMap.put("tableName", "HZ_EBOM_REOCRD_AFTER_CHANGE");
            List<HzBomLineRecord> rs = hzBomLineRecordDao.findBomListForChange(bomLineMap);
            HzBomLineRecord h = hzBomLineRecord;
            if (ListUtil.isEmpty(rs)) {
                h.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                h.setPuid(reqDTO.getPuid());
                h.setLineIndex(bomLineRecord.getLineIndex());
                h.setSortNum(bomLineRecord.getSortNum());
                h.setIsHas(bomLineRecord.getIsHas());
                h.setIs2Y(bomLineRecord.getIs2Y());
                h.setIsPart(bomLineRecord.getIsPart());
                h.setLinePuid(bomLineRecord.getLinePuid());
                hzBomLineRecordDao.insert(h);
            } else {
                boolean update = false;
                Long id = 0L;
                for (HzBomLineRecord r : rs) {
                    if (r.getEwoNo() != null && "1".equals(r.getEwoNo())) {
                        r.setEwoNo(null);
                    }
                    if (r.getEwoNo() == null || r.getEwoNo().equals("")) {
                        id = r.getId();
                        update = true;
                        break;
                    }
                }

                if (update) {
                    h.setLineIndex(bomLineRecord.getLineIndex());
                    h.setSortNum(bomLineRecord.getSortNum());
                    if (id != 0L) {
                        h.setEwoNo("1");
                        h.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                        h.setPuid(reqDTO.getPuid());
                        hzBomLineRecordDao.update(h);
                    }

                }

            }

            hzBomLineRecord.setTableName(null);
//                hzBomLineRecordDao.update(hzBomLineRecord);
            Map<String, Object> map = new HashMap<>();
            map.put("projectId", reqDTO.getProjectId());
            map.put("lineID", lineId);
            map.put("lineId", lineId);
            List<HzEPLManageRecord> hzEPLManageRecords = hzEbomRecordDAO.findEbom(map);

            //pbom mbom  物料数据 也要同步更新数据
            List<HzPbomLineRecord> hzPbomLineRecords = hzPbomRecordDAO.getPbomById(map);

            List<String> type = hzMbomService.loadingCarPartType();
//            List<HzMbomLineRecord> hzMbomLineRecords = hzMbomRecordDAO.findHzMbomByPuid(map);

//            HzMaterielQuery hzMaterielQuery = new HzMaterielQuery();
//            hzMaterielQuery.setProjectId(reqDTO.getProjectId());
//            hzMaterielQuery.setMaterielResourceId(reqDTO.getPuid());
//            hzMaterielQuery.setpMaterielCode(reqDTO.getLineId());
//            List<HzMaterielRecord> hzMaterielRecords = hzMaterielDAO.findHzMaterielForList(hzMaterielQuery);

            if (type.contains(reqDTO.getpBomLinePartResource())) {
//                HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();
//                hzMbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
//                hzMbomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
//                hzMbomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
//                hzMbomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
//                hzMbomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
//                hzMbomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
//                hzMbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());
//                hzMbomLineRecord.setLineId(reqDTO.getLineId());


                HzEPLManageRecord record = new HzEPLManageRecord();
//                if (ListUtil.isEmpty(hzMbomLineRecords)) {
                    for (HzEPLManageRecord hzEPLManageRecord : hzEPLManageRecords) {
                        if (hzEPLManageRecord.getPuid().equals(reqDTO.getPuid())) {
                            record = hzEPLManageRecord;
                        }
                        break;
                    }
//                    if (record != null) {
//                        hzMbomLineRecord.setPuid(UUID.randomUUID().toString());
//                        hzMbomLineRecord.setSortNum(record.getSortNum());
//                        hzMbomLineRecord.setLineIndex(record.getLineIndex());
//                        hzMbomLineRecord.setIs2Y(record.getIs2Y());
//                        hzMbomLineRecord.setIsPart(record.getIsPart());
//                        hzMbomLineRecord.setIsHas(record.getIsHas());
//                        hzMbomLineRecord.setParentUid(record.getParentUid());
//                        hzMbomLineRecord.setIsDept(record.getIsDept());
//                        hzMbomLineRecord.seteBomPuid(record.getPuid());
//                        hzMbomLineRecord.setLinePuid(record.getLinePuid());
//                        List<HzMbomLineRecord> records = new ArrayList<>();
//                        records.add(hzMbomLineRecord);
//                        hzMbomRecordDAO.insertList(records);
//                    }
//                } else {
//                    hzMbomLineRecords.forEach(re -> {
//                        hzMbomLineRecord.seteBomPuid(re.geteBomPuid());
//                        hzMbomRecordDAO.update(hzMbomLineRecord);
//                    });
//                }
//
//                HzMaterielRecord hzMaterielRecord = HzMaterielFactory.updateHzEbomReqDTOMaterielRecord(reqDTO);
//                if (ListUtil.isNotEmpty(hzMaterielRecords)) {
//                    hzMaterielRecords.forEach(re -> {
//                        hzMaterielRecord.setPuid(re.getPuid());
//                        hzMaterielDAO.update(hzMaterielRecord);
//                    });
//                } else {
//                    List<HzMaterielRecord> records = new ArrayList<>();
//                    hzMaterielRecord.setMaterielResourceId(reqDTO.getPuid());
//                    hzMaterielRecord.setPuid(UUID.randomUUID().toString());
//                    records.add(hzMaterielRecord);
//                    hzMaterielDAO.insertList(records);
//                }

                HzPbomLineRecord hzPbomLineRecord = HzPbomRecordFactory.updateHzEbomReqDTOPbomRecord(reqDTO);
                hzPbomLineRecord.setBomDigifaxId(hzBomMainRecord.getPuid());

                if (ListUtil.isNotEmpty(hzPbomLineRecords)) {
                    hzPbomLineRecords.forEach(re -> {
                        hzPbomLineRecord.seteBomPuid(re.geteBomPuid());
                        hzPbomRecordDAO.update(hzPbomLineRecord);
                    });
                } else {
                    if(record!=null){
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
                        records.add(hzPbomLineRecord);
                        hzPbomRecordDAO.insertList(records);
                    }

                }

            }
            else {
//                if (ListUtil.isNotEmpty(hzMbomLineRecords)) {
//                    hzMbomLineRecords.forEach(hzMbomLineRecord -> {
//                        hzMbomRecordDAO.delete(hzMbomLineRecord.geteBomPuid());
//                    });
//                }
//                if (ListUtil.isNotEmpty(hzMaterielRecords)) {
//                    hzMaterielRecords.forEach(hzMaterielRecord -> {
//                        hzMaterielDAO.delete(hzMaterielRecord.getMaterielResourceId());
//                    });
//                }

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

            return OperateResultMessageRespDTO.getSuccessResult();

        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
//        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO deleteHzEbomRecordById(DeleteHzEbomReqDTO reqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            boolean b = PrivilegeUtil.writePrivilege();
            if (!b) {
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            if (reqDTO.getPuids() == null || reqDTO.getPuids().equals("") || reqDTO.getProjectId() == null || reqDTO.getProjectId().equals("")) {
                respDTO.setErrMsg("非法参数！");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            String bomPuids[] = reqDTO.getPuids().trim().split(",");
            /**关联特性的不允许删除*/
            StringBuilder sb = null;
            if (null != (sb = checkConnectWithFeature(bomPuids, reqDTO.getProjectId()))) {
                respDTO.setErrMsg(sb.toString());
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            //需要判断层级关系 并更改层级关系
            for (String puid : bomPuids) {
                HzEbomTreeQuery treeQuery = new HzEbomTreeQuery();
                treeQuery.setProjectId(reqDTO.getProjectId());
                treeQuery.setPuid(puid);
                List<HzEPLManageRecord> lineRecords = hzEbomRecordDAO.getHzBomLineChildren(treeQuery);//自己
                Set<String> set = new HashSet<>();//去除重复
                if (ListUtil.isNotEmpty(lineRecords)) {//将删除数据在历史记录表里做备份,删除前的数据状态也要做记录
                    for (int i = 0; i < lineRecords.size(); i++) {
                        Map<String, Object> after = new HashMap<>();
                        after.put("tableName", "HZ_EBOM_REOCRD_AFTER_CHANGE");
                        after.put("puid", lineRecords.get(i).getPuid());
                        after.put("projectId", reqDTO.getProjectId());

                        Map<String, Object> before = new HashMap<>();
                        before.put("tableName", "HZ_EBOM_REOCRD_BEFORE_CHANGE");
                        before.put("puid", lineRecords.get(i).getPuid());
                        before.put("projectId", reqDTO.getProjectId());

                        HzBomLineRecord record = hzBomLineRecordDao.findBomLineByPuid(after);
                        HzBomLineRecord beforeRecord = hzBomLineRecordDao.findBomLineByPuid(before);
                        if (beforeRecord == null) {//不存在 记录添加进去
                            HzEPLManageRecord be = lineRecords.get(i);
                            be.setTableName("HZ_EBOM_REOCRD_BEFORE_CHANGE");
                            hzEbomRecordDAO.insert(be);
                        } else {//存在记录 查看是否都有产生流程历史记录
                            if (beforeRecord.getEwoNo() != null && !beforeRecord.getEwoNo().equals("")) {
                                HzEPLManageRecord be = lineRecords.get(i);
                                be.setTableName("HZ_EBOM_REOCRD_BEFORE_CHANGE");
                                hzEbomRecordDAO.insert(be);
                            }
                        }

                        if (record != null && record.getEwoNo() != null && record.getEwoNo().equals("1")) {
                            if (!record.getStatus().equals(4)) {
                                record.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                                record.setStatus(4);//删除状态
                                hzBomLineRecordDao.update(record);
                            }
                        } else if (record != null && (null == record.getEwoNo() || "".equals(record.getEwoNo()))) {
                            if (!record.getStatus().equals(4)) {
                                record.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                                record.setStatus(4);//删除状态
                                hzBomLineRecordDao.update(record);
                            }
                        } else if (record == null) {
                            HzEPLManageRecord r = lineRecords.get(i);
                            r.setTableName("HZ_EBOM_REOCRD_AFTER_CHANGE");
                            r.setStatus(4);
                            hzEbomRecordDAO.insert(r);
                        }


                        HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
                        hzEbomTreeQuery.setPuid(lineRecords.get(0).getParentUid());
                        hzEbomTreeQuery.setProjectId(reqDTO.getProjectId());
                        List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);//父亲
                        if (ListUtil.isNotEmpty(records)) {
                            if (records.size() - lineRecords.size() == 1) {
                                HzEPLManageRecord hzEPLManageRecord = records.get(0);
                                HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                                hzBomLineRecord.setIsHas(0);
                                hzBomLineRecord.setIsPart(1);
//                                if (hzEPLManageRecord.getIs2Y().equals(1)) {
//                                    hzBomLineRecord.setIs2Y(0);
//                                }
                                hzBomLineRecordDao.update(hzBomLineRecord);
                            }

                        }
                        set.add(lineRecords.get(i).getPuid());
                    }
                }
                List<DeleteHzEbomReqDTO> list = new ArrayList<>();
                for (String s : set) {
                    DeleteHzEbomReqDTO deleteHzEbomReqDTO = new DeleteHzEbomReqDTO();
                    deleteHzEbomReqDTO.setPuid(s);
//                    deleteHzEbomReqDTO.setStatus(4);
                    list.add(deleteHzEbomReqDTO);
                }
                if (ListUtil.isNotEmpty(list)) {
                    hzEbomRecordDAO.deleteList(list);//mabatis 做批量更新时 返回值为-1 所以这里根据异常情况来判断成功与否
                }
            }
            return OperateResultMessageRespDTO.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
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
    public OperateResultMessageRespDTO RecoverDeleteEbomRecord(String projectId, String puid) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            if (projectId == null || projectId == "" || puid == null || puid == "") {
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                respDTO.setErrMsg("非法参数");
                return respDTO;
            }
            HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid, projectId);
            if (record != null) {
                respDTO.setErrMsg("当前要恢复对象已存在bom系统中！");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }

            record = hzEbomRecordDAO.getHasDeletedBom(puid, projectId);
            if (record != null) {
                if (record.getLineIndex().split("\\.").length == 2) {
                    respDTO.setErrMsg("2Y层结构无法恢复！");
                    respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    return respDTO;
                }
                HzEPLManageRecord manageRecord = hzEbomRecordDAO.findEbomById(record.getParentUid(), projectId);
                if (manageRecord == null) {
                    respDTO.setErrMsg("当前要恢复对象的父结构不存在，无法恢复！");
                    respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
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
                            return OperateResultMessageRespDTO.getFailResult();
                        }
                    }
                    HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
                    hzBomLineRecord.setPuid(record.getPuid());
                    int i = hzBomLineRecordDao.update(hzBomLineRecord);
                    if (i > 0) {
                        return OperateResultMessageRespDTO.getSuccessResult();
                    }
                }
            }
            return OperateResultMessageRespDTO.getFailResult();
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
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
    public OperateResultMessageRespDTO setCurrentBomAsLou(SetLouReqDTO reqDTO) {
        try {
            /**
             * 设置为LOU 或者取消设置为LOU
             */
            if (reqDTO.getLineIds() == null || reqDTO.getProjectId() == null) {
                return OperateResultMessageRespDTO.IllgalArgument();
            }
            boolean b = PrivilegeUtil.writePrivilege();
            if (!b) {
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
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
                                hzBomLineRecord.setTableName("HZ_BOM_LINE_RECORD");
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
                        record1.setTableName("HZ_BOM_LINE_RECORD");
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
            return OperateResultMessageRespDTO.getSuccessResult();
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
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

    /**
     * 检查是否关联特性
     *
     * @param puids
     * @param projectUid
     * @return
     */
    private StringBuilder checkConnectWithFeature(String[] puids, String projectUid) {
        HzFullCfgMain hzFullCfgMain = hzFullCfgMainDao.selectByProjectId(projectUid);
        HzExFullCfgWithCfg cfg = null;
        StringBuilder sb = null;
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
}
