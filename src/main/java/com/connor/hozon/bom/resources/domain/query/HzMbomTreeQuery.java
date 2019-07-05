package com.connor.hozon.bom.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/7/11
 * @Description:
 */
public class HzMbomTreeQuery extends DefaultQuery {
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 当前查询树结构的根节点
     */
    private String puid;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 颜色id
     */
    private String colorId;

    /**
     * 状态标志位
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
