package com.connor.hozon.bom.resources.service.work.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.AddHzProcessReqDTO;
import com.connor.hozon.bom.resources.dto.request.ApplyMbomDataTOHzMaterielReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzProcessReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzWorkProcessRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkCenterDAO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.query.HzWorkProcessByPageQuery;
import com.connor.hozon.bom.resources.service.work.HzWorkProcessService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.HzMaterielRecord;
import sql.pojo.work.HzWorkCenter;
import sql.pojo.work.HzWorkProcedure;
import sql.pojo.work.HzWorkProcess;

import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/7/5
 * @Description:
 */
@Service("HzWorkProcessService")
public class HzWorkProcessServiceImpl implements HzWorkProcessService {
    @Autowired
    private HzWorkProcedureDAO hzWorkProcedureDAO;

    @Autowired
    private HzWorkCenterDAO hzWorkCenterDAO;

    @Autowired
    private HzMaterielDAO hzMaterielDAO;

    @Autowired
    private HzFactoryDAO hzFactoryDAO;
    @Override
    public OperateResultMessageRespDTO addHzWorkProcess(AddHzProcessReqDTO reqDTO) {
        try{
            OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
            User user = UserInfo.getUser();
            if(user.getGroupId()!=9l){
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                respDTO.setErrMsg("您当前没有权限进行此操作！");
                return respDTO;
            }

            HzWorkProcedure hzWorkProcedure = new HzWorkProcedure();
            HzWorkProcedure workProcess = hzWorkProcedureDAO.getHzWorkProcessByMaterielId(reqDTO.getMaterielId());
            if(workProcess!=null){
                respDTO.setErrMsg("当前插入的对象已存在,编辑属性请点击修改按钮进行操作!");
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return respDTO;
            }
            String factoryCode = reqDTO.getFactoryCode();
            if(factoryCode != null && !factoryCode.equals("")){
                HzFactory hzFactory = hzFactoryDAO.findFactory("",factoryCode);
                String puid = UUID.randomUUID().toString();
                if(hzFactory == null){
                    hzFactory = new HzFactory();
                    hzFactory.setPuid(puid);
                    hzFactory.setpFactoryCode(factoryCode);
                    int i = hzFactoryDAO.insert(hzFactory);
                    if(i<0){
                        return OperateResultMessageRespDTO.getFailResult();
                    }
                }else{
                    puid = hzFactory.getPuid();
                }
                HzMaterielQuery query = new HzMaterielQuery();
                query.setProjectId(reqDTO.getProjectId());
                query.setPuid(reqDTO.getMaterielId());
                HzMaterielRecord hzMaterielRecord = hzMaterielDAO.getHzMaterielRecord(query);
                if(hzMaterielRecord == null){
                    return OperateResultMessageRespDTO.getFailResult();
                }
                hzMaterielRecord.setpFactoryPuid(puid);
                int i = hzMaterielDAO.update(hzMaterielRecord);
                if(i<0){
                    return OperateResultMessageRespDTO.getFailResult();
                }
            }

            hzWorkProcedure.setMaterielId(reqDTO.getMaterielId());
            hzWorkProcedure.setControlCode( reqDTO.getControlCode());
            hzWorkProcedure.setpBurn(reqDTO.getpBurn());
            hzWorkProcedure.setpCount(reqDTO.getpCount());
            hzWorkProcedure.setpCreateName(user.getUserName());
            hzWorkProcedure.setpDirectLabor(reqDTO.getpDirectLabor());
            hzWorkProcedure.setpIndirectLabor(reqDTO.getpIndirectLabor());
            hzWorkProcedure.setpMachineLabor(reqDTO.getpMachineLabor());
            hzWorkProcedure.setpMachineMaterialLabor(reqDTO.getpMachineMaterialLabor());
            hzWorkProcedure.setpOtherCost(reqDTO.getpOtherCost());
            hzWorkProcedure.setpProcedureDesc(reqDTO.getpProcedureDesc());
            hzWorkProcedure.setpProcedureCode(reqDTO.getpProcedureCode());
            hzWorkProcedure.setProjectId(reqDTO.getProjectId());
            hzWorkProcedure.setpUpdateName(user.getUserName());
            hzWorkProcedure.setPurpose(reqDTO.getPurpose());
            hzWorkProcedure.setState(reqDTO.getState());
            hzWorkProcedure.setPuid(UUID.randomUUID().toString());
            List<HzWorkCenter> hzWorkCenterList = hzWorkCenterDAO.findWorkCenter(reqDTO.getProjectId(),reqDTO.getpWorkCode());
            if(hzWorkCenterList != null && hzWorkCenterList.size()>0){
                hzWorkProcedure.setpWorkPuid("");
            }else {
                String puid = UUID.randomUUID().toString();
                HzWorkCenter hzWorkCenter = new HzWorkCenter();
                hzWorkCenter.setPuid(puid);
                hzWorkCenter.setpWorkCode(reqDTO.getpWorkCode());
                hzWorkCenter.setpWorkDesc(reqDTO.getpWorkDesc());
                hzWorkCenterDAO.insert(hzWorkCenter);
                hzWorkProcedure.setpWorkPuid(puid);
            }
           int i =  hzWorkProcedureDAO.insert(hzWorkProcedure);
           if(i>0){
               return OperateResultMessageRespDTO.getSuccessResult();
           }
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }

        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO updateHzWorkProcess(UpdateHzProcessReqDTO reqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        User user = UserInfo.getUser();
        if(user.getGroupId()!=9l){
            respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
            respDTO.setErrMsg("您当前没有权限进行此操作！");
            return respDTO;
        }
        HzWorkProcedure workProcess = hzWorkProcedureDAO.getHzWorkProcessByMaterielId(reqDTO.getMaterielId());
        if(workProcess == null){
            HzWorkProcedure hzWorkProcedure = new HzWorkProcedure();
            String factoryCode = reqDTO.getFactoryCode();
            if(factoryCode != null && !factoryCode.equals("")){
                HzFactory hzFactory = hzFactoryDAO.findFactory("",factoryCode);
                String puid = UUID.randomUUID().toString();
                if(hzFactory == null){
                    hzFactory = new HzFactory();
                    hzFactory.setPuid(puid);
                    hzFactory.setpFactoryCode(factoryCode);
                    int i = hzFactoryDAO.insert(hzFactory);
                    if(i<0){
                        return OperateResultMessageRespDTO.getFailResult();
                    }
                }else{
                    puid = hzFactory.getPuid();
                }
                HzMaterielQuery query = new HzMaterielQuery();
                query.setProjectId(reqDTO.getProjectId());
                query.setPuid(reqDTO.getMaterielId());
                HzMaterielRecord hzMaterielRecord = hzMaterielDAO.getHzMaterielRecord(query);
                if(hzMaterielRecord == null){
                    return OperateResultMessageRespDTO.getFailResult();
                }
                hzMaterielRecord.setpFactoryPuid(puid);
                int i = hzMaterielDAO.update(hzMaterielRecord);
                if(i<0){
                    return OperateResultMessageRespDTO.getFailResult();
                }
            }

            hzWorkProcedure.setMaterielId(reqDTO.getMaterielId());
            hzWorkProcedure.setControlCode( reqDTO.getControlCode());
            hzWorkProcedure.setpBurn(reqDTO.getpBurn());
            hzWorkProcedure.setpCount(reqDTO.getpCount());
            hzWorkProcedure.setpCreateName(user.getUserName());
            hzWorkProcedure.setpDirectLabor(reqDTO.getpDirectLabor());
            hzWorkProcedure.setpIndirectLabor(reqDTO.getpIndirectLabor());
            hzWorkProcedure.setpMachineLabor(reqDTO.getpMachineLabor());
            hzWorkProcedure.setpMachineMaterialLabor(reqDTO.getpMachineMaterialLabor());
            hzWorkProcedure.setpOtherCost(reqDTO.getpOtherCost());
            hzWorkProcedure.setpProcedureDesc(reqDTO.getpProcedureDesc());
            hzWorkProcedure.setpProcedureCode(reqDTO.getpProcedureCode());
            hzWorkProcedure.setProjectId(reqDTO.getProjectId());
            hzWorkProcedure.setpUpdateName(user.getUserName());
            hzWorkProcedure.setPurpose(reqDTO.getPurpose());
            hzWorkProcedure.setState(reqDTO.getState());
            hzWorkProcedure.setPuid(UUID.randomUUID().toString());
            List<HzWorkCenter> hzWorkCenterList = hzWorkCenterDAO.findWorkCenter(reqDTO.getProjectId(),reqDTO.getpWorkCode());
            if(hzWorkCenterList != null && hzWorkCenterList.size()>0){
                hzWorkProcedure.setpWorkPuid("");
            }else {
                String puid = UUID.randomUUID().toString();
                HzWorkCenter hzWorkCenter = new HzWorkCenter();
                hzWorkCenter.setPuid(puid);
                hzWorkCenter.setpWorkCode(reqDTO.getpWorkCode());
                hzWorkCenter.setpWorkDesc(reqDTO.getpWorkDesc());
                hzWorkCenterDAO.insert(hzWorkCenter);
                hzWorkProcedure.setpWorkPuid(puid);
            }
            int i =  hzWorkProcedureDAO.insert(hzWorkProcedure);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }else {
                return OperateResultMessageRespDTO.getFailResult();
            }
        }
        String factoryCode = reqDTO.getFactoryCode();
        if(factoryCode != null && !factoryCode.equals("")){
            HzFactory hzFactory = hzFactoryDAO.findFactory("",factoryCode);
            String puid = UUID.randomUUID().toString();
            if(hzFactory == null){
                hzFactory.setPuid(puid);
                hzFactory.setpFactoryCode(factoryCode);
                int i = hzFactoryDAO.insert(hzFactory);
                if(i<0){
                    return OperateResultMessageRespDTO.getFailResult();
                }
            }else{
                puid = hzFactory.getPuid();
            }
            HzMaterielQuery query = new HzMaterielQuery();
            query.setProjectId(reqDTO.getProjectId());
            query.setPuid(reqDTO.getMaterielId());
            HzMaterielRecord hzMaterielRecord = hzMaterielDAO.getHzMaterielRecord(query);
            if(hzMaterielRecord == null){
                return OperateResultMessageRespDTO.getFailResult();
            }
            hzMaterielRecord.setpFactoryPuid(puid);
            int i = hzMaterielDAO.update(hzMaterielRecord);
            if(i<0){
                return OperateResultMessageRespDTO.getFailResult();
            }
        }
        HzWorkProcedure hzWorkProcedure = new HzWorkProcedure();
        hzWorkProcedure.setMaterielId(reqDTO.getMaterielId());
        hzWorkProcedure.setControlCode( reqDTO.getControlCode());
        hzWorkProcedure.setpBurn(reqDTO.getpBurn());
        hzWorkProcedure.setpCount(reqDTO.getpCount());
        hzWorkProcedure.setpDirectLabor(reqDTO.getpDirectLabor());
        hzWorkProcedure.setpIndirectLabor(reqDTO.getpIndirectLabor());
        hzWorkProcedure.setpMachineLabor(reqDTO.getpMachineLabor());
        hzWorkProcedure.setpMachineMaterialLabor(reqDTO.getpMachineMaterialLabor());
        hzWorkProcedure.setpOtherCost(reqDTO.getpOtherCost());
        hzWorkProcedure.setpProcedureDesc(reqDTO.getpProcedureDesc());
        hzWorkProcedure.setpProcedureCode(reqDTO.getpProcedureCode());
        hzWorkProcedure.setpUpdateName(user.getUserName());
        hzWorkProcedure.setPurpose(reqDTO.getPurpose());
        hzWorkProcedure.setState(reqDTO.getState());
        List<HzWorkCenter> hzWorkCenterList = hzWorkCenterDAO.findWorkCenter(reqDTO.getProjectId(),reqDTO.getpWorkCode());
        if(hzWorkCenterList != null && hzWorkCenterList.size()>0){
            hzWorkProcedure.setpWorkPuid(hzWorkCenterList.get(0).getPuid());
        }else {
            String puid = UUID.randomUUID().toString();
            HzWorkCenter hzWorkCenter = new HzWorkCenter();
            hzWorkCenter.setPuid(puid);
            hzWorkCenter.setpWorkCode(reqDTO.getpWorkCode());
            hzWorkCenter.setpWorkDesc(reqDTO.getpWorkDesc());
            hzWorkCenterDAO.insert(hzWorkCenter);
            hzWorkProcedure.setpWorkPuid(puid);
        }
        int i =  hzWorkProcedureDAO.update(hzWorkProcedure);
        if(i>0){
            return OperateResultMessageRespDTO.getSuccessResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO deleteHzWorkProcess(String puid) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        User user = UserInfo.getUser();
        if(user.getGroupId()!=9l){
            respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
            respDTO.setErrMsg("您当前没有权限进行此操作！");
            return respDTO;
        }
        try{
            HzWorkProcedure workProcess = hzWorkProcedureDAO.getHzWorkProcessByMaterielId(puid);
            if(workProcess == null){
                HzWorkProcedure hzWorkProcedure = new HzWorkProcedure();
                hzWorkProcedure.setMaterielId(puid);
                hzWorkProcedure.setPuid(UUID.randomUUID().toString());
                int i =hzWorkProcedureDAO.insert(hzWorkProcedure);
                if(i<=0){
                    return OperateResultMessageRespDTO.getFailResult();
                }
            }
            int i =hzWorkProcedureDAO.delete(puid);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
            return OperateResultMessageRespDTO.getFailResult();
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public Page<HzWorkProcessRespDTO> findHzWorkProcessForPage(HzWorkProcessByPageQuery query) {
        try {
            Page<HzWorkProcess> hzWorkProcessPage = hzWorkProcedureDAO.findHzWorkProcessByPage(query);
            if(hzWorkProcessPage == null || hzWorkProcessPage.getResult() == null){
                return  new Page<>(hzWorkProcessPage.getPageNumber(),hzWorkProcessPage.getPageSize(),0);
            }
            int num = (hzWorkProcessPage.getPageNumber()-1)*hzWorkProcessPage.getPageSize();
            List<HzWorkProcess> list = hzWorkProcessPage.getResult();
            List<HzWorkProcessRespDTO> respDTOS = new ArrayList<>();
            for(HzWorkProcess process:list){
                HzWorkProcessRespDTO respDTO = new HzWorkProcessRespDTO();
                respDTO.setNo(++num);
                if(process.getFactoryCode() == null){
                    respDTO.setFactoryCode("1001");
                }else{
                    respDTO.setFactoryCode(process.getFactoryCode());
                }
                respDTO.setpBurn(process.getpBurn());
                respDTO.setpCount(process.getpCount());
                respDTO.setpDirectLabor(process.getpDirectLabor());
                respDTO.setpIndirectLabor(process.getpIndirectLabor());
                respDTO.setpMachineMaterialLabor(process.getpMachineMaterialLabor());
                respDTO.setpMachineLabor(process.getpMachineLabor());
                respDTO.setpOtherCost(process.getpOtherCost());
                respDTO.setpProcedureCode(process.getpProcedureCode());
                respDTO.setpProcedureDesc(process.getpProcedureDesc());
                respDTO.setpWorkCode(process.getWorkCenterCode());
                respDTO.setpWorkDesc(process.getWorkCenterDesc());
                respDTO.setMaterielId(process.getPuid());
                respDTO.setpMaterielCode(process.getpMaterielCode());
                respDTO.setpMaterielDesc(process.getpMaterielDesc());
                respDTO.setControlCode(process.getControlCode());
                respDTO.setPurpose(process.getPurpose());
                respDTO.setState(process.getState());
                respDTOS.add(respDTO);
            }
            return new Page<>(hzWorkProcessPage.getPageNumber(),hzWorkProcessPage.getPageSize(),hzWorkProcessPage.getTotalCount(),respDTOS);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public HzWorkProcessRespDTO findHzWorkProcess(String materielId,String projectId) {
        try {
            HzWorkProcess hzWorkProcess =hzWorkProcedureDAO.getHzWorkProcess(materielId,projectId);
            if(hzWorkProcess != null){
                HzWorkProcessRespDTO respDTO   = new HzWorkProcessRespDTO();
                respDTO.setFactoryCode(hzWorkProcess.getFactoryCode());
                respDTO.setMaterielId(hzWorkProcess.getPuid());
                respDTO.setpBurn(hzWorkProcess.getpBurn());
                respDTO.setpCount(hzWorkProcess.getpCount());
                respDTO.setpDirectLabor(hzWorkProcess.getpDirectLabor());
                respDTO.setpIndirectLabor(hzWorkProcess.getpIndirectLabor());
                respDTO.setpMachineMaterialLabor(hzWorkProcess.getpMachineMaterialLabor());
                respDTO.setpMachineLabor(hzWorkProcess.getpMachineLabor());
                respDTO.setpOtherCost(hzWorkProcess.getpOtherCost());
                respDTO.setpProcedureCode(hzWorkProcess.getpProcedureCode());
                respDTO.setpProcedureDesc(hzWorkProcess.getpProcedureDesc());
                respDTO.setpWorkCode(hzWorkProcess.getWorkCenterCode());
                respDTO.setpWorkDesc(hzWorkProcess.getWorkCenterDesc());
                respDTO.setPuid(hzWorkProcess.getPuid());
                respDTO.setpMaterielCode(hzWorkProcess.getpMaterielCode());
                respDTO.setpMaterielDesc(hzWorkProcess.getpMaterielDesc());
                respDTO.setControlCode(hzWorkProcess.getControlCode());
                respDTO.setPurpose(hzWorkProcess.getPurpose());
                respDTO.setState(hzWorkProcess.getState());
                respDTO.setpWorkPuid(hzWorkProcess.getpWorkPuid());
                respDTO.setIsSent(hzWorkProcess.getIsSent());
                return respDTO;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public OperateResultMessageRespDTO applyMbomDataToHzMaterielOneKey(ApplyMbomDataTOHzMaterielReqDTO reqDTO) {
        List<HzMbomRecordRespDTO> respDTOList = reqDTO.getMbomRecordRespDTOS();
        OperateResultMessageRespDTO operateResultMessageRespDTO= new OperateResultMessageRespDTO();
        Integer type = reqDTO.getType();
        String projectId = reqDTO.getProjectId();
        if(type == null || projectId == null || "".equals(projectId) ){
            operateResultMessageRespDTO.setErrMsg("非法操作！");
            operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
            return operateResultMessageRespDTO;
        }
        try{
            List<HzMaterielRecord> materielRecords = new ArrayList<>();
            if(ListUtil.isNotEmpty(respDTOList)){
                for(HzMbomRecordRespDTO respDTO :respDTOList){
                    Map<String,Object> map = new HashMap<>();
                    map.put("projectId",projectId);
                    map.put("materielResourceId",respDTO.getPuid());
                    boolean exist = hzMaterielDAO.HzMaterielIsExist(map);
                    if(exist){
                        operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                        operateResultMessageRespDTO.setErrMsg("物料号："+respDTO.getLineId()+"已存在发送记录，请检查后重新发送！");
                        return operateResultMessageRespDTO;
                    }
                    HzMaterielRecord record = new HzMaterielRecord();
                    record.setpMaterielCode(respDTO.getLineId());
                    record.setMaterielResourceId(respDTO.getpBomLinePartResource());
                    record.setpMaterielDesc(respDTO.getpBomLinePartName());
                    record.setType(type);
                    record.setpPertainToProjectPuid(projectId);
                    record.setMaterielResourceId(respDTO.getPuid());
                    record.setPuid(UUID.randomUUID().toString());
                    record.setpMaterielDescEn(respDTO.getpBomLinePartEnName());
                    materielRecords.add(record);
                }
                int i = hzMaterielDAO.insertList(materielRecords);
                if(i>0){
                    return OperateResultMessageRespDTO.getSuccessResult();
                }
            }
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public int doUpdateByBatch(Map<String, Object> map) {
        return hzWorkProcedureDAO.updateSendFlag(map);
    }
}
