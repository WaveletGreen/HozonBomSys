/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.configuration.derivative;

import cn.net.connor.hozon.common.entity.QueryBase;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzCfg0ModelFeature;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzComposeDelDto;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.services.request.configuration.derivative.HzDerivativeRequestDTO;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 13:17
 * @Modified By:
 */
public interface HzDerivativeService {
    /**
     * 保存单个衍生物料信息
     *
     * @param hzDerivativeRequestDTO
     * @param results
     */
    void saveDerivative(HzDerivativeRequestDTO hzDerivativeRequestDTO, JSONObject results);

    /**
     * 查询所有选配的
     *
     * @param projectUid
     * @param queryBase
     * @return
     */
    Map<String, Object> loadProjectsDerivatives(String projectUid, QueryBase queryBase);

    /**
     * 标记基本信息,自动在查询的时候进行标记，如果基本信息数据为空，且有平台、车型年、版型等可进行自动生成基本信息的数据，则将自动更新基本信息数据
     *
     * @param feature
     * @param _result
     * @param modelRecord
     * @param inner
     */
    @Transactional
    void remarkBaseInfo(HzCfg0ModelFeature feature, Map<String, Object> _result, HzCfg0ModelRecord modelRecord, HzFeatureValue inner);

    /**
     * 删除衍生物料
     *
     * @param delDtos
     * @param result
     */
    void deleteDerivative(List<HzComposeDelDto> delDtos, JSONObject result);

    /**
     * 自动创建并保存项目下的所有衍生物料信息
     *
     * @param projectPuid
     * @return
     */
    JSONObject saveProjectDerivatives(String projectPuid);

    /**
     * 状态更新，标记衍生物料状态为删除
     *
     * @param delDtos
     * @return
     */
    int deleteVehicleFake(List<HzComposeDelDto> delDtos);
}
