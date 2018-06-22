package com.connor.hozon.bom.resources.dto.request;

import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/22
 * @Description: 新增一条EBOM
 */
public class AddEbomReqDTO {

    /**
     * 父puid
     */
    private String parentPuid;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 专业
     */
    private String pBomOfWhichDept;

    /**
     * EBOM 信息
     *
     */
    private Map<String,Object> map;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getParentPuid() {
        return parentPuid;
    }

    public void setParentPuid(String parentPuid) {
        this.parentPuid = parentPuid;
    }
}
