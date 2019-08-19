/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.dto.request;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:
 */
public class EditEWOImpactDeptReqDTO extends BaseDTO {
    private String deptIds;

    private String ewoNo;

    private String projectId;

    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    public String getEwoNo() {
        return ewoNo;
    }

    public void setEwoNo(String ewoNo) {
        this.ewoNo = ewoNo;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
