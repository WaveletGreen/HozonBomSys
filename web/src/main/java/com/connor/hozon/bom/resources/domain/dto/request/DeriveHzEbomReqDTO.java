package com.connor.hozon.bom.resources.domain.dto.request;

import java.util.Map;

/**
 * @Author: zl
 * @Date: 2019/8/14
 * @Description: 派生一条新EBOM
 */
public class DeriveHzEbomReqDTO {


    /**
     * 派生的新id(必填)
     */
    private String newLineId;
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
    private String parentLineId;

    public String getNewLineId() {
        return newLineId;
    }

    public void setNewLineId(String newLineId) {
        this.newLineId = newLineId;
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

    public String getParentLineId() {
        return parentLineId;
    }

    public void setParentLineId(String parentLineId) {
        this.parentLineId = parentLineId;
    }
}
