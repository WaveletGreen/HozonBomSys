package com.connor.hozon.bom.resources.dto.request;


import java.util.Map;

public class AddProcessComposeReqDTO {

    private String lineId;//1
    private String projectPuid;//2
    private String pBomLinePartName;//3
    private String pBomLinePartClass;//4
    private String puid;
    private String pBomOfWhichDept;

    /**
     * EBOM中的零件内容，是一个json串
     */
    private Map<String,Object> eBomContent;//5

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getProjectPuid() {
        return projectPuid;
    }

    public void setProjectPuid(String projectPuid) {
        this.projectPuid = projectPuid;
    }

    public String getpBomLinePartName() {
        return pBomLinePartName;
    }

    public void setpBomLinePartName(String pBomLinePartName) {
        this.pBomLinePartName = pBomLinePartName;
    }

    public String getpBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setpBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
    }

    public Map<String, Object> geteBomContent() {
        return eBomContent;
    }

    public void seteBomContent(Map<String, Object> eBomContent) {
        this.eBomContent = eBomContent;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }
}
