package com.connor.hozon.bom.resources.dto.request;

import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/22
 * @Description: 新增一条EBOM
 */
public class AddEbomReqDTO {

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 层级
     */
    private String level;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
}
