/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.bom;

import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.dao.pojo.interaction.HzSingleVehicles;
import com.alibaba.fastjson.JSONObject;
import cn.net.connor.hozon.services.bean.SingleVehicleCheckStatus;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzSingleVehiclesReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;

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
     *
     * @param projectId
     * @return
     */
    List<HzSingleVehiclesRespDTO> singleVehiclesList(String projectId);

    /**
     * 更改单车信息
     *
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateSingleVehicle(UpdateHzSingleVehiclesReqDTO reqDTO);

    /**
     * 从配置中同步单车信息
     *
     * @param projectId
     * @return
     */
    WriteResultRespDTO refreshSingleVehicle(String projectId);

    /**
     * 获取单条单车信息
     *
     * @param projectId
     * @param id
     * @return
     */
    HzSingleVehiclesRespDTO getSingleVehiclesById(String projectId, Long id);

    /**
     * 单车用量标题
     *
     * @param projectId
     * @return
     */
    LinkedHashMap<String, String> singleVehDosageTitle(String projectId);

    /**
     * 单车用量
     *
     * @param bytes
     * @return
     */
    JSONObject singleVehDosage(byte[] bytes, List<HzCfg0ModelRecord> list, JSONObject object);

    /**
     * 单车用量
     *
     * @param vehNum
     * @param list
     * @param object
     * @return
     */
    JSONObject singleVehNum(String vehNum, List<HzCfg0ModelRecord> list, JSONObject object);

    /**
     * 单车用量
     *
     * @param vehNum
     * @param list
     * @return
     */
    JSONObject singleVehNum(String vehNum, List<HzCfg0ModelRecord> list);

    JSONObject sendSap(List<HzSingleVehicles> hzSingleVehicles);

    JSONObject deleteSap(List<HzSingleVehicles> hzSingleVehicles);

    /**
     * 获取到当前选择的单车的2Y与特性值对应数据
     *
     * 但是2Y与特性如果选择的不对，有可能出现2条特性值对应的特性与不同的2Y对应关系，这种情况就是重复选用相同功能的不同组件造成
     *
     * @param projectId 项目ID
     * @param vehiclesId 单车ID
     * @return
     */
    SingleVehicleCheckStatus checkStatus(String projectId, Long vehiclesId);

    /**
     * 查询项目下所有的单车信息
     * @param projectId
     * @return
     */
    List<HzSingleVehicles> selectByProjectUid(String projectId);

    void postCheck(List<HzSingleVehicles> hzSingleVehicles, String projectId);
}
