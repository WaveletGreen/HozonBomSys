/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/7/4
 * @Description:
 */
public class HzMaterielQuery extends DefaultQuery{
    /**
     * 项目id
     */
    private String projectId;
    /**
     * puid
     */
    private String puid;
    /**
     * 物料类型
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

    private String pMaterielCode;

    public String getpMaterielCode() {
        return pMaterielCode;
    }

    public void setpMaterielCode(String pMaterielCode) {
        this.pMaterielCode = pMaterielCode;
    }

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
