package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;

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
