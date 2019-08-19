package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;

/**
 * @Author: haozt
 * @Date: 2018/8/16
 * @Description:
 */
public class SetLouReqDTO extends BaseDTO {
    private static final long serialVersionUID = 1459552556861660198L;
    private String projectId;

    private String puids;

    private String puid;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPuids() {
        return puids;
    }

    public void setPuids(String puids) {
        this.puids = puids;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }
}
