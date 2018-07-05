package com.connor.hozon.bom.resources.service.work.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.AddHzProcessReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzProcessReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzWorkProcessRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkCenterDAO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzWorkProcessByPageQuery;
import com.connor.hozon.bom.resources.service.work.HzWorkProcessService;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.work.HzWorkCenter;
import sql.pojo.work.HzWorkProcedure;
import sql.pojo.work.HzWorkProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    @Override
    public OperateResultMessageRespDTO addHzWorkProcess(AddHzProcessReqDTO reqDTO) {
        try{
            User user = UserInfo.getUser();
            HzWorkProcedure hzWorkProcedure = new HzWorkProcedure();
            hzWorkProcedure.setMaterielId(reqDTO.getMaterielId());
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

            HzWorkCenter hzWorkCenter = hzWorkCenterDAO.findWorkCenter(reqDTO.getProjectId(),reqDTO.getpWorkCode());
            if(hzWorkCenter != null){
                hzWorkProcedure.setpWorkPuid(hzWorkCenter.getPuid());
            }else {
                String puid = UUID.randomUUID().toString();
                hzWorkCenter = new HzWorkCenter();
                hzWorkCenter.setPuid(puid);
                hzWorkCenter.setpWorkCode(reqDTO.getpWorkCode());
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
        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO deleteHzWorkProcess(String puid) {
        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public Page<HzWorkProcessRespDTO> findHzWorkProcessForPage(HzWorkProcessByPageQuery query) {
        try {
            Page<HzWorkProcess> hzWorkProcessPage = hzWorkProcedureDAO.findHzWorkProcessByPage(query);
            if(hzWorkProcessPage == null || hzWorkProcessPage.getResult() == null){
                return  new Page<>(query.getPage(),query.getLimit(),0);
            }
            int num = (query.getPage()-1)*query.getLimit();
            List<HzWorkProcess> list = hzWorkProcessPage.getResult();
            List<HzWorkProcessRespDTO> respDTOS = new ArrayList<>();
            for(HzWorkProcess process:list){
                HzWorkProcessRespDTO respDTO = new HzWorkProcessRespDTO();
                respDTO.setNo(++num);
                respDTO.setFactoryCode(process.getFactoryCode());
                respDTO.setMaterielId(process.getPuid());
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
                respDTO.setPuid(process.getPuid());
                respDTO.setpMaterielCode(process.getpMaterielCode());
                respDTO.setpMaterielDesc(process.getpMaterielDesc());
                respDTOS.add(respDTO);
            }
            return new Page<>(query.getPage(),query.getLimit(),hzWorkProcessPage.getTotalCount(),respDTOS);
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
                return respDTO;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
