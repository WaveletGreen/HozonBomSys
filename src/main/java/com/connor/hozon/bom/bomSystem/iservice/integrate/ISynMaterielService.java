/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.integrate;

import com.connor.hozon.bom.resources.domain.dto.request.EditHzMaterielReqDTO;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public interface ISynMaterielService {
    /**
     * 执行更新的时候传
     *
     * @param dtos
     * @return
     */
    JSONObject updateOrAddByUids(List<EditHzMaterielReqDTO> dtos, String tableName, String field);

    /**
     * 删除时候传
     *
     * @param dtos
     * @return
     */
    JSONObject deleteByPuids(List<EditHzMaterielReqDTO> dtos,String tableName,String field);

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
    JSONObject synAllByProjectPuid(String projectPuid,String tableName,String field);
}
