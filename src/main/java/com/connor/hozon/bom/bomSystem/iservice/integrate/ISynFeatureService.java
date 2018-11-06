/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.integrate;

import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.cfg0.HzCfg0Record;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
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
    JSONObject addFeature(List<HzCfg0Record> features) throws Exception;

    /**
     * 更新特性值
     *
     * @param projectPuid
     * @return
     */
    JSONObject updateFeature(String projectPuid);

    /**
     * 删除特性值
     *
     * @param features
     * @return
     */
    JSONObject deleteFeature(List<HzCfg0Record> features) throws Exception;

    /**
     * 先删除后添加ERP
     *
     * @param projectPuid
     * @return
     */
    JSONObject deleteThenAdd(String projectPuid);

}
