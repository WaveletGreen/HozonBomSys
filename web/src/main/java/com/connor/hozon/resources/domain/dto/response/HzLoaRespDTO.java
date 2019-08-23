package com.connor.hozon.resources.domain.dto.response;

import cn.net.connor.hozon.common.entity.BaseDTO;

/**
 * @Author: haozt
 * @Date: 2018/7/18
 * @Description:
 */
public class HzLoaRespDTO extends BaseDTO {
    private String parentLevel;

    private String parentName;

    private String parentLineId;

    private String childLevel;

    private String childName;

    private String childLineId;

    public String getParentLevel() {
        return parentLevel;
    }

    public void setParentLevel(String parentLevel) {
        this.parentLevel = parentLevel;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentLineId() {
        return parentLineId;
    }

    public void setParentLineId(String parentLineId) {
        this.parentLineId = parentLineId;
    }

    public String getChildLevel() {
        return childLevel;
    }

    public void setChildLevel(String childLevel) {
        this.childLevel = childLevel;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildLineId() {
        return childLineId;
    }

    public void setChildLineId(String childLineId) {
        this.childLineId = childLineId;
    }
}
