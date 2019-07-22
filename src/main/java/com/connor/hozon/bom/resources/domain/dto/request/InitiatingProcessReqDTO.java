package com.connor.hozon.bom.resources.domain.dto.request;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;

/**
 * @Author: haozt
 * @Date: 2018/8/13
 * @Description: 发起流程 页面请求参数
 */
@Deprecated
public class InitiatingProcessReqDTO extends BaseDTO {
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 发起流程表单列表ids
     */
    private String puids;

    private String lineId;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

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
}
