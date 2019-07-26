package com.connor.hozon.bom.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/7/7
 * @Description: 查找Pbom树状结构
 */
public class HzPbomTreeQuery extends DefaultQuery{
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

    public Integer getIsColorPart() {
        return isColorPart;
    }

    public void setIsColorPart(Integer isColorPart) {
        this.isColorPart = isColorPart;
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
}
