package com.connor.hozon.bom.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/7/12
 * @Description:
 */
public class HzEbomTreeQuery {
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 当前查询树结构的根节点
     */
    private String puid;

    /**
     * 是否颜色件
     */
    private Integer isColorPart;
    /**
     * 是否装车件
     */
    private Integer isCarPart;

    public Integer getIsCarPart() {
        return isCarPart;
    }

    public void setIsCarPart(Integer isCarPart) {
        this.isCarPart = isCarPart;
    }

    public Integer getIsColorPart() {
        return isColorPart;
    }

    public void setIsColorPart(Integer isColorPart) {
        this.isColorPart = isColorPart;
    }

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
}
