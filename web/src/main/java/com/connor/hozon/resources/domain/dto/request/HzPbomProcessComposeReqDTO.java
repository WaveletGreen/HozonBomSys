package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;

public class HzPbomProcessComposeReqDTO extends BaseDTO {

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 零件号
     */
    private String lineId;

    /**
     * puid
     */
    private String puid;


    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
}
