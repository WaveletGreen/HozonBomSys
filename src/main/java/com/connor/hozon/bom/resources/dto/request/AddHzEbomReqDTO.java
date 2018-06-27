package com.connor.hozon.bom.resources.dto.request;

import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/22
 * @Description: 新增一条EBOM
 */
public class AddHzEbomReqDTO {

    /**
     * 项目id
     */
    private String projectId;

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


    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}
