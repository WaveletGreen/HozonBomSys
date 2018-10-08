package com.connor.hozon.bom.resources.service.work.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzProcessReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.ApplyMbomDataTOHzMaterielReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzProcessReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzWorkProcessRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.domain.query.HzWorkProcessByPageQuery;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkCenterDAO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.work.HzWorkProcessService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.apache.commons.collections.map.HashedMap;
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
            if(!PrivilegeUtil.writePrivilege()){
               return OperateResultMessageRespDTO.getFailPrivilege();
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
        User user = UserInfo.getUser();
        if(!PrivilegeUtil.writePrivilege()){
            return OperateResultMessageRespDTO.getFailPrivilege();
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
                if(!puid.equals(hzMaterielRecord.getpFactoryPuid())){
                    hzMaterielRecord.setpFactoryPuid(puid);
                    int i = hzMaterielDAO.update(hzMaterielRecord);
                    if(i<0){
                        return OperateResultMessageRespDTO.getFailResult();
                    }
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
            if(ListUtil.isNotEmpty(hzWorkCenterList)){
                HzWorkCenter workCenter = hzWorkCenterList.get(0);
                hzWorkProcedure.setpWorkPuid(hzWorkCenterList.get(0).getPuid());
                HzWorkCenter hzWorkCenter = new HzWorkCenter();
                hzWorkCenter.setPuid(workCenter.getPuid());
                hzWorkCenter.setpWorkCode(reqDTO.getpWorkCode());
                hzWorkCenter.setpWorkDesc(reqDTO.getpWorkDesc());
                hzWorkCenter.setProjectId(reqDTO.getProjectId());
                if(!workCenter.equals(hzWorkCenter)){
                    hzWorkCenterDAO.update(hzWorkCenter);
                }
            }else {
                String puid = UUID.randomUUID().toString();
                HzWorkCenter hzWorkCenter = new HzWorkCenter();
                hzWorkCenter.setPuid(puid);
                hzWorkCenter.setpWorkCode(reqDTO.getpWorkCode());
                hzWorkCenter.setpWorkDesc(reqDTO.getpWorkDesc());
                hzWorkCenter.setProjectId(reqDTO.getProjectId());
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
            if(!hzMaterielRecord.getpFactoryPuid().equals(puid)){
                hzMaterielRecord.setpFactoryPuid(puid);
                int i = hzMaterielDAO.update(hzMaterielRecord);
                if(i<0){
                    return OperateResultMessageRespDTO.getFailResult();
                }
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
        if(ListUtil.isNotEmpty(hzWorkCenterList)){
            HzWorkCenter workCenter = hzWorkCenterList.get(0);
            hzWorkProcedure.setpWorkPuid(hzWorkCenterList.get(0).getPuid());
            HzWorkCenter hzWorkCenter = new HzWorkCenter();
            hzWorkCenter.setpWorkCode(reqDTO.getpWorkCode());
            hzWorkCenter.setpWorkDesc(reqDTO.getpWorkDesc());
            hzWorkCenter.setProjectId(reqDTO.getProjectId());
            hzWorkCenter.setPuid(hzWorkCenterList.get(0).getPuid());
            if(!workCenter.equals(hzWorkCenter)){
                hzWorkCenterDAO.update(hzWorkCenter);
            }
        }else {
            String puid = UUID.randomUUID().toString();
            HzWorkCenter hzWorkCenter = new HzWorkCenter();
            hzWorkCenter.setPuid(puid);
            hzWorkCenter.setpWorkCode(reqDTO.getpWorkCode());
            hzWorkCenter.setpWorkDesc(reqDTO.getpWorkDesc());
            hzWorkCenter.setProjectId(reqDTO.getProjectId());
            hzWorkCenterDAO.insert(hzWorkCenter);
            hzWorkProcedure.setpWorkPuid(puid);
        }
        int i =  hzWorkProcedureDAO.update(hzWorkProcedure);
        if(i>0){
            return OperateResultMessageRespDTO.getSuccessResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }

    public OperateResultMessageRespDTO updateHzWorkProcess2(UpdateHzProcessReqDTO reqDTO){
        User user = UserInfo.getUser();
        HzWorkProcedure hzWorkProcedure = new HzWorkProcedure();

        hzWorkProcedure.setProjectId(reqDTO.getProjectId());
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
        if(ListUtil.isNotEmpty(hzWorkCenterList)){
            HzWorkCenter workCenter = hzWorkCenterList.get(0);
            hzWorkProcedure.setpWorkPuid(hzWorkCenterList.get(0).getPuid());
            HzWorkCenter hzWorkCenter = new HzWorkCenter();
            hzWorkCenter.setpWorkCode(reqDTO.getpWorkCode());
            hzWorkCenter.setpWorkDesc(reqDTO.getpWorkDesc());
            hzWorkCenter.setProjectId(reqDTO.getProjectId());
            hzWorkCenter.setPuid(hzWorkCenterList.get(0).getPuid());
            if(!workCenter.equals(hzWorkCenter)){
                hzWorkCenterDAO.update(hzWorkCenter);
            }
        }else {
            String puid = UUID.randomUUID().toString();
            HzWorkCenter hzWorkCenter = new HzWorkCenter();
            hzWorkCenter.setPuid(puid);
            hzWorkCenter.setpWorkCode(reqDTO.getpWorkCode());
            hzWorkCenter.setpWorkDesc(reqDTO.getpWorkDesc());
            hzWorkCenter.setProjectId(reqDTO.getProjectId());
            hzWorkCenterDAO.insert(hzWorkCenter);
            hzWorkProcedure.setpWorkPuid(puid);
        }
        int i =  hzWorkProcedureDAO.update2(hzWorkProcedure);
        if(i>0){
            return OperateResultMessageRespDTO.getSuccessResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO deleteHzWorkProcess(String puid) {
        boolean b = PrivilegeUtil.writePrivilege();
        if(!b){
            return OperateResultMessageRespDTO.getFailPrivilege();
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

    public OperateResultMessageRespDTO deleteHzWorkProcesses(Map<String, List<String>> datas){
        List<String> materielIds = datas.get("materielIds");
        List<String> procedureDesc = datas.get("procedureDesc");
        if(procedureDesc.size()==0||procedureDesc==null){
            return OperateResultMessageRespDTO.cantDelete();
        }
        for(String pd : procedureDesc){
            if(pd==null){
                return OperateResultMessageRespDTO.cantDelete();
            }
        }
        List<HzWorkProcedure> hzWorkProceduresDel = new ArrayList<HzWorkProcedure>();
        for(int i=0;i<materielIds.size();i++){
            HzWorkProcedure hzWorkProcedure = new HzWorkProcedure();
            hzWorkProcedure.setMaterielId(materielIds.get(i));
            hzWorkProcedure.setpProcedureDesc(procedureDesc.get(i));
            hzWorkProceduresDel.add(hzWorkProcedure);
        }
        try{
            int delNum = -1;
            if(hzWorkProceduresDel.size()>0&&hzWorkProceduresDel!=null){
                delNum = hzWorkProcedureDAO.deleteHzWorkProcesses(hzWorkProceduresDel);
            }
            if(delNum==hzWorkProceduresDel.size()){
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
    public Page<HzWorkProcessRespDTO> findHzWorkProcessForPage2(HzWorkProcessByPageQuery query) {
        try {
            Page<HzWorkProcess> hzWorkProcessPage = hzWorkProcedureDAO.findHzWorkProcessByPage2(query);
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
    public HzWorkProcessRespDTO findHzWorkProcess2(String materielId,String projectId, String procedureDesc) {
        try {
            HzWorkProcess hzWorkProcess =hzWorkProcedureDAO.getHzWorkProcess2(materielId,projectId, procedureDesc);
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

    @Override
    public int insertHzWorkProcedures(List<HzWorkProcedure> hzWorkProcedures) {
        return hzWorkProcedureDAO.insertHzWorkProcedures(hzWorkProcedures);
    }

    /**
     * 工艺路线初始化
     * @param projectId
     */
    @Override
    public void initProcess(String projectId) {
        //查询所有数据类型为11、21、71的物料
        List<HzMaterielRecord> hzMaterielRecords = hzMaterielDAO.findHzMaterielForProcess(projectId);
        if(hzMaterielRecords==null||hzMaterielRecords.size()==0){
            return;
        }
        //查询所有工艺路线
        List<HzWorkProcedure> hzWorkProcedures = hzWorkProcedureDAO.findHzWorkProcessByProjectId(projectId);
        List<HzMaterielRecord> hzMaterielRecordAddList = new ArrayList<HzMaterielRecord>();

        //过滤紧固件物料
        Iterator<HzMaterielRecord> iterator = hzMaterielRecords.iterator();
        while(iterator.hasNext()){
            HzMaterielRecord hzMaterielRecord = iterator.next();
            String materielCode = hzMaterielRecord.getpMaterielCode();
            try {
                String materielCodeHead = materielCode.substring(0, 1);
                String materielCodeHeadUp = materielCodeHead.toUpperCase();
                if("F".equals(materielCodeHeadUp)||"Q".equals(materielCodeHeadUp)){
                    iterator.remove();
                }
            }catch (Exception e){ }
        }
        //找出所有没有工艺路线的物料
        for(HzMaterielRecord hzMaterielRecord : hzMaterielRecords){
            boolean flag = false;
            for(HzWorkProcedure hzWorkProcedure : hzWorkProcedures){
                if(hzMaterielRecord.getPuid().equals(hzWorkProcedure.getMaterielId())){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                hzMaterielRecordAddList.add(hzMaterielRecord);
            }
        }
        //为物料生成工艺路线
        List<HzWorkProcedure> hzWorkProceduresAdd = new ArrayList<HzWorkProcedure>();
        for(HzMaterielRecord hzMaterielRecord : hzMaterielRecordAddList ){
            HzWorkProcedure hzWorkProcedure = new HzWorkProcedure();
            //puid
            String puid = UUID.randomUUID().toString();
            hzWorkProcedure.setPuid(puid);
            //项目ID
            hzWorkProcedure.setProjectId(projectId);
            //物料id
            hzWorkProcedure.setMaterielId(hzMaterielRecord.getPuid());
            //工厂
            hzWorkProcedure.setpFactoryId("1001");
            hzWorkProceduresAdd.add(hzWorkProcedure);
        }
        if(hzWorkProceduresAdd!=null&&hzWorkProceduresAdd.size()!=0){
            hzWorkProcedureDAO.insertHzWorkProcedures(hzWorkProceduresAdd);
        }
    }

    @Override
    public int deleteHzWorkProcessByMaterielIds(List<HzWorkProcedure> hzWorkProceduresDel) {
        return hzWorkProcedureDAO.deleteHzWorkProcessByMaterielIds(hzWorkProceduresDel);
    }

    @Override
    public List<String> queryProcessDesc(String puids) {
        List<String> puidList = new ArrayList<String>();
        String[] puidsArr = puids.split(",");
        for(String puid : puidsArr){
            puidList.add(puid);
        }
        List<String> processDescList = hzWorkProcedureDAO.queryProcessDescsByPuid(puidList);
        List<String> processDescHeadList = new ArrayList<String>();
        if(processDescList!=null&&processDescList.size()>0){

            for(String processDesc : processDescList){
                String processDescHead =  processDesc.split("-")[0];
                processDescHeadList.add(processDescHead );
            }
        }
        return processDescHeadList;
    }
}
