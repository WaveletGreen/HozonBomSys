package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;

public class UpdateHzEbomLeveReqDTO extends BaseDTO {
    /**
     *查找编号
     */
    private String lineNo;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 调整层级的当前零件puid
     */
    private String puid;
    /**
     * 父层零件号(必填)
     */
    private String lineId;

    private Integer isHas;//判断是否有子层

    public Integer getIsHas() {
        return isHas;
    }

    public void setIsHas(Integer isHas) {
        this.isHas = isHas;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
}
