package com.connor.hozon.bom.resources.service.bom.impl;

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
import com.connor.hozon.bom.resources.domain.model.HzMaterielFactory;
import com.connor.hozon.bom.resources.domain.model.HzMbomRecordFactory;
import com.connor.hozon.bom.resources.domain.query.*;
import com.connor.hozon.bom.resources.enumtype.MbomTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.RefreshMbomThread;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomLineRecordVO;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.cfg.HzCfg0OfBomLineRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.factory.HzFactory;
import sql.pojo.interaction.HzConfigBomColorBean;
import sql.pojo.project.HzMaterielRecord;

import java.util.*;
import java.util.concurrent.CountDownLatch;

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

    @Autowired
    private HzMaterielDAO hzMaterielDAO;

    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;
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
            return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), recordPage.getTotalCount(), respDTOList);
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public HzMbomRecordRespDTO findHzMbomByPuid(HzMbomByIdQuery query) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("projectId", query.getProjectId());
            map.put("pPuid", query.getPuid());
            map.put("tableName",MbomTableNameEnum.tableName(query.getType()));
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
            record.setTableName(MbomTableNameEnum.tableName(reqDTO.getType()));
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
                treeQuery.setTableName(MbomTableNameEnum.tableName(reqDTO.getType()));

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
                    deleteHzPbomReqDTO.setTableName(MbomTableNameEnum.tableName(reqDTO.getType()));
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



    /**
     * 配色方案发生变更时，需要和进行数据同步
     * 这里的数据源直接从PBOM获取数据
     * 1.分超级MBOM，白车身生产MBOM，白车身财务MBOM 3种
     * 2.每种BOM可分为3种情况进行数据同步
     * 2.1查询数据，找到原来的数据，若存在，则进行数据更新
     * 2.2若不存在，则进行数据的添加
     * 2.3找出数据库中全部的数据，与2.1与2.2的和求差集，剩余数据需要进行删除
     * 3.MBOM中新增的数据 需要去重后进入物料数据
     * 4.MBOM中更新的数据，需要去重后再物料中进行更新
     * 5.MBOM中删除的数据，需要在物料中进行删除
     * 6.鉴于同步数据量大，操作的种类较多，使用多线程进行数据同步
     */

    @Override
    public OperateResultMessageRespDTO refreshHzMbom(String projectId) {
        if(null == projectId || ""==projectId){
            return OperateResultMessageRespDTO.IllgalArgument();
        }
        try {
            Double sortNumber = 0.0;
            //颜色件 非颜色件
            List<HzPbomLineRecord> records = hzPbomRecordDAO.getAll2YBomRecord(projectId);//全部的2Y层
            //S00-1001YY0->S00-1001BA/S00-1001BB       1001  1001AAAA
            //类型  1生产 6 财务
            if(ListUtil.isNotEmpty(records)){
                List<HzMbomLineRecord> superMboms = new ArrayList<>();//超级MBOM 新增
                List<HzMbomLineRecord> productMboms = new ArrayList<>();//生产用MBOM 新增
                List<HzMbomLineRecord> financeMboms = new ArrayList<>();//财务用MBOM 新增

                List<HzMbomLineRecord> updateSuperMboms = new ArrayList<>();//超级MBOM 更新
                List<HzMbomLineRecord> updateProductMboms = new ArrayList<>();//生产用MBOM 更新
                List<HzMbomLineRecord> updateFinanceMboms = new ArrayList<>();//财务用MBOM 更新

                List<HzMbomLineRecord> deleteSuperMboms = new ArrayList<>();//超级MBOM 删除
                List<HzMbomLineRecord> deleteProductMboms = new ArrayList<>();//生产用MBOM 删除
                List<HzMbomLineRecord> deleteFinanceMboms = new ArrayList<>();//财务用MBOM 删除

                Set<HzMaterielRecord> materielRecords = new HashSet<>();//物料数据 新增
                Set<HzMaterielRecord> updateMaterielRecords = new HashSet<>();//物料数据 更新
                Set<HzMaterielRecord> deleteMaterielRecords = new HashSet<>();//物料数据 删除


                for(HzPbomLineRecord record:records){
                    Set<HzPbomLineRecord> bodyOfWhiteSet = new HashSet<>();
                    boolean b = false;
                    if(Integer.valueOf(1).equals(record.getColorPart())){//是颜色件 找出对应的颜色 多色时，需要乘以颜色信息
                        List<HzConfigBomColorBean> beans = iHzConfigBomColorService.doSelectBy2YUidWithProject(record.geteBomPuid(), projectId);
                        if(ListUtil.isNotEmpty(beans)){
                            for(int i =0;i<beans.size();i++){
                                if(null == beans.get(i).getColorCode()){
                                    beans.remove(beans.get(i));
                                }
                            }
                        }


                        if(ListUtil.isNotEmpty(beans)){//找到对应的颜色件
                            for(int i =0;i<beans.size();i++){
                                b = analysisMbom(record,i,projectId,bodyOfWhiteSet,beans,superMboms);
                            }
                        }else {
                            b= analysisMbom(record,0,projectId,bodyOfWhiteSet,null,superMboms);
                        }

                    }else {
                        b=analysisMbom(record,0,projectId,bodyOfWhiteSet,null,superMboms);
                    }
                    if(!b){
                        OperateResultMessageRespDTO respDTO  = new OperateResultMessageRespDTO();
                        respDTO.setErrMsg("BOM结构不正确，油漆车身下面必须挂有白车身总成！");
                        respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                        return respDTO;
                    }
                    if(ListUtil.isNotEmpty(bodyOfWhiteSet)){//白车身总成
                        for(HzPbomLineRecord pbomLineRecord :bodyOfWhiteSet){
                            HzPbomTreeQuery ebomTreeQuery = new HzPbomTreeQuery();//找出白车身的全部子件
                            ebomTreeQuery.setProjectId(projectId);
                            ebomTreeQuery.setPuid(pbomLineRecord.geteBomPuid());
                            List<HzPbomLineRecord> whiteBodys = hzPbomRecordDAO.getHzPbomTree(ebomTreeQuery);
                            int m = 0;
                            if(ListUtil.isNotEmpty(whiteBodys)){
                                for(int j =0;j<whiteBodys.size();j++){
                                    HzPbomLineRecord whiteBody = whiteBodys.get(j);
                                    HzMbomLineRecord mbomRecord = HzMbomRecordFactory.pBomRecordToMbomRecord(whiteBody);
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
                                        mbomRecord.setParentUid(pbomLineRecord.geteBomPuid());
                                        mbomRecord.setLineIndex(pbomLineRecord.getLineIndex()+"."+(10+(m*10)));
                                        mbomRecord.setIsHas(0);
                                        m+=1;
                                        productMboms.add(mbomRecord);
                                        HzPbomTreeQuery query = new HzPbomTreeQuery();
                                        query.setProjectId(projectId);
                                        query.setPuid(whiteBody.geteBomPuid());
                                        List<HzPbomLineRecord> buyRecords = hzPbomRecordDAO.getHzPbomTree(query);
                                        if(ListUtil.isNotEmpty(buyRecords)){
                                            j+=buyRecords.size()-1;
                                        }

                                    }else if(whiteBody.getpBomLinePartResource().equals("自制单件")){
                                        mbomRecord.setParentUid(pbomLineRecord.geteBomPuid());
                                        mbomRecord.setLineIndex(pbomLineRecord.getLineIndex()+"."+(10+(m*10)));
                                        mbomRecord.setIsHas(0);
                                        m+=1;
                                        HzPbomTreeQuery query = new HzPbomTreeQuery();
                                        query.setProjectId(projectId);
                                        query.setPuid(whiteBody.geteBomPuid());
                                        productMboms.add(mbomRecord);
                                        List<HzPbomLineRecord> makeRecords = hzPbomRecordDAO.getHzPbomTree(query);
                                        if(ListUtil.isNotEmpty(makeRecords) && makeRecords.size()>1){
                                            mbomRecord.setIsHas(1);
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


                List<HzMbomLineRecord> mbomLineRecords = hzMbomRecordDAO.findHzMbomAll(projectId,MbomTableNameEnum.tableName(0));
                List<HzMbomLineRecord> productMbomLineRecords = hzMbomRecordDAO.findHzMbomAll(projectId,MbomTableNameEnum.tableName(1));
                List<HzMbomLineRecord> financeMbomLineRecords = hzMbomRecordDAO.findHzMbomAll(projectId,MbomTableNameEnum.tableName(6));



                refreshResult(superMboms,mbomLineRecords,updateSuperMboms,deleteSuperMboms,0);
                refreshResult(productMboms,productMbomLineRecords,updateProductMboms,deleteProductMboms,1);
                refreshResult(financeMboms,financeMbomLineRecords,updateFinanceMboms,deleteFinanceMboms,6);


                List<HzMbomLineRecord> allMbomWillBeAddToDBList = new ArrayList<>();//全部的即将插入表MBOM总和
                allMbomWillBeAddToDBList.addAll(superMboms);
                allMbomWillBeAddToDBList.addAll(productMboms);
                allMbomWillBeAddToDBList.addAll(financeMboms);

                List<HzMbomLineRecord> allMbomWillBeUpdateToDBList = new ArrayList<>();//全部的即将更新表MBOM总和
                allMbomWillBeUpdateToDBList.addAll(updateSuperMboms);
                allMbomWillBeUpdateToDBList.addAll(updateProductMboms);
                allMbomWillBeUpdateToDBList.addAll(updateFinanceMboms);


                if(ListUtil.isNotEmpty(allMbomWillBeAddToDBList)){
                    for(HzMbomLineRecord record :allMbomWillBeAddToDBList){
                        HzMaterielRecord hzMaterielRecord = HzMaterielFactory.mbomRecordToMaterielRecord(projectId,record);
                        materielRecords.add(hzMaterielRecord);
                    }
                }
                if(ListUtil.isNotEmpty(allMbomWillBeUpdateToDBList)){
                    HzMaterielQuery hzMaterielQuery = new HzMaterielQuery();
                    hzMaterielQuery.setProjectId(projectId);
                    for(HzMbomLineRecord record:allMbomWillBeUpdateToDBList){
                        HzMaterielRecord hzMaterielRecord = HzMaterielFactory.mbomRecordToMaterielRecord(projectId,record);
                        hzMaterielQuery.setpMaterielCode(hzMaterielRecord.getpMaterielCode());
                        List<HzMaterielRecord> recordList = hzMaterielDAO.findHzMaterielForList(hzMaterielQuery);
                        if(ListUtil.isNotEmpty(recordList)){
                            hzMaterielRecord.setPuid(recordList.get(0).getPuid());
                            updateMaterielRecords.add(hzMaterielRecord);
                        }else {
                            HzMaterielRecord materielRecord = HzMaterielFactory.mbomRecordToMaterielRecord(projectId,record);
                            materielRecords.add(materielRecord);
                        }
                    }
                }

                List<HzMaterielRecord> materielRecordList = hzMaterielDAO.getAllMaterielExceptVehicleMateriel(projectId);//数据库中除整车物料外的物料数据总和
                if(ListUtil.isNotEmpty(materielRecordList)){//删除物料数据
                    Set<HzMaterielRecord> set1 = new HashSet<>();
                    set1.addAll(materielRecords);
                    set1.addAll(updateMaterielRecords);
                    Set<HzMaterielRecord> set2 = new HashSet<>(materielRecordList);

                    Collection m = new HashSet(set1);
                    Collection n = new HashSet(set2);

                    n.removeAll(m);
                    if(n instanceof Set){
                        deleteMaterielRecords = (Set) n;
                    }else {
                        deleteMaterielRecords = new HashSet<>(n);
                    }
                }

                HzMbomLineRecordVO superMbom = new HzMbomLineRecordVO();
                HzMbomLineRecordVO productMbom = new HzMbomLineRecordVO();
                HzMbomLineRecordVO financeMbom = new HzMbomLineRecordVO();

                HzMbomLineRecordVO updateSuperMbom = new HzMbomLineRecordVO();
                HzMbomLineRecordVO updateProductMbom = new HzMbomLineRecordVO();
                HzMbomLineRecordVO updateFinanceMbom = new HzMbomLineRecordVO();

                HzMbomLineRecordVO deleteSuperMbom = new HzMbomLineRecordVO();
                HzMbomLineRecordVO deleteProductMbom = new HzMbomLineRecordVO();
                HzMbomLineRecordVO deleteFinanceMbom = new HzMbomLineRecordVO();


                Map<Integer,HzMbomLineRecordVO> listMap = new HashMap<>();

                Map<Integer,List<HzMaterielRecord>> materielMap = new HashMap<>();
                if(ListUtil.isNotEmpty(superMboms)){
                    superMbom.setRecordList(superMboms);
                    superMbom.setTableName(MbomTableNameEnum.tableName(0));
                    listMap.put(1,superMbom);
                }
                if(ListUtil.isNotEmpty(productMboms)){
                    productMbom.setTableName(MbomTableNameEnum.tableName(1));
                    productMbom.setRecordList(productMboms);
                    listMap.put(2,productMbom);
                }
                if(ListUtil.isNotEmpty(financeMboms)){
                    financeMbom.setRecordList(financeMboms);
                    financeMbom.setTableName(MbomTableNameEnum.tableName(6));
                    listMap.put(3,financeMbom);
                }


                if(ListUtil.isNotEmpty(updateSuperMboms)){
                    updateSuperMbom.setRecordList(updateSuperMboms);
                    updateSuperMbom.setTableName(MbomTableNameEnum.tableName(0));
                    listMap.put(4,updateSuperMbom);
                }
                if(ListUtil.isNotEmpty(updateProductMboms)){
                    updateProductMbom.setTableName(MbomTableNameEnum.tableName(1));
                    updateProductMbom.setRecordList(updateProductMboms);
                    listMap.put(5,updateProductMbom);
                }
                if(ListUtil.isNotEmpty(updateFinanceMboms)){
                    updateFinanceMbom.setRecordList(updateFinanceMboms);
                    updateFinanceMbom.setTableName(MbomTableNameEnum.tableName(6));
                    listMap.put(6,updateFinanceMbom);
                }


                if(ListUtil.isNotEmpty(deleteSuperMboms)){
                    deleteSuperMbom.setRecordList(deleteSuperMboms);
                    deleteSuperMbom.setTableName(MbomTableNameEnum.tableName(0));
                    listMap.put(7,deleteSuperMbom);
                }
                if(ListUtil.isNotEmpty(deleteProductMboms)){
                    deleteProductMbom.setTableName(MbomTableNameEnum.tableName(1));
                    deleteProductMbom.setRecordList(deleteProductMboms);
                    listMap.put(8,deleteProductMbom);
                }
                if(ListUtil.isNotEmpty(deleteFinanceMboms)){
                    deleteFinanceMbom.setRecordList(deleteFinanceMboms);
                    deleteFinanceMbom.setTableName(MbomTableNameEnum.tableName(6));
                    listMap.put(9,deleteFinanceMbom);
                }


                if(ListUtil.isNotEmpty(materielRecords)){
                    List<HzMaterielRecord> list = new ArrayList<>(materielRecords);
                    materielMap.put(10,list);
                }

                if(ListUtil.isNotEmpty(updateMaterielRecords)){
                    List<HzMaterielRecord> list = new ArrayList<>(updateMaterielRecords);
                    materielMap.put(11,list);
                }
                if(ListUtil.isNotEmpty(deleteMaterielRecords)){
                    List<HzMaterielRecord> list = new ArrayList<>(deleteMaterielRecords);
                    materielMap.put(12,list);

                }


                List<Thread> threads = new ArrayList<>();
                CountDownLatch countDownLatch = new CountDownLatch(listMap.size()+materielMap.size());
                for(Map.Entry<Integer,HzMbomLineRecordVO> entry: listMap.entrySet()){
                    RefreshMbomThread thread = new RefreshMbomThread(countDownLatch) {
                        @Override
                        public void refreshMbom() {
                            if(Integer.valueOf(1).equals(entry.getKey())
                                    || Integer.valueOf(2).equals(entry.getKey())
                                    || Integer.valueOf(3).equals(entry.getKey())){
                                hzMbomRecordDAO.insertVO(entry.getValue());
                            }else if(Integer.valueOf(4).equals(entry.getKey())
                                    || Integer.valueOf(5).equals(entry.getKey())
                                    || Integer.valueOf(6).equals(entry.getKey())){
                                hzMbomRecordDAO.updateVO(entry.getValue());
                            }else if(Integer.valueOf(7).equals(entry.getKey())
                                    || Integer.valueOf(8).equals(entry.getKey())
                                    || Integer.valueOf(9).equals(entry.getKey())){
                                hzMbomRecordDAO.deleteMbomList(entry.getValue());
                            }

                        }
                    };
                    threads.add(new Thread(thread));
                }

                for(Map.Entry<Integer,List<HzMaterielRecord>> entry: materielMap.entrySet()){
                    RefreshMbomThread thread = new RefreshMbomThread(countDownLatch) {
                        @Override
                        public void refreshMbom() {
                            if(Integer.valueOf(10).equals(entry.getKey())){
                                hzMaterielDAO.insertList(entry.getValue());
                            }else if(Integer.valueOf(11).equals(entry.getKey())){
                                hzMaterielDAO.updateList(entry.getValue());
                            }else if(Integer.valueOf(12).equals(entry.getKey())){
                                hzMaterielDAO.deleteMaterielList(entry.getValue());
                            }

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
                    return OperateResultMessageRespDTO.getFailResult();
                }
            }
            return OperateResultMessageRespDTO.getSuccessResult();
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }

    }






    public boolean analysisMbom(HzPbomLineRecord record,int i,String projectId,Set<HzPbomLineRecord> bodyOfWhiteSet,
                             List<HzConfigBomColorBean> beans,List<HzMbomLineRecord> superMboms){
        //解析产生油漆车身总成 在解析其他bom  油漆车身需要产生白车身
        if(record.getpBomLinePartName().contains("油漆车身总成") && record.getIs2Y().equals(1)){
            //找出全部的子件 是颜色件的需要乘以颜色
            //找出对应的油漆车身超级MBOM 以及对应白车身
            //油漆车身总成下面一定会出现白车身总成 否则就直接报错
            Map<String,Object> map = new HashMap<>();
            map.put("parentId",record.geteBomPuid());
            map.put("projectId",projectId);
            List<HzPbomLineRecord> records1 = hzPbomRecordDAO.findPbom(map);
            if(ListUtil.isNotEmpty(records1)){
                boolean b = false;
                for(HzPbomLineRecord manageRecord:records1){
                    if(manageRecord.getpBomLinePartName().contains("白车身总成")){
                        b = true;
                        break;
                    }
                }
                if(!b){
                    return false;
                }
            }else {
                return false;
            }
            //油漆车身 和白车身
            List<HzPbomLineRecord> recordList = hzPbomRecordDAO.getPaintAndWhiteBody(record.geteBomPuid(),projectId);

            if(ListUtil.isNotEmpty(recordList)){//lineId lineIndex sortNum

                for(HzPbomLineRecord pbomManageRecord:recordList){
                    if(pbomManageRecord.getpBomLinePartName().contains("白车身总成")){
                        bodyOfWhiteSet.add(pbomManageRecord);
                    }
                }

                //超级MBOM包含除白车身以外的bom,除去白车身BOM
//                if(ListUtil.isNotEmpty(bodyOfWhiteSet)){
//                    for(HzEPLManageRecord bodyOfWhite :bodyOfWhiteSet){
//                        HzEbomTreeQuery q = new HzEbomTreeQuery();
//                        q.setProjectId(projectId);
//                        q.setIsCarPart(1);
//                        q.setPuid(bodyOfWhite.getPuid());
//                        List<HzEPLManageRecord> rs = hzEbomRecordDAO.getHzBomLineChildren(q);
//                        if(rs.get(0).getpBomLinePartName().contains("白车身总成")) {
//                            rs.remove(rs.get(0));//白车身总成不需要去重
//                        }
//                        if(ListUtil.isNotEmpty(rs)){
//
//                            Collection a  = new ArrayList(recordList);
//                            Collection b = new ArrayList(rs);
//                            a.removeAll(b);
//                            if(a instanceof List){
//                                recordList = (List)a;
//                            }else {
//                                recordList = new ArrayList<>(a);
//                            }
//                        }
//                    }
//                }


                if(ListUtil.isNotEmpty(records1)){//超级MBOM
                    records1.add(record);//油漆车身也添加进去
                    for(HzPbomLineRecord pbomLineRecord :records1){
                        HzPbomTreeQuery query = new HzPbomTreeQuery();
                        if(pbomLineRecord.getpBomLinePartName().contains("油漆车身总成")||
                                pbomLineRecord.getpBomLinePartName().contains("白车身总成")){
                            HzMbomLineRecord mbomLineRecord = HzMbomRecordFactory.generateSupMbom(pbomLineRecord,i,projectId,beans);
                            superMboms.add(mbomLineRecord);
                        }else {
                            query.setPuid(pbomLineRecord.geteBomPuid());
                            query.setProjectId(projectId);
                            List<HzPbomLineRecord> manageRecords = hzPbomRecordDAO.getHzPbomTree(query);
                            if(ListUtil.isNotEmpty(manageRecords)){
                                for(HzPbomLineRecord r :manageRecords){
                                    HzMbomLineRecord mbomLineRecord = HzMbomRecordFactory.generateSupMbom(r,i,projectId,beans);
                                    superMboms.add(mbomLineRecord);
                                }
                            }
                        }
                    }
                }
            }
        }else {//非油漆车身总成下面的件 直接加到超级MBOM
            HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();//找出油漆车身的全部子件
            hzPbomTreeQuery.setProjectId(projectId);
            hzPbomTreeQuery.setPuid(record.geteBomPuid());
            List<HzPbomLineRecord> recordList = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
            if(ListUtil.isNotEmpty(recordList)){
                for(HzPbomLineRecord eplManageRecord:recordList){
                    HzMbomLineRecord mbomLineRecord = HzMbomRecordFactory.generateSupMbom(eplManageRecord,i,projectId,beans);
//                    HzMbomLineRecord lineRecord = hzMbomRecordDAO.findHzMbomByEbomIdAndLineIndex(mbomLineRecord.geteBomPuid(),mbomLineRecord.getLineIndex(),MbomTableNameEnum.tableName(0));
                    superMboms.add(mbomLineRecord);
                }

            }

        }
        return true;
    }

    /**
     * 最终要同步数据结果
     * @param a 新增数据
     * @param b 数据库查询结果集
     * @param u 更新的数据
     * @param d 删除的数据
     * @param type 数据库表名 映射类型 1生产BOM  6财务BOM 其他值为超级MBOM
     */

    public void refreshResult(List<HzMbomLineRecord> a,List<HzMbomLineRecord> b,List<HzMbomLineRecord> u,List<HzMbomLineRecord> d,int type){
        if(ListUtil.isNotEmpty(a) && ListUtil.isNotEmpty(b)){
            Collection m = new ArrayList(a);//要新增
            Collection n = new ArrayList(b);//数据库原有的
            n.removeAll(m);
            if(n instanceof List){
                d = (List)n;
            }else {
                d = new ArrayList<>(n);
            }
            List<HzMbomLineRecord> lineRecords = new ArrayList<>();
            for(HzMbomLineRecord record :a){
                HzMbomLineRecord lineRecord = hzMbomRecordDAO.findHzMbomByEbomIdAndLineIndex(record.geteBomPuid(),record.getLineIndex(),MbomTableNameEnum.tableName(type));
                if(lineRecord != null){
                    lineRecords.add(record);
                    lineRecord.setLineId(record.getLineId());
                    u.add(lineRecord);
                }
            }
            a.removeAll(lineRecords);
        }
    }

}



