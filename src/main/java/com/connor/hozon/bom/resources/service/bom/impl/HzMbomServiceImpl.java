package com.connor.hozon.bom.resources.service.bom.impl;

import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.AddMbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.DeleteHzMbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateMbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzSuperMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzVehicleModelRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.query.HzMbomByPageQuery;
import com.connor.hozon.bom.resources.query.HzMbomTreeQuery;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomRecord;
import sql.redis.SerializeUtil;

import java.util.*;

import static com.connor.hozon.bom.resources.service.bom.impl.HzPbomServiceImpl.getLevelAndRank;

/**
 * @Author: haozt
 * @Date: 2018/6/20
 * @Description:
 */
@Service("HzMbomService")
public class HzMbomServiceImpl implements HzMbomService {
    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Override
    public Page<HzMbomRecordRespDTO> fingHzMbomForPage(HzMbomByPageQuery query) {
        try {
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

            int count = hzMbomRecordDAO.getHzMbomTotalCount(query.getProjectId());
            if(count<=0){
                List<HzBomLineRecord> lineRecords = hzBomLineRecordDao.getAllBomLineRecordByProjectId(query.getProjectId());
                if(ListUtil.isNotEmpty(lineRecords)){
                    int size = lineRecords.size();
                    //分批插入数据 一次1000条
                    int i =0;
                    int cout = 0;
                    if(size>1000){
                        for(i =0;i<size/1000;i++){
                            List<HzMbomLineRecord> list = new ArrayList<>();
                            for(int j = 0;j<1000;j++){
                                HzMbomLineRecord hzPbomLineRecord =bomLineToMbomLine(lineRecords.get(cout));
                                list.add(hzPbomLineRecord);
                                cout++;
                            }
                            hzMbomRecordDAO.insertList(list);
                        }
                    }
                    if(i*1000<size){
                        List<HzMbomLineRecord> list = new ArrayList<>();
                        for(int j = 0;j<size-i*1000;j++){
                            HzMbomLineRecord hzPbomLineRecord =bomLineToMbomLine(lineRecords.get(cout));
                            list.add(hzPbomLineRecord);
                            cout++;
                        }
                        hzMbomRecordDAO.insertList(list);
                    }
                }
            }
            Page<HzMbomLineRecord> recordPage =hzMbomRecordDAO.findMbomForPage(query);
            int num = (recordPage.getPageNumber()-1)*recordPage.getPageSize();
            if(recordPage == null || recordPage.getResult() == null){
                return  new Page<>(recordPage.getPageNumber(),recordPage.getPageSize(),0);
            }
            List<HzMbomLineRecord> lineRecords = recordPage.getResult();
            List<HzMbomRecordRespDTO> respDTOList = new ArrayList<>();
            for(HzMbomLineRecord record:lineRecords){
                HzMbomRecordRespDTO respDTO= new HzMbomRecordRespDTO();
                respDTO.setNo(++num);
                respDTO.setPuid(record.getPuid());
                respDTO.seteBomPuid(record.geteBomPuid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                respDTO.setLevel(strings[0]);//层级
                respDTO.setLineId(record.getLineId());
                respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept());
                respDTO.setpBomLinePartClass(record.getpBomLinePartClass());
                respDTO.setpBomLinePartEnName(record.getpBomLinePartEnName());
                respDTO.setpBomLinePartName(record.getpBomLinePartName());
                respDTO.setpBomLinePartResource(record.getpBomLinePartResource());

                respDTO.setSparePart(record.getSparePart());
                respDTO.setSparePartNum(record.getSparePartNum());
                respDTO.setLaborHour(record.getLaborHour());
                respDTO.setRhythm(record.getRhythm());
                respDTO.setSolderJoint(record.getSolderJoint());
                respDTO.setMachineMaterial(record.getMachineMaterial());
                respDTO.setStandardPart(record.getStandardPart());
                respDTO.setTools(record.getTools());
                respDTO.setWasterProduct(record.getWasterProduct());
                respDTO.setChange(record.getChange());
                respDTO.setChangeNum(record.getChangeNum());

                respDTO.setpBomType(record.getpBomType());
                respDTO.setpFactoryCode("1001");
                respDTO.setpStockLocation(record.getpStockLocation());
                respDTOList.add(respDTO);
            }
            return new Page<>(recordPage.getPageNumber(),recordPage.getPageSize(),recordPage.getTotalCount(),respDTOList);
        }catch (Exception e){
            return null;
        }
    }


    @Override
    public HzMbomRecordRespDTO findHzMbomByPuid(String projectId, String puid) {
        try{
            Map<String,Object> map = new HashMap<>();
            map.put("projectId",projectId);
            map.put("pPuid",puid);
            HzMbomLineRecord record = hzMbomRecordDAO.findHzMbomByPuid(map);
            if(record != null){
                HzMbomRecordRespDTO respDTO = new HzMbomRecordRespDTO();
                respDTO.setPuid(record.getPuid());
                respDTO.seteBomPuid(record.geteBomPuid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                respDTO.setLevel(strings[0]);//层级
                respDTO.setLineId(record.getLineId());
                respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept());


                respDTO.setObject_name(record.getpBomLinePartName());
                respDTO.setpBomLinePartClass(record.getpBomLinePartClass());
                respDTO.setpBomLinePartEnName(record.getpBomLinePartEnName());
                respDTO.setpBomLinePartName(record.getpBomLinePartName());
                respDTO.setpBomLinePartResource(record.getpBomLinePartResource());

                respDTO.setSparePart(record.getSparePart());
                respDTO.setSparePartNum(record.getSparePartNum());
                respDTO.setLaborHour(record.getLaborHour());
                respDTO.setRhythm(record.getRhythm());
                respDTO.setSolderJoint(record.getSolderJoint());
                respDTO.setMachineMaterial(record.getMachineMaterial());
                respDTO.setStandardPart(record.getStandardPart());
                respDTO.setTools(record.getTools());
                respDTO.setWasterProduct(record.getWasterProduct());
                respDTO.setChange(record.getChange());
                respDTO.setChangeNum(record.getChangeNum());
                return  respDTO;
            }

        }catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public OperateResultMessageRespDTO insertMbomRecord(AddMbomReqDTO reqDTO) {
        OperateResultMessageRespDTO operateResultMessageRespDTO = new OperateResultMessageRespDTO();
        try {
            User user = UserInfo.getUser();
            if(user.getGroupId()!=9){
                operateResultMessageRespDTO.setErrMsg("你当前没有权限执行此操作!");
                operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return operateResultMessageRespDTO;
            }
//            HzMbomRecord record = hzMbomRecordDAO.findHzMbomByeBomPuid(reqDTO.geteBomPuid());
//            if(record != null){
//                operateResultMessageRespDTO.setErrMsg("当前插入的对象已存在,编辑属性请点击修改按钮进行操作!");
//                operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
//                return operateResultMessageRespDTO;
//            }


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
            hzMbomRecord.setCreateName(user.getUserName());
            hzMbomRecord.setUpdateName(user.getUserName());
            Map<String,Object> map = new HashMap<>();
            map.put("pPuid",reqDTO.geteBomPuid());
            map.put("projectId",reqDTO.getProjectId());
            HzMbomLineRecord lineRecord = hzMbomRecordDAO.findHzMbomByPuid(map);
            if(lineRecord!=null){
                int i = hzMbomRecordDAO.update(hzMbomRecord);
                if(i>0){
                    return  OperateResultMessageRespDTO.getSuccessResult();
                }else{
                    return OperateResultMessageRespDTO.getFailResult();
                }
            }
            return OperateResultMessageRespDTO.getFailResult();
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public OperateResultMessageRespDTO updateMbomRecord(UpdateMbomReqDTO reqDTO) {
        OperateResultMessageRespDTO operateResultMessageRespDTO = new OperateResultMessageRespDTO();
        try {
            User user = UserInfo.getUser();
            if(user.getGroupId()!=9){
                operateResultMessageRespDTO.setErrMsg("你当前没有权限执行此操作");
                operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return operateResultMessageRespDTO;
            }
            HzMbomLineRecord record = new HzMbomLineRecord();
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
            int i = hzMbomRecordDAO.update(record);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO deleteMbomRecord(DeleteHzMbomReqDTO reqDTO) {
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
                HzMbomTreeQuery treeQuery = new HzMbomTreeQuery();
                treeQuery.setProjectId(reqDTO.getProjectId());
                treeQuery.setPuid(puid);
                List<HzMbomLineRecord> lineRecords = hzMbomRecordDAO.getHzMbomTree(treeQuery);//自己
                Set<String> set = new HashSet<>();//去除重复
                if(ListUtil.isNotEmpty(lineRecords)){
                    for(int i = 0;i<lineRecords.size();i++){
                        HzMbomTreeQuery hzMbomTreeQuery = new HzMbomTreeQuery();
                        hzMbomTreeQuery.setPuid(lineRecords.get(0).getParentUid());
                        hzMbomTreeQuery.setProjectId(reqDTO.getProjectId());
                        List<HzMbomLineRecord> records = hzMbomRecordDAO.getHzMbomTree(hzMbomTreeQuery);//父亲
                        if(ListUtil.isNotEmpty(records)){
                            if(records.size() -lineRecords.size()==1){
                                HzMbomLineRecord hzMbomLineRecord = records.get(0);
                                hzMbomLineRecord.setIsHas(0);
                                hzMbomLineRecord.setIsPart(1);
                                if(hzMbomLineRecord.getIs2Y().equals(1)){
                                    hzMbomLineRecord.setIs2Y(0);
                                }
                                hzMbomRecordDAO.update(hzMbomLineRecord);
                            }

                        }
                        set.add(lineRecords.get(i).geteBomPuid());
                    }
                }
                List<DeleteHzMbomReqDTO> list = new ArrayList<>();
                for(String s:set){
                    DeleteHzMbomReqDTO deleteHzPbomReqDTO = new DeleteHzMbomReqDTO();
                    deleteHzPbomReqDTO.setPuid(s);
                    list.add(deleteHzPbomReqDTO);
                }
                if(ListUtil.isNotEmpty(list)){
                    hzMbomRecordDAO.deleteList(list);//mabatis 做批量更新时 返回值为-1 所以这里根据异常情况来判断成功与否
                }
            }
            return OperateResultMessageRespDTO.getSuccessResult();
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public Page<HzSuperMbomRecordRespDTO> getHzSuperMbomPage(HzMbomByPageQuery query) {
        try{
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
            Page<HzMbomLineRecord> recordPage =hzMbomRecordDAO.getHzSuberMbomByPage(query);
            int num = (recordPage.getPageNumber()-1)*recordPage.getPageSize();
            if(recordPage == null || recordPage.getResult() == null){
                return  new Page<>(recordPage.getPageNumber(),recordPage.getPageSize(),0);
            }
            Map<String,Object> map = new HashMap<>();
            map.put("projectId",query.getProjectId());
            List<HzMbomLineRecord> records = recordPage.getResult();
            List<HzSuperMbomRecordRespDTO> respDTOS = new ArrayList<>();
            for(HzMbomLineRecord record :records){
             HzSuperMbomRecordRespDTO respDTO = new HzSuperMbomRecordRespDTO();
                respDTO.setNo(++num);
                respDTO.setPuid(record.getPuid());
                respDTO.seteBomPuid(record.getpPuid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                respDTO.setLevel(strings[0]);//层级
                respDTO.setLineId(record.getLineId());
                respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept());
                byte[] bytes = record.getBomLineBlock();
                Object obj = SerializeUtil.unserialize(bytes);
                Object name = null;
                if (obj instanceof LinkedHashMap) {
                    if (((LinkedHashMap) obj).size() > 0) {
                        name =((LinkedHashMap) obj).get("object_name");
                    }
                }
                respDTO.setObject_name((String) name);
                respDTO.setSparePart(record.getSparePart());
                respDTO.setSparePartNum(record.getSparePartNum());
                respDTO.setLaborHour(record.getLaborHour());
                respDTO.setRhythm(record.getRhythm());
                respDTO.setSolderJoint(record.getSolderJoint());
                respDTO.setMachineMaterial(record.getMachineMaterial());
                respDTO.setStandardPart(record.getStandardPart());
                respDTO.setTools(record.getTools());
                respDTO.setWasterProduct(record.getWasterProduct());
                respDTO.setChange(record.getChange());
                respDTO.setChangeNum(record.getChangeNum());
                if(null == record.getObjectName()){
                    HzMbomLineRecord lineRecord = getHzSuperMbomByPuid(query.getProjectId(),record.getParentUid());
                    respDTO.setCfg0Desc(lineRecord.getCfg0Desc());
                    respDTO.setCfg0FamilyDesc(lineRecord.getCfg0FamilyDesc());
                    respDTO.setCfg0FamilyName(lineRecord.getCfg0FamilyName());
                    respDTO.setCfg0ModelBasicDetail(lineRecord.getCfg0ModelBasicDetail());
                    respDTO.setObjectName(lineRecord.getObjectName());
                    respDTO.setObjectDesc(lineRecord.getObjectDesc());

                }else{
                    respDTO.setCfg0Desc(record.getCfg0Desc());
                    respDTO.setCfg0FamilyDesc(record.getCfg0FamilyDesc());
                    respDTO.setCfg0FamilyName(record.getCfg0FamilyName());
                    respDTO.setCfg0ModelBasicDetail(record.getCfg0ModelBasicDetail());
                    respDTO.setObjectName(record.getObjectName());
                    respDTO.setObjectDesc(record.getObjectDesc());
                }
                respDTOS.add(respDTO);
            }
            return new Page<>(recordPage.getPageNumber(),recordPage.getPageSize(),recordPage.getTotalCount(),respDTOS);
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public List<HzVehicleModelRespDTO> getHzVehicleModelByProjectId(String projectId) {
        try {
            List<HzMbomLineRecord> records = hzMbomRecordDAO.getHzVehicleModelName(projectId);
            List<HzVehicleModelRespDTO> respDTOS = new ArrayList<>();
            if(ListUtil.isNotEmpty(records)){
                for(HzMbomLineRecord record:records){
                    HzVehicleModelRespDTO vehicleModelRespDTO = new HzVehicleModelRespDTO();
                    vehicleModelRespDTO.setObjectName(record.getObjectName());
                    vehicleModelRespDTO.setCfg0ModelRecordId(record.getCfg0ModelRecordId());
                    respDTOS.add(vehicleModelRespDTO);
                }
                return respDTOS;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public HzMbomLineRecord getHzSuperMbomByPuid(String projectId, String puid) {
        HzMbomLineRecord record = hzMbomRecordDAO.getHzSuperMbomByPuid(projectId,puid);
        if(record != null){
            return record;
        }else {
            record = hzMbomRecordDAO.getHzMbom(projectId,puid);
            if(record == null){
                return null;
            }
            return  getHzSuperMbomByPuid(projectId,record.getParentUid());
        }
    }

    @Override
    public List<HzMbomLineRecord> getHzMbomTree(HzMbomTreeQuery query) {
        return hzMbomRecordDAO.getHzMbomTree(query);
    }

    @Override
    public Page<HzMbomRecordRespDTO> getHzMbomRecycleByPage(HzBomRecycleByPageQuery query) {
        try {

            Page<HzMbomLineRecord> recordPage =hzMbomRecordDAO.getHzMbomRecycleRecord(query);
            int num = (recordPage.getPageNumber()-1)*recordPage.getPageSize();
            if(recordPage == null || recordPage.getResult() == null){
                return  new Page<>(recordPage.getPageNumber(),recordPage.getPageSize(),0);
            }
            List<HzMbomLineRecord> lineRecords = recordPage.getResult();
            List<HzMbomRecordRespDTO> respDTOList = new ArrayList<>();
            for(HzMbomLineRecord record:lineRecords){
                HzMbomRecordRespDTO respDTO= new HzMbomRecordRespDTO();
                respDTO.setNo(++num);
                respDTO.setPuid(record.getPuid());
                respDTO.seteBomPuid(record.geteBomPuid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                respDTO.setLevel(strings[0]);//层级
                respDTO.setLineId(record.getLineId());
                respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept());
                respDTO.setpBomLinePartClass(record.getpBomLinePartClass());
                respDTO.setpBomLinePartEnName(record.getpBomLinePartEnName());
                respDTO.setpBomLinePartName(record.getpBomLinePartName());
                respDTO.setpBomLinePartResource(record.getpBomLinePartResource());
                respDTO.setSparePart(record.getSparePart());
                respDTO.setSparePartNum(record.getSparePartNum());
                respDTO.setLaborHour(record.getLaborHour());
                respDTO.setRhythm(record.getRhythm());
                respDTO.setSolderJoint(record.getSolderJoint());
                respDTO.setMachineMaterial(record.getMachineMaterial());
                respDTO.setStandardPart(record.getStandardPart());
                respDTO.setTools(record.getTools());
                respDTO.setWasterProduct(record.getWasterProduct());
                respDTO.setChange(record.getChange());
                respDTO.setChangeNum(record.getChangeNum());
                respDTO.setpBomType(record.getpBomType());
                respDTO.setpFactoryCode("1001");
                respDTO.setpStockLocation(record.getpStockLocation());
                respDTOList.add(respDTO);
            }
            return new Page<>(recordPage.getPageNumber(),recordPage.getPageSize(),recordPage.getTotalCount(),respDTOList);
        }catch (Exception e){
            return null;
        }
    }

    private HzMbomLineRecord bomLineToMbomLine(HzBomLineRecord record){
        HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();
        hzMbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzMbomLineRecord.setIsHas(record.getIsHas());
        hzMbomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzMbomLineRecord.seteBomPuid(record.getPuid());
        hzMbomLineRecord.setIsDept(record.getIsDept());
        hzMbomLineRecord.setLineId(record.getLineID());
        hzMbomLineRecord.setIsPart(record.getIsPart());
        hzMbomLineRecord.setIs2Y(record.getIs2Y());
        hzMbomLineRecord.setLineIndex(record.getLineIndex());
        hzMbomLineRecord.setParentUid(record.getParentUid());
        hzMbomLineRecord.setpBomLinePartClass(record.getpBomLinePartClass());
        hzMbomLineRecord.setpBomLinePartName(record.getpBomLinePartName());
        hzMbomLineRecord.setpBomOfWhichDept(record.getpBomOfWhichDept());
        hzMbomLineRecord.setpBomLinePartEnName(record.getpBomLinePartEnName());
        hzMbomLineRecord.setpBomLinePartResource(record.getpBomLinePartResource());
        hzMbomLineRecord.setOrderNum(record.getOrderNum());
        return hzMbomLineRecord;
    }

}
