package com.connor.hozon.bom.resources.service.bom.impl;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielBasicDao;
import com.connor.hozon.bom.interaction.dao.HzSingleVehiclesDao;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzSingleVehiclesReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzSingleVehiclesFactory;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzDerivativeMaterielBasic;
import sql.pojo.interaction.HzSingleVehicles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/9/26
 * @Description:
 */
@Service("HzSingleVehiclesServices")
public class HzSingleVehiclesServicesImpl implements HzSingleVehiclesServices {

    @Autowired
    private HzDerivativeMaterielBasicDao hzDerivativeMaterielBasicDao;

    @Autowired
    private HzSingleVehiclesDao hzSingleVehiclesDao;
    @Override
    public List<HzSingleVehiclesRespDTO> singleVehiclesList(String projectId) {

        try {
            List<HzSingleVehiclesRespDTO> vehiclesRespDTOS = new ArrayList<>();
            if(StringUtil.isEmpty(projectId)){
                return vehiclesRespDTOS;
            }
            List<HzSingleVehicles> hzSingleVehicles = hzSingleVehiclesDao.selectByProjectUid(projectId);
            if(ListUtil.isNotEmpty(hzSingleVehicles)){
                for(HzSingleVehicles vehicles :hzSingleVehicles){
                    HzSingleVehiclesRespDTO respDTO = HzSingleVehiclesFactory.singleVehiclesToRespDTO(vehicles);
                    vehiclesRespDTOS.add(respDTO);
                }
                return vehiclesRespDTOS;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OperateResultMessageRespDTO updateSingleVehicle(UpdateHzSingleVehiclesReqDTO reqDTO) {
        try {
            if(!PrivilegeUtil.writePrivilege()){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            HzSingleVehicles hzSingleVehicles = HzSingleVehiclesFactory.updateReqDTOSingleVehicles(reqDTO);
            int i = hzSingleVehiclesDao.updateSingleVehicles(hzSingleVehicles);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
            return OperateResultMessageRespDTO.getFailResult();
        }catch (Exception e){
            e.printStackTrace();
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public OperateResultMessageRespDTO refreshSingleVehicle(String projectId) {
        if(!PrivilegeUtil.writePrivilege()){
            return OperateResultMessageRespDTO.getFailPrivilege();
        }
        HzDerivativeMaterielBasic basic = new HzDerivativeMaterielBasic();
        basic.setDmbProjectUid(projectId);
        Map<String, Object> params = new HashMap<>();
        params.put("basic", basic);
        try {
            List<HzSingleVehicles> vehicles = hzSingleVehiclesDao.selectOrgByProjectUid(projectId);
            List<HzSingleVehicles> singleVehicles = new ArrayList<>();
            List<HzDerivativeMaterielBasic> basics = hzDerivativeMaterielBasicDao.selectByProjectUid(params);
            if(ListUtil.isNotEmpty(basics) && ListUtil.isNotEmpty(vehicles)){
                for(HzSingleVehicles vehicles1:vehicles){
                    HzSingleVehicles hzSingleVehicles = new HzSingleVehicles();
                    for(HzDerivativeMaterielBasic materielBasic : basics){
                        if(vehicles1.getSvlDmbId().equals(materielBasic.getId())){
                            if(StringUtil.isEmpty(materielBasic.getDmbModelFeatureUid())){
                                OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
                                respDTO.setErrMsg("未找到相关配置项数据！");
                                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                                return respDTO;
                            }
                            //查重 存在的不在进行添加
                            boolean b = hzSingleVehiclesDao.checkExist(projectId,materielBasic.getDmbModelFeatureUid());
                            if(!b){
                                hzSingleVehicles.setSvlDmbId(vehicles1.getSvlDmbId());
                                hzSingleVehicles.setSvlCfgMaterialUid(materielBasic.getDmbModelFeatureUid());
                                hzSingleVehicles.setSvlProjectUid(projectId);
                                singleVehicles.add(hzSingleVehicles);
                                break;
                            }else {
                                break;
                            }
                        }
                    }
                }
                if(ListUtil.isNotEmpty(singleVehicles)){
                    int i = hzSingleVehiclesDao.insertList(singleVehicles);
                    if(i>0){
                        return OperateResultMessageRespDTO.getSuccessResult();
                    }
                    return OperateResultMessageRespDTO.getFailResult();
                }
                return OperateResultMessageRespDTO.getSuccessResult();
            }
            return OperateResultMessageRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public HzSingleVehiclesRespDTO getSingleVehiclesById(String projectId, Long id) {
        try {
            HzSingleVehicles hzSingleVehicles = hzSingleVehiclesDao.getSingleVehiclesById(projectId,id);
            if(hzSingleVehicles != null){
                return HzSingleVehiclesFactory.singleVehiclesToRespDTO(hzSingleVehicles);
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
