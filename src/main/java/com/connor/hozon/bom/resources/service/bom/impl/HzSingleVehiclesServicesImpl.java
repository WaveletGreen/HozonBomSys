package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzDerivativeMaterielBasicDao;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0ModelService;
import com.connor.hozon.bom.bomSystem.service.integrate.SynMaterielCfgService;
import com.connor.hozon.bom.interaction.dao.HzSingleVehiclesDao;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzSingleVehiclesReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzSingleVehiclesFactory;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.derivative.HzDerivativeMaterielBasic;
import sql.pojo.cfg.derivative.HzMaterielCfgBean;
import sql.pojo.cfg.model.HzCfg0ModelRecord;
import sql.pojo.interaction.HzSingleVehicles;
import sql.redis.SerializeUtil;

import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/9/26
 * @Description:
 */
@Service("hzSingleVehiclesServices")
public class HzSingleVehiclesServicesImpl implements HzSingleVehiclesServices {

    @Autowired
    private HzDerivativeMaterielBasicDao hzDerivativeMaterielBasicDao;

    @Autowired
    private HzSingleVehiclesDao hzSingleVehiclesDao;


    @Autowired
    private HzCfg0ModelService hzCfg0ModelService;

    @Autowired
    private SynMaterielCfgService synMaterielCfgService;

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
    public WriteResultRespDTO updateSingleVehicle(UpdateHzSingleVehiclesReqDTO reqDTO) {
        try {
//            if(!PrivilegeUtil.writePrivilege()){
//                return WriteResultRespDTO.getFailPrivilege();
//            }
            HzSingleVehicles hzSingleVehicles = HzSingleVehiclesFactory.updateReqDTOSingleVehicles(reqDTO);
            int i = hzSingleVehiclesDao.updateSingleVehicles(hzSingleVehicles);
            if(i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
            return WriteResultRespDTO.getFailResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public WriteResultRespDTO refreshSingleVehicle(String projectId) {

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
                        if(null == vehicles1.getSvlDmbId()){
                            vehicles1.setSvlDmbId(0L);
                        }
                        if(vehicles1.getSvlDmbId().equals(materielBasic.getId())){
                            if(StringUtil.isEmpty(materielBasic.getDmbModelFeatureUid())){
//                                WriteResultRespDTO respDTO = new WriteResultRespDTO();
//                                respDTO.setErrMsg("未找到相关配置项数据！");
//                                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
//                                return respDTO;
                                continue;
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
                        return WriteResultRespDTO.getSuccessResult();
                    }
                    return WriteResultRespDTO.getFailResult();
                }
                return WriteResultRespDTO.getSuccessResult();
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
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

    @Override
    public LinkedHashMap<String, String> singleVehDosageTitle(String projectId) {
        //获取该项目下的所有车型模型
        List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(projectId);
        LinkedHashMap map = new LinkedHashMap();
        if(ListUtil.isNotEmpty(hzCfg0ModelRecords)){
            for(int i = 0;i<hzCfg0ModelRecords.size();i++){
                map.put("title"+i,hzCfg0ModelRecords.get(i).getObjectName()+"(单车用量)");
            }
        }
        return map;
    }

    @Override
    public JSONObject singleVehDosage(byte[] bytes, List<HzCfg0ModelRecord> list, JSONObject object) {
        if(null == bytes){
            return object;
        }
        Object obj = SerializeUtil.unserialize(bytes);
        if(ListUtil.isNotEmpty(list)){
            if(obj instanceof Map){
                Map<String,Object> map = (Map)obj;
                if(map.size()>0){
                    for(int i = 0;i<list.size();i++){
                        for(Map.Entry<String,Object> entry:map.entrySet()){
                            if(list.get(i).getPuid().equals(entry.getKey())){
                                object.put("title"+i,entry.getValue());
                                break;
                            }
                        }
                    }
                }
            }
        }
        return object;
    }

    @Override
    public JSONObject sendSap(List<HzSingleVehicles> hzSingleVehicles) {
        List<HzSingleVehicles> hzSingleVehiclesSap = hzSingleVehiclesDao.selectByIds(hzSingleVehicles);
        List<HzMaterielCfgBean> hzMaterielCfgBeans = new ArrayList<>();
        for(HzSingleVehicles hzSingleVehicles1 : hzSingleVehiclesSap){
            HzMaterielCfgBean hzMaterielCfgBean = new HzMaterielCfgBean();
            hzMaterielCfgBean.setpBrandCode(hzSingleVehicles1.getBrandCode());
            hzMaterielCfgBean.setpBrandName(hzSingleVehicles1.getBrandName());
            hzMaterielCfgBean.setpPlatformCode(hzSingleVehicles1.getPlatformCode());
            hzMaterielCfgBean.setpPlatformName(hzSingleVehicles1.getPlatformName());
            hzMaterielCfgBean.setpVehicleCode(hzSingleVehicles1.getVehicleCode());
            hzMaterielCfgBean.setpVehicleName(hzSingleVehicles1.getVehicleName());
            hzMaterielCfgBean.setpInColorCode(hzSingleVehicles1.getSvlInnerColorCode());
            hzMaterielCfgBean.setpInColorName(hzSingleVehicles1.getSvlInnerColorName());
            hzMaterielCfgBean.setpColorCode(hzSingleVehicles1.getColorCode());
            hzMaterielCfgBean.setpColorName(hzSingleVehicles1.getColorName());
            hzMaterielCfgBean.setpBatteryModel(hzSingleVehicles1.getSvlBatteryCode());
            hzMaterielCfgBean.setpMotorModel(hzSingleVehicles1.getSvlMotorCode());
            hzMaterielCfgBean.setObjectName(hzSingleVehicles1.getSvlMaterialCode());
            hzMaterielCfgBean.setIsSent(hzSingleVehicles1.getIsSend());
            hzMaterielCfgBeans.add(hzMaterielCfgBean);
        }
        synMaterielCfgService.addMaterielCfg(hzMaterielCfgBeans);
        return null;
    }

    @Override
    public JSONObject deleteSap(List<HzSingleVehicles> hzSingleVehicles) {
        List<HzSingleVehicles> hzSingleVehiclesSap = hzSingleVehiclesDao.selectByIds(hzSingleVehicles);
        List<HzMaterielCfgBean> hzMaterielCfgBeans = new ArrayList<>();
        for(HzSingleVehicles hzSingleVehicles1 : hzSingleVehiclesSap){
            HzMaterielCfgBean hzMaterielCfgBean = new HzMaterielCfgBean();
            hzMaterielCfgBean.setpBrandCode(hzSingleVehicles1.getBrandCode());
            hzMaterielCfgBean.setpBrandName(hzSingleVehicles1.getBrandName());
            hzMaterielCfgBean.setpPlatformCode(hzSingleVehicles1.getPlatformCode());
            hzMaterielCfgBean.setpPlatformName(hzSingleVehicles1.getPlatformName());
            hzMaterielCfgBean.setpVehicleCode(hzSingleVehicles1.getVehicleCode());
            hzMaterielCfgBean.setpVehicleName(hzSingleVehicles1.getVehicleName());
            hzMaterielCfgBean.setpInColorCode(hzSingleVehicles1.getSvlInnerColorCode());
            hzMaterielCfgBean.setpInColorName(hzSingleVehicles1.getSvlInnerColorName());
            hzMaterielCfgBean.setpColorCode(hzSingleVehicles1.getColorCode());
            hzMaterielCfgBean.setpColorName(hzSingleVehicles1.getColorName());
            hzMaterielCfgBean.setpBatteryModel(hzSingleVehicles1.getSvlBatteryCode());
            hzMaterielCfgBean.setpMotorModel(hzSingleVehicles1.getSvlMotorCode());
            hzMaterielCfgBean.setObjectName(hzSingleVehicles1.getSvlMaterialCode());
            hzMaterielCfgBean.setIsSent(hzSingleVehicles1.getIsSend());
            hzMaterielCfgBeans.add(hzMaterielCfgBean);
        }
        return synMaterielCfgService.deleteMaterielCfg(hzMaterielCfgBeans);
    }

}
