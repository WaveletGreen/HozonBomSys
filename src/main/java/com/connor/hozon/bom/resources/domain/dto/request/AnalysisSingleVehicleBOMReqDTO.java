package com.connor.hozon.bom.resources.domain.dto.request;

/**
 * @Author: haozt
 * @Date: 2018/9/27
 * @Description:解算单车BOM入参
 */
public class AnalysisSingleVehicleBOMReqDTO {
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
