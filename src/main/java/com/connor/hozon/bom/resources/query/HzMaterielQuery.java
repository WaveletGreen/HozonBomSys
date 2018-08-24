package com.connor.hozon.bom.resources.query;

/**
 * @Author: haozt
 * @Date: 2018/7/4
 * @Description:
 */
public class HzMaterielQuery {
    /**
     * 项目id
     */
    private String projectId;
    /**
     * puid
     */
    private String puid;
    /**
     * 物料类型  严格按照注释来读写数据
     * （11 整车超级物料主数据
     * 21 整车衍生物料主数据
     * 31 虚拟件物料主数据
     * 41半成品物料主数据
     * 51 生产性外购物料主数据
     * 61自制备件物料主数据）
     */
    private Integer pMaterielDataType;

    /**
     * 1整车工艺路线 2半成品工艺路线 3总成分总成工艺路线
     */
    private Integer type;
    /**
     * 物料来源id
     */
    private String materielResourceId;

    public String getMaterielResourceId() {
        return materielResourceId;
    }

    public void setMaterielResourceId(String materielResourceId) {
        this.materielResourceId = materielResourceId;
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

    public Integer getpMaterielDataType() {
        return pMaterielDataType;
    }

    public void setpMaterielDataType(Integer pMaterielDataType) {
        this.pMaterielDataType = pMaterielDataType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}