package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.bomSystem.service.cfg.*;
import com.connor.hozon.bom.bomSystem.service.integrate.SynBomService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.interaction.iservice.IHzConfigBomColorService;
import com.connor.hozon.bom.resources.domain.dto.request.AddMbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzMbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.SetLouReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateMbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.*;
import com.connor.hozon.bom.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.bom.resources.domain.model.HzMbomRecordFactory;
import com.connor.hozon.bom.resources.domain.query.*;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzMbomRecordDAOImpl;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.AddMbomThread;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.apache.xerces.xs.datatypes.ObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomLineRecordVO;
import sql.pojo.cfg.HzCfg0OfBomLineRecord;
import sql.pojo.cfg.HzCfg0Record;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.factory.HzFactory;
import sql.pojo.interaction.HzConfigBomColorBean;
import sql.redis.SerializeUtil;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.connor.hozon.bom.resources.domain.model.HzBomSysFactory.getLevelAndRank;


/**
 * @Author: haozt
 * @Date: 2018/6/20
 * @Description:
 */
@Service("HzMbomService")
public class HzMbomServiceImpl implements HzMbomService{

    @Autowired
    SynBomService synBomService;

    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzFactoryDAO hzFactoryDAO;

    @Autowired
    private HzCfg0OfBomLineService hzCfg0OfBomLineService;

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    private IHzConfigBomColorService iHzConfigBomColorService;

    @Override
    public Page<HzMbomRecordRespDTO> findHzMbomForPage(HzMbomByPageQuery query) {
        try {
            String level = query.getLevel();
            if (level != null && level!="") {
                if(level.trim().toUpperCase().endsWith("Y")){
                    int length = Integer.valueOf(level.replace("Y",""));
                    query.setIsHas(1);
                    query.setLineIndex(String.valueOf(length-1));
                }else {
                    query.setIsHas(0);
                    int length = Integer.valueOf(level.trim());
                    query.setLineIndex(String.valueOf(length));
                }
            }

            Page<HzMbomLineRecord> recordPage = hzMbomRecordDAO.findMbomForPage(query);
            int num = (recordPage.getPageNumber() - 1) * recordPage.getPageSize();
            if (recordPage == null || recordPage.getResult() == null) {
                return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), 0);
            }
            List<HzMbomLineRecord> lineRecords = recordPage.getResult();
            List<HzMbomRecordRespDTO> respDTOList = new ArrayList<>();
            for (HzMbomLineRecord record : lineRecords) {
                HzMbomRecordRespDTO respDTO = HzMbomRecordFactory.mbomRecordToRespDTO(record);
                respDTO.setNo(++num);
                if(Integer.valueOf(1).equals(query.getType())){
                    respDTO.setpBomType("生产");
                }else if(Integer.valueOf(6).equals(query.getType())){
                    respDTO.setpBomType("财务");
                }
                if (record.getpFactoryId() != null && record.getpFactoryId() != "") {
                    HzFactory hzFactory = hzFactoryDAO.findFactory(record.getpFactoryId(), "");
                    if (hzFactory != null) {
                        respDTO.setpFactoryCode(hzFactory.getpFactoryCode());
                    } else {
                        respDTO.setpFactoryCode("1001");
                    }
                } else {
                    respDTO.setpFactoryCode("1001");
                }
                respDTOList.add(respDTO);
            }
            return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), recordPage.getTotalCount(), null);
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public HzMbomRecordRespDTO findHzMbomByPuid(String projectId, String puid) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("projectId", projectId);
            map.put("pPuid", puid);
            List<HzMbomLineRecord> records = hzMbomRecordDAO.findHzMbomByPuid(map);
            if (ListUtil.isNotEmpty(records)) {
                HzMbomLineRecord record = records.get(0);
                HzMbomRecordRespDTO respDTO = HzMbomRecordFactory.mbomRecordToRespDTO(record);
                return respDTO;
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public OperateResultMessageRespDTO insertMbomRecord(AddMbomReqDTO reqDTO) {
        try {
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            HzMbomLineRecord hzMbomRecord = new HzMbomLineRecord();
            hzMbomRecord.seteBomPuid(reqDTO.geteBomPuid());
            hzMbomRecord.setChange(reqDTO.getChange());
            hzMbomRecord.setChangeNum(reqDTO.getChangeNum());
            hzMbomRecord.setLaborHour(reqDTO.getLaborHour());
            hzMbomRecord.setMachineMaterial(reqDTO.getMachineMaterial());
            hzMbomRecord.setProcessRoute(reqDTO.getProcessRoute());
            hzMbomRecord.setRhythm(reqDTO.getRhythm());
            hzMbomRecord.setSolderJoint(reqDTO.getSolderJoint());
            hzMbomRecord.setSparePart(reqDTO.getSparePart());
            hzMbomRecord.setSparePartNum(reqDTO.getSparePartNum());
            hzMbomRecord.setStandardPart(reqDTO.getStandardPart());
            hzMbomRecord.setTools(reqDTO.getTools());
            hzMbomRecord.setWasterProduct(reqDTO.getWasterProduct());
            hzMbomRecord.setCreateName(UserInfo.getUser().getUserName());
            hzMbomRecord.setUpdateName(UserInfo.getUser().getUserName());
            Map<String, Object> map = new HashMap<>();
            map.put("pPuid", reqDTO.geteBomPuid());
            map.put("projectId", reqDTO.getProjectId());
            List<HzMbomLineRecord> lineRecords = hzMbomRecordDAO.findHzMbomByPuid(map);
            if (ListUtil.isNotEmpty(lineRecords)) {
                int i = hzMbomRecordDAO.update(hzMbomRecord);
                if (i > 0) {
                    //什么时候发送到SAP？单独新增2Y不用ECN，修改原有的BOM结构应该有ECN，新增2Y层则不传输
                    return OperateResultMessageRespDTO.getSuccessResult();
                } else {
                    return OperateResultMessageRespDTO.getFailResult();
                }
            }
            return OperateResultMessageRespDTO.getFailResult();
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public OperateResultMessageRespDTO updateMbomRecord(UpdateMbomReqDTO reqDTO) {
        try {
            User user = UserInfo.getUser();
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            HzMbomLineRecord record = new HzMbomLineRecord();
            if(Integer.valueOf(1).equals(reqDTO.getType())){
                record.setTableName("HZ_MBOM_OF_PRODUCT");
            }else if(Integer.valueOf(6).equals(reqDTO.getType())){
                record.setTableName("HZ_MBOM_OF_FINANCE");
            }else {
                record.setTableName("HZ_MBOM_RECORD");
            }
            record.setUpdateName(user.getUserName());
            record.setWasterProduct(reqDTO.getWasterProduct());
            record.setTools(reqDTO.getTools());
            record.setStandardPart(reqDTO.getStandardPart());
            record.setSparePartNum(reqDTO.getSparePartNum());
            record.setSolderJoint(reqDTO.getSolderJoint());
            record.setRhythm(reqDTO.getRhythm());
            record.setProcessRoute(reqDTO.getProcessRoute());
            record.setMachineMaterial(reqDTO.getMachineMaterial());
            record.setLaborHour(reqDTO.getLaborHour());
            record.setChangeNum(reqDTO.getChangeNum());
            record.setChange(reqDTO.getChange());
            record.setSparePart(reqDTO.getSparePart());
            record.seteBomPuid(reqDTO.geteBomPuid());
            if (!reqDTO.getpFactoryCode().equals("") && null != reqDTO.getpFactoryCode()) {
                HzFactory hzFactory = hzFactoryDAO.findFactory("", reqDTO.getpFactoryCode());
                if (hzFactory == null) {
                    String puid = UUID.randomUUID().toString();
                    hzFactory = new HzFactory();
                    hzFactory.setPuid(puid);
                    hzFactory.setpFactoryCode(reqDTO.getpFactoryCode());
                    hzFactory.setpUpdateName(user.getUserName());
                    hzFactory.setpCreateName(user.getUserName());
                    int i = hzFactoryDAO.insert(hzFactory);
                    if (i < 0) {
                        return OperateResultMessageRespDTO.getFailResult();
                    }
                    record.setpFactoryId(puid);
                } else {
                    record.setpFactoryId(hzFactory.getPuid());
                }
            }
            if (reqDTO.getpBomType().equals("生产")) {
                record.setpBomType(1);
            } else if (reqDTO.getpBomType().equals("财务")) {
                record.setpBomType(6);
            } else {
                record.setpBomType(0);
            }
            record.setpStockLocation(reqDTO.getpStockLocation());
            int i = hzMbomRecordDAO.update(record);
            if (i > 0) {
                //更新后传输到SAP
                //synBomService.updateByUids(record.getPuid(), record.getProjectPuid());
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO deleteMbomRecord(DeleteHzMbomReqDTO reqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            if (reqDTO.getPuids() == null || reqDTO.getPuids().equals("") || reqDTO.getProjectId() == null || reqDTO.getProjectId().equals("")) {
                respDTO.setErrMsg("非法参数！");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            String bomPuids[] = reqDTO.getPuids().trim().split(",");
            //需要判断层级关系 并更改层级关系
            for (String puid : bomPuids) {
                HzMbomTreeQuery treeQuery = new HzMbomTreeQuery();
                treeQuery.setProjectId(reqDTO.getProjectId());
                treeQuery.setPuid(puid);
                if(Integer.valueOf(1).equals(reqDTO.getType())){
                    treeQuery.setTableName("HZ_MBOM_OF_PRODUCT");
                }else if(Integer.valueOf(6).equals(reqDTO.getType())){
                    treeQuery.setTableName("HZ_MBOM_OF_FINANCE");
                }

                List<HzMbomLineRecord> lineRecords = hzMbomRecordDAO.getHzMbomTree(treeQuery);//自己
                Set<String> set = new HashSet<>();//去除重复
                if (ListUtil.isNotEmpty(lineRecords)) {
                    for (int i = 0; i < lineRecords.size(); i++) {
                        HzMbomTreeQuery hzMbomTreeQuery = new HzMbomTreeQuery();
                        hzMbomTreeQuery.setPuid(lineRecords.get(0).getParentUid());
                        hzMbomTreeQuery.setProjectId(reqDTO.getProjectId());
                        List<HzMbomLineRecord> records = hzMbomRecordDAO.getHzMbomTree(hzMbomTreeQuery);//父亲
                        if (ListUtil.isNotEmpty(records)) {
                            if (records.size() - lineRecords.size() == 1) {
                                HzMbomLineRecord hzMbomLineRecord = records.get(0);
                                hzMbomLineRecord.setIsHas(0);
                                hzMbomLineRecord.setIsPart(1);
                                hzMbomRecordDAO.update(hzMbomLineRecord);
                            }

                        }
                        set.add(lineRecords.get(i).geteBomPuid());
                    }
                }
                List<DeleteHzMbomReqDTO> list = new ArrayList<>();
                for (String s : set) {
                    DeleteHzMbomReqDTO deleteHzPbomReqDTO = new DeleteHzMbomReqDTO();
                    deleteHzPbomReqDTO.setPuid(s);
                    if(Integer.valueOf(1).equals(reqDTO.getType())){
                        deleteHzPbomReqDTO.setTableName("HZ_MBOM_OF_PRODUCT");
                    }else if(Integer.valueOf(6).equals(reqDTO.getType())){
                        deleteHzPbomReqDTO.setTableName("HZ_MBOM_OF_FINANCE");
                    }else {
                        deleteHzPbomReqDTO.setTableName("HZ_MBOM_RECORD");
                    }
                    list.add(deleteHzPbomReqDTO);
                }
                if (ListUtil.isNotEmpty(list)) {
                    hzMbomRecordDAO.deleteList(list);//mabatis 做批量更新时 返回值为-1 所以这里根据异常情况来判断成功与否
                    //将删除数据发送到SAP
                    List<String> puids = new ArrayList<>();
                    list.stream().filter(l -> l.getPuid() != null).forEach(l -> puids.add(l.getPuid()));
                    synBomService.deleteByUids(reqDTO.getProjectId(), puids);
                }

            }
            return OperateResultMessageRespDTO.getSuccessResult();
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public List<HzMbomLineRecord> getHzMbomTree(HzMbomTreeQuery query) {
        return hzMbomRecordDAO.getHzMbomTree(query);
    }

    @Override
    public Page<HzMbomRecordRespDTO> getHzMbomRecycleByPage(HzBomRecycleByPageQuery query) {
        try {

            Page<HzMbomLineRecord> recordPage = hzMbomRecordDAO.getHzMbomRecycleRecord(query);
            int num = (recordPage.getPageNumber() - 1) * recordPage.getPageSize();
            if (recordPage == null || recordPage.getResult() == null) {
                return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), 0);
            }
            List<HzMbomLineRecord> lineRecords = recordPage.getResult();
            List<HzMbomRecordRespDTO> respDTOList = new ArrayList<>();
            for (HzMbomLineRecord record : lineRecords) {
                HzMbomRecordRespDTO respDTO = HzMbomRecordFactory.mbomRecordToRespDTO(record);
                respDTO.setNo(++num);
                respDTO.setpFactoryCode("1001");
                respDTOList.add(respDTO);
            }
            return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), recordPage.getTotalCount(), respDTOList);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public OperateResultMessageRespDTO RecoverDeleteMbomRecord(String projectId, String puid) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            if (projectId == null || projectId == "" || puid == null || puid == "") {
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                respDTO.setErrMsg("非法参数");
                return respDTO;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("projectId", projectId);
            map.put("pPuid", puid);
            List<HzMbomLineRecord> records = hzMbomRecordDAO.findHzMbomByPuid(map);
            if (ListUtil.isNotEmpty(records)) {
                respDTO.setErrMsg("当前要恢复对象已存在bom系统中！");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            map.put("status", 0);//已删除的bom
            records = hzMbomRecordDAO.findHzMbomByPuid(map);
            if (ListUtil.isNotEmpty(records)) {
                if (records.get(0).getLineIndex().split("\\.").length == 2) {
                    respDTO.setErrMsg("2或者2Y层结构无法恢复！");
                    respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    return respDTO;
                }
                Map<String, Object> map1 = new HashMap<>();
                map1.put("projectId", projectId);
                map1.put("pPuid", records.get(0).getParentUid());
                List<HzMbomLineRecord> mbomLineRecords = hzMbomRecordDAO.findHzMbomByPuid(map1);
                if (ListUtil.isEmpty(mbomLineRecords)) {
                    respDTO.setErrMsg("当前要恢复对象的父结构不存在，无法恢复！");
                    respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    return respDTO;
                } else {
                    if (mbomLineRecords.get(0).getIsHas().equals(0)) {
                        mbomLineRecords.get(0).setIsHas(1);
                        mbomLineRecords.get(0).setIsPart(0);
                        if (mbomLineRecords.get(0).getLineIndex().split("\\.").length == 2 && mbomLineRecords.get(0).getIs2Y().equals(0)) {
                            mbomLineRecords.get(0).setIs2Y(1);
                        }
                        int i = hzMbomRecordDAO.update(mbomLineRecords.get(0));
                        if (i <= 0) {
                            return OperateResultMessageRespDTO.getFailResult();
                        }
                    }
                    int i = hzMbomRecordDAO.recoverBomById(records.get(0).geteBomPuid());
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
    public List<String> loadingCarPartType() {
        List<String> list = new ArrayList<>();
        list.add("采购件");
        list.add("自制总成");
        list.add("自制单件");
        list.add("自用标准件");
        list.add("自用非标件");
        list.add("虚拟总成");
        return list;
    }

    @Override
    public OperateResultMessageRespDTO setCurrentBomToLou(SetLouReqDTO reqDTO) {
        try {
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
                map.put("lineId",lineId);
                map.put("projectId",reqDTO.getProjectId());
                List<HzMbomLineRecord> mbomList  =  hzMbomRecordDAO.findHzMbomByPuid(map);
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
    public HzMbomLineRecord findParentBomUtil2Y(String projectId, String puid) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("pPuid",puid);
        List<HzMbomLineRecord> records = hzMbomRecordDAO.findHzMbomByPuid(map);
        if(ListUtil.isNotEmpty(records)){
            if(records.get(0).getIs2Y().equals(1)){
                return records.get(0);
            }else if(records.get(0).getParentUid() == null){
                return records.get(0);
            }else {
                return  findParentBomUtil2Y(projectId,records.get(0).getParentUid());
            }
        }else {
            return null;
        }
    }


    @Override
    public HzLouRespDTO getHzLouInfoById(HzLouaQuery query) {
        try {
            HzLouRespDTO respDTO = new HzLouRespDTO();
            HzMbomLineRecord hzBomLineRecord = findParentBomUtil2Y(query.getProjectId(),query.getPuid());
            if(hzBomLineRecord != null){
                HzCfg0OfBomLineRecord record = hzCfg0OfBomLineService.doSelectByBLUidAndPrjUid(query.getProjectId(),hzBomLineRecord.geteBomPuid());
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

    @Override
    public boolean refreshHzMbom(String projectId) {
        try {
            Double sortNumber = 0.0;
            //颜色件 非颜色件
            List<HzEPLManageRecord> records = hzEbomRecordDAO.getAll2YBomRecord(projectId,null);//全部的2Y层
            //S00-1001YY0->S00-1001BA/S00-1001BB       1001  1001AAAA
            //类型  1生产 6 财务
            if(ListUtil.isNotEmpty(records)){
                List<HzMbomLineRecord> superMboms = new ArrayList<>();//超级MBOM
                List<HzMbomLineRecord> productMboms = new ArrayList<>();//生产用MBOM
                List<HzMbomLineRecord> financeMboms = new ArrayList<>();//财务用MBOM
                for(HzEPLManageRecord record:records){
                    Set<HzEPLManageRecord> bodyOfWhiteSet = new HashSet<>();

                    if(Integer.valueOf(1).equals(record.getColorPart())){//是颜色件 找出对应的颜色 多色时，需要乘以颜色信息
                        List<HzConfigBomColorBean> beans = iHzConfigBomColorService.doSelectBy2YUidWithProject(record.getPuid(), projectId);
                        if(ListUtil.isNotEmpty(beans)){
                            for(HzConfigBomColorBean bean:beans){
                                if(null == bean.getColorCode()){
                                    beans.remove(bean);
                                }
                            }
                        }

                        if(ListUtil.isNotEmpty(beans)){//找到对应的颜色件
                            for(int i =0;i<beans.size();i++){
                                analysisMbom(record,i,projectId,bodyOfWhiteSet,beans,superMboms);
                            }
                        }else {
                            analysisMbom(record,0,projectId,bodyOfWhiteSet,null,superMboms);
                        }

                    }else {
                        analysisMbom(record,0,projectId,bodyOfWhiteSet,null,superMboms);
                    }

                    if(ListUtil.isNotEmpty(bodyOfWhiteSet)){//白车身总成
                        for(HzEPLManageRecord eplManageRecord :bodyOfWhiteSet){
                            HzEbomTreeQuery ebomTreeQuery = new HzEbomTreeQuery();//找出白车身的全部子件
                            ebomTreeQuery.setProjectId(projectId);
                            ebomTreeQuery.setPuid(eplManageRecord.getPuid());
                            ebomTreeQuery.setIsCarPart(1);
                            List<HzEPLManageRecord> whiteBodys = hzEbomRecordDAO.getHzBomLineChildren(ebomTreeQuery);
                            int m = 0;
                            if(ListUtil.isNotEmpty(whiteBodys)){
                                for(int j =0;j<whiteBodys.size();j++){
                                    HzEPLManageRecord whiteBody = whiteBodys.get(j);
                                    HzMbomLineRecord mbomRecord = HzMbomRecordFactory.ebomRecordToMbomRecord(whiteBody);
                                    String lineIndex = whiteBody.getLineIndex();
                                    String firstIndex = lineIndex.split("\\.")[0];
                                    String othersIndex = lineIndex.substring(firstIndex.length(),lineIndex.length());
                                    String sortNum = whiteBody.getSortNum();
                                    mbomRecord.setSortNum(String.valueOf(sortNumber+Double.parseDouble(sortNum)+100));
                                    String lineId = HzBomSysFactory.resultLineId(mbomRecord.getLineId(),projectId);

                                    mbomRecord.setLineId(lineId);

                                    if(whiteBody.getpBomLinePartName().contains("白车身总成")){
                                        Integer first = Integer.valueOf(firstIndex+Integer.valueOf(firstIndex));
                                        mbomRecord.setLineIndex(String.valueOf(first)+othersIndex);
                                        mbomRecord.setParentUid("");
                                        financeMboms.add(mbomRecord);
                                        productMboms.add(mbomRecord);
                                    }else if(whiteBody.getpBomLinePartResource().equals("采购件")){
                                        mbomRecord.setParentUid(eplManageRecord.getPuid());
                                        mbomRecord.setLineIndex(eplManageRecord.getLineIndex()+"."+(10+(m*10)));
                                        mbomRecord.setIsHas(0);
                                        m+=1;
                                        productMboms.add(mbomRecord);
                                        HzEbomTreeQuery query = new HzEbomTreeQuery();
                                        query.setProjectId(projectId);
                                        query.setPuid(whiteBody.getPuid());
                                        query.setIsCarPart(1);
                                        List<HzEPLManageRecord> buyRecords = hzEbomRecordDAO.getHzBomLineChildren(query);
                                        if(ListUtil.isNotEmpty(buyRecords)){
                                            j+=buyRecords.size()-1;
                                        }

                                    }else if(whiteBody.getpBomLinePartResource().equals("自制单件")){
                                        mbomRecord.setParentUid(eplManageRecord.getPuid());
                                        mbomRecord.setLineIndex(eplManageRecord.getLineIndex()+"."+(10+(m*10)));
                                        mbomRecord.setIsHas(0);
                                        m+=1;
                                        HzEbomTreeQuery query = new HzEbomTreeQuery();
                                        query.setProjectId(projectId);
                                        query.setPuid(whiteBody.getPuid());
                                        query.setIsCarPart(1);
                                        List<HzEPLManageRecord> makeRecords = hzEbomRecordDAO.getHzBomLineChildren(query);
                                        if(ListUtil.isNotEmpty(makeRecords) && makeRecords.size()>1){
                                            mbomRecord.setIsHas(1);
                                            productMboms.add(mbomRecord);
                                            HzMbomRecordFactory factory = new HzMbomRecordFactory();
                                            productMboms.addAll(factory.movePartBomStructureToThis(mbomRecord,makeRecords));
                                            sortNumber = factory.getMaxSortNum();
                                            j+=makeRecords.size()-1;
                                        }
                                    }else {
                                        Integer first = Integer.valueOf(firstIndex+Integer.valueOf(firstIndex));
                                        mbomRecord.setLineIndex(String.valueOf(first)+othersIndex);
                                        financeMboms.add(mbomRecord);
                                    }
                                }
                            }
                        }
                    }

                }

                HzMbomLineRecordVO superMbom = new HzMbomLineRecordVO();
                HzMbomLineRecordVO productMbom = new HzMbomLineRecordVO();
                HzMbomLineRecordVO financeMbom = new HzMbomLineRecordVO();
                Map<Integer,HzMbomLineRecordVO> listMap = new HashMap<>();
                if(ListUtil.isNotEmpty(superMboms)){
                    superMbom.setRecordList(superMboms);
                    superMbom.setTableName("HZ_MBOM_RECORD");
                    listMap.put(1,superMbom);
                }
                if(ListUtil.isNotEmpty(productMboms)){
                    productMbom.setTableName("HZ_MBOM_OF_PRODUCT");
                    productMbom.setRecordList(productMboms);
                    listMap.put(2,productMbom);
                }
                if(ListUtil.isNotEmpty(financeMboms)){
                    financeMbom.setRecordList(financeMboms);
                    financeMbom.setTableName("HZ_MBOM_OF_FINANCE");
                    listMap.put(3,financeMbom);
                }

                List<Thread> threads = new ArrayList<>();
                CountDownLatch countDownLatch = new CountDownLatch(listMap.size());
                for(Map.Entry<Integer,HzMbomLineRecordVO> entry: listMap.entrySet()){
                    AddMbomThread thread = new AddMbomThread(countDownLatch) {
                        @Override
                        public void insertMbom() {
                            hzMbomRecordDAO.insertVO(entry.getValue());
                        }
                    };
                    threads.add(new Thread(thread));
                }

                for(Thread thread:threads){
                    thread.start();
                }
                try {
                    // 等待中 等子线程全部ok 继续
                    countDownLatch.await();
                }catch (Exception e){
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }

    }




    public void analysisMbom(HzEPLManageRecord record,int i,String projectId,Set<HzEPLManageRecord> bodyOfWhiteSet,
                             List<HzConfigBomColorBean> beans,List<HzMbomLineRecord> superMboms){
        //解析产生油漆车身总成 在解析其他bom  油漆车身需要产生白车身
        if(record.getpBomLinePartName().contains("油漆车身总成") && record.getIs2Y().equals(1)){
            //找出全部的子件 是颜色件的需要乘以颜色
            //找出对应的油漆车身超级MBOM 以及对应白车身
            HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();//找出油漆车身的全部子件
            hzEbomTreeQuery.setProjectId(projectId);
            hzEbomTreeQuery.setPuid(record.getPuid());
            hzEbomTreeQuery.setIsCarPart(1);
            List<HzEPLManageRecord> recordList = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);

            if(ListUtil.isNotEmpty(recordList)){//lineId lineIndex sortNum

                for(HzEPLManageRecord eplManageRecord:recordList){
                    if(eplManageRecord.getpBomLinePartName().contains("白车身总成")){
                        bodyOfWhiteSet.add(eplManageRecord);
                    }
                }

                //超级MBOM包含除白车身以外的bom,除去白车身BOM
                if(ListUtil.isNotEmpty(bodyOfWhiteSet)){
                    for(HzEPLManageRecord bodyOfWhite :bodyOfWhiteSet){
                        HzEbomTreeQuery q = new HzEbomTreeQuery();
                        q.setProjectId(projectId);
                        q.setIsCarPart(1);
                        q.setPuid(bodyOfWhite.getPuid());
                        List<HzEPLManageRecord> rs = hzEbomRecordDAO.getHzBomLineChildren(q);
                        if(rs.get(0).getpBomLinePartName().contains("白车身总成")) {
                            rs.remove(rs.get(0));//白车身总成不需要去重
                        }
                        if(ListUtil.isNotEmpty(rs)){

                            Collection a  = new ArrayList(recordList);
                            Collection b = new ArrayList(rs);
                            a.removeAll(b);
                            if(a instanceof List){
                                recordList = (List)a;
                            }else {
                                recordList = new ArrayList<>(a);
                            }
                        }
                    }
                }


                if(ListUtil.isNotEmpty(recordList)){//超级MBOM
                    for(HzEPLManageRecord eplManageRecord :recordList){
                        HzMbomLineRecord mbomLineRecord = HzMbomRecordFactory.generateSupMbom(eplManageRecord,i,projectId,beans);
                        superMboms.add(mbomLineRecord);
                    }
                }
            }
        }else {//非油漆车身总成下面的件 直接加到超级MBOM
            HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();//找出油漆车身的全部子件
            hzEbomTreeQuery.setProjectId(projectId);
            hzEbomTreeQuery.setPuid(record.getPuid());
            hzEbomTreeQuery.setIsCarPart(1);
            List<HzEPLManageRecord> recordList = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);
            if(ListUtil.isNotEmpty(recordList)){
                for(HzEPLManageRecord eplManageRecord:recordList){
                    HzMbomLineRecord mbomLineRecord = HzMbomRecordFactory.generateSupMbom(eplManageRecord,i,projectId,beans);
                    superMboms.add(mbomLineRecord);
                }

            }

        }
    }

}
