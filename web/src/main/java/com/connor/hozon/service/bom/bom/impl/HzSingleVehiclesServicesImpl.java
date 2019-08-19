/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.bom.impl;

import cn.net.connor.hozon.common.util.SerializeUtils;
import cn.net.connor.hozon.dao.dao.configuration.derivative.HzDerivativeMaterielBasicDao;
import cn.net.connor.hozon.dao.dao.interaction.FeatureBomLineRelationHistoryDao;
import cn.net.connor.hozon.dao.dao.interaction.SingleVehicleStatusDao;
import com.connor.hozon.dao.interaction.HzSingleVehiclesDao;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasic;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzMaterielCfgBean;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.dao.pojo.interaction.HzSingleVehicles;
import cn.net.connor.hozon.dao.pojo.interaction.SingleVehicleBomRelation;
import cn.net.connor.hozon.dao.pojo.interaction.SingleVehicleStatus;
import cn.net.connor.hozon.services.service.configuration.fullConfigSheet.impl.HzCfg0ModelServiceImpl;
import cn.net.connor.hozon.common.util.ListUtils;
import com.alibaba.fastjson.JSONObject;
import integration.service.integrate.impl.SynMaterielCfgService;
import cn.net.connor.hozon.services.bean.SingleVehicleCheckStatus;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzSingleVehiclesReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzSingleVehiclesFactory;
import com.connor.hozon.service.bom.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private HzCfg0ModelServiceImpl hzCfg0ModelServiceImpl;

    @Autowired
    private SynMaterielCfgService synMaterielCfgService;

    @Autowired
    private FeatureBomLineRelationHistoryDao featureBomLineRelationHistoryDao;

    @Override
    public List<HzSingleVehiclesRespDTO> singleVehiclesList(String projectId) {

        try {
            List<HzSingleVehiclesRespDTO> vehiclesRespDTOS = new ArrayList<>();
            if (StringUtil.isEmpty(projectId)) {
                return vehiclesRespDTOS;
            }
            List<HzSingleVehicles> hzSingleVehicles = hzSingleVehiclesDao.selectByProjectUid(projectId);
            if (ListUtils.isNotEmpty(hzSingleVehicles)) {
                for (HzSingleVehicles vehicles : hzSingleVehicles) {
                    HzSingleVehiclesRespDTO respDTO = HzSingleVehiclesFactory.singleVehiclesToRespDTO(vehicles);
                    vehiclesRespDTOS.add(respDTO);
                }
                return vehiclesRespDTOS;
            }
            return null;
        } catch (Exception e) {
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
            if (i > 0) {
                return WriteResultRespDTO.getSuccessResult();
            }
            return WriteResultRespDTO.getFailResult();
        } catch (Exception e) {
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
            if (ListUtils.isNotEmpty(basics) && ListUtils.isNotEmpty(vehicles)) {
                for (HzSingleVehicles vehicles1 : vehicles) {
                    HzSingleVehicles hzSingleVehicles = new HzSingleVehicles();
                    for (HzDerivativeMaterielBasic materielBasic : basics) {
                        if (null == vehicles1.getSvlDmbId()) {
                            vehicles1.setSvlDmbId(0L);
                        }
                        if (vehicles1.getSvlDmbId().equals(materielBasic.getId())) {
                            if (StringUtil.isEmpty(materielBasic.getDmbModelFeatureUid())) {
//                                WriteResultRespDTO respDTO = new WriteResultRespDTO();
//                                respDTO.setErrMsg("未找到相关配置项数据！");
//                                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
//                                return respDTO;
                                continue;
                            }
                            //查重 存在的不在进行添加
                            boolean b = hzSingleVehiclesDao.checkExist(projectId, materielBasic.getDmbModelFeatureUid());
                            if (!b) {
                                hzSingleVehicles.setSvlDmbId(vehicles1.getSvlDmbId());
                                hzSingleVehicles.setSvlCfgMaterialUid(materielBasic.getDmbModelFeatureUid());
                                hzSingleVehicles.setSvlProjectUid(projectId);
                                singleVehicles.add(hzSingleVehicles);
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
                if (ListUtils.isNotEmpty(singleVehicles)) {
                    int i = hzSingleVehiclesDao.insertList(singleVehicles);
                    if (i > 0) {
                        return WriteResultRespDTO.getSuccessResult();
                    }
                    return WriteResultRespDTO.getFailResult();
                }
                return WriteResultRespDTO.getSuccessResult();
            }
            return WriteResultRespDTO.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public HzSingleVehiclesRespDTO getSingleVehiclesById(String projectId, Long id) {
        try {
            HzSingleVehicles hzSingleVehicles = hzSingleVehiclesDao.getSingleVehiclesById(projectId, id);
            if (hzSingleVehicles != null) {
                return HzSingleVehiclesFactory.singleVehiclesToRespDTO(hzSingleVehicles);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LinkedHashMap<String, String> singleVehDosageTitle(String projectId) {
        //获取该项目下的所有车型模型
        List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelServiceImpl.doSelectByProjectPuid(projectId);
        LinkedHashMap<String, String> map = new LinkedHashMap();
        if (ListUtils.isNotEmpty(hzCfg0ModelRecords)) {
            Set<HzCfg0ModelRecord> set = new HashSet<>(hzCfg0ModelRecords);
            Iterator iterator = set.iterator();
            for (int i = 0; i < set.size(); i++) {
                HzCfg0ModelRecord record = (HzCfg0ModelRecord) iterator.next();
                if (null == record.getObjectName()) {
                    continue;
                }
                map.put(record.getObjectName() + "title" + i, record.getObjectName() + "(单车用量)");
            }
        }
        return map;
    }

    @Override
    public JSONObject singleVehDosage(byte[] bytes, List<HzCfg0ModelRecord> list, JSONObject object) {
        if (null == bytes) {
            return object;
        }
        Object obj = SerializeUtils.unserialize(bytes);
        if (ListUtils.isNotEmpty(list)) {
            if (obj instanceof Map) {
                Map<String, Object> map = (Map) obj;
                if (map.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (list.get(i).getPuid().equals(entry.getKey())) {
                                object.put("title" + i, entry.getValue());
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
    public JSONObject singleVehNum(String vehNum, List<HzCfg0ModelRecord> list, JSONObject object) {
        if (ListUtils.isEmpty(list)) {
            return object;
        }
        Set<HzCfg0ModelRecord> set = new HashSet<>(list);
        Iterator<HzCfg0ModelRecord> iterator = set.iterator();

        for (int i = 0; i < set.size(); i++) {
            HzCfg0ModelRecord record = iterator.next();
            if (null == record.getObjectName()) {
                continue;
            }
            if (StringUtils.isNotBlank(vehNum)) {
                //智时版#12,360e#14,380pro#16   key:名字  value:value
                String[] strings = vehNum.split(",");
                boolean find = false;
                for (String veh : strings) {
                    String vehName = veh.split("#")[0];
                    String vehNumber = veh.split("#")[1];
                    if (record.getObjectName().equals(vehName)) {
                        object.put(vehName + "title" + i, vehNumber);
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    object.put(record.getObjectName() + "title" + i, "");
                }
            } else {
                object.put(record.getObjectName() + "title" + i, "");
            }
        }
        return object;
    }

    @Override
    public JSONObject singleVehNum(String vehNum, List<HzCfg0ModelRecord> list) {
        JSONObject object = new JSONObject();
        if (ListUtils.isEmpty(list)) {
            return object;
        }
        Set<HzCfg0ModelRecord> set = new HashSet<>(list);
        Iterator<HzCfg0ModelRecord> iterator = set.iterator();

        for (int i = 0; i < set.size(); i++) {
            HzCfg0ModelRecord record = iterator.next();
            if (null == record.getObjectName()) {
                continue;
            }
            if (StringUtils.isNotBlank(vehNum)) {
                //智时版#12,360e#14,380pro#16   key:名字  value:value
                String[] strings = vehNum.split(",");
                boolean find = false;
                for (String veh : strings) {
                    String vehName = veh.split("#")[0];
                    String vehNumber = veh.split("#")[1];
                    if (record.getObjectName().equals(vehName)) {
                        object.put(vehName, vehNumber);
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    object.put(record.getObjectName(), "");
                }
            } else {
                object.put(record.getObjectName(), "");
            }
        }
        return object;
    }

    @Override
    public JSONObject sendSap(List<HzSingleVehicles> hzSingleVehicles) {
        List<HzSingleVehicles> hzSingleVehiclesSap = hzSingleVehiclesDao.selectByIds(hzSingleVehicles);
        List<HzMaterielCfgBean> hzMaterielCfgBeans = new ArrayList<>();
        for (HzSingleVehicles hzSingleVehicles1 : hzSingleVehiclesSap) {
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
        for (HzSingleVehicles hzSingleVehicles1 : hzSingleVehiclesSap) {
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

    @Override
    public SingleVehicleCheckStatus checkStatus(String projectId, Long vehiclesId) {
        List<SingleVehicleBomRelation> list = featureBomLineRelationHistoryDao.selectHistory(projectId, vehiclesId);
        Set<String> featureValueSet = new HashSet<>(64);
        SingleVehicleCheckStatus status = new SingleVehicleCheckStatus();
        SingleVehicleCheckStatus.SingleVehicleStatusCode statusCode = SingleVehicleCheckStatus.SingleVehicleStatusCode.Ok;
        for (SingleVehicleBomRelation rel :
                list) {
            //存在重复项
            if (featureValueSet.contains(rel.getFeatureValueCode())) {
                rel.setStatus("<span style='color:red'>特性值选择重复<br>请到全配置BOM一级清单确认重复项</span>");//重复项
                statusCode = SingleVehicleCheckStatus.SingleVehicleStatusCode.Duplicate;//重复项
            } else {
                rel.setStatus("OK");//OK
                featureValueSet.add(rel.getFeatureValueCode());
            }
        }
        status.setRel(list);
        status.setStatus(statusCode);
        return status;
    }

    @Override
    public List<HzSingleVehicles> selectByProjectUid(String projectId) {
        return hzSingleVehiclesDao.selectByProjectUid(projectId);
    }

    /**
     * 单车状态dao层
     */
    @Autowired
    SingleVehicleStatusDao singleVehicleStatusDao;

    @Override
    public void postCheck(List<HzSingleVehicles> vehicle, String projectId) {
        List<SingleVehicleStatus> statusList = new LinkedList();
        for (HzSingleVehicles v :
                vehicle) {
            SingleVehicleCheckStatus status = checkStatus(projectId, v.getId());
            SingleVehicleStatus vehicleStatus = new SingleVehicleStatus();
            vehicleStatus.setProjectId(projectId);
            vehicleStatus.setSvgId(v.getId());
            vehicleStatus.setStatus(status.getStatus().getCode());
            statusList.add(vehicleStatus);
        }

        if (!org.thymeleaf.util.ListUtils.isEmpty(statusList)) {
            //先删除以前的数据
            singleVehicleStatusDao.deleteByProjectId(projectId);
            singleVehicleStatusDao.insertList(statusList);
        }
    }

}
