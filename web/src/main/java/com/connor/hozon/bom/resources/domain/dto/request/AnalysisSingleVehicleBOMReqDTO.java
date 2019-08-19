/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.dto.request;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;

/**
 * @Author: haozt
 * @Date: 2018/9/27
 * @Description:解算单车BOM入参
 */
public class AnalysisSingleVehicleBOMReqDTO extends BaseDTO {
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 单车ID
     */
    private Long singleVehiclesId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Long getSingleVehiclesId() {
        return singleVehiclesId;
    }

    public void setSingleVehiclesId(Long singleVehiclesId) {
        this.singleVehiclesId = singleVehiclesId;
    }
}
