package com.connor.hozon.bom.resources.service.bom;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzSingleVehiclesReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.dao.pojo.interaction.HzSingleVehicles;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/9/26
 * @Description:
 */
public interface HzSingleVehiclesServices {
    /**
     * 获取单车清单列表
     * @param projectId
     * @return
     */
    List<HzSingleVehiclesRespDTO> singleVehiclesList(String projectId);

    /**
     * 更改单车信息
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateSingleVehicle(UpdateHzSingleVehiclesReqDTO reqDTO);

    /**
     * 从配置中同步单车信息
     * @param projectId
     * @return
     */
    WriteResultRespDTO refreshSingleVehicle(String projectId);

    /**
     * 获取单条单车信息
     * @param projectId
     * @param id
     * @return
     */
    HzSingleVehiclesRespDTO getSingleVehiclesById(String projectId,Long id);

    /**
     * 单车用量标题
     * @param projectId
     * @return
     */
    LinkedHashMap<String,String> singleVehDosageTitle(String projectId);

    /**
     * 单车用量
     * @param bytes
     * @return
     */
    JSONObject singleVehDosage(byte[] bytes, List<HzCfg0ModelRecord> list,JSONObject object);

    /**
     * 单车用量
     * @param vehNum
     * @param list
     * @param object
     * @return
     */
    JSONObject singleVehNum(String vehNum,List<HzCfg0ModelRecord> list,JSONObject object);

    /**
     *单车用量
     * @param vehNum
     * @param list
     * @return
     */
    JSONObject singleVehNum(String vehNum,List<HzCfg0ModelRecord> list);

    JSONObject sendSap(List<HzSingleVehicles> hzSingleVehicles);

    JSONObject deleteSap(List<HzSingleVehicles> hzSingleVehicles);
}
