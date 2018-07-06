package com.connor.hozon.bom.resources.dto.request;

/**
 * @Author: haozt
 * @Date: 2018/7/6
 * @Description:
 */
public class ApplyMbomDataTOHzMaterielReqDTO {
    /**
     * MBOM  puid 中间用，隔开 英文逗号
     */
    private String mbomPuids;
    /**
     * 类型（1 半成品工艺路线 2整车工艺路线  3总成分总成工艺路线  ）
     */
    private String type;
    /**
     * 项目id
     */
    private String projectId;

    public String getMbomPuids() {
        return mbomPuids;
    }

    public void setMbomPuids(String mbomPuids) {
        this.mbomPuids = mbomPuids;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
