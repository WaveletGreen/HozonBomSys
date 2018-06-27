package com.connor.hozon.bom.resources.service.bom.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.AddMbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateMbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMbomByPageQuery;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            Page<HzMbomLineRecord> recordPage =hzMbomRecordDAO.findMbomForPage(query);
            int num = (query.getPage()-1)*query.getLimit();
            if(recordPage == null || recordPage.getResult() == null){
                return  new Page<>(query.getPage(),query.getLimit(),0);
            }
            List<HzMbomLineRecord> lineRecords = recordPage.getResult();
            List<HzMbomRecordRespDTO> respDTOList = new ArrayList<>();
            for(HzMbomLineRecord record:lineRecords){
                HzMbomRecordRespDTO respDTO= new HzMbomRecordRespDTO();
                respDTO.setNo(++num);
                respDTO.setPuid(record.getPuid());
                respDTO.seteBomPuid(record.getpPuid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                respDTO.setLevel(strings[0]);//层级
                respDTO.setLineId(record.getLineID());
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
                respDTOList.add(respDTO);
            }
            return new Page<>(query.getPage(),query.getLimit(),recordPage.getTotalCount(),respDTOList);
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
                respDTO.seteBomPuid(record.getpPuid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                respDTO.setLevel(strings[0]);//层级
                respDTO.setLineId(record.getLineID());
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
            HzMbomRecord record = hzMbomRecordDAO.findHzMbomByeBomPuid(reqDTO.geteBomPuid());
            if(record != null){
                operateResultMessageRespDTO.setErrMsg("当前插入的对象已存在,编辑属性请点击修改按钮进行操作!");
                operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return operateResultMessageRespDTO;
            }
            HzMbomRecord hzMbomRecord = new HzMbomRecord();
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
            hzMbomRecord.setStatus(1);
            hzMbomRecord.setUpdateName(user.getUserName());
            hzMbomRecord.setPuid(UUID.randomUUID().toString());
            int i = hzMbomRecordDAO.insert(hzMbomRecord);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
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
            HzMbomRecord record = new HzMbomRecord();
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
    public OperateResultMessageRespDTO deleteMbomRecord(String puid) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        try {
            User user = UserInfo.getUser();
            if (user.getGroupId() != 9) {
                respDTO.setErrMsg("你当前没有权限执行此操作");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            HzMbomRecord record = hzMbomRecordDAO.findHzMbomByeBomPuid(puid);
            if (null == record) {
                record = new HzMbomRecord();
                record.seteBomPuid(puid);
                record.setPuid(UUID.randomUUID().toString());
                int i = hzMbomRecordDAO.insert(record);
                if (i < 0) {
                    return OperateResultMessageRespDTO.getFailResult();
                }
            }
            int i = hzMbomRecordDAO.deleteByForeignId(puid);
            if (i > 0) {
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }
}
