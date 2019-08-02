/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.integrate;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public interface ISynFeatureService {

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
    JSONObject synAllByProjectPuid(String projectPuid);

    /**
     * 添加特性值
     *
     * @param features
     * @return
     */
    JSONObject addFeature(List<HzFeatureValue> features) throws Exception;

    /**
     * 更新特性值
     *
     * @param features
     * @return
     */
    JSONObject updateFeature(List<HzFeatureValue> features) throws Exception;

    /**
     * 删除特性值
     *
     * @param features
     * @return
     */
    JSONObject deleteFeature(List<HzFeatureValue> features) throws Exception;

    /**
     * 先删除后添加ERP
     *
     * @param projectPuid
     * @return
     */
    JSONObject deleteThenAdd(String projectPuid);

}
